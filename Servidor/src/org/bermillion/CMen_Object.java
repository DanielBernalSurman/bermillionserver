package org.bermillion;

import java.io.Serializable;

public class CMen_Object implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean state;
	private String men;
	private int type;
	
	public CMen_Object(boolean state, String men, int type)
	{
		this.state=state;
		this.men=men;
		this.type=type;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMen() {
		return men;
	}

	public void setMen(String men) {
		this.men = men;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
