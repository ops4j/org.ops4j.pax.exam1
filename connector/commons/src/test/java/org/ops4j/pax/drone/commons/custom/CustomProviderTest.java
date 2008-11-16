package org.ops4j.pax.drone.commons.custom;

import org.junit.Test;
import org.ops4j.pax.drone.commons.CompositeProvider;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 21, 2008
 */
public class CustomProviderTest
{

    @Test
    public void runCustom()
        throws Exception
    {
        CustomConnector con = new CompositeProvider<CustomConnector>().get( CustomConnector.class );
        con.setRecipeHost( this.getClass().getName() );
        con.addBundle( "b1" );
        con.addBundle( "b2" );
        con.execute( null );

    }


}
