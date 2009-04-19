package org.ops4j.pax.exam.plugintest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class UseConfigurationOption
{

    @Inject
    BundleContext context;

    @Configuration
    public Option[] configure()
    {
        return options(
            mavenConfiguration()
        );
    }

    @Test
    public void use()
    {
        System.out.println( "Hello World from " + context.getBundle().getSymbolicName() );
    }
}

