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

import org.ops4j.pax.exam.options.ProvisionOption;
import static org.ops4j.pax.runner.provision.scanner.ScannerConstants.*;

/**
 * Abstract implementation of {@link ScannerProvisionOption}.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 08, 2008
 */
abstract class AbstractScannerProvisionOption<T extends AbstractScannerProvisionOption>
    implements ProvisionOption
{

    /**
     * If the scanned bundles should be updated. If null default Pax Runner behavior will be used.
     */
    private Boolean m_shouldUpdate;
    /**
     * If the scanned bundles should be started. If null default Pax Runner behavior will be used.
     */
    private Boolean m_shouldStart;
    /**
     * Start level of scanned bundles. If null default Pax Runner behavior will be used.
     */
    private Integer m_startLevel;

    /**
     * Getter.
     *
     * @return true if the scanned bundles should be updated, false if not or null if the default behavior should be
     *         used
     */
    public Boolean shouldUpdate()
    {
        return m_shouldUpdate;
    }

    /**
     * Setter.
     *
     * @param shouldUpdate true if the scanned bundles should be updated, false otherwise
     *
     * @return itself, for fluent api usage
     */
    public T update( boolean shouldUpdate )
    {
        m_shouldUpdate = shouldUpdate;
        return itself();
    }

    /**
     * Setter. Specifyies that the scanned bundles should be updated.
     *
     * @return itself, for fluent api usage
     */
    public T update()
    {
        return update( true );
    }

    /**
     * Setter. Specifyies that the scanned bundles should not be updated.
     *
     * @return itself, for fluent api usage
     */
    public T noUpdate()
    {
        return update( false );
    }

    /**
     * Getter.
     *
     * @return true if the scanned bundles should be started, false if not or null if the default behavior should be
     *         used
     */
    public Boolean shouldStart()
    {
        return m_shouldStart;
    }

    /**
     * Setter.
     *
     * @param shouldStart true if the scanned bundles should be started, false otherwise
     *
     * @return itself, for fluent api usage
     */
    public T start( boolean shouldStart )
    {
        m_shouldStart = shouldStart;
        return itself();
    }

    /**
     * Setter. Specifyies that the scanned bundles should be started.
     *
     * @return itself, for fluent api usage
     */
    public T start()
    {
        return start( true );
    }

    /**
     * Setter. Specifyies that the scanned bundles should not be started.
     *
     * @return itself, for fluent api usage
     */
    public T noStart()
    {
        return start( false );
    }

    /**
     * Getter.
     *
     * @return start level the scanned bundles or null if the default behavior should be used
     */
    public Integer getStartLevel()
    {
        return m_startLevel;
    }

    /**
     * Setter.
     *
     * @param startLevel start level of the scanned bundles
     *
     * @return itself, for fluent api usage
     */
    public T startLevel( final int startLevel )
    {
        m_startLevel = startLevel;
        return itself();
    }

    /**
     * Implemented by sub classes in order to return itself (this) for fluent api usage
     *
     * @return itself
     */
    abstract T itself();

    /**
     * Returns common scanner options. Ment to be used by subclasses when building the url.
     *
     * @return common scanner options (cannot be null)
     */
    String getOptions()
    {
        final StringBuilder options = new StringBuilder();
        if( m_shouldUpdate != null && m_shouldUpdate )
        {
            options.append( SEPARATOR_OPTIONS ).append( UPDATE_OPTION );
        }
        if( m_shouldStart != null && !m_shouldStart )
        {
            options.append( SEPARATOR_OPTIONS ).append( NO_START_OPTION );
        }
        if( m_startLevel != null )
        {
            options.append( SEPARATOR_OPTIONS ).append( m_startLevel );
        }
        return options.toString();
    }

}