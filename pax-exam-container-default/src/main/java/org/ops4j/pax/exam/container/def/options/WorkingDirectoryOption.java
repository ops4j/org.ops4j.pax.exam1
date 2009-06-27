package org.ops4j.pax.exam.container.def.options;

import org.ops4j.pax.exam.Option;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jun 26, 2009
 * Time: 7:51:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkingDirectoryOption implements Option
{
    final private String m_workingDirectory;

    public WorkingDirectoryOption(String directory) {
        m_workingDirectory = directory;
    }

    public String getWorkingDirectory() {
        return m_workingDirectory;
    }
}
