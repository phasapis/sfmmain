package eu.deri.dataanalysis.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author yaskha
 * 
 *         DataType is a utility class to find the type of data being analyzed.
 * 
 */
public class DataType {
	
	private static boolean isContinuous = false;
	private static boolean isDiscrete = false;
	private static boolean isInterval = false;
	private static List<Integer> integerList = new ArrayList<Integer>();

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isInteger(List<Object> data) {

		for (Object value : data) {

			try {
				System.out.println("[DA][DT][I] " + value);
				integerList.add(Integer.parseInt(value.toString()));

			} catch (NumberFormatException e) {
				return false;
			}

		}

		return true;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isFloat(List<Object> data) {

		for (Object value : data) {

			try {
				System.out.println("[DA][DT][F] " + value);
				Float.parseFloat(value.toString());

			} catch (NumberFormatException e) {
				return false;
			}

		}

		return true;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isDouble(List<Object> data) {

		for (Object value : data) {

			try {
				System.out.println("[DA][DT][D] " + value);
				Double.parseDouble(value.toString());

			} catch (NumberFormatException e) {
				return false;
			}

		}

		return true;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isQuantitative(List<Object> data) {

		if (isInteger(data)) {
			isContinuous = false;
			isDiscrete = true;
			isInterval = checkInterval(integerList);
			return true;
		} else if(isFloat(data) || isDouble(data)) {
			isContinuous = true;
			isDiscrete = false;
			isInterval = false;
			return true;
		} else {
			isContinuous = false;
			isDiscrete = false;
			isInterval = false;
			return false;
		}

	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean checkInterval(List<Integer> data) {
		
		boolean interval = true;
		Integer intervalValue = 0;
		
		for (int i=0; i<data.size(); i++) {

			if(i != 0) {
				
				Integer diff = data.get(i) - data.get(i-1);
				
				if(i != 1) {
					if(!diff.equals(intervalValue)) {
						interval = false;
						break;
					} else {
						intervalValue = diff;
					}
				} else {
					intervalValue = diff;
				}
				
			}
			
		}
		
		return interval;
		
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean isContinuous() {
		if(isContinuous) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean isDiscrete() {
		if(isDiscrete) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean isInterval() {
		if(isInterval) {
			return true;
		} else {
			return false;
		}
	}

}
