package org.ops4j.pax.exam.samples.simple;

import org.osgi.framework.Constants;
import org.ops4j.pax.exam.api.DroneConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.spi.junit.DroneTestCase;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleEquinoxTest extends DroneTestCase
{

    protected DroneConnector configure()
    {
        return create().setPlatform( Platforms.EQUINOX );
    }

    public void testSayHelloEclipse()
    {
        String name = bundleContext.getProperty( Constants.FRAMEWORK_VENDOR );
        assertEquals( "Eclipse", name );
        System.out.println( "Hello " + name );
    }
}