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

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * Inject annotation integration tests.
 *
 * @author Toni Menzel (toni@okidokiteam.com)
 * @since 0.3.0, January 12, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class InjectionTest
{

    @Inject
    public BundleContext bundleContext;

    @Inject
    public String shouldBeNull;

    public BundleContext shouldBeNullAsWell;

    /**
     * Tests if bundleContext is being injected correctly.
     */
    @Test
    public void injectTest()
    {
        assertNotNull( bundleContext );
        assertNull( shouldBeNull );
        assertNull( shouldBeNullAsWell );

    }

}