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
package org.ops4j.pax.tinybundles.test.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.tinybundles.core.TinyBundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
@RunWith( JUnit4TestRunner.class )
public class ThousandBundlesTest
{

    private static Log LOG = LogFactory.getLog( ThousandBundlesTest.class );

    @Configuration
    public static Option[] rootConfig()
    {
        return options(
            profile( "url" ),
            provision(
                mavenBundle()
                    .groupId( "org.ops4j.pax.tinybundles" )
                    .artifactId( "pax-tinybundles-core" )
                    .version( "0.4.0-SNAPSHOT"
                )

            ),

            logProfile()
            //,cleanCaches()
        );
    }

    /**
     * Create many (small) bundles to give our framework a little load
     *
     * @param amount
     * @param prefix
     */
    private static TinyBundle[] constructManyBundles( int amount, String prefix )
    {
        List<TinyBundle> many = new ArrayList<TinyBundle>();
        for( int i = 1; i <= amount; i++ )
        {
            many.add( newBundle()
                .set( Constants.BUNDLE_SYMBOLICNAME, prefix + "_" + i )
            );
        }
        return many.toArray( new TinyBundle[many.size()] );
    }

    @Inject
    BundleContext context;

    @Test
    public void rawSample()
        throws BundleException, IOException
    {

        // this time we install them INSIDE our test to give felix the full work at runtime and not upfront.
        // Compare this to SimpleUseTest.java

        int i = 0;
        for( TinyBundle b : constructManyBundles( 1000, "Toni" ) )
        {
            Bundle bundle = context.installBundle( "file:/dev/null" + ( i++ ), b.build( asStream() ) );
            LOG.info( "Installed bundle: " + bundle.getBundleId() );
            bundle.start();
        }

        // print our work:
        for( Bundle b : context.getBundles() )
        {
            LOG.info( "Bundle " + b.getBundleId() + ":" + b.getSymbolicName() + " : " + b.getState() );
            assertEquals( Bundle.ACTIVE, b.getState() );
        }

    }
}
