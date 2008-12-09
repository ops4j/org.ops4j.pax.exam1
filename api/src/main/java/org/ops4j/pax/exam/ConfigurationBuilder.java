package org.ops4j.pax.exam;

import java.util.ArrayList;
import java.util.List;
import org.ops4j.pax.exam.options.CompositeOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:16:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationBuilder
    implements Configuration
{

    private final List<ConfigurationOption> m_options;

    public ConfigurationBuilder()
    {
        m_options = new ArrayList<ConfigurationOption>();
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

    public <T> Iterable<T> getOptions( Class<T> optionType )
    {
        final List<T> filtered = new ArrayList<T>();
        for( ConfigurationOption option : getOptions() )
        {
            if( optionType.isAssignableFrom( option.getClass() ) )
            {
                filtered.add( (T) option );
            }
        }
        return filtered;
    }

    public static ConfigurationBuilder newConfiguration( ConfigurationOption... options )
    {
        return new ConfigurationBuilder();
    }

}