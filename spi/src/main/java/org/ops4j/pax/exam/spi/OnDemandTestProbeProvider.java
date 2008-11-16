package org.ops4j.pax.exam.spi;

import java.io.InputStream;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.TestProbeProvider;
import org.ops4j.pax.exam.spi.internal.TestProbeBuilderImpl;
import org.ops4j.pax.exam.spi.internal.IntelliResourceFinder;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 10, 2008
 */
public class OnDemandTestProbeProvider implements TestProbeProvider
{

    private String m_host;
    private String m_recipe;

    public OnDemandTestProbeProvider( String recipe, String host )
    {
        NullArgumentException.validateNotEmpty( recipe, "recipe" );
        NullArgumentException.validateNotEmpty( host, "host" );

        m_host = host;
        m_recipe = recipe;
    }

    public InputStream build()
    {
        return new TestProbeBuilderImpl( m_recipe, m_host, new IntelliResourceFinder( m_host ) ).build();
    }
}
