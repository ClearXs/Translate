package Servce;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class DataConnector implements Runnable {
	//�����Ķ˿�
	private int port = 8080;
	//����ѹ���ջ��
	private Stack processors = new Stack();
	//����processors����
	private int maxProcessor = 20;
	//��¼��������processor������ֵ
	private int curProcessor = 0;
	//�ɽ��ܵ�������
	private int acceptCount = 10;
	//���ӵĵ�ַ
	private String address = "localhost";
	private InetAddress paraAddress = null;
	//��̨�߳�
	private Thread backThread = null;
	
	private ServerSocket serverSocket = null;
	
	public static DataBase dataBase = null;
	
	//�����������׽���
	//�������ݿ�
	public void initalize() {
		try {
			dataBase = new DataBase();
			paraAddress = InetAddress.getByName(address);
			serverSocket = new ServerSocket(port,acceptCount,paraAddress);
			//InetSocketAddress socketAddress = InetSocketAddress.createUnresolved("39.104.177.160", 8080);
			//serverSocket.bind(socketAddress);
			start();
		}catch(Exception e) {
			System.out.println("��������ʼ��ʧ��");
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
