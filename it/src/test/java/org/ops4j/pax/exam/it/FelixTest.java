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
package org.ops4j.pax.exam.it;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.OsgiTestRunner;
import org.ops4j.pax.exam.junit.JunitSupport;

/**
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Toni Menzel (tonimenzel@gmx.de)
 * @since 0.3.0, November 17, 2008
 */
@RunWith(OsgiTestRunner.class)
public class FelixTest {

    @Configuration
    public TestRunnerConnector configure() {
        return create(createBundleProvision()
                // see AddtionalApiTest for explanation
                .addBundle(JunitSupport.bundles())
        )
                .setPlatform(Platforms.FELIX);
    }

    @Test
    public void frameworkIsUpAndRunning(final BundleContext bundleContext) {
        assertThat("Bundle context", bundleContext, is(notNullValue()));
        assertThat(
                "Framework vendor",
                bundleContext.getProperty(Constants.FRAMEWORK_VENDOR),
                is(equalTo("Apache Software Foundation"))
        );
    }

}