package org.ops4j.pax.exam.options;

import org.ops4j.pax.exam.ConfigurationOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:26:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CompositeConfigurationOption
    extends ConfigurationOption
{
    Iterable<ConfigurationOption> getOptions();
}