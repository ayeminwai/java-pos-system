package com.java.sample.ezpos.constant;
public interface IRegEx {
	
	public final String ALPHA =  "[a-zA-Z]+";
	public final String NUMARIC =  "[0-9]+";
	public final String ALPHA_NUMARIC =  "[0-9a-zA-Z]+";
	public final String ALPHA_NUMARIC_SPACE =  "[0-9a-zA-Z\\s]+";
	public final String ALPHA_SPACE =  "[a-zA-Z\\s]+";
	public final String ADDRESS =  "[0-9a-zA-Z\\s-,]+";
	public final String CURR_FORMAT = "^((0|[1-9][0-9]{0,9})(\\.([0-9][0-9])))$";
	public final String CURR_CODE_NUM = "^([0-9]{3})$";
	public final String DATE_FORMAT = "^([0-2][0-9]|(3)[0-1])(((0)[0-9])|((1)[0-2]))\\d{4}$";
	public final String STATUS_ALPHA = "[A I]{1}";

}
