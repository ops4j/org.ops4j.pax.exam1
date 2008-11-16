package org.ops4j.pax.drone.spi;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.drone.api.DroneException;
import org.ops4j.pax.drone.api.DroneProvider;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 10, 2008
 */
public class PrebuildDroneProvider implements DroneProvider
{

    private String m_bundleUrl;

    public PrebuildDroneProvider( String bundleUrl )
    {
        NullArgumentException.validateNotEmpty( bundleUrl, "bundleUrl" );
        m_bundleUrl = bundleUrl;
    }

    public InputStream build()
    {
        // if use with paxrunner it should be possible to resolve directly from url:
        try
        {
            URL url = new URL( m_bundleUrl );
            return url.openStream();
        } catch( MalformedURLException e )
        {
            throw new DroneException( "Looks the url cannot be resolved (not using paxrunner connector ??: " + m_bundleUrl + ")" );
        } catch( IOException e )
        {
            throw new DroneException( "Looks the url cannot be resolved (Not connected to the internet ?: " + m_bundleUrl + ")" );
        }
    }
}
