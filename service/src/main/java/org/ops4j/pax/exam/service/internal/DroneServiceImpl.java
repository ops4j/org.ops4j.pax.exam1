/*
 * Copyright 2008 Toni Menzel
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
package org.ops4j.pax.exam.service.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.exam.api.RecipeException;
import org.ops4j.pax.exam.api.DroneException;
import org.ops4j.pax.exam.api.DroneService;

/**
 * @author Toni Menzel (tonit)
 * @since May 29, 2008
 *
 *        This service is responsible to load the testclass, inject bundleContext, start tests
 */
public class DroneServiceImpl
    implements DroneService
{

    private BundleContext m_bundleContext;

    public DroneServiceImpl( BundleContext ctx )
    {
        NullArgumentException.validateNotNull( ctx, "ctx" );
        m_bundleContext = ctx;
    }

    public String execute()

    {
        // try to load test class
        Bundle testBundle = findTestBundle();

        if( testBundle != null )
        {
            String recipeHosts = (String) testBundle.getHeaders().get( DroneService.DRONE_RECIPE_HOST_HEADER );

            for( String recipeHost : recipeHosts.split( "," ) )
            {
                Class clazz;
                try
                {
                    clazz = m_bundleContext.getBundle().loadClass( recipeHost );
                }
                catch( ClassNotFoundException e )
                {
                    throw new DroneException( "Recipe Host class not found: " + recipeHost );
                }

                if( clazz != null )
                {
                    // find method
                    String recipe = (String) testBundle.getHeaders().get( DroneService.DRONE_RECIPE_CODE_HEADER );

                    for( Method m : clazz.getDeclaredMethods() )
                    {
                        if( m.getName().equals( recipe ) )
                        {
                            try
                            {
                                injectContextAndInvoke( clazz, m, clazz.newInstance(), m_bundleContext );
                            }
                            catch( InstantiationException e )
                            {
                                throw new DroneException( "InstantiationException for : " + clazz + "," + m, e );
                            }
                            catch( IllegalAccessException e )
                            {
                                throw new DroneException( "IllegalAccessException for : " + clazz + "," + m, e );
                            }
                            catch( RecipeException e )
                            {
                                throw new DroneException( "Wrap the RecipeException", e );
                            }
                        }
                    }
                }
                else
                {
                    throw new DroneException( "RecipeHost " + recipeHost + " not found!" );
                }

            }
        }
        else
        {
            throw new DroneException(
                "No recipe host found (bundle that has a valid  " + DroneService.DRONE_RECIPE_HOST_HEADER + " header)"
            );
        }
        return "";
    }

    private void injectContextAndInvoke( Class clazz, Method m, Object o, BundleContext bundleContext )
        throws RecipeException
    {
        try
        {
            Field field = clazz.getField( "bundleContext" );
            if( field != null )
            {
                field.setAccessible( true );
                field.set( o, bundleContext );
            }
        }
        catch( NoSuchFieldException e )
        {
            // skip that
        }
        catch( IllegalAccessException e )
        {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try
        {
            if( m.getParameterTypes().length == 1 )
            {
                // must be of type DroneContext ! TODO add additional validation
                m.invoke( o, bundleContext );
            }
            else
            {
                m.invoke( o );
            }
        }
        catch( Exception e )
        {
            throw new RecipeException( e );
        }
    }

    private Bundle findTestBundle()
    {
        for( Bundle bundle : m_bundleContext.getBundles() )
        {
            String tests = (String) bundle.getHeaders().get( DroneService.DRONE_RECIPE_HOST_HEADER );
            if( tests != null )
            {
                return bundle;
            }
        }

        return null;
    }
}
