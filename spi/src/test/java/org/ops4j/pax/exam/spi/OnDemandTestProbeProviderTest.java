package org.ops4j.pax.exam.spi;

import org.junit.Test;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class OnDemandTestProbeProviderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testInstance()
    {
        new OnDemandTestProbeProvider( null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance2()
    {
        new OnDemandTestProbeProvider( "foo", null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance3()
    {
        new OnDemandTestProbeProvider( null, "bar" );
    }

    // just make sure that this is being accepted
    @Test
    public void testInstance4()
    {
        new OnDemandTestProbeProvider( "foo", "bar" );
    }
}
