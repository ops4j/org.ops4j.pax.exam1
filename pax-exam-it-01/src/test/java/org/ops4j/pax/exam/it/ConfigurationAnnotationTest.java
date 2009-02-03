package org.ops4j.pax.exam.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.RequiresConfiguration;
import org.ops4j.pax.exam.junit.AppliesTo;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.SystemPropertyOption;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Toni Menzel (tonit)
 * @since Jan 8, 2009
 */
@RunWith(JUnit4TestRunner.class)
public class ConfigurationAnnotationTest {

    @Configuration
    public static Option[] standardConfig() {
        return options(
                systemProperties(new SystemPropertyOption("standardConfig").value("true"))
        );
    }

    @Configuration
    public static Option[] extraConfig() {
        return options(
                systemProperties(new SystemPropertyOption("extraConfig").value("true"))
        );
    }

    @Configuration
    @AppliesTo({".*test3.*", "test4"})
    public static Option[] loggingConfig() {
        return options(
                systemProperties(new SystemPropertyOption("loggingConfig").value("true"))
        );
    }

    @Test
    public void test1(final BundleContext bundleContext) {
        assertEquals("true", bundleContext.getProperty("standardConfig"));
        assertEquals("true", bundleContext.getProperty("extraConfig"));
        assertNull(bundleContext.getProperty("loggingConfig"));
    }

    @Test
    @RequiresConfiguration(".*extraConfig.*")
    public void test2(final BundleContext bundleContext) {
        assertEquals("true", bundleContext.getProperty("extraConfig"));
        assertNull(bundleContext.getProperty("loggingConfig"));
        assertNull(bundleContext.getProperty("standardConfig"));
    }

    @Test
    public void test4(final BundleContext bundleContext) {
        assertEquals("true", bundleContext.getProperty("standardConfig"));
        assertEquals("true", bundleContext.getProperty("extraConfig"));
        assertEquals("true", bundleContext.getProperty("loggingConfig"));
    }

    /*
    * @Note Toni, Feb, 03, 2009
    * What we have here currently is a merge:
    * It gets extraConfig because of RequiresConfiguration and Logging because of AppliesTo.
    */

    @Test
    @RequiresConfiguration(".*extraConfig.*")
    public void test3(final BundleContext bundleContext) {
        assertNull(bundleContext.getProperty("standardConfig"));
        assertEquals("true", bundleContext.getProperty("extraConfig"));
        assertEquals("true", bundleContext.getProperty("loggingConfig"));
    }

}

