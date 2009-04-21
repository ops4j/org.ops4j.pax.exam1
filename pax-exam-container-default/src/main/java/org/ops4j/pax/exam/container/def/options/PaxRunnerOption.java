package org.ops4j.pax.exam.container.def.options;

import org.ops4j.pax.exam.Option;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 21, 2009
 */
public class PaxRunnerOption implements Option
{

    private String m_value;
    private String m_key;

    public PaxRunnerOption( String key, String value )
    {
        m_key = key;
        m_value = value;
    }

    public String getKey()
    {
        return m_key;
    }

    public String getValue()
    {
        return m_value;
    }
}
