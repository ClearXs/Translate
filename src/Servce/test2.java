package Servce;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class test2 implements Runnable {
	Socket socket = null;
	public static void main(String [] a) {
		int port = 8080;

		ServerSocket ssockt = null;
		try {
			ssockt = new ServerSocket(port);
			while(true) {
				Socket socket = null;
				socket = ssockt.accept();
				test2 t = new test2();
				t.setSocket(socket);
				Thread th = new Thread(t);
				System.out.println("1");
				th.start();
			}
		}catch(Exception e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		//带有解码的双字节输入流
		InputStreamReader input = null;
		//带有编码集的双字节输出流
		OutputStreamWriter output = null;
		//采用默认编码集的双字节输入流
		try {
			input =new InputStreamReader(this.socket.getInputStream());
			//采用默认编码集的双字节的输出流
			output = new OutputStreamWriter(this.socket.getOutputStream());
			int c ;
			char [] b =  new char[1024];
			output.write("曹！！");
			output.write("eof");
			output.flush();
			String s = null;
			while((c = input.read(b))>0) {
				s = new String(b);
				if(s.indexOf("eof")!= -1) {
					s = s.substring(0,s.indexOf("eof"));
					break;
				}
			}
			System.out.println(s);
			System.out.println(Thread.currentThread().getName());
			input.close();
			output.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
