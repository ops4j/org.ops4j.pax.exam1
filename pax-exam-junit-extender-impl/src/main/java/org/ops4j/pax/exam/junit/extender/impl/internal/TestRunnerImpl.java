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
package org.ops4j.pax.exam.junit.extender.impl.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.osgi.framework.BundleContext;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.junit.extender.TestExecutionException;
import org.ops4j.pax.exam.junit.extender.TestRunner;

/**
 * Test runner service responsible for loading the test class, inject bundle context and run the test.
 *
 * @author Toni Menzel (tonit)
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since May 29, 2008
 */
class TestRunnerImpl
    implements TestRunner
{

    private BundleContext m_bundleContext;
    private final String m_testCase;
    private final String m_testMethod;

    /**
     * Constructor.
     *
     * @param bundleContext bundle context to be injected into test method
     * @param testCase      test case class name
     * @param testMethod    test method name
     */
    TestRunnerImpl( final BundleContext bundleContext,
                    final String testCase,
                    final String testMethod )
    {
        NullArgumentException.validateNotNull( bundleContext, "bundle context" );
        NullArgumentException.validateNotEmpty( testCase, true, "test case" );
        NullArgumentException.validateNotEmpty( testMethod, true, "test method" );

        m_testCase = testCase;
        m_testMethod = testMethod;
        m_bundleContext = bundleContext;
    }

    /**
     * {@inheritDoc}
     */
    public void execute()
        throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        try
        {
            final Class clazz = m_bundleContext.getBundle().loadClass( m_testCase );
            for( Method method : clazz.getDeclaredMethods() )
            {
                if( method.getName().equals( m_testMethod ) )
                {
                    injectContextAndInvoke( clazz, method, clazz.newInstance(), m_bundleContext );
                }
            }
        }
        catch( ClassNotFoundException e )
        {
            throw new TestExecutionException( "Test case class not found: " + m_testCase );
        }
    }

    private void injectContextAndInvoke( Class clazz, Method m, Object o, BundleContext bundleContext )
        throws IllegalAccessException, InvocationTargetException
    {
        try
        {
            Field field = clazz.getField( "bundleContext" );
            if( field != null )
            {
                field.setAccessible( true );
                field.set( o, bundleContext );
            }
        }
        catch( NoSuchFieldException e )
        {
            // skip that
        }
        catch( IllegalAccessException e )
        {
            // TODO print a warning message about bundle context field not being accessible
            //e.printStackTrace();
        }
        if( m.getParameterTypes().length == 1 )
        {
            // TODO add additional validation
            m.invoke( o, bundleContext );
        }
        else
        {
            m.invoke( o );
        }
    }

}
