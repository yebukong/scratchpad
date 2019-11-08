package pers.mine.scratchpad.poi;

public class StringParameter extends WordParameter<String>{
	private static final long serialVersionUID = -7646193102466738047L;

	public StringParameter(String name, String value) {
		super(name, value);
	}

	public String getStringValue() {
		if(value==null)return "";
		return value;
	}

}
