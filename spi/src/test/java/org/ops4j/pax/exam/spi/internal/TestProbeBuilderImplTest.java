package org.ops4j.pax.exam.spi.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.notNull;
import org.junit.Test;
import org.ops4j.pax.exam.api.TestProbeBuilder;
import org.ops4j.pax.exam.spi.ResourceLocator;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarOutputStream;

/**
 * User: Toni Menzel (tonit)
 * Date: Jun 24, 2008
 */
public class TestProbeBuilderImplTest
{

    @Test( expected = IllegalArgumentException.class )
    public void failedConstruction1()
    {
        new BndTestProbeBuilder( null, null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void failedConstruction2()
    {
        new BndTestProbeBuilder( this.getClass().getName(), "", null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void failedConstruction3()
    {
        ResourceLocator locator = createMock( ResourceLocator.class );
        new BndTestProbeBuilder( null, null, locator );
    }

    @Test( expected = IllegalArgumentException.class )
    public void buildingWithWrongResource()
        throws IOException
    {
        ResourceLocator locator = new IntelliResourceLocator( new File( "." ), "foo" );

        locator.write( ( JarOutputStream ) notNull() );
        TestProbeBuilder builder = new BndTestProbeBuilder( this.getClass().getName(), null, locator );
        builder.build();
    }


}
