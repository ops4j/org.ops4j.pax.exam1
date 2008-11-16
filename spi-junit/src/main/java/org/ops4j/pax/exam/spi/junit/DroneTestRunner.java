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
package org.ops4j.pax.exam.spi.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.ops4j.pax.exam.api.DroneConnector;
import org.ops4j.pax.exam.api.DroneProvider;
import org.ops4j.pax.exam.spi.OnDemandDroneProvider;

/**
 * JUnit4 Runner to be used with the {@link org.junit.runner.RunWith} annotation
 * to run with Pax Drone.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 14, 2008
 */
public class DroneTestRunner extends Runner
{

    private final Class m_clazz;
    private final Description m_suite;
    private Method m_config;
    private ArrayList<Method> m_tests;

    public DroneTestRunner( Class testClazz )
    {
        m_clazz = testClazz;
        m_suite = Description.createSuiteDescription( m_clazz );
        m_tests = new ArrayList<Method>();

        for( Method m : m_clazz.getMethods() )
        {
            if( m.getAnnotation( Test.class ) != null )
            {
                m_suite.addChild( Description.createTestDescription( m_clazz, m.getName(), m.getAnnotations() ) );
                m_tests.add( m );
            }

            if( m.getAnnotation( DroneConfiguration.class ) != null )
            {
                m_config = m;
            }
        }

    }

    public Description getDescription()
    {
        return m_suite;
    }

    public void run( RunNotifier notifier )
    {

        if( m_config == null )
        {
            notifier.fireTestFailure( new Failure( m_suite, new RuntimeException( "No configuation provided!" ) ) );
        }

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
        DroneProvider provider = new OnDemandDroneProvider( m.getName(), m_clazz.getName() );
        DroneConnector connector = null;
        try
        {
            connector = (DroneConnector) m_config.invoke( m_clazz.newInstance() );
        }
        catch( Exception e )
        {
            notifier.fireTestFailure( new Failure( d, e ) );
        }
        try
        {
            JUnitSummaryHandling.handleSummary( connector.execute( provider ) );
        }
        catch( Throwable t )
        {
            notifier.fireTestFailure( new Failure( d, t ) );
        }

        notifier.fireTestFinished( d );

    }
}
