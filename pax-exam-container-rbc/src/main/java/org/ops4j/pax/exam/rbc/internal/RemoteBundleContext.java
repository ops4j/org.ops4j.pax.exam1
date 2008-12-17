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
package org.ops4j.pax.exam.rbc.internal;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.osgi.framework.BundleException;

/**
 * TODO Add JavaDoc.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 10, 2008
 */
public interface RemoteBundleContext
    extends Remote
{

    Object remoteCall( Class<?> serviceType,
                       String methodName,
                       Class<?>[] methodParams,
                       int timeoutInMillis,
                       Object... actualParams )
        throws
        RemoteException,
        NoSuchServiceException,
        NoSuchMethodException,
        IllegalAccessException,
        InvocationTargetException;

    long installBundle( String bundleUrl )
        throws RemoteException, BundleException;

    long installBundle( String bundleLocation, byte[] bundle )
        throws RemoteException, BundleException;

    void startBundle( long bundleId )
        throws RemoteException, BundleException;

    void stopBundle( long bundleId )
        throws RemoteException, BundleException;

}
