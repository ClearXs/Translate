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
		
		//ʹ���Զ����PaenlUI
		/*ImageIcon mainImg = new ImageIcon(Constants.MAINPAGEPATH);
		JLabel mainLabel = new JLabel(mainImg);
		mainPagePanel.add(mainLabel);
		mainPagePanel.setPreferredSize(mainLabel.getPreferredSize());*/
		
		mainPagePanel = new BackImage();
		//�����JSrollPane
		/*mainJsp= new JScrollPane(mainPagePanel);
		mainJsp.getVerticalScrollBar().setUI(new VerticalJsroll());
		mainJsp.getHorizontalScrollBar().setUI(new HorizontalJsroll());*/
	}
	
	public static JPanel getMainPage() {
		MainPage mg = new MainPage();
		return mg.mainPagePanel;
	}
}

// ���û��ʹ��JScrollPane ������Щ����Բ�ʹ��

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
		//��дButton�� painButtonPressed����
		button.setUI(new PressedJbutton());
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

class PressedJbutton extends BasicButtonUI{
	
	protected void paintButtonPressed(Graphics g,AbstractButton b) {
		g.setColor(Color.BLACK);
		// ���������εı߿�
		g.fillRect(0, 0, b.getWidth(), b.getHeight());

	}
}
