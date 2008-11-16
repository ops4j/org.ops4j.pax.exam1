/*
 * Copyright 2008 Toni Menzel
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;
import java.util.jar.JarOutputStream;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.DroneProvider;
import org.ops4j.pax.exam.api.DroneService;
import org.ops4j.pax.exam.spi.ResourceLocator;
import org.ops4j.pax.exam.spi.util.DroneUtils;

/**
 * @author Toni Menzel (tonit)
 * @since May 29, 2008
 *        Responsible for creating the on-the fly testing drone.
 *        Expected to radically change once things are working. (1st make it work, 2nd make it efficient)
 */
public class DroneBuilderImpl
    implements DroneProvider
{

    private ResourceLocator m_finder;
    private String m_recipeHost;
    private String m_recipeCode;

    /**
     * @param recipe name of test class
     * @param finder locator that gathers all resources that have to be inside the drone
     */
    public DroneBuilderImpl( String recipe, String recipeHost, ResourceLocator finder )
    {
        NullArgumentException.validateNotNull( recipeHost, "recipeHost" );
        NullArgumentException.validateNotNull( finder, "finder" );

        m_finder = finder;
        m_recipeHost = recipeHost;
        m_recipeCode = recipe;
    }

    /**
     * Implementation that uses bnd to calculate the final stuff.
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
                    JarOutputStream jos = null;
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
                            //  throw new DroneException( "Cannot close builder stream ??", e );
                        }
                    }
                }
            }.start();

            // 2. wrap and calc manifest using bnd
            Properties props = new Properties();
            // Recipe Host
            props.put( DroneService.DRONE_RECIPE_HOST_HEADER, m_recipeHost );
            props.put( DroneService.DRONE_RECIPE_CODE_HEADER, m_recipeCode );

            // include connector clazzes to be used inside
            //props.put( "Private-Package", "org.ops4j.pax.drone.connector.*" );
            InputStream result = DroneUtils.createBundle( fis, props, DroneService.DRONE_SYMBOLICNAME );
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
        File finalOutput = File.createTempFile( "droneFinal", "jar" );
        FileOutputStream outstream = new FileOutputStream( finalOutput );
        DroneUtils.copy( inputStream, outstream );
        inputStream.close();
        outstream.close();
        return new FileInputStream( finalOutput );
    }


}