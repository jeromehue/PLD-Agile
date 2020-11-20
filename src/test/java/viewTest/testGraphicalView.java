package viewTest;

import javax.swing.JFrame;

import view.GraphicalView;

public class testGraphicalView {
	public static void main(String[] args) {
		JFrame frame = new JFrame("test vue grapique");
		GraphicalView graphicalView = new GraphicalView();
		frame.getContentPane().add(graphicalView);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
