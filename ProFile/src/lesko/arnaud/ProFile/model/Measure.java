package lesko.arnaud.ProFile.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;

@XmlRootElement(name = "measure")
public class Measure {
	    
    private StringProperty id;

    private StringProperty measureType;

    private BooleanProperty userDefined;
    
    private IntegerProperty count;

	    /**
	     * Default constructor.
	     */
	    public Measure() {
	        this(null, null, true, 0);
	    }
	    
	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Measure(String id, String measureType, boolean userDefined, int count) {
		        this.id = new SimpleStringProperty(id);
		        this.measureType = new SimpleStringProperty(measureType);
		        this.userDefined = new SimpleBooleanProperty(userDefined);
		        this.count = new SimpleIntegerProperty(count);
	    }

		/**
		 * @return the measureName
		 */
		@XmlAttribute(name = "id")
		public String getId() {
			return id.get();
		}
		
		public void setId(String id) {
	        this.id.set(id);
	    }
		
		public StringProperty idProperty() {
	        return id;
	    }


		/**
		 * @return the measureType
		 */
	    @XmlAttribute(name = "measuretype")
		public String getMeasureType() {
			return measureType.get();
		}
		
		public void setMeasureType(String measureType) {
	        this.measureType.set(measureType);
	    }
		
		public StringProperty measureTypeProperty() {
	        return measureType;
	    }

		/**
		 * @return the isCustom
		 */
	    @XmlAttribute(name = "userdefined")
		public Boolean getUserDefined() {
			return userDefined.get();
		}
		
		public void setUserDefined(Boolean userDefined) {
	        this.userDefined.set(userDefined);
	    }
		
		public BooleanProperty userDefinedProperty() {
	        return userDefined;
	    }

		/**
		 * @return the count
		 */
		public Integer getCount() {
			return count.get();
		}
		
		public void setCount(Integer count) {
	        this.count.set(count);
	    }
		
		public IntegerProperty countProperty() {
	        return count;
	    }
}
