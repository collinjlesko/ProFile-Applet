package lesko.arnaud.ProFile;

import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class MeasureFilter implements StreamFilter {
	
	private boolean accept = true;
	int count = 0;
	
    public boolean accept(XMLStreamReader reader) {
    	  if(reader.isStartElement() && "measure".equals(reader.getLocalName())) {
    		 if ("TransactionMeasure".equals(reader.getAttributeValue(null, "measuretype")))
    			 return accept = false;
    		 else if ("ErrorDetectionMeasure".equals(reader.getAttributeValue(null, "measuretype")))
    			 return accept = false;
    		 else if ("ViolationMeasure".equals(reader.getAttributeValue(null, "measuretype")))
    			 return accept = false;
    		 else if ("MonitorMeasure".equals(reader.getAttributeValue(null, "measuretype")))
    			 return accept = false;
    		 else if ("JmxMeasure".equals(reader.getAttributeValue(null, "measuretype")))
    			 return accept = false;
    		 else if ("ErrorDetectionMeasure".equals(reader.getAttributeValue(null, "measuretype")))
    			 return accept = false;
    						
    		accept = "true".equals(reader.getAttributeValue(null, "userdefined"));
    		
          } else if(reader.isEndElement() && "measure".equals(reader.getLocalName())) {
              boolean returnValue = accept;
              accept = true;
              return returnValue;
          }
          return accept;
      }
}
