/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 1997-2005  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 */

package org.openscience.cdk.test.io.cml;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestSuite for doing regression tests on the org.openscience.cdk.io.cml
 * package.
 *
 * @cdk.module test
 */
public class CMLIOTests {

    public static void main (String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite () {
        TestSuite suite= new TestSuite("The cdk.io.cml Tests");
        suite.addTest(JumboTest.suite());
        // suite.addTest(JmolTest.suite());
        suite.addTest(JChemPaintTest.suite());
        suite.addTest(CML2Test.suite());
        suite.addTest(CMLFragmentsTest.suite());
        suite.addTest(Jumbo46CMLFragmentsTest.suite());
        
        // the following classes require Java 1.4
        try {
            Class testClass = ClassLoader.getSystemClassLoader().loadClass("org.openscience.cdk.test.io.CML2WriterTest");
            suite.addTest(new TestSuite(testClass));
        } catch (Exception exception) {
            // ok, do without. Probably compiled not Java 1.4
            System.out.println("Could not load the CML2Writer test: " + exception.getMessage());
        }
        try {
            Class testClass = ClassLoader.getSystemClassLoader().loadClass("org.openscience.cdk.test.io.CMLRoundTripTest");
            suite.addTest(new TestSuite(testClass));
        } catch (Exception exception) {
            // ok, do without. Probably compiled not Java 1.4
            System.out.println("Could not load the CML Roundtrip test: " + exception.getMessage());
        }
        return suite;
    }

}
