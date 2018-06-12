package UIPanel;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import Org.Transfer;
public class TopPane{
	public JPanel mainPanel = null;
	public JPanel leftPanel = null;
	public JPanel rightPanel = null;
	public JLabel content,close,minisize,maxsize,icon = null;
	public ImageIcon minisizeImg,maxsizeImg,closeImg = null;
	private int x;
	private int y;
	private int tempX;
	private int tempY;
	private int winX;
	private int winY;
	private int oldX;
	private int oldY;
	private static boolean setMax = false;
	public static boolean isClosed = false;
	
	public TopPane() {
		initalize();
		addEvent();
	}
	/*public static void main(String [] a) {
		TopPane tp = new TopPane();
		tp.jw = new JWindow();
		tp.jw.setSize(1920, 1000);	
		JInternalFrame ji = new JInternalFrame("asd");
		ji.setSize(200,200);
		ji.setVisible(true);
		tp.jw.getLayeredPane().add(tp.mainPanel,new Integer(1));
		tp.jw.getLayeredPane().add(ji, new Integer(2));
		tp.jw.getContentPane().add(tp.content);
		tp.jw.setVisible(true);
	}*/
	private void initalize() {
		//主背景Pane
		mainPanel = new JPanel();
		//设置mainPanel边框的颜色
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
		mainPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,45));
		//左图标按钮Pane
		ImageIcon mainImg = new ImageIcon(Constants.MAINPATH);
		leftPanel = new JPanel();
		icon = new JLabel(mainImg);
		icon.setToolTipText("返回主页");
		leftPanel.add(icon);
		//右图标按钮Pane
		ImageIcon minimizeImg = new ImageIcon(Constants.MINIPATH_0);
		minisize = new JLabel(minimizeImg);
		maxsizeImg = new ImageIcon(Constants.MAXPATH_0);
		maxsize = new JLabel(maxsizeImg);
		closeImg = new ImageIcon(Constants.CLOSEPATH_0);
		close = new JLabel(closeImg);
		rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		rightPanel.add(minisize);
		rightPanel.add(maxsize);
		rightPanel.add(close);	
		//设置背景
		ImageIcon contentImg = new ImageIcon("D:\\java文件\\EnglishSerach\\icon\\Top背景.png");
		content = new JLabel(contentImg);

		//设置主Pane
		mainPanel.setLayout(new BorderLayout(-10,10));
		mainPanel.add(leftPanel,BorderLayout.WEST);
		mainPanel.add(rightPanel,BorderLayout.EAST);
	}
	
	public void addEvent() {
		close.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					//编写客户端关闭连接的业务逻辑
					//关闭时，首先向服务器发送close 如果服务器收到，并返回yes 则关闭数据流
					Transfer.output.write("close");
					Transfer.output.write("eof");
					Transfer.output.flush();
					int count = 0;
					char [] buffer = new char[1024];
					String response = null;
					while((count=Transfer.input.read(buffer))>0) {
						response = new String(buffer);
						if(response.indexOf("eof")!=-1) {
							response = response.substring(0,response.indexOf("eof"));
							break;
						}
					}
					if(response == "yes") {
						Transfer.input.close();
						Transfer.output.close();
						Transfer.clientSocket.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				close.setCursor(new Cursor(Cursor.HAND_CURSOR));
				closeImg = new ImageIcon(Constants.CLOSEPATH_1);
				close.setIcon(closeImg);
				close.setToolTipText("关闭");
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				closeImg = new ImageIcon(Constants.CLOSEPATH_0);
				close.setIcon(closeImg);
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		minisize.addMouseListener(new MouseListener() {

			@Override
			//最小化到任务栏中
			public void mouseClicked(MouseEvent e) {
				if(MainFrame.frame.tray.isSupported()) {
					MainFrame.frame.setVisible(false);
					MainFrame.frame.setTray();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				minisize.setCursor(new Cursor(Cursor.HAND_CURSOR));
				minisizeImg = new ImageIcon(Constants.MINIPATH_1);
				minisize.setIcon(minisizeImg);
				minisize.setToolTipText("最小化");
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				minisizeImg = new ImageIcon(Constants.MINIPATH_0);
				minisize.setIcon(minisizeImg);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		maxsize.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!isMax()) {
					//获取当前屏幕的大小
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					//根据当前屏幕大小创建Rectangle对象 其他x=0,y=0 width=屏幕宽度，height=屏幕高度
					Rectangle bounds = new Rectangle(screenSize);
					//获取JFrame的图像大小,即全屏在任务栏上方的图像
					Insets insert = Toolkit.getDefaultToolkit().getScreenInsets(MainFrame.frame.getGraphicsConfiguration());
					//insert.left = 0;
					bounds.x += insert.left;
					//insert.top = 0 最上方
					bounds.y += insert.top;
					bounds.width -= insert.left + insert.right;
					bounds.height -= insert.bottom + insert.top;
					MainFrame.frame.setBounds(bounds);
					setMax = true;
				}
				else {
					MainFrame.frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 1000);
					setMax = false;
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				maxsize.setCursor(new Cursor(Cursor.HAND_CURSOR));
				maxsizeImg = new ImageIcon(Constants.MAXPATH_1);
				maxsize.setIcon(maxsizeImg);
				if(!isMax()) {
					maxsize.setToolTipText("最大化");
				}
				else maxsize.setToolTipText("还原");
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				maxsizeImg = new ImageIcon(Constants.MAXPATH_0);
				maxsize.setIcon(maxsizeImg);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		icon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
				icon.setToolTipText("回到主页");
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mainPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() % 2 == 0 && !isMax()) {
					//获取当前屏幕的大小
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					//根据当前屏幕大小创建Rectangle对象 其他x=0,y=0 width=屏幕宽度，height=屏幕高度
					Rectangle bounds = new Rectangle(screenSize);
					//获取JFrame的图像大小
					Insets insert = Toolkit.getDefaultToolkit().getScreenInsets(MainFrame.frame.getGraphicsConfiguration());
					bounds.x += insert.left;
					bounds.y += insert.top;
					bounds.width -= insert.left + insert.right;
					bounds.height -= insert.bottom + insert.top;
					MainFrame.frame.setBounds(bounds);
					setMax(true);
				}
				if(e.getClickCount() % 2 != 0 && isMax()) {
					MainFrame.frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 1000);
					setMax(false);
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
	
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				 Rectangle rec = MainFrame.frame.getBounds();  
			     Point point = e.getPoint();       
			     tempX = (int)point.getX();  
			     tempY = (int)point.getY();  
			     oldX = (int)point.getX();  
			     oldY = (int)point.getY(); 
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mainPanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				//获取当前鼠标定位的点
				Point point = e.getPoint();
				//获得JFrame容器当前绑定的坐标
				Rectangle rec = MainFrame.frame.getBounds();
				winX = (int)rec.getX();
				winY = (int)rec.getY();    
				x = (int)point.getX();  
		        y = (int)point.getY();        
		        tempX = x - oldX;  
		        tempY = y - oldY;             
		        MainFrame.frame.setLocation((int)(winX + tempX), (int)(winY + tempY)); 
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private static boolean isMax() {
		return setMax;
	}
	private static void setMax(boolean flag) {
		setMax = flag;
	}
}
