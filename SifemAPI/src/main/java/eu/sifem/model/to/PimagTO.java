package eu.sifem.model.to;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PimagTO  extends Simple2DGraphTO{

		private static final long serialVersionUID = 5826870848652756309L;

		public PimagTO(){}
		
		public PimagTO(Simple2DGraphTO simple2DGraphTO){
			this.setxView(super.getxView());
			this.setyView(super.getyView());
		}

		@Override
		public List<Double> getxView() {
			// TODO Auto-generated method stub
			return super.getxView();
		}

		@Override
		public void setxView(List<Double> xView) {
			// TODO Auto-generated method stub
			super.setxView(xView);
		}

		@Override
		public List<Double> getyView() {
			// TODO Auto-generated method stub
			return super.getyView();
		}

		@Override
		public void setyView(List<Double> yView) {
			// TODO Auto-generated method stub
			super.setyView(yView);
		}
		
	
}
