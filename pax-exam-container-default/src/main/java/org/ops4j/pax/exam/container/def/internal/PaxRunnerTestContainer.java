/*
 * Copyright 2008 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.container.def.internal;

import java.rmi.registry.Registry;
import org.ops4j.net.FreePort;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.OptionUtils.*;
import static org.ops4j.pax.exam.container.def.internal.ArgumentsBuilder.*;
import org.ops4j.pax.exam.container.def.options.TimeoutOption;
import org.ops4j.pax.exam.rbc.Constants;
import org.ops4j.pax.exam.rbc.client.RemoteBundleContextClient;
import org.ops4j.pax.exam.spi.container.TestContainer;
import org.ops4j.pax.exam.spi.container.TestContainerException;
import static org.ops4j.pax.runner.Run.*;
import org.ops4j.pax.runner.platform.JavaRunner;
import org.ops4j.pax.runner.platform.StoppableJavaRunner;

/**
 * {@link TestContainer} implementation using Pax Runner.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 09, 2008
 */
class PaxRunnerTestContainer
    implements TestContainer
{

    /**
     * Number of ports to check for a free rmi communication port.
     */
    private static final int AMOUNT_OF_PORTS_TO_CHECK = 100;
    /**
     * Default timeout in millis that will be taken while searching for remote bundle context via RMI.
     */
    private static final Integer DEFAULT_RMI_LOOKUP_TIMEOUT = 5000;

    /**
     * {@link JavaRunner} used to start up Pax Runner.
     */
    private final StoppableJavaRunner m_javaRunner;
    /**
     * Remote bundle context client.
     */
    private final RemoteBundleContextClient m_remoteBundleContextClient;

    /**
     * Constructor.
     *
     * @param javaRunner java runner to be used to start up Pax Runner.
     * @param options    user startup options
     */
    PaxRunnerTestContainer( final StoppableJavaRunner javaRunner,
                            final Option... options )
    {
        // TODO wrap maven bundle with a @update if a version is a snapshot
        m_remoteBundleContextClient = new RemoteBundleContextClient(
            findFreeCommunicationPort(),
            getRMILookupTimeout( options )
        );
        final Option[] expandedOptions = expand( combine( options, localOptions() ) );
        start( m_javaRunner = javaRunner, buildArguments( expandedOptions ) );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public <T> T getService( final Class<T> serviceType )
    {
        return m_remoteBundleContextClient.getService( serviceType );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public <T> T getService( final Class<T> serviceType,
                             final int timeoutInMillis )
    {
        return m_remoteBundleContextClient.getService( serviceType, timeoutInMillis );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public long installBundle( final String bundleUrl )
    {
        return m_remoteBundleContextClient.installBundle( bundleUrl );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public long installBundle( final String bundleLocation,
                               final byte[] bundle )
    {
        return m_remoteBundleContextClient.installBundle( bundleLocation, bundle );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public void startBundle( long bundleId )
        throws TestContainerException
    {
        m_remoteBundleContextClient.startBundle( bundleId );
    }

    /**
     * {@inheritDoc}
     */
    public void stop()
    {
        m_javaRunner.shutdown();
    }

    /**
     * Return the options required by this container implementation.
     *
     * @return local options
     */
    private Option[] localOptions()
    {
        return new Option[]{
            // remote bundle context bundle
            CoreOptions.mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam-container-rbc" )
                .version( Info.getPaxExamVersion() ),
            // rmi communication port
            CoreOptions.systemProperty( Constants.RMI_PORT_PROPERTY )
                .value( m_remoteBundleContextClient.getRmiPort().toString() )
        };
    }

    /**
     * Determine the rmi lookup timeout.<br/>
     * Timeout is dermined by first looking for a {@link TimeoutOption} in the user options. If not specified a default
     * {@link #DEFAULT_RMI_LOOKUP_TIMEOUT} is used.
     *
     * @param options user options
     *
     * @return rmi lookup timeout
     */
    private static Integer getRMILookupTimeout( final Option... options )
    {
        final TimeoutOption[] timeoutOptions = filter( TimeoutOption.class, options );
        if( timeoutOptions.length > 0 )
        {
            return timeoutOptions[ 0 ].getTimeout();
        }
        return DEFAULT_RMI_LOOKUP_TIMEOUT;
    }

    /**
     * Scanns ports for a free port to be used for RMI communication.
     *
     * @return found free port
     */
    public static Integer findFreeCommunicationPort()
    {
        return new FreePort( Registry.REGISTRY_PORT, Registry.REGISTRY_PORT + AMOUNT_OF_PORTS_TO_CHECK ).getPort();
    }

    @Override
    public String toString()
    {
        return "PaxRunnerTestContainer{}";
    }

}
