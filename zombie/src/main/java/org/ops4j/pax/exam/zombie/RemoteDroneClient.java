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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.ops4j.pax.exam.api.DroneException;
import org.ops4j.pax.exam.api.DroneService;
import org.ops4j.pax.exam.zombie.internal.RemoteDroneService;

/**
 * This is a DroneService Proxy that access a remote DroneService via RMI
 *
 * @author Toni Menzel (tonit)
 * @since Jun 2, 2008
 */
public class RemoteDroneClient
    implements DroneService
{

    private int m_port;

    public RemoteDroneClient( int port )
    {
        m_port = port;
    }

    public String execute()

    {
        try
        {
            return getDroneService().execute();

        } catch( RemoteException e )
        {
            // we cannot do much more, just pack it into a DroneException and be fine
            throw new DroneException( "Derived from Remote by calling recipe ", e );
        }
    }

    public void install( InputStream inp )
    {
        try
        {
            // transform inputstream into bytearray for rmi transfer
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            copy( inp, out );
            RemoteDroneService s = getDroneService();
            s.install( out.toByteArray() );
        }
        catch( RemoteException e )
        {
            throw new RuntimeException( e );
        }
        catch( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    private static void copy( InputStream in, OutputStream out )
        throws IOException
    {
        byte[] buffer = new byte[1024];
        int b;
        while( ( b = in.read( buffer ) ) != -1 )
        {
            out.write( buffer, 0, b );
        }
    }

    private RemoteDroneService getDroneService()
    {
        try
        {
            Thread.currentThread().setContextClassLoader( this.getClass().getClassLoader() ); //!! Absolutely nececary for RMIClassLoading to work

            Registry registry = LocateRegistry.getRegistry( m_port );
            RemoteDroneService stub = (RemoteDroneService) registry.lookup( DroneService.class.getName() );
            return stub;
        } catch( AccessException e )
        {
            throw new DroneException( "Problem accessing the rmi registry for stub: " + DroneService.class.getName(), e );
        } catch( NotBoundException e )
        {
            throw new DroneException( "Problem accessing the rmi registry for stub: " + DroneService.class.getName(), e );
        } catch( RemoteException e )
        {
            throw new DroneException( "Problem accessing the rmi registry for stub: " + DroneService.class.getName(), e );
        }
    }
}
