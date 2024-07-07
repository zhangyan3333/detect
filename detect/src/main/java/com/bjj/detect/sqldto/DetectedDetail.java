package com.bjj.detect.sqldto;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
public class DetectedDetail {

	private int did;
	private int mid;
	private Date ddate;
	private byte[] bookWord;
	private byte[] bookJl;
	private int meterResault;
	private String meterRlaCode;
	private String meterRlaType;
	private int checkStep;
	private int checkResault;
	private int isPrint;

	@Override
	public String toString() {
		return "DetectedDetail{" +
				"did=" + did +
				", mid=" + mid +
				", ddate=" + ddate +
				", bookWord=" + Arrays.toString(bookWord) +
				", bookJl=" + Arrays.toString(bookJl) +
				", meterResault=" + meterResault +
				", meterRlaCode='" + meterRlaCode + '\'' +
				", meterRlaType='" + meterRlaType + '\'' +
				", checkStep=" + checkStep +
				", checkResault=" + checkResault +
				", isPrint=" + isPrint +
				'}';
	}
}
