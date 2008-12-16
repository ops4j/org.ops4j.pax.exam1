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
    implements ProvisionOption
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
     * Sets the artifact group id.
     *
     * @param groupId artifact group id (cannot be null or empty)
     *
     * @return itself, for fluent api usage
     *
     * @throws IllegalArgumentException - If group id is null or empty
     */
    public MavenUrlProvisionOption group( final String groupId )
    {
        validateNotEmpty( groupId, true, "Group" );
        m_groupId = groupId;
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
    public MavenUrlProvisionOption artifact( final String artifactId )
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
     * Sets the artifact version or version range.
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
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException - If group id is null or empty
     * @throws IllegalArgumentException - If artifact id is null or empty
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

}