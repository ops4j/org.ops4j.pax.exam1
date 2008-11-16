package org.ops4j.pax.exam.spi.internal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;

/**
 * User: Toni Menzel (tonit)
 * Date: Jun 19, 2008
 */
public class IntelliResourceFinderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void createWithAllNull()
    {
        new IntelliResourceLocator( null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithFirstNull()
    {
        new IntelliResourceLocator( null, "foo" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithSecNull()
    {
        new IntelliResourceLocator( new File( "foo" ), null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithEmptyClassName()
    {
        new IntelliResourceLocator( new File( "foo" ), "" );
    }

    @Test
    public void create()
    {
        new IntelliResourceLocator( getTestFolder(), "recipe" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithExistingFileButNotFolder()
    {
        File f = getTestFile();
        // be sure its a file and it exists.. (provoke error on foldercheck)
        if( f.exists() && f.isFile() )
        {
            new IntelliResourceLocator( new File( "pom.xml" ), "foo" );
        }
        else
        {
            fail( "Bad unit test workingfolder: file " + f.getAbsolutePath() + " not found." );
        }
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithNonExistingFolder()
    {
        new IntelliResourceLocator( new File( "doesnotexist" ), "foo" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void findNonExistingClass()
        throws IOException
    {
        IntelliResourceLocator locator = new IntelliResourceLocator( new File( "." ), "nonexistingclass" );
        JarOutputStream target = new JarOutputStream( new OutputStream()
        {

            public void write( int i )
                throws IOException
            {
                fail( "nothing to write, so nothing to call.." );
            }
        }
        );

        locator.write( target );
    }

    @Test
    public void findExistingClass()
        throws IOException
    {
        IntelliResourceLocator locator = new IntelliResourceLocator( new File( "." ), this.getClass().getName() );
        final boolean[] blnFlag = new boolean[]{ false };
        JarOutputStream target = new JarOutputStream( new OutputStream()
        {

            public void write( int i )
                throws IOException
            {
                blnFlag[ 0 ] = true;
            }
        }
        );

        locator.write( target );
        assertTrue( blnFlag[ 0 ] );
    }

    private File getTestFolder()
    {
        return new File( System.getProperty( "java.io.tmpdir" ) );
    }

    private File getTestFile()
    {
        return new File( "pom.xml" );
    }

}
