package org.ops4j.pax.exam.samples.simple;

import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.spi.junit.PaxExamTestCase;
import org.osgi.framework.Constants;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleFelixTest extends PaxExamTestCase
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
