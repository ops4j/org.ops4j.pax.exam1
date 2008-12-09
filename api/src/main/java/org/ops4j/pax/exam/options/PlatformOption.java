package org.ops4j.pax.exam.options;

import org.ops4j.pax.exam.ConfigurationOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:36:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlatformOption
    implements ConfigurationOption
{

    private final String m_name;
    private String m_version;

    public PlatformOption( String name )
    {
        m_name = name;
    }

    public PlatformOption version( String version )
    {
        m_version = version;
        return this;
    }
}
