package org.ops4j.pax.tinybundles.test.simpliest.internal;

import java.util.Dictionary;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.ops4j.pax.tinybundles.test.simpliest.HelloWorldService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
public class SimpliestSampleActivator implements BundleActivator
{

    private ServiceRegistration ref;

    public void start( BundleContext bundleContext )
        throws Exception
    {
        Dictionary dict = new Properties();

        ref = bundleContext.registerService( HelloWorldService.class.getName(), new HelloWorldImpl(), dict );
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        ref.unregister();
    }
}
