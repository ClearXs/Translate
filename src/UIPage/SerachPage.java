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
		//����������
		mainPanel = new BackImage();
		
		//���ôν���
		orderPanel = new JPanel();
		orderPanel.setLayout(new GridBagLayout());
		GridBagConstraints controlPage = new GridBagConstraints();
		orderPanel.setOpaque(false);
		
		
		//ȷ��ͼ��
		backImg = new ImageIcon(Constants.SUREPATH);
		
		//������ʾ��ͼƬ
		mainImg = new ImageIcon(Constants.SERACHBACKGROUNDPATH);
		mainLabel = new JLabel(mainImg);
		//�����ʾ����������������ʱ�򣬿������������������Ϊ����������Ǵ�ֱ��䣬����ˮƽ��䣬������������һ����䣬�������
		controlPage.fill = GridBagConstraints.NONE;
		//��ʵ����������е����ã�ע�ⶼ�Ǵ�0��ʼ�ģ����� gridx=0��gridy=1ʱ����0��1��
		controlPage.gridx = 0;
		controlPage.gridy = 0;
		//������ռ�����������ʱ��Ҫ��������ںδ��� ��CENTER��Ĭ��ֵ����NORTH��NORTHEAST��EAST��SOUTHEAST��WEST��NORTHWESTѡ�� 
		controlPage.anchor = GridBagConstraints.CENTER;
		//�����������࣬����ĸ߶Ⱦ�������������С�߶ȼ���ipadyֵ��
		//controlPage.ipady = 50;
		orderPanel.add(mainLabel, controlPage);
			
		//���������
		serachTextField = new JTextField(55);
		serachTextField.setText("������Ӣ��,����50��Ӣ���ַ�");
		serachTextField.setFont(new Font("KaiT",Font.PLAIN,18));
		//����ǰ����ɫ����������������ɫ
		serachTextField.setForeground(new Color(139,139,122));
		serachTextField.setPreferredSize(new Dimension(300,50));
		serachTextField.setBorder(BorderFactory.createLineBorder(new Color(123,104,238), 3));	 
		controlPage.fill = GridBagConstraints.NONE;
		//Ĭ��ֵΪGridBagConstraints.REMAINDER��������������Ϊ���л���е����һ���������ռ������ʣ��Ŀռ䣻 
		controlPage.gridx = 0;
		controlPage.gridy = 1;
		controlPage.anchor = GridBagConstraints.EAST;
		controlPage.insets = new Insets(100,0,0,0);
		orderPanel.add(serachTextField,controlPage);
		
		//�������밴��
		serachButton = new JButton(backImg);
		serachButton.setUI(new PressedJbutton());
		//����ʾ�߿�
		serachButton.setBorderPainted(false);
		serachButton.setBorder(null);
		serachButton.setBackground(new Color(132,112,255));
		serachButton.setPreferredSize(new Dimension(120,50));
		serachButton.setMnemonic('\r');
		//�����
		controlPage.fill = GridBagConstraints.NONE;
		controlPage.gridwidth = 2;
		controlPage.gridx = 1;
		controlPage.gridy = 1;
		//����������
		controlPage.anchor = GridBagConstraints.WEST;
		//�������֮��˴˵ļ�ࡣ�����ĸ��������ֱ����ϣ����£��ң�Ĭ��Ϊ��0��0��0��0���� 
		controlPage.insets = new Insets(100,0,0,0);
		orderPanel.add(serachButton,controlPage);		
		
		histroyPanel = new JPanel();
		JLabel histroyLabel = new JLabel("��ʷ������");
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
		
		//����������
		mainPanel.add(orderPanel);
		
		// ����������ǩ
		// ����Ϊ�ڶ���
		mainTable = new STabbedPane();
		tableImg = new ImageIcon(Constants.TABLEPATH);
		mainTable.addTab("������ҳ", tableImg, mainPanel, "����", true);
		mainTable.setColorNorth(Color.decode("#EEEEEE"));
		mainTable.setColorSouth(Color.decode("#c8c3e9"));
		mainTable.setColorBorder(Color.BLACK);
		mainTable.setFont(new Font("KaiT",Font.PLAIN,16));
		mainTable.setForeground(Color.decode("#000000"));
	

		
	}
	
	public void addEvent() {
		serachTextField.addKeyListener(new KeyListener() {

			@Override
			// ֻ����Сд��ĸ���˸��
			// ����count���ڼ�¼�ַ������ж��ٸ��ַ�
			public void keyPressed(KeyEvent e) {
				if(((e.getKeyChar()>=65 && e.getKeyChar() <= 90) 
						|| (e.getKeyChar()>=97 && e.getKeyChar()<=122))
						|| e.getKeyChar() == '\b' ) {
					//����⵽�˸��ǣ�ɾ����һ����ĸ ����count��ֵ��һ����֤count׼ȷ��¼�ַ����е�ֵ�ĸ���
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
			//����ť�������ʱ�����һ���µĽ��棬���Ҳ����µ���ʷ��¼
			public void mouseClicked(MouseEvent e) {
				serachButton.setBackground(new Color(123,104,238));
				//����½���
				try {
					Transfer.output.write(english.toString());
					Transfer.output.write("eof");
					Transfer.output.flush();
					ContentPage cp = new ContentPage();
					mainTable.addTab(english.toString(),tableImg, cp.mainJsp , "����", true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//��ת���½���
				mainTable.setSelectedIndex(mainTable.getTabCount()-1);
				//�����µ���ʷ��¼
				serachLabel[serachCount] = new JLabel(english.toString());
				//����һЩѡ��
				serachLabel[serachCount].setOpaque(false);
				serachLabel[serachCount].setForeground(Color.decode("#e3e8fd"));
				serachLabel[serachCount].setFont(new Font("KaiT",Font.PLAIN,15));
				//Ϊ�����ʷ��¼��Ӽ�����
				serachLabel[serachCount].addMouseListener(new MouseAdapter() {
					
					
					//ÿ����ʷ��¼��õ�ǰ��¼����ֵ
					//����Ҫ�����򽫲��������Ӧ�Ķ�̬�仯
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
			// ���������εı߿�
			g.fillRect(0, 0, b.getWidth(), b.getHeight());

		}
	}
}


