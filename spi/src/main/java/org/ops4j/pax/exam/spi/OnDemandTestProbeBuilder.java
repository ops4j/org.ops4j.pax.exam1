package org.ops4j.pax.exam.spi;

import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.TestProbeBuilder;
import org.ops4j.pax.exam.spi.internal.BndTestProbeBuilder;
import org.ops4j.pax.exam.spi.internal.IntelliResourceLocator;

import java.io.InputStream;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 10, 2008
 */
public class OnDemandTestProbeBuilder implements TestProbeBuilder
{

    private String m_host;
    private String m_recipe;

    public OnDemandTestProbeBuilder( String recipe, String host )
    {
        NullArgumentException.validateNotEmpty( recipe, "recipe" );
        NullArgumentException.validateNotEmpty( host, "host" );

        m_host = host;
        m_recipe = recipe;
    }

    public InputStream build()
    {
        return new BndTestProbeBuilder( m_host, m_recipe, new IntelliResourceLocator( m_host ) ).build();
    }
}
