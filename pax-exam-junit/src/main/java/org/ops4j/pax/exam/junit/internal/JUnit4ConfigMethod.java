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

/**
 * Models a configuration method (those marked with {@link Configuration} annotation.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
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
        if( appliesTo != null )
        {
            m_patterns = appliesTo.value();
        }
        else
        {
            m_patterns = new String[]{ ".*" };
        }
    }

    /**
     * Matches a test method name against this configuration method.
     *
     * @param methodName test method name (cannot be null or empty)
     *
     * @return true if the test method name matches the configuration method, false otherwise
     *
     * @throws IllegalArgumentException - If method name is null or empty
     */
    public boolean matches( final String methodName )
    {
        validateNotEmpty( methodName, true, "Method name" );
        for( String pattern : m_patterns )
        {
            if( methodName.matches( pattern ) )
            {
                return true;
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
