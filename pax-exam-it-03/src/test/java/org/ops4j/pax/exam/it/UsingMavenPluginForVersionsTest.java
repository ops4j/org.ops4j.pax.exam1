/*
 * Copyright 2008 Alin Dreghiciu
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.it;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.MavenUtils.*;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * How to use the maven plugin for determining maven artifact version integration tests.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, November 18, 2008
 */
@RunWith( JUnit4TestRunner.class )
public class UsingMavenPluginForVersionsTest
{

    /**
     * Pax Exam test options that provisions the Pax URL mvn: url handler bundle.
     *
     * @return integration tests options
     */
    @Configuration
    public static Option[] configureForValidURL()
    {
        return options(
            provision(
                mavenBundle().groupId( "org.ops4j.pax.url" ).artifactId( "pax-url-mvn" ).version( asInProject() ),
                mavenBundle( "org.ops4j.pax.url", "pax-url-war" ).version( asInProject() ),
                mavenBundle( "org.ops4j.pax.url", "pax-url-link" ).versionAsInProject()
            ),
            logProfile()
        );
    }

    /**
     * Tests that the Pax URL mvn: url handler has been provisioned by creating a mvn: url. If the url creation fails,
     * it means that the bundle was not provisioned.
     *
     * @throws MalformedURLException - Not expected
     */
    @Test
    public void validMvnURL()
        throws MalformedURLException
    {
        new URL( "mvn:org.ops4j.pax.swissbox/pax-swissbox-core" );
    }

    /**
     * Tests that the Pax URL war: url handler has been provisioned by creating a war: url. If the url creation fails,
     * it means that the bundle was not provisioned.
     *
     * @throws MalformedURLException - Not expected
     */
    @Test
    public void validWarURL()
        throws MalformedURLException
    {
        new URL( "war:file:foo.war" );
    }

    /**
     * Tests that the Pax URL link: url handler has been provisioned by creating a link: url. If the url creation fails,
     * it means that the bundle was not provisioned.
     *
     * @throws MalformedURLException - Not expected
     */
    @Test
    public void validLinkURL()
        throws MalformedURLException
    {
        new URL( "link:file:foo.link" );
    }

}