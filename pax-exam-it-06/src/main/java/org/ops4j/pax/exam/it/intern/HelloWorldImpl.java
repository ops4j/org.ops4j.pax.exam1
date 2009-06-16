package org.ops4j.pax.exam.it.intern;

import org.ops4j.pax.exam.it.HelloWorld;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 9, 2009
 */
public class HelloWorldImpl implements HelloWorld
{

    public String sayHello( String name )
    {
        return "Hello," + name;
    }

}
