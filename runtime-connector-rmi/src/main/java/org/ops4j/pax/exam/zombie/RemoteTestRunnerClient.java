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
import org.ops4j.pax.exam.zombie.RemoteTestRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This is a [@link TestRunner} Proxy that access a remote [@link TestRunner} via RMI
 *
 * @author Toni Menzel (tonit)
 * @since Jun 2, 2008
 */
public class RemoteTestRunnerClient
    implements TestRunner
{

    private int m_port;

    public RemoteTestRunnerClient( int port )
    {
        m_port = port;
    }

    public String execute()

    {
        try
        {
            return getTestRunner().execute();

        } catch( RemoteException e )
        {
            // we cannot do much more, just pack it into a TestExecutionException and be fine
            throw new TestExecutionException( "Derived from Remote by calling recipe ", e );
        }
    }

    public void install( InputStream inp )
    {
        try
        {
            // transform inputstream into bytearray for rmi transfer
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            copy( inp, out );
            RemoteTestRunner s = getTestRunner();
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

    private RemoteTestRunner getTestRunner()
    {
        try
        {
            Thread.currentThread().setContextClassLoader( this.getClass().getClassLoader()
            ); //!! Absolutely nececary for RMIClassLoading to work

            Registry registry = LocateRegistry.getRegistry( m_port );
            RemoteTestRunner stub = ( RemoteTestRunner ) registry.lookup( TestRunner.class.getName() );
            return stub;
        } catch( AccessException e )
        {
            throw new TestExecutionException(
                "Problem accessing the rmi registry for stub: " + TestRunner.class.getName(), e
            );
        } catch( NotBoundException e )
        {
            throw new TestExecutionException(
                "Problem accessing the rmi registry for stub: " + TestRunner.class.getName(), e
            );
        } catch( RemoteException e )
        {
            throw new TestExecutionException(
                "Problem accessing the rmi registry for stub: " + TestRunner.class.getName(), e
            );
        }
    }
}
