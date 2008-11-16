package org.ops4j.pax.drone.spi.intern;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;
import org.ops4j.pax.exam.api.RunnerContext;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 29, 2008
 */
public class RunnerContextImplTest
{

    @Test
    public void testFindsValidWorkingDirectory()
    {
        RunnerContext ctx = new RunnerContextImpl();
        File f = ctx.getWorkingDirectory();
        assertNotNull( f );
        assertTrue( f.exists() );
        assertTrue( f.isDirectory() );
    }

    @Test
    public void testFindsValidPort()
    {
        RunnerContext ctx = new RunnerContextImpl();
        int p = ctx.getCommunicationPort();
        assertTrue( p > 1 );
        assertTrue( p < 9999 );
    }
}
