package shangj;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Results {	
	public List<Integer> settings;
	public String EUI;
	
	public Results() {};
	public Results(String eui, List<Integer> settings) {
		this.EUI = eui;
		this.settings = settings;
	}	
}
