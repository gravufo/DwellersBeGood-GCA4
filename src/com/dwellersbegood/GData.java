package com.dwellersbegood;

import java.io.Serializable;

import android.graphics.Paint;

public class GData implements Serializable{
	private static final long serialVersionUID = 1L;
	private int m_Dolla;
	
	public GData()
	{
		this.m_Dolla = 0;
	}
	
	public void setDolla(int newDolla){
		m_Dolla = newDolla;
	}
	
	public int getDolla(){
		return m_Dolla;
	}

}
