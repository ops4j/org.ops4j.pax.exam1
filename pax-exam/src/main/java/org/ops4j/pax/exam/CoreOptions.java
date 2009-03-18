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
package org.ops4j.pax.exam;

import java.util.ArrayList;
import java.util.List;
import static org.ops4j.lang.NullArgumentException.*;
import static org.ops4j.pax.exam.OptionUtils.*;
import org.ops4j.pax.exam.options.BootDelegationOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.EquinoxFrameworkOption;
import org.ops4j.pax.exam.options.FelixFrameworkOption;
import org.ops4j.pax.exam.options.FrameworkOption;
import org.ops4j.pax.exam.options.KnopflerfishFrameworkOption;
import org.ops4j.pax.exam.options.MavenUrlProvisionOption;
import org.ops4j.pax.exam.options.ProvisionOption;
import org.ops4j.pax.exam.options.SystemPackageOption;
import org.ops4j.pax.exam.options.SystemPropertyOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;
import org.ops4j.pax.exam.options.WrappedUrlProvisionOption;
import org.ops4j.pax.exam.options.MavenConfigurationOption;

/**
 * Factory methods for core options.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 08, 2008
 */
public class CoreOptions
{

    /**
     * Utility class. Ment to be used via the static factory methods.
     */
    private CoreOptions()
    {
        // utility class
    }

    /**
     * Convenience method (more to be used for a nice fluent api) for creating an array of options.
     * It also expands the composite options.
     *
     * @param options options
     *
     * @return provided options, expanded
     *
     * @see OptionUtils#expand(Option[])
     */
    public static Option[] options( final Option... options )
    {
        return expand( options );
    }

    /**
     * Convenience method (more to be used for a nice fluent api) for creating a composite option.
     *
     * @param options options
     *
     * @return provided options
     */
    public static Option composite( final Option... options )
    {
        return new DefaultCompositeOption( options );
    }

    /**
     * Creates a composite option of {@link FrameworkOption}s.
     *
     * @param frameworks framework options
     *
     * @return composite option of framework options
     */
    public static Option frameworks( final FrameworkOption... frameworks )
    {
        return composite( frameworks );
    }

    /**
     * Creates a {@link FelixFrameworkOption}.
     *
     * @return felix framework option
     */
    public static FelixFrameworkOption felix()
    {
        return new FelixFrameworkOption();
    }

     /**
     * Creates a {@link MavenConfigurationOption}.
     *
     * @return MavenConfigurationOption option
     */
    public static MavenConfigurationOption configureWithMaven()
    {
        return new MavenConfigurationOption();
    }

    /**
     * Creates a composite option of all {@link FelixFrameworkOption} versions (up to the date of Pax Exam release).
     *
     * @return all felix framework versions composite option
     */
    public static Option allFelixVersions()
    {
        return composite(
            felix().version( "1.0.0" ),
            felix().version( "1.0.1" ),
            felix().version( "1.0.3" ),
            felix().version( "1.0.4" ),
            felix().version( "1.2.0" ),
            felix().version( "1.2.1" ),
            felix().version( "1.2.2" ),
            felix().version( "1.4.0" ),
            felix().version( "1.4.1" )
        );
    }

    /**
     * Creates a {@link EquinoxFrameworkOption}.
     *
     * @return equinox framework option
     */
    public static EquinoxFrameworkOption equinox()
    {
        return new EquinoxFrameworkOption();
    }

    /**
     * Creates a composite option of all {@link EquinoxFrameworkOption} versions (up to the date of Pax Exam release).
     *
     * @return all equinox framework versions composite option
     */
    public static Option allEquinoxVersions()
    {
        return composite(
            equinox().version( "3.2.1" ),
            equinox().version( "3.3.0" ),
            equinox().version( "3.3.1" ),
            equinox().version( "3.3.2" ),
            equinox().version( "3.4.0" ),
            equinox().version( "3.4.1" ),
            equinox().version( "3.4.2" )
        );
    }

    /**
     * Creates a {@link KnopflerfishFrameworkOption}.
     *
     * @return knopflerfish framework option
     */
    public static KnopflerfishFrameworkOption knopflerfish()
    {
        return new KnopflerfishFrameworkOption();
    }

    /**
     * Creates a composite option of all {@link KnopflerfishFrameworkOption} versions (up to the date of Pax Exam
     * release).
     *
     * @return all knopflerfish framework versions composite option
     */
    public static Option allKnopflerfishVersions()
    {
        return composite(
            //exclude version 2.0.0 as it looks like it has an internal problem of NPE in BundlePackages.java:266 while
            //looking for annotations from CallableTestMethodImpl.getAnnotatedMethods
            //knopflerfish().version( "2.0.0" ),
            knopflerfish().version( "2.0.1" ),
            knopflerfish().version( "2.0.2" ),
            knopflerfish().version( "2.0.3" ),
            knopflerfish().version( "2.0.4" ),
            knopflerfish().version( "2.0.5" ),
            knopflerfish().version( "2.1.0" ),
            knopflerfish().version( "2.1.1" ),
            knopflerfish().version( "2.2.0" )
        );
    }

    /**
     * Creates a composite option of latest versions of Felix, Equinox and Knopflerfish.
     *
     * @return latest versions of all frameworks composite option
     */
    public static Option allFrameworks()
    {
        return composite(
            felix(),
            equinox(),
            knopflerfish()
        );
    }

    /**
     * Creates a composite option of all versions of Felix, Equinox and Knopflerfish.
     *
     * @return all framework versions composite option
     */
    public static Option allFrameworksVersions()
    {
        return composite(
            allFelixVersions(),
            allEquinoxVersions(),
            allKnopflerfishVersions()
        );
    }

    /**
     * Creates a composite option of {@link ProvisionOption}s.
     *
     * @param urls provision urls (cannot be null or containing null entries)
     *
     * @return composite option of provision options
     *
     * @throws IllegalArgumentException - If urls array is null or contains null entries
     */
    public static Option provision( final String... urls )
    {
        validateNotEmptyContent( urls, true, "URLs" );
        final List<ProvisionOption> options = new ArrayList<ProvisionOption>();
        for( String url : urls )
        {
            options.add( new UrlProvisionOption( url ) );
        }
        return provision( options.toArray( new ProvisionOption[options.size()] ) );
    }

    /**
     * Creates a composite option of {@link ProvisionOption}s.
     *
     * @param urls provision options
     *
     * @return composite option of provision options
     */
    public static Option provision( final ProvisionOption... urls )
    {
        return composite( urls );
    }

    /**
     * Creates a {@link UrlProvisionOption}.
     *
     * @param url bundle url
     *
     * @return url provisioning option
     */
    public static UrlProvisionOption bundle( final String url )
    {
        return new UrlProvisionOption( url );
    }

    /**
     * Creates a {@link MavenUrlProvisionOption}.
     *
     * @return maven specific provisioning option
     */
    public static MavenUrlProvisionOption mavenBundle()
    {
        return new MavenUrlProvisionOption();
    }

    /**
     * Creates a {@link WrappedUrlProvisionOption}.
     *
     * @param url wrapped bundle url
     *
     * @return wrap specific provisioning option
     */
    public static WrappedUrlProvisionOption wrappedBundle( final String url )
    {
        return new WrappedUrlProvisionOption( url );
    }

    /**
     * Creates a {@link WrappedUrlProvisionOption}.
     *
     * @param wrapped wrapped bundle
     *
     * @return wrap specific provisioning option
     */
    public static WrappedUrlProvisionOption wrappedBundle( final ProvisionOption wrapped )
    {
        return new WrappedUrlProvisionOption( wrapped );
    }

    /**
     * Creates a composite option of {@link BootDelegationOption}s.
     *
     * @param packages boot delegation packages (cannot be null or containing null entries)
     *
     * @return composite option of boot delegation package options
     *
     * @throws IllegalArgumentException - If urls array is null or contains null entries
     */
    public static Option bootDelegationPackages( final String... packages )
    {
        validateNotEmptyContent( packages, true, "Packages" );
        final List<BootDelegationOption> options = new ArrayList<BootDelegationOption>();
        for( String pkg : packages )
        {
            options.add( bootDelegationPackage( pkg ) );
        }
        return bootDelegationPackages( options.toArray( new BootDelegationOption[options.size()] ) );
    }

    /**
     * Creates a composite option of {@link BootDelegationOption}s.
     *
     * @param packages boot delegation package options
     *
     * @return composite option of boot delegation package options
     */
    public static Option bootDelegationPackages( final BootDelegationOption... packages )
    {
        return composite( packages );
    }

    /**
     * Creates a {@link BootDelegationOption}.
     *
     * @param pkg boot delegation package
     *
     * @return boot delegation package option
     */
    public static BootDelegationOption bootDelegationPackage( final String pkg )
    {
        return new BootDelegationOption( pkg );
    }

    /**
     * Creates a composite option of {@link SystemPackageOption}s.
     *
     * @param packages system packages (cannot be null or containing null entries)
     *
     * @return composite option of system package options
     *
     * @throws IllegalArgumentException - If urls array is null or contains null entries
     */
    public static Option systemPackages( final String... packages )
    {
        validateNotEmptyContent( packages, true, "Packages" );
        final List<SystemPackageOption> options = new ArrayList<SystemPackageOption>();
        for( String pkg : packages )
        {
            options.add( systemPackage( pkg ) );
        }
        return systemPackages( options.toArray( new SystemPackageOption[options.size()] ) );
    }

    /**
     * Creates a composite option of {@link SystemPackageOption}s.
     *
     * @param packages system package options
     *
     * @return composite option of system package options
     */
    public static Option systemPackages( final SystemPackageOption... packages )
    {
        return composite( packages );
    }

    /**
     * Creates a {@link SystemPackageOption}.
     *
     * @param pkg system package
     *
     * @return system package option
     */
    public static SystemPackageOption systemPackage( final String pkg )
    {
        return new SystemPackageOption( pkg );
    }

    /**
     * Creates a composite option of {@link SystemPropertyOption}s.
     *
     * @param systemProperties system property options
     *
     * @return composite option of system property options
     */
    public static Option systemProperties( final SystemPropertyOption... systemProperties )
    {
        return composite( systemProperties );
    }

    /**
     * Creates a {@link SystemPropertyOption}.
     *
     * @param key system property key
     *
     * @return system property option
     */
    public static SystemPropertyOption systemProperty( final String key )
    {
        return new SystemPropertyOption( key );
    }

}
