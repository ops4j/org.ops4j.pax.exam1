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
package org.ops4j.pax.exam.spi.container;

/**
 * Management of an OSGi framework that can be used as a integration test container.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 09, 2008
 */
public interface TestContainer
{

    final int NO_WAIT = 0;

    <T> T getService( Class<T> serviceType )
        throws TestContainerException;

    <T> T getService( Class<T> serviceType, int timeoutInMillis )
        throws TestContainerException;

    long installBundle( String bundleUrl )
        throws TestContainerException;

    long installBundle( String bundleLocation, byte[] bundle )
        throws TestContainerException;

    void startBundle( long bundleId )
        throws TestContainerException;

    /**
     * Sets the start level for a bundle.
     *
     * @param bundleId   bundle id
     * @param startLevel start level
     *
     * @throws TestContainerException if startlevel cannot be set
     */
    void setBundleStartLevel( long bundleId, int startLevel )
        throws TestContainerException;

    /**
     * Stops the osgi container.
     */
    void stop();

}
