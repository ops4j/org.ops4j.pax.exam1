package org.ops4j.pax.exam.spi;

import org.junit.Test;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class OnDemandTestProbeBuilderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testInstance()
    {
        new OnDemandTestProbeBuilder( null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance2()
    {
        new OnDemandTestProbeBuilder( "foo", null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance3()
    {
        new OnDemandTestProbeBuilder( null, "bar" );
    }

    // just make sure that this is being accepted
    @Test
    public void testInstance4()
    {
        new OnDemandTestProbeBuilder( "foo", "bar" );
    }
}
