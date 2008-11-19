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
package org.ops4j.pax.exam.connector.paxrunner.internal;

import org.ops4j.pax.exam.connector.paxrunner.PaxRunnerBundleProvision;
import org.ops4j.pax.exam.spi.internal.BundleProvisionImpl;

/**
 * @author Toni Menzel (tonit)
 * @since Nov 11, 2008
 */
public class PaxRunnerBundleProvisionImpl extends BundleProvisionImpl implements PaxRunnerBundleProvision
{

    // default scanner used.
    public static final String SCANNER_PREFIX = "scan-bundle:";

    private boolean update = true;

    private boolean prefix = true;

    public PaxRunnerBundleProvision addBundle( String bundleUrl )
    {
        return (PaxRunnerBundleProvision) super.addBundle( bundleUrl );
    }

    public PaxRunnerBundleProvision addBundle( String[] bundleUrls )
    {
        return (PaxRunnerBundleProvision) super.addBundle( bundleUrls );
    }

    public PaxRunnerBundleProvision updateBundles( boolean doUpdate )
    {
        update = doUpdate;
        return this;
    }

    public PaxRunnerBundleProvision prefixScanDirective( boolean doPrefix )
    {
        prefix = doPrefix;
        return this;
    }

    public String[] getBundles()
    {

        String[] arr = super.getBundles();

        // enhance update
        for( int i = 0; i < arr.length; i++ )
        {
            String b = arr[ i ];
            if( update && ( b.indexOf( "@update" ) < 0 ) )
            {
                arr[ i ] = b + "@update";
            }
            if( prefix )
            {
                arr[ i ] = SCANNER_PREFIX + arr[ i ];
            }
        }
        return arr;
    }
}
