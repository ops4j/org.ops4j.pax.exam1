package org.ops4j.pax.demo.standalone;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import org.junit.Test;
import org.osgi.framework.Constants;
import org.ops4j.pax.tinybundles.demo.intern.MyFirstActivator;
import org.ops4j.pax.tinybundles.demo.intern.HelloWorldImpl;
import org.ops4j.pax.tinybundles.demo.HelloWorld;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;
import org.ops4j.io.StreamUtils;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 20, 2009
 */
public class BndTest
{

    @Test
    public void use()
        throws IOException
    {
        newBundle()
            .set( Constants.BUNDLE_SYMBOLICNAME, "MyFirstTinyBundle" )
            .set( Constants.EXPORT_PACKAGE, "org.ops4j.pax.tinybundles.demo" )
            .set( Constants.IMPORT_PACKAGE, "org.ops4j.pax.tinybundles.demo,org.osgi.framework" )
            .set( Constants.BUNDLE_ACTIVATOR, MyFirstActivator.class.getName() )
            .addClass( MyFirstActivator.class )
            .addClass( HelloWorld.class )
            .addClass( HelloWorldImpl.class )
            .build( asFile( new File( "MyFirstBundle.jar" ) ) );

    }
}
