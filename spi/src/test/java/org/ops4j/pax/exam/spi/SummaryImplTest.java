package org.ops4j.pax.exam.spi;

import static org.junit.Assert.*;
import org.junit.Test;
import org.ops4j.pax.exam.api.RecipeException;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 30, 2008
 */
public class SummaryImplTest
{

    @Test
    public void testNewHasNotCause()
    {
        SummaryImpl summary = new SummaryImpl();
        assertNull( summary.getCause() );
    }

    @Test
    public void testNewHasNotException()
    {
        SummaryImpl summary = new SummaryImpl();
        assertNull( summary.getException() );
    }

    @Test
    public void testNewIsNotARecipeException()
    {
        SummaryImpl summary = new SummaryImpl();
        assertFalse( summary.isRecipeException() );
    }

    @Test
    public void testNewIHasNotFailed()
    {
        SummaryImpl summary = new SummaryImpl();
        assertFalse( summary.hasFailed() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testNewSetExceptionWithNull()
    {
        SummaryImpl summary = new SummaryImpl();
        summary.setException( null );
    }

    @Test
    public void testNormalException()
    {
        SummaryImpl summary = new SummaryImpl();
        Exception ex = new Exception();
        summary.setException( ex );
        assertTrue( summary.hasFailed() );
        assertFalse( summary.isRecipeException() );
        assertSame( ex, summary.getException() );
        assertSame( ex, summary.getCause() );
    }

    @Test
    public void testRecpipeException()
    {
        SummaryImpl summary = new SummaryImpl();
        Exception root = new Exception();
        Exception ex = new RecipeException( root );
        summary.setException( ex );
        assertTrue( summary.hasFailed() );
        assertTrue( summary.isRecipeException() );
        assertSame( ex, summary.getException() );
        assertSame( ex, summary.getCause() );
    }
}
