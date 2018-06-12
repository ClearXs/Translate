package UIPage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;



class newButton extends JButton{
	
	public newButton() {
		super();
	}
	
	public newButton(String str){
		super(str);
	}
	
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Graphics2D g2d = (Graphics2D) g;
		 
		 AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		 g2d.setComposite(composite);
		 drawButtonBackground(g2d,this,Color.white,Color.BLACK,Color.BLUE,Color.GRAY);
	}
	
    private static void drawButtonBackground(Graphics2D g2, JButton bt,
            Color c1, Color c2, Color c3, Color c4) {

        // 使平滑,没有锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // 造一个圆角区域
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0,
                bt.getWidth() - 1, bt.getHeight() - 1, 10, 10);
        Shape clip = g2.getClip();
        g2.clip(r2d);
        //
        g2.setClip(clip);

        g2.setColor(Color.BLACK);
        //绘制圆角矩形
        g2.drawRoundRect(0, 0, bt.getWidth() - 2, bt.getHeight() - 1, 10, 10);

        // 渐变背景
        g2.setPaint(new GradientPaint(100, 2, c1, 100, bt.getHeight() / 3, c2));
        g2.fillRoundRect(100, 2, bt.getWidth() - 5, bt.getHeight() / 3, 10, 10);
        // 渐变二段
        g2.setPaint(new GradientPaint(1, bt.getHeight() / 3, c3, 1, bt
                .getHeight(), c4));
        g2.fillRoundRect(2, bt.getHeight() / 3, bt.getWidth() - 5,
        bt.getHeight() / 3 * 2 - 1, 10, 10);

//      g2.dispose();

    }
	
}

class newTable extends BasicTabbedPaneUI{
	
	protected  int	calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
		
		return maxTabHeight+10;
	}

	
	protected int calculateTabWidth(int tabPlacement, int tabIndex,
            FontMetrics metrics) {
		
		return 150;
	}
	
    protected int calculateTabHeight(int tabPlacement) {
        return 25;
    }
    
    
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,  
            int selectedIndex, int x, int y, int w, int h)  {
    	
    	switch(tabPlacement) {
    	case TOP:
    		g.setColor(Color.black);
        	g.drawLine(x, y , x+w, y);
        	break;
    	}
    }
    
    protected void	paintTabArea​(Graphics g, int tabPlacement, int selectedIndex) {
    	super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    
    protected void paintTabBorder(Graphics g, int tabPlacement,  
            int tabIndex, int x, int y, int w, int h, boolean isSelected ) {  
        g.setColor(Color.GRAY);  
        switch (tabPlacement) {  
          case LEFT:  
              g.drawLine(x, y, x, y+h-1);  
              g.drawLine(x, y, x+w-1, y);  
              g.drawLine(x, y+h-1, x+w-1, y+h-1);  
              break;  
          case RIGHT:  
              g.drawLine(x, y, x+w-1, y);  
              g.drawLine(x, y+h-1, x+w-1, y+h-1);  
              g.drawLine(x+w-1, y, x+w-1, y+h-1);  
              break;                
          case BOTTOM:  
              g.drawLine(x, y, x, y+h-1);  
              g.drawLine(x+w-1, y, x+w-1, y+h-1);  
              g.drawLine(x, y+h-1, x+w-1, y+h-1);  
              break;  
          case TOP:  
          default:             
              g.drawLine(x, y, x, y+h-1);  
              g.drawLine(x, y, x+w-1, y);  
              g.drawLine(x+w-1, y, x+w-1, y+h-1);  
        }  
    }
    
    protected void paintTabBackground(Graphics g, int tabPlacement,  
            int tabIndex, int x, int y, int w, int h, boolean isSelected) {  
        GradientPaint gradient;  
        Graphics2D g2d = (Graphics2D)g;  
        switch(tabPlacement) {  
        case LEFT:  
            if (isSelected) {  
                gradient = new GradientPaint(x+1, y, Color.blue,   
                        x+w, y, Color.black, true);  
            } else {  
                gradient = new GradientPaint(x+1, y, Color.LIGHT_GRAY,   
                        x+w, y, Color.WHITE, true);  
            }  
            g2d.setPaint(gradient);  
            g.fillRect(x+1, y+1, w-1, h-2);  
            break;
        case RIGHT:  
            if (isSelected) {  
                gradient = new GradientPaint(x+w, y, Color.blue,   
                        x+1, y, Color.black, true);  
            } else {  
                gradient = new GradientPaint(x+w, y, Color.LIGHT_GRAY,   
                        x+1, y, Color.WHITE, true);  
            }  
            g2d.setPaint(gradient);  
            g.fillRect(x, y+1, w-1, h-2);  
            break;  
        case BOTTOM:  
            if (isSelected) {  
                gradient = new GradientPaint(x+1, y+h, Color.blue,   
                        x+1, y, Color.black, true);  
            } else {  
                gradient = new GradientPaint(x+1, y+h, Color.LIGHT_GRAY,   
                        x+1, y, Color.WHITE, true);  
            }  
            g2d.setPaint(gradient);  
            g.fillRect(x+1, y, w-2, h-1);  
            break;  
        case TOP:  
        default:  
            if (isSelected) {  
                gradient = new GradientPaint(x+1, y, Color.blue,   
                        x+1, y+h, Color.black, true);  
            } else {  
                gradient = new GradientPaint(x+1, y, Color.LIGHT_GRAY,   
                        x+1, y+h, Color.WHITE, true);  
            }  
            g2d.setPaint(gradient);  
            g2d.fillRect(x+1, y+1, w-2, h-1);  
        }
    }
    
    protected void layoutLabel(int tabPlacement, FontMetrics metrics,  
            int tabIndex, String title, Icon icon, Rectangle tabRect,  
            Rectangle iconRect, Rectangle textRect, boolean isSelected) {  
        textRect.x = textRect.y = iconRect.x = iconRect.y = 0;  
        View v = getTextViewForTab(tabIndex);  
        if (v != null) {  
            tabPane.putClientProperty("html", v);  
        }  
        SwingUtilities.layoutCompoundLabel(  
                (JComponent) tabPane,  
                metrics, title, icon,   
                SwingUtilities.CENTER,   
                SwingUtilities.CENTER,  
                SwingUtilities.CENTER,   
                SwingUtilities.TRAILING,   
                tabRect, iconRect, textRect, textIconGap);  
        tabPane.putClientProperty("html", null);  
    } 
}
public class test3{

	
	public static void main(String [] a) {
		JFrame jf = new JFrame("asd");
		jf.setSize(300, 300);
		JButton jb = new newButton("xxx");
		JButton jb2 = new JButton("xxx");
		jb.setPreferredSize(new Dimension(100,100));
		JTabbedPane jtb = new JTabbedPane();
		jtb.setBackground(Color.BLUE);
		jf.add(jtb);
		jtb.setUI(new newTable());
		jtb.add("11", jb);
		jtb.setBackgroundAt(0, Color.BLUE);
		JLabel jl = new JLabel("sad");
		jl.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		jtb.addTab("222", jb2);
		jtb.setTabComponentAt(0, jl);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jtb.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(jtb.getSelectedIndex());
				jtb.setTabComponentAt(jtb.getSelectedIndex(), jl);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println(jtb.getSelectedIndex());
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
	
	
}