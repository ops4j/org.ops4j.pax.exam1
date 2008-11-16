package org.ops4j.pax.exam.recipe.propertycheck;

import org.osgi.framework.BundleContext;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 13, 2008
 */
public class PropertyCheck
{

    public void run( BundleContext bundleContext )
    {
        System.out.println( "-----> Here we are!" );
    }
}
