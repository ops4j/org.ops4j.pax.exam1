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
package org.ops4j.pax.exam.tools;

import java.io.PrintStream;

import org.ops4j.pax.exam.api.DroneSummary;
import org.ops4j.pax.exam.api.RunnerContext;
import org.ops4j.pax.exam.connector.paxrunner.PaxRunnerConnector;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.connector.paxrunner.internal.PaxRunnerConnectorImpl;
import org.ops4j.pax.exam.spi.PrebuildDroneProvider;
import org.ops4j.pax.exam.spi.TextSummary;
import org.ops4j.pax.exam.spi.internal.BundleProvisionImpl;
import org.ops4j.pax.exam.spi.internal.RunnerContextImpl;
import org.ops4j.pax.exam.api.BundleProvision;
import org.ops4j.pax.exam.api.DroneProvider;

/**
 * Commandline tool to use paxdrone with
 * prebuild tests with customized configurataion
 * from commandline.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 4, 2008
 */
public class Main
{

    // public static Log LOG = LogFactory.getLog( Main.class, LogLevel.DEBUG );

    static public void main( String[] args )
    {
        new Main().run( args, System.out );
    }

    private void run( String[] args, PrintStream out )
    {
        System.out.println( "--> Pax Drone Tools <--" );

        if( args.length > 0 )
        {
            String mainCommand = args[ 0 ];
            if( mainCommand.equalsIgnoreCase( "run" ) )
            {
                // collect recipes and additional bundles

                // 1. A Context. You can set things on the runner like:
                // bundles you wanna have in it.
                // when and what to start.
                // Event things like working directory and clearance are set/made with this.

                RunnerContext context = new RunnerContextImpl();

                BundleProvision provision = new BundleProvisionImpl();
                //context.addBundle( "fooBundle" );

                // 2. Defining Connector means:
                // choose a framework distribution

                // Pax Runner is a very bright choice. Its the default one
                PaxRunnerConnector connector = new PaxRunnerConnectorImpl( context, provision );

                // configure details con connector Instance:
                connector.setPlatform( Platforms.FELIX );
                // or any other settings you would normally do for the system bundle:
                // set bootdelegation packages, system environment and so on.

                // you'll need a provider that will make up your test.
                // though we will use a prebuild one

                // TODO replace param with real recipe from recipes sub project.

                DroneProvider provider = new PrebuildDroneProvider( "mvn:org.ops4j.pax.exam/pax-exam-recipe-propertycheck@update" );

                // Now you can execute your connector using the provider just constructed.
                DroneSummary s = connector.execute( provider );

                // for every run you'll get a summary which contains everything you want.
                // even (human thinkable) exceptions.

                // you can use a formatter to print it to the console for example.
                new TextSummary( s ).printSummary( false );
            }
        }
        else
        {
            printUsageAndExit();
        }
        System.out.println( "end." );


    }

    private void printUsageAndExit()
    {
        System.out.println( "Usage: <command> <optional parameters>" );
        System.out.println( "  Supported Commands: " );
        System.out.println( "    scaffold (invokes the scaffolding mode)" );
        System.out.println( "    run (invokes the test runner mode)" );
        System.exit( 1 );
    }
}
