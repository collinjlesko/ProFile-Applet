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
@XmlRootElement(name = "transactions")
public class TransactionListWrapper {

    private List<Transaction> measureProperties = new ArrayList<>();

    @XmlElement(name = "transaction")
    public List<Transaction> getMeasures() {
        return measureProperties;
    }
    
    public void setMeasures(List<Transaction> measureProperties) {
        this.measureProperties = measureProperties;
    }
}