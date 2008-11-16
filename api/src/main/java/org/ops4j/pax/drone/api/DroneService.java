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

package org.ops4j.pax.drone.api;

/**
 * This is the service that will be installed by drone-service in your target environment.
 * DroneConnector.execute(recipe) will result in DroneService.execute(recipe) calls in the running
 * OSGi framework.
 * This Service is being exposed for remote invocation if necessary. (remote osgi frameworks)
 *
 * @author Toni Menzel (tonit)
 * @since May 29, 2008
 */
public interface DroneService
{

    final String PROPERTY_COMMUNICATION_PORT = "drone.communication.port";
    final String DRONE_RECIPE_HOST_HEADER = "Drone-RecipeHost";
    final String DRONE_RECIPE_CODE_HEADER = "Drone-RecipeCode";
    final String DRONE_SYMBOLICNAME = "pax-drone-probe";

    /**
     * Triggers whatever the drone implementation is about.
     * Currenlty its about invoking the in-container integration tests.
     * But this is just one use-case.
     *
     * @throws RecipeException if the recipe code throws an exception, it is being wrapped into a (unchecked) RecipeException
     */
    String execute();
}
