package org.ops4j.pax.exam.plugintest;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
@RunWith( org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner.class )
public class SimpleTest
{

    @Test
    @Ignore
    public void use()
    {
        System.out.println( "Hello World" );
    }
}
