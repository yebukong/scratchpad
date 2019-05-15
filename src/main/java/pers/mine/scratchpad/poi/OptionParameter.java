package pers.mine.scratchpad.poi;

import java.text.MessageFormat;

/**
 * 可选类型参数:通过布尔值来确定被"可选类型开始关键字"和"可选类型结束关键字"所包含内容是否展示
 */
public class OptionParameter extends WordParameter<Boolean> {
	private String beginKeyFmt = "【※{0}※";
	private String endKeyFmt = "※{0}※】";
	
	private static final long serialVersionUID = 1L;
	public OptionParameter(String name, Boolean value) {
		super(name, value);
	}
	
	public String getStringValue() {
		if(value==null){
			return "null";
		}
		return value.toString();
	}

	
	/**
	 * 获取可选类型开始的key
	 * @return
	 */
	public String getBeginKey(){
		return MessageFormat.format(beginKeyFmt, this.name);
	}
	
	/**
	 *  获取可选类型结束的key
	 * @return
	 */
	public String getEndKey(){
		return MessageFormat.format(endKeyFmt, this.name);
	}
	
	public String getBeginKeyFmt() {
		return beginKeyFmt;
	}

	public void setBeginKeyFmt(String beginKeyFmt) {
		this.beginKeyFmt = beginKeyFmt;
	}

	public String getEndKeyFmt() {
		return endKeyFmt;
	}

	public void setEndKeyFmt(String endKeyFmt) {
		this.endKeyFmt = endKeyFmt;
	}
}

