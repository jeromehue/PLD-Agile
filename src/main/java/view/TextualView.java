package view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class TextualView extends JLabel{

	private static final long serialVersionUID = 1L;

	public TextualView(){
		super();
		setBorder(BorderFactory.createTitledBorder("Vue textuelle"));
		this.setVerticalTextPosition(TOP);
		this.setVerticalAlignment(TOP);
        this.setPreferredSize(new Dimension(300,30));
	}
}