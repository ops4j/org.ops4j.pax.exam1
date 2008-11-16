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
package org.ops4j.pax.drone.commons.urlhandler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.qi4j.composite.ConcernOf;
import org.ops4j.pax.drone.api.DroneConnector;
import org.ops4j.pax.drone.api.DroneProvider;
import org.ops4j.pax.drone.api.DroneSummary;
import org.ops4j.pax.url.mvn.internal.ConfigurationImpl;
import org.ops4j.pax.url.mvn.internal.Connection;
import org.ops4j.pax.url.mvn.internal.SettingsImpl;
import org.ops4j.util.property.PropertyResolver;

/**
 * Adds URLHandler support to a connector.
 *
 * @author Toni Menzel (tonit)
 * @since Jul 21, 2008
 */
public class URLHandlerConcern extends ConcernOf<DroneConnector> implements DroneConnector
{

    public DroneSummary execute( DroneProvider provider )

    {
        // add handler
//        URLStreamHandlerExtender extender = new URLStreamHandlerExtender();
//        try
//        {
//            registerURLHander( extender );
//            // run real impl.
//            next.execute( recipe );
//        } finally
//        {
//            // cleanup
//            extender.unregister( new String[]{ "mvn" } );
//
//        }
        return null;
    }

//    private void registerURLHander( URLStreamHandlerExtender extender )
//    {
//        extender.register( new String[]{ "mvn" }, new AbstractURLStreamHandlerService()
//        {
//
//            public URLConnection openConnection( URL url )
//                throws IOException
//            {
//                ConfigurationImpl configuration = new ConfigurationImpl( new PropertyResolver()
//                {
//
//                    public String get( String s )
//                    {
//                        return null;
//                    }
//                }
//                );
//                configuration.setSettings( new SettingsImpl( null ) );
//                return new Connection( url, configuration );
//            }
//        }
//        );
//        extender.start();
//    }
}
