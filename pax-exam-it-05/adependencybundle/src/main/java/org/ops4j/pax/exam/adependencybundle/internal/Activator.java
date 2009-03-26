package org.ops4j.pax.exam.adependencybundle.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 25, 2009
 */
public class Activator implements BundleActivator
{

    public void start( BundleContext bundleContext )
        throws Exception
    {
        System.out.println( "Hello from " + bundleContext.getBundle().getSymbolicName() );
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        System.out.println( "Goodby from " + bundleContext.getBundle().getSymbolicName() );

    }
}
