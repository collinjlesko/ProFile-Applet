package lesko.arnaud.ProFile.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 */
@XmlRootElement(name = "measures")
public class MeasureListWrapper {

    private List<Measure> measureProperties = new ArrayList<>();

    @XmlElement(name = "measure")
    public List<Measure> getMeasures() {
        return measureProperties;
    }
    
    public void setMeasures(List<Measure> measureProperties) {
        this.measureProperties = measureProperties;
    }
}