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
	 * �ָ�����
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
		//������������
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
		//�����С������
		int[] floats = nums[1];
		if(floats != null && floats.length > 0){
			str += "��";
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
                //������������
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
                       //if(ints.length<6&&i==0)str+="<u>&nbsp;&nbsp;</u>ʰ";
                       str += "<u>&nbsp;"+getSingleChineseNumber(ints[i]) +"&nbsp;</u>"+ getChineseMedianu(ints.length - (i+1));
                }
                //�����С������
                int[] floats = nums[1];
                if(floats != null && floats.length > 0){
                        str += "��";
                        for(int i=0;i<floats.length;i++){
                                str += getSingleChineseNumber(floats[i]);
                        }
                }

                return str;
        }


	public static String getSingleChineseNumber(int num) {
		switch (num) {
			case 0:
				return "��";
			case 1:
				return "Ҽ";
			case 2:
				return "��";
			case 3:
				return "��";
			case 4:
				return "��";
			case 5:
				return "��";
			case 6:
				return "½";
			case 7:
				return "��";
			case 8:
				return "��";
			case 9:
				return "��";
			default:
				return "";
		}
	}


	public static String getChineseMedianu(int median) {
		switch (median) {
			case 0:
				return "";
			case 1:
				return "ʰ";
			case 2:
				return "��";
			case 3:
				return "Ǫ";
			case 4:
				return "��";
			case 5:
				return "ʰ";
			case 6:
				return "��";
			case 7:
				return "Ǫ";
			case 8:
				return "��";
			case 9:
				return "ʰ";
			case 10:
				return "��";
			case 11:
				return "Ǫ";
			case 12:
				return "��";
			case 13:
				return "ʰ";
			case 14:
				return "��";
			case 15:
				return "Ǫ";
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
