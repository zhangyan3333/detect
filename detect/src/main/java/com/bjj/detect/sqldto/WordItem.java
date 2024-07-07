package com.bjj.detect.sqldto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordItem {

	private int mid;
	private String itemCode;
	private String itemName;

	@Override
	public String toString() {
		return "WordItem{" +
				"mid=" + mid +
				", itemCode='" + itemCode + '\'' +
				", itemName='" + itemName + '\'' +
				'}';
	}
}
