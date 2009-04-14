package org.ops4j.pax.tinybundles.test.configadmin.internal;

import java.util.Dictionary;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.ops4j.pax.tinybundles.test.configadmin.SampleService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
public class ConfigAdminSampleActivator implements BundleActivator, ManagedServiceFactory
{

    private static Log LOG = LogFactory.getLog( ConfigAdminSampleActivator.class );
    private ServiceRegistration ref;
    public static final String PID = "samples.configadmin";

    public void start( BundleContext bundleContext )
        throws Exception
    {
        Hashtable props = new Hashtable();
        props.put( Constants.SERVICE_PID, getName() );
        ref = bundleContext.registerService( ManagedServiceFactory.class.getName(), this, props );

        Hashtable dict = new Hashtable();
        dict.put( Constants.SERVICE_PID, PID );
        dict.put( "greeting", "Guten Morgen, " );
        ref = bundleContext.registerService( SampleService.class.getName(), new ManagedServiceSample(), dict );
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        ref.unregister();
    }

    public String getName()
    {
        return "sample.configadmin.factory";
    }

    public void updated( String s, Dictionary dictionary )
        throws ConfigurationException
    {
        LOG.info( "Got Update PID" + s );

    }

    public void deleted( String s )
    {
        LOG.info( "Got Delete PID" + s );
    }
}
