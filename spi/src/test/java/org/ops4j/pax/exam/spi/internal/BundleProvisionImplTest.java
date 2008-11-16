package org.ops4j.pax.exam.spi.internal;

import static org.junit.Assert.*;
import org.junit.Test;
import org.ops4j.pax.exam.api.BundleProvision;
import org.ops4j.pax.exam.spi.internal.BundleProvisionImpl;

/**
 * @author Toni Menzel (tonit)
 * @since Oct 29, 2008
 */
public class BundleProvisionImplTest
{

    @Test
    public void testDefaultState()
    {
        BundleProvision provision = new BundleProvisionImpl();
        assertEquals( 0, provision.getBundles().length );
    }

    @Test
    public void testAddOne()
    {
        BundleProvision provision = new BundleProvisionImpl();
        provision.addBundle( "foo" );
        assertEquals( 1, provision.getBundles().length );
    }

    @Test
    public void testAddSameTwice()
    {
        BundleProvision provision = new BundleProvisionImpl();
        provision.addBundle( "foo" );
        provision.addBundle( "foo" );

        assertEquals( 1, provision.getBundles().length );
    }

    @Test
    public void testAddMore()
    {
        BundleProvision provision = new BundleProvisionImpl();
        provision.addBundle( "foo" );
        provision.addBundle( "foo2" );

        assertEquals( 2, provision.getBundles().length );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testAddingNull()
    {
        BundleProvision provision = new BundleProvisionImpl();
        provision.addBundle( null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testAddingEmptyString()
    {
        BundleProvision provision = new BundleProvisionImpl();
        provision.addBundle( "" );
    }

    @Test
    public void testHumaneApiNess()
    {
        BundleProvision prov1 = new BundleProvisionImpl();
        BundleProvision prov2 = prov1.addBundle( "foo" );
        assertSame( prov1, prov2 );
    }
}
