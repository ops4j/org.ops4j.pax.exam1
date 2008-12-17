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
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.junit.AppliesTo;

/**
 * Framework options integration tests.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 17, 2008
 */
@RunWith( JUnit4TestRunner.class )
public class MultiFrameworkOptionsTest
{

    /**
     * Pax Exam test options that adds a log profile.
     * Valid for all test methods.
     *
     * @return test options
     */
    @Configuration
    public static Option[] configure()
    {
        return options(
            logProfile()
        );
    }

    /**
     * Pax Exam test options that specified more Felix versions as test framework.
     * Valid for test methods that starts with "felix".
     *
     * @return test options
     */
    @Configuration
    @AppliesTo( "felix.*" )
    public static Option[] configureFelix()
    {
        return options(
            frameworks(
                felix().version( "1.0.0" ),
                felix().version( "1.0.3" ),
                felix().version( "1.0.4" ),
                felix()
            )
        );
    }

    /**
     * Pax Exam test options that specified all frameworks as test framework.
     * Valid for test methods that starts with "all".
     *
     * @return test options
     */
    @Configuration
    @AppliesTo( "multiple.*" )
    public static Option[] configureAllFrameworks()
    {
        return options(
            frameworks(
                equinox(),
                felix(),
                knopflerfish()
            )
        );
    }

    /**
     * Test that the started framewrok is Felix.
     *
     * @param bundleContext injected bundle context
     */
    @Test
    public void felixIsUpAndRunning( final BundleContext bundleContext )
    {
        assertThat( "Bundle context", bundleContext, is( notNullValue() ) );
        assertThat(
            "Framework vendor",
            bundleContext.getProperty( Constants.FRAMEWORK_VENDOR ),
            is( equalTo( "Apache Software Foundation" ) )
        );
    }

    @Test
    public void multipleIsUpAndRunning( final BundleContext bundleContext )
    {
        assertThat( "Bundle context", bundleContext, is( notNullValue() ) );
        assertThat(
            "Framework vendor",
            bundleContext.getProperty( Constants.FRAMEWORK_VENDOR ),
            is( notNullValue() )
        );
    }

}