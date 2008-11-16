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
package org.ops4j.pax.drone.connector.paxrunner.intern;

import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.ops4j.pax.exam.api.BundleProvision;
import org.ops4j.pax.exam.api.RunnerContext;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 18, 2008
 */
public class PaxRunnerConnectorConfigurationImplTest
{

    @Test( expected = IllegalArgumentException.class )
    public void instanceWithAllNull()
    {
        new PaxRunnerConnectorImpl( null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void instanceWithNullProvision()
    {
        RunnerContext runnerContex = createMock( RunnerContext.class );
        new PaxRunnerConnectorImpl( runnerContex, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void instanceWithNullContext()
    {
        BundleProvision prov = createMock( BundleProvision.class );
        new PaxRunnerConnectorImpl( null, prov );
    }
}
