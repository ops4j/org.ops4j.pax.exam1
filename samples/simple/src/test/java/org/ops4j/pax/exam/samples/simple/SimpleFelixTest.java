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

import org.osgi.framework.Constants;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.*;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.junit.JunitSupport;
import org.ops4j.pax.exam.junit.OsgiTestCase;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 22, 2008
 */
public class SimpleFelixTest extends OsgiTestCase
{

    protected TestRunnerConnector configure()
    {
        return create( createBundleProvision()
            .addBundle( JunitSupport.bundles() )
        ).setPlatform( Platforms.FELIX );
    }

    public void testSayHello()
    {
        String name = bundleContext.getProperty( Constants.FRAMEWORK_VENDOR );
        assertEquals( "Apache Software Foundation", name );
        System.out.println( "Hello " + name );
    }
}
