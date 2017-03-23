package com.ntua.ote.logger.web.components;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class MenuController implements Serializable {

	private static final long serialVersionUID = -9046564223661389378L;
	
	private static final String CLASS_NAME = "active";
	
	private String menuEntries[] = new String[]{CLASS_NAME, "", "", ""};
	
	public void listener(ActionEvent e) {
		for(int i = 0; i < menuEntries.length; i++) {
			menuEntries[i] = "";
		}
		int id = Integer.parseInt(e.getComponent().getId().substring(4));
		menuEntries[id] = CLASS_NAME;
	}

	public String[] getMenuEntries() {
		return menuEntries;
	}
	
}
