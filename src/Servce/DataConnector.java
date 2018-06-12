package Servce;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class DataConnector implements Runnable {
	//监听的端口
	private int port = 8080;
	//数据压入的栈区
	private Stack processors = new Stack();
	//最大的processors对象
	private int maxProcessor = 20;
	//记录所创建的processor对象数值
	private int curProcessor = 0;
	//可接受的连接数
	private int acceptCount = 10;
	//连接的地址
	private String address = "localhost";
	private InetAddress paraAddress = null;
	//后台线程
	private Thread backThread = null;
	
	private ServerSocket serverSocket = null;
	
	public static DataBase dataBase = null;
	
	//启动服务器套接字
	//连接数据库
	public void initalize() {
		try {
			dataBase = new DataBase();
			paraAddress = InetAddress.getByName(address);
			serverSocket = new ServerSocket(port,acceptCount,paraAddress);
			//InetSocketAddress socketAddress = InetSocketAddress.createUnresolved("39.104.177.160", 8080);
			//serverSocket.bind(socketAddress);
			start();
		}catch(Exception e) {
			System.out.println("服务器初始化失败");
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		
		while(true) {
			Socket socket = null;
			try {

				socket = serverSocket.accept();
				DataProcessor processor = createProcessor();
				if(processor == null) {
					continue;
				}
				processor.initalize(socket,dataBase.returnHashMap());
				Thread thread = new Thread(processor);
				thread.start();
			}catch(Exception e) {	
				e.printStackTrace();
			}
		}
	}
	public void start() {
		
		threadStrat();
		
		while(curProcessor<maxProcessor) {
			DataProcessor processor = newProcessor();
			recyle(processor);
		}
	}
	
	private DataProcessor createProcessor() {
		if(processors.size()>0) {
			return (DataProcessor) processors.pop();
		}
		else return null;
	}
	
	private void recyle(DataProcessor processor) {
		processors.push(processor);
		curProcessor++;
	}
	private DataProcessor newProcessor() {
		return new DataProcessor();
	}
	
	public void threadStrat() {
		backThread = new Thread(this);
		backThread.start();
	}
}
