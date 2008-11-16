package org.ops4j.pax.drone.spi;

import org.junit.Test;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class PrebuildDroneProviderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testInstance()
    {
        new PrebuildDroneProvider( null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance2()
    {
        new PrebuildDroneProvider( "" );
    }
}
