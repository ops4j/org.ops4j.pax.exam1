package org.ops4j.pax.exam;

import java.util.ArrayList;
import java.util.List;
import org.ops4j.pax.exam.options.BootDelegationOption;
import org.ops4j.pax.exam.options.BundleURLOption;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.EquinoxPlatformOption;
import org.ops4j.pax.exam.options.FelixPlatformOption;
import org.ops4j.pax.exam.options.MavenBundleURLOption;
import org.ops4j.pax.exam.options.PlainBundleURLOption;
import org.ops4j.pax.exam.options.PlatformOption;
import org.ops4j.pax.exam.options.SystemPackageOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:16:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoreOptions
{

    public static ConfigurationOption provision( String... urls )
    {
        final List<BundleURLOption> options = new ArrayList<BundleURLOption>();
        for( String url : urls )
        {
            options.add( new PlainBundleURLOption( url ) );
        }
        return provision( options.toArray( new BundleURLOption[options.size()] ) );
    }

    public static ConfigurationOption provision( BundleURLOption... urls )
    {
        return new CompositeOption( urls );
    }

    public static ConfigurationOption platform( PlatformOption... platforms )
    {
        return new CompositeOption( platforms );
    }

    public static PlatformOption felix()
    {
        return new FelixPlatformOption();
    }

    public static PlatformOption equinox()
    {
        return new EquinoxPlatformOption();
    }

    public static MavenBundleURLOption mavenBundle()
    {
        return new MavenBundleURLOption();
    }

    public static ConfigurationOption bootDelegation( String... packages )
    {
        final List<BootDelegationOption> options = new ArrayList<BootDelegationOption>();
        for( String pkg : packages )
        {
            options.add( new BootDelegationOption( pkg ) );
        }
        return bootDelegation( options.toArray( new BootDelegationOption[options.size()] ) );
    }

    public static ConfigurationOption bootDelegation( BootDelegationOption... packages )
    {
        return new CompositeOption( packages );
    }

    public static ConfigurationOption systemPackage( String... packages )
    {
        final List<SystemPackageOption> options = new ArrayList<SystemPackageOption>();
        for( String pkg : packages )
        {
            options.add( new SystemPackageOption( pkg ) );
        }
        return bootDelegation( options.toArray( new BootDelegationOption[options.size()] ) );
    }

    public static ConfigurationOption systemPackage( SystemPackageOption... packages )
    {
        return new CompositeOption( packages );
    }

}
