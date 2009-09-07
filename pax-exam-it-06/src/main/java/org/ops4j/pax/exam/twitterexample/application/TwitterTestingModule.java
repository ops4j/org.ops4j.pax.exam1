package org.ops4j.pax.exam.twitterexample.application;

import com.google.inject.AbstractModule;
import org.ops4j.pax.exam.twitterexample.api.TwitterService;
import org.ops4j.pax.exam.twitterexample.api.TwitterBackend;
import org.ops4j.pax.exam.twitterexample.backend.TwitterBackendImpl;
import org.ops4j.pax.exam.twitterexample.service.MockTwitterImpl;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Sep 1, 2009
 * Time: 10:13:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class TwitterTestingModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind( TwitterService.class ).to( MockTwitterImpl.class );
        bind( TwitterBackend.class ).to( TwitterBackendImpl.class );
    }
}
