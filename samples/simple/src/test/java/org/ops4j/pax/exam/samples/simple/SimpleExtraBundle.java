package org.ops4j.pax.exam.samples.simple;

import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.createBundleProvision;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.spi.junit.PaxExamTestCase;
import org.osgi.framework.Bundle;
import org.osgi.service.log.LogService;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleExtraBundle extends PaxExamTestCase
{

    protected TestRunnerConnector configure()
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
