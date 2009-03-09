/*
 * Copyright 2008 Alin Dreghiciu.
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
package org.ops4j.pax.exam.options;

import static org.ops4j.lang.NullArgumentException.*;

/**
 * Option specifying provisioning from an maven url (Pax URL mvn: handler).
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 08, 2008
 */
public class MavenUrlProvisionOption
    extends AbstractProvisionOption<MavenUrlProvisionOption>
{

    /**
     * Artifact group id (cannot be null or empty).
     */
    private String m_groupId;
    /**
     * Artifact id  (cannot be null or empty).
     */
    private String m_artifactId;
    /**
     * Artifact type (can be null case when the default type is used = jar).
     */
    private String m_type;
    /**
     * Artifact version/version range (can be null case when latest version will be used).
     */
    private String m_version;
    /**
     * True if the user used update method.
     */
    private boolean m_updateUsed;

    /**
     * Sets the artifact group id.
     *
     * @param groupId artifact group id (cannot be null or empty)
     *
     * @return itself, for fluent api usage
     *
     * @throws IllegalArgumentException - If group id is null or empty
     */
    public MavenUrlProvisionOption groupId( final String groupId )
    {
        validateNotEmpty( groupId, true, "Group" );
        m_groupId = groupId;
        m_updateUsed = false;
        return this;
    }

    /**
     * Sets the artifact id.
     *
     * @param artifactId artifact id (cannot be null or empty)
     *
     * @return itself, for fluent api usage
     *
     * @throws IllegalArgumentException - If artifact id is null or empty
     */
    public MavenUrlProvisionOption artifactId( final String artifactId )
    {
        validateNotEmpty( artifactId, true, "Artifact" );
        m_artifactId = artifactId;
        return this;
    }

    /**
     * Sets the artifact type. Do not set the value (use this method) if default artifact type should be used.
     *
     * @param type artifact type (cannot be null or empty)
     *
     * @return itself, for fluent api usage
     *
     * @throws IllegalArgumentException - If type is null or empty
     */
    public MavenUrlProvisionOption type( final String type )
    {
        validateNotEmpty( type, true, "type" );
        m_type = type;
        return this;
    }

    /**
     * Sets the artifact version or version range. If version is a SNAPSHOT version the bundle will be set to updatable,
     * otherwise the bundle will not be updated. This handling happens only if the user dows not use the update() by
     * itself (see {@link org.ops4j.pax.exam.options.ProvisionOption#update(boolean)}).
     *
     * @param version artifact version / version range (cannot be null or empty)
     *
     * @return itself, for fluent api usage
     *
     * @throws IllegalArgumentException - If version is null or empty
     */
    public MavenUrlProvisionOption version( final String version )
    {
        validateNotEmpty( version, true, "Version" );
        m_version = version;
        if( !m_updateUsed )
        {
            update( m_version.endsWith( "SNAPSHOT" ) );
        }
        return this;
    }

    /**
     * Determines the artifact version using an {@link VersionResolver}.
     *
     * @param resolver a {@link VersionResolver} (cannot be null)
     *
     * @return itself, for fluent api usage
     *
     * @throws IllegalArgumentException - If version is null
     */
    public MavenUrlProvisionOption version( final VersionResolver resolver )
    {
        validateNotNull( resolver, "Version resolver" );
        return version( resolver.getVersion( m_groupId, m_artifactId ) );
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException - If group id is null or empty
     *                                  - If artifact id is null or empty
     */
    public String getURL()
    {
        validateNotEmpty( m_groupId, true, "Group" );
        validateNotEmpty( m_artifactId, true, "Artifact" );
        final StringBuilder url = new StringBuilder();
        url.append( "mvn:" ).append( m_groupId ).append( "/" ).append( m_artifactId );
        if( m_version != null )
        {
            url.append( "/" ).append( m_version );
        }
        return url.toString();
    }

    /**
     * {@inheritDoc}
     * Keep track if the user used the update method, so we do not override the value when handling automatic update on
     * SNAPSHOT versions.
     */
    @Override
    public MavenUrlProvisionOption update( boolean shouldUpdate )
    {
        m_updateUsed = true;
        return super.update( shouldUpdate );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "MavenUrlProvisionOption" );
        sb.append( "{groupId='" ).append( m_groupId ).append( '\'' );
        sb.append( ", artifactId='" ).append( m_artifactId ).append( '\'' );
        sb.append( ", version='" ).append( m_version ).append( '\'' );
        sb.append( ", type='" ).append( m_type ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    protected MavenUrlProvisionOption itself()
    {
        return this;
    }

    /**
     * Resolves versions based on maven artifact groupId / atifactid.
     *
     * @author Alin Dreghiciu (adreghiciu@gmail.com)
     * @since 0.3.1, March 09, 2009
     */
    public static interface VersionResolver
    {

        String getVersion( String groupId, String artifactId );

    }

}