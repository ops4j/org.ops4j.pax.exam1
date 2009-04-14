package org.ops4j.pax.tinybundles.core;

import java.io.InputStream;
import java.net.URL;
import org.ops4j.pax.tinybundles.core.intern.TinyBundleImpl;
import org.ops4j.pax.tinybundles.core.targets.BundleAsStreamImpl;
import org.ops4j.pax.tinybundles.core.targets.BundleAsURLImpl;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
public class TinyBundles
{

    public static TinyBundle newBundle()
    {
        return new TinyBundleImpl();
    }

    public static BundleAs<URL> asURL()
    {
        return new BundleAsURLImpl();
    }

    public static BundleAs<InputStream> asStream()
    {
        return new BundleAsStreamImpl();
    }


}
