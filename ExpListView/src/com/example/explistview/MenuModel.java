package com.example.explistview;

import android.content.Context;

public class MenuModel {
	private String price;
	private String recepie;
	private String name;
	private String category;
	private String desc;
	public MenuModel ()
	{
		this.price="";
		this.recepie="";
		this.name="";
		this.category="";
		this.desc="desc";
	}
	public void setdesc(String desc)
	{
		this.desc=desc;
	}
	public void setprice(String price)
	{
		this.price=price;
	}
	public void setrecepie(String recepie)
	{
		this.recepie=recepie;
	}
	public void setname(String name)
	{
		this.name=name;
	}
	public void setcategory(String category)
	{
		this.category=category;
	}
	public String getprice()
	{
		return this.price;
	}
	public String getname()
	{
		return this.name;
	}
	public String getrecepie()
	{
		return this.recepie;
	}
	public String getcategory()
	{
		return this.category;
	}
	public String getdesc()
	{
		return this.desc;
	}
}
