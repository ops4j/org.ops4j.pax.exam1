package org.ops4j.pax.exam.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.SetConfiguration;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Toni Menzel (tonit)
 * @since Jan 8, 2009
 */
@RunWith(JUnit4TestRunner.class)
public class ContextAnnotationTest {

    @Configuration
    public static Option[] configureWithUI() {
        return options(
                webProfile()
        );
    }

    @Configuration
    public static Option[] configureHeadless() {
        return options(
                
        );
    }

    @Test
    public void defaultBundles(final BundleContext bundleContext) {
        assertEquals(12, bundleContext.getBundles().length);
    }

    @Test
    @SetConfiguration(".*Headless.*")
    public void webTest(final BundleContext bundleContext) {
        assertEquals(9, bundleContext.getBundles().length);
    }

}

