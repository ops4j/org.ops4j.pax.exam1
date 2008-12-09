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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import static org.ops4j.pax.exam.ConfigurationBuilder.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.junit.JUnitOptions.*;
import org.ops4j.pax.exam.junit.OsgiTestRunner;
import static org.ops4j.pax.exam.paxrunner.PaxRunnerOptions.*;

/**
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 18, 2008
 */
@RunWith( OsgiTestRunner.class )
public class NewApi
{

    /**
     * Logger.
     */
    private static final Log LOG = LogFactory.getLog( NewApi.class );

    public Configuration configure()
    {
        return newConfiguration(
            provision(
                "mvn:org.ops4j.pax.logging/pax-logging-api",
                "mvn:org.ops4j.pax.logging/pax-logging-service"
            ),
            provision(
                mavenBundle().group( "org.ops4j.pax.web" ).artifact( "pax-web-service" ).version( "0.5.1" ),
                easyMockBundle(),
                easyMockBundle().version( "2.3" ),
                junitBundle(),
                junitBundle().version( "4.4" )
            ),
            platform(
                felix(),
                equinox().version( "1.0.0" )
            ),
            profile(
                web()
            ),
            bootDelegation(
                "sun.*",
                "javax.*"
            ),
            systemPackage(
                "org.ops4j.pax"
            )
        );

    }

    @Test
    public void logMessage()
    {
        LOG.info( "This test is just about logging this message" );
    }

}