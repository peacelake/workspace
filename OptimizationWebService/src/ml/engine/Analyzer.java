 package ml.engine;

import javax.swing.SwingUtilities;

import ml.gui.SimpleInterface;

public class Analyzer {
    // Messages
	  
    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		Engine m = new Engine();
		try {
		    SimpleInterface gui = new SimpleInterface(m);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	});
    }
}
