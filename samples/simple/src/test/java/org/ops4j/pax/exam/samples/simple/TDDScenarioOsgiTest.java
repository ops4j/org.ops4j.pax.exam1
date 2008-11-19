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
package org.ops4j.pax.exam.samples.simple;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.TestFirstOsgiTestCase;
import org.ops4j.pax.exam.junit.JunitSupport;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public class TDDScenarioOsgiTest extends TestFirstOsgiTestCase
{

    protected TestRunnerConnector configureFailing()
    {
        return create(createBundleProvision().addBundle( JunitSupport.bundles() )).setPlatform( Platforms.FELIX );
    }

    protected TestRunnerConnector configureSuccessful()
    {
        return create( createBundleProvision()
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-api" )
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-service" )
            .addBundle( JunitSupport.bundles() )
        ).setPlatform( Platforms.FELIX );
    }

    public void testLogServicePublished()
    {
        // check if there is a log service implementation
        ServiceReference ref = bundleContext.getServiceReference( LogService.class.getName() );
        assertNotNull( ref );
    }

}
