/*
 * Copyright 2008 Toni Menzel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.container.def.options;

import static org.ops4j.lang.NullArgumentException.*;

/**
 * The option specifiying pax runners repository option.
 *
 * @author Toni Menzel (tonit)
 * @since Dec 18, 2008
 */
public class RepositoryOptionImpl implements RepositoryOption
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

    public RepositoryOptionImpl( String repositoryOption )
    {
        validateNotEmpty( repositoryOption, "repository" );
        m_repository = repositoryOption;
    }

    public RepositoryOptionImpl allowSnapshots()
    {
        allowSnapshots = true;
        return this;
    }

    public RepositoryOptionImpl disableReleases()
    {
        allowReleases = false;
        return this;
    }

    /**
     * @return the full repository as given plus eventual snapshot/release tags.
     */
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
        sb.append( "RepositoryOptionImpl {" );

        sb.append( getRepositoryAsOption() );

        sb.append( '}' );
        return sb.toString();
    }
}
