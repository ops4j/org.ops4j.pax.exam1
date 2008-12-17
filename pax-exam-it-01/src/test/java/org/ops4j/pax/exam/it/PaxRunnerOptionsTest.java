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

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.AppliesTo;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * Pax Runner options integration tests.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 17, 2008
 */
@RunWith( JUnit4TestRunner.class )
public class PaxRunnerOptionsTest
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
     * Pax Exam test options that adds provisioning via a file scanner.
     * Valid for all test methods.
     *
     * @return test options
     */
    @Configuration
    @AppliesTo( "file.*" )
    public static Option[] configureFileScanner()
    {
        return options(
            scanFile( "x" ).noStart().update().startLevel( 10 )
        );
    }

    /**
     * TODO what we can test here?
     */
    @Test
    public void dirScanner()
    {
    }

    /**
     * TODO what we can test here?
     */
    @Test
    public void fileScanner()
    {
    }


}