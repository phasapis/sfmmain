package eu.deri.dataanalysis.main;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author yaskha
 * 
 */
public class WriteToExcel {

	public void writeMapToExcel(Map<String,List<String>> xAndYMap) {

		try {
			
			String filename = "D:/NewExcelFile.xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");
			
			Iterator<String> keyIter = xAndYMap.keySet().iterator();
			
			String key1 = keyIter.next(); 
			String key2 = keyIter.next();
			List<String> valueList1 = xAndYMap.get(key1);
			List<String> valueList2 = xAndYMap.get(key2);
			
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue(key1);
			rowhead.createCell(1).setCellValue(key2);

			int i = 0;
			for(String value1 : valueList1) {
				
				String value2 = valueList2.get(i);
				
				HSSFRow row = sheet.createRow((short) (i+1));
				row.createCell(0).setCellValue(value1.replace("'", ""));
				row.createCell(1).setCellValue(value2.replace("'", ""));
				
				i++;
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			System.out.println(ex);

		}

	}

}
