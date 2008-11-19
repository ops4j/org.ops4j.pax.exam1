/*
 * Copyright 2008 Toni Menzel
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
package org.ops4j.pax.exam.samples.simple;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.OsgiTestRunner;
import org.ops4j.pax.exam.junit.JunitSupport;

/**
 * TODO fill test results with proper timesheet.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 14, 2008
 */
@RunWith( OsgiTestRunner.class )
public class JUnit4Test
{

    @Configuration
    public TestRunnerConnector configure()
    {
        return create( createBundleProvision().addBundle( JunitSupport.bundles() ) ).setPlatform( Platforms.FELIX );
    }

    @Test
    public void aVerySimpleOne()
    {
        System.out.println( "Hello World" );
    }

    @Test
    public void anotherTestForTesting()
    {
        System.out.println( "Again, Hello World" );
        //  fail( "thats bad" );
    }

    @Test
    public void haveBundleContextPassedInto( BundleContext bc )
    {
        System.out.println( "Again, Hello World" );
        assertNotNull( bc );
    }


}
