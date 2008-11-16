package org.ops4j.pax.drone.spi;

import java.io.InputStream;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.DroneProvider;
import org.ops4j.pax.drone.spi.intern.DroneBuilderImpl;
import org.ops4j.pax.drone.spi.intern.IntelliResourceFinder;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 10, 2008
 */
public class OnDemandDroneProvider implements DroneProvider
{

    private String m_host;
    private String m_recipe;

    public OnDemandDroneProvider( String recipe, String host )
    {
        NullArgumentException.validateNotEmpty( recipe, "recipe" );
        NullArgumentException.validateNotEmpty( host, "host" );

        m_host = host;
        m_recipe = recipe;
    }

    public InputStream build()
    {
        return new DroneBuilderImpl( m_recipe, m_host, new IntelliResourceFinder( m_host ) ).build();
    }
}
