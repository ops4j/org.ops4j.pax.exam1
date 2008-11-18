package org.ops4j.pax.exam.samples.simple;

import org.osgi.framework.Constants;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.OsgiTestCase;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleFelixTest extends OsgiTestCase
{

    protected TestRunnerConnector configure()
    {
        return create().setPlatform( Platforms.FELIX );
    }

    public void testSayHello()
    {
        String name = bundleContext.getProperty( Constants.FRAMEWORK_VENDOR );
        assertEquals( "Apache Software Foundation", name );
        System.out.println( "Hello " + name );
    }
}
