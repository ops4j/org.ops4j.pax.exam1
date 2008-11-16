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
package org.ops4j.pax.exam.connector.paxrunner;

import org.ops4j.pax.exam.api.BundleProvision;
import org.ops4j.pax.exam.api.RunnerContext;
import org.ops4j.pax.exam.connector.paxrunner.internal.PaxRunnerBundleProvisionImpl;
import org.ops4j.pax.exam.connector.paxrunner.internal.PaxRunnerConnectorImpl;
import org.ops4j.pax.drone.spi.intern.RunnerContextImpl;

/**
 * The utility to create and configure connectors based on paxrunner connector.
 *
 * Use static import like this:
 * import static org.ops4j.pax.drone.connector.paxrunner.GenericConnector.*;
 *
 * To get a short but very powerfull api inside your testcase.
 * The use its methods to create and configure your connector.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 23, 2008
 */
public class GenericConnector
{

    public static PaxRunnerConnector create()
    {
        return new PaxRunnerConnectorImpl( new RunnerContextImpl(), new PaxRunnerBundleProvisionImpl() );
    }

    public static PaxRunnerConnector create( BundleProvision provision )
    {
        return new PaxRunnerConnectorImpl( new RunnerContextImpl(), provision );
    }

    public static PaxRunnerConnector create( RunnerContext context )
    {
        return new PaxRunnerConnectorImpl( context, new PaxRunnerBundleProvisionImpl() );
    }

    public static PaxRunnerConnector create( RunnerContext context, BundleProvision provision )
    {
        return new PaxRunnerConnectorImpl( context, provision );
    }

    public static RunnerContext createRunnerContext()
    {
        return new RunnerContextImpl();
    }

    public static PaxRunnerBundleProvision createBundleProvision()
    {
        return new PaxRunnerBundleProvisionImpl();
    }
}
