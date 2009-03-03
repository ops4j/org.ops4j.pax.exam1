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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.net.FreePort;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.OptionUtils.*;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import static org.ops4j.pax.exam.container.def.internal.ArgumentsBuilder.*;
import org.ops4j.pax.exam.container.def.options.BundleScannerProvisionOption;
import org.ops4j.pax.exam.container.def.options.ScannerProvisionOption;
import org.ops4j.pax.exam.container.def.options.TimeoutOption;
import org.ops4j.pax.exam.options.ProvisionOption;
import org.ops4j.pax.exam.rbc.Constants;
import org.ops4j.pax.exam.rbc.client.RemoteBundleContextClient;
import org.ops4j.pax.exam.spi.container.TestContainer;
import org.ops4j.pax.exam.spi.container.TestContainerException;
import static org.ops4j.pax.runner.Run.*;
import org.ops4j.pax.runner.platform.DefaultJavaRunner;

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
     * JCL logger.
     */
    private static final Log LOG = LogFactory.getLog( PaxRunnerTestContainer.class );

    /**
     * Number of ports to check for a free rmi communication port.
     */
    private static final int AMOUNT_OF_PORTS_TO_CHECK = 100;
    /**
     * Default timeout in millis that will be taken while searching for remote bundle context via RMI.
     */
    private static final Integer DEFAULT_RMI_LOOKUP_TIMEOUT = 5000;

    /**
     * Remote bundle context client.
     */
    private final RemoteBundleContextClient m_remoteBundleContextClient;
    /**
     * Java runner to be used to start up Pax Runner.
     */
    private final DefaultJavaRunner m_javaRunner;

    /**
     * Constructor.
     *
     * @param javaRunner java runner to be used to start up Pax Runner
     * @param options    user startup options
     */
    PaxRunnerTestContainer( final DefaultJavaRunner javaRunner,
                            final Option... options )
    {
        LOG.info( "Starting up the test container (Pax Runner " + Info.getPaxRunnerVersion() + " )" );
        long startedAt = System.currentTimeMillis();
        m_javaRunner = javaRunner;
        m_remoteBundleContextClient = new RemoteBundleContextClient(
            findFreeCommunicationPort(),
            getRMILookupTimeout( options )
        );
        start( javaRunner, buildArguments( wrap( expand( combine( options, localOptions() ) ) ) ) );
        LOG.info(
            "Test container (Pax Runner " + Info.getPaxRunnerVersion() + ") started in "
            + ( System.currentTimeMillis() - startedAt ) + " millis"
        );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public <T> T getService( final Class<T> serviceType )
    {
        LOG.debug( "Lookup a [" + serviceType.getName() + "]" );
        return m_remoteBundleContextClient.getService( serviceType );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public <T> T getService( final Class<T> serviceType,
                             final int timeoutInMillis )
    {
        LOG.debug( "Lookup a [" + serviceType.getName() + "]" );
        return m_remoteBundleContextClient.getService( serviceType, timeoutInMillis );
    }

    /**
     * {@inheritDoc}
     * Delegates to {@link RemoteBundleContextClient}.
     */
    public long installBundle( final String bundleUrl )
    {
        LOG.debug( "Installing bundle [" + bundleUrl + "] .." );
        long id = m_remoteBundleContextClient.installBundle( bundleUrl );
        LOG.debug( "Installed bundle " + bundleUrl + " as ID: " + id );
        return id;
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
        LOG.info( "Shutting down the test container (Pax Runner)" );
        m_remoteBundleContextClient.stop();
        m_javaRunner.waitForExit();
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
            mavenBundle()
                .groupId( "org.ops4j.pax.exam" )
                .artifactId( "pax-exam-container-rbc" )
                .version( Info.getPaxExamVersion() )
                .update( Info.isPaxExamSnapshotVersion() ),
            // rmi communication port
            systemProperty( Constants.RMI_PORT_PROPERTY )
                .value( m_remoteBundleContextClient.getRmiPort().toString() ),
            // boot delegation for sun.*. This seems only necessary in Knopflerfish version > 2.0.0
            bootDelegationPackage( "sun.*" )
        };
    }

    /**
     * Wrap provision options that are not already scanner provision bundles with a {@link BundleScannerProvisionOption}
     * in order to force update.
     *
     * @param options options to be wrapped (can be null or an empty array)
     *
     * @return eventual wrapped bundles
     */
    private Option[] wrap( final Option... options )
    {
        if( options != null && options.length > 0 )
        {
            // get provison options out of options
            final ProvisionOption[] provisionOptions = filter( ProvisionOption.class, options );
            if( provisionOptions != null && provisionOptions.length > 0 )
            {
                final List<Option> processed = new ArrayList<Option>();
                for( final ProvisionOption provisionOption : provisionOptions )
                {
                    if( !( provisionOption instanceof ScannerProvisionOption ) )
                    {
                        // if is not a scanner the wrap as scanner and force update
                        processed.add( scanBundle( provisionOption ) );
                    }
                    else
                    {
                        processed.add( provisionOption );
                    }
                }
                // finally combine the processed provision options with the original options
                // (where provison options are removed)
                return combine(
                    remove( ProvisionOption.class, options ),
                    processed.toArray( new Option[processed.size()] )
                );
            }
        }
        // if there is nothing to process of there are no provision options just return the original options
        return options;
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
