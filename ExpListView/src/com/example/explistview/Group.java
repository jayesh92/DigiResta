package com.example.explistview;

import java.util.ArrayList;
import java.util.List;

public class Group {

	public String string;
	public String img_name;
	public final List<String> children = new ArrayList<String>(); 
	public Group(String grp_name, String img) {
		this.string=grp_name;
		this.img_name=img;
	}

}
