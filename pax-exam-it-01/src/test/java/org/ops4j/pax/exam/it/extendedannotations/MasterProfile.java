package org.ops4j.pax.exam.it.extendedannotations;

import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.options.CompositeOption;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 6, 2009
 */
public class MasterProfile implements CompositeOption
{

    public Option[] getOptions()
    {
        return new Option[]{ equinox() };
    }
}
