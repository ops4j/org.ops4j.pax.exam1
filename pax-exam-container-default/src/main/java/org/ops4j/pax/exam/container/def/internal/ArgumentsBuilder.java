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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.OptionUtils.*;
import org.ops4j.pax.exam.container.def.options.AutoWrapOption;
import org.ops4j.pax.exam.container.def.options.CleanCachesOption;
import org.ops4j.pax.exam.container.def.options.ExcludeDefaultRepositoriesOption;
import org.ops4j.pax.exam.container.def.options.LocalRepositoryOption;
import org.ops4j.pax.exam.container.def.options.ProfileOption;
import org.ops4j.pax.exam.container.def.options.RawPaxRunnerOptionOption;
import org.ops4j.pax.exam.container.def.options.RepositoryOptionImpl;
import org.ops4j.pax.exam.container.def.options.VMOption;
import org.ops4j.pax.exam.options.BootDelegationOption;
import org.ops4j.pax.exam.options.FrameworkOption;
import org.ops4j.pax.exam.options.MavenPluginGeneratedConfigOption;
import org.ops4j.pax.exam.options.ProvisionOption;
import org.ops4j.pax.exam.options.SystemPackageOption;
import org.ops4j.pax.exam.options.SystemPropertyOption;

/**
 * Utility methods for converting configuration options to Pax Runner arguments.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0 December 10, 2008
 */
class ArgumentsBuilder
{

    /**
     * Controls if one of the options set a Args Option manually.
     * Otherwise, defaultArguments will include a --noArgs flag to prevent
     * unintentional runner.args files being picked up by paxrunner.
     */
    private static boolean argsSetManually = false;

    /**
     * Utility class. Ment to be used via the static methods.
     */
    private ArgumentsBuilder()
    {
        // utility class
    }

    /**
     * Converts configuration options to Pax Runner arguments.
     *
     * @param options array of configuration options
     *
     * @return Pax Runner arguments
     */
    static String[] buildArguments( final Option... options )
    {
        final List<String> arguments = new ArrayList<String>();

        add( arguments, extractArguments( filter( MavenPluginGeneratedConfigOption.class, options ) ) );
        add( arguments, defaultArguments() );
        add( arguments, extractArguments( filter( FrameworkOption.class, options ) ) );
        add( arguments, extractArguments( filter( ProfileOption.class, options ) ) );
        add( arguments, extractArguments( filter( BootDelegationOption.class, options ) ) );
        add( arguments, extractArguments( filter( SystemPackageOption.class, options ) ) );
        add( arguments, extractArguments( filter( ProvisionOption.class, options ) ) );
        add( arguments,
             extractArguments(
                 filter( RepositoryOptionImpl.class, options ),
                 filter( ExcludeDefaultRepositoriesOption.class, options )
             )
        );
        add( arguments, extractArguments( filter( AutoWrapOption.class, options ) ) );
        add( arguments, extractArguments( filter( CleanCachesOption.class, options ) ) );
        add( arguments, extractArguments( filter( LocalRepositoryOption.class, options ) ) );
        add( arguments, extractArguments( filter( RawPaxRunnerOptionOption.class, options ) ) );
        add( arguments,
             extractArguments(
                 filter( SystemPropertyOption.class, options ),
                 filter( VMOption.class, options )
             )
        );

        return arguments.toArray( new String[arguments.size()] );
    }

    /**
     * Adds a collection of arguments to a list of arguments by skipping null arguments.
     *
     * @param arguments      list to which the arguments should be added
     * @param argumentsToAdd arguments to be added (can be null or empty)
     */
    private static void add( final List<String> arguments,
                             final Collection<String> argumentsToAdd )
    {
        if( argumentsToAdd != null && argumentsToAdd.size() > 0 )
        {
            arguments.addAll( argumentsToAdd );
        }
    }

    /**
     * Adds an argumentto a list of arguments by skipping null or empty arguments.
     *
     * @param arguments list to which the arguments should be added
     * @param argument  argument to be added (can be null or empty)
     */
    private static void add( final List<String> arguments,
                             final String argument )
    {
        if( argument != null && argument.trim().length() > 0 )
        {
            arguments.add( argument );
        }
    }

    /**
     * Returns a collection of default Pax Runner arguments.
     *
     * @return collection of default arguments
     */
    private static Collection<String> defaultArguments()
    {
        final List<String> arguments = new ArrayList<String>();
        arguments.add( "--noConsole" );
        arguments.add( "--noDownloadFeedback" );
        if( !argsSetManually )
        {
            arguments.add( "--noArgs" );
        }
        arguments.add( "--workingDirectory=" + createWorkingDirectory().getAbsolutePath() );
        return arguments;
    }

    /**
     * Converts framework options into corresponding arguments (--platform, --version).
     *
     * @param frameworks framework options
     *
     * @return converted Pax Runner collection of arguments
     *
     * @throws IllegalArgumentException - If there are more then one framework options
     */
    private static Collection<String> extractArguments( final FrameworkOption[] frameworks )
    {
        final List<String> arguments = new ArrayList<String>();
        if( frameworks.length > 1 )
        {
            throw new IllegalArgumentException( "Configuration cannot contain more then one platform" );
        }
        if( frameworks.length > 0 )
        {
            arguments.add( "--platform=" + frameworks[ 0 ].getName() );
            final String version = frameworks[ 0 ].getVersion();
            if( version != null && version.trim().length() > 0 )
            {
                arguments.add( "--version=" + version );
            }
        }
        return arguments;
    }

    /**
     * @return all arguments that have been recognized by OptionResolvers as PaxRunner arguments
     */
    private static Collection<String> extractArguments(
        MavenPluginGeneratedConfigOption[] mavenPluginGeneratedConfigOption )
    {
        final List<String> arguments = new ArrayList<String>();
        for( MavenPluginGeneratedConfigOption arg : mavenPluginGeneratedConfigOption )
        {
            URL url = arg.getURL();
            arguments.add( "--args=" + url.toExternalForm() );
        }
        argsSetManually = true;
        return arguments;
    }

    /**
     * Converts provision options into corresponding arguments (provision urls).
     *
     * @param bundles provision options
     *
     * @return converted Pax Runner collection of arguments
     */
    private static Collection<String> extractArguments( final ProvisionOption[] bundles )
    {
        final List<String> arguments = new ArrayList<String>();
        for( ProvisionOption bundle : bundles )
        {
            arguments.add( bundle.getURL() );
        }
        return arguments;
    }

    /**
     * Converts profile options into corresponding arguments (--profiles).
     *
     * @param profiles profile options
     *
     * @return converted Pax Runner collection of arguments
     */
    private static String extractArguments( final ProfileOption[] profiles )
    {
        final StringBuilder argument = new StringBuilder();
        if( profiles != null && profiles.length > 0 )
        {
            for( ProfileOption profile : profiles )
            {
                if( profile != null && profile.getProfile() != null && profile.getProfile().length() > 0 )
                {
                    if( argument.length() == 0 )
                    {
                        argument.append( "--profiles=" );
                    }
                    else
                    {
                        argument.append( "," );
                    }
                    argument.append( profile.getProfile() );
                }
            }
        }
        return argument.toString();
    }

    /**
     * Converts boot delegation packages options into corresponding arguments (--bootDelegation).
     *
     * @param packages boot delegation package options
     *
     * @return converted Pax Runner collection of arguments
     */
    private static String extractArguments( final BootDelegationOption[] packages )
    {
        final StringBuilder argument = new StringBuilder();
        if( packages != null && packages.length > 0 )
        {
            for( BootDelegationOption pkg : packages )
            {
                if( pkg != null && pkg.getPackage() != null && pkg.getPackage().length() > 0 )
                {
                    if( argument.length() == 0 )
                    {
                        argument.append( "--bootDelegation=" );
                    }
                    else
                    {
                        argument.append( "," );
                    }
                    argument.append( pkg.getPackage() );
                }
            }
        }
        return argument.toString();
    }

    /**
     * Converts system package options into corresponding arguments (--systemPackages).
     *
     * @param packages system package options
     *
     * @return converted Pax Runner collection of arguments
     */
    private static String extractArguments( final SystemPackageOption[] packages )
    {
        final StringBuilder argument = new StringBuilder();
        if( packages != null && packages.length > 0 )
        {
            for( SystemPackageOption pkg : packages )
            {
                if( pkg != null && pkg.getPackage() != null && pkg.getPackage().length() > 0 )
                {
                    if( argument.length() == 0 )
                    {
                        argument.append( "--systemPackages=" );
                    }
                    else
                    {
                        argument.append( "," );
                    }
                    argument.append( pkg.getPackage() );
                }
            }
        }
        return argument.toString();
    }

    /**
     * Converts system properties and vm options into corresponding arguments (--vmOptions).
     *
     * @param systemProperties system property options
     * @param vmOptions        virtual machine options
     *
     * @return converted Pax Runner argument
     */
    private static String extractArguments( final SystemPropertyOption[] systemProperties,
                                            final VMOption[] vmOptions )
    {
        final StringBuilder argument = new StringBuilder();
        if( systemProperties != null && systemProperties.length > 0 )
        {
            for( SystemPropertyOption property : systemProperties )
            {
                if( property != null && property.getKey() != null && property.getKey().trim().length() > 0 )
                {
                    if( argument.length() > 0 )
                    {
                        argument.append( " " );
                    }
                    argument.append( "-D" ).append( property.getKey() ).append( "=" ).append( property.getValue() );
                }
            }
        }
        if( vmOptions != null && vmOptions.length > 0 )
        {
            for( VMOption vmOption : vmOptions )
            {
                if( vmOption != null && vmOption.getOption() != null && vmOption.getOption().trim().length() > 0 )
                {
                    if( argument.length() > 0 )
                    {
                        argument.append( " " );
                    }
                    argument.append( vmOption.getOption() );
                }
            }
        }
        if( argument.length() > 0 )
        {
            argument.insert( 0, "--vmOptions=" );
        }
        return argument.toString();
    }

    /**
     * Converts repository options into corresponding arguments (--repositories).
     *
     * @param repositoriesOptions repository options to be converted
     * @param excludeDefaultRepositoriesOptions
     *                            if array not empty the default list of maven repos should be excluded
     *
     * @return converted Pax Runner argument
     */
    private static String extractArguments( RepositoryOptionImpl[] repositoriesOptions,
                                            ExcludeDefaultRepositoriesOption[] excludeDefaultRepositoriesOptions )
    {
        final StringBuilder argument = new StringBuilder();
        final boolean excludeDefaultRepositories = excludeDefaultRepositoriesOptions.length > 0;

        if( repositoriesOptions.length > 0 || excludeDefaultRepositories )
        {
            argument.append( "--repositories=" );
            if( !excludeDefaultRepositories )
            {
                argument.append( "+" );
            }
            for( int i = 0; i < repositoriesOptions.length; i++ )
            {
                argument.append( repositoriesOptions[ i ].getRepository() );
                if( i + 1 < repositoriesOptions.length )
                {
                    argument.append( "," );
                }
            }
        }
        return argument.toString();
    }

    private static String extractArguments( AutoWrapOption[] autoWrapOptions )
    {
        if( autoWrapOptions.length > 0 )
        {
            return "--autoWrap";
        }
        else
        {
            return null;
        }
    }

    private static String extractArguments( CleanCachesOption[] cleanCachesOption )
    {
        if( cleanCachesOption.length > 0 )
        {
            return "--clean";
        }
        else
        {
            return null;
        }
    }

    private static String extractArguments( LocalRepositoryOption[] localRepositoryOptions )
    {
        if( localRepositoryOptions != null && localRepositoryOptions.length > 0 )
        {
            LocalRepositoryOption local = localRepositoryOptions[ 0 ];
            return "--localRepository=" + local.getLocalRepositoryPath();

        }
        return null;
    }

    private static List<String> extractArguments( RawPaxRunnerOptionOption[] paxrunnerOptions )
    {
        List<String> args = new ArrayList<String>();
        final boolean excludeDefaultRepositories = paxrunnerOptions.length > 0;

        if( paxrunnerOptions.length > 0 || excludeDefaultRepositories )
        {
            for( int i = 0; i < paxrunnerOptions.length; i++ )
            {

                args.add( paxrunnerOptions[ i ].getOption().trim() );
            }
        }
        return args;
    }

    /**
     * Creates a working directory as ${java.io.tmpdir}/paxexam_runner_${user.name}.
     *
     * @return created working directory
     */
    private static File createWorkingDirectory()
    {

        final File workDir = new File( System.getProperty( "java.io.tmpdir" )
                                       + "/paxexam_runner_"
                                       + System.getProperty( "user.name" )
        );
        // create if not existent:
        if( !workDir.exists() )
        {
            workDir.mkdirs();
        }
        return workDir;
    }
}
