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
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.OsgiTestRunner;

/**
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 18, 2008
 */
@RunWith( OsgiTestRunner.class )
public class AdditionalBundlesTest
{

    /**
     * Logger.
     */
    private static final Log LOG = LogFactory.getLog( AdditionalBundlesTest.class );

    @Configuration
    public TestRunnerConnector configure()
    {
        return create( createBundleProvision()
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-api" )
            .addBundle( "mvn:org.ops4j.pax.logging/pax-logging-service" )
        ).setPlatform( Platforms.FELIX );
    }

    @Test
    public void logMessage()
    {
        LOG.info( "This test is just about logging this message" );
    }

}