package org.ops4j.pax.exam.it;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import org.junit.Test;
import org.ops4j.pax.exam.api.DroneException;
import org.ops4j.pax.exam.spi.internal.IntelliResourceFinder;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 28, 2008
 */
public class PoisonManifestTest
{
    @Test( expected = DroneException.class )
    public void testForSelfishManifest()
        throws IOException
    {
        IntelliResourceFinder finder = new IntelliResourceFinder( new File( "." ), this.getClass().getName() );
        finder.write( new JarOutputStream( new OutputStream()
        {

            public void write( int i )
                throws IOException
            {
                // nothing
            }
        }
        )
        );
    }
}
