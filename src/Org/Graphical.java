package Org;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
public class Graphical {
	//一下变量是设置主界面的UI变量
	private JFrame mainJframe;
	
	private JPanel mainJpanel;
	
	private JLabel mainJlabel1;
	
	private JLabel mainJlabel2;
	
	private JLabel mainJlabel3;
	
	private JTextField mainJtextfield;
	
	private JButton mainJbutton;
	
	private String serchEnglish;
	
	private String returnChinese;
	//以下变量是设置响应界面的UI变量
	
	private JPanel returnJpanel;
	
	private JPanel mainReturnJpanel;
	
	private JLabel englishJlabel;
	
	private JLabel chineseJlabel;
	
	private JLabel returnJlabel3;
	
	private JButton confirmJbutton;
	
	private JButton returnJbutton;
	
	private JTextField returnJtextfield;
	
	private JTabbedPane setJTabbedPane = new JTabbedPane();
	
	private HashMap <String,String>userHashMap = null;
	/*
	 * setMaintUI()，会获得主界面UI，并注册相应的事件
	 * 
	 */
	public void setContainer() {
		ImageIcon im = new ImageIcon("D:\\java文件\\EnglishSerach\\icon\\English-o.png");
		mainJframe = new JFrame("EnglishSerach");
		mainJframe.setSize(1000, 300);
		mainJframe.setIconImage(im.getImage());
		mainJframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JPanel framepane = new JPanel();
		
		setMaintUI();
		setReturnUI();
		mainJframe.getContentPane().add(mainJpanel);
		mainJframe.getContentPane().add(returnJpanel);
		mainJframe.getContentPane().add(setJTabbedPane);
		setJTabbedPane.addTab("主界面",null,mainJpanel,"下一个");
		mainJframe.setVisible(true);
	}
	public void setMaintUI() {
		StringBuffer saveEnglishName = new StringBuffer();
		mainJpanel = new JPanel();
		mainJlabel1 = new JLabel("输入英文单词：");
		mainJtextfield = new JTextField(25);
		mainJtextfield.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				saveEnglishName.append((char)e.getKeyCode());
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mainJbutton = new JButton("确认");
		//主界面确认事件注册
		mainJbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setEnglishName(saveEnglishName.toString());
				setReturnUI();
				setJTabbedPane.addTab(getEnglishName(),null,mainReturnJpanel,"下一个");
			}
			
		});
		mainJlabel2 = new JLabel("本软件测试中..");
		mainJlabel2.setHorizontalAlignment(SwingConstants.CENTER);
		mainJlabel3 = new JLabel("显示部分..");
		mainJlabel3.setHorizontalAlignment(SwingConstants.CENTER);
		setMainLayout();
	}
	/*
	 * setReturnUI(),返回查询结果页面，并注册相应的事件
	 * 具有重新查询，再次查询的按钮
	 */
	public void setReturnUI() {
		StringBuffer saveEnglishName = new StringBuffer();
		mainReturnJpanel = new JPanel();
		returnJpanel = new JPanel();
		returnJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		returnJlabel3 = new JLabel("请继续输入查询单词");
		returnJtextfield = new JTextField(25);
		returnJtextfield.addKeyListener(new KeyListener() {

			
			public void keyPressed(KeyEvent e) {
				saveEnglishName.append((char)e.getKeyChar());
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		confirmJbutton = new JButton("确认");
		confirmJbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEnglishName(saveEnglishName.toString());
				setReturnUI();
				setJTabbedPane.addTab(getEnglishName(),null,mainReturnJpanel,"下一个");
			}
			
		});
		returnJbutton = new JButton("返回主界面");
		returnJbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		returnJpanel.add(returnJbutton);
		returnJpanel.add(returnJlabel3);
		returnJpanel.add(returnJtextfield);
		returnJpanel.add(confirmJbutton);
		englishJlabel = new JLabel(serchEnglish);
		getChineseName(serchEnglish);
		chineseJlabel = new JLabel(returnChinese);
		setReturnLayout();
	}
	//设置主界面的布局
	public void setMainLayout() {
		mainJpanel.setLayout(new BorderLayout(10,7));
		mainJpanel.add(setJTabbedPane, "West");
		mainJpanel.add(mainJtextfield, "Center");
		mainJpanel.add(mainJbutton, "East");
		mainJpanel.add(mainJlabel2, "North");
		mainJpanel.add(mainJlabel3, "South");
		mainJpanel.setVisible(true);
	}
	//设置查询结果返回界面的布局
	public void setReturnLayout() {
		mainReturnJpanel.setLayout(new BorderLayout(10,100));
		mainReturnJpanel.add(returnJpanel, "North");
		mainReturnJpanel.add(englishJlabel, "West");
		mainReturnJpanel.add(chineseJlabel, "East");
		mainReturnJpanel.setVisible(true);
	}
	
	public void setEnglishName(String name) {
		this.serchEnglish = name;
	}
	
	public String getEnglishName() {
		return serchEnglish;
	}
	
	
	public void getChineseName(String serchEnglish) {
		this.returnChinese = userHashMap.get(serchEnglish);
	}
	public void setHashMap(HashMap hm) {
		this.userHashMap = hm;
	}
}
