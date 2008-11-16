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
package org.ops4j.pax.exam.zombie;

import org.ops4j.pax.exam.api.TestExecutionException;
import org.ops4j.pax.exam.api.TestRunner;
import org.ops4j.pax.exam.zombie.internal.RemoteTestRunner;
import org.osgi.framework.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Dictionary;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 10, 2008
 */
public class RemoteTestRunnerDelegate
    implements RemoteTestRunner, Serializable
{

    private transient BundleContext m_bundleContext;
    private transient int i;

    public RemoteTestRunnerDelegate( BundleContext bundleContext )
    {
        m_bundleContext = bundleContext;
    }

    public String execute()
        throws RemoteException
    {
        startAllBundles();

        ServiceReference ref = m_bundleContext.getServiceReference( TestRunner.class.getName() );
        if( ref != null )
        {
            TestRunner s = ( TestRunner ) m_bundleContext.getService( ref );
            return s.execute();
        }
        else
        {
            throw new TestExecutionException( "TestRunner is not available inside the OSGi framework." );
        }
    }

    private void startAllBundles()
    {
        // first make sure all installed bundles are up
        Bundle[] bundles = m_bundleContext.getBundles();
        for( Bundle bundle : bundles )
        {
            startBundle( bundle );
        }
    }

    private void startBundle( Bundle bundle )
    {
        // Don't start if bundle already active
        int bundleState = bundle.getState();
        if( bundleState == Bundle.ACTIVE )
        {
            return;
        }

        // Don't start if bundle is a fragment bundle
        Dictionary bundleHeaders = bundle.getHeaders();
        if( bundleHeaders.get( Constants.FRAGMENT_HOST ) != null )
        {
            return;
        }

        // Start bundle
        try
        {
            bundle.start();

            bundleState = bundle.getState();
            if( bundleState != Bundle.ACTIVE )
            {
                long bundleId = bundle.getBundleId();
                String bundleName = bundle.getSymbolicName();
                String bundleStateStr = bundleStateToString( bundleState );
                throw new TestExecutionException(
                    "Bundle (" + bundleId + ", " + bundleName + ") not started (still " + bundleStateStr + ")"
                );
            }
        } catch( BundleException e )
        {
            throw new RuntimeException( e );
        }
    }

    private String bundleStateToString( int bundleState )
    {
        switch( bundleState )
        {
            case Bundle.ACTIVE:
                return "active";
            case Bundle.INSTALLED:
                return "installed";
            case Bundle.RESOLVED:
                return "resolved";
            case Bundle.STARTING:
                return "starting";
            case Bundle.STOPPING:
                return "stopping";
            case Bundle.UNINSTALLED:
                return "uninstalled";
            default:
                return "unknown (" + bundleState + ")";
        }
    }

    public void install( byte[] bundle )
        throws RemoteException
    {
        String bundleLocationIdentifier = "zombie" + ( i++ );
        ByteArrayInputStream inp = new ByteArrayInputStream( bundle );
        try
        {
            m_bundleContext.installBundle( bundleLocationIdentifier, inp );
        }
        catch( BundleException e )
        {
            throw new RemoteException( "cannot install bundle: " + e.getMessage(), e );
        }
        finally
        {
            try
            {
                inp.close();
            }
            catch( IOException e )
            {
                // ignore.
            }
        }
    }
}