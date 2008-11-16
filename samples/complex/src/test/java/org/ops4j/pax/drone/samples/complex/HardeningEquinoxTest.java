package org.ops4j.pax.drone.samples.complex;

import org.ops4j.pax.drone.api.DroneConnector;
import org.ops4j.pax.drone.spi.intern.RunnerContextImpl;
import org.ops4j.pax.drone.api.RunnerContext;
import org.ops4j.pax.drone.connector.paxrunner.PaxRunnerConnector;
import org.ops4j.pax.drone.connector.paxrunner.Platforms;
import static org.ops4j.pax.drone.connector.paxrunner.GenericConnector.create;
import org.ops4j.pax.drone.connector.paxrunner.intern.PaxRunnerConnectorImpl;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public class HardeningEquinoxTest extends HardeningTest
{

    protected DroneConnector configure()
    {
         return create().setPlatform( Platforms.EQUINOX );
    }
}
