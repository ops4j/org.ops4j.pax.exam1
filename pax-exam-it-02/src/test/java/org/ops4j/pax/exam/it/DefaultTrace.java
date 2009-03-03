package org.ops4j.pax.exam.it;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.Inject;

/**
 * This is a sample that is being configured to give max. insight into pax exam by
 * - having log service installed
 * - setting low log levels.
 *
 * This test is mostly being used manually to track problems if they show up.
 *
 * @author Toni Menzel (tonit)
 * @since Mar 3, 2009
 */
public abstract class DefaultTrace
{

    @Inject
    BundleContext bundleContext = null;

    public Log logger = LogFactory.getLog( DefaultTrace.class );

    @Test
    public void test1()
    {
        logger.trace( "******** This a trace from OSGi" );
        logger.debug( "******** This a debug from OSGi" );
        logger.info( "******** This a info from OSGi" );
        logger.warn( "******** This a warn from OSGi" );
        logger.error( "******** This a errory from OSGi" );

        logger.info( "This is running inside Felix. With all configuration set up like you specified. " );
        for( Bundle b : bundleContext.getBundles() )
        {
            logger.info( "Bundle " + b.getBundleId() + " : " + b.getSymbolicName() );
        }

    }
}
