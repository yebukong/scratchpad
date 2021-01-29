package pers.mine.scratchpad.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordTest {
	XWPFDocument document = null;

	@Before
	public void init() throws FileNotFoundException, IOException {
		File file = new File("D:\\Mine\\Desktop\\test.docx");
		FileInputStream fileInputStream = new FileInputStream(file);
		document = new XWPFDocument(fileInputStream);
	}

	@After
	public void write() throws IOException {
		File outFile = new File(
				"D:\\Mine\\Desktop\\test_out.docx");
		OutputStream os = new FileOutputStream(outFile);
		document.write(os);
		os.close();
	}

	/**
	 * 获取段落
	 */
	@Test
	public void getParagraphs() {
		List<XWPFParagraph> paragraphList = document.getParagraphs();
		if (paragraphList != null && paragraphList.size() > 0) {
			for (int i = 0; i < paragraphList.size(); i++) {
				XWPFParagraph paragraph = paragraphList.get(i);
				String paragraphText = paragraph.getParagraphText();
				System.out.println(String.format("[%-5s]", i));
				System.out.println("paragraphText:" + paragraphText);
				List<XWPFRun> runs = paragraph.getRuns();
				System.out.println("runs:" + paragraphText);
				System.out.println("runs.size:" + paragraph.getRuns().size());
				for (XWPFRun xwpfRun : runs) {
					String text = xwpfRun.getText(0);
					System.out.print(text + " | ");
				}
				System.out.println();
			}
		}
	}

	/**
	 * 获取BodyElements
	 */
	@Test
	public void getBodyElements() {
		List<IBodyElement> bodyElements = document.getBodyElements();
		if (bodyElements != null && bodyElements.size() > 0) {
			for (int i = 0, size = bodyElements.size(); i < size; i++) {
				IBodyElement iBodyElement = bodyElements.get(i);
				BodyElementType elementType = iBodyElement.getElementType();
				System.out.println(String.format(" %4s - %s ", i, elementType));
				if (elementType.equals(BodyElementType.PARAGRAPH)) {
					XWPFParagraph temp = (XWPFParagraph) iBodyElement;
					// 段落编号
					// 的样式名称（GetNumFmt），编号分组ID（GetNumID），编号样式（NumLevelText）等
					System.out.println(temp.getNumID() + "|"
							+ temp.getNumIlvl() + "|" + temp.getNumFmt() + "|");
					System.out.println("text:" + temp.getParagraphText());
				}
			}
		}
	}

	/**
	 * 删除body元素测试
	 */
	@Test
	public void removeBodyElements() {
		List<Integer> rmvIds = new ArrayList<Integer>();
		List<IBodyElement> bodyElements = document.getBodyElements();
		if (bodyElements != null && bodyElements.size() > 0) {
			for (int i = 0, size = bodyElements.size(); i < size; i++) {
				IBodyElement iBodyElement = bodyElements.get(i);
				BodyElementType elementType = iBodyElement.getElementType();
				System.out.println(i + "类型  ========  " + elementType);// XWPFParagraph
				if (elementType.equals(BodyElementType.PARAGRAPH)) {
					rmvIds.add(i);
				}
			}
		}
		int rmvCount = 0;
		for (Integer i : rmvIds) {
			document.removeBodyElement(i + rmvCount); // 由于需要合并到第一个小块内，所以需要把当前Run移除掉
			rmvCount--;
		}
	}

	/**
	 * 查询run所在块位置
	 * @throws Exception 
	 */
	@Test
	public void searchRun() throws Exception {
		String startKey = "【※A1※";
		String endKey = "※A1※】";

		List<IBodyElement> bodyElements = document.getBodyElements();
		if (bodyElements != null && bodyElements.size() > 0) {
			for (int i = 0, size = bodyElements.size(); i < size; i++) {
				IBodyElement iBodyElement = bodyElements.get(i);
				BodyElementType elementType = iBodyElement.getElementType();

				if (elementType.equals(BodyElementType.PARAGRAPH)) {
					XWPFParagraph temp = (XWPFParagraph) iBodyElement;
					if (temp.getParagraphText().contains(startKey)) {// 处理可选开头
						System.out.println(String.format(" %4s - %s 所处段落", i,
								startKey));
						// 定位关键字所在 run 索引列表
						List<Integer> keyRunPosList = getKeyRunPosList(temp, startKey);
						System.out.println(keyRunPosList.toString());
					}
					if (temp.getParagraphText().contains(endKey)) {// 处理可选结尾
						System.out.println(String.format(" %4s - %s 所处段落", i,endKey));
						List<Integer> keyRunPosList = getKeyRunPosList(temp, endKey);
						System.out.println(keyRunPosList.toString());
					}
				}
			}
		}
	}


	/**
	 * 单个可选块参数处理
	 */
	private void processSingleOptionElement(IBody iBody,
			OptionParameter op){
		List<IBodyElement> bodyElements = iBody.getBodyElements();
		if(bodyElements==null || bodyElements.isEmpty()){
			return;
		}
		
		String beginKey = op.getBeginKey();
		String endKey = op.getEndKey();
		boolean value = op.getValue();
		int beSize = bodyElements.size();
		BodyElementType eType;
		XWPFParagraph xp;
		int matchBeginkeyPos = -1;
		int matchEndkeyPos = -1;
		boolean removeBeginElement = false;//开始key所在块是否需要移除
		boolean removeEndElement = false;//结束key所在块是否需要移除
		
		List<Integer> removeBeginRunPosList = null;
		List<Integer> removeEndRunPosList = null;
		List<Integer> rmRunPosList = null;
		for (int i = 0; i < beSize; i++) {
			eType = bodyElements.get(i).getElementType();
			if (eType.equals(BodyElementType.PARAGRAPH)) {
				xp = (XWPFParagraph) (bodyElements.get(i));
				rmRunPosList = new ArrayList<Integer>();
				removeBeginRunPosList = getKeyRunPosList(xp, beginKey);
				removeEndRunPosList = getKeyRunPosList(xp, endKey);
				int rmStartPos = -1;
				int rmEndPos = -1;
				if(!removeBeginRunPosList.isEmpty() && !removeEndRunPosList.isEmpty()){//同时匹配到开始和结束
					matchBeginkeyPos = i;
					matchEndkeyPos = i;
					rmStartPos = removeBeginRunPosList.get(0);
					rmEndPos = removeEndRunPosList.get(removeEndRunPosList.size()-1);
				}else if(!removeBeginRunPosList.isEmpty()){//仅匹配到开始
					matchBeginkeyPos = i;
					rmStartPos = removeBeginRunPosList.get(0);
					rmEndPos = xp.getRuns().size()-1;
				}else if(!removeEndRunPosList.isEmpty()){//仅匹配到结束
					matchEndkeyPos = i;
					rmStartPos = 0;
					rmEndPos = removeEndRunPosList.get(removeEndRunPosList.size()-1);
				}else{
					continue;
				}
				
				if(value){
					rmRunPosList.addAll(removeBeginRunPosList);
					rmRunPosList.addAll(removeEndRunPosList);
				}else{
					for (int j = rmStartPos; j <= rmEndPos; j++) {
						rmRunPosList.add(j);
					}
				}
				removeElement(xp, rmRunPosList);
				if (xp.getRuns().size() < 1) {// 在替换完元素后，当前块不再包含run则一并删除
					if(!removeBeginRunPosList.isEmpty()){
						removeBeginElement = true;
					}
					if(!removeEndRunPosList.isEmpty()){
						removeEndElement = true;
					}
				}
			}
			
		}
		if(matchBeginkeyPos==-1 && matchEndkeyPos == -1){
			return;
		}
		if(!(matchBeginkeyPos!=-1 && matchEndkeyPos!=-1) || matchEndkeyPos < matchBeginkeyPos){
			throw new RuntimeException(String.format("可选块[%s]配置错误,未找到成对开始，结束标签", op.getName()));
		}
		
		//移除待删除块
		List<Integer> rmvIds = new ArrayList<Integer>();
		if(!value){
			for (int i = (matchBeginkeyPos+1); i < matchEndkeyPos; i++) {
				rmvIds.add(i);
			}
		}
		if(removeBeginElement){
			rmvIds.add(matchBeginkeyPos);
		}
		if(removeEndElement && matchEndkeyPos!=matchBeginkeyPos){
			rmvIds.add(matchEndkeyPos);
		}
		removeElement(iBody, rmvIds);
	}
	
	/**
	 * 根据rmvIds移除XWPFDocument，XWPFTableCell，XWPFParagraph的元素
	 */
	public void removeElement(Object body,List<Integer> rmvIds){
		Collections.sort(rmvIds);//重新排序:从小到大
		XWPFDocument xwd = null;
		XWPFTableCell xwt = null;
		XWPFParagraph xwp = null;
		if(body instanceof XWPFDocument){
			xwd = (XWPFDocument)body;
		}
		if(body instanceof XWPFTableCell){
			xwt = (XWPFTableCell)body;
			//单元格不可以没有段落,当单元格内所有段落都不展示的时候，添加一个空段落
			if(rmvIds.size()==xwt.getParagraphs().size()){
				xwt.addParagraph();
			}
		}
		if(body instanceof XWPFParagraph){
			xwp = (XWPFParagraph)body;
		}
		
		int rmvCount = 0;
		for (Integer i : rmvIds) {
			if(xwd!=null){
				xwd.removeBodyElement(i + rmvCount); 
			}
			if(xwt!=null){
				xwt.removeParagraph(i + rmvCount);
			}
			if(xwp!=null){
				xwp.removeRun(i + rmvCount);
			}
			rmvCount--;
		}
	}
	/**
	 * 获取key所在段落run位置的pos位置列表,list元素从小到大排列;
	 * 未找到返回list为Empty
	 * @param paragraph 目标段落
	 * @param key 目标关键字
	 * @return 返回开始到结束pos列表
	 */
	public List<Integer> getKeyRunPosList(XWPFParagraph paragraph, String key){
		List<Integer> targetPosList = new ArrayList<Integer>();
		
		List<XWPFRun> runs = paragraph.getRuns();
		int size = runs.size();
		//存放每个run文本在整个paragraph下的起始索引
		int[] runTextStartIndexs = new int[size];
		StringBuffer paragraphTextSbu = new StringBuffer(); 
		XWPFRun run = null;
		String runText = "";
		for (int i = 0; i < size; i++) {
			
			run = runs.get(i);
			runText = run.toString();
			System.out.println(String.format("第[%3s]位,开始索引[%3s],长度[%3s]- %s", i,paragraphTextSbu.length(),runText.length(),runText));
			runTextStartIndexs[i] = paragraphTextSbu.length();
			paragraphTextSbu.append(runText);
		}
		int keyStartIndex = paragraphTextSbu.indexOf(key);
		System.out.println(paragraphTextSbu.length());
		System.out.println(paragraphTextSbu);
		if(keyStartIndex > -1){
			int keyEndIndex = keyStartIndex + key.length()-1;
			System.out.println(String.format("key[%s]所在位置：开始索引[%3s],长度[%3s],结束索引[%3s]", key,keyStartIndex,key.length(),keyEndIndex));
			int start = 0;
			int end = 0;
			for (int i = 0; i < runTextStartIndexs.length; i++) {
				start = runTextStartIndexs[i];
				if(i!=(runTextStartIndexs.length-1)){
					end =  runTextStartIndexs[i+1]-1; 
				}else{
					end =  paragraphTextSbu.length()-1; 
				}
				if (start >= keyStartIndex && end <= keyEndIndex) {
					targetPosList.add(i);
				}
			}
		}
		return targetPosList;
	}

	
	@Test
	public void setRun() {
		String key = "【※A1※";
		XWPFParagraph xwpfParagraph = document.getParagraphs().get(0);
		String paragraphText = xwpfParagraph.getParagraphText();
		if (!paragraphText.contains(key)) {
			List<Integer> rmvIds = new ArrayList<Integer>();
			// 展示run 情况
			List<XWPFRun> runs = xwpfParagraph.getRuns();
			int size = runs.size();
			System.out.println(xwpfParagraph.getParagraphText());
			System.out.println("run数量 : " + size);
			for (int i = 0; i < size; i++) {
				XWPFRun xwpfRun = runs.get(i);
				int textSize = xwpfRun.getCTR().sizeOfTArray();
				StringBuffer sbu = new StringBuffer();
				for (int j = 0; j < textSize; j++) {
					sbu.append(xwpfRun.getText(j));
				}
				System.out.println(sbu.toString()+"@"+xwpfRun.getTextPosition());
			}
			for (int i = 0; i < size; i++) {
				XWPFRun xwpfRun = runs.get(i);
				xwpfRun.setText("【"+i+"】",xwpfRun.getTextPosition());
				System.out.println(runs.size()+"||"+xwpfParagraph.getRuns().size());
			}
			
			
			//测试设置null移除   不会删除[xwpfRun.addBreak();添加的]换行符号;
//			for (XWPFRun xwpfRun : runs) {
//				xwpfRun.setText(null,0);//
//			}
			
			//外部方式移除
//			for (int i = 0; i < size; i++) {
//				rmvIds.add(i);
//			}
//			int rmvCount = 0;
//			for (Integer pos : rmvIds) {
//				xwpfParagraph.removeRun(pos + rmvCount);
//				rmvCount--;
//			}
		}
	}

	/**
	 * 删除run元素测试
	 */
	@Test
	public void removeRun() {

	}

	
	@Test
	public void testOptionParameter() {
		OptionParameter op = new OptionParameter("测试", true);
		System.out.println(op.getBeginKey());
		System.out.println(op.getEndKey());
	}
	
	@Test
	public void sortTest(){
		List<Integer> rmvIds = new ArrayList<Integer>();
		rmvIds.add(10);
		rmvIds.add(3);
		rmvIds.add(4);
		rmvIds.add(2);
		rmvIds.add(0);
		System.out.println(rmvIds);
		Collections.sort(rmvIds);//重新排序:从小到大
		System.out.println(rmvIds);
	}
}
