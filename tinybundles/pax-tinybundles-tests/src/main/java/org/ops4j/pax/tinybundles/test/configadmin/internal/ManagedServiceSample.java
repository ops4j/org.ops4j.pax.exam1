package org.ops4j.pax.tinybundles.test.configadmin.internal;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.ops4j.pax.tinybundles.test.configadmin.SampleService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
public class ManagedServiceSample implements ManagedService, SampleService
{

    private static Log LOG = LogFactory.getLog( ManagedServiceSample.class );
    Hashtable p = new Hashtable();

    public void updated( Dictionary dictionary )
        throws ConfigurationException
    {
        LOG.info( "-------------------- dict is " + dictionary + " . Received event!" );
        Enumeration enu = dictionary.keys();
        while( enu.hasMoreElements() )
        {
            String key = (String) enu.nextElement();
            String val = (String) dictionary.get( key );
            LOG.info( "Retrieved: key=" + key + ", value=" + val );
        }

    }

    public String hello( String name )
    {
        return p.get( "greeting" ) + name;
    }
}
