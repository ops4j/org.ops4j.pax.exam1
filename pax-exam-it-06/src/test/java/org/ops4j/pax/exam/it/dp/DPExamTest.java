package org.ops4j.pax.exam.it.dp;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.osgi.service.deploymentadmin.DeploymentAdmin;
import org.osgi.service.deploymentadmin.DeploymentException;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.*;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import static org.ops4j.pax.swissbox.tinybundles.dp.DP.*;
import org.ops4j.pax.swissbox.tinybundles.dp.Constants;

/**
 * @author Toni Menzel (tonit)
 * @since Jun 14, 2009
 */

@RunWith( JUnit4TestRunner.class )
public class DPExamTest
{

    @Configuration
    public static Option[] configure()
    {
        return options(
            mavenBundle()
                .groupId( "org.ops4j.pax.swissbox" )
                .artifactId( "pax-swissbox-tinybundles" )
                .version( "1.0.1-SNAPSHOT" ),
            mavenBundle()
                .groupId( "org.ops4j.base" )
                .artifactId( "ops4j-base-io" )
                .version( "1.0.0" ),
            mavenBundle()
                .groupId( "org.ops4j.base" )
                .artifactId( "ops4j-base-monitors" )
                .version( "1.0.0" ),
            mavenBundle()
                .groupId( "org.ops4j.base" )
                .artifactId( "ops4j-base-lang" )
                .version( "1.0.0" ),
            mavenBundle()
                .groupId( "org.ops4j.base" )
                .artifactId( "ops4j-base-net" )
                .version( "1.0.0" ),
            /**
             mavenBundle()
             .groupId( "org.ops4j.pax.swissbox" )
             .artifactId( "samples-tinybundles" )
             .version( "0.3.0-SNAPSHOT" ),
             **/
            profiles( profile( "url" ) ),
            mavenBundle()
                .groupId( "org.apache.felix" )
                .artifactId( "org.apache.felix.deploymentadmin" )
                .version( "0.9.0-SNAPSHOT" )
            ,
            mavenBundle()
                .groupId( "org.apache.felix" )
                .artifactId( "org.apache.felix.dependencymanager" )
                .version( "2.0.2-SNAPSHOT" )
        );
    }

    @Inject
    BundleContext context;

    @Test
    public void test1()
        throws IOException, DeploymentException
    {
        DeploymentAdmin admin =
            (DeploymentAdmin) context.getService( context.getServiceReference( DeploymentAdmin.class.getName() ) );

        admin.installDeploymentPackage(
            newDeploymentPackage()
                .set( Constants.DEPLOYMENTPACKAGE_SYMBOLICMAME, "MyFirstDeploymentPackage" )
                .set( Constants.DEPLOYMENTPACKAGE_VERSION, "1.0.0" )
                .addBundle( "logging-api.jar", "mvn:org.ops4j.pax.logging/pax-logging-api/1.3.0" )
                .addBundle( "logging-service.jar", "mvn:org.ops4j.pax.logging/pax-logging-service/1.3.0" )
                .build()
        );

        System.out.println( "Round 1" );

        for( Bundle b : context.getBundles() )
        {
            System.out.println( "Bundle: " + b.getBundleId() + " " + b.getSymbolicName() + " " );
        }

        admin.installDeploymentPackage(
            newDeploymentPackage()
                .set( Constants.DEPLOYMENTPACKAGE_SYMBOLICMAME, "MyFirstDeploymentPackage" )
                .set( Constants.DEPLOYMENTPACKAGE_VERSION, "1.0.0" )
                .addBundle( "logging-api.jar", "mvn:org.ops4j.pax.logging/pax-logging-api/1.3.0" )
                .build()
        );

        System.out.println( "Round 2" );
        for( Bundle b : context.getBundles() )
        {
            System.out.println( "Bundle: " + b.getBundleId() + " " + b.getSymbolicName() + " " );
        }
    }
}
