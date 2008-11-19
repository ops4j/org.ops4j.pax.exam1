/*
 * Copyright 2008 Toni Menzel
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.connector.paxrunner.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.io.Pipe;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.TestExecutionException;
import org.ops4j.pax.exam.api.TestRunner;
import org.ops4j.pax.exam.connector.paxrunner.SubProcess;
import org.ops4j.pax.exam.runtime.connector.rmi.RemoteTestRunner;
import org.ops4j.pax.runner.*;
import org.ops4j.pax.runner.platform.JavaRunner;
import org.ops4j.pax.runner.platform.PlatformException;
import org.ops4j.pax.runner.platform.internal.CommandLineBuilder;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

/**
 * This controls paxrunner in a separate process while mantaining the framework state (start/stop)
 *
 * @author Toni Menzel (tonit)
 * @author Stuart McCulloch (mcculls)
 * @since Jun 18, 2008
 */
public class PaxRunnerInstanceImpl implements SubProcess
{

    private static final Log LOG = LogFactory.getLog( PaxRunnerInstanceImpl.class );
    private int m_waitingDelay;
    private Process frameworkProcess;
    private Thread pipeShutdownHook;
    private String[] m_options;
    private File m_work;
    private int m_communicationPort;

    PaxRunnerInstanceImpl( String[] options, File workingDirectory, int communicationPort )
    {
        this( options, workingDirectory, communicationPort, PaxRunnerConnectorImpl.DEFAULT_TIMEOUT );
    }

    PaxRunnerInstanceImpl( String[] options, File workingDirectory, int communicationPort, int waitingDelay )
    {
        NullArgumentException.validateNotNull( options, "options" );
        NullArgumentException.validateNotNull( workingDirectory, "workingDirectory" );

        m_options = options;
        m_work = workingDirectory;
        m_communicationPort = communicationPort;
        m_waitingDelay = waitingDelay;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void startup()
    {
        if( frameworkProcess == null )
        {
            LOG.info( "Trying to load paxrunner instance.." );
            CommandLine commandLine = new CommandLineImpl( m_options );
            final Configuration config = new ConfigurationImpl( "classpath:META-INF/runner.properties" );
            Run runner = new Run();
            runner.start(
                commandLine,
                config,
                new OptionResolverImpl( commandLine, config ),
                new Runner()
            );
            LOG.info( "OSGi Framework has been started successfully!" );
        }
        else
        {
            throw new RuntimeException( "There is already an instance running!" );
        }
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void shutdown()
    {
        try
        {
            if( frameworkProcess != null )
            {
                Runtime.getRuntime().removeShutdownHook( pipeShutdownHook );
                pipeShutdownHook.start();
                long timeout = System.currentTimeMillis() + 200;
                while( pipeShutdownHook.isAlive() || System.currentTimeMillis() < timeout )
                {
                    ;
                }

                frameworkProcess.destroy();


            }
        }
        catch( Exception e )
        {
            throw new TestExecutionException( "Exception during shutdown process..", e );
        }
    }

    /**
     * Spawns a new process and provides a way to shutdown it.
     */
    private class Runner implements JavaRunner
    {

        public void exec( String[] vmOptions, String[] classpath, String mainClassName, String[] programOptions )
            throws PlatformException
        {
            final CommandLineBuilder commandLine = new CommandLineBuilder()
                .append( getJavaExecutable() )
                .append( vmOptions )
                .append( "-cp" )
                .append( classpath )
                .append( mainClassName )
                .append( programOptions );

            executeProcess( commandLine.toArray(), m_work );
        }

        private String getJavaExecutable()
            throws PlatformException
        {
            final String javaHome = System.getProperty( "java.home" );
            if( javaHome == null )
            {
                throw new PlatformException( "JAVA_HOME is not setProperty." );
            }
            return javaHome + "/bin/java";
        }

        /**
         * Executes a new process and waits for a signal (remote service) being available to continue
         *
         * @param commandLine
         * @param workingDirectory
         */
        private void executeProcess( String[] commandLine, final File workingDirectory )
            throws PlatformException
        {
            try
            {
                if( getRemoteTestRunner() != null )
                {
                    throw new RuntimeException( "PaxRunner Instance is already running..!" );
                }
                frameworkProcess = Runtime.getRuntime().exec( commandLine, null, workingDirectory );

            }
            catch( IOException e )
            {
                throw new RuntimeException( "Could not start up the process", e );
            }

            pipeShutdownHook = createShutdownHook( frameworkProcess );
            Runtime.getRuntime().addShutdownHook( pipeShutdownHook );

            LOG.debug(
                "wait for boot on port: " + m_communicationPort + " max. " + m_waitingDelay + "ms. to wait now.."
            );
            // try to reach server.. or timeout
            if( !testForRunningInstance( m_waitingDelay ) )
            {
                throw new RuntimeException( "NOT RESPONDING! Last exception: " );
            }


        }

        /**
         * Create helper thread to safely shutdown the external framework process
         *
         * @param process framework process
         *
         * @return stream handler
         */
        private Thread createShutdownHook( final Process process )
        {
            final Pipe errPipe = new Pipe( process.getErrorStream(), System.err ).start( "Error pipe" );
            final Pipe outPipe = new Pipe( process.getInputStream(), System.out ).start( "Out pipe" );
            final Pipe inPipe = new Pipe( process.getOutputStream(), System.in ).start( "In pipe" );

            Thread shutdownHook = new Thread( new Runnable()
            {
                public void run()
                {
                    inPipe.stop();
                    outPipe.stop();
                    errPipe.stop();
                    try
                    {
                        process.destroy();
                    }
                    catch( Exception e )
                    {
                        // ignore if already shutting down
                    }


                }
            }, "Pax Exam shutdown hook"
            );

            return shutdownHook;
        }

        /**
         * Tests for a running instance and keeps trying until the instance is retrieved (return true) or the amount of timout (milliseconds)
         * has passed (return false)
         *
         * @param timeout the amount of time to keep trying (milliseconds)
         *
         * @return true if instance has been found. Otherwise (timed out) false
         */
        private boolean testForRunningInstance( long timeout )
        {
            long startedTrying = System.currentTimeMillis();
            do
            {
                try
                {
                    // exit conditions: established connection or timeout reached.
                    if( getRemoteTestRunner() != null )
                    {
                        return true;

                    }
                }
                catch( Exception e )
                {
                    // if this happens, we just leave the loop and trust in value of {established}
                    break;
                }
            }
            while( timeout == 0 || System.currentTimeMillis() < startedTrying + timeout );

            return false;
        }

        private RemoteTestRunner getRemoteTestRunner()

        {
            RemoteTestRunner stub = null;
            try
            {
                //!! Absolutely nececary for RMIClassLoading to work
                Thread.currentThread().setContextClassLoader( this.getClass().getClassLoader() );

                Registry registry = LocateRegistry.getRegistry( m_communicationPort );
                stub = (RemoteTestRunner) registry.lookup( TestRunner.class.getName() );
            }
            catch( Exception e )
            {

            }
            return stub;
        }
    }


}
