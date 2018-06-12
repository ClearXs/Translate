package Servce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.sql.PreparedStatement;

public class DataBase {
	public static String englishName;
	
	public static String chineseName;
		
	private static HashMap<String,String> hm = new HashMap<String,String>();
		
	private static Connection conn = null;
	
	private static String[] chinese = null;
	
	public static void main(String [] a) throws SQLException {
		DataBase db = new DataBase();
		Set<Map.Entry<String, String>> set = hm.entrySet();
		for(Map.Entry<String, String> me : set) {
			System.out.println(me.getKey()+me.getValue());
		}
	}
	
	
	DataBase(){
		initalizeHashMap();
	}
	/*
	 * 初始化工作，它将做3件事
	 * 1.连接数据库
	 * 2.把数据库表"entoch" 从"english"表压入哈希表中 
	 * 3.最后，优化哈希表中的数据
	 */
	public void initalizeHashMap() {
		connectionDataBase();
		//Statement 为数据相关的对象
		Statement myStmt = null;
		Statement myStmt2 = null;
		//数据库返回的结果集
		ResultSet myRst = null;
		ResultSet myRst2 = null;
		try {
			
			myStmt = conn.createStatement();
			myStmt2 = conn.createStatement();
			//
			myRst = myStmt.executeQuery("select * from entoch;");
			myRst.last();
			String[] english = new String[myRst.getRow()+1];
			myRst.first();
			//
			myRst2 = myStmt2.executeQuery("select * from english;");
			myRst2.last();
			StringBuffer[] chinese = new StringBuffer[myRst2.getRow()];
			String[] englishname = new String[myRst2.getRow()];
			myRst2.first();
			for(int i = 0;;i++) {
				englishname[i] = myRst2.getString("englishname");
				if( !myRst2.next()) {
					break;
				}
			}
			
			//这个算法是怎么读取中英文表的数据
			for(int i = 0,j = 0;;i++) {
				english[i] = myRst.getString("englishname");
				if(i!=0) {
					if(!english[i].equals(english[i-1])) {
						if(chinese[j]!=null) {
							hm.put(englishName, chineseName);
							
							chinese[++j] = new StringBuffer();	
						}
						else chinese[j] = new StringBuffer();
					}
				}
				else chinese[j] = new StringBuffer();
				chinese[j].append(myRst.getString("chinesename"));
				chinese[j].append(",");
				englishName = english[i];
				chineseName = chinese[j].toString();
				if(!myRst.next()) {
					break;
				}
			}
			hm.put(englishName, chineseName);
			dispose(englishname);
			//可以写一下
			myStmt.close();
			myStmt2.close();
			myRst.close();
			myRst2.close();
		}catch(SQLException e) {
			System.out.println("数据读写失败");
			e.printStackTrace();
		}
	}
	/*
	 * 连接数据库，在初始化中连接
	 */
	public static void connectionDataBase() {
		//载入数据库驱动程序到JDBC中
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e) {
			System.out.println("加载数据库驱动程序失败");
			e.printStackTrace();
		}
		//连接到指定的数据库中
		try {
			String url = "jdbc:mysql://localhost/englishtochinese?useUnicode=true&characterEncoding=utf8";
			String user = "root";
			String passwd = "993155asd";
			conn = DriverManager.getConnection(url, user, passwd);
		}catch(SQLException e) {
			System.out.println("连接数据库失败");
			e.printStackTrace();
		}
	}
	/*
	 * parseChineseData()方法用于解析用户增加的中文意思，比如有 "搜素，搜寻"
	 * 把其解析成"搜素"，"搜寻"
	 */
	public static void parseChineseData(String chineseName) {
		chinese = new String[chineseName.length()];
		int endIndex;
		for(int i = 0;;i++) {
			endIndex = chineseName.indexOf(",");
			if(endIndex>0) {
				chinese[i] = chineseName.substring(0, endIndex);
			}
			//当找到最好的翻译时，结束整个循环
			else {
				chinese[i] = chineseName;
				break;
			}
			chineseName = chineseName.substring(endIndex+1);
		}
	}
	
	public static String getHowRead(String englishName) {
		Statement sta = null;
		ResultSet res = null;
		try {
			sta = conn.createStatement();
			
			res = sta.executeQuery("select english.englishname,english.howread from english\n"
					+"where english.englishname IN('"+englishName+"');");
			res.next();
			return res.getString("howread");
		}catch(Exception e) {
			System.out.println("查询失败");
			e.printStackTrace();
			return null;
		}
	}
	//设置"chinese"表的count排序
	public static void setChineseCount() {
		try{
			Statement chstmt = conn.createStatement();
			ResultSet chRst = chstmt.executeQuery("select * from chinese;");
			
			for(int i = 1;;i++) {
				if(!chRst.next()) {
					break;
				}
				String chinese = chRst.getString("chinesename");
				PreparedStatement prs =  conn.prepareStatement(
						"UPDATE CHINESE SET COUNT = ?\n WHERE CHINESE.CHINESENAME = ?;");
				prs.setInt(1, i);
				prs.setString(2, chinese);
				prs.executeUpdate();
				prs.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//设置"english"表的count排序
	public static void setEnglishCount() {
		try{
			Statement enstmt = conn.createStatement();
			ResultSet enRst = enstmt.executeQuery("select * from english;");
			
			for(int i = 1;;i++) {
				if(!enRst.next()) {
					break;
				}
				String english = enRst.getString("englishname");
				PreparedStatement prs =  conn.prepareStatement(
						"UPDATE ENGLISH SET COUNT = ?\n WHERE ENGLISH.ENGLISHNAME = ?;");
				prs.setInt(1, i);
				prs.setString(2, english);
				prs.executeUpdate();
				prs.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 向数据库中添加数据,其中中文是以"搜索，搜寻"这样的参数传递进去
	 * 依次添加"english"表，"chinese"表，"entoch"表
	 */
	public static void addDataBase(String englishName,String howRead,String chineseName) throws SQLException {
		parseChineseData(chineseName);
		try {
			Statement enstmt = conn.createStatement();
			ResultSet enRst = enstmt.executeQuery("select * from english");
			int enCount = 0;
			while(enRst.next()) {
				if(enCount < enRst.getInt("count")) {
					enCount = enRst.getInt("count");
				}
			}
			//
			Statement chstmt = conn.createStatement();
			ResultSet chRst = chstmt.executeQuery("select * from chinese");
			int chCount = 0;
			while(chRst.next()) {
				if(chCount < chRst.getInt("count")) {
					chCount = chRst.getInt("count"); 
				}
			}
			Statement connStmt = conn.createStatement();
			//添加到英文表格中
			connStmt.addBatch("INSERT INTO ENGLISH (COUNT,ENGLISHNAME,HOWREAD)\n"
					+"VALUES\n"
					+"('"+(++enCount)+"','"+englishName.toUpperCase()+"','"+howRead+"');");
			//添加到中文表格中
			for(int i = 0;;i++) {
				if(chinese[i]!=null) {
					connStmt.addBatch("INSERT INTO CHINESE(COUNT,CHINESENAME)\n"
					+ "VALUES\n"
					+"('"+(++chCount)+"','"+chinese[i]+"');");
				}
				else break;
			}
			//添加到entoch表格中
			for(int i = 0;;i++) {
				if(chinese[i]!=null) {
					connStmt.addBatch("INSERT INTO ENTOCH(ENGLISHNAME,CHINESENAME)\n"
							+"VALUES\n"
							+"('"+englishName.toUpperCase()+"','"+chinese[i]+"');");
				}
				else break;
			}
			//当每次进行添加操作时，都重新设置好count的排序
			setChineseCount();
			setEnglishCount();
			updataHashMap();
			boolean savaedCommitValue = conn.getAutoCommit();
			conn.setAutoCommit(false);
			connStmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(savaedCommitValue);
			updataHashMap();
			enstmt.close();
			enRst.close();
			chstmt.close();
			chRst.close();
			connStmt.close();
		}catch(SQLException e) {
			System.out.println("添加数据出错");
			//当添加错误是，数据库回滚操作
			conn.rollback();
			e.printStackTrace();
		}
		
	}
	/*
	 * 返回当前的哈希表
	 */
	public static HashMap returnHashMap() {
		return hm;
	}
	public static void updataHashMap() {
		hm = new HashMap();
		Statement myStmt = null;
		Statement myStmt2 = null;
		//数据库返回的结果集
		ResultSet myRst = null;
		ResultSet myRst2 = null;
		try {
			
			myStmt = conn.createStatement();
			myStmt2 = conn.createStatement();
			//
			myRst = myStmt.executeQuery("select * from entoch;");
			myRst.last();
			String[] english = new String[myRst.getRow()+1];
			myRst.first();
			//
			myRst2 = myStmt2.executeQuery("select * from english;");
			myRst2.last();
			StringBuffer[] chinese = new StringBuffer[myRst2.getRow()];
			String[] englishname = new String[myRst2.getRow()];
			myRst2.first();
			for(int i = 0;;i++) {
				englishname[i] = myRst2.getString("englishname");
				if( !myRst2.next()) {
					break;
				}
			}
			
			//这个算法是怎么读取中英文表的数据
			for(int i = 0,j = 0;;i++) {
				english[i] = myRst.getString("englishname");
				if(i!=0) {
					if(!english[i].equals(english[i-1])) {
						if(chinese[j]!=null) {
							hm.put(englishName, chineseName);
							
							chinese[++j] = new StringBuffer();	
						}
						else chinese[j] = new StringBuffer();
					}
				}
				else chinese[j] = new StringBuffer();
				chinese[j].append(myRst.getString("chinesename"));
				chinese[j].append(",");
				englishName = english[i];
				chineseName = chinese[j].toString();
				if(!myRst.next()) {
					break;
				}
			}
			hm.put(englishName, chineseName);
			dispose(englishname);
		}catch(SQLException e) {
			System.out.println("添加到哈希表出错");
			e.printStackTrace();
		}
	}
	/*
	 * 当客户点击删除后，将会把相关记录的所有信息全部删除 比如说 "append_ 搜索，搜寻" 这个单词, 
	 * 将会在"english" 表删除 "chinese"表删除 "entoch"表删除
	 */
	public static void deleteRelational(String englishname,String chineseName) {
		//第一步 解析中文的意思 把他放在静态变量chinese中
		parseChineseData(chineseName);
		//第二步 删除entoch表中的相关信息 根据关键词即可
		deleteENTOCHData(englishname);
		//第三步 删除english表中的数据
		deleteENGLISHData(englishname);
		//第四步，删除chinese表中的数据
		
		for(int i = 0;;i++) {
			if(chinese[i]!=null) {
				deleteCHINESEData(chinese[i]);
			}
			else break;
		}
		//当每次进行删除操作时，都重新设置好count的排序
		setChineseCount();
		setEnglishCount();
		updataHashMap();
	}
	/*
	 * 删除数据库中entoch表中的数据
	 * 根据英文名字，删除整个表关于该英文的所有意思
	 */
	private static void deleteENTOCHData(String englishname) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"DELETE FROM ENTOCH WHERE ENGLISHNAME = ?;");
			prs.setString(1, englishname);
			prs.executeUpdate();
			updataHashMap();
			prs.close();
		}catch(SQLException e ) {	
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	private static void deleteCHINESEData(String chinesename) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"DELETE FROM CHINESE WHERE CHINESE.CHINESENAME = ?;");
			prs.setString(1, chinesename);
			prs.executeUpdate();
			prs.close();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	private static void deleteENGLISHData(String englishname) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"DELETE FROM ENGLISH WHERE ENGLISH.ENGLISHNAME = ?;");
			prs.setString(1, englishname);
			prs.executeUpdate();
			prs.close();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}

	/*
	 * 修改所以表格关于"english"的数据
	 * 根据原来的值 查询到相应的列
	 * 在根据改变的后的值填充到该列中
	 */
	public static void updataEnglishData(String nowEnglish,String previousEnglish) {
		updataENGLISHData(nowEnglish,previousEnglish);
		updataENTOCHData(nowEnglish,previousEnglish);
		updataHashMap();
	}
	/*
	 * 修改所有表格关于"howread"的数据
	 *
	 */
	public static void updataHowRead(String nowHowRead,String previousHowRead) {
		updataEnglishHowRead(nowHowRead,previousHowRead);
		updataHashMap();
	}
	/*
	 * 修改所有表格中关于"chinese"的值
	 */
	public static void updataChinese(String nowChinese,String previousChinese) {
		updataENTOCHchinese(nowChinese,previousChinese);
		updataCHINESEData(nowChinese,previousChinese);
		updataHashMap();
	}
	//根据计数值 更新中文内容
	private static void updataCHINESEData(String nowChinese,String previousChinses) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"UPDATE CHINESE SET CHINESENAME = ? WHERE CHINESE.CHINESENAME = ?;");
			prs.setString(1, nowChinese);
			prs.setString(2, previousChinses);
			prs.executeUpdate();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	//修改"entoch"表中english中的值
	private static void updataENTOCHData(String nowEnglish,String previousEnglish) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"UPDATE ENTOCH SET ENGLISHNAME = ? WHERE ENTOCH.ENGLISHNAME = ?;");
			prs.setString(1, nowEnglish);
			prs.setString(2, previousEnglish);
			prs.executeUpdate();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	//修改"english"表中的english中的值
	private static void updataENGLISHData(String nowEnglish,String previousEnglish) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"UPDATE ENGLISH SET ENGLISHNAME = ? WHERE ENGLISH.ENGLISHNAME = ?;");
			prs.setString(1, nowEnglish);
			prs.setString(2, previousEnglish);
			prs.executeUpdate();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	private static void updataEnglishHowRead(String nowHowRead,String previousHowRead) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"UPDATE ENGLISH SET HOWREAD = ? WHERE ENGLISH.HOWREAD = ?;");
			prs.setString(1, nowHowRead);
			prs.setString(2, previousHowRead);
			prs.executeUpdate();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	
	private static void updataENTOCHchinese(String nowChinese,String previousChinses) {
		try {
			PreparedStatement prs =  conn.prepareStatement(
					"UPDATE ENTOCH SET CHINESENAME = ? WHERE ENTOCH.CHINESENAME = ?;");
			prs.setString(1, nowChinese);
			prs.setString(2, previousChinses);
			prs.executeUpdate();
		}catch(SQLException e) {
			System.out.println("删除数据库出错误");
			e.printStackTrace();
		}
	}
	/*
	 * 使用这个方法，可以对数据进行优化工作
	 * 在初始化进行
	 */
	public static void dispose(String [] englishname) {
		String [] chinesename = new String[englishname.length];
		//获取映射关系对中的中文
		for(int i = 0 ; i < englishname.length;i++) {
			chinesename[i] = new String();
			chinesename[i] = hm.get(englishname[i]);
			hm.remove(englishname[i]);
		}
		//对中文进行优化
		for(int i = 0;i<chinesename.length;i++) {
			if(chinesename[i]!=null) {
				chinesename[i] = chinesename[i].substring(0, chinesename[i].length()-1);
			}
			else break;
		}
		//在次进行映射,能保证相关的是下标号
		for(int i = 0;i < englishname.length;i++) {
			hm.put(englishname[i], chinesename[i]);
		}
	}
}
