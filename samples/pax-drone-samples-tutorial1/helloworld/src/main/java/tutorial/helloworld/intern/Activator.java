package tutorial.helloworld.intern;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Toni Menzel (tonit)
 * @since Jul 13, 2008
 */
public class Activator implements BundleActivator {
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Hello from OSGi 3");
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Stopping Helloworld Bundle");

    }
}
