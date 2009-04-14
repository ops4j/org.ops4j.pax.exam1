package org.ops4j.pax.demo.standalone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.osgi.framework.Constants;
import org.ops4j.io.StreamUtils;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;
import org.ops4j.pax.tinybundles.demo.HelloWorld;
import org.ops4j.pax.tinybundles.demo.intern.HelloWorldImpl;
import org.ops4j.pax.tinybundles.demo.intern.MyFirstActivator;

/**
 * This is a standalone test.
 * Practically you define your bundle in java api and finally will get the bundle written to disk.
 *
 * This is mainly for demonstrative purposes.
 * Real value comes when using tinybundles together with a pax exam test.
 *
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
public class StandaloneTest
{

    @Test
    public void myFirstTinyBundle()
        throws IOException
    {

        toDisk( newBundle()
            .set( Constants.BUNDLE_SYMBOLICNAME, "MyFirstTinyBundle" )
            .set( Constants.EXPORT_PACKAGE, "org.ops4j.pax.tinybundles.demo" )
            .set( Constants.IMPORT_PACKAGE, "org.ops4j.pax.tinybundles.demo,org.osgi.framework" )
            .set( Constants.BUNDLE_ACTIVATOR, MyFirstActivator.class.getName() )
            .addClass( MyFirstActivator.class )
            .addClass( HelloWorld.class )
            .addClass( HelloWorldImpl.class )
            .build( asStream() ), new File( "MyFirstBundle.jar" )
        );

    }

    private void toDisk( InputStream inputStream, File file )
        throws IOException
    {
        FileOutputStream fout = new FileOutputStream( file );
        try
        {
            StreamUtils.copyStream( inputStream, fout, false );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // inputStream.close();
                fout.close();

            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }

    }
}
