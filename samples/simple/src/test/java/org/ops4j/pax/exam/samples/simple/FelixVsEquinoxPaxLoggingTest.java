package org.ops4j.pax.exam.samples.simple;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.MultiConfigOsgiTestCase;
import org.ops4j.pax.exam.junit.JunitSupport;

/**
 * This test compares felix and equinox directly in terms of compatibilty with pax logging support.
 *
 * @author Toni Menzel (tonit)
 * @since Nov 11, 2008
 */
public class FelixVsEquinoxPaxLoggingTest extends MultiConfigOsgiTestCase {

    protected TestRunnerConnector[] configure() {
        return new TestRunnerConnector[]{
                create(createBundleProvision()
                        .addBundle("mvn:org.ops4j.pax.logging/pax-logging-api")
                        .addBundle("mvn:org.ops4j.pax.logging/pax-logging-service")
                        .addBundle(JunitSupport.bundles())
                ).setPlatform(Platforms.FELIX),
                create(createBundleProvision()
                        .addBundle("mvn:org.ops4j.pax.logging/pax-logging-api")
                        .addBundle("mvn:org.ops4j.pax.logging/pax-logging-service")
                        .addBundle(JunitSupport.bundles())
                ).setPlatform(Platforms.EQUINOX)
        };
    }

    public void testServiceExposed() {
        ServiceReference ref = bundleContext.getServiceReference(LogService.class.getName());
        assertNotNull("LogService should be exposed", ref);

    }

}
