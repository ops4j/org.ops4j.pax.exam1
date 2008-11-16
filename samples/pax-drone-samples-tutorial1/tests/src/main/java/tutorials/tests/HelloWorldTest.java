package tutorials.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.pax.drone.api.ConnectorConfiguration;
import org.ops4j.pax.drone.connector.paxrunner.ConnectorConfigurationFactory;
import org.ops4j.pax.drone.connector.paxrunner.Platforms;
import org.ops4j.pax.drone.spi.junit.DroneTestCase;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 13, 2008
 */
public class HelloWorldTest extends DroneTestCase {
    public static final Log logger = LogFactory.getLog(HelloWorldTest.class);

    public ConnectorConfiguration configure() {
        return ConnectorConfigurationFactory.create(this)
                .setPlatform(Platforms.FELIX)
                .addBundle("mvn:org.ops4j.pax.logging/pax-logging-api")
                // here we use the normal mvn handler
                .addBundle("mvn:org.ops4j.pax.exam/tutorial-helloworld");
    }

    public void testCheckFramework() {
        // do nothing.
    }

    

}
