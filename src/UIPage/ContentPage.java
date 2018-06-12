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
	//��������
	public static JPanel mainPanel = null;
	public static JScrollPane mainJsp = null;
	
	//�ν���
	private JPanel orderPanel = null;
	//������������
	private JPanel serachPanel = null;
	private JLabel serachImgLabel = null;
	private JTextField serachTextField = null;
	private JButton serachButton = null;
	
	private JLabel histroyLabel = null;
	private JPanel histroyPanel = null;
	private JLabel[] serachLabel = new JLabel[10];
	//���ݽ���
	private JPanel contentPanel = null;
	
	//����Ľ���
	private JPanel translatePanel = null;
	private JLabel englishLabel = null;
	private JLabel soundsLabel = null;
	private JLabel translateLabel = null;
	private JLabel chineseLabel = null;
	
	//��ҳ����Ľ���,�����
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
		
		//����������
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		//���ô˽���
		orderPanel = new JPanel();
		orderPanel.setOpaque(false);
		orderPanel.setLayout(new GridBagLayout());
		GridBagConstraints controlOrder = new GridBagConstraints();
		
		
		//�����Ĳ���
		serachPanel = new JPanel();
		serachPanel.setOpaque(false);
		serachPanel.setLayout(new GridBagLayout());
		GridBagConstraints controlSerach = new GridBagConstraints();
		//�������벿��ͼ��
		ImageIcon serachImg = new ImageIcon(Constants.LOGOPATH);
		serachImgLabel = new JLabel(serachImg);
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 0;
		controlSerach.gridy = 0;
		controlSerach.anchor = GridBagConstraints.WEST;
		controlSerach.insets = new Insets(0,0,0,30);
		serachPanel.add(serachImgLabel,controlSerach);
		
		//���������
		serachTextField = new JTextField(30);
		serachTextField.setFont(new Font("KaiT",Font.PLAIN,18));
		//����ǰ����ɫ����������������ɫ
		serachTextField.setForeground(new Color(139,139,122));
		serachTextField.setPreferredSize(new Dimension(200,40));
		serachTextField.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 3));	
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 1;
		controlSerach.gridy = 0;
		controlSerach.anchor = GridBagConstraints.CENTER;
		controlSerach.insets = new Insets(0,0,0,0);
		serachPanel.add(serachTextField,controlSerach);
		
		//���ð�ť
		ImageIcon sureImg = new ImageIcon(Constants.SUREPATH);
		serachButton = new JButton(sureImg);
		serachButton.setUI(new PressedJbutton());
		//����ʾ�߿�
		serachButton.setBorderPainted(false);
		serachButton.setBorder(null);
		serachButton.setBackground(new Color(132,112,255));
		serachButton.setPreferredSize(new Dimension(100,40));
		//�����
		controlSerach.fill = GridBagConstraints.NONE;
		controlSerach.gridx = 2;
		controlSerach.gridy = 0;
		controlSerach.anchor = GridBagConstraints.CENTER;
		controlSerach.insets = new Insets(0,0,0,0);
		serachPanel.add(serachButton,controlSerach);
		
		//������ʷ��¼
		/*histroyPanel = new JPanel();
		JLabel histroyLabel = new JLabel("��ʷ������");
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
		
		//������ʾ����
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		contentPanel.setOpaque(false);
		GridBagConstraints controlContent = new GridBagConstraints();
		
		//���벿��
		translatePanel = new JPanel();
		translatePanel.setLayout(new GridBagLayout());
		translatePanel.setBackground(Color.LIGHT_GRAY);
		translatePanel.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 2));
		translatePanel.setPreferredSize(new Dimension(1000,400));
		GridBagConstraints controlTranslate = new GridBagConstraints();
		
		//Ӣ����ʾ�Ĳ���
		englishLabel = new JLabel(SerachPage.english.toString()+"  "+howRead);
		englishLabel.setFont(new Font("KaiT",Font.BOLD,25));
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 0;
		controlTranslate.gridy = 0;
		controlTranslate.anchor = GridBagConstraints.WEST;
		controlTranslate.insets = new Insets(-20,-450,100,50);
		translatePanel.add(englishLabel,controlTranslate);
		
		//��������
		ImageIcon soundsImg = new ImageIcon(Constants.SOUNDSPATH);
		soundsLabel = new JLabel(soundsImg);
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 1;
		controlTranslate.gridy = 0;
		controlTranslate.anchor = GridBagConstraints.CENTER;
		controlTranslate.insets = new Insets(-20,-250,100,50);
		translatePanel.add(soundsLabel,controlTranslate);
		
		//�����������ʾ
		translateLabel = new JLabel("����:");
		translateLabel.setFont(new Font("KaiT",Font.PLAIN,25));
		translateLabel.setForeground(Color.BLACK);
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 0;
		controlTranslate.gridy = 1;
		controlTranslate.insets = new Insets(100,-450,0,0);
		controlTranslate.anchor = GridBagConstraints.WEST;
		translatePanel.add(translateLabel,controlTranslate);
		
		//��ʾ�����ķ���

		chineseLabel = new JLabel(chinese);
		chineseLabel.setFont(new Font("KaiT",Font.BOLD,20));
		controlTranslate.fill = GridBagConstraints.NONE;
		controlTranslate.gridx = 1;
		controlTranslate.gridy = 1;
		controlTranslate.anchor = GridBagConstraints.CENTER;
		controlTranslate.insets = new Insets(100,-550,0,0);
		translatePanel.add(chineseLabel,controlTranslate);
		
		//���緭����ʾ
		netPanel = new JPanel();
		netPanel.setBackground(Color.LIGHT_GRAY);
		netPanel.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 2));
		netPanel.setPreferredSize(new Dimension(1000,500));
		
		netLabel = new JLabel("�����");
		netPanel.add(netLabel);
		
		
		//���Ӣ�﷭���Panel
		controlContent.fill = GridBagConstraints.NONE;
		controlContent.gridx = 0;
		controlContent.gridy = 0;
		controlContent.anchor = GridBagConstraints.CENTER;
		contentPanel.add(translatePanel, controlContent);
		
		//������������Panel
		controlContent.fill = GridBagConstraints.NONE;
		controlContent.gridx = 0;
		controlContent.gridy = 1;
		controlContent.anchor = GridBagConstraints.CENTER;
		controlContent.insets = new Insets(50,0,0,0);
		contentPanel.add(netPanel,controlContent);
		
		//���������������
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
				serachImgLabel.setToolTipText("����ص�������ҳ");
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
			// ֻ����Сд��ĸ���˸��
						// ����count���ڼ�¼�ַ������ж��ٸ��ַ�
						public void keyPressed(KeyEvent e) {
							if(((e.getKeyChar()>=65 && e.getKeyChar() <= 90) 
									|| (e.getKeyChar()>=97 && e.getKeyChar()<=122))
									|| e.getKeyChar() == '\b' ) {
								//����⵽�˸��ǣ�ɾ����һ����ĸ ����count��ֵ��һ����֤count׼ȷ��¼�ַ����е�ֵ�ĸ���
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
			//����ť�������ʱ�����һ���µĽ��棬���Ҳ����µ���ʷ��¼
			public void mouseClicked(MouseEvent e) {
				serachButton.setBackground(new Color(123,104,238));
				//����½���
				ContentPage cp = null;
				try {
					Transfer.output.write(SerachPage.english.toString());
					Transfer.output.write("eof");
					Transfer.output.flush();
					cp = new ContentPage();
					SerachPage.mainTable.addTab(SerachPage.english.toString(),SerachPage.tableImg, cp.mainJsp , "����", true);
					//��ת���½���
					SerachPage.mainTable.setSelectedIndex(SerachPage.mainTable.getTabCount()-1);
					//�����µ���ʷ��¼
					SerachPage.serachLabel[SerachPage.serachCount] = new JLabel(SerachPage.english.toString());
					//����һЩѡ��
					SerachPage.serachLabel[SerachPage.serachCount].setOpaque(false);
					SerachPage.serachLabel[SerachPage.serachCount].setForeground(Color.decode("#e3e8fd"));
					SerachPage.serachLabel[SerachPage.serachCount].setFont(new Font("KaiT",Font.PLAIN,15));
					//Ϊ�����ʷ��¼��Ӽ�����
					SerachPage.serachLabel[SerachPage.serachCount].addMouseListener(new MouseAdapter() {
						
						
						//ÿ����ʷ��¼��õ�ǰ��¼����ֵ
						//����Ҫ�����򽫲��������Ӧ�Ķ�̬�仯
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
				//������Ƶ�ļ�������һ���߳̽��в���
				File musicPath = new File(System.getProperty("user.dir")+File.separator+"\\sound\\ǳ�}���� - �L��ħ��.mp3");
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
				soundsLabel.setToolTipText("�������");
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
			// ���������εı߿�
			g.fillRect(0, 0, b.getWidth(), b.getHeight());

		}
	}
	
	//�Զ���Ļ��ֲ���
	class HorizontalJsroll extends javax.swing.plaf.basic.BasicScrollBarUI{
		protected void configureScrollBarColors() {
			trackColor = Color.lightGray;
		}
		
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			super.paintTrack(g, c, trackBounds);
		}
		
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			//���Ȱ�ͼ���������ƶ�������ĵ�x,y��
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
			// ����
			thumbColor = Color.GRAY;
			thumbHighlightColor = Color.BLUE;
			thumbDarkShadowColor = Color.BLACK;
			thumbLightShadowColor = Color.YELLOW;
			
			// ����
			trackColor = Color.lightGray;
			trackHighlightColor = Color.GREEN;
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			super.paintTrack(g, c, trackBounds);
		}
		
		
		
		@Override
		// ��ô���ð��� �ı仯��
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			// �ѻ�������x��y�����궨��Ϊ����ϵ��ԭ��
			// ���һ��һ��Ҫ���ϰ�����Ȼ�϶���ʧЧ��
			// ��ͼ�������ĵ�ԭ��ƽ�Ƶ���ǰ����ϵ�еĵ� (x, y)��
			// ��ȡ��ǰ���������
			g.translate(thumbBounds.x, thumbBounds.y); 
			// ���ð�����ɫ
			// ����ͼ�������ĵĵ�ǰ��ɫ����Ϊָ����ɫ
			g.setColor(Color.GRAY);
			// ��һ��Բ�Ǿ���
			// ������ǰ�ĸ������Ͳ��ི�ˣ�����Ϳ��
			// ������������Ҫע��һ�£����������ƽ����Բ�ǻ���
			g.drawRoundRect(0, 0, thumbBounds.width, thumbBounds.height-1, 5, 5); 
			// �������
			Graphics2D g2 = (Graphics2D) g;
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.addRenderingHints(rh);
			// ��͸��
			//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			// ���������ɫ�����������˽��䣬��������
			// g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() / 2, c.getHeight(), Color.GRAY));
			// ���Բ�Ǿ���
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
			//��дButton�� painButtonPressed����
			button.setUI(new SrollButton());
			// �ޱ߿��JButton
			button.setBorderPainted(false);
			// �ޱ߽�
			button.setBorder(null);
			// ���ñ�����ɫ
			button.setBackground(Color.LIGHT_GRAY);
			// �����ǲ�����ͼ��仯
			button.setRolloverIcon(produceImage(Constants.UPRPATH_1));
			// ���ʱ��������ɫ�仯
			button.setPressedIcon(produceImage(Constants.UPRPATH_2));
			return button;
		}
	}

	class SrollButton extends BasicButtonUI{
		
		protected void paintButtonPressed(Graphics g,AbstractButton b) {
			g.setColor(Color.BLACK);
			// ���������εı߿�
			g.fillRect(0, 0, b.getWidth(), b.getHeight());

		}
	}
	class AudioPlayer extends Thread{
	    Player player;
	    File music;
	    //���췽��  ������һ��.mp3��Ƶ�ļ�
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
	    //���ŷ���
	    public void play() throws FileNotFoundException, JavaLayerException {
	        
	            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
	            player = new Player(buffer);
	            player.play();
	            
	    }
	}
}
