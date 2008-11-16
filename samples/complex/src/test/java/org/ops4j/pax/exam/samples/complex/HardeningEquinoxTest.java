package org.ops4j.pax.exam.samples.complex;

import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public class HardeningEquinoxTest extends HardeningTest
{

    protected TestRunnerConnector configure()
    {
        return create().setPlatform( Platforms.EQUINOX );
    }
}
