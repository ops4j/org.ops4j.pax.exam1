package org.ops4j.pax.exam;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:05:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Configuration
{

    Iterable<ConfigurationOption> getOptions();

    <T> Iterable<T> getOptions(Class<T> optionType);

}
