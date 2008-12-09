package org.ops4j.pax.exam.options;

/**
 * Created by IntelliJ IDEA.
 * User: alindreghiciu
 * Date: Dec 8, 2008
 * Time: 7:27:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MavenBundleURLOption
    implements BundleURLOption
{

    public MavenBundleURLOption group(String groupId )
    {
        return this;
    }

    public MavenBundleURLOption artifact(String artifactId )
    {
        return this;
    }

    public MavenBundleURLOption type(String type )
    {
        return this;
    }

    public MavenBundleURLOption version(String version )
    {
        return this;
    }    

}