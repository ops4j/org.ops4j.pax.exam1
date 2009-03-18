package org.ops4j.pax.exam.container.def;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import org.ops4j.pax.exam.OptionResolver;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.container.def.options.ProfileOption;

/**
 * Recognizes paxrunner options from properties.
 *
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public class PaxRunnerOptionResolver implements OptionResolver
{

    private static final String PROFILES = "profiles";

    public Option[] getOptionsFromProperties( Properties properties )
    {
        List<Option> options = new ArrayList<Option>();
        parseProfileOptions( options, properties );
        return options.toArray( new Option[options.size()] );
    }

    private void parseProfileOptions( List<Option> options, Properties properties )
    {
        String profiles = (String) properties.get( PROFILES );
        if( profiles != null )
        {
            for( String p : profiles.split( "," ) )
            {
                options.add( new ProfileOption( p ) );
            }
        }
    }
}
