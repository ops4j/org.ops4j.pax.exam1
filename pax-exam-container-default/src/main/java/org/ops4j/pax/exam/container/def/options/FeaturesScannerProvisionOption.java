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
package org.ops4j.pax.exam.container.def.options;

import static org.ops4j.pax.exam.container.def.options.ScannerUtils.*;
import org.ops4j.pax.exam.options.AbstractProvisionWrapperOption;
import org.ops4j.pax.exam.options.ProvisionOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;
import static org.ops4j.pax.runner.provision.ServiceConstants.*;
import static org.ops4j.pax.runner.scanner.features.ServiceConstants.*;

/**
 * Option specifying provision from an Pax Runner Features scanner.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.5.0, April 19, 2008
 */
public class FeaturesScannerProvisionOption
    extends AbstractProvisionWrapperOption<FeaturesScannerProvisionOption>
    implements ScannerProvisionOption<FeaturesScannerProvisionOption>
{

    private String[] m_features;

    /**
     * Constructor.
     *
     * @param repositoryUrl url of features respository to be scanned (cannot be null or empty)
     * @param features      features to be scanned
     *
     * @throws IllegalArgumentException - If url is null or empty
     */
    public FeaturesScannerProvisionOption( final String repositoryUrl,
                                           final String... features )
    {
        super( new UrlProvisionOption( repositoryUrl ) );
        m_features = features;
    }

    /**
     * Constructor.
     *
     * @param repositoryUrl url of features respository to be scanned (cannot be null)
     * @param features      features to be scanned
     *
     * @throws IllegalArgumentException - If url is null
     */
    public FeaturesScannerProvisionOption( final ProvisionOption repositoryUrl,
                                           final String... features )
    {
        super( repositoryUrl );
        m_features = features;
    }

    /**
     * {@inheritDoc}
     */
    public String getURL()
    {
        final StringBuilder url = new StringBuilder()
            .append( SCHEMA )
            .append( SEPARATOR_SCHEME )
            .append( super.getURL() )
            .append( SEPARATOR_FILTER );
        boolean first = true;
        for( String feature : m_features )
        {
            if( !first )
            {
                url.append( FEATURE_SEPARATOR );
            }
            first = false;
            url.append( feature );
        }
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
        sb.append( FeaturesScannerProvisionOption.class.getSimpleName() );
        sb.append( "{url='" ).append( getURL() ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    protected FeaturesScannerProvisionOption itself()
    {
        return this;
    }

}