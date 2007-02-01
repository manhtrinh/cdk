/*  $RCSfile: $
 *  $Author$
 *  $Date$
 *  $Revision$
 *
 *  Copyright (C) 1997-2007  The Chemistry Development Kit (CDK) project
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.test.smiles;

import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openscience.cdk.Atom;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.PseudoAtom;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.isomorphism.IsomorphismTester;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.test.CDKTestCase;

/**
 * Please see the test.gui package for visual feedback on tests.
 * 
 * @author         steinbeck
 * @cdk.module     test-smiles
 * @cdk.created    2003-09-19
 * 
 * @see org.openscience.cdk.test.gui.SmilesParserTest
 */
public class SmilesParserTest extends CDKTestCase {
	
	private SmilesParser sp;

	/**
	 *  Constructor for the SmilesParserTest object
	 *
	 *@param  name  Description of the Parameter
	 */
	public SmilesParserTest(String name) {
		super(name);
	}

	public void setUp() {
		sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
	}

	/**
	 *  A unit test suite for JUnit
	 *
	 *@return    The test suite
	 */
	public static Test suite()
	{
		return new TestSuite(SmilesParserTest.class);
	}

	
	public void testPyridine_N_oxideUncharged() throws Exception {
		String smiles = "O=n1ccccc1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(7, mol.getAtomCount());
	}
	
	public void testPyridine_N_oxideCharged() throws Exception {
		String smiles = "[O-][n+]1ccccc1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(7, mol.getAtomCount());
	}

	/**
	 *  This method tests compounds with several conjugated rings
	 *  These compounds would not fail if the Aromaticity Detection was changed
	 *  so that a ring is aromatic if all the atoms in a ring have already been flagged
	 *  as aromatic from the testing of other rings in the system 
	 * @throws Exception
	 */
	public void testUnusualConjugatedRings() throws Exception {
		
		//7090-41-7:
		String smiles = "c1(Cl)cc2c3cc(Cl)c(Cl)cc3c2cc1Cl";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(16, mol.getAtomCount());
				
		//206-44-0:
		smiles="c(c(ccc1)ccc2)(c1c(c3ccc4)c4)c23";
		mol = sp.parseSmiles(smiles);
		assertEquals(16, mol.getAtomCount());
			
		//207-08-9:
		smiles="c2ccc1cc3c(cc1c2)c4cccc5cccc3c45";
		mol = sp.parseSmiles(smiles);
		assertEquals(20, mol.getAtomCount());
		
		//2693-46-1:
		smiles="Nc1c(c23)cccc3c4ccccc4c2cc1";
		mol = sp.parseSmiles(smiles);
		assertEquals(17, mol.getAtomCount());
		
		//205-99-2:
		smiles="c12ccccc1cc3c4ccccc4c5c3c2ccc5";
		mol = sp.parseSmiles(smiles);
		assertEquals(20, mol.getAtomCount());

	}

	public void test187_78_0 () throws Exception {
		// are all 4 rings aromatic? Is smiles correct?
		String smiles = "c1c(c23)ccc(c34)ccc4ccc2c1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(14, mol.getAtomCount());
	}
	
	public void test187_78_0_PubChem() throws Exception {
		// are all 4 rings aromatic? Is smiles correct?
		String smiles = "C1=CC2=C3C(=CC=C4C3=C1C=C4)C=C2";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(14, mol.getAtomCount());
	}

	public void test41814_78_2 () throws Exception {
		String smiles = "Cc1cccc2sc3nncn3c12";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(13, mol.getAtomCount());
	}
	
	public void test239_64_5 () throws Exception {
		String smiles = "c1ccc4c(c1)ccc5c3ccc2ccccc2c3nc45";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(21, mol.getAtomCount());
	}
	
	/*
	 * Compounds like Indolizine (274-40-8) with a fused nitrogen as part of a 6 membered ring
	 * and another ring do not parse
	 */
	public void testIndolizine () throws Exception {
		String smiles = "c2cc1cccn1cc2";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(9, mol.getAtomCount());
	}
	
	
	/**
	 *  A unit test for JUnit
	 */
	public void testSmiles1() throws Exception {
		String smiles = "C1c2c(c3c(c(O)cnc3)cc2)CC(=O)C1";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertEquals(16, molecule.getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSmiles2() throws Exception {
		String smiles = "O=C(O3)C1=COC(OC4OC(CO)C(O)C(O)C4O)C2C1C3C=C2COC(C)=O";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertEquals(29, molecule.getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSmiles3()
	{
		try
		{
			String smiles = "CN1C=NC2=C1C(N(C)C(N2C)=O)=O";
			IMolecule molecule = sp.parseSmiles(smiles);
			assertEquals(14, molecule.getAtomCount());
		} catch (Exception exception)
		{
			fail(exception.getMessage());
		}
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSmiles4() throws Exception {
		String smiles = "CN(C)CCC2=CNC1=CC=CC(OP(O)(O)=O)=C12";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertEquals(19, molecule.getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSmiles5() throws Exception {
		String smiles = "O=C(O)C1C(OC(C3=CC=CC=C3)=O)CC2N(C)C1CC2";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertEquals(21, molecule.getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSmiles6() throws Exception {
		String smiles = "C1(C2(C)(C))C(C)=CCC2C1";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertEquals(10, molecule.getAtomCount());
	}


	/**
	 *  Description of the Method
	 */
	public void testSmiles7() throws Exception {
		String smiles = "C1(C=C(C=C(C=C(C=C(C=CC%35=C%36)C%31=C%35C%32=C%33C%36=C%34)C%22=C%31C%23=C%32C%24=C%25C%33=C%26C%34=CC%27=CC%28=CC=C%29)C%14=C%22C%15=C%23C%16=C%24C%17=C%18C%25=C%19C%26=C%27C%20=C%28C%29=C%21)C6=C%14C7=C%15C8=C%16C9=C%17C%12=C%11C%18=C%10C%19=C%20C%21=CC%10=CC%11=CC(C=C%30)=C%12%13)=C(C6=C(C7=C(C8=C(C9=C%13C%30=C5)C5=C4)C4=C3)C3=C2)C2=CC=C1";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertNotNull(molecule);
	}


	/**
	 *  Description of the Method
	 */
	public void testSmiles8() throws Exception {
		String smiles = "CC1(C(=C(CC(C1)O)C)C=CC(=CC=CC(=CC=CC=C(C=CC=C(C=CC1=C(CC(CC1(C)C)O)C)C)C)C)C)C";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertNotNull(molecule);
	}


	/**
	 *  Description of the Method
	 */
	public void testSmiles9() throws Exception {
		String smiles = "NC(C(C)C)C(NC(C(C)O)C(NC(C(C)C)C(NC(CCC(N)=O)C(NC(CC([O-])[O-])C(NCC(NC(CC(N)=O)C(NC(Cc1ccccc1)C(NC(CO)C(NC(Cc2ccccc2)C(NC(CO)C(NC(CC(C)C)C(NC(CCC([O-])[O-])C(NC(CO)C(NC(C(C)C)C(NC(CCCC[N+])C(NC(CCCC[N+])C(NC(CC(C)C)C(NC(CCCC[N+])C(NC(CC([O-])[O-])C(NC(CC(C)C)C(NC(CCC(N)=O)C(NC(CCC([O-])[O-])C(N3CCCC3C(NC(CCC(N)=O)C(NC(CCC([O-])[O-])C(N4CCCC4C(NC(CCCNC([N+])[N+])C(NC(C(C)C)C(NCC(NC(CCCC[N+])C(NC(CC(C)C)C(NC(CCCNC([N+])[N+])C(NC(CC(N)=O)C(NC(Cc5ccccc5)C(NC(C)C(N6CCCC6C(NC(C(C)CC)C(N7CCCC7C(NCC(NC(CCC([O-])[O-])C(N8CCCC8C(NC(C(C)C)C(NC(C(C)C)C(N9CCCC9C(NC(C(C)CC)C(NC(CC(C)C)C(NC%19C[S][S]CC(C(NC(CCCC[N+])C(NC(CCC([O-])[O-])C(N%10CCCC%10C(NC(CC(N)=O)C(NC(C)C(NC(CCC(N)=O)C(NC(CCC([O-])[O-])C(NC(C(C)CC)C(NC(CC(C)C)C(NC(CCC(N)=O)C(NC(CCCNC([N+])[N+])C(NC(CC(C)C)C(NC(CCC([O-])[O-])C(NC(CCC([O-])[O-])C(NC(C(C)CC)C(NC(C)C(NC(CCC([O-])[O-])C(NC(CC([O-])[O-])C(N%11CCCC%11C(NCC(NC(C(C)O)C(NC%14C[S][S]CC%13C(NC(C(C)O)C(NCC(NC(C[S][S]CC(C(NC(C)C(NC(Cc%12ccc(O)cc%12)C(NC(C)C(NC(C)C(N%13)=O)=O)=O)=O)=O)NC(=O)C(C(C)CC)NC(=O)C(CCC([O-])[O-])NC%14=O)C(O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)NC(=O)C(CC(C)C)NC(=O)C%15CCCN%15C(=O)C(CCCC[N+])NC(=O)C(CC(C)C)NC(=O)C(CCC([O-])[O-])NC(=O)C(CCC([O-])[O-])NC(=O)C%16CCCN%16C(=O)C(Cc%17ccccc%17)NC(=O)C(CC(N)=O)NC(=O)C%18CCCN%18C(=O)C(CC(N)=O)NC(=O)C(CO)NC%19=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O)=O";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertNotNull(molecule);
	}

	/**
	 * @cdk.bug 1296113
	 */
	public void testSFBug1296113() throws Exception {
		String smiles = "S(=O)(=O)(-O)-c1c2c(c(ccc2-N-c2ccccc2)-N=N-c2c3c(c(cc2)-N=N-c2c4c(c(ccc4)-S(=O)(=O)-O)ccc2)cccc3)ccc1";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertNotNull(molecule);
	}

    /**
     * @cdk.bug 1324105
     */
    public void testAromaticSmiles2() throws Exception {
    	String smiles = "n12:n:n:n:c:2:c:c:c:c:1";
    	IMolecule molecule = sp.parseSmiles(smiles);
    	Iterator bonds = molecule.bonds();
    	while (bonds.hasNext()) assertTrue(((IBond)bonds.next()).getFlag(CDKConstants.ISAROMATIC));
    }

	/**
	 *  A unit test for JUnit
	 */
	public void testAromaticSmilesWithCharge() throws Exception {
		String smiles = "c1cc[c-]cc1";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertTrue(molecule.getAtom(0).getFlag(CDKConstants.ISAROMATIC));
		assertTrue(molecule.getBond(0).getFlag(CDKConstants.ISAROMATIC));
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testAromaticSmiles() throws Exception {
		String smiles = "c1ccccc1";
		IMolecule molecule = sp.parseSmiles(smiles);
		assertTrue(molecule.getAtom(0).getFlag(CDKConstants.ISAROMATIC));
		assertTrue(molecule.getBond(0).getFlag(CDKConstants.ISAROMATIC));
	}
	
	
	/**
	 * @cdk.bug 630475
	 */
	public void testSFBug630475() throws Exception {
		String smiles = "CC1(C(=C(CC(C1)O)C)C=CC(=CC=CC(=CC=CC=C(C=CC=C(C=CC1=C(CC(CC1(C)C)O)C)C)C)C)C)C";
		IMolecule mol = sp.parseSmiles(smiles);
		assertTrue(mol.getAtomCount() > 0);
	}


	/**
	 * @cdk.bug 585811
	 */
	public void testSFBug585811() throws Exception {
		String smiles = "CC(C(C8CCC(CC8)=O)C3C4C(CC5(CCC(C9=CC(C=CN%10)=C%10C=C9)CCCC5)C4)C2CCC1CCC7(CCC7)C6(CC6)C1C2C3)=O";
		IMolecule mol = sp.parseSmiles(smiles);
		assertTrue(mol.getAtomCount() > 0);
	}


	/**
	 * @cdk.bug 593648
	 */
	public void testSFBug593648() throws Exception {
		String smiles = "CC1=CCC2CC1C(C)2C";
		IMolecule mol = sp.parseSmiles(smiles);
		
		Molecule apinene = new Molecule();
		apinene.addAtom(new Atom("C"));
		// 1
		apinene.addAtom(new Atom("C"));
		// 2
		apinene.addAtom(new Atom("C"));
		// 3
		apinene.addAtom(new Atom("C"));
		// 4
		apinene.addAtom(new Atom("C"));
		// 5
		apinene.addAtom(new Atom("C"));
		// 6
		apinene.addAtom(new Atom("C"));
		// 7
		apinene.addAtom(new Atom("C"));
		// 8
		apinene.addAtom(new Atom("C"));
		// 9
		apinene.addAtom(new Atom("C"));
		// 10
		
		apinene.addBond(0, 1, 2.0);
		// 1
		apinene.addBond(1, 2, 1.0);
		// 2
		apinene.addBond(2, 3, 1.0);
		// 3
		apinene.addBond(3, 4, 1.0);
		// 4
		apinene.addBond(4, 5, 1.0);
		// 5
		apinene.addBond(5, 0, 1.0);
		// 6
		apinene.addBond(0, 6, 1.0);
		// 7
		apinene.addBond(3, 7, 1.0);
		// 8
		apinene.addBond(5, 7, 1.0);
		// 9
		apinene.addBond(7, 8, 1.0);
		// 10
		apinene.addBond(7, 9, 1.0);
		// 11
		
		IsomorphismTester it = new IsomorphismTester(apinene);
		assertTrue(it.isIsomorphic(mol));
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testReadingOfTwoCharElements() throws Exception {
		String smiles = "[Na]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals("Na", mol.getAtom(0).getSymbol());
	}

	public void testReadingOfOneCharElements() throws Exception {
		String smiles = "[K]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals("K", mol.getAtom(0).getSymbol());
	}

	/**
	 *  A unit test for JUnit
	 */
	public void testOrganicSubsetUnderstanding() throws Exception {
		String smiles = "[Ni]";
		SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals("Ni", mol.getAtom(0).getSymbol());

		smiles = "Ni";
		mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals("N", mol.getAtom(0).getSymbol());
		assertEquals("I", mol.getAtom(1).getSymbol());
	}

	/**
	 *  A unit test for JUnit
	 */
	public void testMassNumberReading() throws Exception {
		String smiles = "[13C]";
		SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals("C", mol.getAtom(0).getSymbol());
		assertEquals(13, mol.getAtom(0).getMassNumber());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testFormalChargeReading() throws Exception {
		String smiles = "[OH-]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals("O", mol.getAtom(0).getSymbol());
		assertEquals(-1, mol.getAtom(0).getFormalCharge());
	}

	/**
	 *  A unit test for JUnit
	 */
	public void testReadingPartionedMolecules() throws Exception {
		String smiles = "[Na+].[OH-]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(0, mol.getBondCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testExplicitSingleBond() throws Exception {
		String smiles = "C-C";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(1, mol.getBondCount());
		assertEquals(1.0, mol.getBond(0).getOrder(), 0.0001);
	}


	/**
	 * @cdk.bug 1175478
	 */
	public void testSFBug1175478() throws Exception {
		String smiles = "c1cc-2c(cc1)C(c3c4c2onc4c(cc3N5CCCC5)N6CCCC6)=O";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(27, mol.getAtomCount());
		assertEquals(32, mol.getBondCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testUnkownAtomType() throws Exception {
		String smiles = "*C";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(1, mol.getBondCount());
		assertTrue(mol.getAtom(0) instanceof PseudoAtom);
		assertFalse(mol.getAtom(1) instanceof PseudoAtom);

		smiles = "[*]C";
		mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(1, mol.getBondCount());
		assertTrue(mol.getAtom(0) instanceof PseudoAtom);
		assertFalse(mol.getAtom(1) instanceof PseudoAtom);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testBondCreation() throws Exception {
		String smiles = "CC";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(1, mol.getBondCount());

		smiles = "cc";
		mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(1, mol.getBondCount());
	}


	/**
	 * @cdk.bug 784433
	 */
	public void testSFBug784433() throws Exception {
		String smiles = "c1cScc1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(5, mol.getAtomCount());
		assertEquals(5, mol.getBondCount());
	}


	/**
	 * @cdk.bug 873783.
	 */
	public void testProton() throws Exception {
		String smiles = "[H+]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals(1, mol.getAtom(0).getFormalCharge());
	}


	/**
	 * @cdk.bug 881330.
	 */
	public void testSMILESFromXYZ() throws Exception {
		String smiles = "C.C.N.[Co].C.C.C.[H].[He].[H].[H].[H].[H].C.C.[H].[H].[H].[H].[H]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(20, mol.getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSingleBracketH() throws Exception {
		String smiles = "[H]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testSingleH() {
		try {
			String smiles = "H";
			sp.parseSmiles(smiles);
			fail("The SMILES string 'H' is not valid: H is not in the organic element subset");
		} catch (Exception e) {
			// yes! it should fail
		}
	}


	/**
	 * @cdk.bug 862930.
	 */
	public void testHydroxonium() throws Exception {
		String smiles = "[H][O+]([H])[H]";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(4, mol.getAtomCount());
	}


	/**
	 * @cdk.bug 809412
	 */
	public void testSFBug809412() throws Exception {
		String smiles = "Nc4cc3[n+](c2c(c1c(cccc1)cc2)nc3c5c4cccc5)c6c7c(ccc6)cccc7";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(33, mol.getAtomCount());
	}


	/**
	 * A bug found with JCP.
	 *  
	 * @cdk.bug 956926
	 */
	public void testSFBug956926() throws Exception {
		String smiles = "[c+]1ccccc1";
		// C6H5+, phenyl cation
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(6, mol.getAtomCount());
		// it's a bit hard to detect three double bonds in the phenyl ring
		// but I do can check the total order in the whole molecule
		double totalBondOrder = 0.0;
		Iterator bonds = mol.bonds();
		while (bonds.hasNext())
			totalBondOrder += ((IBond)bonds.next()).getOrder();
		assertEquals(9.0, totalBondOrder, 0.001);
		// I can also check wether all carbons have exact two neighbors
		for (int i = 0; i < mol.getAtomCount(); i++)
		{
			assertEquals(2, mol.getConnectedAtomsCount(mol.getAtom(i)));
		}
		// and the number of implicit hydrogens
		int hCount = 0;
		for (int i = 0; i < mol.getAtomCount(); i++)
		{
			hCount += mol.getAtom(i).getHydrogenCount();
		}
		assertEquals(5, hCount);
	}


	/**
	 * A bug found with JCP.
	 * 
	 * @cdk.bug 956929 
	 */
	public void testPyrole() throws Exception {
		String smiles = "c1cccn1";
		IMolecule mol = sp.parseSmiles(smiles);

		StructureDiagramGenerator sdg=new StructureDiagramGenerator(mol);
		sdg.generateCoordinates();

		/*MoleculeViewer2D v2d=new MoleculeViewer2D(mol);
		    v2d.display();

		    Thread.sleep(100000);*/

		for(int i=0;i<mol.getAtomCount();i++){
			if(mol.getAtom(i).getSymbol().equals("N")){
				assertEquals(1,((IBond)mol.getConnectedBondsList(mol.getAtom(i)).get(0)).getOrder(),.1);
				assertEquals(1,((IBond)mol.getConnectedBondsList(mol.getAtom(i)).get(1)).getOrder(),.1);
			}
		}
	}

	/**
	 * A bug found with JCP.
	 * 
	 * @cdk.bug 956929 
	 */
	public void testSFBug956929() throws Exception {
		String smiles = "Cn1cccc1";
		IMolecule mol = sp.parseSmiles(smiles);

		StructureDiagramGenerator sdg=new StructureDiagramGenerator(mol);
		sdg.generateCoordinates();
		assertEquals(6, mol.getAtomCount());
		// it's a bit hard to detect two double bonds in the pyrrole ring
		// but I do can check the total order in the whole molecule
		double totalBondOrder = 0.0;
		Iterator bonds = mol.bonds();
		while (bonds.hasNext())
			totalBondOrder += ((IBond)bonds.next()).getOrder();
		assertEquals(8.0, totalBondOrder, 0.001);
		// I can also check wether the total neighbor count around the
		// nitrogen is 3, all single bonded
		org.openscience.cdk.interfaces.IAtom nitrogen = mol.getAtom(1);
		// the second atom
		assertEquals("N", nitrogen.getSymbol());
		totalBondOrder = 0.0;
		List bondsList = mol.getConnectedBondsList(nitrogen);
		assertEquals(3, bondsList.size());
		for (int i = 0; i < bondsList.size(); i++)
		{
			totalBondOrder += ((IBond)bondsList.get(i)).getOrder();
		}
		assertEquals(3.0, totalBondOrder, 0.001);
	}


	/**
	 * A bug found with JCP.
	 * 
	 * @cdk.bug 956921
	 */
	public void testSFBug956921() throws Exception {
		String smiles = "[cH-]1cccc1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(5, mol.getAtomCount());
		// each atom should have 1 implicit hydrogen, and two neighbors
		java.util.Iterator atoms = mol.atoms();
		while (atoms.hasNext())
		{
			IAtom atomi = (IAtom)atoms.next();
			assertEquals(1, atomi.getHydrogenCount());
			assertEquals(2, mol.getConnectedAtomsCount(atomi));
		}
		// and the first atom should have a negative charge
		assertEquals(-1, mol.getAtom(0).getFormalCharge());
	}


	/**
	 * @cdk.bug 1095696
	 */
	public void testSFBug1095696() throws Exception {
		String smiles = "Nc1ncnc2[nH]cnc12";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(10, mol.getAtomCount());
		assertEquals("N", mol.getAtom(6).getSymbol());
		assertEquals(1, mol.getAtom(6).getHydrogenCount());
	}


	/**
	 *  Example taken from 'Handbook of Chemoinformatics', Gasteiger, 2003, page 89
	 *  (Part I).
	 */
	public void testNonBond() throws Exception {
		String sodiumPhenoxide = "c1cc([O-].[Na+])ccc1";
		IMolecule mol = sp.parseSmiles(sodiumPhenoxide);
		assertEquals(8, mol.getAtomCount());
		assertEquals(7, mol.getBondCount());
		
		IMoleculeSet fragments = ConnectivityChecker.partitionIntoMolecules(mol);
		int fragmentCount = fragments.getMoleculeCount();
		assertEquals(2, fragmentCount);
		org.openscience.cdk.interfaces.IMolecule mol1 = fragments.getMolecule(0);
		org.openscience.cdk.interfaces.IMolecule mol2 = fragments.getMolecule(1);
		// one should have one atom, the other seven atoms
		// in any order, so just test the difference
		assertEquals(6, Math.abs(mol1.getAtomCount() - mol2.getAtomCount()));
	}


	/**
	 *  Example taken from 'Handbook of Chemoinformatics', Gasteiger, 2003, page 89
	 *  (Part I).
	 */
	public void testConnectedByRingClosure() throws Exception {
		String sodiumPhenoxide = "C1.O2.C12";
		IMolecule mol = sp.parseSmiles(sodiumPhenoxide);
		assertEquals(3, mol.getAtomCount());
		assertEquals(2, mol.getBondCount());
		
		IMoleculeSet fragments = ConnectivityChecker.partitionIntoMolecules(mol);
		int fragmentCount = fragments.getMoleculeCount();
		assertEquals(1, fragmentCount);
		org.openscience.cdk.interfaces.IMolecule mol1 = fragments.getMolecule(0);
		assertEquals(3, mol1.getAtomCount());
	}


	/**
	 *  Example taken from 'Handbook of Chemoinformatics', Gasteiger, 2003, page 89
	 *  (Part I).
	 */
	public void testReaction() throws Exception {
		String reactionSmiles = "O>>[H+].[OH-]";
		IReaction reaction = sp.parseReactionSmiles(reactionSmiles);
		assertEquals(1, reaction.getReactantCount());
		assertEquals(2, reaction.getProductCount());
	}


	/**
	 *  Example taken from 'Handbook of Chemoinformatics', Gasteiger, 2003, page 90
	 *  (Part I).
	 */
	public void testReactionWithAgents() throws Exception {
		String reactionSmiles = "CCO.CC(=O)O>[H+]>CC(=O)OCC.O";
		IReaction reaction = sp.parseReactionSmiles(reactionSmiles);
		assertEquals(2, reaction.getReactantCount());
		assertEquals(2, reaction.getProductCount());
		assertEquals(1, reaction.getAgents().getMoleculeCount());
		
		assertEquals(1, reaction.getAgents().getMolecule(0).getAtomCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount() throws Exception {
		String smiles = "C";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(1, mol.getAtomCount());
		assertEquals(4, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount2() throws Exception {
		String smiles = "CC";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(3, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount2b() throws Exception {
		String smiles = "C=C";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(2, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount2c() throws Exception {
		String smiles = "C#C";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(2, mol.getAtomCount());
		assertEquals(1, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount3() throws Exception {
		String smiles = "CCC";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(3, mol.getAtomCount());
		assertEquals(2, mol.getAtom(1).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount4() throws Exception {
		String smiles = "C1CCCCC1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(6, mol.getAtomCount());
		assertEquals(2, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount4a() throws Exception {
		String smiles = "c1=cc=cc=c1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(6, mol.getAtomCount());
		assertEquals(1, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testImplicitHydrogenCount4b() throws Exception {
		String smiles = "c1ccccc1";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(6, mol.getAtomCount());
		assertEquals(1, mol.getAtom(0).getHydrogenCount());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testHOSECodeProblem() throws Exception {
		String smiles = "CC=CBr";
		IMolecule mol = sp.parseSmiles(smiles);
		assertEquals(4, mol.getAtomCount());
		assertEquals("Br", mol.getAtom(3).getSymbol());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testPyridine() throws Exception {
		SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());

		IMolecule mol = sp.parseSmiles("c1ccncc1");
		assertEquals(6, mol.getAtomCount());
		// it's a bit hard to detect two double bonds in the pyrrole ring
		// but I do can check the total order in the whole molecule
		double totalBondOrder = 0.0;
		Iterator bonds = mol.bonds();
		while (bonds.hasNext())
			totalBondOrder += ((IBond)bonds.next()).getOrder();
		assertEquals(9.0, totalBondOrder, 0.001);
		// I can also check wether the total neighbor count around the
		// nitrogen is 3, all single bonded
		org.openscience.cdk.interfaces.IAtom nitrogen = mol.getAtom(3);
		// the second atom
		assertEquals("N", nitrogen.getSymbol());
		totalBondOrder = 0.0;
		List bondsList = mol.getConnectedBondsList(nitrogen);
		assertEquals(2, bondsList.size());
		for (int i = 0; i < bondsList.size(); i++)
		{
			totalBondOrder += ((IBond)bondsList.get(i)).getOrder();
		}
		assertEquals(3.0, totalBondOrder, 0.001);
	}

	/**
	 * @cdk.bug 1306780
	 */
	public void testParseK() throws CDKException {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol = p.parseSmiles("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[Na+]");
		assertNotNull(mol);
		assertEquals(23, mol.getAtomCount());
		mol = p.parseSmiles("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[K]");
		assertNotNull(mol);
		assertEquals(23, mol.getAtomCount());
		mol = p.parseSmiles("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[K+]");
		assertNotNull(mol);
		assertEquals(23, mol.getAtomCount());
	}
	
	
	/**
	 * @cdk.bug 1459299
	 */
	public void testBug1459299() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol = p.parseSmiles("Cc1nn(C)cc1[C@H]2[C@H](C(=O)N)C(=O)C[C@@](C)(O)[C@@H]2C(=O)N");
		assertNotNull(mol);
		assertEquals(22, mol.getAtomCount());
	}
	
	/**
	 * @cdk.bug 1365547
	 */
	public void testBug1365547() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol = p.parseSmiles("c2ccc1[nH]ccc1c2");
		assertNotNull(mol);
		assertEquals(9, mol.getAtomCount());
		assertTrue(mol.getBond(0).getFlag(CDKConstants.ISAROMATIC));
	}
	
	/**
	 * @cdk.bug 1365547
	 */
	public void testBug1365547_2() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol = p.parseSmiles("[H]c1c([H])c(c([H])c2c([H])c([H])n([H])c12)Br");
		assertNotNull(mol);
		assertEquals(16, mol.getAtomCount());
		assertEquals(17, mol.getBondCount());
		for (int i=0; i<17; i++) {
			IBond bond = mol.getBond(i);
			if (bond.getAtom(0).getSymbol().equals("H") ||
					bond.getAtom(0).getSymbol().equals("Br") ||
					bond.getAtom(1).getSymbol().equals("H") ||
					bond.getAtom(1).getSymbol().equals("Br")) {
				assertFalse(bond.getFlag(CDKConstants.ISAROMATIC));
			} else {
				assertTrue(bond.getFlag(CDKConstants.ISAROMATIC));
			}
		}
	}
	
	/**
	 * @cdk.bug 1235852
	 */
	public void testBug1235852() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		//                             0 1 234 56 7 890 12 3456 78
		IMolecule mol = p.parseSmiles("O=C(CCS)CC(C)CCC2Cc1ccsc1CC2");
		assertNotNull(mol);
		assertEquals(19, mol.getAtomCount());
		assertEquals(20, mol.getBondCount());
		// test only option for delocalized bond system
		assertEquals(4.0, mol.getBondOrderSum(mol.getAtom(12)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(13)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(14)), 0.001);
		assertEquals(2.0, mol.getBondOrderSum(mol.getAtom(15)), 0.001);
		assertEquals(4.0, mol.getBondOrderSum(mol.getAtom(16)), 0.001);
	}
	
	/**
	 * @cdk.bug 1519183
	 */
	public void testBug1519183() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		//                             0    12345  6
		IMolecule mol = p.parseSmiles("c%101ccccc1.O%10"); // phenol
		assertNotNull(mol);
		assertEquals(7, mol.getAtomCount());
		assertEquals(7, mol.getBondCount());
	}
	
	/**
	 * @cdk.bug 1530926
	 */
	public void testBug1530926() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		//                              0      12345   6
		IMolecule mol = p.parseSmiles("[n+]%101ccccc1.[O-]%10");
		assertNotNull(mol);
		assertEquals(7, mol.getAtomCount());
		assertEquals(7, mol.getBondCount());
		for (int i=0; i<7; i++) {
			IBond bond = mol.getBond(i);
			if (bond.getAtom(0).getSymbol().equals("O") ||
					bond.getAtom(1).getSymbol().equals("O")) {
				assertFalse(bond.getFlag(CDKConstants.ISAROMATIC));
			} else {
				assertTrue(bond.getFlag(CDKConstants.ISAROMATIC));
			}
		}
	}

	/**
	 * @cdk.bug 1541333
	 */
	public void testBug1541333() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		//                              01  2 345  67  8 9 0 12 3 4  5 67 89  0  1 2
		IMolecule mol1 = p.parseSmiles("OC(=O)CSC1=NC=2C=C(C=CC2N1C=3C=CC=CC3)N(=O)O");
		assertNotNull(mol1);
		assertEquals(23, mol1.getAtomCount());
		assertEquals(25, mol1.getBondCount());
		IMolecule mol2 = p.parseSmiles("OC(=O)CSc1nc2cc(ccc2n1c3ccccc3)N(=O)O");
		assertNotNull(mol2);
		assertEquals(23, mol2.getAtomCount());
		assertEquals(25, mol2.getBondCount());
		// do some checking
		assertEquals(2.0, mol1.getBond(1).getOrder(), 0.0001);
		assertEquals(2.0, mol2.getBond(1).getOrder(), 0.0001);
		assertTrue(mol1.getBond(7).getFlag(CDKConstants.ISAROMATIC));
		assertTrue(mol2.getBond(7).getFlag(CDKConstants.ISAROMATIC));
	}

	/**
	 * @cdk.bug 1503541
	 */
	public void testBug1503541() throws Exception {
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		//                             0  1 23 45
		IMolecule mol = p.parseSmiles("C=1C=CC=CC=1"); // phenol
		assertNotNull(mol);
		assertEquals(6, mol.getAtomCount());
		assertEquals(6, mol.getBondCount());
		// test only option for delocalized bond system
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(0)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(1)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(2)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(3)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(4)), 0.001);
		assertEquals(3.0, mol.getBondOrderSum(mol.getAtom(5)), 0.001);			
	}
	
}

