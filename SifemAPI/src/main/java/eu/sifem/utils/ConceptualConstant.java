package eu.sifem.utils;


/**
 * 
 * @author kartik
 * 
 */
public class ConceptualConstant {

	public static final String PREFIX="PREFIX prov: <http://www.w3.org/ns/prov#>"+
	"PREFIX Simulation: <http://www.sifemontologies.com/ontologies/Simulation.owl#> \n"+
	"PREFIX OpenFoamOntology: <http://www.sifemontologies.com/ontologies/OpenFoamOntology.owl#> \n"+
	"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
	"PREFIX swrl: <http://www.w3.org/2003/11/swrl#> \n"+
	"PREFIX graph: <http://www.sifemontologies.com/ontology/graph.owl#> \n"+
	"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
	"PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"+
	"PREFIX swrlb: <http://www.w3.org/2003/11/swrlb#> \n"+
	"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"+
	"PREFIX DataAnalysis: <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> \n"+
	"PREFIX FEMOntology: <http://www.sifemontologies.com/ontologies/FEMOntology.owl#> \n"+
	"PREFIX graph2: <http://www.sifemontologies.com/ontologies/graph.owl#> \n"+
	"PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> \n "+
	"PREFIX bw:<http://www.sifemontologies.com/ontologies/BoxModel#> \n ";
	
	public static final String NAME_SPACE="http://www.sifemontologies.com/ontologies/LidABox5.owl";

	
	public static final String SUFFIX_BOUNDARYCOND="BoundaryCondition";
	public static final String SUFFIX_INTERNAL="InternalCondition";
	public static final String GRAPH_TYPE_CLASS="GraphFile";
	public static final String SOLVER_TYPE_CLASS="Solver";
	public static final String PARAM_TYPE_CLASS="Parameter";
	public static final String THING_TYPE_CLASS="Thing";
	public static final String DISTANCEFROM_CLASS="DistanceFromCavityBase";
	public static final String NUMMETHOD_TYPE_CLASS="NumericalMethod";
	
	
	
	public static final String SIM_NAME_SPACE="http://www.sifemontologies.com/ontologies/Simulation.owl";
	public static final String FEM_NAME_SPACE="http://www.sifemontologies.com/ontologies/FEMOntology.owl";
	public static final String OWL_NAME_SPACE="http://www.w3.org/2002/07/owl";
	public static final String DATAANALYSIS_NAME_SPACE="http://www.sifemontologies.com/ontologies/DataAnalysis.owl";
	public static final String FILTER_PARAMS="VectorParameter,ScalarParameter";
	public static final String FILTER_ATTR_NUM="hasTolerance,hasRelativeTolerance,hasPreconditioner";

	public static final String EXPERIMENT_ONTOLOGY_URI = "http://www.sifemontologies.com/ontologies/LidABox5.owl#";
	
	public static final String EXPERIMENT_MODEL="Experiment";
	
	public static final String FILTER_PARAM_LIST="DistanceFromCavityBase";
	
	public static final String EXPERIMENT_Q="What are the maximum fluid velocities?=MX_V_FLUID,At which positions do the maximum fluid velocity occur?=MX_POS_V_FLUID,"+
											"What are the maximum fluid pressure?=MX_P_FLUID,At which positions do the maximum fluid pressure occur?=MX_POS_P_FLUID";
	public static final String LIDABOX5_Q="What is the fluid viscosity?=FLUID_VISCO,How many different scenarios were tested in this simulation?=SCENARIO_SIM";
	
	public static final String BOXMODELABOX_Q="What is the maximum real fluid pressure for this simulation?=REAL_FLUID_PRESSURE";
	
	
	public static final String MX_V_FLUID="MX_V_FLUID";
	public static final String MX_POS_V_FLUID="MX_POS_V_FLUID";
	public static final String MX_P_FLUID="MX_P_FLUID";
	public static final String MX_POS_P_FLUID="MX_POS_P_FLUID";
	public static final String FLUID_VISCO="FLUID_VISCO";
	public static final String SCENARIO_SIM="SCENARIO_SIM";
	public static final String QUERY_MX_V_FLUID="SELECT   (max (?velocity) as ?MaxXVelocity) WHERE   { ?x rdf:type FEMOntology:Cell . ?x FEMOntology:hasParameter ?y . ?y sim:hasVectorValue ?z . ?z sim:hasVectorXValue ?velocity }";
	public static final String QUERY_MX_POS_V_FLUID="SELECT DISTINCT ?positionX ?positionY ?positionZ WHERE { ?x1 rdf:type FEMOntology:Cell . ?x1 FEMOntology:hasCellCoordinate ?y1 . ?y1 sim:hasVectorXValue ?positionX  . ?y1 sim:hasVectorYValue ?positionY . ?y1 sim:hasVectorZValue ?positionZ   FILTER regex(str(?x1),str(?x)) { SELECT   ?x  WHERE   { ?x rdf:type FEMOntology:Cell .	?x FEMOntology:hasParameter ?y . ?y sim:hasVectorValue ?z . ?z sim:hasVectorXValue ?velocity } ORDER BY DESC(?velocit) LIMIT 1 } } LIMIT 1";
	public static final String QUERY_MX_P_FLUID="SELECT   (max(?pressure) as ?MaxPressure) WHERE   { ?x rdf:type FEMOntology:Cell . ?x FEMOntology:hasParameter ?y . ?y sim:hasScalarValue ?z . ?z sim:hasScalarDataValue ?pressure }";
	public static final String QUERY_MX_POS_P_FLUID="SELECT DISTINCT ?positionX ?positionY ?positionZ WHERE { ?x1 rdf:type FEMOntology:Cell . ?x1 FEMOntology:hasCellCoordinate ?y1 . ?y1 sim:hasVectorXValue ?positionX  . ?y1 sim:hasVectorYValue ?positionY . ?y1 sim:hasVectorZValue ?positionZ   FILTER regex(str(?x1),str(?x)) { SELECT   ?x  WHERE   { ?x rdf:type FEMOntology:Cell .	?x FEMOntology:hasParameter ?y . ?y sim:hasScalarValue ?z . ?z sim:hasScalarDataValue ?pressure } ORDER BY DESC(?pressure) LIMIT 1 } } LIMIT 1";
	public static final String QUERY_FLUID_VISCO="SELECT ?y WHERE { ?x rdf:type FEMOntology:MaterialPropertyDynamicViscosity . ?x FEMOntology:hasMaterialPropertyValue ?y . FILTER regex(str(?x),'{0}LidModelNu') }";


	

	public static final String HAS_RULE="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>   "+
										"PREFIX owl: <http://www.w3.org/2002/07/owl#>  "+
										"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>  "+
										"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  "+
										"PREFIX dao: <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#>  "+
										 "CONSTRUCT {     "+
										 " ?datafeature1 dao:isFollowedByFeature ?datafeature2 ."+                                        
										 "}"+
										 "where {"+
										 "?region1 rdf:type dao:BehaviourRegion ."+                              
										 "?region1 dao:isFollowedByRegion ?region2 ."+
										 "?region2 rdf:type dao:BehaviourRegion . "+
										 "?datafeature1 dao:isDataFeatureOf  ?region1 ."+
										 "?datafeature2 dao:isDataFeatureOf  ?region2 ."+
										 "}";
	public static final String HAS_QUERY="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
								"PREFIX owl:  <http://www.w3.org/2002/07/owl#> "+
								"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#> "+
								"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
								"PREFIX dao:  <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> "+
									"Select ?dataview "+
									"# ASK"+
									"where {"+
							                  "?dataview rdf:type dao:DataView ."+
							                  "?dataview dao:hasFeature ?x ."+
							                  "?dataview dao:hasFeature ?y ."+
							                  "?dataview dao:hasFeature ?z ."+
							                  "?x rdf:type dao:NegativeMinimum. "+
							                  "?y rdf:type dao:NegativeSlope. "+
							                  "?z rdf:type dao:PositiveSlope. "+
							                "?y dao:isFollowedByFeature ?z ."+
							   "}";
	
	public static final String DATA_VIEW_QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" + 
	"PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" + 
	"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
	"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
	"PREFIX dao: <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> \n" +
	"Select ?dataview \n"+ 
	//	# ASK
	"where { \n" +
	"	?dataview rdf:type dao:DataView .\n"+
	"	?dataview dao:hasFeature ?x .\n"+
	"	?dataview dao:hasFeature ?y . \n"+
	"	?dataview dao:hasFeature ?z . \n"+
	"	?x rdf:type dao:Minimum.  \n"+
	"	?y rdf:type dao:NegativeSlope.  \n"+
	"	?z rdf:type dao:PositiveSlope.  \n"+
	"	?y dao:isFollowedByFeature ?z . \n"+
	"	}";

	public static final String REAL_FLUID_PRESSURE = "REAL_FLUID_PRESSURE";

	public static final String QUERY_REAL_FLUID_PRESSURE = "Select ?pressureDataValue where {?node fem:holdsValueFor  ?pressure.  ?pressure sim:hasScalarValue ?pressureValue. ?pressureValue  sim:hasScalarDataValue ?pressureDataValue. } ORDER BY DESC(?pressureDataValue) LIMIT 1";

	

}
