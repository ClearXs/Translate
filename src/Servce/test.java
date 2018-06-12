package Servce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class test {
	public static void main(String [] a) {
		int port = 8080;
		InetAddress localhostAddress = null;
		BufferedReader input = null;
		OutputStreamWriter output = null;
		Socket localhostSocket = null;
		try{
			localhostAddress = InetAddress.getByName("localhost");
			localhostSocket = new Socket(localhostAddress,port);
			input = new BufferedReader(new InputStreamReader(localhostSocket.getInputStream()));
			output = new OutputStreamWriter(localhostSocket.getOutputStream());
			output.write("其它东西");
			output.write("eof");
			output.flush();
			int c;
			System.out.println("1");
			char [] b =  new char[1024];
			String s = null;
			while((c = input.read(b))>0) {
				s = new String(b);
				if(s.indexOf("eof")!=-1) {
					break;
				}
			}
			System.out.println(s);
			input.close();
			output.close();
			localhostSocket.close();
		}catch(Exception e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}
		
	}
}
