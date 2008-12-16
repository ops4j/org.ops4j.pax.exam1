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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * Provisioning integration tests.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 18, 2008
 */
@RunWith( JUnit4TestRunner.class )
public class JunitAnnotationsTest
{

    /**
     * An ignored test taht should not be run.
     */
    @Test
    @Ignore
    public void ignored()
    {
        fail( "This method was not supposed to be run" );
    }

    @Test( expected = RuntimeException.class )
    public void expected()
    {
        throw new RuntimeException( "This exception should not fail the test" );
    }

}