package org.ops4j.pax.tinybundles.test.simpliest.internal;

import org.ops4j.pax.tinybundles.test.simpliest.HelloWorldService;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 7, 2009
 */
public class HelloWorldImpl implements HelloWorldService
{

    public String hello( String name )
    {
        return "Hello, " + name;
    }
}
