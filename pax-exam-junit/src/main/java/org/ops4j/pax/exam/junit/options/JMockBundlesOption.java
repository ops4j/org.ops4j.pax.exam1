package org.ops4j.pax.exam.junit.options;

import org.ops4j.pax.exam.options.AbstractProvisionWrapperOption;
import org.ops4j.pax.exam.options.MavenUrlProvisionOption;
import org.ops4j.pax.exam.options.WrappedUrlProvisionOption;

/**
 * This provides JMock Support for Pax Exam.
 * By default we supply version 2.5.1. Version can be changed.
 *
 * @author Toni Menzel (tonit)
 * @since Mar 15, 2009
 */
public class JMockBundlesOption extends AbstractProvisionWrapperOption<JMockBundlesOption>
{

    /**
     * You'll get a wrapped artifact of jmock version 2.5.1 by default.
     */
    public JMockBundlesOption()
    {
        super(
            new WrappedUrlProvisionOption( new MavenUrlProvisionOption()
                .groupId( "org.jmock" )
                .artifactId( "jmock" )
                .version( "2.5.1" )
                .noUpdate()
            )
        );
    }

    /**
     * Sets the JMock version.
     *
     * @param version JMock version.
     *
     * @return itself, for fluent api usage
     */
    public JMockBundlesOption version( final String version )
    {
        ( (MavenUrlProvisionOption) ( (WrappedUrlProvisionOption) getDelegate() ).getDelegate() ).version( version );
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "JMockBundlesOption" );
        sb.append( "{url=" ).append( getURL() );
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    protected JMockBundlesOption itself()
    {
        return this;
    }

}
