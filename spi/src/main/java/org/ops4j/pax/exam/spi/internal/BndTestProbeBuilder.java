/*
 * Copyright 2008 Toni Menzel
 * Copyright 2008 Alin Dreghiciu
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.spi.internal;

import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.TestProbeBuilder;
import org.ops4j.pax.exam.api.TestRunner;
import org.ops4j.pax.exam.spi.ResourceLocator;
import org.ops4j.pax.exam.spi.util.BndUtils;
import org.ops4j.pax.exam.spi.util.IOUtils;

import java.io.*;
import java.util.Properties;
import java.util.jar.JarOutputStream;

/**
 * Responsible for creating the on-the fly testing probe.
 *
 * @author Toni Menzel (tonit)
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since May 29, 2008
 */
public class BndTestProbeBuilder
    implements TestProbeBuilder
{

    private ResourceLocator m_finder;
    private String m_testClass;
    private String m_testMethod;

    /**
     * Constructor.
     *
     * @param testClass  name of test class
     * @param testMethod name of the test method
     * @param finder     locator that gathers all resources that have to be inside the test probe
     */
    public BndTestProbeBuilder( final String testClass,
                                 final String testMethod,
                                 final ResourceLocator finder )
    {
        NullArgumentException.validateNotNull( testClass, "recipeHost" );
        NullArgumentException.validateNotNull( finder, "finder" );

        m_finder = finder;
        m_testClass = testClass;
        m_testMethod = testMethod;
    }

    /**
     * {@inheritDoc}
     */
    public InputStream build()
    {
        try
        {
            // 1. create a basic jar with all classes in it..
            final PipedOutputStream pout = new PipedOutputStream();
            PipedInputStream fis = new PipedInputStream( pout );
            new Thread()
            {

                public void run()
                {
                    JarOutputStream jos;
                    try
                    {
                        jos = new DuplicateAwareJarOutputStream( pout );
                        m_finder.write( jos );
                        jos.close();
                    }

                    catch( IOException e )
                    {
                        //throw new RuntimeException( e );
                    }
                    finally
                    {
                        try
                        {
                            pout.close();
                        }
                        catch( Exception e )
                        {
                            //  throw new TestExecutionException( "Cannot close builder stream ??", e );
                        }
                    }
                }
            }.start();

            // 2. wrap and calc manifest using bnd
            Properties props = new Properties();
            // Recipe Host
            props.put( TestRunner.PROBE_TEST_CASE, m_testClass );
            props.put( TestRunner.PROBE_TEST_METHOD, m_testMethod );

            // include connector clazzes to be used inside
            //props.put( "Private-Package", "org.ops4j.pax.exam.connector.*" );
            InputStream result = BndUtils.createBundle( fis, props, TestRunner.PROBE_SYMBOLICNAME );
            fis.close();
            pout.close();
            return result;
        }
        catch( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Store result into an intermediate file. Just used for now to see what actually is inside the stream.
     *
     * @param inputStream stream will be store into a local file
     *
     * @return a new stream reading from that file.
     */
    private InputStream traceResultBundle( InputStream inputStream )
        throws IOException
    {
        File finalOutput = File.createTempFile( "paxExamFinal", "jar" );
        FileOutputStream outstream = new FileOutputStream( finalOutput );
        IOUtils.copy( inputStream, outstream );
        inputStream.close();
        outstream.close();
        return new FileInputStream( finalOutput );
    }


}
