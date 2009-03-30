package org.ops4j.pax.exam.tutorial1;

import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner;
import org.ops4j.pax.exam.Inject;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 30, 2009
 */
@RunWith( MavenConfiguredJUnit4TestRunner.class )
public class T1S6_MavenConfigured
{

    @Inject
    BundleContext context;

    @Test
    public void testMe()
    {
        assertNotNull( context );
         System.out.println( "printing bundle states.." );
        for( Bundle b : context.getBundles() )
        {

            System.out.println( "Bundle " + b.getBundleId() + ":" + b.getSymbolicName() + " is " + b.getState() );
        }
    }
}
