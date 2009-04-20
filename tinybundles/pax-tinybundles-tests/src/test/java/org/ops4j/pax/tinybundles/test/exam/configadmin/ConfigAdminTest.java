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
package org.ops4j.pax.tinybundles.test.exam.configadmin;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Properties;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;
import org.ops4j.pax.tinybundles.test.DemoBundles;
import org.ops4j.pax.tinybundles.test.configadmin.SampleService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class ConfigAdminTest
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
            profile( "felix.config" ), logProfile()
            ,
            // install extra tiny bundles that are created on demand:
            provision( DemoBundles.configadmin( "configadmin" ).prepare( asURL() ).toExternalForm() )

            //,cleanCaches()
        );
    }

    @Inject
    BundleContext context;

    /**
     * Some playful stuff with tinybundles..
     */
    @Test
    public void configAdminTest()
        throws BundleException, IOException
    {
        // check and call configAdmin
        ServiceReference ref = context.getServiceReference( ConfigurationAdmin.class.getName() );
        assertNotNull( ref );
        ConfigurationAdmin configAdmin = (ConfigurationAdmin) context.getService( ref );
        assertNotNull( configAdmin );
        // now reconfigure configAdmin:
        org.osgi.service.cm.Configuration configuration =
            configAdmin.createFactoryConfiguration( "samples.configadmin.factory", "4711" );
        assertNotNull( configuration );
        Dictionary dict = new Properties();
        dict.put( "greeting", "Good Morning, " );
        configuration.update( dict );

        context.ungetService( ref );

        System.out.println( "Waiting.." );
        try
        {
            Thread.sleep( 10000 );
        }
        catch( InterruptedException e )
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println( "Done waiting." );

        // call real configAdmin
        ref = context.getServiceReference( SampleService.class.getName() );
        SampleService sample = (SampleService) context.getService( ref );
//        assertEquals( "Good Morning, Donald", sample.hello( "Donald" ) );

    }

}
