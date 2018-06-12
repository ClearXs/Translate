package UIPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import Org.Transfer;
import UIPage.SerachPage.BackImage;
import UIPanel.Constants;
import UIPanel.MainFrame;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;


public class ContentPage {
	//背景界面
	public static JPanel mainPanel = null;
	public static JScrollPane mainJsp = null;
	
	//次界面
	private JPanel orderPanel = null;
	//顶部搜索部分
	private JPanel serachPanel = null;
	private JLabel serachImgLabel = null;
	private JTextField serachTextField = null;
	private JButton serachButton = null;
	
	private JLabel histroyLabel = null;
	private JPanel histroyPanel = null;
	private JLabel[] serachLabel = new JLabel[10];
	//内容界面
	private JPanel contentPanel = null;
	
	//翻译的界面
	private JPanel translatePanel = null;
	private JLabel englishLabel = null;
	private JLabel soundsLabel = null;
	private JLabel translateLabel = null;
	private JLabel chineseLabel = null;
	
	//网页翻译的界面,待添加
	private JPanel netPanel = null;
	private JLabel netLabel = null;
	
	private int count = 0;
	
	public String chinese = null;
	
	public String howRead = null;
	
	public ContentPage() throws IOException {
		
		initalize();
		addEvent();
	}
	
	private void initalize() throws IOException {
		
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
		howRead = response.substring(0, response.indexOf(","));
		chinese = response.substring(response.indexOf(",")+1,response.length());
		
		//设置主界面
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		//设置此界面
		orderPanel = new JPanel();
		orderPanel.setOpaque(false);
		orderPanel.setLayout(new GridBagLayout());
		GridBagConstraints controlOrder = new GridBagConstraints();
		
		
		//搜索的部分
		serachPanel = new JPanel();
		serachPanel.setOpaque(false);
		serachPanel.setLayout(new GridBagLayout());
		GridBagConstraints controlSerach = new GridBagConstraints();
		//设置输入部分图标
		ImageIcon serachImg = new ImageIcon(Constants.LOGOPATH);
		serachImgLabel = new JLabel(serachImg);
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 0;
		controlSerach.gridy = 0;
		controlSerach.anchor = GridBagConstraints.WEST;
		controlSerach.insets = new Insets(0,0,0,30);
		serachPanel.add(serachImgLabel,controlSerach);
		
		//设置输入框
		serachTextField = new JTextField(30);
		serachTextField.setFont(new Font("KaiT",Font.PLAIN,18));
		//设置前景颜色，进而设置字体颜色
		serachTextField.setForeground(new Color(139,139,122));
		serachTextField.setPreferredSize(new Dimension(200,40));
		serachTextField.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 3));	
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 1;
		controlSerach.gridy = 0;
		controlSerach.anchor = GridBagConstraints.CENTER;
		controlSerach.insets = new Insets(0,0,0,0);
		serachPanel.add(serachTextField,controlSerach);
		
		//设置按钮
		ImageIcon sureImg = new ImageIcon(Constants.SUREPATH);
		serachButton = new JButton(sureImg);
		serachButton.setUI(new PressedJbutton());
		//不显示边框
		serachButton.setBorderPainted(false);
		serachButton.setBorder(null);
		serachButton.setBackground(new Color(132,112,255));
		serachButton.setPreferredSize(new Dimension(100,40));
		//不填充
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 2;
		controlSerach.gridy = 0;
		controlSerach.anchor = GridBagConstraints.CENTER;
		controlSerach.insets = new Insets(0,0,0,0);
		serachPanel.add(serachButton,controlSerach);
		
		//设置历史记录
		/*histroyPanel = new JPanel();
		JLabel histroyLabel = new JLabel("历史搜索：");
		histroyLabel.setForeground(Color.LIGHT_GRAY);
		histroyLabel.setFont(new Font("KaiT",Font.PLAIN,15));
		histroyLabel.setOpaque(false);
		histroyPanel.setOpaque(false);
		histroyPanel.add(histroyLabel);
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 0;
		controlSerach.gridy = 1;
		controlSerach.anchor = GridBagConstraints.WEST;
		controlSerach.insets = new Insets(5,0,0,0);
		serachPanel.add(histroyPanel,controlSerach);*/
		
		//内容显示部分
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		contentPanel.setOpaque(false);
		GridBagConstraints controlContent = new GridBagConstraints();
		
		//翻译部分
		translatePanel = new JPanel();
		translatePanel.setLayout(new GridBagLayout());
		translatePanel.setBackground(Color.LIGHT_GRAY);
		translatePanel.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 2));
		translatePanel.setPreferredSize(new Dimension(1000,400));
		GridBagConstraints controlTranslate = new GridBagConstraints();
		
		//英文显示的部分
		englishLabel = new JLabel(SerachPage.english.toString()+"  "+howRead);
		englishLabel.setFont(new Font("KaiT",Font.BOLD,25));
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 0;
		controlTranslate.gridy = 0;
		controlTranslate.anchor = GridBagConstraints.WEST;
		controlTranslate.insets = new Insets(-20,-450,100,50);
		translatePanel.add(englishLabel,controlTranslate);
		
		//声音部分
		ImageIcon soundsImg = new ImageIcon(Constants.SOUNDSPATH);
		soundsLabel = new JLabel(soundsImg);
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 1;
		controlTranslate.gridy = 0;
		controlTranslate.anchor = GridBagConstraints.CENTER;
		controlTranslate.insets = new Insets(-20,-250,100,50);
		translatePanel.add(soundsLabel,controlTranslate);
		
		//翻译字体的显示
		translateLabel = new JLabel("释义:");
		translateLabel.setFont(new Font("KaiT",Font.PLAIN,25));
		translateLabel.setForeground(Color.BLACK);
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 0;
		controlTranslate.gridy = 1;
		controlTranslate.insets = new Insets(100,-450,0,0);
		controlTranslate.anchor = GridBagConstraints.WEST;
		translatePanel.add(translateLabel,controlTranslate);
		
		//显示的中文翻译

		chineseLabel = new JLabel(chinese);
		chineseLabel.setFont(new Font("KaiT",Font.BOLD,20));
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 1;
		controlTranslate.gridy = 1;
		controlTranslate.anchor = GridBagConstraints.CENTER;
		controlTranslate.insets = new Insets(100,-550,0,0);
		translatePanel.add(chineseLabel,controlTranslate);
		
		//网络翻译显示
		netPanel = new JPanel();
		netPanel.setBackground(Color.LIGHT_GRAY);
		netPanel.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 2));
		netPanel.setPreferredSize(new Dimension(1000,500));
		
		netLabel = new JLabel("待添加");
		netPanel.add(netLabel);
		
		
		//添加英语翻译的Panel
		controlContent.fill = GridBagConstraints.NONE;
		controlContent.gridx = 0;
		controlContent.gridy = 0;
		controlContent.anchor = GridBagConstraints.CENTER;
		contentPanel.add(translatePanel, controlContent);
		
		//添加网络释义的Panel
		controlContent.fill = GridBagConstraints.NONE;
		controlContent.gridx = 0;
		controlContent.gridy = 1;
		controlContent.anchor = GridBagConstraints.CENTER;
		controlContent.insets = new Insets(50,0,0,0);
		contentPanel.add(netPanel,controlContent);
		
		//把搜索和内容添加
		controlOrder.fill = GridBagConstraints.BOTH;
		controlOrder.gridx = 0;
		controlOrder.gridy = 0;
		controlOrder.anchor = GridBagConstraints.CENTER;
		orderPanel.add(serachPanel, controlOrder);
		
		controlOrder.fill = GridBagConstraints.BOTH;
		controlOrder.gridy = GridBagConstraints.RELATIVE;
		controlOrder.anchor = GridBagConstraints.CENTER;
		controlOrder.gridheight = GridBagConstraints.RELATIVE;
		controlOrder.gridwidth = GridBagConstraints.RELATIVE;
		orderPanel.add(contentPanel,controlOrder);
		
		mainPanel.add(orderPanel);
		mainJsp= new JScrollPane(mainPanel);
		mainJsp.getVerticalScrollBar().setUI(new VerticalJsroll());
		mainJsp.getHorizontalScrollBar().setUI(new HorizontalJsroll());
	}
	
	private class BackImage extends JPanel{
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
	
	
	private void addEvent() {
		
		
		serachImgLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				serachImgLabel.setToolTipText("点击回到搜索主页");
				serachImgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseClicked(MouseEvent e) {
				
				SerachPage.mainTable.setSelectedIndex(0);
			}
		});
		
		serachTextField.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				serachTextField.setBorder(BorderFactory.createLineBorder(new Color(135,206,250), 3));
				serachTextField.setText("");
				serachTextField.setForeground(new Color(79,79,79));
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				serachTextField.setBorder(BorderFactory.createLineBorder(new Color(150,170,255), 3));
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				serachTextField.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 3));
			}
		});
		serachTextField.addKeyListener(new KeyAdapter() {
			// 只检测大小写字母和退格号
						// 其中count用于记录字符串中有多少个字符
						public void keyPressed(KeyEvent e) {
							if(((e.getKeyChar()>=65 && e.getKeyChar() <= 90) 
									|| (e.getKeyChar()>=97 && e.getKeyChar()<=122))
									|| e.getKeyChar() == '\b' ) {
								//当检测到退格是，删除上一个字母 并且count的值减一，保证count准确记录字符串中的值的个数
								if(e.getKeyCode() == '\b') {
									SerachPage.english.deleteCharAt(count-1);
									count--;
								}
								else {
									SerachPage.english.append((char)e.getKeyChar());
									count++;
								}
								System.out.println(SerachPage.english);
							}
						}

		});
		serachButton.addMouseListener(new MouseAdapter()  {
			//当按钮产生点击时，添加一个新的界面，并且产生新的历史记录
			public void mouseClicked(MouseEvent e) {
				serachButton.setBackground(new Color(123,104,238));
				//添加新界面
				ContentPage cp = null;
				try {
					Transfer.output.write(SerachPage.english.toString());
					Transfer.output.write("eof");
					Transfer.output.flush();
					cp = new ContentPage();
					SerachPage.mainTable.addTab(SerachPage.english.toString(),SerachPage.tableImg, cp.mainJsp , "其他", true);
					//跳转到新界面
					SerachPage.mainTable.setSelectedIndex(SerachPage.mainTable.getTabCount()-1);
					//产生新的历史记录
					SerachPage.serachLabel[SerachPage.serachCount] = new JLabel(SerachPage.english.toString());
					//设置一些选项
					SerachPage.serachLabel[SerachPage.serachCount].setOpaque(false);
					SerachPage.serachLabel[SerachPage.serachCount].setForeground(Color.decode("#e3e8fd"));
					SerachPage.serachLabel[SerachPage.serachCount].setFont(new Font("KaiT",Font.PLAIN,15));
					//为这个历史记录添加监听器
					SerachPage.serachLabel[SerachPage.serachCount].addMouseListener(new MouseAdapter() {
						
						
						//每个历史记录获得当前记录的数值
						//很重要，否则将不会产生相应的动态变化
						int i = SerachPage.serachCount;
						
						public void mouseClicked(MouseEvent e) {
							SerachPage.mainTable.setSelectedIndex(i+1);
							
						}
						public void mouseEntered(MouseEvent e) {
							
							SerachPage.serachLabel[i].setForeground(Color.decode("#aaaef8"));
							SerachPage.serachLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {
							SerachPage.serachLabel[i].setForeground(Color.decode("#e3e8fd"));
						}
					});
					SerachPage.histroyPanel.add(SerachPage.serachLabel[SerachPage.serachCount]);
					SerachPage.serachCount++;
					SerachPage.english = new StringBuffer();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		});
		
		soundsLabel.addMouseListener(new MouseAdapter() {
			boolean player = false;
			AudioPlayer lastPlayer = null;
			public void mouseClicked(MouseEvent e) {
				//播放音频文件，创建一个线程进行播放
				File musicPath = new File(System.getProperty("user.dir")+File.separator+"\\sound\\浅倉杏美 - 風の魔法.mp3");
				AudioPlayer musicPlayer = new AudioPlayer(musicPath);
				if(!player) {
					lastPlayer = musicPlayer;
					System.out.println(lastPlayer.getName());
					System.out.println(player);
					lastPlayer.start();
					player = true;	
				}else {
					player = false;
					lastPlayer.stop();
				}
			}
			
			public void mouseEntered(MouseEvent e) {
				soundsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				ImageIcon soundImg = new ImageIcon(Constants.SOUNDSPATH_1);
				soundsLabel.setIcon(soundImg);
				soundsLabel.setToolTipText("点击发声");
			}
			public void mouseExited(MouseEvent e) {
				ImageIcon soundImg = new ImageIcon(Constants.SOUNDSPATH);
				soundsLabel.setIcon(soundImg);
			}
		});
		
		
	}
	
	private class PressedJbutton extends BasicButtonUI{
		
		protected void paintButtonPressed(Graphics g,AbstractButton b) {
			g.setColor(new Color(106,90,205));
			// 填充这个矩形的边框
			g.fillRect(0, 0, b.getWidth(), b.getHeight());

		}
	}
	
	//自定义的滑轮部分
	class HorizontalJsroll extends javax.swing.plaf.basic.BasicScrollBarUI{
		protected void configureScrollBarColors() {
			trackColor = Color.lightGray;
		}
		
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			super.paintTrack(g, c, trackBounds);
		}
		
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			//首先把图像上下文移动到组件的的x,y中
			g.translate(thumbBounds.x, thumbBounds.y); 
			
			g.setColor(Color.GRAY);
			
			g.drawRoundRect(0, 0, thumbBounds.width-1, thumbBounds.height-1, 5, 5);
			
			Graphics2D g2d = (Graphics2D) g;
			RenderingHints rih = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHints(rih);
			
			g2d.fillRoundRect(0, 0, thumbBounds.width-1,thumbBounds.height-1, 5, 5);
		}
		
		
		protected JButton createIncreaseButton(int orientation) {
			JButton button = new JButton(produceImage(Constants.RIGHTPATH));
			button.setUI(new PressedJbutton());
			button.setBorderPainted(false);
			button.setBackground(Color.LIGHT_GRAY);
			button.setBorder(null);
			button.setRolloverIcon(produceImage(Constants.RIGHTPATH_1));
			button.setPressedIcon(produceImage(Constants.RIGHTPATH_2));
			return button;
		}
		
		private ImageIcon produceImage(String name) {
			ImageIcon backImage = new ImageIcon(name);
			return backImage;
		}
		@Override
		protected JButton createDecreaseButton(int orientation) {
			JButton button = new JButton(produceImage(Constants.LEFTPATH));
			button.setUI(new PressedJbutton());
			button.setBorderPainted(false);
			button.setBackground(Color.LIGHT_GRAY);
			button.setBorder(null);
			button.setRolloverIcon(produceImage(Constants.LEFTPATH_1));
			button.setPressedIcon(produceImage(Constants.LEFTPATH_2));
			return button;
		}
	}

	class VerticalJsroll extends javax.swing.plaf.basic.BasicScrollBarUI{
		protected void configureScrollBarColors() {
			// 把手
			thumbColor = Color.GRAY;
			thumbHighlightColor = Color.BLUE;
			thumbDarkShadowColor = Color.BLACK;
			thumbLightShadowColor = Color.YELLOW;
			
			// 滑道
			trackColor = Color.lightGray;
			trackHighlightColor = Color.GREEN;
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			super.paintTrack(g, c, trackBounds);
		}
		
		
		
		@Override
		// 怎么设置把手 的变化？
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			// 把绘制区的x，y点坐标定义为坐标系的原点
			// 这句一定一定要加上啊，不然拖动就失效了
			// 将图形上下文的原点平移到当前坐标系中的点 (x, y)。
			// 获取当前组件的坐标
			g.translate(thumbBounds.x, thumbBounds.y); 
			// 设置把手颜色
			// 将此图形上下文的当前颜色设置为指定颜色
			g.setColor(Color.GRAY);
			// 画一个圆角矩形
			// 这里面前四个参数就不多讲了，坐标和宽高
			// 后两个参数需要注意一下，是用来控制角落的圆角弧度
			g.drawRoundRect(0, 0, thumbBounds.width, thumbBounds.height-1, 5, 5); 
			// 消除锯齿
			Graphics2D g2 = (Graphics2D) g;
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.addRenderingHints(rh);
			// 半透明
			//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			// 设置填充颜色，这里设置了渐变，由下往上
			// g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() / 2, c.getHeight(), Color.GRAY));
			// 填充圆角矩形
			g2.fillRoundRect(0, 0, thumbBounds.width, thumbBounds.height-1, 5, 5);
		}
		
		@Override
		protected JButton createIncreaseButton(int orientation) {
			JButton button = new JButton(produceImage(Constants.DOWNPATH));
			button.setUI(new SrollButton());
			button.setBorderPainted(false);
			button.setBorder(null);
			button.setBackground(Color.LIGHT_GRAY);
			button.setRolloverIcon(produceImage(Constants.DOWNPATH_1));
			button.setPressedIcon(produceImage(Constants.DOWNPATH_2));
			return button;
		}
		
		private ImageIcon produceImage(String name) {
			ImageIcon backImage = new ImageIcon(name);
			return backImage;
		}
		@Override
		protected JButton createDecreaseButton(int orientation) {
			JButton button = new JButton(produceImage(Constants.UPRPATH));
			//重写Button的 painButtonPressed方法
			button.setUI(new SrollButton());
			// 无边框的JButton
			button.setBorderPainted(false);
			// 无边界
			button.setBorder(null);
			// 设置背景颜色
			button.setBackground(Color.LIGHT_GRAY);
			// 触碰是产生的图标变化
			button.setRolloverIcon(produceImage(Constants.UPRPATH_1));
			// 点击时产生的颜色变化
			button.setPressedIcon(produceImage(Constants.UPRPATH_2));
			return button;
		}
	}

	class SrollButton extends BasicButtonUI{
		
		protected void paintButtonPressed(Graphics g,AbstractButton b) {
			g.setColor(Color.BLACK);
			// 填充这个矩形的边框
			g.fillRect(0, 0, b.getWidth(), b.getHeight());

		}
	}
	class AudioPlayer extends Thread{
	    Player player;
	    File music;
	    //构造方法  参数是一个.mp3音频文件
	    public AudioPlayer(File file) {
	        this.music = file;
	    }
	    public void run() {
	        super.run();
	        try {
	            play();     
	        } catch (FileNotFoundException | JavaLayerException e) {
	            e.printStackTrace();
	        }
	    }
	    //播放方法
	    public void play() throws FileNotFoundException, JavaLayerException {
	        
	            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
	            player = new Player(buffer);
	            player.play();
	            
	    }
	}
}
