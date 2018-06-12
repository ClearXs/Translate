package UIPage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;

public class test2 {
	public static void main(String[] args) {
        JButton button;
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setTitle("GridBagLayout测试");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        button = new JButton("Button 1");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 60;
        frame.add(button, c);

        button = new JButton("Button 2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        frame.add(button, c);

        button = new JButton("Button 3");
        button.setPreferredSize(new Dimension(50,50));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 0;
        frame.add(button, c);

        button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40; // 在原来的基础上又加了40个单位
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        frame.add(button, c);

        button = new JButton("5");
        /* 在这里可以把垂直改成水平看效果*/
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 0;
        c.weighty = 0.5;
        /*weight是比重，即分配空白的部分,前面所有的组件均没有设置weighty，所以这里的weighty设置成任何值，都是填满空白部分*/
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 2;
        frame.add(button, c);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
