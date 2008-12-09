package org.ops4j.pax.exam.junit.options;

import org.ops4j.pax.exam.options.BundleURLOption;
import org.ops4j.pax.exam.options.MavenBundleURLOption;
import org.ops4j.pax.exam.api.Info;
import static org.ops4j.pax.exam.api.Info.*;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 9, 2008
 * Time: 1:01:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class JUnitBundleOption
    implements BundleURLOption
{

    private final MavenBundleURLOption m_url;

    public JUnitBundleOption()
    {
        m_url = new MavenBundleURLOption()
            .group( "org.ops4j.pax.exam" ).artifact( "com.springsource.org.easymock" ).version( getPaxExamVersion() );
    }

    public JUnitBundleOption version( String version )
    {
        m_url.version( version );
        return this;
    }

}