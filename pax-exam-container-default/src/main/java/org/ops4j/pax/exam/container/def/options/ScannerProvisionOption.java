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
package org.ops4j.pax.exam.container.def.options;

import org.ops4j.pax.exam.options.ProvisionOption;

/**
 * Option specifying a provisioning from a Pax Runner scanner.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 08, 2008
 */
public interface ScannerProvisionOption
    extends ProvisionOption
{

    /**
     * If the bundles provisioned by this scanner should be updated (re-downloaded).
     *
     * @return true if the bundles should be updated, false otherwise.
     *         If the returned value is null, default of Pax Runner will be used
     */
    Boolean shouldUpdate();

    /**
     * If the bundles provisioned by this scanner should be started.
     *
     * @return true if the bundles should be started, false otherwise.
     *         If the returned value is null, default of Pax Runner will be used
     */
    Boolean shouldStart();

    /**
     * The start level for the bundles provisioned by this scanner
     *
     * @return start level. If the returned value is null, default of Pax Runner will be used
     */
    Integer startLevel();

}