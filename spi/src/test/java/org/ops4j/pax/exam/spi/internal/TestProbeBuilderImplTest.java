package org.ops4j.pax.exam.spi.internal;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.ops4j.pax.exam.api.TestProbeProvider;
import org.ops4j.pax.exam.spi.ResourceLocator;
import org.ops4j.pax.exam.spi.internal.TestProbeBuilderImpl;
import org.ops4j.pax.exam.spi.internal.IntelliResourceFinder;

/**
 * User: Toni Menzel (tonit)
 * Date: Jun 24, 2008
 */
public class TestProbeBuilderImplTest
{

    @Test( expected = IllegalArgumentException.class )
    public void failedConstruction1()
    {
       new TestProbeBuilderImpl( null, null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void failedConstruction2()
    {
        new TestProbeBuilderImpl( "", this.getClass().getName(), null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void failedConstruction3()
    {
        ResourceLocator locator = createMock( ResourceLocator.class );
        new TestProbeBuilderImpl( null, null, locator );
    }

    @Test( expected = IllegalArgumentException.class )
    public void buildingWithWrongResource()
        throws IOException
    {
        ResourceLocator locator = new IntelliResourceFinder( new File( "." ), "foo" );

        locator.write( (JarOutputStream) notNull() );
        TestProbeProvider provider = new TestProbeBuilderImpl( null, this.getClass().getName(), locator );
        provider.build();
    }

   

}
