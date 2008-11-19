package org.ops4j.pax.exam.samples.simple;

import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JunitSupport;
import org.ops4j.pax.exam.junit.OsgiTestCase;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;

/**
 * @author Toni Menzel (tonit)
 * @since Nov 19, 2008
 */
public class DebugFelixSample extends OsgiTestCase
{

    public TestRunnerConnector configure()
    {
        return create( createBundleProvision()
            // see AddtionalApiTest for explanation
            .addBundle( JunitSupport.bundles() )
        )
            .setPlatform( Platforms.FELIX )
            .addVMOption( "-Xdebug" )
            .addVMOption( " -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005" )
            .setStartupTimeout( 0 );
    }

    public void testWithDebugger()
    {
        // 1. set breakpoint on next line:
        System.out.println( "Foo" );
        assertNotNull( bundleContext );
        // 2. then run your test with as usual
        // 3. then start a remote debugger session (use port 5005 as mentioned above)
    }
}
