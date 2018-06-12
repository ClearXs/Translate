package UIPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel {
	public JPanel mainPanel = null;
	public JPanel mainPagePanel = null;
	public JPanel serachPanel = null;
	public JPanel checkDataBasePanel = null;
	
	private JLabel homeLabel,serachLabel,checkLabel= null;
	private ImageIcon homeImg,serachImg,checkImg = null;
	
	private static boolean loadMainPage = false;
	
	private static boolean viewMainPage = false;
	
	private static boolean loadSerachPage = false;
	
	private static boolean viewSerachPage = false;
	
	private static boolean loadCheckDataBasePage = false;
	
	private static boolean viewCheckDataBasePage = false;
	public LeftPanel() {
			initalize();
			addEvent();
	}
	public void initalize() {
		//主界面的设置 	
		mainPanel = new JPanel();
		mainPanel.setSize(300, 1000);
		//设置主页
		mainPagePanel = new JPanel();
		homeImg = new ImageIcon(Constants.HOMEATH);
		homeLabel = new JLabel(homeImg);
		homeLabel.setText("主页");
		homeLabel.setFont(new Font("KaiT",Font.PLAIN,14));
		homeLabel.setIconTextGap(10);
		mainPagePanel.setPreferredSize(homeLabel.getPreferredSize());
		mainPagePanel.setBackground(new Color(246,246,236));
		mainPagePanel.add(homeLabel);
		
		//设置搜索
		serachPanel = new JPanel();
		serachImg = new ImageIcon(Constants.SERACHPATH);
		serachLabel = new JLabel(serachImg);
		serachLabel.setText("搜索");
		//设置字体
		serachLabel.setFont(new Font("KaiT",Font.PLAIN,14));
		//设置图标字体之间的距离
		serachLabel.setIconTextGap(10);
		serachPanel.setPreferredSize(serachLabel.getPreferredSize());
		serachPanel.setBackground(new Color(246,246,236));
		serachPanel.add(serachLabel);

		//设置查看数据库
		checkDataBasePanel = new JPanel();
		checkImg = new ImageIcon(Constants.CHECKPATH);
		checkLabel = new JLabel(checkImg);
		checkLabel.setText("查看数据库");
		checkLabel.setFont(new Font("KaiT",Font.PLAIN,14));
		checkLabel.setIconTextGap(10);
		checkDataBasePanel.setPreferredSize(checkLabel.getPreferredSize());
		checkDataBasePanel.setBackground(new Color(246,246,236));
		checkDataBasePanel.add(checkLabel);
		
		//设置主界面的布局
		
		//设置为盒式布局，此时每个组件在给定的高度平分相应的高度
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(mainPagePanel);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(serachPanel);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(checkDataBasePanel);
		//创建一个不可见的 指定为650高度的组件，使在Box布局能使上面的组件尽量在小的高度显示
		//怎么保持组件的宽度不变？
		mainPanel.add(Box.createVerticalStrut(800));
		//需要用这个方法才能从新修改大小
		mainPanel.setBackground(new Color(246,246,236));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		mainPanel.setPreferredSize(new Dimension(150,1000));
		
	}
	
	public void addEvent() {
		mainPagePanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose(checkDataBasePanel);
				dispose(serachPanel);
				mainPagePanel.setBackground(new Color(158,158,158));		
				//只加载一次mainPage
				if(!loadMainPage) {
					MainFrame.frame.getContentPane().add(CenterPanel.getMainPage(),BorderLayout.CENTER);
					loadMainPage = true;
				}
				//当前"主页"为false时，把当前"主页"设置为显示
				if(!viewMainPage) {
					CenterPanel.setViewMainPage(true);
					viewMainPage = true;
				}
				//当前如果其他的页面显示的话，则把其他的页面设置为未显示
				if(viewSerachPage) {
					CenterPanel.setViewSerachPage(false);
					viewSerachPage = false;
				}
				//同理设置为未显示
				if(viewCheckDataBasePage) {
					CenterPanel.setViewCheckDataBasePage(false);
					viewCheckDataBasePage = false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				homeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				homeImg = new ImageIcon(Constants.HOMEATH_1);
				homeLabel.setIcon(homeImg);
				homeLabel.setFont(new Font("KaiT",Font.BOLD,14));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				homeImg = new ImageIcon(Constants.HOMEATH);
				homeLabel.setIcon(homeImg);
				homeLabel.setFont(new Font("KaiT",Font.PLAIN,14));
				
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
		
		serachPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose(mainPagePanel);
				dispose(checkDataBasePanel);
				serachPanel.setBackground(new Color(158,158,158));
				if(!loadSerachPage) {
					MainFrame.frame.getContentPane().add(CenterPanel.getSerachPage(), BorderLayout.CENTER);
					loadSerachPage = true;
				}
				if(!viewSerachPage) {
					CenterPanel.setViewSerachPage(true);
					viewSerachPage = true;
				}
				
				if(viewMainPage) {
					CenterPanel.setViewMainPage(false);
					viewMainPage = false;
				}
				if(viewCheckDataBasePage) {
					CenterPanel.setViewCheckDataBasePage(false);
					viewCheckDataBasePage = false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				serachLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				serachImg = new ImageIcon(Constants.SERACHPATH_1);
				serachLabel.setIcon(serachImg);
				serachLabel.setFont(new Font("KaiT",Font.BOLD,14));
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				serachImg = new ImageIcon(Constants.SERACHPATH);
				serachLabel.setIcon(serachImg);
				serachLabel.setFont(new Font("KaiT",Font.PLAIN,14));
				
			}

			
		});
		
		checkDataBasePanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose(mainPagePanel);
				dispose(serachPanel);
				checkDataBasePanel.setBackground(new Color(158,158,158));
				if(!loadCheckDataBasePage) {
					MainFrame.frame.getContentPane().add(CenterPanel.getCheckDataBasePage(), BorderLayout.CENTER);
					loadCheckDataBasePage = true;
				}
				
				if(!viewCheckDataBasePage) {
					CenterPanel.setViewCheckDataBasePage(true);
					viewCheckDataBasePage = true;
				}
				
				if(viewSerachPage) {
					CenterPanel.setViewSerachPage(false);
					viewSerachPage = false;
				}
				
				if(viewMainPage) {
					CenterPanel.setViewMainPage(false);
					viewMainPage = false;
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				checkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				checkImg = new ImageIcon(Constants.CHECKPATH_1);
				checkLabel.setIcon(checkImg);
				checkLabel.setFont(new Font("KaiT",Font.BOLD,14));
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				checkImg = new ImageIcon(Constants.CHECKPATH);
				checkLabel.setIcon(checkImg);
				checkLabel.setFont(new Font("KaiT",Font.PLAIN,14));
				
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
	}
	
	private static void dispose(JPanel panel) {
		panel.setBackground(new Color(246,246,236));
	}
}
