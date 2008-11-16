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
package org.ops4j.pax.exam.sample;

import org.ops4j.pax.exam.api.BundleProvision;
import org.ops4j.pax.exam.api.RunnerContext;
import org.ops4j.pax.exam.connector.paxrunner.PaxRunnerConnector;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.connector.paxrunner.internal.PaxRunnerConnectorImpl;
import org.ops4j.pax.exam.spi.OnDemandTestProbeBuilder;
import org.ops4j.pax.exam.spi.internal.BundleProvisionImpl;
import org.ops4j.pax.exam.spi.internal.RunnerContextImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 2, 2008
 */
public class PaxRunnerSample
{

    public void simpleRecipe( BundleContext bundleContext )
    {
        System.out.println( "----------------------- THIS RUNS iNSIDE THE FRAMEWORK / START -----------------------" );
        Bundle[] bundles = bundleContext.getBundles();
        for( int i = 0; i < bundles.length; i++ )
        {
            System.out
                .println( "Found bundle: " + bundles[ i ].getSymbolicName() + " in state: " + bundles[ i ].getState() );
        }
        System.out.println( "----------------------- THIS RUNS iNSIDE THE FRAMEWORK / END -----------------------" );
    }

    public static void main( String[] args )
        throws Throwable
    {
        /**
         * Here we see the bare bones what we need to do in order to run a minimum but full featured pax exam run:
         *
         * We say: we want the PaxRunnerConnector:
         */
        RunnerContext context = new RunnerContextImpl();
        BundleProvision prov = new BundleProvisionImpl();
        PaxRunnerConnector connector = new PaxRunnerConnectorImpl( context, prov );
        connector.setPlatform( Platforms.FELIX );

        /**
         * the run method takes a recipe which must be resolvable.
         * Consult the connector configuration wiki for more about a specific connector.
         * Here we use the PaxRunnerConnector. 
         */
        connector.execute( new OnDemandTestProbeBuilder( "simpleRecipe", PaxRunnerSample.class.getName() ) );
    }
}
