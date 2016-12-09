package lesko.arnaud.ProFile.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//@XmlRootElement(name="measureref")
public class Measureref {
	
    private StringProperty refmeasure;
    
    /**
     * Default constructor.
     */
    public Measureref() {
        this(null);
    }
    
    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Measureref(String refmeasure) {
	        this.refmeasure = new SimpleStringProperty(refmeasure);
    }
    
    /**
	 * @return the measureName
	 */
	@XmlAttribute(name = "refmeasure")
	public String getrefmeasure() {
		return refmeasure.get();
	}
	
	public void setrefmeasure(String refmeasure) {
        this.refmeasure.set(refmeasure);
    }
	
	public StringProperty refmeasureProperty() {
        return refmeasure;
    }

}
