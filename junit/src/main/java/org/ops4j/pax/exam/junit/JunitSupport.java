package org.ops4j.pax.exam.junit;

import org.ops4j.pax.exam.api.Info;

/**
 * @author Toni Menzel (tonit)
 * @since Nov 19, 2008
 */
public class JunitSupport {
    public static String[] bundles() {
        return new String[]{"wrap:mvn:org.ops4j.pax.exam/pax-exam-junit/" + Info.getPaxExamVersion()};
    }
}
