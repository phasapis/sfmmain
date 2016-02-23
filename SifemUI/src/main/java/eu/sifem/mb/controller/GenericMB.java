package eu.sifem.mb.controller;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import eu.sifem.mb.entitybean.BoundaryInternalConditionsItemEB;
import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.model.to.ParameterTO;

/**
 * @author swapnil/jbjares
 * 
 */
public abstract class GenericMB implements Serializable {

	final static Logger logger = Logger.getLogger(GenericMB.class);
	
	private static final long serialVersionUID = -2922359124374673540L;
	
	static final String PROJECT_SESSION_OBJ = "projectStreamObj";
	
	static final String CONFIG_SESSION_OBJ = "configSessionObj";
	
	static final String TRANSFORMATION_NAME = "transformationName";

	static final String PARAMETER_NAME_COMBOBOX = "loadParametersComboBox";
	
	static final String MATERIAL_PROPERTY_DIALOG = "materialPropertyDialogID";
	
	static final String SIMULATION_NAME = "simulationName";
	
	public String getCurrentSimulationName(){
		return (String) getSession().getAttribute(SIMULATION_NAME);
	}

	public void setCurrentSimulationName(String simulationName){
		getSession().setAttribute(SIMULATION_NAME,simulationName);
	}

	public void removeMessage(String msgBoardId) {
		Iterator it = FacesContext.getCurrentInstance().getMessages(msgBoardId);
		while (it.hasNext()) {
			FacesContext.getCurrentInstance().getMessages(msgBoardId).remove();
		}
	}

	public void removeMessage() {
		Iterator it = FacesContext.getCurrentInstance().getMessages(
				"mainDeskBoard");
		while (it.hasNext()) {
			FacesContext.getCurrentInstance().getMessages("mainDeskBoard")
					.remove();
		}
	}

	public void addInfoMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage("mainDeskBoard", message);
	}

	public void addWarnMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage("mainDeskBoard", message);
	}

	public void addErrorMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage("mainDeskBoard", message);
	}

	public void addFatalMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage("mainDeskBoard", message);
	}

	
	public void addInfoMessage(String summary, String detail,String deskBoardName) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(deskBoardName, message);
	}

	public void addWarnMessage(String summary, String detail,String deskBoardName) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(deskBoardName, message);
	}

	public void addErrorMessage(String summary, String detail,String deskBoardName) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(deskBoardName, message);
	}

	public void addFatalMessage(String summary, String detail,String deskBoardName) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(deskBoardName, message);
	}

	
	
	public HttpSession getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		return session;
	}

	public String getUserLoggedInName() {
		return "Andre Freitas";
	}

	public void removeSessionBean(final String beanName) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove(beanName);
	}

	public Object getSessionBean(final String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(beanName);
	}

	public void putSessionBean(final String beanKey, Object obj) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(beanKey, obj);
	}

	public String getRequestParameter(String paramName) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return request.getParameter(paramName);
	}
	
	public HttpServletRequest getRequest() {
		return  (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
	}

	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public String getAttributeFromRequest(String beanName, String attributeName)
			throws Exception {
		Object obj = getSessionBean(beanName);
		String conditionType = null;
		if (obj != null) {
			if (obj != null) {
				String firstChar = attributeName.substring(0, 1);
				attributeName = attributeName.substring(1,
						attributeName.length());
				Method method = obj.getClass().getMethod(
						"get" + firstChar.toUpperCase() + attributeName);
				conditionType = (String) method.invoke(obj);
			}
		}
		return conditionType;
	}

	public String getParameterFromRequest(String name) throws Exception {
		String result = getHttpServletRequest().getParameter(name);
		if (result == null || "".equals(result)) {
			result = (String) getHttpServletRequest().getAttribute(name);
		}
		return result;
	}

	public UIComponent findComponent(String id) {

		UIComponent result = null;
		UIComponent root = FacesContext.getCurrentInstance().getViewRoot();
		if (root != null) {
			result = findComponent(root, id);
		}
		return result;

	}

	private UIComponent findComponent(UIComponent root, String id) {

		UIComponent result = null;
		if (root.getId().equals(id))
			return root;

		for (UIComponent child : root.getChildren()) {
			if (child.getId().equals(id)) {
				result = child;
				break;
			}
			result = findComponent(child, id);
			if (result != null)
				break;
		}
		return result;
	}

	protected List<ParameterTO> adjustNameAndCodeOfLoadParametersObject(
			Map<String, String> loadParametersType) {
		List<ParameterTO> result = new ArrayList<ParameterTO>();
		for (Map.Entry<String, String> entry : loadParametersType.entrySet()) {
			ParameterTO parameterTO = new ParameterTO();
			parameterTO.setId(entry.getValue());
			parameterTO.setName(entry.getKey());
			result.add(parameterTO);
		}
		return result;
	}

	protected ParameterTO adjustNameAndCodeOfLoadParametersObject(String code,
			Map<String, String> loadParametersType) {
		ParameterTO parameterTO = new ParameterTO();
		for (Map.Entry<String, String> entry : loadParametersType.entrySet()) {
			if (code.equals(entry.getValue())) {
				parameterTO.setId(entry.getValue());
				parameterTO.setName(entry.getKey());
			}
		}
		return parameterTO;
	}

	public void removeManagedBean(final String beanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getELContext().getELResolver().setValue(fc.getELContext(), null, beanName, null);
	}
	
	protected void refreshPage() { 
	    FacesContext fc = FacesContext.getCurrentInstance(); 
	    String refreshpage = fc.getViewRoot().getViewId();

	    ViewHandler ViewH = fc.getApplication().getViewHandler(); 
	    UIViewRoot UIV = ViewH.createView(fc,refreshpage);
	    UIV.setViewId(refreshpage);
	    fc.setViewRoot(UIV); 
	}
	
	public void addExceptionMessage(Throwable e) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Error!",ExceptionUtils.getStackTrace(e));
		FacesContext.getCurrentInstance().addMessage("mainDeskBoard", message);
		logger.error("Error! "+ExceptionUtils.getStackTrace(e));
	}
	
	public String getApplicationUri() {
		  try {
		    FacesContext ctxt = FacesContext.getCurrentInstance();
		    ExternalContext ext = ctxt.getExternalContext();
                    
                    System.err.println(" ExternalContext=" + 
                                        ext.getRequestServerName() +
                                        ext.getRequestServerPort());
                    
		    URI uri = new URI(null,
		          null, ext.getRequestServerName(), ext.getRequestServerPort(),
		          null, null, null);
		    String result = uri.toASCIIString();
		    if(result.startsWith("//")){
		    	result = result.replace("//","");
		    }
		    return result;
		  } catch (URISyntaxException e) {
		    throw new FacesException(e);
		  }
		}

}
