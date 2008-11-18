/*
 * Copyright 2008 Alin Dreghiciu
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.runtime.internal;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.Bundle;
import org.ops4j.pax.exam.api.TestRunner;
import org.ops4j.pax.swissbox.extender.BundleObserver;
import org.ops4j.pax.swissbox.extender.ManifestEntry;

/**
 * Registers/Unregisters test services for test probes.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, Nobember 18, 2008
 */
class ProbeObserver
    implements BundleObserver<ManifestEntry>
{

    /**
     * Logger.
     */
    private static final Log LOG = LogFactory.getLog( ProbeObserver.class );

    /**
     * {@inheritDoc}
     * Registers specified test case as a service.
     */
    public void addingEntries( final Bundle bundle,
                               final List<ManifestEntry> manifestEntries )
    {
        String testCase = null;
        String testMethod = null;
        for( ManifestEntry manifestEntry : manifestEntries )
        {
            if( TestRunner.PROBE_TEST_CASE.equals( manifestEntry.getKey() ) )
            {
                testCase = manifestEntry.getValue();
            }
            if( TestRunner.PROBE_TEST_METHOD.equals( manifestEntry.getKey() ) )
            {
                testMethod = manifestEntry.getValue();
            }
        }
        LOG.info( "Found test: " + testCase + "." + testMethod );
    }

    /**
     * {@inheritDoc}
     * Unregisters prior registered test for the service.
     */
    public void removingEntries( final Bundle bundle,
                                 final List<ManifestEntry> manifestEntries )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}