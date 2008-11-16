/*
 * Copyright 2008 Toni Menzel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.drone.commons;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;

/**
 * A wrapper to instantiate a composite without much code.
 *
 * here we assemble a connector using qi4j's runtime.
 * this will just instantiate a SingletonAssembler with
 * one module, one composite (defined by C)
 * 
 *
 * @author Toni Menzel (tonit)
 * @since Jul 21, 2008
 */
public class CompositeProvider<C extends Composite>
{
    public C get( final Class<C> composite )
    {
        SingletonAssembler assembler = new SingletonAssembler()
        {
            public void assemble( ModuleAssembly module )
                throws AssemblyException
            {
                module.addComposites( composite );

            }
        };

        CompositeBuilder<C> builder = assembler.compositeBuilderFactory().newCompositeBuilder( composite );
        return builder.newInstance();
    }
}
