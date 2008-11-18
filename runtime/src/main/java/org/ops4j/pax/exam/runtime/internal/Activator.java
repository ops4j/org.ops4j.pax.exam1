/*
 * Copyright 2008 Toni Menzel
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

import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.api.TestRunner;

/**
 * This activator publishes a {@link TestRunner} implementation.
 *
 * @author Toni Menzel (tonit)
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since May 28, 2008
 */
public class Activator
    implements BundleActivator
{

    public void start( BundleContext bundleContext )
        throws Exception
    {
        // TODO the service publication should be delayed until a certain state (all bundles in ACTIVE state ?) is satisfied.
        bundleContext.registerService(
            TestRunner.class.getName(),
            new TestRunnerImpl( bundleContext ),
            new Hashtable()
        );
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        // do nothing
    }

}
