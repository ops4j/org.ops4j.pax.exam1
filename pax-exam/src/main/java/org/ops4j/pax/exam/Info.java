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
package org.ops4j.pax.exam;

import java.io.InputStream;
import java.util.Properties;

/**
 * A helper class to find versioning and other meta information about this pax exam delivery.
 *
 * Fully static
 *
 * @author Toni Menzel (tonit)
 * @since Jul 25, 2008
 */
public class Info
{

    /**
     * No instances should be made (does not make sense).
     */
    private Info()
    {

    }

    /**
     * Discovers the Pax Exam version. If version cannot be determined returns an empty string.
     *
     * @return pax exam version
     */
    public static String getPaxExamVersion()
    {
        try
        {
            final InputStream is = Info.class.getClassLoader().getResourceAsStream( "META-INF/paxexam.version" );
            if( is != null )
            {
                final Properties properties = new Properties();
                properties.load( is );
                final String version = properties.getProperty( "version" );
                if( version != null )
                {
                    return version.trim();
                }
                return "";
            }
            return "";
        }
        catch( Exception ignore )
        {
            return "";
        }
    }

    /**
     * Display ops4j logo to console.
     */
    public static void showLogo()
    {
        System.out.println( "    ______  ________  __  __" );
        System.out.println( "   / __  / /  __   / / / / /" );
        System.out.println( "  /  ___/ /  __   / _\\ \\ _/" );
        System.out.println( " /  /    /  / /  / / _\\ \\" );
        System.out.println( "/__/    /__/ /__/ /_/ /_/" );
        System.out.println();
        final String logo = "Pax Exam " + Info.getPaxExamVersion() + " from OPS4J - http://www.ops4j.org";
        System.out.println( logo );
        System.out.println(
            "---------------------------------------------------------------------------------------------------------"
                .substring( 0, logo.length() )
        );
        System.out.println();
    }
}
