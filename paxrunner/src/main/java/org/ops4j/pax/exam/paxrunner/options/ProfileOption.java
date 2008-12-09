package org.ops4j.pax.exam.paxrunner.options;

import org.ops4j.pax.exam.ConfigurationOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 9:04:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileOption
    implements ConfigurationOption
{
    private final String m_profile;

    public ProfileOption( String profile )
    {
        m_profile = profile;
    }
}
