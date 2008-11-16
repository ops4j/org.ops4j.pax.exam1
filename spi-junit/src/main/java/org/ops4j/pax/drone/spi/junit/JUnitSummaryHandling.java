package org.ops4j.pax.drone.spi.junit;

import junit.framework.AssertionFailedError;
import org.ops4j.pax.drone.api.DroneSummary;
import org.ops4j.pax.drone.api.RecipeException;
import org.ops4j.pax.drone.spi.TextSummary;

/**
 * This handles droneSummaries in a junit runner usecase.
 * So, it
 * - prints the summary to syserr
 * - recovers assertionfailures
 *
 * @author Toni Menzel (tonit)
 * @since Nov 12, 2008
 */
public class JUnitSummaryHandling
{

    public static void handleSummary( DroneSummary summary )
        throws Throwable
    {
        handleSummary( new DroneSummary[]{ summary } );
    }

    public static void handleSummary( DroneSummary[] summaries )
        throws Throwable
    {
        TextSummary textSummary = new TextSummary();

        DroneSummary firstError = null;
        for( DroneSummary con : summaries )
        {
            if( con.hasFailed() || con.isRecipeException() )
            {
                firstError = con;
            }
            textSummary.addSummary( con );
        }

        textSummary.printSummary( false );

        if( firstError != null )
        {
            if( firstError.isRecipeException() )
            {
                RecipeException rec = (RecipeException) firstError.getCause();
                if( rec.isAssertionError() )
                {
                    // recover junit error so that junit fails and does not tell an error.
                    throw new AssertionFailedError( rec.getOriginalStackTrace() );
                }
                else
                {
                    throw firstError.getCause();
                }
            }
            else if( firstError.hasFailed() )
            {
                throw firstError.getException();
            }
        }
    }
}
