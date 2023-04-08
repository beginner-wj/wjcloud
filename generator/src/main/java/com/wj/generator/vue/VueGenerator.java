package com.wj.generator.vue;

import com.wj.generator.util.DbUtil;
import com.wj.generator.util.Field;
import com.wj.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VueGenerator {
    static String MODULE = "system";

    static String toVuePath = "admin/src/views/admin/";

    static String generatorConfigPath = "server/src/main/resources/generator/generatorConfig.xml";

    public static void main(String[] args) throws Exception {

        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        //读取xml文件到docment中
        Document doc= reader.read(file);
        //获取xml根节点
        Element rootELement = doc.getRootElement();
        //读取context节点
        Element contextElement = rootELement.element("context");
        //定义一个element用于遍历
        Element tableElement;
        //取第一个table节点
        tableElement = contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String  tableNamCn = DbUtil.getTableComment(tableName);

        String domain = tableName.substring(0,1).toLowerCase() + tableName.substring(1);

        /*String Domain = "Section";
        String domain = "section";
        String tableNamCn = "小节";*/
        String module = MODULE;

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);

        Map<String,Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("tableNameCn",tableNamCn);
        map.put("module",module);
        map.put("fieldList",fieldList);
        map.put("typeSet",typeSet);

        createVue(map,domain);
    }
    private static void createVue(Map<String,Object> map,String Domain) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("vue.ftl");
        FreemarkerUtil.generator(map,toVuePath+Domain+".vue");
    }
    private static Set<String> getJavaTypes(List<Field> fieldList){
        Set<String> set = new HashSet<>();
        for(int i=0;i< fieldList.size();i++){
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
