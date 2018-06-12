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
	//静态MainFrame变量，主要用于单例模式
	public static MainFrame frame = null;
	
	public static HashMap userHashMap = null;
	public SystemTray tray = SystemTray.getSystemTray();
	public TrayIcon tIcon = null;
	
	public static TopPane tp = null;
	public static LeftPanel lp = null;
	
	public MainFrame() { 
		this.setUndecorated(true); // 去掉窗口的装饰 
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);//采用指定的窗口装饰风格 
		this.setIconImage(new ImageIcon(Constants.MAINPATH).getImage());//设置图标
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
		//单例模式的实现，因为是静态变量，所以值想的是同一个对象
		if(frame == null) {
			frame = new MainFrame();
		}
		return frame;
	}
	
	public static void setHashMap(HashMap DataHashMap) {
		userHashMap = DataHashMap;
	}
	public void setTray() {
		//可以利用PoupMenu创建菜单栏
		tIcon = new TrayIcon(new ImageIcon(Constants.MAINPATH).getImage(),"翻译");
		tIcon.setImageAutoSize(true);
		try {
			tray.add(tIcon);
		}catch(AWTException a) {
			System.out.println("添加图片出现错误");
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