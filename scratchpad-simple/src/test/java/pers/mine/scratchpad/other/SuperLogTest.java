package pers.mine.scratchpad.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logger自适应
 * @author Mine
 * @data 2019年11月19日 下午7:18:53
 */
public class SuperLogTest extends SuperLog{
	
	public void say() {
		logger.debug("syay");
		logger.info("122");


	}
	public static void main(String[] args) {
		new SuperLogTest().say();
	}
}
class SuperLog{
    protected final Logger logger;
    SuperLog(){
    	System.out.println(getClass().getName());
    	logger = LoggerFactory.getLogger(getClass().getName());
    }
}
