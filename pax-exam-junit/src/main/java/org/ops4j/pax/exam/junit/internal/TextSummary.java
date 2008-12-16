package org.ops4j.pax.exam.junit.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a text output utility for {@link TestExecutionSummary}.
 * While {@link TestExecutionSummary} exactly describes one pax exam execution, this helper supports many executions and will print a
 * user friendly message.
 *
 * @author Toni Menzel (tonit)
 * @since Oct 2, 2008
 */
public class TextSummary
{

    private List<TestExecutionSummary> m_testExecutionSummaries;

    public TextSummary( TestExecutionSummary summary )
    {
        m_testExecutionSummaries = new ArrayList<TestExecutionSummary>();
        m_testExecutionSummaries.add( summary );
    }

    public TextSummary()
    {
        m_testExecutionSummaries = new ArrayList<TestExecutionSummary>();
    }

    public void printSummary( boolean showStackTrace )
    {
        PrintStream out = System.err;
        out.println( "------------------------------------------------------------------------" );
        out.println( "Pax Exam Summary:" );
        out.println( "------------------------------------------------------------------------" );
        out.println( "Configurations : " + m_testExecutionSummaries.size() );

        for( int i = 0; i < m_testExecutionSummaries.size(); i++ )
        {
            TestExecutionSummary summary = m_testExecutionSummaries.get( i );
            if( !summary.hasFailed() )
            {
                out.println( "Configuration " + ( i + 1 ) + " of " + ( m_testExecutionSummaries.size() )
                             + " has passed the test successfully."
                );
            }
            else
            {
                if( summary.isRecipeException() )
                {
                    out.println(
                        "Configuration " + ( i + 1 ) + " of " + ( m_testExecutionSummaries.size() ) + " has a failure."
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
                    // Pax Exam problem.
                    out.println(
                        "Configuration " + ( i + 1 ) + " of " + ( m_testExecutionSummaries.size() ) + " has an error."
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

    public void addSummary( TestExecutionSummary testExecutionSummary )
    {
        m_testExecutionSummaries.add( testExecutionSummary );
    }
}
