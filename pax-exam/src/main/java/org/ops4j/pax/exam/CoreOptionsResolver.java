package org.ops4j.pax.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.ops4j.pax.exam.options.FrameworkOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;

/**
 * Recognizes "just" core options.
 *
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public class CoreOptionsResolver implements OptionResolver
{

    private static final String FRAMEWORK = "framework.type";
    private static final Object FRAMEWORK_VERSION = "framework.version";
    private static final String PROVISIONURL_PREFIX = "provision_url_";

    public Option[] getOptionsFromProperties( Properties properties )
    {
        List<Option> options = new ArrayList<Option>();
        parseFrameworkOptions( options, properties );
        parseProvisioningOptions( options, properties );
        return options.toArray( new Option[options.size()] );
    }

    private void parseProvisioningOptions( List<Option> options, Properties properties )
    {
        for( Object key : properties.keySet() )
        {
            if( ( (String) key ).startsWith( PROVISIONURL_PREFIX ) )
            {
                options.add( new UrlProvisionOption( (String) properties.get( key ) ) );
            }
        }
    }

    private void parseFrameworkOptions( List<Option> options, Properties properties )
    {
        String frameworkType = (String) properties.get( FRAMEWORK );
        String frameworkVersion = (String) properties.get( FRAMEWORK_VERSION );
        if( frameworkType != null )
        {
            FrameworkOption option = new FrameworkOption( frameworkType );
            if( frameworkVersion != null )
            {
                option.version( frameworkVersion );
            }
            options.add( option );
        }

    }
}
