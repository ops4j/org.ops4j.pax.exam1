package org.ops4j.pax.exam.spi.internal;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.ops4j.pax.exam.spi.internal.IntelliResourceFinder;

/**
 * User: Toni Menzel (tonit)
 * Date: Jun 19, 2008
 */
public class IntelliResourceFinderTest
{

    @Test( expected = IllegalArgumentException.class )
    public void createWithAllNull()
    {
        new IntelliResourceFinder( null, null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithFirstNull()
    {
        new IntelliResourceFinder( null, "foo" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithSecNull()
    {
        new IntelliResourceFinder( new File( "foo" ), null );
    }


    @Test( expected = IllegalArgumentException.class )
    public void createWithEmptyClassName()
    {
        new IntelliResourceFinder( new File( "foo" ), "" );
    }

    @Test
    public void create()
    {
        new IntelliResourceFinder( getTestFolder(), "recipe" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithExistingFileButNotFolder()
    {
        File f = getTestFile();
        // be sure its a file and it exists.. (provoke error on foldercheck)
        if( f.exists() && f.isFile() )
        {
            new IntelliResourceFinder( new File( "pom.xml" ), "foo" );
        }
        else
        {
            fail( "Bad unit test workingfolder: file " + f.getAbsolutePath() + " not found." );
        }
    }

    @Test( expected = IllegalArgumentException.class )
    public void createWithNonExistingFolder()
    {
        new IntelliResourceFinder( new File( "doesnotexist" ), "foo" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void findNonExistingClass() throws IOException
    {
        IntelliResourceFinder finder = new IntelliResourceFinder( new File( "." ), "nonexistingclass" );
        JarOutputStream target = new JarOutputStream( new OutputStream()
        {

            public void write( int i ) throws IOException
            {
                fail( "nothing to write, so nothing to call.." );
            }
        } );

        finder.write( target );
    }

    @Test
    public void findExistingClass() throws IOException
    {
        IntelliResourceFinder finder = new IntelliResourceFinder( new File( "." ), this.getClass().getName() );
        final boolean[] blnFlag = new boolean[]{ false };
        JarOutputStream target = new JarOutputStream( new OutputStream()
        {

            public void write( int i ) throws IOException
            {
                blnFlag[ 0 ] = true;
            }
        } );

        finder.write( target );
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
