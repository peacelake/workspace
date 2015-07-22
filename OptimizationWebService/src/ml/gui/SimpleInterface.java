package ml.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ml.engine.Engine;

public class SimpleInterface extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private final JFrame frame;
    private final String DEFAULT_TITLE = "ML-EUI Test Demo";
    
    
	
	
	private Engine core;
	
	public SimpleInterface(Engine e){
		core = e;
		// build the frame
		frame = new JFrame(DEFAULT_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(650, 500));
		frame.setResizable(true);
		
		try {
			core.trainedModel();
		} catch (Exception e1) {
			showErrorDialog(frame,"Error!","Failed to train models");
		}
		
		

		frame.pack();
		frame.setVisible(true);
	}
	
    // for error info
    private static void showErrorDialog(Component c, String title, String msg) {
	JOptionPane.showMessageDialog(c, msg, title, JOptionPane.ERROR_MESSAGE);
    }

}
