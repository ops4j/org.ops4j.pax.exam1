package org.ops4j.pax.exam.samples.simple;

import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.createBundleProvision;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.spi.junit.TestFirstTestCase;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public class TDDScenarioTest extends TestFirstTestCase
{

    protected TestRunnerConnector configureFailing()
    {
        return create().setPlatform( Platforms.FELIX );
    }

    protected TestRunnerConnector configureSuccessful()
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
