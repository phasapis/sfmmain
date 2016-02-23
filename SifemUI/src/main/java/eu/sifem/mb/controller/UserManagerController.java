package eu.sifem.mb.controller;

import java.io.IOException;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userManagerController")
@SessionScoped
public class UserManagerController extends GenericMB {

	private static final long serialVersionUID = 655387175651817353L;

	public void logout() throws IOException {
		for(Entry<String, Object> entry:FacesContext.getCurrentInstance().getViewRoot().getViewMap().entrySet()){
			FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(entry.getValue());
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getApplicationMap().get(entry.getKey()) ;// get an @ApplicationScoped bean instance
			ec.getSessionMap().get(entry.getKey()); // get a @SessionScoped bean instance
			ec.getRequestMap().get(entry.getKey()); // get a @RequestScoped bean instance
			removeManagedBean(entry.getKey());
		}
		getSession().invalidate();
		refreshPage();
		//http://localhost:8080/Sifem/index.jsp
		addInfoMessage("Logout","The session was closed with successful!");
	}

}
