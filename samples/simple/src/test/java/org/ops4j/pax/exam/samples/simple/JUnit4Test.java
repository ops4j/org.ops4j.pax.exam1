package org.ops4j.pax.exam.samples.simple;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import static org.ops4j.pax.exam.connector.paxrunner.GenericConnector.create;
import org.ops4j.pax.exam.connector.paxrunner.Platforms;
import org.ops4j.pax.exam.spi.junit.Configuration;
import org.ops4j.pax.exam.spi.junit.PaxExamTestRunner;
import org.osgi.framework.BundleContext;

/**
 * TODO fill test results with proper timesheet.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 14, 2008
 */
@RunWith( PaxExamTestRunner.class )
public class JUnit4Test
{

    @Configuration
    public TestRunnerConnector configure()
    {
        return create().setPlatform( Platforms.FELIX );
    }

    @Test
    public void aVerySimpleOne()
    {
        System.out.println( "Hello World" );
    }

    @Test
    public void anotherTestForTesting()
    {
        System.out.println( "Again, Hello World" );
        //  fail( "thats bad" );
    }

    @Test
    public void haveBundleContextPassedInto( BundleContext bc )
    {
        System.out.println( "Again, Hello World" );
        assertNotNull( bc );
    }


}