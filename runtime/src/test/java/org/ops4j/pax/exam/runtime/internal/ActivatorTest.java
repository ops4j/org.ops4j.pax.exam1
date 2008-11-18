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
package org.ops4j.pax.exam.runtime.internal;

import java.util.Dictionary;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.ops4j.pax.exam.api.TestRunner;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 20, 2008
 */
public class ActivatorTest
{

    @Test
    public void start()
        throws Exception
    {
        Activator act = new Activator();
        BundleContext bundleContext = createMock( BundleContext.class );
        expect( bundleContext.registerService( eq( TestRunner.class.getName() ), notNull(), (Dictionary) notNull() ) )
            .andReturn( createMock( ServiceRegistration.class ) );
        replay( bundleContext );
        act.start( bundleContext );
        verify( bundleContext );
    }

    @Test
    public void stop()
        throws Exception
    {
        Activator act = new Activator();
        BundleContext bundleContext = createMock( BundleContext.class );
        ServiceRegistration reg = createMock( ServiceRegistration.class );
        expect( bundleContext.registerService( eq( TestRunner.class.getName() ), notNull(), (Dictionary) notNull() ) )
            .andReturn( reg );
        replay( bundleContext, reg );
        act.start( bundleContext );
        act.stop( bundleContext );
        verify( bundleContext, reg );
    }
}
