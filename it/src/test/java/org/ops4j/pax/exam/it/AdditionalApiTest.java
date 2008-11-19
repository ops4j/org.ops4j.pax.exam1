package org.ops4j.pax.exam.it;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.ops4j.pax.exam.junit.OsgiTestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JunitSupport;
import org.ops4j.pax.exam.junit.EasyMockSupport;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.createBundleProvision;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static junit.framework.Assert.fail;

/**
 * @author Toni Menzel (tonit)
 * @since Nov 19, 2008
 */
@RunWith(OsgiTestRunner.class)
public class AdditionalApiTest {

    /**
     * Logger.
     */
    private static final Log LOG = LogFactory.getLog(AdditionalBundlesTest.class);

    @Configuration
    public TestRunnerConnector configure() {
        return create(createBundleProvision()
                .addBundle("mvn:org.ops4j.pax.logging/pax-logging-api")
                .addBundle("mvn:org.ops4j.pax.logging/pax-logging-service")
                // add support for junit and easymock at runtime.
                // Pax Exam provides some support helpers like JunitSupport to ease this step.
                .addBundle(JunitSupport.bundles())
        ).setPlatform(Platforms.FELIX);
    }

    @Test
    public void logMessage() {
        LOG.info("This test is just about logging this message");
    }

}
