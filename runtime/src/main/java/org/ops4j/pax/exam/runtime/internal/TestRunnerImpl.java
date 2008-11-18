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
package org.ops4j.pax.exam.runtime.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.osgi.framework.BundleContext;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.RecipeException;
import org.ops4j.pax.exam.api.TestExecutionException;
import org.ops4j.pax.exam.api.TestRunner;

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
    public String execute()
    {
        Class clazz;
        try
        {
            clazz = m_bundleContext.getBundle().loadClass( m_testCase );
        }
        catch( ClassNotFoundException e )
        {
            throw new TestExecutionException( "Test case class not found: " + m_testCase );
        }

        if( clazz != null )
        {
            for( Method m : clazz.getDeclaredMethods() )
            {
                if( m.getName().equals( m_testMethod ) )
                {
                    try
                    {
                        injectContextAndInvoke( clazz, m, clazz.newInstance(), m_bundleContext );
                    }
                    catch( InstantiationException e )
                    {
                        throw new TestExecutionException( "InstantiationException for : " + clazz + "," + m, e
                        );
                    }
                    catch( IllegalAccessException e )
                    {
                        throw new TestExecutionException( "IllegalAccessException for : " + clazz + "," + m, e
                        );
                    }
                    catch( RecipeException e )
                    {
                        throw new TestExecutionException( "Wrap the RecipeException", e );
                    }
                }
            }
        }
        else
        {
            throw new TestExecutionException( "Test case " + m_testCase + " not found!" );
        }
        return "";
    }

    private void injectContextAndInvoke( Class clazz, Method m, Object o, BundleContext bundleContext )
        throws RecipeException
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
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try
        {
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
        catch( Exception e )
        {
            throw new RecipeException( e );
        }
    }

}
