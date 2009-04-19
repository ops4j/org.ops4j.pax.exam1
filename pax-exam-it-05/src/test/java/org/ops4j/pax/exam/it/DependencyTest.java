package org.ops4j.pax.exam.it;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.Inject;

/**
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Toni Menzel (tonit)
 * @since Mar 26, 2009
 */
@RunWith( org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner.class )
public class DependencyTest
{

    @Inject
    BundleContext context;

    @Test
    public void usePlugin()
    {
        // this is a fragment that should be one of the "api" that come out of PAXEXAM-23.
        // "simple check for bundle states, wait for dependencies to resolve,unresolve" and so on.

        boolean foundAndActive = false;
        for( Bundle b : context.getBundles() )
        {
            if( b.getSymbolicName().equals( "org.ops4j.base.lang" ) )
            {
                if( b.getState() == Bundle.ACTIVE )
                {
                    foundAndActive = true;
                }
            }
        }
        if( !foundAndActive )
        {
            fail( "maven dependent bundle was not found or is not active" );
        }
    }
}
