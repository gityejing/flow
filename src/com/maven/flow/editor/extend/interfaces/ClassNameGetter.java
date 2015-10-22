package com.maven.flow.editor.extend.interfaces;

import java.util.Set;

import com.inbuild.audit.wordflow.enger.application.impl.*;
import com.maven.flow.util.ClassUtil;

public class ClassNameGetter {

	public static String[] getFlowPathsClass() {
		String s[] = { NeedCarFlowPath.class.getName(),
				NotNeedCarFlowPath.class.getName(),
				OverFiveMillion.class.getName(),
				ReCheckerManagerFlowPath.class.getName(),
				SmallerThanFiveMillion.class.getName(),
				AuditToReAuditFlowPath.class.getName(),
				CheckDepartMeneToReCheckDepartMentFlowPath.class.getName(),
				OverAndSmallerThanFiveMoney.class.getName(),
				OverThanTwoHandMoney.class.getName(),
				SmallerThanFiveMoney.class.getName(),
				BigThanFiveMoney.class.getName(),
				OnlyCenterShenHeFinish.class.getName() };
		return s;
	}

	public static String[] getFinishCondiction() {
		String s[] = { FinishedNoCondiction.class.getName(),
				OnlyPinShenZhongXinFinish.class.getName() };
		return s;
	}

	public static void main(String args[]) {
		Set set = ClassUtil.getClasses(Package
				.getPackage("com.maven.flow.editor.extend.impl"));
		if (set != null) {
			Object[] o = set.toArray();
			for (int i = 0; i < o.length; i++) {
				Class c = (Class) o[i];
				System.out.println(c.getCanonicalName());
			}
		}
	}

	public static String[] getBeforeHandleClass() {
		String s[] = { BeforeNoUse.class.getName(),
				BeforeCheck.class.getName(),
				BeforeFuShenKeFuShen.class.getName(),
				BeforeFuZhuRenShenPiPingShenJieLun.class.getName(),
				BeforeGuiDang.class.getName(),
				BeforeJinDuKuanFuZhuRenShenPi.class.getName(),
				BeforeJinDuKuanGuiDang.class.getName(),
				BeforeShenHeShenPi.class.getName(),
				BeforeSplitPerson.class.getName(),
				BeforeTiJiaoPingShenJieLun.class.getName(),
				BeforeXiuZhengBaoGao.class.getName(),
				BeforeEnd.class.getName(), BeforeNotifySendEnd.class.getName() };
		return s;
	}

	public static String[] getAfterHandleClass() {
		String s[] = { AfterNotUse.class.getName(), AfterCheck.class.getName(),
				AfterCheckManagerSubmit.class.getName(),
				AfterFuLingDaoTiJiaoPingShenJieLun.class.getName(),
				AfterGuiDang.class.getName(),
				AfterJinDuKuanGuiDang.class.getName(),
				AfterNotifySend.class.getName(),
				AfterReReCheckManagerSubmit.class.getName(),
				AfterSetReport.class.getName(),
				AfterTiJianPianShengJieLun.class.getName(),
				AfterZhengLingDaoTiJiaoPingShenJieLun.class.getName(),
				AfterShouLi.class.getName() };
		return s;
	}

}
