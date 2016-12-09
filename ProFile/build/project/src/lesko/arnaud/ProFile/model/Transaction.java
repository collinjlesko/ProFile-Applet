package lesko.arnaud.ProFile.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "transaction")
public class Transaction {

		
	    private Filter filter;
		
	    private Group group;
		
	    private Evaluate evaluate;
		
		//Getters and Setters
		/**
		 * @return the filter
		 */
	    @XmlElement(name="filter")
		public Filter getFilter() {
			return filter;
		}
		/**
		 * @param filter the filter to set
		 */
		public void setFilter(Filter filter) {
			this.filter = filter;
		}
		/**
		 * @return the group
		 */
	    @XmlElement(name="group")
		public Group getGroup() {
			return group;
		}
		/**
		 * @param group the group to set
		 */
		public void setGroup(Group group) {
			this.group = group;
		}
		/**
		 * @return the evaluate
		 */
		@XmlElement(name="evaluate")
		public Evaluate getEvaluate() {
			return evaluate;
		}
		/**
		 * @param evaluate the evaluate to set
		 */
		public void setEvaluate(Evaluate evaluate) {
			this.evaluate = evaluate;
		}

	    
	}