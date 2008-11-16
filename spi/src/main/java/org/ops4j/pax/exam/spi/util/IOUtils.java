/*
 * Copyright 2008 Toni Menzel
 * Copyright 2008 Alin Dreghiciu
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
package org.ops4j.pax.exam.spi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO related utilities.
 *
 * @author Toni Menzel (tonit)
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 16, 2008
 */
public class IOUtils
{

    /**
     * Utility class. Ment to be used using static methods
     */
    private IOUtils()
    {
        // utility class
    }

    /**
     * Copy the content of an input stream to an output stream
     *
     * @param in  source; cannot be null
     * @param out destination; cannot be null
     *
     * @throws IOException - Re-thrown
     */
    public static void copy( InputStream in, OutputStream out )
        throws IOException
    {
        byte[] buffer = new byte[1024];
        int b;
        while( ( b = in.read( buffer ) ) != -1 )
        {
            out.write( buffer, 0, b );
        }
    }

}