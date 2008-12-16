/*
 * Copyright 2008 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.junit.internal;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.extender.Constants;
import org.ops4j.pax.exam.junit.extender.TestRunner;
import org.ops4j.pax.exam.options.FrameworkOption;
import org.ops4j.pax.exam.runtime.PaxExamRuntime;
import org.ops4j.pax.exam.spi.container.TestContainer;
import org.ops4j.pax.exam.spi.container.TestContainerFactory;

/**
 * TODO Add JavaDoc.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0 December 16, 2008
 */
public class JUnit4TestMethod
    extends TestMethod
{

    private final Method m_method;
    private final Option[] m_options;
    private final String m_name;
    private final String m_testBundleUrl;

    public JUnit4TestMethod( final Method method,
                             final TestClass testClass,
                             final FrameworkOption frameworkOption,
                             final Option... userOptions )
    {
        super( method, testClass );
        m_method = method;
        m_options = OptionUtils.combine( userOptions, frameworkOption );
        m_name = calculateName( method, frameworkOption );
        m_testBundleUrl = getTestBundleUrl( testClass.getName(), m_method.getName() );
    }

    @Override
    public void invoke( Object test )
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        final TestContainerFactory containerFactory = PaxExamRuntime.getTestContainerFactory();
        final TestContainer container = containerFactory.newInstance( m_options );
        container.startBundle( container.installBundle( m_testBundleUrl ) );
        final TestRunner testRunner = container.getService( TestRunner.class );
        try
        {
            testRunner.execute();
        }
        catch( InstantiationException e )
        {
            e.printStackTrace();
            //TODO handle exception
            throw new RuntimeException( e );
        }
    }

    private static String calculateName( final Method method,
                                         final FrameworkOption frameworkOption )
    {
        final StringBuilder name = new StringBuilder();
        name.append( method.getName() );
        if( frameworkOption != null )
        {
            name.append( " [" ).append( frameworkOption.getName() );
            final String version = frameworkOption.getVersion();
            if( version != null )
            {
                name.append( "/" ).append( version );
            }
            name.append( "]" );
        }
        return name.toString();
    }

    public Method getJavaMethod()
    {
        return m_method;
    }

    public String getName()
    {
        return m_name;
    }

    private static String getTestBundleUrl( final String testClassName,
                                            final String testMethodName )
    {
        final StringBuilder url = new StringBuilder();
        url.append( "dir:" )
            .append( new File( "." ).getAbsolutePath() )
            .append( "$" )
            .append( "anchor=" ).append( testClassName.replace( ".", "/" ) ).append( ".class" )
            .append( "," )
            .append( Constants.PROBE_TEST_CASE ).append( "=" ).append( testClassName )
            .append( "," )
            .append( Constants.PROBE_TEST_METHOD ).append( "=" ).append( testMethodName );
        return url.toString();
    }

}
