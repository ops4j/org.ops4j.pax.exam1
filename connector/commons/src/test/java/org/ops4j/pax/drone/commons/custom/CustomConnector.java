package org.ops4j.pax.drone.commons.custom;

import org.qi4j.composite.Composite;
import org.qi4j.composite.Concerns;
import org.qi4j.composite.Mixins;
import org.qi4j.library.beans.properties.PropertiesMixin;
import org.ops4j.pax.drone.api.DroneConnector;
import org.ops4j.pax.drone.commons.DroneProperties;
import org.ops4j.pax.drone.commons.additionalbundles.AdditionalBundlesMixin;
import org.ops4j.pax.drone.commons.additionalbundles.CanAddBundle;
import org.ops4j.pax.drone.commons.urlhandler.URLHandlerConcern;

/**
 *
 * A custom connector composite showing most of
 * the supported functionality.
 * The CustomConnectorMixin is the only part that is being developed "by hand" in this case.
 * 
 * @author Toni Menzel (tonit)
 * @since Jul 21, 2008
 */

@Concerns( { URLHandlerConcern.class } )
@Mixins( { AdditionalBundlesMixin.class, CustomConnectorMixin.class, PropertiesMixin.class } )
public interface CustomConnector extends DroneConnector, CanAddBundle, DroneProperties, Composite
{

}
