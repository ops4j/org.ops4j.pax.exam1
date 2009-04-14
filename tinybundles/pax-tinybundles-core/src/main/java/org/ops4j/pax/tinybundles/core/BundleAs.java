package org.ops4j.pax.tinybundles.core;

import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;
import java.net.URL;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 10, 2009
 */
public interface BundleAs<T>
{
    T make( Map<String, URL> resources, Map<String, String> headers );
}
