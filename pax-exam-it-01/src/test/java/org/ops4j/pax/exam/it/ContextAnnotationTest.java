package org.ops4j.pax.exam.it;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.Context;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Toni Menzel (tonit)
 * @since Jan 8, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class ContextAnnotationTest
{

    @Configuration
    public static Option[] configureToUse()
    {
        return options(

        );
    }

    @Test
    public void defaultBundles( final BundleContext bundleContext )
    {
        assertEquals( 9, bundleContext.getBundles().length );
    }

    @Configuration
    @Context( "web" )
    public static Option[] configForWeb()
    {
        return options(
            webProfile()
        );
    }

    @Test
    @Context( "web" )
    public void webTest( final BundleContext bundleContext )
    {
        assertEquals( 12, bundleContext.getBundles().length );
    }

}

