package org.ops4j.pax.exam.container.def.options;

import org.ops4j.pax.exam.Option;

/**
 * @author Toni Menzel (tonit)
 * @since Dec 19, 2008
 */
public interface RepositoryOption extends Option
{
    public RepositoryOption allowSnapshots();
    public RepositoryOption disableReleases();
    
}
