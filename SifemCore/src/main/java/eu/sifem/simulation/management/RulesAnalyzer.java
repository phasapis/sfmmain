package eu.sifem.simulation.management;

public class RulesAnalyzer {

	//TODO Create test case
//	public static String findAllValidDataViewsQuery(){	
//		//SPARQL queries:
//		//		# find all the data views in Data Analysis ontology that have a negative minimum and a decreasing slope followed by an increasing slope
//		//	Returns DataView_VelocityX_vs_DistanceFromCavityBase
//		//	If we replace ‘Select ?dataview’ with ‘ASK’ – find out if there is a data view in Data Analysis ontology that have a negative minimum and a decreasing slope followed by an increasing slope 
//		//		Returns true
//		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" + 
//				"PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" + 
//				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
//				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
//				"PREFIX dao: <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> \n" +
//				"Select ?dataview \n"+ 
//				//	# ASK
//				"where { \n" +
//				"	?dataview rdf:type dao:DataView .\n"+
//				"	?dataview dao:hasFeature ?x .\n"+
//				"	?dataview dao:hasFeature ?y . \n"+
//				"	?dataview dao:hasFeature ?z . \n"+
//				"	?x rdf:type dao:Minimum.  \n"+
//				"	?y rdf:type dao:NegativeSlope.  \n"+
//				"	?z rdf:type dao:PositiveSlope.  \n"+
//				"	?y dao:isFollowedByFeature ?z . \n"+
//				"	}";	
//		return query;
//	}
//
//	public static String constructRDFForValidDataViewRule(){
//		//		Rules:
//		//		# If Region A isFollowedByRegion  Region B then
//		//		  Feature A (which isDataFeatureOf Region A) IsFollowedByFeature 
//		//		  Feature B (which isDataFeatureOf RegionB):
//		//		Returns the triple 
//		//		NegativeSlope1 isFollowedByFeature PositiveSlope1 
//
//		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" + 
//				"PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" + 
//				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" + 
//				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
//				"PREFIX dao: <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> \n" + 
//				"CONSTRUCT {      \n" + 
//				"                             ?datafeature1 dao:isFollowedByFeature ?datafeature2 . \n" +		                                                                
//				"                    } \n" + 
//				"where { \n" + 
//				"                ?region1 rdf:type dao:BehaviourRegion . \n" +                               
//				"              ?region1 dao:isFollowedByRegion ?region2 . \n" + 
//				"             ?region2 rdf:type dao:BehaviourRegion .  \n" + 		                                
//				"             ?datafeature1 dao:isDataFeatureOf  ?region1 . \n" + 
//				"            ?datafeature2 dao:isDataFeatureOf  ?region2 . \n" + 		            
//				"        } \n";
//		return query;
//	}
}



