package org.ops4j.pax.exam.samples.simple;

import org.osgi.framework.Bundle;
import org.osgi.service.log.LogService;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.OsgiTestCase;
import org.ops4j.pax.exam.junit.JunitSupport;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleExtraBundle extends OsgiTestCase
{

    protected TestRunnerConnector configure()
    {
        return create( createBundleProvision()
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-api" )
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-service" )
            .updateBundles( false )
            .addBundle( JunitSupport.bundles() )
        ).setPlatform( Platforms.FELIX );
    }

    public void testBundlesInstalledAndServiceExposed()
    {
        // must have eight bundles
        assertEquals( 8, bundleContext.getBundles().length );
        // all should be running
        for( Bundle b : bundleContext.getBundles() )
        {
            assertEquals( Bundle.ACTIVE, b.getState() );
        }
        // there must be a service
        assertNotNull( bundleContext.getServiceReference( LogService.class.getName() ) );
    }
}
