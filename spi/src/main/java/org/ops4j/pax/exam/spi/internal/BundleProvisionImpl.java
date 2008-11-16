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
package org.ops4j.pax.exam.spi.internal;

import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.BundleProvision;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 23, 2008
 */
public class BundleProvisionImpl implements BundleProvision
{

    private Set<String> m_bundles;

    public BundleProvisionImpl()
    {
        m_bundles = new HashSet<String>();
    }

    public BundleProvision addBundle( String bundleUrl )
    {
        NullArgumentException.validateNotEmpty( bundleUrl, "bundleUrl" );

        m_bundles.add( bundleUrl );
        return this;
    }

    public String[] getBundles()
    {
        return m_bundles.toArray( new String[m_bundles.size()] );
    }
}
