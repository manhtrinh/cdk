/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2004-2007  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.io.formats;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.tools.DataFeatures;

/**
 * See <a href="http://www.iupac.org/inchi/">here</a>.
 * 
 * @cdk.module ioformats
 * @cdk.githash
 * @cdk.set    io-formats
 */
@TestClass("org.openscience.cdk.io.formats.INChIFormatTest")
public class INChIFormat extends AbstractResourceFormat implements IChemFormatMatcher {

	private static IResourceFormat myself = null;
	
    public INChIFormat() {}
    
    @TestMethod("testResourceFormatSet")
    public static IResourceFormat getInstance() {
    	if (myself == null) myself = new INChIFormat();
    	return myself;
    }
    
    /** {@inheritDoc} */ @Override
    @TestMethod("testGetFormatName")
    public String getFormatName() {
        return "IUPAC-NIST Chemical Identifier (XML)";
    }

    /** {@inheritDoc} */ @Override
    @TestMethod("testGetMIMEType")
    public String getMIMEType() {
        return null;
    }

    /** {@inheritDoc} */ @Override
    @TestMethod("testGetPreferredNameExtension")
    public String getPreferredNameExtension() {
        return getNameExtensions()[0];
    }

    /** {@inheritDoc} */ @Override
    @TestMethod("testGetNameExtensions")
    public String[] getNameExtensions() {
        return new String[]{"inchi"};
    }

    /** {@inheritDoc} */ @Override
    @TestMethod("testGetReaderClassName")
    public String getReaderClassName() { 
      return "org.openscience.cdk.io.INChIReader";
    }

    /** {@inheritDoc} */ @Override
    @TestMethod("testGetWriterClassName")
    public String getWriterClassName() { return null; }

    /** {@inheritDoc} */ @Override
    @TestMethod("testMatches")
    public boolean matches(int lineNumber, String line) {
        if (line.indexOf("<INChI") != -1) {
            return true;
        }
        return false;
    }

    /** {@inheritDoc} */ @Override
	@TestMethod("testIsXMLBased")
    public boolean isXMLBased() {
		return true;
	}

    /** {@inheritDoc} */ @Override
	@TestMethod("testGetSupportedDataFeatures")
	public int getSupportedDataFeatures() {
		return DataFeatures.NONE;
	}

    /** {@inheritDoc} */ @Override
	@TestMethod("testGetRequiredDataFeatures")
    public int getRequiredDataFeatures() {
		return DataFeatures.NONE;
	}
}
