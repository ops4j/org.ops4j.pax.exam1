package org.ops4j.pax.exam.it;

import org.junit.runner.RunWith;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 3, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class TraceKnopflerfish extends DefaultTrace
{

    @Configuration
    public static Option[] configure()
    {
        return options(
            logProfile(),
            systemProperty( "org.ops4j.pax.logging.DefaultServiceLog.level" ).value( "DEBUG" ),
            knopflerfish()
        );
    }
}
