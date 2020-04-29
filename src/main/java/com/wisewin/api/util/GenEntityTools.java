package com.wisewin.api.util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class GenEntityTools {
	//数据库表名


	private String tablename = "blue-whale_human_mail_info";
    //生成model类别名

	private String tmpname = "HumanMailInfo";

	
    private String[] colnames; // 列名数组

    private String[] colTypes; // 列名类型数组

    private int[] colSizes; // 列名大小数组

    private boolean f_util = false; // 是否需要导入包java.util.*

    private boolean f_sql = false; // 是否需要导入包java.sql.*
    
    public GenEntityTools() throws ClassNotFoundException, SQLException {
        Connection conn = this.getConnection(); // 得到数据库连接
        String strsql = "select * from " + tablename;
        try {
            PreparedStatement pstmt = conn.prepareStatement(strsql);
            ResultSetMetaData rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 共有多少列
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("date")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("image")
                        || colTypes[i].equalsIgnoreCase("text")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            
            String content = parse(colnames, colTypes, colSizes);
            String mapper  = mapper(tmpname,colnames,colTypes) ;
            String mapperXML  = mapperXML(tmpname,colnames,colTypes) ;
            
            //如果要生成文件 放开此代码
            try {
                String modelName = "src/com/wyvs/blue-whale/model/"+initcap(tmpname) + ".java" ;
                File file = new File(modelName) ;
                if(!file.exists()){
                    FileWriter fw1 = new FileWriter(modelName);
                    PrintWriter pw1 = new PrintWriter(fw1);
                    pw1.println(content.toString());
                    pw1.flush();
                    pw1.close();
                    System.out.println("实体类已经生成");
                }else{
                	System.out.println("实体类已经存在");
                }          
                
                String filename = "src/com/wyvs/blue-whale/persistence/"+initcap(tmpname)+"Mapper.java" ;
                File file1 = new File(filename) ;
                if(!file1.exists()){
                    FileWriter fw1 = new FileWriter(filename);
                    PrintWriter pw1 = new PrintWriter(fw1);
                    pw1.println(mapper.toString());
                    pw1.flush();
                    pw1.close();
                    System.out.println("接口类已经生成");
                }else{
                	System.out.println("文件已经存在不能生成接口java类");
                }
                
                String xmlName = "src/com/wyvs/blue-whale/persistence/"+initcap(tmpname)+"Mapper.xml" ;
                File file2 = new File(xmlName) ;
                if(!file2.exists()){
                    FileWriter fw1 = new FileWriter(xmlName);
                    PrintWriter pw1 = new PrintWriter(fw1);
                    pw1.println(mapperXML);
                    pw1.flush();
                    pw1.close();
                    System.out.println("xml类已经生成");
                }else{
                	System.out.println("文件已经存在不能生成接口xml类");
                }
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //DBSession.closeConnection(conn);
        	conn.close() ;
        }
    }

	private String mapperXML(String tablename, String[] colnames2, String[] colTypes2) {
		String btablename = initcap(tablename) ;
		String insertSql = this.outInsert(tablename, colnames2) ;
        String updateSql = this.outUpdate(tablename,colnames,colTypes) ;
        String selectSql = this.select(tablename,colnames,colTypes) ;
        
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \r\n") ;
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n") ;
        sb.append("<mapper namespace=\"com.wyvs.blue-whale.persistence."+btablename+"Mapper\">\r\n") ;
        sb.append("			<insert id=\"insert\" parameterType=\"com.wyvs.blue-whale.model."+btablename+"\" useGeneratedKeys=\"true\" keyProperty=\"id\" >\r\n") ;
        sb.append("            "+insertSql+"\r\n") ;
        sb.append("         </insert>\r\n") ;
        sb.append("         <update id=\"update\" parameterType=\"com.wyvs.blue-whale.model."+btablename+"\">\r\n") ;
        sb.append("            "+updateSql+"\r\n") ;
        sb.append("         </update>\r\n") ;
        sb.append("         <select id=\"select\" parameterType=\"map\" resultType=\"com.wyvs.blue-whale.model."+btablename+"\">\r\n") ;
        sb.append("            "+selectSql+"\r\n") ;
        sb.append("         </select>\r\n") ;
        
        sb.append("</mapper>") ; 
       	
		return sb.toString();
	}

	private String select(String tablename2, String[] colnames2,
                          String[] colTypes2) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from ") ;
        sb.append(tablename2 +" tmp ") ;
        sb.append(" where 1=1 ") ;
        for (int i = 0; i < colnames2.length; i++) {
			sb.append(" <if test=\"tmp."+colnames2[i]+" != null\"> and tmp."+colnames2[i]+"=#{tmp."+colnames2[i]+",jdbcType="+sqlType3JavaType(colTypes2[i])+"} </if> ") ;
		}
        sb.append(" <include refid=\"limit\"/> ") ;
        
		return sb.toString();
	}

	private String mapper(String tablename2, String[] colnames2, String[] colTypes2) {
		String btablename2 = initcap(tablename2) ;
        StringBuffer sb = new StringBuffer();
        sb.append("package com.wyvs.blue-whale.persistence; \r\n") ;
        sb.append("import java.util.List;\r\n") ;
        sb.append("import java.util.Map;\r\n") ;
        sb.append("import java.util.ArrayList;\r\n") ;
        sb.append("import java.util.HashMap;\r\n") ;
        
        sb.append("import com.wyvs.blue-whale.model."+btablename2+";\r\n") ;
        
        sb.append("public interface "+btablename2+"Mapper{\r\n") ;
        sb.append("       int insert("+btablename2+" "+tablename2+");\r\n") ;
        sb.append(" 	  int update("+btablename2+" "+tablename2+");\r\n") ;
        sb.append(" 	  ArrayList<"+tablename2+"> select(HashMap<String,Object> map);\r\n") ;
        sb.append("}") ;
        
		return sb.toString();
	}

	private String outUpdate(String tablename, String[] colnames,
                             String[] colTypes) {
        //生成update 语句
        StringBuffer sb = new StringBuffer();
        
        sb.append(" update  "+tablename+" set ") ;
        for (int i = 0; i < colnames.length; i++) {
        	if(!colnames[i].equals("id")){
	            sb.append("              <if test=\""+colnames[i]+" != null\"> "+colnames[i]+"=#{"+colnames[i]+",jdbcType="+sqlType3JavaType(colTypes[i])+"} , </if>\r\n") ;
	            
        	}
		}
        sb.append("              id = id  where id = #{id,jdbcType=INTEGER}") ; 
		return sb.toString();
	}

	private String outInsert(String tablename2, String[] colnames2) {
        //生成insert 语句
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into "+tablename+" (") ;
        for (int i = 0; i < colnames.length; i++) {
        	if(!colnames[i].equals("id")){
        		sb.append(colnames[i]) ;
                if(i<colnames.length-1){
                    sb.append(",") ;            	
                }        		
        	}
		}
        sb.append(") values (") ;
        for (int i = 0; i < colnames.length; i++) {
        	if(!colnames[i].equals("id")){
	            sb.append("#{"+colnames[i]+",jdbcType="+sqlType3JavaType(colTypes[i])+"}") ;
	            if(i<colnames.length-1){
	                sb.append(",") ;            	
	            }
        	}
		}
        sb.append(") ") ;
        //生成insert 结束
        return sb.toString() ;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1/wyvs_blue-whale";
		String user = "root";
		String password = "admin";
		Class.forName(driver);
		Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		return conn;
	}
    /**
    * 解析处理(生成实体类主体代码)
    */
    private String parse(String[] colNames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.wyvs.blue-whale.model;\r\n") ;
        
        sb.append("import java.io.Serializable;\r\n") ;
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n\r\n\r\n");
        }
        sb.append("public class " + initcap(tmpname) + "  implements Serializable {\r\n");
        processAllAttrs(sb);
        processAllMethod(sb);
        sb.append("}\r\n");
        System.out.println(sb.toString());
        return sb.toString();

    }

    /**
    * 生成所有的方法
    * 
    * @param sb
    */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(colnames[i]) + "("
                    + sqlType2JavaType(colTypes[i]) + " " + colnames[i]
                    + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");

            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
                    + initcap(colnames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
    * 解析输出属性
    * 
    * @return
    */
    private void processAllAttrs(StringBuffer sb) {
    	sb.append("\tprivate static final long serialVersionUID = 1L;\r\n") ;
    	
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
                    + colnames[i] + ";\r\n");

        }
    }

    /**
    * 把输入字符串的首字母改成大写
    * 
    * @param str
    * @return
    */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "bool";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            //return "int";
        	return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        }else if (sqlType.equalsIgnoreCase("DATE")) {
            return "Date";
        }
        else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        } else if (sqlType.equalsIgnoreCase("decimal")) {
            return "double";
        }else if (sqlType.equalsIgnoreCase("double")) {
            return "double";
        }
        
        
        return null;
    }
    
    private String sqlType3JavaType(String sqlType) {
    	if (sqlType.equalsIgnoreCase("int")) {
            return "INTEGER";
        } else if (sqlType.equalsIgnoreCase("DATETIME")) {
            return "TIMESTAMP";
        } else if (sqlType.equalsIgnoreCase("VARCHAR")) {
            return "VARCHAR";
        } else if (sqlType.equalsIgnoreCase("DATE")) {
            return "DATE";
        } else if (sqlType.equalsIgnoreCase("double")) {
            return "DOUBLE";
        } else if (sqlType.equalsIgnoreCase("decimal")) {
            return "DOUBLE";
        }
    	
        return null;
    }
    
    

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        new GenEntityTools();
//    }
}