package eu.deri.dataanalysis.analyser;


public class CompareAnalyser implements Analyser {
	private String functionDefn;
	private double max;
	private double min;
	private double interval;
	public CompareAnalyser() {
		// TODO Auto-generated constructor stub
	}
	public CompareAnalyser(String functionDefn,double max,double min,double interval) {
		this.functionDefn=functionDefn;
		this.max=max;
		this.min=min;
		this.interval=interval;
	}
	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
//		RInterface engine=new RInterface();
//		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
//		engine.startR();
//		String x="";
//		Feature featureY=new Feature();
//		Feature featureX=new Feature();
//		for(double i=min;i<=max;i=i+interval){
//			x+=i+",";
//			featureX.getLstOfData().add(i);
//		}
//		String command="x=c("+x.substring(0,x.length()-2)+")";
//		RCommandLine.execute(engine, command);
//		command="x=y="+functionDefn;
//		RCommandLine.execute(engine, command);
//		command="y";
//		double vals[]=RCommandLine.execute(engine, command).asDoubleArray();
//		for(double val:vals){
//			featureY.getLstOfData().add(val);
//		}
//		PairFeature pairFeature=new PairFeature();
//		pairFeature.setFeatureX(featureX);
//		pairFeature.setFeatureY(featureY);
//		lstOfPairOfFeature.add(pairFeature);
//		
//		Object obj=new DistanceCalculation().perform(lstOfPairOfFeature);
		//new FunctionAnalyser().analyse(obj);
		//RCommandLine.execute(engine, command);
		//System.out.println();
		//Feature featureY=lstOfPairOfFeature.get(0).getFeatureY();
		//Feature featureX=lstOfPairOfFeature.get(0).getFeatureX();
		
		
		
		return null;
	}

}
