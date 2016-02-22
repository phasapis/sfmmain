package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.List;

import eu.deri.dataanalysis.util.AnalyserType;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil / yaskha
 * Pipeline for execute all analyser
 */
public class AnalyserPipeline {
	private List<Analyser> lstOfAnalyser;

	public AnalyserPipeline() {
		lstOfAnalyser = new ArrayList<Analyser>();
	}

	public void addAnalyser(AnalyserType type, Analyser analyser) {
		
		if (analyser == null){
			lstOfAnalyser.add(AnalyserFactory.createInstance(type));
			return;
		}
		
		if (analyser != null){
			lstOfAnalyser.add(analyser);
			return;
		}
		
	}

	public List<Analyser> getAddedAnalyser() {
		return lstOfAnalyser;
	}
	 
	public List<PairFeature> execute(Object lstOfFeature) {
//		for(Analyser analyser:lstOfAnalyser){
//			if("InterpretationAnalyser".equals(analyser.getClass().getSimpleName())){
//				lstOfFeature=analyser.analyse(lstOfFeature);
//				continue;
//			}
//			lstOfFeature=analyser.analyse(lstOfFeature);
//		}
		for(Analyser analyser:lstOfAnalyser){
			lstOfFeature=analyser.analyse(lstOfFeature);
			List<Feature> f = (List<Feature>) lstOfFeature;
			
		}
		
		List<PairFeature> pairFList = (List<PairFeature>) lstOfFeature;
//		for (PairFeature pairF : pairFList) {
//			System.out.println("Feature X Attrib: " + pairF.getFeatureX().getAttrName());
//			System.out.println("Feature X Axis:" + pairF.getFeatureX().getAxis());
//			
//			System.out.println("Feature Y Attrib: " + pairF.getFeatureY().getAttrName());
//			System.out.println("Feature Y Axis:" + pairF.getFeatureY().getAxis());
//			
//		}
		
		return pairFList;
		
		}
}
