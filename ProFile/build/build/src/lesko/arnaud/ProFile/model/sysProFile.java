package lesko.arnaud.ProFile.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "systemprofile")
public class sysProFile {
	   
//	@XmlElement(name = "measures")
	private List<Measure> measureProperties = new ArrayList<>();

    @XmlElement(name = "measures")
    public List<Measure> getMeasures() {
        return measureProperties;
    }
    
    public void setMeasures(List<Measure> measureProperties) {
        this.measureProperties = measureProperties;
    }

}
