package org.ops4j.pax.exam.spi;

import org.junit.Test;
import org.ops4j.pax.exam.spi.PrebuildTestProbeProvider;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class PrebuildTestProbeProviderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void testInstance()
    {
        new PrebuildTestProbeProvider( null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testInstance2()
    {
        new PrebuildTestProbeProvider( "" );
    }
}
