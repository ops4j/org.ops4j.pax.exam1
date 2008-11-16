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
import org.ops4j.pax.exam.api.TestExecutionSummary;
import org.ops4j.pax.exam.api.TestProbeProvider;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import org.ops4j.pax.exam.spi.OnDemandTestProbeProvider;
import org.osgi.framework.BundleContext;

/**
 * @author Toni Menzel (tonit)
 * @since Nov 11, 2008
 */
public abstract class MultiConnectorPaxExamTestCase extends TestCase
{

    /**
     * Will be injected inside the framework
     */
    public BundleContext bundleContext = null;

    private transient TestRunnerConnector[] m_connectors;

    protected abstract TestRunnerConnector[] configure();

    protected final TestRunnerConnector[] getConnectors()
    {
        if( m_connectors == null )
        {
            m_connectors = configure();
        }
        return m_connectors;
    }

    /**
     * Instantiates a single runner per call. Slow but max. side-effect free.
     * Hence, the underlying builder should make sure that the probe will not be rebuild each rime (does not change)
     */
    public void runBare()
        throws Throwable
    {
        TestProbeProvider provider = new OnDemandTestProbeProvider( getName(), this.getClass().getName() );

        TestRunnerConnector[] connectors = getConnectors();
        int currentConfig = 0;
        TestExecutionSummary[] summaries = new TestExecutionSummary[connectors.length];

        for( TestRunnerConnector con : connectors )
        {
            summaries[ currentConfig++ ] = con.execute( provider );
        }

        JUnitSummaryHandling.handleSummary( summaries );
    }
}
