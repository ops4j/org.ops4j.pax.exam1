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
