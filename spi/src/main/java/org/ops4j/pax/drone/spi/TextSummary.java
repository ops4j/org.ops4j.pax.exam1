package org.ops4j.pax.drone.spi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.ops4j.pax.drone.api.DroneSummary;

/**
 * This is a text output utility for DroneSummaries.
 * While DroneSummary exactly describes one drone execution, this helper supports many executions and will print a
 * user friendly message.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 2, 2008
 */
public class TextSummary
{

    private List<DroneSummary> m_droneSummaries;

    public TextSummary( DroneSummary summary )
    {
        m_droneSummaries = new ArrayList<DroneSummary>();
        m_droneSummaries.add( summary );
    }

    public TextSummary()
    {
        m_droneSummaries = new ArrayList<DroneSummary>();
    }

    public void printSummary( boolean showStackTrace )
    {
        PrintStream out = System.err;
        out.println( "------------------------------------------------------------------------" );
        out.println( "Pax Drone Summary:" );
        out.println( "------------------------------------------------------------------------" );
        out.println( "Configurations : " + m_droneSummaries.size() );

        for( int i = 0; i < m_droneSummaries.size(); i++ )
        {
            DroneSummary summary = m_droneSummaries.get( i );
            if( !summary.hasFailed() )
            {
                out.println( "Configuration " + ( i + 1 ) + " of " + ( m_droneSummaries.size() )
                             + " has passed the test successfully."
                );
            }
            else
            {
                if( summary.isRecipeException() )
                {
                    out.println(
                        "Configuration " + ( i + 1 ) + " of " + ( m_droneSummaries.size() ) + " has a failure."
                    );

                    out.println( "Original exception message: " + summary.getCause().getMessage() );
                    if( showStackTrace )
                    {
                        String stacktrace = asString( summary.getCause() );
                        out.println( ">>>" );
                        out.println( stacktrace );
                        out.println( "<<<" );
                    }
                }
                else
                {
                    // PaxDrone problem.
                    out.println(
                        "Configuration " + ( i + 1 ) + " of " + ( m_droneSummaries.size() ) + " has an error."
                    );

                    out.println( "Serious Problem in Setup/Environment: " + summary.getCause().getMessage() );
                    // show always the full thing, its serious
                    String stacktrace = asString( summary.getCause() );
                    out.println( ">>>" );
                    out.println( stacktrace );
                    out.println( "<<<" );

                }
            }

        }
        out.println( "------------------------------------------------------------------------" );
    }

    private String asString( Throwable cause )
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        cause.printStackTrace( new PrintStream( bout ) );
        try
        {
            bout.close();
        }
        catch( IOException e )
        {

        }

        return bout.toString();
    }

    public void addSummary( DroneSummary droneSummary )
    {
        m_droneSummaries.add( droneSummary );
    }
}
