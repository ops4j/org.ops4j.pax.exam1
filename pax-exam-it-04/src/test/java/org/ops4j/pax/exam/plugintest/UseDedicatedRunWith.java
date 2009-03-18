package org.ops4j.pax.exam.plugintest;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
@RunWith( MavenConfiguredJUnit4TestRunner.class )
public class UseDedicatedRunWith
{

    @Test
    public void use()
    {
        System.out.println( "Hello World" );
    }
}
