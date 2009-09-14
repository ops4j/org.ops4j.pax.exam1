package org.examples.twitterclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Customizer;
import org.examples.twitterclient.api.TwitterService;
import org.examples.twitterclient.api.TwitterBackend;
import org.examples.twitterclient.service.MockTwitterImpl;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import org.ops4j.io.StreamUtils;

@RunWith( JUnit4TestRunner.class )
public class TwitterServiceTest
{

    @Configuration
    public static Option[] configure()
    {
        return options(
            // add Guice
            provision( mavenBundle( "com.google.inject", "guice", "2.0" ) ),
            // the API Bundle
            provision(
                newBundle()
                    .add( TwitterService.class )
                    .add( TwitterBackend.class )
                    .build( withBnd() )
            ),
            // the service bundle
            provision(
                newBundle()
                    .add( MockTwitterImpl.class )
                    .add( MockTwitterImpl.Foo.class )
                    .build( withBnd() )
            ),
            new Customizer()
            {
                @Override
                public InputStream customizeTestProbe( InputStream testProbe )
                    throws IOException
                {
                    // change test probe on the fly.
                    // you can do everything:
                    // - remove classes
                    // - add stuff
                    // - change manifest
                    return modifyBundle( testProbe )
                        .set( Constants.BUNDLE_SYMBOLICNAME, "My-very-own-probe" )
                        .build();
                }
            }

        );
    }

    @Inject
    BundleContext context;

    //@Test
    public void runMyService()
        throws BundleException, IOException
    {
        for( Bundle b : context.getBundles() )
        {
            System.out.println( "b: " + b.getSymbolicName() + " is " + b.getState() );
        }
        // check classloader refs:
        assertNotNull( MockTwitterImpl.class );
        assertNotNull( MockTwitterImpl.Foo.class );

        new MockTwitterImpl.Foo();

        System.out.println();
        // check and call service
        ServiceReference ref = context.getServiceReference( TwitterService.class.getName() );
        assertNotNull( ref );
        TwitterService service = (TwitterService) context.getService( ref );
        assertNotNull( service );
        service.send( "Toni" );
        context.ungetService( ref );
    }

    //@Test
    public void p1()

    {
        System.out.println( "Hello!" );
    }

    @Test
    public void p2()

    {
        System.out.println( "Hello!" );
        assertEquals("My-very-own-probe",context.getBundle().getHeaders(  ).get( Constants.BUNDLE_SYMBOLICNAME ));
    }
}

