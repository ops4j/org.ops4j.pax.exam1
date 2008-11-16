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
package org.ops4j.pax.drone.spi.junit;

import junit.framework.TestCase;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.api.DroneConnector;
import org.ops4j.pax.exam.spi.OnDemandDroneProvider;

/**
 * This will be instantiated by the local junit runner as well as inside inside the OSGi Container.
 * (where the actual test will run)
 *
 * @author Toni Menzel (tonit)
 * @since May 28, 2008
 */
public abstract class DroneTestCase extends TestCase
{

    /**
     * Will be injected inside the framework
     */
    public BundleContext bundleContext = null;

    private transient DroneConnector m_connector;

    protected abstract DroneConnector configure();

    protected final DroneConnector getConnector()
    {
        if( m_connector == null )
        {
            m_connector = configure();
        }
        return m_connector;
    }

    /**
     * Instantiates a single runner per call. Slow but max. side-effect free.
     * Hence, the underlying builder should make sure that the drone will not be rebuild each rime (does not change)
     */
    public void runBare()
        throws Throwable
    {
        JUnitSummaryHandling.handleSummary(
            getConnector().execute( new OnDemandDroneProvider( getName(), this.getClass().getName() ) )
        );


    }


}
