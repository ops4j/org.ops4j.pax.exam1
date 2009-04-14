package org.ops4j.pax.demo.exam;

import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;

/**
 * This is example will use TinyBundles at runtime (inside osgi itself).
 * You need TinyBundles at runtime in this case.
 *
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class BuildWhileRunningTest
{

    @Configuration
    public static Option[] configure()
    {
        return options(
            provision(
                mavenBundle()
                    .groupId( "org.ops4j.pax.tinybundles" )
                    .artifactId( "pax-tinybundles-core" )
                    .version( "0.4.0-SNAPSHOT"
                )
            )

        );
    }

    @Inject
    BundleContext context;

    @Test
    public void runMyService()
        throws BundleException, IOException
    {
        // first build and install a tinybundle:
        Bundle b = context.installBundle( "file:/dev/null/foo", newBundle()
            .set( Constants.BUNDLE_SYMBOLICNAME, "MyFirstTinyBundle" ).build( asStream() )
        );

        b.start();
        boolean found = false;

        // check if bundle was found:
        for( Bundle bundle : context.getBundles() )
        {
            if( bundle.getSymbolicName().equals( "MyFirstTinyBundle" ) )
            {
                found = true;
            }
        }
        assertTrue( found );

    }
}
