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
package org.ops4j.pax.exam.connector.paxrunner.internal;

import org.junit.Test;

import java.io.File;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 18, 2008
 *
 *        TODO The implementation spawns a new process. add checks for that later
 */
public class PaxRunnerInstanceImplTest
{

    @Test( expected = IllegalArgumentException.class )
    public void createWithNull()
    {
        new PaxRunnerInstanceImpl( null, null, 0 );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithFirstNull()
    {
        new PaxRunnerInstanceImpl( new String[0], null, 0 );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithLastNull()
    {
        new PaxRunnerInstanceImpl( null, new File( "" ), 0 );
    }

    @Test
    public void create()
    {
        PaxRunnerInstanceImpl impl = new PaxRunnerInstanceImpl( new String[0], new File( "" ), 0 );
    }
}
