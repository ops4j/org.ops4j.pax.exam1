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

import junit.framework.TestCase;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.api.DroneSummary;
import org.ops4j.pax.exam.spi.OnDemandDroneProvider;
import org.ops4j.pax.exam.api.DroneConnector;
import org.ops4j.pax.exam.api.DroneProvider;

/**
 * @author Toni Menzel (tonit)
 * @since Nov 11, 2008
 */
public abstract class MultiConnectorDroneTestCase extends TestCase
{

    /**
     * Will be injected inside the framework
     */
    public BundleContext bundleContext = null;

    private transient DroneConnector[] m_connectors;

    protected abstract DroneConnector[] configure();

    protected final DroneConnector[] getConnectors()
    {
        if( m_connectors == null )
        {
            m_connectors = configure();
        }
        return m_connectors;
    }

    /**
     * Instantiates a single runner per call. Slow but max. side-effect free.
     * Hence, the underlying builder should make sure that the drone will not be rebuild each rime (does not change)
     */
    public void runBare()
        throws Throwable
    {
        DroneProvider provider = new OnDemandDroneProvider( getName(), this.getClass().getName() );

        DroneConnector[] connectors = getConnectors();
        int currentConfig = 0;
        DroneSummary[] summaries = new DroneSummary[connectors.length];
        
        for( DroneConnector con : connectors )
        {
            summaries[ currentConfig++ ] = con.execute( provider );
        }

        JUnitSummaryHandling.handleSummary( summaries );
    }
}
