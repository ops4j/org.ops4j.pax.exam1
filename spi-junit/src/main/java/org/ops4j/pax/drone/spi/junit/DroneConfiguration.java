package org.ops4j.pax.drone.spi.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker Annotation to declare the configure method
 * for a Drone based Junit4 testcase.
 *
 * Must return DroneConnector.
 * 
 * @author Toni Menzel (tonit)
 * @since Oct 14, 2008
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface DroneConfiguration
{
   // marker
}
