package com.bjj.detect.sqldto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu {

	private int menuID;
	private int menuParentID;

	//94被检量程95被检单位96阶跃列表97地址98温度99湿度
	private String menuName;

	private String menuText;
	private String menuUrl;
	private int menuLve;
	private int menuModule;
	private int menuOrder;

}
