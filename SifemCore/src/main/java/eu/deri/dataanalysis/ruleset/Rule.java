package eu.deri.dataanalysis.ruleset;

import java.util.List;

import eu.deri.dataanalysis.vo.Feature;

public interface Rule {
	public String ruleName=null;
	public Object test(Object lstOfFeature);
}
