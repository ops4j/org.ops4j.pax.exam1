package org.ops4j.pax.drone.spi.intern;

import java.io.File;
import java.rmi.registry.Registry;
import org.ops4j.net.FreePort;
import org.ops4j.pax.exam.api.RunnerContext;

/**
 * A context is a shared resource.
 * Once a setting has been made, it cannot be changed.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 8, 2008
 */
public class RunnerContextImpl
    implements RunnerContext
{

    private static final int AMOUNT_OF_PORTS_TO_CHECK = 100;
    private FreePort m_communicationPort;
    private File m_work;

    public RunnerContextImpl()
    {
        // lazy initializing communication port
        m_communicationPort = new FreePort( Registry.REGISTRY_PORT, Registry.REGISTRY_PORT + AMOUNT_OF_PORTS_TO_CHECK );
    }

    public int getCommunicationPort()
    {
        return m_communicationPort.getPort();
    }

    public File getWorkingDirectory()
    {
        if( m_work == null )
        {
            String currentUser = "";
            currentUser = System.getProperty( "user.name" );
            m_work = new File( System.getProperty( "java.io.tmpdir" ) + "/paxdrone_runner_" + currentUser );
            // create if not existent:
            if( !m_work.exists() )
            {
                m_work.mkdirs();
            }
        }
        return m_work;
    }


}
