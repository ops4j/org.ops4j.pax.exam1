package org.ops4j.pax.exam.container.def.internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class TestContainerSemaphore
{

    /**
     * JCL logger.
     */
    private static final Log LOG = LogFactory.getLog( PaxRunnerTestContainer.class );

    private File m_workingFolder;

    public TestContainerSemaphore( File workingFolder )
    {
        m_workingFolder = workingFolder;
    }

    public boolean acquire()
    {
        if( lockExists() )
        {
            // blame !
            LOG.error( "Blame ! Acquire lock for new Pax Runner instance failed at " + getLockFile().getAbsolutePath() );

            appendToFile( "! Tried to acquire this on " + new Date().toString() );
            return false;
        }
        else
        {
            // create
            LOG.info( "Acquire lock for new Pax Runner instance on " + getLockFile().getAbsolutePath() );

            appendToFile( "Created on " + new Date().toString() );
            return true;
        }
    }

    public void release()
    {
        // delete file
        getLockFile().delete();
    }

    public File getLockFile()
    {
        return new File( m_workingFolder, "paxexam.lock" );
    }

    private boolean lockExists()
    {
        return getLockFile().exists();
    }

    private void appendToFile( String s )
    {
        FileWriter fw = null;
        try
        {
            fw = new FileWriter( getLockFile(), true );
            fw.write( s );
        } catch( IOException e )
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                fw.close();
            } catch( IOException e )
            {
                e.printStackTrace();
            }
        }


    }
}
