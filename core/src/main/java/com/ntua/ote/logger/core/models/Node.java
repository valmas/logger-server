package com.ntua.ote.logger.core.models;

import java.util.HashSet;
import java.util.Set;

public class Node {

	public Node(String phoneNumber, int level) {
		this.phoneNumber = phoneNumber;
		this.level = level;
		children = new HashSet<>();
	}
	
	public Node(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		children = new HashSet<>();
	}

	private String phoneNumber;
	
	private Set<Node> children;
	
	private int level;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Node> getChildren() {
		return children;
	}

	public void setChildren(Set<Node> children) {
		this.children = children;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(phoneNumber.equals(((Node) obj).getPhoneNumber())){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return phoneNumber.hashCode();
	}

	@Override
	public String toString() {
		return phoneNumber;
	}
	
}
