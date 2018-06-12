package Org;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
public class Graphical {
	//һ�±����������������UI����
	private JFrame mainJframe;
	
	private JPanel mainJpanel;
	
	private JLabel mainJlabel1;
	
	private JLabel mainJlabel2;
	
	private JLabel mainJlabel3;
	
	private JTextField mainJtextfield;
	
	private JButton mainJbutton;
	
	private String serchEnglish;
	
	private String returnChinese;
	//���±�����������Ӧ�����UI����
	
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
	 * setMaintUI()������������UI����ע����Ӧ���¼�
	 * 
	 */
	public void setContainer() {
		ImageIcon im = new ImageIcon("D:\\java�ļ�\\EnglishSerach\\icon\\English-o.png");
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
		setJTabbedPane.addTab("������",null,mainJpanel,"��һ��");
		mainJframe.setVisible(true);
	}
	public void setMaintUI() {
		StringBuffer saveEnglishName = new StringBuffer();
		mainJpanel = new JPanel();
		mainJlabel1 = new JLabel("����Ӣ�ĵ��ʣ�");
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
		mainJbutton = new JButton("ȷ��");
		//������ȷ���¼�ע��
		mainJbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setEnglishName(saveEnglishName.toString());
				setReturnUI();
				setJTabbedPane.addTab(getEnglishName(),null,mainReturnJpanel,"��һ��");
			}
			
		});
		mainJlabel2 = new JLabel("�����������..");
		mainJlabel2.setHorizontalAlignment(SwingConstants.CENTER);
		mainJlabel3 = new JLabel("��ʾ����..");
		mainJlabel3.setHorizontalAlignment(SwingConstants.CENTER);
		setMainLayout();
	}
	/*
	 * setReturnUI(),���ز�ѯ���ҳ�棬��ע����Ӧ���¼�
	 * �������²�ѯ���ٴβ�ѯ�İ�ť
	 */
	public void setReturnUI() {
		StringBuffer saveEnglishName = new StringBuffer();
		mainReturnJpanel = new JPanel();
		returnJpanel = new JPanel();
		returnJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		returnJlabel3 = new JLabel("����������ѯ����");
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
		confirmJbutton = new JButton("ȷ��");
		confirmJbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEnglishName(saveEnglishName.toString());
				setReturnUI();
				setJTabbedPane.addTab(getEnglishName(),null,mainReturnJpanel,"��һ��");
			}
			
		});
		returnJbutton = new JButton("����������");
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
	//����������Ĳ���
	public void setMainLayout() {
		mainJpanel.setLayout(new BorderLayout(10,7));
		mainJpanel.add(setJTabbedPane, "West");
		mainJpanel.add(mainJtextfield, "Center");
		mainJpanel.add(mainJbutton, "East");
		mainJpanel.add(mainJlabel2, "North");
		mainJpanel.add(mainJlabel3, "South");
		mainJpanel.setVisible(true);
	}
	//���ò�ѯ������ؽ���Ĳ���
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
