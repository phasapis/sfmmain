package eu.sifem.utiltest;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import eu.deri.dataanalysis.util.DataType;


/**
 * 
 * @author yaskha
 *
 */

@Test(groups= { "default" })
public class DataTypeTest {

	@Test(groups= { "default" },enabled=false)
	public void isQuantitativeTest() {
		
		List<Object> testInputString = new ArrayList<Object>();
		testInputString.add("string 1");
		testInputString.add("string 2");
		testInputString.add("string 3");
		if(DataType.isQuantitative(testInputString)) {
			System.out.println(testInputString + " is of type Quantitaive.");
			System.out.println("Continuous: " + DataType.isContinuous());
		} else {
			System.out.println(testInputString + " is of type Qualititaive.");
			System.out.println("Continuous:  " + DataType.isContinuous());
		}
		
		
		List<Object> testInputDouble = new ArrayList<Object>();
		testInputDouble.add("2.98");
		testInputDouble.add("5.7");
		testInputDouble.add("8.1587");
		if(DataType.isQuantitative(testInputDouble)) {
			System.out.println(testInputDouble + " is of type Quantitaive.");
			System.out.println("Continuous: " + DataType.isContinuous());
		} else {
			System.out.println(testInputDouble + " is of type Qualititaive.");
			System.out.println("Continuous: " + DataType.isContinuous());
		}
		
		
		List<Object> testInputInteger = new ArrayList<Object>();
		testInputInteger.add("1");
		testInputInteger.add("2");
		testInputInteger.add("3");
		if(DataType.isQuantitative(testInputInteger)) {
			System.out.println(testInputInteger + " is of type Quantitaive.");
			System.out.println("Continuous: " + DataType.isContinuous());
		} else {
			System.out.println(testInputInteger + " is of type Qualititaive.");
			System.out.println("Continuous: " + DataType.isContinuous());
		}
		
	}
	
	
	@Test(groups= { "default" },enabled=true)
	public void checkIntervalTest() {
		
		List<Integer> testInputInteger = new ArrayList<Integer>();
		testInputInteger.add(1);
		testInputInteger.add(2);
		testInputInteger.add(3);
		
		if(DataType.checkInterval(testInputInteger)) {
			System.out.println("Data has Interval");
		} else {
			System.out.println("Data has no Interval");
		}
		
	}
	
}
