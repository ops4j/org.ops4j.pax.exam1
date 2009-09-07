package org.ops4j.pax.exam.twitterexample;

import java.io.IOException;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Constants;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.twitterexample.api.TwitterService;
import org.ops4j.pax.exam.twitterexample.api.TwitterBackend;
import org.ops4j.pax.exam.twitterexample.service.MockTwitterImpl;
import org.ops4j.pax.exam.it.HelloWorld;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;

@RunWith( JUnit4TestRunner.class )
public class TwitterServiceTest
{

    @Configuration
    public static Option[] configure()
    {
        return options(
            // add Guice
            
            // the API Bundle
            provision(
                newBundle()
                    .add( TwitterService.class )
                    .add( TwitterBackend.class )
                    .prepare( withBnd() ).build()
            ),
            // the service bundle
            provision(
                newBundle()
                    .add( MockTwitterImpl.class )
                    .prepare( withBnd() ).build()
            )

        );
    }

    @Inject
    BundleContext context;

    @Test
    public void runMyService()
        throws BundleException, IOException
    {
        // check and call service
        ServiceReference ref = context.getServiceReference( TwitterService.class.getName() );
        assertNotNull( ref );
        TwitterService service = (TwitterService) context.getService( ref );
        assertNotNull( service );
        service.send( "Toni" );
        context.ungetService( ref );
    }
}

