package org.ops4j.pax.exam.spi;

import org.junit.Test;
import org.ops4j.pax.exam.spi.OnDemandDroneProvider;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class OnDemandDroneProviderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testInstance()
    {
        new OnDemandDroneProvider( null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance2()
    {
        new OnDemandDroneProvider( "foo", null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance3()
    {
        new OnDemandDroneProvider( null, "bar" );
    }

    // just make sure that this is being accepted
    @Test
    public void testInstance4()
    {
        new OnDemandDroneProvider( "foo", "bar" );
    }
}
