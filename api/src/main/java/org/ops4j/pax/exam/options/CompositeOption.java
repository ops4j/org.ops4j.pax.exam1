package org.ops4j.pax.exam.options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.ops4j.pax.exam.ConfigurationOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 8:36:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompositeOption
    implements CompositeConfigurationOption
{

    private final List<ConfigurationOption> m_options;

    public CompositeOption( final ConfigurationOption... options )
    {
        m_options = Arrays.asList( options );
    }

    public Iterable<ConfigurationOption> getOptions()
    {
        final List<ConfigurationOption> expanded = new ArrayList<ConfigurationOption>();
        for( ConfigurationOption option : m_options )
        {
            if( option instanceof CompositeOption )
            {
                for( ConfigurationOption expandedOption : ( (CompositeOption) option ).getOptions() )
                {
                    expanded.add( expandedOption );
                }
            }
            else
            {
                expanded.add( option );
            }
        }
        return expanded;
    }
    
}
