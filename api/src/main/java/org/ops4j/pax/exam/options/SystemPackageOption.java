package org.ops4j.pax.exam.options;

import org.ops4j.pax.exam.ConfigurationOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:27:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemPackageOption
    implements ConfigurationOption
{

    private final String m_package;

    public SystemPackageOption( String pkg )
    {
        m_package = pkg;
    }

}