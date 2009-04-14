package org.ops4j.pax.tinybundles.core;

import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.jar.Manifest;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
public interface TinyBundle
{

    TinyBundle addResource( String name, URL url );

    TinyBundle set( String key, String value );

    TinyBundle addClass( Class clazz );

    <T> T build( BundleAs<T> type );
}
