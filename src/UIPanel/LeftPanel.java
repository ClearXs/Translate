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
		//����������� 	
		mainPanel = new JPanel();
		mainPanel.setSize(300, 1000);
		//������ҳ
		mainPagePanel = new JPanel();
		homeImg = new ImageIcon(Constants.HOMEATH);
		homeLabel = new JLabel(homeImg);
		homeLabel.setText("��ҳ");
		homeLabel.setFont(new Font("KaiT",Font.PLAIN,14));
		homeLabel.setIconTextGap(10);
		mainPagePanel.setPreferredSize(homeLabel.getPreferredSize());
		mainPagePanel.setBackground(new Color(246,246,236));
		mainPagePanel.add(homeLabel);
		
		//��������
		serachPanel = new JPanel();
		serachImg = new ImageIcon(Constants.SERACHPATH);
		serachLabel = new JLabel(serachImg);
		serachLabel.setText("����");
		//��������
		serachLabel.setFont(new Font("KaiT",Font.PLAIN,14));
		//����ͼ������֮��ľ���
		serachLabel.setIconTextGap(10);
		serachPanel.setPreferredSize(serachLabel.getPreferredSize());
		serachPanel.setBackground(new Color(246,246,236));
		serachPanel.add(serachLabel);

		//���ò鿴���ݿ�
		checkDataBasePanel = new JPanel();
		checkImg = new ImageIcon(Constants.CHECKPATH);
		checkLabel = new JLabel(checkImg);
		checkLabel.setText("�鿴���ݿ�");
		checkLabel.setFont(new Font("KaiT",Font.PLAIN,14));
		checkLabel.setIconTextGap(10);
		checkDataBasePanel.setPreferredSize(checkLabel.getPreferredSize());
		checkDataBasePanel.setBackground(new Color(246,246,236));
		checkDataBasePanel.add(checkLabel);
		
		//����������Ĳ���
		
		//����Ϊ��ʽ���֣���ʱÿ������ڸ����ĸ߶�ƽ����Ӧ�ĸ߶�
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(mainPagePanel);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(serachPanel);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(checkDataBasePanel);
		//����һ�����ɼ��� ָ��Ϊ650�߶ȵ������ʹ��Box������ʹ��������������С�ĸ߶���ʾ
		//��ô��������Ŀ�Ȳ��䣿
		mainPanel.add(Box.createVerticalStrut(800));
		//��Ҫ������������ܴ����޸Ĵ�С
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
				//ֻ����һ��mainPage
				if(!loadMainPage) {
					MainFrame.frame.getContentPane().add(CenterPanel.getMainPage(),BorderLayout.CENTER);
					loadMainPage = true;
				}
				//��ǰ"��ҳ"Ϊfalseʱ���ѵ�ǰ"��ҳ"����Ϊ��ʾ
				if(!viewMainPage) {
					CenterPanel.setViewMainPage(true);
					viewMainPage = true;
				}
				//��ǰ���������ҳ����ʾ�Ļ������������ҳ������Ϊδ��ʾ
				if(viewSerachPage) {
					CenterPanel.setViewSerachPage(false);
					viewSerachPage = false;
				}
				//ͬ������Ϊδ��ʾ
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
