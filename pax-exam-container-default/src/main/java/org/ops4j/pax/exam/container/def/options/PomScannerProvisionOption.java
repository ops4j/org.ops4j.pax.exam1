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
     * url. can be null. then parts (groupid,artefactid) must be filled.
     * If url is not null, this will be used. (url has priority over artefact/group)
     */
    private String m_url;

    /**
     * artifactId part of maven style provisioning (will be part of mvn url being constructed)
     */
    private String m_artifact;

    /**
     * groupId part of maven style provisioning (will be part of mvn url being constructed)
     */
    private String m_groupId;

    /**
     * version part of maven style provisioning (will be part of mvn url being constructed)
     */
    private String m_version = "";

    /**
     * Constructor.
     *
     * @param url directory to be scanned path (cannot be null or empty)
     *
     * @throws IllegalArgumentException - If url is null or empty
     */
    public PomScannerProvisionOption( final String url )
    {
        validateNotEmpty( url, true, "url" );
        m_url = url;
    }

    /**
     * Constructor.
     */
    public PomScannerProvisionOption()
    {

    }

    /**
     * {@inheritDoc}
     */
    public String getURL()
    {
        if( m_url == null )
        {
            m_url = "mvn:" + m_groupId + "/" + m_artifact + "/" + m_version + "/pom";
        }
        final StringBuilder url = new StringBuilder().append( SCHEMA ).append( SEPARATOR_SCHEME ).append( m_url );
        url.append( getOptions( this ) );
        return url.toString();
    }

    public PomScannerProvisionOption artifactId( String s )
    {
        m_artifact = s;
        return this;
    }

    public PomScannerProvisionOption groupId( String s )
    {
        m_groupId = s;
        return this;
    }

    public PomScannerProvisionOption version( String s )
    {
        m_version = s;
        return this;
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