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

import java.io.File;

/**
 * A (possibly shared) of information used by a pax drone connector instance.
 * Implementations should be thread-safe.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 10, 2008
 */
public interface RunnerContext
{

    /**
     * a port where system communication can happen in case of remote connectors.
     *
     * @return depending who calls it can be initially a free port (to be taken) or the port where a client can
     *         reach its server counterpart.
     */
    int getCommunicationPort();

    /**
     * The directory being used to cache stuff on disk.
     * For PaxRunner Connector this is his working directory (norrmally its "runner")
     */
    File getWorkingDirectory();

    
}
