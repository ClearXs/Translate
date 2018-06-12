package Org;

public class text {
	public static String [] chinese = null;
	public static void main(String [] a) {
		StringBuffer sb = new StringBuffer();
		String s = new String("ËÑË÷,ËÑÑ°");
		parse(s);

	}
	public static void parse(String s) {
		chinese = new String[s.length()];
		int endIndex;
		int count = 0;
		for(int i = 0;s.length()>0;i++) {
			endIndex = s.indexOf(",");
			if(endIndex>=0) {
				chinese[i] = s.substring(0, endIndex);
			}
			else {
				chinese[i] = s;
				System.out.println(chinese[i]);
				break;
			}
			s = s.substring(endIndex+1);
			System.out.println((++count)+chinese[i]);
		}
	}
}