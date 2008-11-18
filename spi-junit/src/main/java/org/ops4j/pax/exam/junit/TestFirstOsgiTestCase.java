package org.ops4j.pax.exam.junit;

import junit.framework.TestCase;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.api.TestExecutionSummary;
import org.ops4j.pax.exam.api.TestProbeBuilder;
import org.ops4j.pax.exam.api.TestRunnerConnector;
import org.ops4j.pax.exam.spi.OnDemandTestProbeBuilder;

/**
 * TestFirst Scenario brought to osgi integration testing:
 * start a testcase, check that something fails
 * then start a testcase with a known change where the test must not fail
 * this is kind of a TDD approach, brought to integration testing.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public abstract class TestFirstOsgiTestCase extends TestCase
{

    /**
     * Will be injected inside the framework
     */
    public BundleContext bundleContext = null;
    private transient TestRunnerConnector m_connectorSuccessful;
    private transient TestRunnerConnector m_connectorFailing;

    protected abstract TestRunnerConnector configureFailing();

    protected abstract TestRunnerConnector configureSuccessful();

    protected final TestRunnerConnector getConnectorSuccessful()
    {
        if( m_connectorSuccessful == null )
        {
            m_connectorSuccessful = configureSuccessful();
        }
        return m_connectorSuccessful;
    }

    protected final TestRunnerConnector getConnectorFailing()
    {
        if( m_connectorFailing == null )
        {
            m_connectorFailing = configureFailing();
        }
        return m_connectorFailing;
    }

    /**
     * Instantiates a single runner per call. Slow but max. side-effect free.
     * Hence, the underlying builder should make sure that the probe will not be rebuild each rime (does not change)
     */
    public void runBare()
        throws Throwable
    {
        // runs the same thing with two different configurations:
        // the first must fail (RecipeException), the seccond must succeed.

        TestProbeBuilder builder = new OnDemandTestProbeBuilder( getName(), this.getClass().getName() );
        TestExecutionSummary interMustFail = getConnectorFailing().execute( builder );

        if( interMustFail.hasFailed() )
        {
            if( interMustFail.isRecipeException() )
            {
                TestExecutionSummary m_runnerSuc = getConnectorSuccessful().execute( builder );

            }
            else
            {
                throw new RuntimeException( "First run ran into errors." );
            }
        }
        else
        {
            throw new RuntimeException( "First Configuration must FAIL in this scenario. But it ran successful :-(" );
        }

    }
}
