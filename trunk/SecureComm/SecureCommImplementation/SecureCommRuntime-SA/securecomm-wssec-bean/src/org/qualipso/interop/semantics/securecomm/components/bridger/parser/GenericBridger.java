package org.qualipso.interop.semantics.securecomm.components.bridger.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.qualipso.interop.semantics.securecomm.components.bridger.BridgerConstants;
import org.qualipso.interop.semantics.securecomm.components.bridger.runtime.SemanticBridge;
import org.qualipso.interop.semantics.securecomm.components.SemanticBridging;


/** This class implements a GenericBridger that is used everytime a custom bridger/code
 * has not been provided from the user. It implements the <code>ComponentImplementator</code>
 * interface though only the <code>semanticBridging</code> method is implemented. 
 * It mostly parses the given ontologies by loading them to the appropriate objects 
 * and then set's them to the <code>SemanticBridge</code> class which in the end does
 * the actual bridging/infering.
 * 
 * @author wp32
 *
 */
public class GenericBridger implements BridgerConstants, SemanticBridging {
	
	/** default constructor */
	public GenericBridger() {
		
	}
	
	/** Implements the method defined by <code>ComponentImplementator</code> in 
	 * a generic way. This bridger is deployed within the ASM.
	 * It sets the data needed by the <code>SemanticBridge</code> by parsing the 
	 * provided ontologies. Finally it calls the <b>bridge</b> method and returns
	 * the result as <code>OutputStream</code>
	 */
	public void semanticBridging(String sourceOnto, String targetOnto,
			String bridgeOnto, String bridgeRepository, List otherOnto, String resultOfBridging) 
			throws Exception {
		
		/** load bridgeOntology data */
		BridgeOntologyIdentifer bridgeOntology = new BridgeOntologyIdentifer(bridgeOnto);
		HashMap importedPrefixNamespace = bridgeOntology.getImportedPreficesAndNamespaces();
		
		/** load sourceOntology data */
		OntologyIdentifier sourceOntology = new OntologyIdentifier(sourceOnto);
		if (importedPrefixNamespace.containsKey(sourceOntology.getNamespace()))
			sourceOntology.setPrefix((String) importedPrefixNamespace.get(sourceOntology.getNamespace()));
		
		List otherOntologies = new ArrayList<OntologyIdentifier>();
		/** load target ontology -- 1st on the list*/
		OntologyIdentifier targetOnt = new OntologyIdentifier(targetOnto);
		//System.out.println("TARGET->["+targetOnt.getNamespace()+"]");
		if (importedPrefixNamespace.containsKey(targetOnt.getNamespace())) {
			targetOnt.setPrefix((String) importedPrefixNamespace.get(targetOnt.getNamespace()));
			otherOntologies.add(targetOnt);
		}
		
		/** load other ontologies */
		if (otherOnto!=null) {
			for (int i=0; i<otherOnto.size(); i++) {
				OntologyIdentifier tmp = new OntologyIdentifier((String)otherOnto.get(i));
				if (importedPrefixNamespace.containsKey(tmp.getNamespace())) {
					tmp.setPrefix((String) importedPrefixNamespace.get(tmp.getNamespace()));
					otherOntologies.add(tmp);
				}
				tmp = null;
			}
		}
		
		SemanticBridge semanticBridge = new SemanticBridge();
		
		semanticBridge.setSourceOntology(sourceOntology);
		semanticBridge.setBridgeOntology(bridgeOntology);
		semanticBridge.setOtherOntologies(otherOntologies);
		
		semanticBridge.setResultingOntURI(BridgerConstants.RESULT_ONTOLOGY_URI);
		semanticBridge.createRepositoryFile(bridgeRepository);
		semanticBridge.setResultingBridgeOntFile(resultOfBridging);
		
		semanticBridge.execute();
	}
	
	
	
}
