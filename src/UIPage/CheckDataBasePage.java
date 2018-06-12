package UIPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.MenuDragMouseEvent;
import javax.swing.event.MenuDragMouseListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import Org.Transfer;
import UIPage.ContentPage.SrollButton;
import UIPanel.Constants;

public class CheckDataBasePage implements TableModelListener {

	public static JPanel mainPanel = null;
	private JTable entochTable = null;
	public static JScrollPane jsp = null;
	private static getTableModel my = null;
	private JMenuItem tableMenuItem = null;
	private JPopupMenu tablePopup = null;
	private JMenuItem insertMenuItem = null;
	private Integer rowIndex = null;
	private JLabel flushLabel = null;
	private ImageIcon flushImg = null;
	private static CheckDataBasePage cd = null;
	
	private static boolean stopped = false;
	
	public void initalize() {
		
		mainPanel = new JPanel();
		my = new getTableModel();
		my.addTableModelListener(this);
		entochTable = new JTable(my);
		TableRowSorter sorter = new TableRowSorter(my);
		entochTable.setRowSorter(sorter);
		//���ñ����ͼ��ѡ�ķ�ʽ
		entochTable.setPreferredScrollableViewportSize(new Dimension(1000,1000));
		//����ÿ����Ԫ������
		entochTable.setFont(new Font("KaiT",Font.PLAIN,15));
		//����ѡ��Ԫ��ı���ɫ
		entochTable.setSelectionBackground(Color.WHITE);
		//����ѡ�е�Ԫ���������ɫ
		entochTable.setSelectionForeground(Color.blue);
		//����ÿ����Ԫ��ĸ߶�
		entochTable.setRowHeight(25);
		jsp = new JScrollPane(entochTable);
		//��ӻ��ֵ�UI
		jsp.getVerticalScrollBar().setUI(new VerticalJsroll());
		jsp.getHorizontalScrollBar().setUI(new HorizontalJsroll());
		jsp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		//ˢ�²���
		flushImg = new ImageIcon(Constants.FLUSHPATH);
		flushLabel = new JLabel(flushImg);
		mainPanel.add(jsp);
		mainPanel.add(flushLabel);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		mainPanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
	}	
	public CheckDataBasePage() {
		initalize();
		addEvent();
	}
	
	public void addEvent() {
		//�����˵���
		tablePopup = new JPopupMenu();
		tablePopup.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		//�Ӳ˵�"ɾ��"��
		tableMenuItem = new JMenuItem("ɾ��");
		tableMenuItem.setFont(new Font("KaiT",Font.PLAIN,15));
		tableMenuItem.setBackground(Color.white);
		tableMenuItem.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		//�Ӳ˵�"���"��
		insertMenuItem = new JMenuItem("��ӵ�ѡ�еĺ�һ��");
		insertMenuItem.setFont(new Font("KaiT",Font.PLAIN,15));
		insertMenuItem.setBackground(Color.WHITE);
		insertMenuItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		//��JTable����������¼�
		entochTable.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				//�ж�����Ƿ����Ҽ����,�����ж��Ƿ�ѡ��
				if(e.isMetaDown() && entochTable.getSelectedRow()!= -1) {
					//��ȡѡ�е�����������
					int rowsIndex[] = entochTable.getSelectedRows();
					//��ȡ����ѡ�е�����
					rowIndex = entochTable.getSelectedRow();

					tablePopup.add(tableMenuItem);
					tablePopup.add(insertMenuItem);
					tablePopup.show(entochTable, e.getX(), e.getY());
				}
			}	
		});
		//Ϊʲô����JTable������Ӳ˵������������Ҫ��û��һ�ε����������ڴ�Ϊ����������һ�μ����������������ֽ��
		tableMenuItem.addMouseListener(new MouseAdapter() {
			
			@Override
			//�����Ƿ���"ɾ���˵���" ��ѡ����ɾ������֪ͨ���еļ�����
			public void mousePressed(MouseEvent e) {
				my.delete(rowIndex);
			}
		});
		//"�������"
		insertMenuItem.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				String [] value = new String[4];
				JOptionPane insertMessge = new JOptionPane("����");
				String getValue = insertMessge.showInputDialog("����ֵ");
				value[0] = getValue;
				getValue = insertMessge.showInputDialog("Ӣ��");
				value[1] = getValue;
				getValue = insertMessge.showInputDialog("����");
				value[2] = getValue;
				getValue = insertMessge.showInputDialog("����");
				value[3] = getValue;
				my.insert(value, rowIndex);	
			}
		});
		flushLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				my.fireTableStructureChanged();
			}
			
			public void mouseEntered(MouseEvent e) {
				flushImg = new ImageIcon(Constants.FLUSHPATH_1);
				flushLabel.setIcon(flushImg);
				flushLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				flushLabel.setToolTipText("���ͬ�����ݿ�����");
			}
			
			public void mouseExited(MouseEvent e) {
				flushImg = new ImageIcon(Constants.FLUSHPATH);
				flushLabel.setIcon(flushImg);
			}
		});
	}
	
	public static JPanel getCheckDataBasePage() {
		cd = new CheckDataBasePage();
		return mainPanel;
	}
	
	private class getTableModel extends AbstractTableModel{
		//��ű�����ݵ����Ա�
		public Vector tableData;
		public Vector tableLists;
		
		public void setTableData(String response) {
			try{
				int deviant ;
				//countֵ
				deviant = response.indexOf(";");
				String countString = response.substring(0, deviant);
				response = response.substring(deviant+1);
				//englishֵ
				deviant = response.indexOf(";");
				String english = response.substring(0, deviant);
				response = response.substring(deviant+1);
				//howReadֵ
				deviant = response.indexOf(";");
				String howRead = response.substring(0, deviant);
				response = response.substring(deviant+1);
				//chineseֵ
				String chinese = response;
				String[] value = {countString,english,howRead,chinese};
				tableData.add(value);
				Transfer.output.write("next one");
				Transfer.output.write("eof");
				Transfer.output.flush();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		

		public getTableModel()  {
			//��������������
			tableData = new Vector();
			//����������
			tableLists = new Vector();
			
			tableLists.add("����");
			tableLists.add("Ӣ��");
			tableLists.add("����");
			tableLists.add("����");
			try {
				Transfer.output.write("query");
				Transfer.output.write("eof");
				Transfer.output.flush();
			}catch(Exception e) {
				System.out.println("��������ʧ��");
				e.printStackTrace();
			}
			while(!stopped) {
				try{
					int count = 0;
					char [] buffer = new char[1024];
					String response = null;
					System.out.println("1");
					while((count=Transfer.input.read(buffer))>0) {
						response = new String(buffer);
						if(response.indexOf("eof")!=-1) {
							response = response.substring(0,response.indexOf("eof"));
							break;
						}
					}
					System.out.println("2");
					switch(response) {
					case "finish":stopped = true;break;
					default : setTableData(response);break;
					}
				}catch(Exception e) {
					System.out.println("��ʼ�����ݱ��ʱ��������");
					e.printStackTrace();
				}
			}
		}	
		
		public void getTable() {
			//��������������
			tableData = new Vector();
			//����������
			tableLists = new Vector();
			
			tableLists.add("����");
			tableLists.add("Ӣ��");
			tableLists.add("����");
			tableLists.add("����");
			try {
				Transfer.output.write("query");
				Transfer.output.write("eof");
				Transfer.output.flush();
			}catch(Exception e) {
				System.out.println("��������ʧ��");
				e.printStackTrace();
			}
			while(!stopped) {
				try{
					int count = 0;
					char [] buffer = new char[1024];
					String response = null;
					System.out.println("1");
					while((count=Transfer.input.read(buffer))>0) {
						response = new String(buffer);
						if(response.indexOf("eof")!=-1) {
							response = response.substring(0,response.indexOf("eof"));
							break;
						}
					}
					System.out.println("2");
					switch(response) {
					case "finish":stopped = true;break;
					default : setTableData(response);break;
					}
				}catch(Exception e) {
					System.out.println("��ʼ�����ݱ��ʱ��������");
					e.printStackTrace();
				}
			}
		}
		
		public int getRowCount() {
			return tableData.size();
			
		}
		
		public int getColumnCount() {
			return tableLists.size();
		}
		
		public Object getValueAt(int row, int column) {
			String [] getValue = (String[])tableData.get(row);
			return getValue[column];
		}
		
		public String getColumnName(int column) {
			return (String)tableLists.get(column);
		}
		
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}
		//���¼���ֵ
		public void updataCount(String count,String previousCount) {
			try {
				Transfer.output.write("count");
				Transfer.output.write(";");
				Transfer.output.write(previousCount);
				Transfer.output.write(";");
				Transfer.output.write(count);
				Transfer.output.write("eof");
				Transfer.output.flush();
				
			}catch(Exception e) {
				System.out.println("���¼���ֵʧ��");
				e.printStackTrace();
			}
		}
		//����Ӣ��ֵ
		public void updataEnglish(String english,String previousEnglish) {
			try {
				Transfer.output.write("english");
				Transfer.output.write(";");
				Transfer.output.write(previousEnglish);
				Transfer.output.write(";");
				Transfer.output.write(english);
				Transfer.output.write("eof");
				Transfer.output.flush();
				System.out.println("��������");
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
				System.out.println(response);
				switch(response) {
				case "failure" : {
					JOptionPane.showMessageDialog(null, "����ʧ��");break;
				}
				case "sucess" : {
					JOptionPane.showMessageDialog(null, "���³ɹ�");break;
				}
				default : JOptionPane.showMessageDialog(null, "��֪���Ĵ���");break;
				}
				
			}catch(Exception e) {
				System.out.println("����Ӣ��ֵʧ��");
				e.printStackTrace();
			}
		}
		//��������ֵ
		public  void updataHowRead(String howRead,String previousHowRead) {
			try {
				Transfer.output.write("howRead");
				Transfer.output.write(";");
				Transfer.output.write(previousHowRead);
				Transfer.output.write(";");
				Transfer.output.write(howRead);
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
				switch(response) {
				case "failure" : {
					JOptionPane.showMessageDialog(null, "����ʧ��");break;
				}
				case "sucess" : {
					JOptionPane.showMessageDialog(null, "���³ɹ�");break;
				}
				default : JOptionPane.showMessageDialog(null, "��֪���Ĵ���");break;
				}
				
			}catch(Exception e) {
				System.out.println("��������ֵʧ��");
				e.printStackTrace();
			}
		}
		
		public void updataChinese(String chinese,String previousChinese) {
			try {
				Transfer.output.write("chinese");
				Transfer.output.write(";");
				Transfer.output.write(previousChinese);
				Transfer.output.write(";");
				Transfer.output.write(chinese);
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
				switch(response) {
				case "failure" : {
					JOptionPane.showMessageDialog(null, "����ʧ��");break;
				}
				case "sucess" : {
					JOptionPane.showMessageDialog(null, "���³ɹ�");break;
				}
				default : JOptionPane.showMessageDialog(null, "��֪���Ĵ���");break;
				}
				
			}catch(Exception e) {
				System.out.println("��������ֵʧ��");
				e.printStackTrace();
			}
		}
		//�����µ�ֵ
		public void	setValueAt(Object aValue, int rowIndex, int columnIndex) {	
			//���˸�����������������ݣ���Ҫ�������ݵ�����
			String [] getValue = (String[])tableData.get(rowIndex);
			//��ȡ֮ǰ��ֵ
			String previousValue = getValue[columnIndex];
			//��ȡ���ڵ�ֵ
			getValue[columnIndex] = (String)aValue;
			tableData.set(rowIndex, getValue);
			//�������ݿ������
			try {
				Thread.sleep(500);
				Transfer.output.write("updata");
				Transfer.output.write("eof");
				Transfer.output.flush();
				switch(columnIndex) {
				case 0 :updataCount(getValue[columnIndex],previousValue);break;
				case 1 :updataEnglish(getValue[columnIndex],previousValue);break;
				case 2 :updataHowRead(getValue[columnIndex],previousValue);break;
				case 3 :updataChinese(getValue[columnIndex],previousValue);break;
				}
				
			}catch(IOException | InterruptedException e) {
				System.out.println("�������ݿ�ʧ��");
				e.printStackTrace();
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		//�Ƴ�����е����ݣ�����֪ͨ���ݿ��Ƴ���Ӧ������
		public void delete(int rowIndex) {
			String [] getValue = (String[]) tableData.get(rowIndex);
			//Ӣ��
			String english = (String)getValue[1];
			//����
			String chinese = (String)getValue[3];
			
			try {
				Transfer.output.write("delete");
				Transfer.output.write("eof");
				Transfer.output.flush();
				//������ ����ͷ��Ϣ��˯��0.5�����ɾ����������Ϣ
				Thread.sleep(500);
				Transfer.output.write(english);
				Transfer.output.write(";");
				Transfer.output.write(chinese);
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
				JOptionPane.showMessageDialog(null, "���³ɹ�"+response);
			}catch(IOException | InterruptedException e) {
				System.out.println("�Ƴ�����ʧ��");
				e.printStackTrace();
			}
			
			tableData.remove(rowIndex);
			
			//֪ͨ���м�����ɾ����
			fireTableRowsDeleted(rowIndex,rowIndex);
			
		}
		//�����µ����ݣ����ȸ��±����ڸ��·�����
		public void insert(String [] value,int rowIndex) {
			String english = value[1];
			String howRead = value[2];
			String chinese = value[3];
			System.out.println("������?");
			try {
				Transfer.output.write("insert");
				Transfer.output.write("eof");
				Transfer.output.write(";");
				Transfer.output.flush();
				Thread.sleep(500);
				Transfer.output.write(english);
				Transfer.output.write(";");
				Transfer.output.write(howRead);
				Transfer.output.write(";");
				Transfer.output.write(chinese);
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
				JOptionPane.showMessageDialog(null, "���³ɹ�"+response);
			}catch(IOException | InterruptedException e) {
				System.out.println("�������ʧ��");
				e.printStackTrace();
			}
			
			
			tableData.add(rowIndex+1, value);
			fireTableRowsInserted(rowIndex, rowIndex); 
			
		}
	}	
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

	@Override
	public void tableChanged(TableModelEvent e) {
		if(e.getType() == e.UPDATE) {
			int row = e.getFirstRow();
			int column = e.getColumn();
			entochTable.repaint();
		}
		
		if(e.getType() == e.DELETE) {
			entochTable.repaint();
		}
		if(e.getType() == e.INSERT) {
			entochTable.repaint();
		}
		entochTable.repaint();
	}
	
}
