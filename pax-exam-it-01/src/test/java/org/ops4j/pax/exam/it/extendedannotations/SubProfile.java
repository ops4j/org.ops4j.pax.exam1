package org.ops4j.pax.exam.it.extendedannotations;

import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 6, 2009
 */
public class SubProfile implements CompositeOption
{

    public Option[] getOptions()
    {
        return new Option[]{ webProfile() };
    }
}
