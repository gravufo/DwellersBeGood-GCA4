package com.dwellersbegood;

import java.io.Serializable;

import android.graphics.Paint;

public class GData implements Serializable{
	private static final long serialVersionUID = 1L;
	private int m_Coins = 0;
	private int m_DistanceTraveled = 0;
	
	public GData()
	{
		this.m_Coins = 0;
		this.m_DistanceTraveled = 0;
	}
	
	public void addCoins(int coins){
		m_Coins += coins;
	}
	
	public void addDistanceTraveled(int distanceTraveled){
		m_DistanceTraveled += distanceTraveled;
	}
	
	public void setCoins(int coins){
		m_Coins = coins;
	}
	
	public int getCoins(){
		return m_Coins;
	}
	
	public void setDistanceTraveled(int distanceTraveled){
		m_DistanceTraveled = distanceTraveled;
	}
	
	public int getDistanceTraveled(){
		return m_DistanceTraveled;
	}

}
