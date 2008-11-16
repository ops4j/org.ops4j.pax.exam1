package org.ops4j.pax.exam.it;

import org.junit.Test;
import org.ops4j.pax.exam.api.TestExecutionException;
import org.ops4j.pax.exam.spi.internal.IntelliResourceLocator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 28, 2008
 */
public class PoisonManifestTest
{

    @Test( expected = TestExecutionException.class )
    public void testForSelfishManifest()
        throws IOException
    {
        IntelliResourceLocator locator = new IntelliResourceLocator( new File( "." ), this.getClass().getName() );
        locator.write( new JarOutputStream( new OutputStream()
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
