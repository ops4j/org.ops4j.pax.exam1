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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import org.osgi.framework.BundleContext;
import static org.ops4j.lang.NullArgumentException.*;
import org.ops4j.pax.exam.junit.extender.CallableTestMethod;

/**
 * {@link Callable} implementation.
 *
 * TODO implement calls to @Before and @After
 *
 * @author Toni Menzel (tonit)
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since May 29, 2008
 */
class CallableTestMethodImpl
    implements CallableTestMethod
{

    /**
     * Bundle context of the bundle containing the test class (cannot be null).
     */
    private BundleContext m_bundleContext;
    /**
     * Test class name (cannot be null or empty).
     */
    private final String m_testClassName;
    /**
     * Test method name (cannot be null or empty).
     */
    private final String m_testMethodName;

    /**
     * Constructor.
     *
     * @param bundleContext  bundle context of the bundle containing the test class (cannot be null)
     * @param testClassName  test class name (cannot be null  or empty)
     * @param testMethodName test method name (cannot be null or empty)
     *
     * @throws IllegalArgumentException - If bundle context is null
     *                                  - If test class name is null or empty
     *                                  - If test method name is null or empty
     */
    CallableTestMethodImpl( final BundleContext bundleContext,
                            final String testClassName,
                            final String testMethodName )
    {
        validateNotNull( bundleContext, "Bundle context" );
        validateNotEmpty( testClassName, true, "Test class name" );
        validateNotEmpty( testMethodName, true, "Test method name" );

        m_bundleContext = bundleContext;
        m_testClassName = testClassName;
        m_testMethodName = testMethodName;
    }

    /**
     * {@inheritDoc}
     */
    public void call()
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        final Class testClass = m_bundleContext.getBundle().loadClass( m_testClassName );
        for( final Method testMethod : testClass.getDeclaredMethods() )
        {
            if( testMethod.getName().equals( m_testMethodName ) )
            {
                injectContextAndInvoke( testClass.newInstance(), testMethod );
            }
        }
    }

    /**
     * Invokes the bundle context (if possible and required) and executes the test method.
     *
     * @param testInstance an instance of the test class
     * @param testMethod   test method
     *
     * @throws IllegalAccessException    - Re-thrown from reflection invokation
     * @throws InvocationTargetException - Re-thrown from reflection invokation
     */
    private void injectContextAndInvoke( final Object testInstance,
                                         final Method testMethod )
        throws IllegalAccessException, InvocationTargetException
    {
        final Class<?>[] paramTypes = testMethod.getParameterTypes();
        // if there is only one param and is of type BundleContext we inject it, otherwise just call
        // this means that if there are actual params the call will fail, but that is okay as it will be reported back
        if( paramTypes.length == 1
            && paramTypes[ 0 ].isAssignableFrom( BundleContext.class ) )
        {
            testMethod.invoke( testInstance, m_bundleContext );
        }
        else
        {
            testMethod.invoke( testInstance );
        }
    }

}
