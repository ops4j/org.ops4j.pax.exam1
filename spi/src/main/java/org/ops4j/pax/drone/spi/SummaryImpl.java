/*
 * Copyright 2008 Toni Menzel
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.drone.spi;

import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.drone.api.DroneSummary;
import org.ops4j.pax.drone.api.RecipeException;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 3, 2008
 */
public class SummaryImpl implements DroneSummary
{

    private boolean m_isRecipe = false;
    private Exception m_exception;
    protected Throwable m_cause;

    public boolean hasFailed()
    {
        return ( m_exception != null );
    }

    public boolean isRecipeException()
    {
        return m_isRecipe;
    }

    public void setException( Exception e )
    {
        NullArgumentException.validateNotNull( e, "exception given" );
        m_exception = e;
        m_cause = getCause( e );
        if( isRecipeException( e ) )
        {
            m_isRecipe = true;
        }
    }

    public Throwable getCause()
    {
        return m_cause;
    }

    /**
     * Walks down the stacktrace and looks for RecipeException (by name).
     * RecipeExceptions indicate exceptions originating inside the users recipe
     * and might be provoked.
     *
     * If found we will return true. Otherwise false
     *
     * @param e a throwable to be checked
     *
     * @return if e is a RecipeException (by name)
     */
    private boolean isRecipeException( Throwable e )
    {
        if( e.getClass().getName().equals( RecipeException.class.getName() ) )
        {
            return true;
        }
        Throwable t = e.getCause();
        if( t != null )
        {
            if( RecipeException.class.getName().equals( t.getClass().getName() ) )
            {
                return true;
            }
            return isRecipeException( t );
        }
        else
        {
            return false;
        }
    }

    private Throwable getCause( Throwable e )
    {

        Throwable t = e.getCause();
        if( t != null )
        {
            return getCause( t );
        }
        else
        {
            return e;
        }
    }

    public Exception getException()
    {
        return m_exception;
    }
}
