package org.ops4j.pax.exam.junit;

import org.ops4j.pax.exam.junit.options.EasyMockBundleOption;
import org.ops4j.pax.exam.junit.options.JUnitBundleOption;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 9, 2008
 * Time: 12:59:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class JUnitOptions
{

    public static EasyMockBundleOption easyMockBundle()
    {
        return new EasyMockBundleOption();
    }

    public static JUnitBundleOption junitBundle()
    {
        return new JUnitBundleOption();
    }

}
