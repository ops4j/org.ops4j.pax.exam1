package org.ops4j.pax.exam.junit;

import org.junit.internal.runners.InitializationError;
import org.ops4j.pax.exam.Option;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 18, 2009
 */
public class MavenConfiguredJUnit4TestRunner extends AbstractJUnit4TestRunner
{

    public MavenConfiguredJUnit4TestRunner( Class<?> aClass )
        throws InitializationError
    {
        super( aClass );
    }

    protected Option[] getTestOptions()
    {
        return new Option[] {
            // TODO: useMavenConfiguration() Option
        };
    }
}
