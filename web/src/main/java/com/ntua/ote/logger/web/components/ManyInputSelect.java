package com.ntua.ote.logger.web.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.util.StringUtils;

public class ManyInputSelect {

	private List<ManyInputSelectItem> inputs;
	private String input;
	private Stack<String> colorStack;
	
	/** Initialize the stack of colors with five preset colors */
	public ManyInputSelect(){
		inputs = new ArrayList<>();
		colorStack = new Stack<>();
		colorStack.push("#a020f0");
		colorStack.push("#FF9900");
		colorStack.push("#0000FF");
		colorStack.push("#228b22");
		colorStack.push("#FF0000");
	}
	
	/** Checks if the input has already been added */
	private boolean exists(String input){
		for(ManyInputSelectItem item : inputs) {
			if(input.equals(item.input)) {
				return true;
			}
		}
		return false;
	}
	
	/** Add an entry from input. Remove the top color from the stack. 
	 * Create a ManyInputSelectItem with the removed color and input and add it to the list */
	public void add(){
		if(StringUtils.hasLength(input) && !exists(input) && !colorStack.isEmpty()) {
			ManyInputSelectItem item = new ManyInputSelectItem();
			item.input = input;
			item.color = colorStack.pop();
			inputs.add(item);
			input="";
		}
	}
	
	/** Remove an entry from input. Adds the removed entry's color to the stack. 
	 * Removes the ManyInputSelectItem from the list */
	public void remove(ManyInputSelectItem item){
		colorStack.push(item.color);
		inputs.remove(item);
	}
	
	/** The class ManyInputSelectItem */
	public class ManyInputSelectItem {
		private String input;
		private String color;
		public String getInput() {
			return input;
		}
		public void setInput(String input) {
			this.input = input;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
	}

	public List<ManyInputSelectItem> getInputs() {
		return inputs;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
}
