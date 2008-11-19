package org.ops4j.pax.exam.it;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import org.junit.Test;
import org.ops4j.pax.exam.api.TestExecutionException;
import org.ops4j.pax.exam.spi.internal.IntelliResourceLocator;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 28, 2008
 */
public class PoisonManifest
{

    // TODO re-enable thsi test (see bellow)
    // for this test to fail it needs a file in src/main/resources/META-INF/MANIFEST.MF with any context as for example POISEN
    // for the moment is disabled as it interfeers with teh other tests.
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
