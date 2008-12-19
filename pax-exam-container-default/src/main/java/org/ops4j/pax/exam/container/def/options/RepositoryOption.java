/*
 * Copyright 2008 Toni Menzel.
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

import org.ops4j.pax.exam.Option;

/**
 * The option specifiying pax runners repository option.
 *
 * @author Toni Menzel (tonit)
 * @since Dec 19, 2008
 */
public interface RepositoryOption extends Option
{

    /**
     * \@snapshots flag of repositories entry
     *
     * @return this for fluent api
     */
    public RepositoryOption allowSnapshots();

    /**
     * \@noreleases flag of repositories entry
     *
     * @return this for fluent api
     */
    public RepositoryOption disableReleases();

}
