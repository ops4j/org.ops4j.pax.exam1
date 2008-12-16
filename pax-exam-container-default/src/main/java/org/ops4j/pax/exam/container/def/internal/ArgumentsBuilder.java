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
package org.ops4j.pax.exam.container.def.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.OptionUtils.*;
import org.ops4j.pax.exam.container.def.options.ProfileOption;
import org.ops4j.pax.exam.container.def.options.VMOption;
import org.ops4j.pax.exam.options.BootDelegationOption;
import org.ops4j.pax.exam.options.FrameworkOption;
import org.ops4j.pax.exam.options.ProvisionOption;
import org.ops4j.pax.exam.options.SystemPackageOption;
import org.ops4j.pax.exam.options.SystemPropertyOption;

/**
 * TODO Add JavaDoc.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0 December 10, 2008
 */
class ArgumentsBuilder
{

    static String[] buildArguments( final Option... options )
    {
        final List<String> runnerOptions = new ArrayList<String>();

        add( runnerOptions, defaultArguments() );
        add( runnerOptions, extractArguments( filter( FrameworkOption.class, options ) ) );
        add( runnerOptions, extractArguments( filter( ProfileOption.class, options ) ) );
        add( runnerOptions, extractArguments( filter( BootDelegationOption.class, options ) ) );
        add( runnerOptions, extractArguments( filter( SystemPackageOption.class, options ) ) );
        add( runnerOptions, extractArguments( filter( ProvisionOption.class, options ) ) );
        add( runnerOptions,
             extractArguments(
                 filter( SystemPropertyOption.class, options ),
                 filter( VMOption.class, options )
             )
        );

        return runnerOptions.toArray( new String[runnerOptions.size()] );
    }

    private static void add( List<String> runnerOptions, Collection<String> options )
    {
        if( options != null && options.size() > 0 )
        {
            runnerOptions.addAll( options );
        }
    }

    private static void add( List<String> runnerOptions, String option )
    {
        if( option != null && option.trim().length() > 0 )
        {
            runnerOptions.add( option );
        }
    }

    private static Collection<String> defaultArguments()
    {
        final List<String> options = new ArrayList<String>();
        options.add( "--noConsole" );
        options.add( "--noDownloadFeedback" );
        options.add( "--noArgs" );
        options.add( "--workingDirectory=" + createWorkingDirectory().getAbsolutePath() );
        return options;
    }

    private static Collection<String> extractArguments( final FrameworkOption[] frameworks )
    {
        final List<String> options = new ArrayList<String>();
        if( frameworks.length > 1 )
        {
            throw new IllegalArgumentException( "Configuration cannot contain more then one platform" );
        }
        if( frameworks.length > 0 )
        {
            options.add( "--platform=" + frameworks[ 0 ].getName() );
            final String version = frameworks[ 0 ].getVersion();
            if( version != null && version.trim().length() > 0 )
            {
                options.add( "--version=" + version );
            }
        }
        return options;
    }

    private static Collection<String> extractArguments( final ProvisionOption[] bundles )
    {
        final List<String> options = new ArrayList<String>();
        for( ProvisionOption bundle : bundles )
        {
            options.add( bundle.getURL() );
        }
        return options;
    }

    private static String extractArguments( final ProfileOption[] profiles )
    {
        final StringBuilder option = new StringBuilder();
        if( profiles != null && profiles.length > 0 )
        {
            for( ProfileOption profile : profiles )
            {
                if( profile != null && profile.getName() != null && profile.getName().length() > 0 )
                {
                    if( option.length() == 0 )
                    {
                        option.append( "--profiles=" );
                    }
                    else
                    {
                        option.append( "," );
                    }
                    option.append( profile.getName() );
                }
            }
        }
        return option.toString();
    }

    private static String extractArguments( final BootDelegationOption[] packages )
    {
        final StringBuilder option = new StringBuilder();
        if( packages != null && packages.length > 0 )
        {
            for( BootDelegationOption pkg : packages )
            {
                if( pkg != null && pkg.getPackage() != null && pkg.getPackage().length() > 0 )
                {
                    if( option.length() == 0 )
                    {
                        option.append( "--bootDelegation=" );
                    }
                    else
                    {
                        option.append( "," );
                    }
                    option.append( pkg.getPackage() );
                }
            }
        }
        return option.toString();
    }

    private static String extractArguments( final SystemPackageOption[] packages )
    {
        final StringBuilder option = new StringBuilder();
        if( packages != null && packages.length > 0 )
        {
            for( SystemPackageOption pkg : packages )
            {
                if( pkg != null && pkg.getPackage() != null && pkg.getPackage().length() > 0 )
                {
                    if( option.length() == 0 )
                    {
                        option.append( "--systemPackages=" );
                    }
                    else
                    {
                        option.append( "," );
                    }
                    option.append( pkg.getPackage() );
                }
            }
        }
        return option.toString();
    }

    private static String extractArguments( final SystemPropertyOption[] systemProperties,
                                            final VMOption[] vmOptions )
    {
        final StringBuilder option = new StringBuilder();
        if( systemProperties != null && systemProperties.length > 0 )
        {
            for( SystemPropertyOption property : systemProperties )
            {
                if( property != null && property.getKey() != null && property.getKey().trim().length() > 0 )
                {
                    if( option.length() > 0 )
                    {
                        option.append( " " );
                    }
                    option.append( "-D" ).append( property.getKey() ).append( "=" ).append( property.getValue() );
                }
            }
        }
        if( vmOptions != null && vmOptions.length > 0 )
        {
            for( VMOption vmOption : vmOptions )
            {
                if( vmOption != null && vmOption.getOption() != null && vmOption.getOption().trim().length() > 0 )
                {
                    if( option.length() > 0 )
                    {
                        option.append( " " );
                    }
                    option.append( vmOption.getOption() );
                }
            }
        }
        if( option.length() > 0 )
        {
            option.insert( 0, "--vmOptions=" );
        }
        return option.toString();
    }

    public static File createWorkingDirectory()
    {

        String currentUser = "";
        currentUser = System.getProperty( "user.name" );
        File workDir = new File( System.getProperty( "java.io.tmpdir" ) + "/paxexam_runner_" + currentUser );
        // create if not existent:
        if( !workDir.exists() )
        {
            workDir.mkdirs();
        }
        return workDir;
    }
}
