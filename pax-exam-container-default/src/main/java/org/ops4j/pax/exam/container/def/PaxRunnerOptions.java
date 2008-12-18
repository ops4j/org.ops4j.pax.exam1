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
package org.ops4j.pax.exam.container.def;

import java.util.ArrayList;
import java.util.List;
import static org.ops4j.lang.NullArgumentException.*;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.container.def.options.DirScannerProvisionOption;
import org.ops4j.pax.exam.container.def.options.FileScannerProvisionOption;
import org.ops4j.pax.exam.container.def.options.ProfileOption;
import org.ops4j.pax.exam.container.def.options.TimeoutOption;
import org.ops4j.pax.exam.container.def.options.VMOption;
import org.ops4j.pax.exam.container.def.options.BundleScannerProvisionOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.ProvisionOption;

/**
 * Factory methods for Pax Runner options.
 *
 * TODO add support for scanner options and wrapping as scanner urls for urls in order to have @update, @start, ...
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 08, 2008
 */
public class PaxRunnerOptions
{

    /**
     * Utility class. Ment to be used via the static factory methods.
     */
    private PaxRunnerOptions()
    {
        // utility class
    }

    /**
     * Creates a composite option of {@link ProfileOption}.
     *
     * @param profiles profile options
     *
     * @return composite option of profiles options
     */
    public static Option profiles( final ProfileOption... profiles )
    {
        return new DefaultCompositeOption( profiles );
    }

    /**
     * Creates a web {@link ProfileOption}.
     *
     * @return web profile option
     */
    public static ProfileOption webProfile()
    {
        return new ProfileOption( "web" );
    }

    /**
     * Creates a log {@link ProfileOption}.
     *
     * @return log profile option
     */
    public static ProfileOption logProfile()
    {
        return new ProfileOption( "log" );
    }

    /**
     * Creates a config {@link ProfileOption}.
     *
     * @return config profile option
     */
    public static ProfileOption configProfile()
    {
        return new ProfileOption( "config" );
    }

    /**
     * Creates a {@link TimeoutOption}.
     *
     * @param timeout timeout in millis
     *
     * @return timeout option
     */
    public static TimeoutOption timeout( final Integer timeout )
    {
        return new TimeoutOption( timeout );
    }

    /**
     * Creates a composite option of {@link VMOption}s.
     *
     * @param vmOptions virtual machine options (cannot be null or containing null entries)
     *
     * @return composite option of virtual machine options
     *
     * @throws IllegalArgumentException - If urls array is null or contains null entries
     */
    public static Option vmOptions( final String... vmOptions )
    {
        validateNotEmptyContent( vmOptions, true, "VM options" );
        final List<VMOption> options = new ArrayList<VMOption>();
        for( String vmOption : vmOptions )
        {
            options.add( vmOption( vmOption ) );
        }
        return vmOptions( options.toArray( new VMOption[options.size()] ) );
    }

    /**
     * Creates a composite option of {@link VMOption}s.
     *
     * @param vmOptions virtual machine options
     *
     * @return composite option of virtual machine options
     */
    public static Option vmOptions( final VMOption... vmOptions )
    {
        return new DefaultCompositeOption( vmOptions );
    }

    /**
     * Creates a {@link VMOption}.
     *
     * @param vmOption virtual machine option
     *
     * @return virtual machine option
     */
    public static VMOption vmOption( final String vmOption )
    {
        return new VMOption( vmOption );
    }

    /**
     * Creates a {@link DirScannerProvisionOption}.
     *
     * @param directory directory to be scanned
     *
     * @return directory scanner option
     */
    public static DirScannerProvisionOption scanDir( final String directory )
    {
        return new DirScannerProvisionOption( directory );
    }

    /**
     * Creates a {@link FileScannerProvisionOption}.
     *
     * @param url url of the file to be scanned
     *
     * @return file scanner option
     */
    public static FileScannerProvisionOption scanFile( final String url )
    {
        return new FileScannerProvisionOption( url );
    }

    /**
     * Creates a {@link FileScannerProvisionOption}.
     *
     * @param provision file to be scanned as a provision option
     *
     * @return file scanner option
     */
    public static FileScannerProvisionOption scanFile( final ProvisionOption provision )
    {
        return new FileScannerProvisionOption( provision );
    }

    /**
     * Creates a {@link BundleScannerProvisionOption}.
     *
     * @param url url of the bundle to be scanned
     *
     * @return bundle scanner option
     */
    public static BundleScannerProvisionOption scanBundle( final String url )
    {
        return new BundleScannerProvisionOption( url );
    }

    /**
     * Creates a {@link BundleScannerProvisionOption}.
     *
     * @param provision bundle to be scanned as a provision option
     *
     * @return bundle scanner option
     */
    public static BundleScannerProvisionOption scanBundle( final ProvisionOption provision )
    {
        return new BundleScannerProvisionOption( provision );
    }

}