package eu.sifem.dao;



import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;


/**
 * 
 * @author jbjares
 * 
 */
//TODO rewrite
public class SimplePreparedStatementForSPARQL implements PreparedStatement{
	
	private String query;

	public SimplePreparedStatementForSPARQL(String query) {
		this.query=query;
	}
	
	
	
	public SimplePreparedStatementForSPARQL() {}



	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return query;
	}
	



	@Override
	public void addBatch(String arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void cancel() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void clearBatch() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void clearWarnings() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void close() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void closeOnCompletion() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public boolean execute(String arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int[] executeBatch() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int executeUpdate(String arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public Connection getConnection() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getFetchDirection() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getFetchSize() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getMaxRows() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean getMoreResults() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getQueryTimeout() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public ResultSet getResultSet() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getResultSetType() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int getUpdateCount() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean isClosed() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public boolean isPoolable() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public void setCursorName(String arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setFetchSize(int arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setMaxRows(int arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public void addBatch() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void clearParameters() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public boolean execute() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public ResultSet executeQuery() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public int executeUpdate() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
	}



	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNString(int parameterIndex, String value)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scaleOrLength) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setString(int parameterIndex, String str) throws SQLException {
		try{
			query = query.replace("?"+parameterIndex,str);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	

	}
	

	
	public String addQuotationMarks(String var){
		if(var!=null && !"".equals(var) && !var.contains("\"")){
				var = "\""+var+"\"";			
		}
		return var;
	}
	

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}



	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		throw new RuntimeException("Method not implemented yet!");
		
	}
	
	public List<String> getStringAsList(String stringAsList){
		List<String> result = new ArrayList<String>();
		
		while(stringAsList.contains("{")){
			stringAsList = stringAsList.replace("{", "").trim();
		}
		while(stringAsList.contains("}")){
			stringAsList = stringAsList.replace("}", "").trim();
		}

		while(stringAsList.contains("[")){
			stringAsList = stringAsList.replace("[", "").trim();
		}
		
		while(stringAsList.contains("]")){
			stringAsList = stringAsList.replace("]", "").trim();
		}
		
		while(stringAsList.contains(",")){
			stringAsList = stringAsList.replace(",", ";").trim();
		}
		
		String[] stringArr = stringAsList.split(";");
		
		int arrLength = stringArr.length;
		
		for(int i=0;i<arrLength;i++){
			result.add(stringArr[i].trim());
		}
		
		return result;
	}
	
	public static String getListAsString(List<String> listAsString){
		String listAsStringStr = Arrays.deepToString(listAsString.toArray(new String[]{}));
		if(listAsStringStr==null || "".equalsIgnoreCase(listAsStringStr)){
			return ""; 
		}
		while(listAsStringStr.contains("{")){
			listAsStringStr = listAsStringStr.replace("{", "").trim();
		}
		while(listAsStringStr.contains("}")){
			listAsStringStr = listAsStringStr.replace("}", "").trim();
		}

		while(listAsStringStr.contains("[")){
			listAsStringStr = listAsStringStr.replace("[", "").trim();
		}
		
		while(listAsStringStr.contains("]")){
			listAsStringStr = listAsStringStr.replace("]", "").trim();
		}
		
		while(listAsStringStr.contains(",")){
			listAsStringStr = listAsStringStr.replace(",", ";").trim();
		}
		
	
		if(listAsString==null || listAsString.isEmpty()){
			return ""; 
		}
		
		StringBuilder resultSb = new StringBuilder();
		int size = listAsString.size();
		int count = 1;
		for(String str:listAsString){
			resultSb.append(str);
			if(count<size){
				resultSb.append(";");				
			}
			count++;
		}

		String result = resultSb.toString();
		if(result!=null && result.startsWith(";")){
			result = result.substring(1,result.length());
		}
		
		return result;
	}



	public String getListAsString(List<?> transformations,String attribName) throws Exception {
		List<String> strList = new ArrayList<String>();
		for(Object obj:transformations){
			Method method = obj.getClass().getDeclaredMethod("get"+attribName);
			if(!strList.isEmpty() && strList.contains(obj)){
				continue;
			}
			strList.add((String) method.invoke(obj));
		}
		return getListAsString(strList);
	}



	@SuppressWarnings("unchecked")
	public List getStringAsList(String stringAsList,Class<?> clazz, String parameter) throws Exception {
		List result = new ArrayList();
		
		while(stringAsList.contains("{")){
			stringAsList = stringAsList.replace("{", "").trim();
		}
		while(stringAsList.contains("}")){
			stringAsList = stringAsList.replace("}", "").trim();
		}

		while(stringAsList.contains("[")){
			stringAsList = stringAsList.replace("[", "").trim();
		}
		
		while(stringAsList.contains("]")){
			stringAsList = stringAsList.replace("]", "").trim();
		}
		
		while(stringAsList.contains(",")){
			stringAsList = stringAsList.replace(",", ";").trim();
		}
		
		String[] stringArr = stringAsList.split(";");
		
		int arrLength = stringArr.length;
		
		for(int i=0;i<arrLength;i++){
			Object obj = clazz.newInstance();
			Method method = clazz.getDeclaredMethod("set"+parameter,String.class);
			method.invoke(obj,stringArr[i].trim());
			result.add(obj);
		}
				
		return result;
	}

	
}
