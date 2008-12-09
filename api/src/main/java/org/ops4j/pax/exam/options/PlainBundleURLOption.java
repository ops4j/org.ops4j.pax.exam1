package org.ops4j.pax.exam.options;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:27:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlainBundleURLOption
    implements BundleURLOption
{

    private final String m_url;

    public PlainBundleURLOption( String url )
    {
        m_url = url;
    }

}