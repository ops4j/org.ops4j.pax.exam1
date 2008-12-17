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
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.AppliesTo;

/**
 * TODO Add JavaDoc.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 16, 2008
 */
public class JUnit4ConfigMethod
{

    private final Method m_method;
    private final String[] m_patterns;
    private Option[] m_options;

    public JUnit4ConfigMethod( final Method method )
    {
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

    public boolean matches( String methodName )
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
