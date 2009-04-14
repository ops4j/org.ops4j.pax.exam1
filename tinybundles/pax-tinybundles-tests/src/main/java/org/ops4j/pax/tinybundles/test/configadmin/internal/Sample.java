package org.ops4j.pax.tinybundles.test.configadmin.internal;

import org.ops4j.pax.tinybundles.test.configadmin.SampleService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
public class Sample implements SampleService
{

    public String hello( String name )
    {
        return "Hello, " + name;
    }
}
