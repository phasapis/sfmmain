package eu.deri.dataanalysis.analyser;

import java.util.List;

import eu.deri.dataanalysis.vo.Feature;

public interface Analyser {
	public Object analyse();

	public Object analyse(Object object);
	
}
