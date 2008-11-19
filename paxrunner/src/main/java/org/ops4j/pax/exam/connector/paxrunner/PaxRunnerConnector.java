/*
 * Copyright 2008 Toni Menzel.
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
package org.ops4j.pax.exam.connector.paxrunner;

import org.ops4j.pax.exam.api.TestRunnerConnector;

/**
 * This covers the connector that will execute a pax runner process.
 * As well as its configuration.
 * You can get an implementation of this type via PaxRunnerConnectorFactory.
 *
 * It is recommended that methods should return @this for fluid api usage.
 *
 * @author Toni Menzel
 * @author Alin Dreghiciu
 * @since 0.1.0, June 17, 2008
 */
public interface PaxRunnerConnector extends TestRunnerConnector
{

    /**
     * User setting. Here the target platform can be set.
     *
     * @param p one of Platforms (enumeration)
     *
     * @return itself for fluid api usage
     */
    PaxRunnerConnector setPlatform( Platforms p );

    /**
     * Sets the version of the framework to be used.
     *
     * @param version framework version
     *
     * @return itself for fluid api usage
     */
    PaxRunnerConnector setVersion( String version );

    /**
     * Adds a boot delegation package.
     *
     * @param pkg package for which framework should delegate to boot classpath
     *
     * @return itself, for fluid api usage
     */
    PaxRunnerConnector addBootDelegationFor( String pkg );

    /**
     * Adds a system package.
     *
     * @param pkg package exported by the system bundle
     *
     * @return itself, for fluid api usage
     */
    PaxRunnerConnector addSystemPackage( String pkg );

    /**
     * Adds a virtual machine option.
     *
     * @param option virtual machine option
     *
     * @return itself, for fluid api usage
     */
    PaxRunnerConnector addVMOption( String option );

    /**
     * Adds Pax Runner raw options = as supported by pax runner command line.
     * This should be used as a last resort, if the option cannot be set using dedicated API.
     *
     * @param options command line options
     *
     * @return itself, for fluid api usage
     */
    PaxRunnerConnector addRawOptions( String options );

    // String[] getOptions();

    //TestProbeBuilder getBuilder();

    // Set<String> getBundles();

    TestRunnerConnector setStartupTimeout( int i );
}
