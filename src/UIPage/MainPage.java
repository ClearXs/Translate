package UIPage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalScrollPaneUI;

import UIPanel.Constants;

class BackImage extends JPanel{
	
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

public class MainPage extends JPanel {
	
	public static JPanel mainPagePanel = null;
	public static JScrollPane mainJsp = null;
	
	public MainPage() {
		
		//使用自定义的PaenlUI
		/*ImageIcon mainImg = new ImageIcon(Constants.MAINPAGEPATH);
		JLabel mainLabel = new JLabel(mainImg);
		mainPagePanel.add(mainLabel);
		mainPagePanel.setPreferredSize(mainLabel.getPreferredSize());*/
		
		mainPagePanel = new BackImage();
		//不添加JSrollPane
		/*mainJsp= new JScrollPane(mainPagePanel);
		mainJsp.getVerticalScrollBar().setUI(new VerticalJsroll());
		mainJsp.getHorizontalScrollBar().setUI(new HorizontalJsroll());*/
	}
	
	public static JPanel getMainPage() {
		MainPage mg = new MainPage();
		return mg.mainPagePanel;
	}
}

// 如果没有使用JScrollPane 下面这些类可以不使用

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
		button.setUI(new PressedJbutton());
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
		button.setUI(new PressedJbutton());
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

class PressedJbutton extends BasicButtonUI{
	
	protected void paintButtonPressed(Graphics g,AbstractButton b) {
		g.setColor(Color.BLACK);
		// 填充这个矩形的边框
		g.fillRect(0, 0, b.getWidth(), b.getHeight());

	}
}
