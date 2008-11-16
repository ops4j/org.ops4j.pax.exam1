package org.ops4j.pax.drone.spi.junit;

import junit.framework.TestCase;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.api.DroneSummary;
import org.ops4j.pax.exam.spi.OnDemandDroneProvider;
import org.ops4j.pax.exam.api.DroneConnector;
import org.ops4j.pax.exam.api.DroneProvider;

/**
 * TestFirst Scenario brought to osgi integration testing:
 * start a testcase, check that something fails
 * then start a testcase with a known change where the test must not fail
 * this is kind of a TDD approach, brought to integration testing.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 1, 2008
 */
public abstract class TestFirstTestCase extends TestCase
{

    /**
     * Will be injected inside the framework
     */
    public BundleContext bundleContext = null;
    private transient DroneConnector m_connectorSuccessful;
    private transient DroneConnector m_connectorFailing;

    protected abstract DroneConnector configureFailing();

    protected abstract DroneConnector configureSuccessful();

    protected final DroneConnector getConnectorSuccessful()
    {
        if( m_connectorSuccessful == null )
        {
            m_connectorSuccessful = configureSuccessful();
        }
        return m_connectorSuccessful;
    }

    protected final DroneConnector getConnectorFailing()
    {
        if( m_connectorFailing == null )
        {
            m_connectorFailing = configureFailing();
        }
        return m_connectorFailing;
    }

    /**
     * Instantiates a single runner per call. Slow but max. side-effect free.
     * Hence, the underlying builder should make sure that the drone will not be rebuild each rime (does not change)
     */
    public void runBare()
        throws Throwable
    {
        // runs the same thing with two different configurations:
        // the first must fail (RecipeException), the seccond must succeed.

        DroneProvider provider = new OnDemandDroneProvider( getName(), this.getClass().getName() );
        DroneSummary interMustFail = getConnectorFailing().execute( provider );

        if( interMustFail.hasFailed() )
        {
            if( interMustFail.isRecipeException() )
            {
                DroneSummary m_runnerSuc = getConnectorSuccessful().execute( provider );

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
