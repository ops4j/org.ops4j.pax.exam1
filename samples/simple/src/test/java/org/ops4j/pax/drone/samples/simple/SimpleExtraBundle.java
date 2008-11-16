package org.ops4j.pax.drone.samples.simple;

import org.osgi.framework.Bundle;
import org.osgi.service.log.LogService;
import org.ops4j.pax.exam.api.DroneConnector;
import static org.ops4j.pax.drone.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.drone.connector.paxrunner.Platforms;
import org.ops4j.pax.drone.spi.junit.DroneTestCase;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleExtraBundle extends DroneTestCase
{

    protected DroneConnector configure()
    {
        return create( createBundleProvision()
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-api" )
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-service" )
            .updateBundles( false )
        ).setPlatform( Platforms.FELIX );
    }

    public void testBundlesInstalledAndServiceExposed()
    {
        // must have three bundles
        assertEquals( 7, bundleContext.getBundles().length );
        // all should be running
        for( Bundle b : bundleContext.getBundles() )
        {
            assertEquals( Bundle.ACTIVE, b.getState() );
        }
        // there must be a service
        assertNotNull( bundleContext.getServiceReference( LogService.class.getName() ) );
    }
}
