package org.bermillion;

import java.io.Serializable;

/**
 * Created by Emilio Rueda on 06/04/2015.
 */

public class CMen_Object implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean state;
	private String mes;
	private int type;
	
	public CMen_Object(boolean state, String mes, int type)
	{
		this.state=state;
		this.mes=mes;
		this.type=type;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
