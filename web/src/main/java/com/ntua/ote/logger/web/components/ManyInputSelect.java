package com.ntua.ote.logger.web.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.util.StringUtils;

public class ManyInputSelect {

	private List<ManyInputSelectItem> inputs;
	
	private String input;
	
	private Stack<String> colorStack;
	
	public ManyInputSelect(){
		inputs = new ArrayList<>();
		colorStack = new Stack<>();
		colorStack.push("#a020f0");
		colorStack.push("#FF9900");
		colorStack.push("#0000FF");
		colorStack.push("#228b22");
		colorStack.push("#FF0000");
	}
	
	private boolean exists(String input){
		for(ManyInputSelectItem item : inputs) {
			if(input.equals(item.input)) {
				return true;
			}
		}
		return false;
	}
	
	public void add(){
		if(StringUtils.hasLength(input) && !exists(input) && !colorStack.isEmpty()) {
			ManyInputSelectItem item = new ManyInputSelectItem();
			item.input = input;
			item.color = colorStack.pop();
			inputs.add(item);
			input="";
		}
	}
	
	public void remove(ManyInputSelectItem item){
		colorStack.push(item.color);
		inputs.remove(item);
	}
	
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
