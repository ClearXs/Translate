package Org;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import UIPanel.MainFrame;

public class Transfer {
	public static InetAddress localhostAddress = null;
	 private int port = 41457;
	//��Ҫ�ǹ�������ʹ�õľ�̬����
	public static BufferedReader input = null;
	public static BufferedWriter output = null;
	public static Socket clientSocket = null;
	public void strat() {
		MainFrame mf = MainFrame.getFrame();
		mf.setMainFrame();
		//��ʼ��
		initalizeConnection();
		
	}	
	
	public void initalizeConnection() {
		
		try {
			localhostAddress = InetAddress.getByName("2l1135d112.51mypc.cn");
			System.out.println(localhostAddress);
			clientSocket = new Socket(localhostAddress,port);
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()),20000);
			output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()),20000);
		}catch(Exception e) {
			System.out.println("����ʧ��");
			e.printStackTrace();
		}
	}
	
	
}
