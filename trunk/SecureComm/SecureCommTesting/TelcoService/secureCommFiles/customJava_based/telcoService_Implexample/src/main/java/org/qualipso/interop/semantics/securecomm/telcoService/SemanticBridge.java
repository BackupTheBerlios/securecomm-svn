/*
 * IST-FP6-034763 QualiPSo: QualiPSo is a unique alliance 
 * of European, Brazilian and Chinese ICT industry players, 
 * SMEs, governments and academics to help industries and 
 * governments fuel innovation and competitiveness with Open 
 * Source software. To meet that goal, the QualiPSo consortium 
 * intends to define and implement the technologies, processes 
 * and policies to facilitate the development and use of Open 
 * Source software components, with the same level of trust 
 * traditionally offered by proprietary software. QualiPSo is 
 * partially funded by the European Commission under EU�s sixth 
 * framework program (FP6), as part of the Information Society 
 * Technologies (IST) initiative. 
 * 
 * This program has been created as part of the QualiPSo work 
 * package on "Semantic Interoperability". The basic idea is to show 
 * how semantic technologies can be used to cope with the diversity 
 * and heterogeneity of software and services in the OSS domain.
 *
 * You can redistribute this program and/or modify it under 
 * the terms of the European Union Public License v1.0 (EUPL v1.0)
 * as published by the European Commission.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *
 * You should have received a copy of the EU Public License
 * along with this program; if not, please have a look at 
 * http://ec.europa.eu/idabc/en/document/6523 
 * to obtain the full license text.
 *
 * Author of this program: 
 * Fraunhofer Institute FOKUS, http://www.fokus.fraunhofer.de
 * 
 *
 */
package org.qualipso.interop.semantics.securecomm.telcoService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import jess.Rete;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.util.FileUtils;
import edu.stanford.smi.protege.util.URIUtilities;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.inference.pellet.ProtegePelletOWLAPIReasoner;
import edu.stanford.smi.protegex.owl.inference.protegeowl.ReasonerManager;
import edu.stanford.smi.protegex.owl.inference.reasoner.ProtegeReasoner;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.jena.creator.JenaCreator;
import edu.stanford.smi.protegex.owl.model.NamespaceManager;
import edu.stanford.smi.protegex.owl.model.RDFIndividual;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.model.RDFSClass;
import edu.stanford.smi.protegex.owl.model.util.ImportHelper;
import edu.stanford.smi.protegex.owl.repository.Repository;
import edu.stanford.smi.protegex.owl.swrl.bridge.exceptions.SWRLRuleEngineBridgeException;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.SWRLJessBridge;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.factory.SWRLJavaFactory;

/** This class is responsible for the pure semantic bridge process. 
 *  It does the necessary copying/setting and calls jena/protege/jess
 *  functions.
 * 
 * @author rwe, wp32
 *
 */
public class SemanticBridge {
	
	private OntologyIdentifier sourceOntology = null;
	private BridgeOntologyIdentifer bridgeOntology = null;
	private List<OntologyIdentifier> otherOntologies = null; 
	
	private String resultingOntURI = null;
	private String resultingBridgeOntFile = null;
	
	/** Specifications for model creation (such as URI to URL mappings).*/
	OntModelSpec spec = null;
	
	public ProtegeReasoner createProtegeReasoner(JenaOWLModel owlModel) {
		
		// Get the reasoner manager and obtain a reasoner for the OWL model. 
		ReasonerManager reasonerManager = ReasonerManager.getInstance();
	
		//Get an instance of the Protege Pellet reasoner
		ProtegeReasoner reasoner = reasonerManager.createProtegeReasoner(owlModel, ProtegePelletOWLAPIReasoner.class);
	
		return reasoner;
	}

	
	/**
	 * Deep-copy all typed individuals (types must be of registered namespaces)
	 * from one OWL-model to another.
	 *
	 * @param fromOwlModel source model
	 * @param toOwlModel   destination model
	 */
	public void transferIndividuals(JenaOWLModel fromOwlModel, JenaOWLModel toOwlModel, Collection registeredPrefixes) {
        makeCompatibleForDeepCopyOperation(fromOwlModel, toOwlModel);        
 
	    Collection<RDFIndividual> individuals = fromOwlModel.getRDFIndividuals(true);
	    boolean isRegisteredNamespacePrefix; 
	    for (Iterator<RDFIndividual> it = individuals.iterator(); it.hasNext();) {
	        RDFIndividual individual = it.next();
	        Collection individualTypes = individual.getRDFTypes();
	        
	        isRegisteredNamespacePrefix = false;
	        
	        for (Iterator types = individualTypes.iterator(); types.hasNext();) {
	            RDFResource type = (RDFResource) types.next();
	            String namespacePrefix = type.getNamespacePrefix ();
	            
	            if (registeredPrefixes.contains(namespacePrefix)) {
	                isRegisteredNamespacePrefix = true;
	            }
	        }
	
	        individualTypes = individual.getInferredTypes ();
	        for (Iterator types = individualTypes.iterator(); types.hasNext();) {
	            RDFSClass type = (RDFSClass) types.next();
	            String namespacePrefix = type.getNamespacePrefix();
	            
	            // TODO: hard-coded!!!
	            if (registeredPrefixes.contains(namespacePrefix)) {
	                isRegisteredNamespacePrefix = true;
	            }
	        }
	        
	        if (isRegisteredNamespacePrefix) {              
	            //System.out.println(" deep-copy " + fromOwlModel + "#" + individual.getNamespacePrefix() + ":" + individual.getName());
	            RDFIndividual deepCopy = (RDFIndividual) individual.deepCopy(toOwlModel, null);
	            //deepCopy.setName(individual.getName());
	            //System.out.println(" to " + toOwlModel + "#" +  deepCopy.getNamespacePrefix() + ":" + deepCopy.getName());
	        }    
	    }
	}

	
	/**
	 * Copy namespaces/prefixes and necessary imports from a source owl-model to a destination owl-model
	 * in order to be able to copy individuals afterwards.
	 * 
	 * @param fromOwlModel the source owl-model
	 * @param toOwlModel the destination owl-model (which is to be made compatible)
	 */
	public void makeCompatibleForDeepCopyOperation(JenaOWLModel fromOwlModel, JenaOWLModel toOwlModel) {
		//System.out.println("*** begin copy Namespace-definitions and Imports-statements from " 
		//		+ fromOwlModel.getName() + " to " + toOwlModel.getName() + " ***" );
		
		NamespaceManager nsmFrom = fromOwlModel.getNamespaceManager();
        NamespaceManager nsmTo = toOwlModel.getNamespaceManager();
        
        // make the mappings of owl-URIs to owl-Files available in the destination owl-model
        // in order to be able to copy the imports
        List<Repository> repositories = fromOwlModel.getRepositoryManager().getProjectRepositories();
        
        for (Iterator<Repository> repIt = repositories.iterator(); repIt.hasNext();) {
			Repository repository = repIt.next();
	        toOwlModel.getRepositoryManager().addProjectRepository(repository);
	        
	        System.out.println("Added repository: " + repository.getRepositoryDescriptor());
		}
        
        //create the ImportHelper
        ImportHelper importHelper = new ImportHelper(toOwlModel);
        Collection<String> prefixes = nsmFrom.getPrefixes();
        
        // copy namespaces
        for (Iterator prefixIt = prefixes.iterator (); prefixIt.hasNext();) {
            String prefix = (String) prefixIt.next();
            String namespaceForPrefix = nsmFrom.getNamespaceForPrefix(prefix);

    		nsmTo.setPrefix(namespaceForPrefix, prefix);
    			
    		System.out.println("set prefix '" + prefix + "' for uri: " + namespaceForPrefix);
        }       

        try {
        	importHelper.addImport(URIUtilities.createURI(sourceOntology.getFilepath()));
            for (int i=0; i<otherOntologies.size(); i++) {
    			OntologyIdentifier temp = (OntologyIdentifier) otherOntologies.get(i);
    			importHelper.addImport(URIUtilities.createURI(temp.getFilepath()));
    		}

            //do the actual import
            System.out.println("importing ontologies...");
            
            importHelper.importOntologies(false);

            System.out.println("importing ontologies finished");
        } catch (Exception e) {  
        	System.out.println("IMPORT FAILED!!!!!!!!!!!");
            e.printStackTrace();
        }
        
		//System.out.println("*** end copyImports ***" );
		//System.out.println();
	}

	
	/** Creates a repository file containing references to all
	 *  the files, which are imported into bridge ontology.
	 */
	public void createRepositoryFile(String repositoryURL){
		
		String forceReadOnlyString = "?forceReadOnly=true";
		File repositoryFile = new File(repositoryURL);
		
		try {
			FileWriter fw = new FileWriter(repositoryFile);
			String repositoryFileContents = "";
			
			for (int i=0; i<otherOntologies.size(); i++) {
    			OntologyIdentifier temp = (OntologyIdentifier) otherOntologies.get(i);
    			repositoryFileContents += temp.getFilepath() + forceReadOnlyString + "\n";
    		}
            repositoryFileContents += sourceOntology.getFilepath() + forceReadOnlyString;
			fw.write(repositoryFileContents);
			fw.close();
		} catch (IOException e) {
		    pr("createRepositoryFile ----> EXCEPTION=["+e.getMessage()+"]");
		    //e.printStackTrace(System.out);
		}
	}
	
	
	/** This method saves the result of the bridging process in a file 
	 * using the Jena API. The file name is stored in <code>resultingBridgeOntFile</code>
	 * 
	 * @param resultingOWLModel: Bridge result model.
	 */
	public void saveResultingOntologyInFile(JenaOWLModel resultingOWLModel) {
		
		NamespaceManager namespaceManager = resultingOWLModel.getNamespaceManager();
	    namespaceManager.setDefaultNamespace(resultingOntURI);
		   
	    Collection clses = resultingOWLModel.getUserDefinedOWLNamedClasses();
	    
	    JenaCreator jenaCreator = new JenaCreator(resultingOWLModel, false, true, clses, null);
	    OntModel ontModel = jenaCreator.createOntModel(spec);
	    ontModel.setNsPrefix("", resultingOntURI);
	   
	    try {
	    	File file = new File(resultingBridgeOntFile);
	    	JenaOWLModel.save(file, ontModel, FileUtils.langXMLAbbrev, resultingOntURI);
	        //System.out.println("File saved!!");
	   }
	    catch (Exception ex) {
		    System.out.println("File NOT saved!");	
		    ex.printStackTrace();
	    }
	}
	
	/** This method saves the result of the bridging process in a 
	 * <code>OutputStream</code> which is then returned to the caller.
	 * 
	 * @param resultingOWLModel: Bridge result model.
	 */
	public OutputStream saveResultingOntologyInStream(JenaOWLModel resultingOWLModel) {
		
		NamespaceManager namespaceManager = resultingOWLModel.getNamespaceManager();
	    namespaceManager.setDefaultNamespace(resultingOntURI);
		   
	    Collection clses = resultingOWLModel.getUserDefinedOWLNamedClasses();
	    
	    JenaCreator jenaCreator = new JenaCreator(resultingOWLModel, false, true, clses, null);
	    OntModel ontModel = jenaCreator.createOntModel(spec);
	     
	    ontModel.setNsPrefix("", resultingOntURI);
	    try {
	    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    	JenaOWLModel.saveModel(stream, ontModel, FileUtils.langXMLAbbrev, resultingOntURI);
	    	return stream;
	    }
	    catch (Exception ex) {
		    System.out.println("File NOT saved!");	
		    ex.printStackTrace();
	    }
	    return null;
	}
	
	
	/** Initialize the <code>OntDocumentManager</code> with the sourceOntology
	 * and define the <code>OntModelSpec</code>.
	 * 
	 */
	private void init() {
		OntDocumentManager dm = new OntDocumentManager();
	    
		dm.addAltEntry(sourceOntology.getNamespace(), sourceOntology.getFilepath());
		for (int i=0; i<otherOntologies.size(); i++) {
			OntologyIdentifier temp = (OntologyIdentifier) otherOntologies.get(i);
			dm.addAltEntry(temp.getNamespace(), temp.getFilepath());
		}
		
	    spec = new OntModelSpec(OntModelSpec.OWL_DL_MEM);
	    spec.setDocumentManager(dm);
	}


	/** Do magic semantic bridging */
	public void execute() {	
		try {
			
			init();
		
			// Load the source and bridge ontologeies from the specified URI			
			//pr("SOURCE  url=["+sourceOntology.getFilepath()+"]");
			JenaOWLModel sourceOwlModel = ProtegeOWL.createJenaOWLModelFromURI(sourceOntology.getFilepath());
			JenaOWLModel bridgeOwlModel = ProtegeOWL.createJenaOWLModelFromURI(bridgeOntology.getFilepath());
	
			// print debug info
			//SemanticDebugUtils.printIndividualsWithAssertedTypes(sourceOwlModel, sourceOntURI);		
	
			// create the reasoner for an initial check of the bridging ontology
			ProtegeReasoner reasoner = createProtegeReasoner(bridgeOwlModel);
			try { 
				// According to the  SWRLJessBridge documentation
				// we should check for consistency before running Jess
				// due to some limitations of Jess.
				SemanticDebugUtils.printInconsistentConcepts(reasoner, bridgeOwlModel);				
			} 
			catch(Exception ex) { 
				ex.printStackTrace(); 
			} 
			
			// Create Jess rule engine and the bridge to copy swrl-rules and owl-facts to Jess.
			Rete rete = new Rete(); 
			SWRLJessBridge bridge = null;
			
			try {
				// create the bridge from SWRL to Jess-Rules
				bridge = new SWRLJessBridge(bridgeOwlModel, rete);
				
				// Execute the semantic bridge 
				// 1. copy swrl-rules and owl-facts to Jess
				// 2. fire the SWRL-rules
				// 3. updates the Protege-OWL-model with new facts
				bridge.infer();

                /*System.out.println("number of imported classes : " + bridge.getNumberOfImportedClasses());
                System.out.println("number of imported axioms : " + bridge.getNumberOfImportedAxioms());
                System.out.println("number of imported property assertion axioms : " + bridge.getNumberOfImportedPropertyAssertionAxioms());
                System.out.println("number of imported rules : " + bridge.getNumberOfImportedSWRLRules());
                System.out.println("number of imported individuals : " + bridge.getNumberOfImportedIndividuals());
                System.out.println("number of inferred individuals : " + bridge.getNumberOfInferredIndividuals());
                System.out.println("number of inferred property assertion axioms : " + bridge.getNumberOfInferredPropertyAssertionAxioms());*/				
			} catch (SWRLRuleEngineBridgeException ex) {
				ex.printStackTrace();
			} 
			
            // Create a new OWL-model to save the resulting individuals in.
            JenaOWLModel resultingOWLModel  = ProtegeOWL.createJenaOWLModel();
            
            resultingOWLModel.setOWLJavaFactory(new SWRLJavaFactory(resultingOWLModel));
            SWRLFactory factory = new SWRLFactory(resultingOWLModel);            
            
            // Copy the inferred individuals into the new model in order to be able to save the individuals 
            // with out any SWRL-rules sand other irrelevant facts.
            Collection registeredPrefixes = new HashSet<String>();
            registeredPrefixes.add(sourceOntology.getPrefix());
            for (int j=0; j<otherOntologies.size(); j++) {
            	OntologyIdentifier tmp = (OntologyIdentifier) otherOntologies.get(j);
            	registeredPrefixes.add(tmp.getPrefix());
            }
			transferIndividuals(bridgeOwlModel, resultingOWLModel, registeredPrefixes);                

            reasoner = createProtegeReasoner(resultingOWLModel);
			
			try { 
				// after the semantic bridge was executed, compute if the individuals do match additional classes now.
				reasoner.computeInferredIndividualTypes();
				
				// some debugging info
				SemanticDebugUtils.printInconsistentConcepts(reasoner, resultingOWLModel);
				SemanticDebugUtils.printIndividualsWithInferredTypes(resultingOWLModel);
			} 
			catch(Exception e) { 
				e.printStackTrace(); 
			} 
			
			saveResultingOntologyInFile(resultingOWLModel);
			
		} catch (Exception e) {	
			e.printStackTrace();
		} 	
	}

	/** Helper printer method */
	public void pr (Object m) {
		System.out.println(""+m);
	}

	/** sourceOntology setter */
	public void setSourceOntology(OntologyIdentifier sourceOntology) {
		this.sourceOntology = sourceOntology;
	}

	/** bridgeOntology setter */
	public void setBridgeOntology(BridgeOntologyIdentifer bridgeOntology) {
		this.bridgeOntology = bridgeOntology;
	}

	/** otherOntologies setter */
	public void setOtherOntologies(List<OntologyIdentifier> otherOntologies) {
		this.otherOntologies = otherOntologies;
	}

	/** resultingOntURI setter */
	public void setResultingOntURI(String resultingOntURI) {
		this.resultingOntURI = resultingOntURI;
	}

	/** resultingBridgeOntFile setter */
	public void setResultingBridgeOntFile(String resultingOntFile) {
		this.resultingBridgeOntFile = resultingOntFile;
	}
	

}
