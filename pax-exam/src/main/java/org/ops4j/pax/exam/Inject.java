package org.ops4j.pax.exam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Toni Menzel ( toni@okidokiteam.com )
 * @since Jan 12, 2009
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Inject
{

}
