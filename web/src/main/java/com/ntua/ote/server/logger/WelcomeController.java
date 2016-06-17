package com.ntua.ote.server.logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class WelcomeController {

	private int numberOfClicks;
	
	public void increase(){
		numberOfClicks += 1;
	}

	public int getNumberOfClicks() {
		return numberOfClicks;
	}

	public void setNumberOfClicks(int numberOfClicks) {
		this.numberOfClicks = numberOfClicks;
	}
	
}
