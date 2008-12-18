package org.ops4j.pax.exam.container.def.options;

import org.ops4j.pax.exam.Option;
import static org.ops4j.lang.NullArgumentException.*;

/**
 * @author Toni Menzel (tonit)
 * @since Dec 18, 2008
 */
public class RepositoriesOption implements Option
{

    /**
     * \@snapshots flag of repositories entry
     */
    private boolean allowSnapshots = false;

    /**
     * \@noreleases flag of repositories entry
     */
    private boolean allowReleases = true;

    private String m_repository;

    public RepositoriesOption( String repositoryOption )
    {
        validateNotEmpty( repositoryOption, "repository" );
        m_repository = repositoryOption;
    }

    public RepositoriesOption allowSnapshots()
    {
        allowSnapshots = true;
        return this;
    }

    public RepositoriesOption disableReleases()
    {
        allowReleases = false;
        return this;
    }

    public String getRepositoryAsOption()
    {

        if( !allowReleases && !allowSnapshots )
        {
            throw new IllegalArgumentException( "Does not make sense to disallow both releases and snapshots." );
        }

        String option = m_repository;

        if( allowSnapshots )
        {
            option += "@snapshots";
        }

        if( !allowReleases )
        {
            option += "@noreleases";
        }

        return option;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "RepositoriesOption {" );

        sb.append( getRepositoryAsOption() );

        sb.append( '}' );
        return sb.toString();
    }
}
