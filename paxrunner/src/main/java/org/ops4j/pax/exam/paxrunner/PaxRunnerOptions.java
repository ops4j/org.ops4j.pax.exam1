package org.ops4j.pax.exam.paxrunner;

import org.ops4j.pax.exam.ConfigurationOption;
import org.ops4j.pax.exam.paxrunner.options.ProfileOption;
import org.ops4j.pax.exam.options.CompositeOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 9:00:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaxRunnerOptions
{

    public static ConfigurationOption profile( ProfileOption... profiles )
    {
        return new CompositeOption( profiles );
    }

    public static ProfileOption web()
    {
        return new ProfileOption( "web" );
    }

}
