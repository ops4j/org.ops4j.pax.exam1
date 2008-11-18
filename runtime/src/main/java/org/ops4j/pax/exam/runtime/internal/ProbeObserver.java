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

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.ops4j.pax.exam.api.TestRunner;
import org.ops4j.pax.swissbox.core.BundleUtils;
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
     * Holder for test runner registrations per bundle.
     */
    private final Map<Bundle, Registration> m_registrations;

    /**
     * Constructor.
     */
    ProbeObserver()
    {
        m_registrations = new HashMap<Bundle, Registration>();
    }

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
        Dictionary<String, String> props = new Hashtable<String, String>();
        props.put( "testCase", testCase );
        props.put( "testMethod", testMethod );
        final BundleContext bundleContext = BundleUtils.getBundleContext( bundle );
        final ServiceRegistration serviceRegistration = bundleContext.registerService(
            TestRunner.class.getName(),
            new TestRunnerImpl( bundleContext, testCase, testMethod ),
            props
        );
        m_registrations.put( bundle, new Registration( testCase, testMethod, serviceRegistration ) );
        LOG.info( "Registered testcase [" + testCase + "." + testMethod + "]" );
    }

    /**
     * {@inheritDoc}
     * Unregisters prior registered test for the service.
     */
    public void removingEntries( final Bundle bundle,
                                 final List<ManifestEntry> manifestEntries )
    {
        final Registration registration = m_registrations.remove( bundle );
        if( registration != null )
        {
            registration.serviceRegistration.unregister();
            LOG.info( "Unregistered testcase [" + registration.testCase + "." + registration.testMethod + "]" );
        }
    }

    /**
     * Registration holder.
     */
    private static class Registration
    {

        final String testCase;
        final String testMethod;
        final ServiceRegistration serviceRegistration;

        public Registration( final String testCase,
                             final String testMethod,
                             final ServiceRegistration serviceRegistration )
        {
            this.testCase = testCase;
            this.testMethod = testMethod;
            this.serviceRegistration = serviceRegistration;
        }
    }

}