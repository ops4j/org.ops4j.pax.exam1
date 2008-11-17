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
package org.ops4j.pax.exam.zombie.internal;

import org.ops4j.pax.exam.api.TestRunner;
import org.ops4j.pax.exam.zombie.RemoteTestRunner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Registers the an instance of RemoteTestRunnerService as RMI service using a port set by system property
 * pax.exam.communication.port.
 *
 * @author Toni Menzel (tonit)
 * @since Jun 2, 2008
 */
public class Activator implements BundleActivator
{

    private Registry m_registry;
    private RemoteTestRunner stub;
    private RemoteTestRunnerDelegate obj;

    public void start( BundleContext bundleContext )
        throws Exception
    {
        ClassLoader cl = null;
        try
        {
            // create new instance
            obj = new RemoteTestRunnerDelegate( bundleContext );
            cl = Thread.currentThread().getContextClassLoader();
            // try to find port from property
            int port = getPort();

            //!! Absolutely nececary for RMIClassLoading to work
            Thread.currentThread().setContextClassLoader( this.getClass().getClassLoader() );
            m_registry = java.rmi.registry.LocateRegistry.createRegistry( port );

            stub = ( RemoteTestRunner ) UnicastRemoteObject.exportObject( obj, port );
            m_registry.bind( TestRunner.class.getName(), stub );
        } catch( Exception e )
        {
            throw new BundleException( "Problem setting up rmi registry", e );
        } finally
        {
            // reset classloader:
            if( cl != null )
            {
                Thread.currentThread().setContextClassLoader( cl );
            }
        }
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        UnicastRemoteObject.unexportObject( m_registry, true );
        this.m_registry = null;
        System.gc(); // this is necessary, unfortunately.. RMI wouldn' stop otherwise
    }

    /**
     * @return the port where {@link RemoteTestRunner} is being exposed as an RMI service.
     */
    private int getPort()
    {
        /**
         * The port is usually given by starting client (owner of this process).
         */
        return Integer.parseInt( System.getProperty( TestRunner.PROPERTY_COMMUNICATION_PORT ) );
    }
}
