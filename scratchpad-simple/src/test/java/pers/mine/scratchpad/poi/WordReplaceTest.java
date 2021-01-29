package pers.mine.scratchpad.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordReplaceTest {
	WordReplace wordReplace = null;
	@Before
	public void init() throws FileNotFoundException, IOException {
		File file = new File("D:\\Mine\\Desktop\\test.docx");
		FileInputStream fileInputStream = new FileInputStream(file);
		wordReplace = new WordReplace(fileInputStream);
		wordReplace.setKitFlag(true);
	}

	@After
	public void write() throws IOException, WordReplaceException {
		File outFile = new File(
				"D:\\Mine\\Desktop\\test_out.docx");
		OutputStream os = new FileOutputStream(outFile);
		wordReplace.write(os);
		os.close();
	}
	
	@Test
    public void wrTest4() throws WordReplaceException{
        WordParameterSet parameter = new WordParameterSet();
        parameter.addOptionParameter("A1", false);
        parameter.addOptionParameter("A2", false);
        parameter.addOptionParameter("A3", false);
        parameter.addOptionParameter("A4", false);
        parameter.addOptionParameter("A5", false);
        parameter.addOptionParameter("A6", false);
        parameter.addOptionParameter("A11", false);
        parameter.addOptionParameter("A12", false);
        parameter.addOptionParameter("A13", false);
        parameter.addOptionParameter("A14", false);
        parameter.addOptionParameter("A15", false);
        parameter.addOptionParameter("A16", false);
        wordReplace.replace(parameter);
    }
	
	@Test
    public void wrTest3() throws WordReplaceException{
        WordParameterSet parameter = new WordParameterSet();
        parameter.addOptionParameter("AF-PRE-PF-PAGE", true);
        parameter.addStringParameter("AF-PRE-PF-No-A", "附件10086");
        parameter.addStringParameter("基础信息-合同名称", "测试合同名称");
        parameter.addStringParameter("融资主体提前到期-行权期", "测试合同名称");
        
        parameter.addStringParameter("基础信息-合同编号", "测试合同编号");
        parameter.addOptionParameter("A10086", true);
        wordReplace.replace(parameter);
    }
	
}

