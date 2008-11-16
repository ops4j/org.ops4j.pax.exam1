package org.ops4j.pax.exam.spi;

import org.junit.Test;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class PrebuildTestProbeBuilderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testInstance()
    {
        new PrebuildTestProbeBuilder( null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance2()
    {
        new PrebuildTestProbeBuilder( "" );
    }
}
