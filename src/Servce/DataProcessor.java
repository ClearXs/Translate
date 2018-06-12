package Servce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import Org.Transfer;

public class DataProcessor  implements Runnable{
	
	
	private BufferedReader input = null;
	
	private BufferedWriter output = null;
	
	private Socket socket = null;
	
	private HashMap dataHashMap = null;
	
	private Vector dataVector = new Vector();
	
	private static String chineseName = null;
	
	private static String howRead = null;
	
	private static boolean stopped = false;

	private static Set<Map.Entry<String,String>> dataSet = null;
	
	public void initalize(Socket socket , HashMap dataHashMap) throws IOException {
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()),20000);
		output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),20000);
		setHashMap(dataHashMap);
	}
	
	public void closed() throws IOException {
		output.write("yes");
		output.write("eof");
		output.flush();
		input.close();
		output.close();
	}
	
	public synchronized void query() throws IOException {
		//获取数据库哈希表的集合实例
		dataSet = dataHashMap.entrySet();
		//计数值
		int countString = 0;
		for(Map.Entry<String, String> dataMap : dataSet) {
			System.out.println(dataMap.getKey()+dataMap.getValue());
			String english = dataMap.getKey();
			english = english.toLowerCase();
			String howRead = DataConnector.dataBase.getHowRead(english);
			String chinese = dataMap.getValue();
			output.write(countString+"");
			output.write(";");
			output.write(english);
			output.write(";");
			output.write(howRead);
			output.write(";");
			output.write(chinese);
			output.write("eof");
			output.flush();
			countString++;
			int count = 0;
			char [] buffer = new char[1024];
			String response = null;
			while((count=input.read(buffer))>0) {
				response = new String(buffer);
				if(response.indexOf("eof")!=-1) {
					response = response.substring(0,response.indexOf("eof"));
					break;
				}
			}
		}
		output.write("finish");
		output.write("eof");
		output.flush();
	}
	
	public synchronized  void request(String request) throws IOException, InterruptedException {
		paraRequest(request);
		if(chineseName == null||howRead == null) {
			output.write("没有查询到音标");
			output.write(",");
			output.write("没有查询到翻译");
			output.write("eof");
			output.flush();
		}
		else {
			output.write(howRead);
			output.write(",");
			output.write(chineseName);
			output.write("eof");
			output.flush();
		}
	}
	//更新数据后 把hashmap的数据也更新
	public synchronized void flushHashMap() {
		this.dataHashMap = DataConnector.dataBase.returnHashMap();
	}
	
	//完成更新操作后，同步数据库的哈希表数据
	public synchronized void updata(){
		try{
			int count = 0;
			char [] buffer = new char[1024];
			String response = null;
			System.out.println("卡了吗");
			while((count=input.read(buffer))>0) {
				response = new String(buffer);
				if(response.indexOf("eof")!=-1) {
					response = response.substring(0,response.indexOf("eof"));
					break;
				}
			}
			System.out.println("没卡");
			System.out.println(response + "=?");
			String parseRequest = response.substring(0,response.indexOf(";"));
			response = response.substring(response.indexOf(";")+1);
			switch(parseRequest) {
			case "count": break;
			case "english": {
				//查找未更改的值
				String previousEnglish = response.substring(0,response.indexOf(";"));
				response = response.substring(response.indexOf(";")+1);
				String english = response;
				DataConnector.dataBase.updataEnglishData(english.toUpperCase(), previousEnglish);
				output.write("sucess");
				output.write("eof");
				output.flush();

			};break;
			case "chinese": {
				String previousChinese = response.substring(0,response.indexOf(";"));
				response = response.substring(response.indexOf(";")+1);
				String chinese = response;
				boolean notUpdate = false;
				while(!notUpdate) {
					try {
						String previous = previousChinese.substring(0, previousChinese.indexOf(","));
						previousChinese = previousChinese.substring(previousChinese.indexOf(",")+1);
						String now = chinese.substring(0,chinese.indexOf(","));
						chinese = chinese.substring(chinese.indexOf(",")+1);
						System.out.println(now+previous);
						DataConnector.dataBase.updataChinese(now, previous);
					}catch(IndexOutOfBoundsException e) {
						notUpdate = true;
					}finally {
						String previous = previousChinese;
						String now = chinese;
						System.out.println(now+previous);
						DataConnector.dataBase.updataChinese(now, previous);
						output.write("sucess");
						output.write("eof");
						output.flush();
					}
				}
			};break;
			case "howRead": {
				String previousHowRead = response.substring(0,response.indexOf(";"));
				response = response.substring(response.indexOf(";")+1);
				String howRead = response;
				DataConnector.dataBase.updataHowRead(howRead, previousHowRead);
				output.write("sucess");
				output.write("eof");
				output.flush();
			};break;
			default : {
				output.write("failure");
				output.write("eof");
				output.flush();
			}
			}
			flushHashMap();
			
		}catch (IOException e) {
			System.out.println("更新数据出错");
			e.printStackTrace();
		}finally {
			run();
		}
	}
	//完成删除操作后，同步数据库的哈希表的数据
	public void delete() {
		try {
			int count = 0;
			char [] buffer = new char[1024];
			String response = null;
			System.out.println("卡了吗");
			while((count=input.read(buffer))>0) {
				response = new String(buffer);
				if(response.indexOf("eof")!=-1) {
					response = response.substring(0,response.indexOf("eof"));
					break;
				}
			}
			String english = response.substring(0, response.indexOf(";"));
			String chinese = response.substring(response.indexOf(";")+1);
			DataConnector.dataBase.deleteRelational(english, chinese);
			//告诉客户端
			output.write("finish");
			output.write("eof");
			output.flush();
			flushHashMap();
		}catch(IOException e) {
			System.out.println("删除数据出错");
			e.printStackTrace();
		}
	}
	// 完成插入操作后 本处理类 同步数据库的哈希表
	public void insert() {
		try {
			int count = 0;
			char [] buffer = new char[1024];
			String response = null;
			while((count=input.read(buffer))>0) {
				response = new String(buffer);
				if(response.indexOf("eof")!=-1) {
					response = response.substring(0,response.indexOf("eof"));
					break;
				}
			}
			String english = response.substring(0,response.indexOf(";"));
			response = response.substring(response.indexOf(";")+1);
			String howRead = response.substring(0,response.indexOf(";"));
			String chinese = response.substring(response.indexOf(";")+1);
			DataConnector.dataBase.addDataBase(english, howRead, chinese);
			//告诉客户端
			output.write("finish");
			output.write("eof");
			output.flush();
			flushHashMap();
		}catch(IOException | SQLException e) {
			System.out.println("添加数据失败");
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		
		int c = 0;
		char [] buffer = new char[1024];
		String request = null;
		//把循环条件初始化 以免下次新的线程进不去循环，导致无法响应客户端的请求
		stopped = false;
		try {
			while(!stopped) {
				while((c = input.read(buffer))>0) {
					request = new String(buffer);
					if(request.indexOf("eof")!=-1) {
						request = request.substring(0,request.indexOf("eof"));
						break;
					}
				}
				System.out.println(request);
				//根据请求的不同响应不同的信息
				switch(request) {
				case "close" : closed(); stopped = true; break;
				case "query" : query(); break;
				case "updata" : updata(); break;
				case "delete" : delete(); break;
				case "insert" : insert(); break;
				default : request(request);break;
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setHashMap(HashMap dataHashMap) {
		
		this.dataHashMap = dataHashMap;
		
	}
	
	public synchronized void paraRequest(String request) throws IOException, InterruptedException {
		request = request.toUpperCase();
		this.howRead = DataConnector.dataBase.getHowRead(request);
		Thread.sleep(200);
		this.chineseName = (String) dataHashMap.get(request);

	}
}
