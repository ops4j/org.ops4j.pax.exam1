package org.ops4j.pax.tinybundles.core.intern;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.Manifest;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.pax.tinybundles.core.TinyBundle;
import org.ops4j.pax.tinybundles.core.BundleAs;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
public class TinyBundleImpl implements TinyBundle
{

    private static Log LOG = LogFactory.getLog( TinyBundleImpl.class );

    private Map<String, URL> m_resources = new HashMap<String, URL>();

    private Map<String, String> m_headers = new HashMap<String, String>();

    public TinyBundle addClass( Class clazz )
    {
        String name = clazz.getName().replaceAll( "\\.", "/" ) + ".class";
        getClass().getResource( "/" + name );
        addResource( name, getClass().getResource( "/" + name ) );
        return this;
    }

    public TinyBundle addResource( String name, URL url )
    {
        m_resources.put( name, url );
        return this;
    }

    public TinyBundle set( String key, String value )
    {
        m_headers.put( key, value );
        return this;
    }

    public <T> T build( BundleAs<T> type )
    {
        return type.make( m_resources, m_headers );
    }
}
