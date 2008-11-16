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
package org.ops4j.pax.exam.api;

import java.io.InputStream;

/**
 * Delegates the actual build action until "build" is called. (dereferred)
 *
 * This produces a test drone.
 * Implementations should cache results between build calls to optimize performance for bulk tests.
 *
 * @author Toni Menzel (tonit)
 * @since May 29, 2008
 */
public interface DroneProvider
{

    /**
     * Bundle that should be installable and startable.
     * It must contain the following headers in addition to the osgi mandatory/recommended ones:
     * Drone-RecipeHost: Class that contains the code to be executed as a "test"
     * Drone-RecipeCode: Method or identifier to be run inside the osgi framework.
     *
     * The bundle can be derived by static references (file, url) as well as being built on demand.
     */
    public InputStream build();
}
