/*
 * Copyright 2008 Alin Dreghiciu.
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
package org.ops4j.pax.exam.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.junit.internal.runners.ClassRoadie;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.MethodValidator;
import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import static org.ops4j.pax.exam.junit.JUnitOptions.*;
import org.ops4j.pax.exam.junit.internal.JUnit4ConfigMethod;
import org.ops4j.pax.exam.junit.internal.JUnit4TestMethod;
import org.ops4j.pax.exam.junit.options.JUnitBundlesOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.FrameworkOption;

/**
 * JUnit4 Runner to be used with the {@link org.junit.runner.RunWith} annotation to run with Pax Exam.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 0.3.0, December 16, 2008
 */
public class JUnit4TestRunner
    extends Runner
    implements Filterable, Sortable
{

    private final List<JUnit4TestMethod> fTestMethods;
    private TestClass fTestClass;

    public JUnit4TestRunner( Class<?> klass )
        throws InitializationError
    {
        fTestClass = new TestClass( klass );
        try
        {
            fTestMethods = getTestMethods();
        }
        catch( Exception e )
        {
            throw new InitializationError( e );
        }
        validate();
    }

    protected List<JUnit4TestMethod> getTestMethods()
        throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        final List<JUnit4ConfigMethod> configMethods = new ArrayList<JUnit4ConfigMethod>();
        for( Method configMethod : fTestClass.getAnnotatedMethods( Configuration.class ) )
        {
            if( !Modifier.isStatic( configMethod.getModifiers() ) )
            {
                System.err.println(
                    "!WARNING: Configuration method [" + configMethod.getName() + "] in [" + fTestClass.getName()
                    + "] must be static"
                );
                continue;
            }
            if( Modifier.isAbstract( configMethod.getModifiers() ) )
            {
                System.err.println(
                    "!WARNING: Configuration method [" + configMethod.getName() + "] in [" + fTestClass.getName()
                    + "] cannot be abstract"
                );
                continue;
            }
            configMethods.add( new JUnit4ConfigMethod( configMethod ) );
        }
        final List<JUnit4TestMethod> methods = new ArrayList<JUnit4TestMethod>();
        final List<Method> testMethods = fTestClass.getAnnotatedMethods( Test.class );
        for( Method testMethod : testMethods )
        {
            final Option configOptions = getOptions( testMethod.getName(), configMethods );
            final FrameworkOption[] frameworkOptions = OptionUtils.filter( FrameworkOption.class, configOptions );
            final Option[] filteredOptions = OptionUtils.remove( FrameworkOption.class, configOptions );
            if( frameworkOptions.length == 0 )
            {
                methods.add( new JUnit4TestMethod( testMethod, fTestClass, null, filteredOptions ) );
            }
            else
            {
                for( FrameworkOption frameworkOption : frameworkOptions )
                {
                    methods.add( new JUnit4TestMethod( testMethod, fTestClass, frameworkOption, filteredOptions ) );
                }
            }
        }
        return methods;
    }

    protected void validate()
        throws InitializationError
    {
        MethodValidator methodValidator = new MethodValidator( fTestClass );
        // skip the validation bellow as we may have BundleContext as parameter
        // TODO shall we validate that just max one param (bundle context) is possible to be used?
        // methodValidator.validateMethodsForDefaultRunner();
        methodValidator.assertValid();
    }

    @Override
    public void run( final RunNotifier notifier )
    {
        new ClassRoadie( notifier, fTestClass, getDescription(), new Runnable()
        {
            public void run()
            {
                runMethods( notifier );
            }
        }
        ).runProtected();
    }

    protected void runMethods( final RunNotifier notifier )
    {
        for( JUnit4TestMethod method : fTestMethods )
        {
            invokeTestMethod( method, notifier );
        }
    }

    @Override
    public Description getDescription()
    {
        Description spec = Description.createSuiteDescription( getName(), classAnnotations() );
        List<JUnit4TestMethod> testMethods = fTestMethods;
        for( JUnit4TestMethod method : testMethods )
        {
            spec.addChild( methodDescription( method ) );
        }
        return spec;
    }

    protected Annotation[] classAnnotations()
    {
        return fTestClass.getJavaClass().getAnnotations();
    }

    protected String getName()
    {
        return getTestClass().getName();
    }

    protected Object createTest()
        throws Exception
    {
        return getTestClass().getConstructor().newInstance();
    }

    protected void invokeTestMethod( JUnit4TestMethod method, RunNotifier notifier )
    {
        Description description = methodDescription( method );
        Object test;
        try
        {
            test = createTest();
        }
        catch( InvocationTargetException e )
        {
            notifier.testAborted( description, e.getCause() );
            return;
        }
        catch( Exception e )
        {
            notifier.testAborted( description, e );
            return;
        }
        new MethodRoadie( test, method, notifier, description ).run();
    }

    protected TestMethod wrapMethod( Method method )
    {
        return new TestMethod( method, fTestClass );
    }

    protected String testName( Method method )
    {
        return method.getName();
    }

    protected String testName( JUnit4TestMethod method )
    {
        return method.getName();
    }

    protected Description methodDescription( Method method )
    {
        return Description.createTestDescription( getTestClass().getJavaClass(), testName( method ),
                                                  testAnnotations( method )
        );
    }

    protected Description methodDescription( JUnit4TestMethod method )
    {
        return Description.createTestDescription( getTestClass().getJavaClass(), testName( method ),
                                                  testAnnotations( method.getJavaMethod() )
        );
    }

    protected Annotation[] testAnnotations( Method method )
    {
        return method.getAnnotations();
    }

    public void filter( Filter filter )
        throws NoTestsRemainException
    {
        for( Iterator<JUnit4TestMethod> iter = fTestMethods.iterator(); iter.hasNext(); )
        {
            JUnit4TestMethod method = iter.next();
            if( !filter.shouldRun( methodDescription( method.getJavaMethod() ) ) )
            {
                iter.remove();
            }
        }
        if( fTestMethods.isEmpty() )
        {
            throw new NoTestsRemainException();
        }
    }

    public void sort( final Sorter sorter )
    {
        Collections.sort( fTestMethods, new Comparator<JUnit4TestMethod>()
        {
            public int compare( JUnit4TestMethod o1, JUnit4TestMethod o2 )
            {
                return sorter.compare( methodDescription( o1 ), methodDescription( o2 ) );
            }
        }
        );
    }

    protected TestClass getTestClass()
    {
        return fTestClass;
    }

    private static Option getOptions( final String methodName,
                                      final Collection<JUnit4ConfigMethod> configMethods )
        throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        // always add the junit extender
        final DefaultCompositeOption option = new DefaultCompositeOption(
            mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam" )
                .version( Info.getPaxExamVersion() ),
            mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam-junit-extender" )
                .version( Info.getPaxExamVersion() ),
            mavenBundle()
                .group( "org.ops4j.pax.exam" )
                .artifact( "pax-exam-junit-extender-impl" )
                .version( Info.getPaxExamVersion() ),
            mavenBundle()
                .group( "org.ops4j.pax.url" )
                .artifact( "pax-url-dir" )
                .version( Info.getPaxUrlVersion() )
        );
        // add options based on available configuration options from the test itself
        for( JUnit4ConfigMethod configMethod : configMethods )
        {
            if( configMethod.matches( methodName ) )
            {
                option.add( configMethod.getOptions() );
            }
        }
        // add junit bundles, if the user did not add junit bundles into configuration
        if( OptionUtils.filter( JUnitBundlesOption.class, option ).length == 0 )
        {
            option.add( junitBundles() );
        }
        return option;
    }

}
