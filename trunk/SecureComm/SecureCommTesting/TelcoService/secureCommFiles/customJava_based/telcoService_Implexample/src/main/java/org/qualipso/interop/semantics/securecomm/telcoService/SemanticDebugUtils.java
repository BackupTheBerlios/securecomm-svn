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
 */
package org.qualipso.interop.semantics.securecomm.telcoService;

import java.util.Collection;
import java.util.Iterator;

import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protegex.owl.inference.reasoner.ProtegeReasoner;
import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.RDFProperty;

/** Utility class
 * 
 * @author rwe
 *
 */
public class SemanticDebugUtils {

	public static void printInconsistentConcepts(ProtegeReasoner reasoner, JenaOWLModel owlModel) {
		try {
			reasoner.computeInconsistentConcepts();
		} catch (ProtegeReasonerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collection inconsistentClasses = owlModel.getInconsistentClasses();
		if (inconsistentClasses.size() == 0){
			System.out.println("--------------------- There are no inconsistent classes");
			return;
		}
		System.out.println("--------------------- There are following inconsistent classes in the model:");
		for (Iterator iter = inconsistentClasses.iterator(); iter.hasNext();) {
			Class element = (Class) iter.next();
			System.out.println("--------------------- inconsistentClass: " + element);
		}
	}

	/**
	 * @param owlModel
	 */
	public static void printIndividualsWithAssertedTypes(JenaOWLModel owlModel, String sourceOntURI) 
	{
		System.out.println("*** Printing asserted Individuals in " + owlModel.getNamespaceManager().getDefaultNamespace());
		
		Collection<OWLIndividual> individuals = owlModel.getOWLIndividuals();
		Collection<Cls> 		  types	   	  = null;
		
		for (Iterator<OWLIndividual> it = individuals.iterator(); it.hasNext();) 
		{
			OWLIndividual individual = it.next();
			if (individual.getNamespace()== null) continue;
			if (individual.getNamespace().indexOf(sourceOntURI) == -1 && 
				individual.getName().indexOf("SWRLCreated") == -1) continue;
			System.out.println("Individual '" + individual.getLocalName() + "' has asserted types and properties: ");
	
			types = individual.getRDFTypes();
			for (Iterator<Cls> typesIt = types.iterator(); typesIt.hasNext();) 
			{
				Cls type = typesIt.next();
				System.out.println("   " + type.getName());
			}
	
			Collection rdfProperties = individual.getRDFProperties();
			for (Iterator iter = rdfProperties.iterator(); iter.hasNext();) {
				RDFProperty property = (RDFProperty) iter.next();
				System.out.println("   property " + property.getNamespace() +  
					property.getLocalName() + " has values: ");
				Collection propValues = individual.getPropertyValues(property, true);
				for (Iterator iterator = propValues.iterator(); iterator.hasNext();) {
					Object propValue = (Object) iterator.next();
					System.out.println("        " + propValue);
				}
				System.out.println();
			}
	
		}
		System.out.println("*** (finished) printing asserted Individuals \n" +
				"******************************************************************************************************");
	}

	public static void printIndividualsWithInferredTypes(JenaOWLModel owlModel) 
	{
		System.out.println("*** Printing inferred Individuals in " + owlModel.getNamespaceManager().getDefaultNamespace());
		
		Collection<OWLIndividual> individuals = owlModel.getOWLIndividuals(true);
		Collection<Cls> 		  types	   	  = null;
		
		for (Iterator<OWLIndividual> it = individuals.iterator(); it.hasNext();) 
		{
			OWLIndividual individual = it.next();			
			types = individual.getInferredTypes();
			if (types.size()== 0) continue;
			
			System.out.println("Individual '" + individual.getName() + "' has inferred types:");
			for (Iterator<Cls> typesIt = types.iterator(); typesIt.hasNext();) 
			{
				Cls type = typesIt.next();
				System.out.println(" " + type.getName());
			}
		}
		System.out.println("*** (finished) Printing inferred Individuals \n" +
				"******************************************************************************************************");
	}

}
