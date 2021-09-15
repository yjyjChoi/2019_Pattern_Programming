package main;

public class GMain {

	static private GMainFrame mainFrame;
	public static void main(String[] args) {
		mainFrame = new GMainFrame();
		mainFrame.initialize();
		mainFrame.setVisible(true);//실제 프로그램이 시작되는 부분. 메인프레임의 이벤트loop을  돌기 시작하며 이벤트 오길 기다림
	}
}
