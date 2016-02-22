package eu.sifem.dataanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import eu.sifem.data.TurtleFormatRDFWriter;
import eu.sifem.model.to.ViewTO;
import eu.sifem.ontologies.DataAnalysisOntology;
import eu.sifem.ontologies.FemOntology;
import eu.sifem.ontologies.SimulationOntology;
import eu.sifem.service.IDataAnalyzerService;
import eu.sifem.service.IFeatureExtractorService;

@Service(value="dataAnalyzer")
public class DataAnalyzer implements IDataAnalyzerService{

	
	@Override
	public void addDataAnalysisInstancesService(IFeatureExtractorService extractor,
			List<ViewTO> views, String experimentNameUri, String femCase,
			String daoDir, String basePath) throws Exception{
		experimentNameUri = experimentNameUri.replace(".owl#", femCase+".owl#");
		TurtleFormatRDFWriter writer = new TurtleFormatRDFWriter();
		String experimentName = experimentNameUri.substring(experimentNameUri.lastIndexOf("/") + 1, experimentNameUri.lastIndexOf(".owl#"));
		Map<String, String> prefixUriMap = new HashMap<String, String>();
		prefixUriMap.put(FemOntology.prefixUsed, "http://www.sifemontologies.com/ontologies/FEMOntology.owl#");
		prefixUriMap.put(DataAnalysisOntology.prefixUsed, "http://www.sifemontologies.com/ontologies/DataAnalysis.owl#");
		prefixUriMap.put(SimulationOntology.prefixUsed, "http://www.sifemontologies.com/ontologies/Simulation.owl#");
		prefixUriMap.put(experimentName, experimentNameUri);
		experimentName = experimentName + ":";
		writer.addPrefixes(prefixUriMap);
		List<String>  properties = new ArrayList<String>();
		//considering distCavBase as the segmentation dimension
		//for(DataView view : views){
		
		if(views==null || views.isEmpty()){
			return;
		}
		
		ViewTO view = (ViewTO) views.get(0);
		Map<String, List<String>> map = view.getDimValMap();
		List<String> datax = map.get("distCavBase");			
		List<String> datay = map.get("velXVals");		
		String varNamx = "distCavBase";
		String varNamy = "velXVals";
		//extractor.plot2dGFeat(datax, "distCavBase", datay, "velXVals", femCase,basePath);			
		System.out.println("plotted");
		Double[] slopes = extractor.computeSlopesService(varNamx, varNamy, datax.size());			
		int globalMaximaIndex = extractor.findGlobalMaximaIndexService(getDoubleArray(datax), getDoubleArray(datay));
		int globalMinimaIndex = extractor.findGlobalMinimaIndexService(getDoubleArray(datax), getDoubleArray(datay));
		String dataViewVelXvsDistFromCavBaseResource = experimentName + "DataViewVelocityXDistanceFromCavityBase";
		List<String> propertiesDataView = new ArrayList<String>();
		String dataViewDimensionDistanceFromCavityBaseResource = experimentName + "DataViewDimensionDistanceFromCavityBase";			
		List<String> propsDist = new ArrayList<String>(); 
		String dataViewDimensionVelocityXResource = experimentName + "DataViewDimension_VelocityX";
		List<String> propsVel = new ArrayList<String>(); 
		for(int i=0; i< datax.size(); i++){
			double slope = slopes[i];			
			properties = new ArrayList<String>();
			String slopeResource = experimentName + "Slope" + i;
			String slopeProperty = DataAnalysisOntology.Property.ObjectProperty.hasSlopeProp + " " + slopeResource +";";
			String dataViewDimensionPairDistResource = experimentName + "DataViewDimensionPair_Distance" + i;
			String indexValue = DataAnalysisOntology.Property.DataProperty.hasDataViewDimensionIndexProp + " " + i + ";";
			String dimVal = DataAnalysisOntology.Property.DataProperty.hasDataViewDimensionValueProp + " " + datax.get(i) + ";";
			properties.add(indexValue);properties.add(dimVal);properties.add(slopeProperty);				
			writer.addRDF(dataViewDimensionPairDistResource, DataAnalysisOntology.Class.dataViewDimensionPairClass, properties);
			properties = new ArrayList<String>();
			String dataViewDimensionPairVelXResource = experimentName + "DataViewDimensionPair_VelocityX" + i;
			indexValue = DataAnalysisOntology.Property.DataProperty.hasDataViewDimensionIndexProp + " " + i + ";";
			dimVal = DataAnalysisOntology.Property.DataProperty.hasDataViewDimensionValueProp + " " + datay.get(i) + ";";
			properties.add(indexValue);properties.add(dimVal);properties.add(slopeProperty);
			writer.addRDF(dataViewDimensionPairVelXResource, DataAnalysisOntology.Class.dataViewDimensionPairClass, properties);
			properties = new ArrayList<String>();
			if(i==globalMaximaIndex){
				String maximaProp = DataAnalysisOntology.Property.ObjectProperty.hasMaximumProp + " " + dataViewDimensionPairDistResource + ";";
				propsDist.add(maximaProp);			
			} 
			if(i==globalMinimaIndex){
				String minimaProp = DataAnalysisOntology.Property.ObjectProperty.hasMinimumProp + " " + dataViewDimensionPairDistResource + ";";
				propsDist.add(minimaProp);
				String minimumResource = experimentName + "Minimum" + i;
				List<String> negMinProps = new ArrayList<String>();
				negMinProps.add(DataAnalysisOntology.Property.ObjectProperty.hasDataViewDimensionPairProp + 
						" " + dataViewDimensionPairDistResource + ";");
				negMinProps.add(DataAnalysisOntology.Property.ObjectProperty.hasDataViewDimensionPairProp + 
						" " + dataViewDimensionPairVelXResource + ";");
				writer.addRDF(minimumResource, DataAnalysisOntology.Class.minimumClass, negMinProps);
				propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasFeatureProp + " " + minimumResource + ";");
				String region1Resource = experimentName + "Region1";
				String region2Resource = experimentName + "Region2";
				propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasRegionProp + " " + region1Resource + ";");
				propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasRegionProp + " " + region2Resource + ";");
				List<String> region1Props = new ArrayList<String>();
				List<String> region2Props = new ArrayList<String>();
				region1Props.add(DataAnalysisOntology.Property.ObjectProperty.hasRegionStartProp + " " + experimentName + "DataViewDimensionPair_Distance" + 0 + ";");
				region1Props.add(DataAnalysisOntology.Property.ObjectProperty.hasRegionEndProp + " " + dataViewDimensionPairDistResource + ";");					
				region1Props.add(DataAnalysisOntology.Property.ObjectProperty.isFollowedByRegionProp + " " + region2Resource + ";");
				region1Props.add(DataAnalysisOntology.Property.ObjectProperty.hasAssociatedDimensionProp + " " + dataViewDimensionVelocityXResource + ";");
				region1Props.add(DataAnalysisOntology.Property.ObjectProperty.isRegionOfProp + " " + dataViewVelXvsDistFromCavBaseResource + ";");
				region1Props.add(DataAnalysisOntology.Property.DataProperty.hasRegionIndexProp + " " + 1 + ";");
				region1Props.add(DataAnalysisOntology.Property.ObjectProperty.hasSegmentationDimensionProp + " " + dataViewDimensionDistanceFromCavityBaseResource + ";");
				//String followedSlopeResource = null;
				if(slopes[i+1] > 0.0){
					String positiveSlopeResource = experimentName + "PositiveSlopeRegion" + 2;
					//followedSlopeResource = positiveSlopeResource;
					List<String> slopeProps = new ArrayList<String>();
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isSlopeOfProp + " " + region2Resource + ";");
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isDataFeatureOfProp + " " + region2Resource + ";");
					writer.addRDF(positiveSlopeResource, DataAnalysisOntology.Class.positiveSlopeClass, slopeProps);						
					region2Props.add(DataAnalysisOntology.Property.ObjectProperty.hasSlopeProp + " " + positiveSlopeResource + ";");
					propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasFeatureProp + " " + positiveSlopeResource + ";");						
				} else if(slopes[i+1]<0.0){
					String negativeSlopeResource = experimentName + "NegativeSlopeRegion" + 2;
					//followedSlopeResource = negativeSlopeResource;						
					List<String> slopeProps = new ArrayList<String>();
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isSlopeOfProp + " " + region2Resource + ";");
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isDataFeatureOfProp + " " + region2Resource + ";");
					writer.addRDF(negativeSlopeResource, DataAnalysisOntology.Class.negativeSlopeClass, slopeProps);
					region2Props.add(DataAnalysisOntology.Property.ObjectProperty.hasSlopeProp + " " + negativeSlopeResource + ";");			
					propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasFeatureProp + " " + negativeSlopeResource + ";");
				}
				if(slopes[i-1]<0.0){
					String negativeSlopeResource = experimentName + "NegativeSlopeRegion" + 1;
					List<String> slopeProps = new ArrayList<String>();
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isSlopeOfProp + " " + region1Resource + ";");
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isDataFeatureOfProp + " " + region1Resource + ";");
					//						if(followedSlopeResource!=null)
					//							slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isFollowedByFeatureProp + " " +  followedSlopeResource + ";");
					writer.addRDF(negativeSlopeResource, DataAnalysisOntology.Class.negativeSlopeClass, slopeProps);						
					region1Props.add(DataAnalysisOntology.Property.ObjectProperty.hasSlopeProp + " " + negativeSlopeResource + ";");
					propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasFeatureProp + " " + negativeSlopeResource + ";");
				} else if( slopes[i-1] > 0.0){
					String positiveSlopeResource = experimentName + "PositiveSlopeRegion" + 1;
					List<String> slopeProps = new ArrayList<String>();
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isSlopeOfProp + " " + region1Resource + ";");
					slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isDataFeatureOfProp + " " + region1Resource + ";");
					//						if(followedSlopeResource!=null)							
					//							slopeProps.add(DataAnalysisOntology.Property.ObjectProperty.isFollowedByFeatureProp + " " + followedSlopeResource + ";");						
					writer.addRDF(positiveSlopeResource, DataAnalysisOntology.Class.positiveSlopeClass, slopeProps);
					region1Props.add(DataAnalysisOntology.Property.ObjectProperty.hasSlopeProp + " " + positiveSlopeResource + ";");
					propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasFeatureProp + " " + positiveSlopeResource + ";");
				}
				region2Props.add(DataAnalysisOntology.Property.ObjectProperty.hasRegionEndProp + " " + experimentName + "DataViewDimensionPair_Distance" + globalMaximaIndex + ";");
				region2Props.add(DataAnalysisOntology.Property.ObjectProperty.hasRegionStartProp + " " + dataViewDimensionPairDistResource + ";");					
				region2Props.add(DataAnalysisOntology.Property.ObjectProperty.isFollowedByRegionProp + " " + region2Resource + ";");
				region2Props.add(DataAnalysisOntology.Property.ObjectProperty.hasAssociatedDimensionProp + " " + dataViewDimensionVelocityXResource + ";");
				region2Props.add(DataAnalysisOntology.Property.ObjectProperty.isRegionOfProp + " " + dataViewVelXvsDistFromCavBaseResource + ";");
				region2Props.add(DataAnalysisOntology.Property.DataProperty.hasRegionIndexProp + " " + 2 + ";");
				region2Props.add(DataAnalysisOntology.Property.ObjectProperty.hasSegmentationDimensionProp + " " + dataViewDimensionDistanceFromCavityBaseResource + ";");
				writer.addRDF(region1Resource, DataAnalysisOntology.Class.behaviourRegionClass, region1Props);
				writer.addRDF(region2Resource, DataAnalysisOntology.Class.behaviourRegionClass, region2Props);					
			}
			String isSlopeOf = DataAnalysisOntology.Property.ObjectProperty.isSlopeOfProp + " " + dataViewDimensionPairDistResource + ";";
			String slopeVal = DataAnalysisOntology.Property.DataProperty.hasSlopeValueProp + " " + slope + ";";
			properties.add(isSlopeOf);properties.add(slopeVal);
			if(slope > 0.0)
				writer.addRDF(slopeResource, DataAnalysisOntology.Class.positiveSlopeClass, properties);	
			else if(slope < 0.0)
				writer.addRDF(slopeResource, DataAnalysisOntology.Class.negativeSlopeClass, properties);	
			else if(slope == 0.0)
				writer.addRDF(slopeResource, DataAnalysisOntology.Class.neutralSlopeClass, properties);
			properties = new ArrayList<String>();
			String hasDataViewDimensionPairDist = DataAnalysisOntology.Property.ObjectProperty.hasDataViewDimensionPairProp + " " + dataViewDimensionPairDistResource + ";";				
			propsDist.add(hasDataViewDimensionPairDist);
			String hasDataViewDimensionPairVelX = DataAnalysisOntology.Property.ObjectProperty.hasDataViewDimensionPairProp + " " + dataViewDimensionPairVelXResource + ";";
			propsVel.add(hasDataViewDimensionPairVelX);				
		}
		writer.addRDF(dataViewDimensionDistanceFromCavityBaseResource, DataAnalysisOntology.Class.dataViewDimensionClass, propsDist);
		properties = new ArrayList<String>();
		writer.addRDF(dataViewDimensionDistanceFromCavityBaseResource, DataAnalysisOntology.Class.distanceFromCavityBaseClass, properties);
		writer.addRDF(dataViewDimensionVelocityXResource, DataAnalysisOntology.Class.dataViewDimensionClass, propsVel);
		properties = new ArrayList<String>();
		writer.addRDF(dataViewDimensionVelocityXResource, FemOntology.Class.velocityClass, properties);
		propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasDataViewDimensionProp + " " + dataViewDimensionDistanceFromCavityBaseResource + ";");
		propertiesDataView.add(DataAnalysisOntology.Property.ObjectProperty.hasDataViewDimensionProp + " " + dataViewDimensionVelocityXResource + ";");
		writer.addRDF(dataViewVelXvsDistFromCavBaseResource, DataAnalysisOntology.Class.dataViewClass, propertiesDataView);	
		writer.write(daoDir + femCase + "Dao.ttl");
	}

	
	private List<Double> getDoubleArray(List<String> strList){
		List<Double> list = new ArrayList<Double>();
		for(String str : strList)
			list.add(Double.parseDouble(str.trim()));
		return list;	
	}	
	
}