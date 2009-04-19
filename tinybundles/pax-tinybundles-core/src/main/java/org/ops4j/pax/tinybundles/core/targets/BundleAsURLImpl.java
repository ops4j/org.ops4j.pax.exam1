package org.ops4j.pax.tinybundles.core.targets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import org.ops4j.io.StreamUtils;
import org.ops4j.pax.tinybundles.core.BundleAs;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
public class BundleAsURLImpl implements BundleAs<URL>
{

    public URL make( final Map<String, URL> resources, final Map<String, String> headers )
    {
        try
        {
            // TODO use url handler instead

            File fout = File.createTempFile( "tinybundle_", ".jar" );
            fout.deleteOnExit();
            StreamUtils.copyStream( new BundleAsStreamImpl().make( resources, headers ),
                                    new FileOutputStream( fout ), true
            );
            return fout.toURI().toURL();
        }
        catch( IOException e )
        {
            throw new RuntimeException( e );
        }
    }
}
