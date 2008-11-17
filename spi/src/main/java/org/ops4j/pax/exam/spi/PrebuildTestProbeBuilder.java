package org.ops4j.pax.exam.spi;

import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.TestExecutionException;
import org.ops4j.pax.exam.api.TestProbeBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 10, 2008
 */
public class PrebuildTestProbeBuilder implements TestProbeBuilder
{

    private String m_bundleUrl;

    public PrebuildTestProbeBuilder( String bundleUrl )
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
            throw new TestExecutionException(
                "Looks the url cannot be resolved (not using paxrunner connector ??: " + m_bundleUrl + ")"
            );
        } catch( IOException e )
        {
            throw new TestExecutionException(
                "Looks the url cannot be resolved (Not connected to the internet ?: " + m_bundleUrl + ")"
            );
        }
    }
}