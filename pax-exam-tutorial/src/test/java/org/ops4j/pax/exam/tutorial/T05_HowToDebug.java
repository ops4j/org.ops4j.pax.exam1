package org.ops4j.pax.exam.tutorial;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 3, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class T05_HowToDebug
{

    @Inject
    BundleContext bundleContext = null;

    @Configuration
    public static Option[] configure()
    {
        return options(
            // this just adds all what you write here to java vm argumenents of the (new) osgi process.
            vmOption( "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5006" ),
            // this is necessary to let junit runner not timout the remote process before attaching debugger
            // setting timeout to 0 means wait as long as the remote service comes available.
            timeout(0)
        );
    }

    @Test
    public void helloAgain()
    {
        System.out.println( "This is running inside Felix. With all configuration set up like you specified. " );
        // feel free to add breakpoints and debug.
        for( Bundle b : bundleContext.getBundles() )
        {
            System.out.println( "Bundle " + b.getBundleId() + " : " + b.getSymbolicName() );
        }

    }
}
