package com.wj.generator.server;

import com.wj.generator.util.DbUtil;
import com.wj.generator.util.Field;
import com.wj.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    static String toServicePath = "server/src/main/java/com/wj/server/service/";
    static String MODULE = "system";

    static String toDtoPath = "server/src/main/java/com/wj/server/dto/";
    static String toControllerPath = MODULE +"/src/main/java/com/wj/"+MODULE+"/controller/admin/";

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
        map.put("tableNamCn",tableNamCn);
        map.put("module",module);
        map.put("fieldList",fieldList);
        map.put("typeSet",typeSet);

        //生成dto controller service
        createDto(map,Domain);
        createController(map,Domain);
        createService(map,Domain);
    }
    private static void createDto(Map<String,Object> map,String Domain) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(map,toDtoPath+Domain+"Dto.java");
    }
    private static void createController(Map<String,Object> map,String Domain) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(map,toControllerPath+Domain+"Controller.java");
    }
    private static void createService(Map<String,Object> map,String Domain) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(map,toServicePath+Domain+"Service.java");
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
