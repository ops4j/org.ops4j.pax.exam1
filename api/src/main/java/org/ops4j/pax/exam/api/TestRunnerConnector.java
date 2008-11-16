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
package org.ops4j.pax.exam.api;

/**
 * A simple astraction for launching a specific recipe (which is just an identifier).
 * It is up to the run-method what actually a recipe is.
 * For unit tests, this will be a specific test method.
 *
 * @author Toni Menzel (tonit)
 * @since May 28, 2008
 */
public interface TestRunnerConnector
{

    /**
     * Just runs the recipe (method) specified.
     * Recipes are local to the RecipeHost:
     * In UnitTests a RecipeHost is the Class and Recipes are its (public) test-Methods.
     *
     * @param provider a pax exam provider that makes up a specific pax exam call
     *
     * @return a summary of the run. Exceptions should be caught es good as possible.
     */
    TestExecutionSummary execute( TestProbeProvider provider );
}
