package org.ops4j.pax.drone.commons.custom;

import java.net.MalformedURLException;
import java.net.URL;
import org.qi4j.injection.scope.This;
import org.ops4j.pax.drone.api.DroneConnector;
import org.ops4j.pax.drone.api.DroneProvider;
import org.ops4j.pax.drone.api.DroneSummary;
import org.ops4j.pax.drone.commons.DroneProperties;
import org.ops4j.pax.drone.commons.additionalbundles.ProvidesBundles;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 21, 2008
 */
public class CustomConnectorMixin implements DroneConnector
{

    @This
    DroneProperties p;

    @This
    ProvidesBundles bundleProvider;

    public DroneSummary execute( DroneProvider provider )
    {
        System.out.println( "-Found " + p.getRecipeHost() );
        for( String s : bundleProvider.getBundles() )
        {
            System.out.println( " + " + s );
        }
        try
        {
            new URL( "mvn:foo/bar" );
        } catch( MalformedURLException e )
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
