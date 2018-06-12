package UIPanel;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

public class MainFrame extends JFrame { 
	//��̬MainFrame��������Ҫ���ڵ���ģʽ
	public static MainFrame frame = null;
	
	public static HashMap userHashMap = null;
	public SystemTray tray = SystemTray.getSystemTray();
	public TrayIcon tIcon = null;
	
	public static TopPane tp = null;
	public static LeftPanel lp = null;
	
	public MainFrame() { 
		this.setUndecorated(true); // ȥ�����ڵ�װ�� 
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);//����ָ���Ĵ���װ�η�� 
		this.setIconImage(new ImageIcon(Constants.MAINPATH).getImage());//����ͼ��
		this.getContentPane().setLayout(new BorderLayout(0,2));
	} 

	public static void setMainFrame() {
		tp = new TopPane();
		lp = new LeftPanel();
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 1000);
		frame.getContentPane().add(lp.mainPanel,BorderLayout.WEST);
		frame.getContentPane().add(tp.mainPanel,BorderLayout.NORTH);
		frame.setVisible(true); 
	}
	
	public static MainFrame getFrame() {
		//����ģʽ��ʵ�֣���Ϊ�Ǿ�̬����������ֵ�����ͬһ������
		if(frame == null) {
			frame = new MainFrame();
		}
		return frame;
	}
	
	public static void setHashMap(HashMap DataHashMap) {
		userHashMap = DataHashMap;
	}
	public void setTray() {
		//��������PoupMenu�����˵���
		tIcon = new TrayIcon(new ImageIcon(Constants.MAINPATH).getImage(),"����");
		tIcon.setImageAutoSize(true);
		try {
			tray.add(tIcon);
		}catch(AWTException a) {
			System.out.println("���ͼƬ���ִ���");
			a.printStackTrace();
		}
		tIcon.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					tray.remove(tIcon);
					frame.setVisible(true);
					frame.setExtendedState(frame.NORMAL);
					frame.toFront();
				}
			}

			
		});
	}

}