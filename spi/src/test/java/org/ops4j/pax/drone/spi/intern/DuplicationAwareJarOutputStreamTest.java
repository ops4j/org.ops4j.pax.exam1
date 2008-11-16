package org.ops4j.pax.drone.spi.intern;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.ops4j.pax.drone.spi.intern.DuplicateAwareJarOutputStream;

/**
 * User: Toni Menzel (tonit)
 * Date: Jun 18, 2008
 */
public class DuplicationAwareJarOutputStreamTest
{
    @Test
    public void createAndCallNormal() throws IOException
    {
        final boolean[] flag = new boolean[]{ false };

        OutputStream backing = new OutputStream()
        {

            public void write( int i ) throws IOException
            {

                flag[ 0 ] = true;

            }
        };
        DuplicateAwareJarOutputStream dup = new DuplicateAwareJarOutputStream( backing );
        dup.putNextEntry( new ZipEntry( "one" ) );
        dup.write( 0 );
        assertTrue( flag[ 0 ] );
    }

    @Test
    public void createAndCallWithDuplicate() throws IOException
    {
        final boolean[] flag = new boolean[]{ false };

        OutputStream backing = new OutputStream()
        {

            public void write( int i ) throws IOException
            {
                if( !flag[ 0 ] )
                {
                    fail( ( "no allowed" ) );
                }
            }
        };
        DuplicateAwareJarOutputStream dup = new DuplicateAwareJarOutputStream( backing );
        flag[ 0 ] = true;
        dup.putNextEntry( new ZipEntry( "one" ) );
        dup.write( 0 );
        dup.putNextEntry( new ZipEntry( "one" ) );
        flag[ 0 ] = false;
        dup.write( 23 );
    }

    @Test
    public void createAndCallWithDuplicateAndKeepGoing() throws IOException
    {
        final boolean[] flag = new boolean[]{ false };

        OutputStream backing = new OutputStream()
        {

            public void write( int i ) throws IOException
            {
                if( !flag[ 0 ] )
                {
                    fail( ( "no allowed" ) );
                }
            }
        };
        DuplicateAwareJarOutputStream dup = new DuplicateAwareJarOutputStream( backing );
        flag[ 0 ] = true;
        dup.putNextEntry( new ZipEntry( "one" ) );
        dup.write( 0 );
        dup.putNextEntry( new ZipEntry( "one" ) );
        flag[ 0 ] = false;
        dup.write( 23 );

        flag[ 0 ] = true;
        dup.putNextEntry( new ZipEntry( "next" ) );
        dup.write( 222 );
    }
}
