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
		//设置表的视图首选的方式
		entochTable.setPreferredScrollableViewportSize(new Dimension(1000,1000));
		//设置每个单元格字体
		entochTable.setFont(new Font("KaiT",Font.PLAIN,15));
		//设置选择单元格的背景色
		entochTable.setSelectionBackground(Color.WHITE);
		//设置选中单元格的字体颜色
		entochTable.setSelectionForeground(Color.blue);
		//设置每个单元格的高度
		entochTable.setRowHeight(25);
		jsp = new JScrollPane(entochTable);
		//添加滑轮的UI
		jsp.getVerticalScrollBar().setUI(new VerticalJsroll());
		jsp.getHorizontalScrollBar().setUI(new HorizontalJsroll());
		jsp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		//刷新操作
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
		//弹出菜单项
		tablePopup = new JPopupMenu();
		tablePopup.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		//子菜单"删除"项
		tableMenuItem = new JMenuItem("删除");
		tableMenuItem.setFont(new Font("KaiT",Font.PLAIN,15));
		tableMenuItem.setBackground(Color.white);
		tableMenuItem.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		//子菜单"添加"项
		insertMenuItem = new JMenuItem("添加到选中的后一行");
		insertMenuItem.setFont(new Font("KaiT",Font.PLAIN,15));
		insertMenuItem.setBackground(Color.WHITE);
		insertMenuItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		//向JTable添加鼠标监听事件
		entochTable.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				//判断鼠标是否是右键点击,并且判断是否选中
				if(e.isMetaDown() && entochTable.getSelectedRow()!= -1) {
					//获取选中的所有行索引
					int rowsIndex[] = entochTable.getSelectedRows();
					//获取单个选中的索引
					rowIndex = entochTable.getSelectedRow();

					tablePopup.add(tableMenuItem);
					tablePopup.add(insertMenuItem);
					tablePopup.show(entochTable, e.getX(), e.getY());
				}
			}	
		});
		//为什么不在JTable里面添加菜单项监听器，主要是没产一次点击都会造成在次为这个对象添加一次监听器，即产生多种结果
		tableMenuItem.addMouseListener(new MouseAdapter() {
			
			@Override
			//监听是否点击"删除菜单项" 若选择则删除，并通知所有的监听器
			public void mousePressed(MouseEvent e) {
				my.delete(rowIndex);
			}
		});
		//"插入表单项"
		insertMenuItem.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				String [] value = new String[4];
				JOptionPane insertMessge = new JOptionPane("名称");
				String getValue = insertMessge.showInputDialog("计数值");
				value[0] = getValue;
				getValue = insertMessge.showInputDialog("英文");
				value[1] = getValue;
				getValue = insertMessge.showInputDialog("音标");
				value[2] = getValue;
				getValue = insertMessge.showInputDialog("中文");
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
				flushLabel.setToolTipText("点击同步数据库数据");
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
		//存放表格数据的线性表
		public Vector tableData;
		public Vector tableLists;
		
		public void setTableData(String response) {
			try{
				int deviant ;
				//count值
				deviant = response.indexOf(";");
				String countString = response.substring(0, deviant);
				response = response.substring(deviant+1);
				//english值
				deviant = response.indexOf(";");
				String english = response.substring(0, deviant);
				response = response.substring(deviant+1);
				//howRead值
				deviant = response.indexOf(";");
				String howRead = response.substring(0, deviant);
				response = response.substring(deviant+1);
				//chinese值
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
			//属性名的向量表
			tableData = new Vector();
			//数据向量表
			tableLists = new Vector();
			
			tableLists.add("计数");
			tableLists.add("英文");
			tableLists.add("音标");
			tableLists.add("中文");
			try {
				Transfer.output.write("query");
				Transfer.output.write("eof");
				Transfer.output.flush();
			}catch(Exception e) {
				System.out.println("发送请求失败");
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
					System.out.println("初始化数据表格时发生错误");
					e.printStackTrace();
				}
			}
		}	
		
		public void getTable() {
			//属性名的向量表
			tableData = new Vector();
			//数据向量表
			tableLists = new Vector();
			
			tableLists.add("计数");
			tableLists.add("英文");
			tableLists.add("音标");
			tableLists.add("中文");
			try {
				Transfer.output.write("query");
				Transfer.output.write("eof");
				Transfer.output.flush();
			}catch(Exception e) {
				System.out.println("发送请求失败");
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
					System.out.println("初始化数据表格时发生错误");
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
		//更新计数值
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
				System.out.println("跟新计数值失败");
				e.printStackTrace();
			}
		}
		//更新英语值
		public void updataEnglish(String english,String previousEnglish) {
			try {
				Transfer.output.write("english");
				Transfer.output.write(";");
				Transfer.output.write(previousEnglish);
				Transfer.output.write(";");
				Transfer.output.write(english);
				Transfer.output.write("eof");
				Transfer.output.flush();
				System.out.println("进来了吗");
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
					JOptionPane.showMessageDialog(null, "更新失败");break;
				}
				case "sucess" : {
					JOptionPane.showMessageDialog(null, "更新成功");break;
				}
				default : JOptionPane.showMessageDialog(null, "不知道的错误");break;
				}
				
			}catch(Exception e) {
				System.out.println("更新英语值失败");
				e.printStackTrace();
			}
		}
		//更新音标值
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
					JOptionPane.showMessageDialog(null, "更新失败");break;
				}
				case "sucess" : {
					JOptionPane.showMessageDialog(null, "更新成功");break;
				}
				default : JOptionPane.showMessageDialog(null, "不知道的错误");break;
				}
				
			}catch(Exception e) {
				System.out.println("更新音标值失败");
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
					JOptionPane.showMessageDialog(null, "更新失败");break;
				}
				case "sucess" : {
					JOptionPane.showMessageDialog(null, "更新成功");break;
				}
				default : JOptionPane.showMessageDialog(null, "不知道的错误");break;
				}
				
			}catch(Exception e) {
				System.out.println("更新中文值失败");
				e.printStackTrace();
			}
		}
		//设置新的值
		public void	setValueAt(Object aValue, int rowIndex, int columnIndex) {	
			//除了更新向量表里面的内容，还要更新数据的内容
			String [] getValue = (String[])tableData.get(rowIndex);
			//获取之前的值
			String previousValue = getValue[columnIndex];
			//获取现在的值
			getValue[columnIndex] = (String)aValue;
			tableData.set(rowIndex, getValue);
			//更新数据库的内容
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
				System.out.println("更新数据库失败");
				e.printStackTrace();
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		//移除表格中的数据，并且通知数据库移除相应的数据
		public void delete(int rowIndex) {
			String [] getValue = (String[]) tableData.get(rowIndex);
			//英文
			String english = (String)getValue[1];
			//中文
			String chinese = (String)getValue[3];
			
			try {
				Transfer.output.write("delete");
				Transfer.output.write("eof");
				Transfer.output.flush();
				//发送完 请求头信息后，睡眠0.5秒后发送删除的内容信息
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
				JOptionPane.showMessageDialog(null, "更新成功"+response);
			}catch(IOException | InterruptedException e) {
				System.out.println("移除数据失败");
				e.printStackTrace();
			}
			
			tableData.remove(rowIndex);
			
			//通知所有监听器删除了
			fireTableRowsDeleted(rowIndex,rowIndex);
			
		}
		//加入新的数据，首先更新表格后，在更新服务器
		public void insert(String [] value,int rowIndex) {
			String english = value[1];
			String howRead = value[2];
			String chinese = value[3];
			System.out.println("进来了?");
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
				JOptionPane.showMessageDialog(null, "更新成功"+response);
			}catch(IOException | InterruptedException e) {
				System.out.println("添加数据失败");
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
