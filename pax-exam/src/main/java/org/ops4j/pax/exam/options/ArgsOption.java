package org.ops4j.pax.exam.options;

import java.net.URL;
import org.ops4j.pax.exam.Option;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public class ArgsOption implements Option
{
    private URL m_url;

    public ArgsOption( URL url )

    {
        m_url = url;
    }

    public URL getURL()
    {
        return m_url;
    }

}
