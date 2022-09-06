package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class ContextOntologyMonitorArgument {

	private String triples;
	private String classes;
	private String individuals;
	private String objectProperties;
	private String datatypeProperties;

	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

		obj.put("triples", triples);
		obj.put("classes", classes);
		obj.put("individuals", individuals);
		obj.put("objectProperties", objectProperties);
		obj.put("datatypeProperties", datatypeProperties);

		return obj.toJSONString();
	}

	public String getObjectProperties() {
		return objectProperties;
	}

	public void setObjectProperties(String objectProperties) {
		this.objectProperties = objectProperties;
	}

	public String getDatatypeProperties() {
		return datatypeProperties;
	}

	public void setDatatypeProperties(String datatypeProperties) {
		this.datatypeProperties = datatypeProperties;
	}

	public String getIndividuals() {
		return individuals;
	}

	public void setIndividuals(String individuals) {
		this.individuals = individuals;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getTriples() {
		return triples;
	}

	public void setTriples(String string) {
		this.triples = string;
	}

}
