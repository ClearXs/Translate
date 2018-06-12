package UIPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import Org.Transfer;
import UIPanel.Constants;



public class SerachPage {
	
	
	public static JLabel mainLabel = null;
	private JLabel backLabel = null;
	private ImageIcon mainImg = null;
	public ImageIcon backImg = null;
	private JLabel orderLabel = null;
	private JPanel mainPanel = null;
	private JPanel orderPanel = null;
	private JTextField serachTextField = null;
	private JButton serachButton = null;
	public static STabbedPane mainTable = null;
	public static  ImageIcon tableImg = null;
	public static JPanel histroyPanel = null;
	public static JLabel[] serachLabel = new JLabel[10];
	public static int serachCount ;
	
	public static StringBuffer english = new StringBuffer();
	public int count = 0; ;
	
	public SerachPage() {
		initailze();
		addEvent();
	}
	
	private void initailze() {
		//设置主界面
		mainPanel = new BackImage();
		
		//设置次界面
		orderPanel = new JPanel();
		orderPanel.setLayout(new GridBagLayout());
		GridBagConstraints controlPage = new GridBagConstraints();
		orderPanel.setOpaque(false);
		
		
		//确认图标
		backImg = new ImageIcon(Constants.SUREPATH);
		
		//设置显示的图片
		mainImg = new ImageIcon(Constants.SERACHBACKGROUNDPATH);
		mainLabel = new JLabel(mainImg);
		//如果显示区域比组件的区域大的时候，可以用来控制组件的行为。控制组件是垂直填充，还是水平填充，或者两个方向一起填充，或则不填充
		controlPage.fill = GridBagConstraints.NONE;
		//其实就是组件行列的设置，注意都是从0开始的，比如 gridx=0，gridy=1时放在0行1列
		controlPage.gridx = 0;
		controlPage.gridy = 0;
		//当组件空间大于组件本身时，要将组件置于何处。 有CENTER（默认值）、NORTH、NORTHEAST、EAST、SOUTHEAST、WEST、NORTHWEST选择； 
		controlPage.anchor = GridBagConstraints.CENTER;
		//组件间的纵向间距，组件的高度就是这个组件的最小高度加上ipady值。
		//controlPage.ipady = 50;
		orderPanel.add(mainLabel, controlPage);
			
		//设置输入框
		serachTextField = new JTextField(55);
		serachTextField.setText("请输入英文,限制50个英文字符");
		serachTextField.setFont(new Font("KaiT",Font.PLAIN,18));
		//设置前景颜色，进而设置字体颜色
		serachTextField.setForeground(new Color(139,139,122));
		serachTextField.setPreferredSize(new Dimension(300,50));
		serachTextField.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 3));	 
		controlPage.fill = GridBagConstraints.NONE;
		//默认值为GridBagConstraints.REMAINDER常量，代表此组件为此行或此列的最后一个组件，会占据所有剩余的空间； 
		controlPage.gridx = 0;
		controlPage.gridy = 1;
		controlPage.anchor = GridBagConstraints.EAST;
		controlPage.insets = new Insets(100,0,0,0);
		orderPanel.add(serachTextField,controlPage);
		
		//设置输入按键
		serachButton = new JButton(backImg);
		serachButton.setUI(new PressedJbutton());
		//不显示边框
		serachButton.setBorderPainted(false);
		serachButton.setBorder(null);
		serachButton.setBackground(new Color(132,112,255));
		serachButton.setPreferredSize(new Dimension(120,50));
		serachButton.setMnemonic('\r');
		//不填充
		controlPage.fill = GridBagConstraints.NONE;
		controlPage.gridwidth = 2;
		controlPage.gridx = 1;
		controlPage.gridy = 1;
		//组件向左对其
		controlPage.anchor = GridBagConstraints.WEST;
		//设置组件之间彼此的间距。它有四个参数，分别是上，左，下，右，默认为（0，0，0，0）； 
		controlPage.insets = new Insets(100,0,0,0);
		orderPanel.add(serachButton,controlPage);		
		
		histroyPanel = new JPanel();
		JLabel histroyLabel = new JLabel("历史搜索：");
		histroyLabel.setForeground(Color.LIGHT_GRAY);
		histroyLabel.setFont(new Font("KaiT",Font.PLAIN,15));
		histroyLabel.setOpaque(false);
		histroyPanel.setOpaque(false);
		histroyPanel.add(histroyLabel);
		controlPage.fill = GridBagConstraints.NONE;
		controlPage.gridx = 0;
		controlPage.gridy = 2;
		controlPage.anchor = GridBagConstraints.WEST;
		controlPage.insets = new Insets(5,0,0,0);
		
		orderPanel.add(histroyPanel,controlPage);
		
		//设置主界面
		mainPanel.add(orderPanel);
		
		// 设置索引标签
		// 布局为在顶部
		mainTable = new STabbedPane();
		tableImg = new ImageIcon(Constants.TABLEPATH);
		mainTable.addTab("搜索主页", tableImg, mainPanel, "其他", true);
		mainTable.setColorNorth(Color.decode("#EEEEEE"));
		mainTable.setColorSouth(Color.decode("#c8c3e9"));
		mainTable.setColorBorder(Color.BLACK);
		mainTable.setFont(new Font("KaiT",Font.PLAIN,16));
		mainTable.setForeground(Color.decode("#000000"));
	

		
	}
	
	public void addEvent() {
		serachTextField.addKeyListener(new KeyListener() {

			@Override
			// 只检测大小写字母和退格号
			// 其中count用于记录字符串中有多少个字符
			public void keyPressed(KeyEvent e) {
				if(((e.getKeyChar()>=65 && e.getKeyChar() <= 90) 
						|| (e.getKeyChar()>=97 && e.getKeyChar()<=122))
						|| e.getKeyChar() == '\b' ) {
					//当检测到退格是，删除上一个字母 并且count的值减一，保证count准确记录字符串中的值的个数
					if(e.getKeyCode() == '\b') {
						System.out.println(count);
						english.deleteCharAt(count-1);
						count--;
					}
					else {
						english.append((char)e.getKeyChar());
						count++;
					}
					System.out.println(english);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
			
			}
			
		});
		
		serachTextField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				serachTextField.setBorder(BorderFactory.createLineBorder(new Color(135,206,250), 3));
				serachTextField.setText("");
				serachTextField.setForeground(new Color(79,79,79));
				
			}
			
		});
		
		serachButton.addMouseListener(new MouseListener() {

			@Override
			//当按钮产生点击时，添加一个新的界面，并且产生新的历史记录
			public void mouseClicked(MouseEvent e) {
				serachButton.setBackground(new Color(123,104,238));
				//添加新界面
				try {
					Transfer.output.write(english.toString());
					Transfer.output.write("eof");
					Transfer.output.flush();
					ContentPage cp = new ContentPage();
					mainTable.addTab(english.toString(),tableImg, cp.mainJsp , "其他", true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//跳转到新界面
				mainTable.setSelectedIndex(mainTable.getTabCount()-1);
				//产生新的历史记录
				serachLabel[serachCount] = new JLabel(english.toString());
				//设置一些选项
				serachLabel[serachCount].setOpaque(false);
				serachLabel[serachCount].setForeground(Color.decode("#e3e8fd"));
				serachLabel[serachCount].setFont(new Font("KaiT",Font.PLAIN,15));
				//为这个历史记录添加监听器
				serachLabel[serachCount].addMouseListener(new MouseAdapter() {
					
					
					//每个历史记录获得当前记录的数值
					//很重要，否则将不会产生相应的动态变化
					int i = serachCount;
					
					public void mouseClicked(MouseEvent e) {
						mainTable.setSelectedIndex(i+1);
						
					}
					public void mouseEntered(MouseEvent e) {
						
						serachLabel[i].setForeground(Color.decode("#aaaef8"));
						serachLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						serachLabel[i].setForeground(Color.decode("#e3e8fd"));
					}
				});
				histroyPanel.add(serachLabel[serachCount]);
				serachCount++;
				english = new StringBuffer();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				serachButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				serachButton.setBackground(new Color(106,90,205));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				serachButton.setBackground(new Color(132,112,255));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
	public static JTabbedPane getSerachTable() {
		SerachPage sp = new SerachPage();
		return sp.mainTable;
	}
	
	public static JPanel getSerachPanel() {
		SerachPage sp = new SerachPage();
		return sp.mainPanel;
	}
	
	public class BackImage extends JPanel{
		private ImageIcon bImg = null;
		BackImage(){
			bImg = new ImageIcon(Constants.MAINPAGEPATH);
		}
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	        g2.drawImage(bImg.getImage(), 0, 0, getWidth(), getHeight(),
	                         0, 0, bImg.getImage().getWidth(null), bImg.getImage().getHeight(null), null);
	    }
		
	}
	

	
	private class PressedJbutton extends BasicButtonUI{
		
		protected void paintButtonPressed(Graphics g,AbstractButton b) {
			g.setColor(new Color(106,90,205));
			// 填充这个矩形的边框
			g.fillRect(0, 0, b.getWidth(), b.getHeight());

		}
	}
}


