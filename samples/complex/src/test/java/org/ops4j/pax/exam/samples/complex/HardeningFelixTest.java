package org.ops4j.pax.exam.samples.complex;

import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.createBundleProvision;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.JunitSupport;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public class HardeningFelixTest extends HardeningTest
{

    protected TestRunnerConnector configure()
    {
        return create( createBundleProvision()
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-api" )
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-service" )
            .addBundle( JunitSupport.bundles() )
        ).setPlatform( Platforms.FELIX );
    }
}
