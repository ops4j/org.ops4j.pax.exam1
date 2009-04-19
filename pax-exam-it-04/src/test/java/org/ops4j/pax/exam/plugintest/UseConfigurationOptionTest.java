package org.ops4j.pax.exam.plugintest;

import org.junit.runner.RunWith;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class UseConfigurationOptionTest
    extends MavenPluginTestCases
{

    @Configuration
    public Option[] configure()
    {
        return options(
            mavenConfiguration()
        );
    }

}

