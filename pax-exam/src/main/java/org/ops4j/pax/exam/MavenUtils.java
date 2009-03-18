/*
 * Copyright 2009 Alin Dreghiciu.
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
package org.ops4j.pax.exam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.ops4j.pax.exam.options.MavenUrlProvisionOption;

/**
 * Utility methods related to Apache Maven.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.1, March 09, 2009
 */
public class MavenUtils
{

    /**
     * Utility class. Ment to be used via the static factory methods.
     */
    private MavenUtils()
    {
        // utility class
    }

    public static String getArtifactVersion( final String groupId,
                                             final String artifactId )
    {
        final Properties dependencies = new Properties();
        try
        {
            dependencies.load(
                new FileInputStream( getFileFromClasspath( "META-INF/maven/dependencies.properties" ) )
            );
            final String version = dependencies.getProperty( groupId + "/" + artifactId + "/version" );
            if( version == null )
            {
                throw new RuntimeException(
                    "Could not detrmine version. Do you have a dependency for " + groupId + "/" + artifactId
                    + " in your maven project?"
                );
            }
            return version;
        }
        catch( IOException e )
        {
            throw new RuntimeException(
                "Could not detrmine version. Did you configured the plugin in your maven project?"
                + "Or maybe you did not run the maven build and you are using an IDE?"
            );
        }
    }

    /**
     * Locates the paxexam properties file and runs resolver with properties loaded.
     * 
     * @param resolver implementation that can recognize parts from properties defined in config file.
     * @return Fully resolved properties.
     */
    public static Option[] parseOptionsFromProperties( final OptionResolver resolver )
    {
        final Properties properties = new Properties();
        try
        {
            properties.load(
                new FileInputStream( getFileFromClasspath( "META-INF/maven/paxexam-config.properties" ) )
            );

            return resolver.getOptionsFromProperties( properties );
        }
        catch( IOException e )
        {
            throw new RuntimeException(
                "Could not read properties file. Did you configured the plugin in your maven project?"
                + "Or maybe you did not run the maven build and you are using an IDE?"
            );
        }
    }

    public static MavenUrlProvisionOption.VersionResolver asInProject()
    {
        return new MavenUrlProvisionOption.VersionResolver()
        {

            /**
             * {@inheritDoc}
             */
            public String getVersion( final String groupId,
                                      final String artifactId )
            {
                return getArtifactVersion( groupId, artifactId );
            }

        };
    }

    /**
     * Searches the classpath for the file denoted by the file path and returns the corresponding file.
     *
     * @param filePath path to the file
     *
     * @return a file corresponding to the path
     *
     * @throws java.io.FileNotFoundException if the file cannot be found
     */
    private static File getFileFromClasspath( final String filePath )
        throws FileNotFoundException
    {
        try
        {
            URL fileURL = MavenUtils.class.getClassLoader().getResource( filePath );
            if( fileURL == null )
            {
                throw new FileNotFoundException( "File [" + filePath + "] could not be found in classpath" );
            }
            return new File( fileURL.toURI() );
        }
        catch( URISyntaxException e )
        {
            throw new FileNotFoundException( "File [" + filePath + "] could not be found: " + e.getMessage() );
        }
    }

}