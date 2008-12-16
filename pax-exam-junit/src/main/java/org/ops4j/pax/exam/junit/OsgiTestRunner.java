/*
 * Copyright 2008 Toni Menzel.
 * Copyright 2008 Alin Dreghiciu.
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
package org.ops4j.pax.exam.junit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import static org.ops4j.pax.exam.junit.JUnitOptions.*;
import org.ops4j.pax.exam.junit.extender.Constants;
import org.ops4j.pax.exam.junit.extender.TestRunner;
import org.ops4j.pax.exam.junit.internal.JUnitSummaryHandling;
import org.ops4j.pax.exam.junit.internal.SummaryImpl;
import org.ops4j.pax.exam.junit.internal.TestExecutionSummary;
import org.ops4j.pax.exam.junit.options.JUnitBundlesOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.runtime.PaxExamRuntime;
import org.ops4j.pax.exam.spi.container.TestContainer;
import org.ops4j.pax.exam.spi.container.TestContainerFactory;

/**
 * JUnit4 Runner to be used with the {@link org.junit.runner.RunWith} annotation to run with Pax Exam.
 *
 * @author Toni Menzel (tonit)
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since Oct 14, 2008
 */
public class OsgiTestRunner extends Runner
{

    private final Description m_suite;
    private List<Method> m_tests;
    private List<ConfigMethod> m_configs;

    public OsgiTestRunner( Class testClass )
    {
        m_suite = Description.createSuiteDescription( testClass );
        m_tests = new ArrayList<Method>();
        m_configs = new ArrayList<ConfigMethod>();
        final Object test;
        try
        {
            test = testClass.newInstance();
        }
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }

        for( Method m : testClass.getMethods() )
        {
            if( m.getAnnotation( Test.class ) != null )
            {
                m_suite.addChild( Description.createTestDescription( testClass, m.getName(), m.getAnnotations() ) );
                m_tests.add( m );
            }
            final Configuration config = m.getAnnotation( Configuration.class );
            if( config != null )
            {
                m_configs.add( new ConfigMethod( test, m, config.value() ) );
            }
        }

    }

    public Description getDescription()
    {
        return m_suite;
    }

    public void run( RunNotifier notifier )
    {

        for( int i = 0; i < m_tests.size(); i++ )
        {
            Method m = m_tests.get( i );
            Description d = m_suite.getChildren().get( i );

            runTest( notifier, m, d );

        }
    }

    private void runTest( RunNotifier notifier, Method m, Description d )

    {
        notifier.fireTestStarted( d );
        try
        {
            final TestContainerFactory containerFactory = PaxExamRuntime.getTestContainerFactory();
            final TestContainer container = containerFactory.newInstance( getOptions( m.getName() ) );
            container.startBundle(
                container.installBundle( getTestBundleUrl( m.getDeclaringClass().getName(), m.getName() ) )
            );
            final TestRunner testRunner = container.getService( TestRunner.class );
            // TODO verify the summary handling and exception handling
            TestExecutionSummary summary = new SummaryImpl();
            try
            {
                testRunner.execute();
            }
            catch( Exception e )
            {
                summary.setException( e );
            }
            JUnitSummaryHandling.handleSummary( summary );
        }
        catch( Throwable t )
        {
            notifier.fireTestFailure( new Failure( d, t ) );
        }

        notifier.fireTestFinished( d );

    }

    private String getTestBundleUrl( String testClassName, String testMethodName )
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

    private Option getOptions( final String methodName )
        throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        // always add the junit extender
        final DefaultCompositeOption option = new DefaultCompositeOption(
            mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam" )
                .version( Info.getPaxExamVersion() ),
            mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam-junit-extender" )
                .version( Info.getPaxExamVersion() ),
            mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam-junit-extender-impl" )
                .version( Info.getPaxExamVersion() ),
            mavenBundle()
                .group( "org.ops4j.pax.url" )
                .artifact( "pax-url-dir" )
                .version( "0.3.3-SNAPSHOT" )
        );
        // add options based on available configuration options from the test itself
        for( ConfigMethod config : m_configs )
        {
            if( config.mathches( methodName ) )
            {
                option.add( config.getOptions() );
            }
        }
        // add junit bundles, if the user did not add junit bundles into configuration
        if( OptionUtils.filter( JUnitBundlesOption.class, option ).length == 0 )
        {
            option.add( junitBundles() );
        }
        return option;
    }

    private class ConfigMethod
    {

        private final Object m_test;
        private final Method m_method;
        private final String[] m_patterns;
        private Option[] m_options;

        ConfigMethod( Object test, final Method method, final String[] patterns )
        {
            m_method = method;
            m_patterns = patterns;
            m_test = test;
        }

        boolean mathches( String methodName )
        {
            for( String pattern : m_patterns )
            {
                if( methodName.matches( pattern ) )
                {
                    return true;
                }
            }
            return false;
        }

        Option[] getOptions()
            throws IllegalAccessException, InvocationTargetException, InstantiationException
        {
            if( m_options == null )
            {
                m_options = (Option[]) m_method.invoke( m_test );

            }
            return m_options;
        }
    }

}
