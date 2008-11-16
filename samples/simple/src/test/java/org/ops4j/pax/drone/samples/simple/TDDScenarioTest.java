package org.ops4j.pax.drone.samples.simple;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.ops4j.pax.exam.api.DroneConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.spi.junit.TestFirstTestCase;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public class TDDScenarioTest extends TestFirstTestCase
{

    protected DroneConnector configureFailing()
    {
        return create().setPlatform( Platforms.FELIX );
    }

    protected DroneConnector configureSuccessful()
    {
        return create( createBundleProvision()
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-api" )
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-service" )
        ).setPlatform( Platforms.FELIX );
    }

    public void testLogServicePublished()
    {
        // check if there is a log service implementation
        ServiceReference ref = bundleContext.getServiceReference( LogService.class.getName() );
        assertNotNull( ref );
    }

}
