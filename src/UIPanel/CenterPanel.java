package UIPanel;
import UIPage.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class CenterPanel {
	
	
	public static JPanel getMainPage() {
		return MainPage.getMainPage();
	}
	
	public static void setViewMainPage(boolean flag) {
		MainPage.mainPagePanel.setVisible(flag);
	}
	
	
	public static JTabbedPane getSerachPage() {
		return SerachPage.getSerachTable();
	}
	
	public static void setViewSerachPage(boolean flag) {
		SerachPage.mainTable.setVisible(flag);
	}
	
	public static JPanel getCheckDataBasePage() {
		return CheckDataBasePage.getCheckDataBasePage();
	}
	
	public static void setViewCheckDataBasePage(boolean flag) {
		CheckDataBasePage.mainPanel.setVisible(flag);
	}
}
	