package org.ops4j.pax.exam.container.def.options;

import org.ops4j.pax.exam.options.AbstractProvisionOption;
import static org.ops4j.pax.exam.container.def.options.ScannerUtils.*;
import static org.ops4j.pax.runner.provision.ServiceConstants.*;
import static org.ops4j.pax.runner.scanner.pom.ServiceConstants.*;
import static org.ops4j.lang.NullArgumentException.*;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 7, 2009
 */
public class PomScannerProvisionOption extends AbstractProvisionOption<PomScannerProvisionOption> implements ScannerProvisionOption<PomScannerProvisionOption>
{

    /**
     * Directory path (cannot be null or empty).
     */
    private final String m_path;

    /**
     * Constructor.
     *
     * @param path directory to be scanned path (cannot be null or empty)
     *
     * @throws IllegalArgumentException - If url is null or empty
     */
    public PomScannerProvisionOption( final String path )
    {
        validateNotEmpty( path, true, "Directory path" );
        m_path = path;
    }

    /**
     * {@inheritDoc}
     */
    public String getURL()
    {
        final StringBuilder url = new StringBuilder().append( SCHEMA ).append( SEPARATOR_SCHEME ).append( m_path );
        url.append( getOptions( this ) );
        return url.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "PomScannerProvisionOption" );
        sb.append( "{url='" ).append( getURL() ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    protected PomScannerProvisionOption itself()
    {
        return this;
    }

}