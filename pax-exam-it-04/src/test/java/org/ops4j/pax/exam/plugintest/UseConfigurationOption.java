package org.ops4j.pax.exam.plugintest;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Ignore;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class UseConfigurationOption
{

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
        System.out.println( "Hello World" );
    }
}

