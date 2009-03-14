package org.ops4j.pax.exam.junit.options;

import org.ops4j.pax.exam.options.AbstractProvisionWrapperOption;
import org.ops4j.pax.exam.options.MavenUrlProvisionOption;
import org.ops4j.pax.exam.options.WrappedUrlProvisionOption;

/**
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
            new WrappedUrlProvisionOption(
                "mvn:org.mockito/mockito-all/1.7"
        ));
    }

    /**
     * Sets the easymock version.
     *
     * @param version easymock version.
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
