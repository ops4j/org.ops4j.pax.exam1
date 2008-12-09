package org.ops4j.pax.exam.junit.options;

import org.ops4j.pax.exam.options.BundleURLOption;
import org.ops4j.pax.exam.options.MavenBundleURLOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 9, 2008
 * Time: 1:01:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class EasyMockBundleOption
    implements BundleURLOption
{

    private final MavenBundleURLOption m_url;

    public EasyMockBundleOption()
    {
        m_url = new MavenBundleURLOption()
            .group( "org.easymock" ).artifact( "pax-exam-junit" ).version( "2.3" );
    }

    public EasyMockBundleOption version( String version )
    {
        m_url.version( version );
        return this;
    }

}
