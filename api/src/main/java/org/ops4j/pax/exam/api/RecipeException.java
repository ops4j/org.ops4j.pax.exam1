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
package org.ops4j.pax.exam.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Must only be thrown by direct caller of customer's test code.
 * If the test code throws an exception this is wrapped into a RecipeException.
 *
 * The original exception is read and written into a flat byte[] (printStackTrace) so that
 *
 * @author Toni Menzel (tonit)
 * @since Jul 10, 2008
 */
public class RecipeException extends Exception
{

    private String m_orginalStacktrace;
    private String m_message;
    private boolean m_assertError;

    public RecipeException( Exception e )
    {
        // always wrap them as string because serialization of this exception may fail
        // and can lead to crazy new exceptions.
        try
        {
            Throwable cause = e;
            if( e.getCause() != null )
            {
                cause = e.getCause();
            }
            m_message = cause.getMessage();
            // fuzzy way to detect a junit exception without having the clazz itself
            m_assertError = ( cause.getClass().getName().endsWith( "AssertionFailedError" )
                              || ( cause instanceof AssertionError ) );
            ByteArrayOutputStream arrOut = new ByteArrayOutputStream();
            PrintStream writer = new PrintStream( arrOut );
            cause.printStackTrace( writer );
            arrOut.close();
            m_orginalStacktrace = arrOut.toString();

        }
        catch( IOException e1 )
        {
            // this would be bad
            e1.printStackTrace();
        }
    }

    public void printStackTrace()
    {
        printStackTrace( System.err );
    }

    public void printStackTrace( PrintStream printStream )
    {
        printStream.print( m_orginalStacktrace );

    }

    public void printStackTrace( PrintWriter printWriter )
    {

        printWriter.print( m_orginalStacktrace );

    }

    public String getMessage()
    {
        return m_message;
    }

    public String getOriginalStackTrace()
    {
        return m_orginalStacktrace;
    }

    public boolean isAssertionError()
    {
        return m_assertError;
    }
}
