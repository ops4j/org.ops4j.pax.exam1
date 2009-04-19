package org.ops4j.pax.tinybundles.test;

import org.osgi.framework.Constants;
import org.ops4j.pax.tinybundles.core.TinyBundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;
import org.ops4j.pax.tinybundles.test.configadmin.SampleService;
import org.ops4j.pax.tinybundles.test.configadmin.internal.ConfigAdminSampleActivator;
import org.ops4j.pax.tinybundles.test.configadmin.internal.ManagedServiceSample;
import org.ops4j.pax.tinybundles.test.simpliest.HelloWorldService;
import org.ops4j.pax.tinybundles.test.simpliest.internal.HelloWorldImpl;
import org.ops4j.pax.tinybundles.test.simpliest.internal.SimpliestSampleActivator;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
public class DemoBundles
{

    public static TinyBundle justSymbolicName( String symbolicname )
    {
        return newBundle().set( Constants.BUNDLE_SYMBOLICNAME, symbolicname );
    }

    public static TinyBundle simpliest( String symbolicname )
    {
        return newBundle()
            .set( Constants.BUNDLE_SYMBOLICNAME, symbolicname )
            .set( Constants.EXPORT_PACKAGE, "org.ops4j.pax.tinybundles.test.simpliest" )
            .set( Constants.IMPORT_PACKAGE,
                  "org.ops4j.pax.tinybundles.test.simpliest,org.osgi.framework"
            )
            .set( Constants.BUNDLE_ACTIVATOR,
                  SimpliestSampleActivator.class.getName()
            )
            .addClass( SimpliestSampleActivator.class )
            .addClass( HelloWorldService.class )
            .addClass( HelloWorldImpl.class );
    }

    public static TinyBundle configadmin( String symbolicname )
    {
        return newBundle()
            .set( Constants.BUNDLE_SYMBOLICNAME, symbolicname )
            .set( Constants.EXPORT_PACKAGE, "org.ops4j.pax.tinybundles.test.configadmin" )
            .set( Constants.IMPORT_PACKAGE,
                  "org.ops4j.pax.tinybundles.test.configadmin,org.osgi.framework,org.osgi.service.cm,org.apache.commons.logging"
            )
            .set( Constants.BUNDLE_ACTIVATOR, ConfigAdminSampleActivator.class.getName() )
            .addClass( ConfigAdminSampleActivator.class )
            .addClass( SampleService.class )
            .addClass( ManagedServiceSample.class )
            ;
    }
}
