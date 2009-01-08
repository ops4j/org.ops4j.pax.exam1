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
import org.ops4j.pax.exam.junit.AppliesTo;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.Context;

/**
 * Models a configuration method (those marked with {@link Configuration} annotation.
 * Currently this implementation accepts two different confuration assignment styles:
 * 1. the "old" appliesTo Annotation that uses the method name to match
 * 2. a new proof of concept of this:
 * Have a context annotation on both sides (configuration plus test itself) and let them match (no regex, no method name dependency).
 * Default context is "" .
 *
 * Because 2nd is just a proof of concept, the old appliesTo annotation has priority.
 * If it is set for a config, it is being used while matching with a test method. Them, any context annotation is being skipped.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Toni Menzel (toni@okidokiteam.com)
 * @since 0.3.0, December 16, 2008
 */
public class JUnit4ConfigMethod
{

    /**
     * Configuration method. Must be a static, accessible method (cannot be null).
     */
    private final Method m_method;
    /**
     * Array of regular expression that are matched against test method name (cannot be null or empty).
     */
    private final String[] m_patterns;

    private final String[] m_context;
    /**
     * Configuration options. Initialized only when the getter is called.
     */
    private Option[] m_options;

    /**
     * Constructor.
     *
     * @param method configuration method (cannot be null)
     *
     * @throws IllegalArgumentException - If method is null
     */
    public JUnit4ConfigMethod( final Method method )
    {
        validateNotNull( method, "Configuration method" );
        m_method = method;
        final AppliesTo appliesTo = method.getAnnotation( AppliesTo.class );
        final Context contextAnnotation = method.getAnnotation( Context.class );

        if( appliesTo != null )
        {
            m_patterns = appliesTo.value();
            m_context = null;
        }
        else
        {
            if( contextAnnotation != null )
            {
                m_context = contextAnnotation.value();
            }
            else
            {
                m_context = new String[]{ "" };
            }
            m_patterns = new String[]{ ".*" };
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

        if( m_context != null )
        {
            for( String context : m_context )
            {
                final Context methodContext = method.getAnnotation( Context.class );
                final String[] methodContexts;
                if( methodContext != null )
                {
                    methodContexts = methodContext.value();
                }
                else
                {
                    methodContexts = new String[]{ "" };
                }
                for( String v : methodContexts )
                {
                    if( v.equals( context ) )
                    {

                        System.out.println( v + " equals" + context + " in method " + method.getName() );
                        return true;
                    }
                }

            }
        }
        else if( m_patterns != null )
        {
            for( String pattern : m_patterns )
            {
                if( method.getName().matches( pattern ) )
                {
                    return true;
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
     * @throws IllegalAccessException    - Re-thrown, from invoking the configuration method via reflection
     * @throws InvocationTargetException - Re-thrown, from invoking the configuration method via reflection
     * @throws InstantiationException    - Re-thrown, from invoking the configuration method via reflection
     */
    public Option[] getOptions()
        throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if( m_options == null )
        {
            m_options = (Option[]) m_method.invoke( null );

        }
        return m_options;
    }

}
