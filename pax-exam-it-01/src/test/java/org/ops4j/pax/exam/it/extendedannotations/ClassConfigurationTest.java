package org.ops4j.pax.exam.it.extendedannotations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.it.extendedannotations.MasterProfile;
import org.ops4j.pax.exam.it.extendedannotations.SubProfile;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 6, 2009
 */
@RunWith( JUnit4TestRunner.class )
@Configuration( extend = MasterProfile.class )
public class ClassConfigurationTest
{

    @Configuration( extend = SubProfile.class )
    public Option[] configure()
    {
        return new Option[]{ logProfile() };
    }

    @Test
    public void use()
    {
        
    }
}
