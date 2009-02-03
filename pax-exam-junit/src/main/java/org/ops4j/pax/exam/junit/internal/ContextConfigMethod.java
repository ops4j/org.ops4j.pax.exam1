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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.ops4j.lang.NullArgumentException.*;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.Context;

/**
 * Models a configuration method (those marked with {@link Configuration} and {@link Context} annotations).
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Toni Menzel (toni@okidokiteam.com)
 * @since 0.3.0, December 16, 2008
 */
public class ContextConfigMethod
    implements org.ops4j.pax.exam.junit.JUnit4ConfigMethod
{

    /**
     * Configuration method. Must be a accessible method (cannot be null).
     */
    private final Method m_method;
    /**
     * Instance of the class containing the configuration method. If null then the method is supposed to be static.
     */
    private final Object m_configInstance;
    /**
     * Array of context names that are matched against test method contexts (cannot be null or empty).
     */
    private final String[] m_contexts;
    /**
     * Configuration options. Initialized only when the getter is called.
     */
    private Option[] m_options;

    /**
     * Constructor.
     *
     * @param configMethod   configuration method (cannot be null)
     * @param configInstance instance of the class containing the test method.
     *                       If null then the method is supposed to be static.
     *
     * @throws IllegalArgumentException - If method is null
     */
    public ContextConfigMethod( final Method configMethod,
                                final Object configInstance )
    {
        validateNotNull( configMethod, "Configuration method" );

        m_method = configMethod;
        m_configInstance = configInstance;

        final Context contextAnnotation = configMethod.getAnnotation( Context.class );
        if( contextAnnotation != null )
        {
            m_contexts = contextAnnotation.value();
        }
        else
        {
            m_contexts = new String[]{ "" };
        }
    }

    /**
     * Matches a test method name against this configuration method.
     *
     * @param method test method name (cannot be null or empty)
     *
     * @return true if the test method name matches the configuration method, false otherwise
     *
     * @throws IllegalArgumentException - If method name is null or empty
     */
    public boolean matches( final Method method )
    {
        validateNotNull( method, "Method" );

        if( m_contexts != null )
        {
            for( String context : m_contexts )
            {
                final Context methodContextAnn = method.getAnnotation( Context.class );
                final String[] methodContexts;
                if( methodContextAnn != null )
                {
                    methodContexts = methodContextAnn.value();
                }
                else
                {
                    methodContexts = new String[]{ "" };
                }
                for( String methodContext : methodContexts )
                {
                    if( methodContext.equals( context ) )
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the configuration options for this configuration method.
     *
     * @return array of configuration options
     *
     * @throws IllegalAccessException - Re-thrown, from invoking the configuration method via reflection
     * @throws java.lang.reflect.InvocationTargetException
     *                                - Re-thrown, from invoking the configuration method via reflection
     * @throws InstantiationException - Re-thrown, from invoking the configuration method via reflection
     */
    public Option[] getOptions()
        throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if( m_options == null )
        {
            m_options = ( Option[] ) m_method.invoke( m_configInstance );

        }
        return m_options;
    }

}