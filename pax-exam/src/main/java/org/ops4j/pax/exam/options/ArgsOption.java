package org.ops4j.pax.exam.options;

import java.net.URL;
import org.ops4j.pax.exam.Option;
import static org.ops4j.lang.NullArgumentException.*;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public class ArgsOption implements Option
{

    private URL m_url;

    public ArgsOption( URL url )

    {
        validateNotNull( url, "url" );
        m_url = url;
    }

    public URL getURL()
    {
        return m_url;
    }

}
