package com.wj.generator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class DbUtil {
    public static Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3308/cloud-eureka-db?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
            String user = "mysqlpwd";
            String pwd = "mysqlpwd";
            conn = DriverManager.getConnection(url,user,pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }


    /***
     * 获取表注释
     * @param tableName
     * @return
     * @throws Exception
     */
    public static String getTableComment(String tableName) throws Exception{
          Connection connection = getConnection();
          Statement stme = connection.createStatement();
          String sql = "select table_comment from information_schema.tables " +
                  " where table_name = '" + tableName + "'";
          ResultSet rs = stme.executeQuery(sql);
          String tableNameCh = "";
          if(rs != null){
              while(rs.next()){
                  tableNameCh = rs.getString("table_comment");
                  break;
              }
          }
          rs.close();
          stme.close();
          connection.close();
          System.out.println("表名:" + tableNameCh);
          return tableNameCh;
    }

    /****
     * 获得所有列信息
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<Field> getColumnByTableName(String tableName) throws Exception{
        List<Field> fieldList = new ArrayList<>();
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
         String sql = "show full columns from `" + tableName +"`";
        ResultSet rs = stmt.executeQuery(sql);
        if(rs != null){
            while (rs.next()) {
                String columnName = rs.getString("Field");
                String type = rs.getString("Type");
                String Comment = rs.getString("Comment");
                String nullAble = rs.getString("Null");//YES NO
                Field field = new Field();
                field.setName(columnName);
                field.setType(type);
                field.setComment(Comment);
                field.setNameHump(lineToHump(columnName));
                field.setNameBigHump(lineToBigHump(columnName));
                field.setJavaType(DbUtil.sqlTypeToJavaType(type));
                field.setNameCn(Comment);
                if(Comment.contains("|")){
                    field.setNameCn(Comment.substring(0,Comment.indexOf("|")));
                }
                field.setNullAble("YES".equals(nullAble));
                if(type.toUpperCase().contains("varchar".toUpperCase())){
                    String lengthStr = type.substring(type.indexOf("(") + 1,type.length() - 1);
                    field.setLength(Integer.valueOf(lengthStr));
                }else{
                    field.setLength(0);
                }
                fieldList.add(field);
            }
        }
        rs.close();
        stmt.close();
        connection.close();
        System.out.println("列:" + fieldList);
        return fieldList;

    }


    /***
     * 下划线转小驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str){
         Pattern linePattern = Pattern.compile("_(\\w)");
         str = str.toLowerCase();
         Matcher matcher = linePattern.matcher(str);
         StringBuilder sb = new StringBuilder();
         while(matcher.find()){
             matcher.appendReplacement(sb,matcher.group(1).toUpperCase());
         }
         matcher.appendTail(sb);
         return sb.toString();
    }

    /***
     * 下划线转大驼峰
     * @param str
     * @return
     */
     public static String lineToBigHump(String str){
        String s = lineToHump(str);
        return s.substring(0,1).toUpperCase() + s.substring(1);
     }

    /****
     * 数据库类型转为java类型
     * @param sqlType
     * @return
     */
     public static String sqlTypeToJavaType(String sqlType){
          if(sqlType.toUpperCase().contains("varchar".toUpperCase())
          || sqlType.toUpperCase().contains("char".toUpperCase())
          || sqlType.toUpperCase().contains("text".toUpperCase())){
              return "String";
          }else if(sqlType.toUpperCase().contains("datetime".toUpperCase())){
              return "Date";
          }else if(sqlType.toUpperCase().contains("int".toUpperCase())){
              return "Integer";
          }else if(sqlType.toUpperCase().contains("long".toUpperCase())){
              return "Long";
          }else if(sqlType.toUpperCase().contains("decimal".toUpperCase())){
              return "BigDecimal";
          }else {
              return "String";
          }
     }
}
