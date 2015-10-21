/**
 * @(#)NumberUtil.java 2007-7-16
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-7-16
 * @since       JDK1.5
 */

public class NumberUtil {

	static DecimalFormat format = new DecimalFormat("0.#################");
    static NumberFormat cash = new DecimalFormat("#,##0.00");
	/**
	 * 分割数字
	 * @param num
	 * @param count
	 * @return
	 */
	public static int[][] splitNumbers(double num, int count) {
		int[] ints = null;
		int[] floats = null;
		String str = format.format(num);
		int idx = str.indexOf(".");

		if (count <= 0) {
			count = str.length();
		} else if (idx != -1) {
			count++;
		}

		if (str.length() > count) {
			str = str.substring(0, count);
		} else {
			while (str.length() < count) {
				str = "0" + str;
			}
		}

		String intStr = null;
		String floatStr = null;
		if (idx != -1) {
			intStr = str.substring(0, idx);
			floatStr = str.substring(idx + 1);
		} else {
			intStr = str;
		}
		if (intStr != null) {
			ints = new int[intStr.length()];
			for (int i = 0; i < intStr.length(); i++) {
				ints[i] = Integer.parseInt(String.valueOf(intStr.charAt(i)));
			}
		}
		if (floatStr != null) {
			floats = new int[floatStr.length()];
			for (int i = 0; i < floatStr.length(); i++) {
				floats[i] = Integer.parseInt(String.valueOf(floatStr.charAt(i)));
			}
		}
		return new int[][] { ints, floats };
	}

	public static String getChineseNumber(double num) {
		int[][] nums = splitNumbers(num, -1);
		String str = "";
		//处理整数部分
		int[] ints = nums[0];
		for (int i = 0; i < ints.length; i++) {
			if (ints[i] == 0){
                            if((ints.length - (i+1)==4)||(ints.length - (i+1)==8)){
                                str += ""+ getChineseMedianu(ints.length - (i+1));
                            }
                            continue;
                        }
			str += getSingleChineseNumber(ints[i]) + getChineseMedianu(ints.length - (i+1));
		}
		//如果有小数部分
		int[] floats = nums[1];
		if(floats != null && floats.length > 0){
			str += "点";
			for(int i=0;i<floats.length;i++){
				str += getSingleChineseNumber(floats[i]);
			}
		}

		return str;
	}


	public static void main(String args[])
	{

		double num=12034567.89;

		String s=getUnderChineseNumber(num);

		System.out.println(s);

	}

        public static String getUnderChineseNumber(double num) {
                int[][] nums = splitNumbers(num, -1);
                String str = "";
                //处理整数部分
                int[] ints = nums[0];
                for(int i=0;i<(6-ints.length);i++){

                 str += "<u>&nbsp;&nbsp;</u>"+ getChineseMedianu(6 - (i+1));
                }

                int f=0;
                f=f+1;
                for (int i = 0; i < ints.length; i++) {
                	/*
                        if (ints[i] == 0){
                                if((ints.length - (i+1)==4)||(ints.length - (i+1)==8)){
                                    str += ""+ getChineseMedianu(ints.length - (i+1));
                                }else if((ints.length - (i+1)==0)){
                                    if(ints.length<2)
                                    str += "<u>&nbsp;&nbsp;</u>";
                                }
                                continue;
                       }
                        */
                       //if(ints.length<6&&i==0)str+="<u>&nbsp;&nbsp;</u>拾";
                       str += "<u>&nbsp;"+getSingleChineseNumber(ints[i]) +"&nbsp;</u>"+ getChineseMedianu(ints.length - (i+1));
                }
                //如果有小数部分
                int[] floats = nums[1];
                if(floats != null && floats.length > 0){
                        str += "点";
                        for(int i=0;i<floats.length;i++){
                                str += getSingleChineseNumber(floats[i]);
                        }
                }

                return str;
        }


	public static String getSingleChineseNumber(int num) {
		switch (num) {
			case 0:
				return "零";
			case 1:
				return "壹";
			case 2:
				return "贰";
			case 3:
				return "叁";
			case 4:
				return "肆";
			case 5:
				return "伍";
			case 6:
				return "陆";
			case 7:
				return "柒";
			case 8:
				return "捌";
			case 9:
				return "玖";
			default:
				return "";
		}
	}


	public static String getChineseMedianu(int median) {
		switch (median) {
			case 0:
				return "";
			case 1:
				return "拾";
			case 2:
				return "佰";
			case 3:
				return "仟";
			case 4:
				return "万";
			case 5:
				return "拾";
			case 6:
				return "佰";
			case 7:
				return "仟";
			case 8:
				return "亿";
			case 9:
				return "拾";
			case 10:
				return "佰";
			case 11:
				return "仟";
			case 12:
				return "兆";
			case 13:
				return "拾";
			case 14:
				return "佰";
			case 15:
				return "仟";
			default:
				return "";
		}
	}

	public static long combineInts(int[] ints){
		String tmp = "";
		for(int i=0;i<ints.length;i++){
			tmp += ints[i];
		}

		return Long.parseLong(tmp);
	}
        public static String formatCurrency(double cents) {
            return cash.format((double)cents);//cents/100
        }
        public static String formatCurrency(int cents) {
            return cash.format((double)cents);
        }



}
