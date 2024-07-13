package com.bjj.detect;

import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgNotice;
import com.bjj.detect.entity.PgRecord;
import com.syzx.framework.generator.CodeGeneratorManager;

public class CodeGenerate {

	public static void main(String... args){
//        new CodeGeneratorManager().generateDefaultEntities();
		new CodeGeneratorManager().generateCode(
				PgRecord.class,
				PgInfo.class,
				PgNotice.class,
				PgCertificate.class
		);
//        new CodeGeneratorManager().createVueProject();
//        CodePackager.packageToWar();

	}


}
