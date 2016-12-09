package lesko.arnaud.ProFile.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name="Filter")
public class Filter {

//	@XmlElement
	private Measureref measureref;
	
	
//Getters and Setters
	
	/**
	 * @return the measureref
	 */
    @XmlElement(name="measureref")
	public Measureref getMeasureref() {
		return measureref;
	}

	/**
	 * @param measureref the measureref to set
	 */
	public void setMeasureref(Measureref measureref) {
		this.measureref = measureref;
	}

	
}
