/*
 * Copyright 2008 Toni Menzel.
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
package org.ops4j.pax.drone.connector.echo;

import java.io.InputStream;
import org.ops4j.pax.drone.api.DroneConnector;
import org.ops4j.pax.drone.api.DroneProvider;
import org.ops4j.pax.drone.api.DroneSummary;

/**
 * User: Toni Menzel (tonit)
 * Date: May 28, 2008
 *
 * The NullConnector implements DroneConnector.
 * It prints out messages to System.out on every service call and does nothing useful.
 * It is a dummy.
 */
public class NullConnector implements DroneConnector
{

    public void install( InputStream inp )
    {
        System.out.println( "NullConnector.install with InputStream: " + inp );
    }

    public DroneSummary execute( DroneProvider provider )
    {
        System.out.println( "NullConnector.execute" );
        return null;
    }

    public void execute( String recipe )
    {
        System.out.println( "NullConnector.execute(" + recipe + ")" );
    }

    public void uninstall()
    {
        System.out.println( "NullConnector.uninstall" );
    }
}
