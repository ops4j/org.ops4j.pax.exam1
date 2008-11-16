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
package org.ops4j.pax.exam.connector.paxrunner.internal;

import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.ops4j.pax.exam.api.BundleProvision;
import org.ops4j.pax.exam.api.RunnerContext;
import org.ops4j.pax.exam.api.TestProbeProvider;
import org.ops4j.pax.exam.connector.paxrunner.PaxRunnerConnector;
import org.ops4j.pax.exam.connector.paxrunner.SubProcess;
import org.ops4j.pax.exam.zombie.RemoteTestRunnerClient;

import java.io.InputStream;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 17, 2008
 */
public class PaxRunnerConnectorTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testSingleCallEmpty()
        throws Exception
    {
        PaxRunnerConnectorImpl connector = new PaxRunnerConnectorImpl( null, null );
        connector.execute( null );
    }

    @Test
    public void testSingleCallNormal()
        throws Exception
    {
        PaxRunnerConnector config = createMock( PaxRunnerConnector.class );
        SubProcess p = createMock( SubProcess.class );
        p.startup();
        p.shutdown();
        replay( p, config );

        RemoteTestRunnerClient client = new RemoteTestRunnerClient( 0 )
        {
            public String execute()
            {
                return "";
            }

            public void install( InputStream inp )
            {

            }
        };
        RunnerContext context = createMock( RunnerContext.class );
        final TestProbeProvider provider = createMock( TestProbeProvider.class );
        final BundleProvision prov = createMock( BundleProvision.class );

        PaxRunnerConnectorImpl connector = new PaxRunnerConnectorImpl( context, prov )
        {

            public TestProbeProvider getBuilder()
            {
                return provider;
            }
        };

        try
        {
            connector.execute( p, client, System.out, new TestProbeProvider()
            {

                public InputStream build()
                {
                    return null;
                }
            }
            );
        } catch( RuntimeException e )
        {

        }
        verify( config, p );
    }
}
