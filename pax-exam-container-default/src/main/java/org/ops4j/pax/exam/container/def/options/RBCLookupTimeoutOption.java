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
package org.ops4j.pax.exam.container.def.options;

import static org.ops4j.lang.NullArgumentException.*;
import org.ops4j.pax.exam.Option;

/**
 * Option specifying the timeout (in milliseconds) while looking up the container process.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0 December 10, 2008
 */
public class RBCLookupTimeoutOption
    implements Option
{

    /**
     * Timeout in milliseconds (cannot be null).
     */
    private final Integer m_timeout;

    /**
     * Constructor.
     *
     * @param timeout timeout (in millis) to look up the server part
     *
     * @throws IllegalArgumentException - If timeout is null
     */
    public RBCLookupTimeoutOption( final Integer timeout )
    {
        validateNotNull( timeout, "Timeout" );
        m_timeout = timeout;
    }

    /**
     * Getter.
     *
     * @return timeout in millis (cannot be null)
     */
    public Integer getTimeout()
    {
        return m_timeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "TimeoutOption" );
        sb.append( "{timeout=" ).append( m_timeout );
        sb.append( '}' );
        return sb.toString();
    }

}
