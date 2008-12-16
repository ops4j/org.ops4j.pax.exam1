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
package org.ops4j.pax.exam.junit.options;

import org.ops4j.pax.exam.options.MavenUrlProvisionOption;
import org.ops4j.pax.exam.options.ProvisionOption;

/**
 * Option specifying Easymock bundles (osgi-fyed easymock).
 * By default uses easymock bundle published by SpringSource, version 2.3.0 (can be changed).
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 09, 2008
 */
public class EasyMockBundlesOption
    implements ProvisionOption
{

    /**
     * Easymock bundle maven provision url.
     */
    private final MavenUrlProvisionOption m_url;

    /**
     * Constructor.
     */
    public EasyMockBundlesOption()
    {
        m_url = new MavenUrlProvisionOption()
            .group( "org.easymock" )
            .artifact( "com.springsource.org.easymock" )
            .version( "2.3.0" );
    }

    /**
     * Sets the easymock version.
     *
     * @param version junit version.
     *
     * @return itself, for fluent api usage
     */
    public EasyMockBundlesOption version( final String version )
    {
        m_url.version( version );
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public String getURL()
    {
        return m_url.getURL();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "EasyMockBundlesOption" );
        sb.append( "{url=" ).append( m_url );
        sb.append( '}' );
        return sb.toString();
    }

}
