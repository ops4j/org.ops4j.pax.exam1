package org.ops4j.pax.drone.samples.complex;

import org.ops4j.pax.exam.api.DroneConnector;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;

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
