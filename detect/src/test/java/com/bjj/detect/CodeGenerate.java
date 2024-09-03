package com.bjj.detect;

import com.bjj.detect.entity.*;
import com.syzx.framework.generator.CodeGeneratorManager;
import com.syzx.framework.generator.packager.CodePackager;

public class CodeGenerate {

	public static void main(String... args){
//        new CodeGeneratorManager().generateDefaultEntities();
//		new CodeGeneratorManager().generateCode(
//				PgRecord.class
//				PgInfo.class,
//				PgNotice.class,
//				PgCertificate.class
//				StandardTool.class
//				StandardMeter.class
//				DeleteRec.class
//				Role.class,
//				MyAuth.class
//				RoleAuthority.class
//		);
//        new CodeGeneratorManager().createVueProject();
        CodePackager.packageToWar();

	}


}
