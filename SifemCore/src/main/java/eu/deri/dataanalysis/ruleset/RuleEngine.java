package eu.deri.dataanalysis.ruleset;

import java.lang.reflect.Constructor;


/**
 * 
 * @author swapnil
 *
 */
public class RuleEngine {
	
	public Object fire(Class ruleName,Object feature) throws Exception {
		
		Constructor contr = ruleName.getDeclaredConstructors()[0];
		contr.setAccessible(true);
		
		Rule rule=(Rule)contr.newInstance();
		
		return rule.test(feature);
		
	}
}
