package org.ops4j.pax.exam;

import java.util.Properties;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public interface OptionResolver
{
    Option[] getOptionsFromProperties( Properties properties );
}
