/*
 * Copyright 2009 Toni Menzel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.tinybundles.test.exam.simpliest;

import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;
import org.ops4j.pax.tinybundles.test.DemoBundles;
import org.ops4j.pax.tinybundles.test.simpliest.HelloWorldService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class SimpliestTest
{

    @Configuration
    public static Option[] rootConfig()
    {
        return options(

            provision(
                mavenBundle()
                    .groupId( "org.ops4j.pax.tinybundles" )
                    .artifactId( "pax-tinybundles-core" )
                    .version( Info.getPaxExamVersion() )
            ),
            // install extra tiny bundles that are created on demand:
            provision( DemoBundles.simpliest( "foo" ).prepare( asURL() ).toExternalForm() )

            //,cleanCaches()
        );
    }

    @Inject
    BundleContext context;

    @Test
    public void testCall()
        throws BundleException, IOException
    {
        // check and call service
        ServiceReference ref = context.getServiceReference( HelloWorldService.class.getName() );
        assertNotNull( ref );
        HelloWorldService service = (HelloWorldService) context.getService( ref );
        assertNotNull( service );
        assertEquals( "Hello, Toni", service.hello( "Toni" ) );
        context.ungetService( ref );

    }
}
