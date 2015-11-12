package eu.deri.dataanalysis.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

 

import eu.deri.dataanalysis.reader.Reader;
import eu.deri.dataanalysis.vo.Feature;


/**
 * @author swapnil
 * This class read implements Reader interface and perform operation related with
 *  CSV file.
 */
public class CSVReader implements Reader {

	public List<Feature> read(String fileName) {
		try {
			List<Feature> data=new ArrayList<Feature>();
			au.com.bytecode.opencsv.CSVReader reader = new 
			au.com.bytecode.opencsv.CSVReader(new FileReader(fileName));
			
			/* Extracting column name and their all values */
			List<String[]> lstOfData=reader.readAll();
			String strOfColumns[]=lstOfData.get(0);
			for(Integer counter=0;counter<strOfColumns.length;counter++){
				Feature feature=new Feature();
				String columnName=strOfColumns[counter].trim();
				feature.setAttrName(columnName);
				List<Object> lstOfval=new ArrayList<Object>();
				for(Integer count=1;count<lstOfData.size();count++) {
					String val=lstOfData.get(count)[counter].trim();
					if(val!=null && (!val.equalsIgnoreCase(""))){
						lstOfval.add(val);
					}
				}
				feature.setLstOfData(lstOfval);
				data.add(feature);
			}
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	 public static void main(String[] args) {
		 Reader r=new CSVReader();
		 r.read("/home/swapnil/swapnil/DERI/DataAnalysis_config/employee.csv");
	 }

}
