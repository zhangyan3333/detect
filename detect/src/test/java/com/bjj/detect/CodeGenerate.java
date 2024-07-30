package com.bjj.detect;

import com.bjj.detect.entity.*;
import com.syzx.framework.generator.CodeGeneratorManager;

public class CodeGenerate {

	public static void main(String... args){
//        new CodeGeneratorManager().generateDefaultEntities();
		new CodeGeneratorManager().generateCode(
//				PgRecord.class
//				PgInfo.class,
//				PgNotice.class,
//				PgCertificate.class,
//				StandardTool.class
//				StandardMeter.class
				DeleteRec.class
		);
//        new CodeGeneratorManager().createVueProject();
//        CodePackager.packageToWar();

	}


}
