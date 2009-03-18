package org.ops4j.pax.exam.container.def;

import java.util.Properties;
import org.ops4j.pax.exam.OptionResolver;
import org.ops4j.pax.exam.Option;

/**
 * Recognizes paxrunner options from properties.
 * 
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public class PaxRunnerOptionResolver implements OptionResolver
{

    public Option[] getOptionsFromProperties( Properties properties )
    {
        System.out.println( "Got props: " + properties.keySet().size() );
        return new Option[0]; 
    }
}
