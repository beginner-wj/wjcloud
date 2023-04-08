package com.wj.generator.util;

import freemarker.template.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FreemarkerUtil {
    static String ftlPath = "generator/src/main/java/com/wj/generator/ftl";

    static Template template;
    public static void initConfig(String ftlName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        template = cfg.getTemplate(ftlName);

    }

    public static void generator(Map<String,Object> map, String fileName) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        template.process(map,bw);
        bw.flush();
        fw.close();
    }
}
