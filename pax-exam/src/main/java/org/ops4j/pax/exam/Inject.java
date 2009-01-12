package org.ops4j.pax.exam;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author Toni Menzel ( toni@okidokiteam.com )
 * @since Jan 12, 2009
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Inject
{

}
