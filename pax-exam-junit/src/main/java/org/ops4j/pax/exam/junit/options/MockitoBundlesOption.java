package org.ops4j.pax.exam.junit.options;

import org.ops4j.pax.exam.options.AbstractProvisionWrapperOption;
import org.ops4j.pax.exam.options.MavenUrlProvisionOption;
import org.ops4j.pax.exam.options.WrappedUrlProvisionOption;

/**
 * Option specifying Mockito bundles (osgi-fyed mockito).
 * See: http://code.google.com/p/mockito/
 * By default uses the mockito-all delivery wrapped up on the fly as a bundle.
 *
 * Version: 1.7. Can be changed.
 *
 * @author Toni Menzel (tonit)
 * @since Mar 14, 2009
 */
public class MockitoBundlesOption extends AbstractProvisionWrapperOption<MockitoBundlesOption>
{

    /**
     * Constructor.
     */
    public MockitoBundlesOption()
    {
        super(
            new WrappedUrlProvisionOption( new MavenUrlProvisionOption()
                .groupId( "org.mockito" )
                .artifactId( "mockito-all" )
                .version( "1.7" )
                .noUpdate()
            )
        );
    }

    /**
     * Sets the Mockito version.
     *
     * @param version Mockito version.
     *
     * @return itself, for fluent api usage
     */
    public MockitoBundlesOption version( final String version )
    {
        ( (MavenUrlProvisionOption) getDelegate() ).version( version );
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "MockitoBundlesOption" );
        sb.append( "{url=" ).append( getURL() );
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    protected MockitoBundlesOption itself()
    {
        return this;
    }

}
