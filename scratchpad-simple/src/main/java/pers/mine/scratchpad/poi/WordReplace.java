package pers.mine.scratchpad.poi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.TextSegment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;


public class WordReplace {
	private InputStream inputStream = null;
	private XWPFDocument document;
	/**
     * 扩展功能支持开关
     */
    private boolean kitFlag = false;
    
	/**
	 * 获取扩展功能开关状态
	 */
	public boolean getKitFlag() {
        return kitFlag;
    }

    /**
     * 扩展功能支持开关 设置
     */
    public void setKitFlag(boolean kitFlag) {
        this.kitFlag = kitFlag;
    }

    /**
	 * 构建一个WordReplace对象，用于使用一个特定的模板进行替换操作
	 * @param inputStream 模板对象输入流
	 */
	public WordReplace(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * 执行替换
	 * @param parameter
	 * @throws WordReplaceException
	 */
	public void replace(WordParameterSet parameter) throws WordReplaceException{
		createAndReplaceDocument(parameter);
	}
	
   
	/**
	 * 对Document对象进行替换操作
	 * @param parameter
	 * @return
	 */
	protected void createAndReplaceDocument(WordParameterSet parameter) throws WordReplaceException{
			try {
				document = new XWPFDocument(inputStream);
				if (parameter != null && parameter.size() > 0) {
                    if (kitFlag) {
                        // 处理正文的可选元素
                        processOptionElement(parameter);
                    }
					//处理页眉页脚
					replaceHeaderAndFotter(parameter);
					//处理段落
					List<XWPFParagraph> paragraphList = document.getParagraphs();
					processParagraphs(paragraphList, parameter, document);
					
					//处理表格
					List<XWPFTable> tableList = document.getTables();
					for(int i=0;i<tableList.size();i++){
						XWPFTable table = tableList.get(i);
						List<XWPFTableRow> rows = table.getRows();
						for (int m=0;m<rows.size();m++) {
							XWPFTableRow row = rows.get(m);
							List<XWPFTableCell> cells = row.getTableCells();
							for (int j=0;j<cells.size();j++) {
								XWPFTableCell cell = cells.get(j);
								if(kitFlag){
								  //处理单元格内部的可选可选元素
	                              processOptionElement(cell,parameter); 
								}
                                List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
								processParagraphs(paragraphListTable, parameter, document);
							}
						}
					}
					
					//表格替换
					processTableReplace(parameter);
					
				}
			} catch (IOException e) {
				throw new WordReplaceException(e);
			}
//			processParagraph();
	}
	
	/**
	 * 处理单元格内可选块
	 * @param cell
	 * @param parameter
	 */
	private void processOptionElement(XWPFTableCell cell,
			WordParameterSet parameter) {
		List<WordParameter<?>> parameters = parameter.getParameters();
		for (WordParameter<?> wp : parameters) {
			if (wp instanceof OptionParameter) {
				processSingleOptionElement(cell, (OptionParameter)wp);
			}else{
				continue;
			}
		}
	}

	/**
	 *  处理正文可选块
	 * @param parameter
	 */
	private void processOptionElement(WordParameterSet parameter) {
		List<WordParameter<?>> parameters = parameter.getParameters();
		for (WordParameter<?> wp : parameters) {
			if (wp instanceof OptionParameter) {
				processSingleOptionElement(document, (OptionParameter)wp);
			}else{
				continue;
			}
		}
	}

	/**
	 * 单个可选块参数处理
	 */
	private void processSingleOptionElement(IBody iBody,
			OptionParameter op){
	    
		List<? extends IBodyElement> bodyElements = iBody.getBodyElements();
		if(iBody instanceof XWPFTableCell) {
			bodyElements = iBody.getParagraphs();
		}
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
				//TextSegment searchText = xp.searchText(null, null);
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
			runTextStartIndexs[i] = paragraphTextSbu.length();
			paragraphTextSbu.append(runText);
		}
		int keyStartIndex = paragraphTextSbu.indexOf(key);
		if(keyStartIndex > -1){
			int keyEndIndex = keyStartIndex + key.length()-1;
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
	
	private void replaceHeaderAndFotter(WordParameterSet parameter) throws WordReplaceException{
		 List<XWPFHeader> headers = document.getHeaderList();
		 List<XWPFFooter>  fotters = document.getFooterList();
		 for(XWPFHeader header:headers){
			 processParagraphs(header.getParagraphs(), parameter, document);
		 }
		 for(XWPFFooter footer:fotters){
			 processParagraphs(footer.getParagraphs(), parameter, document);
		 }
	}
	
//	protected void processParagraph(){
//		try {
//			FileOutputStream fos = new FileOutputStream("C:/aaa.docx");
//			document.write(fos);
//			fos.close();
//			FileInputStream fis = new FileInputStream("C:/aaa.docx");
//			HWPFDocument doc = new HWPFDocument(fis);
//			
//			Range range = doc.getRange();
//			range.replaceText("<p>", "\r");
//			fos = new FileOutputStream("C:/aaa.docx");
//			doc.write(fos);
//			fis.close();
//			fos.close();
//			document = new WordReplaceXWPFDocument(new FileInputStream("C:/aaa.docx"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 处理表格替换<表格替换，非表格内部替换，是整个表格替换功能>
	 */
	private void processTableReplace(WordParameterSet parameter){
		//replaceTable(document,parameter);
	}
	
	
	/**
	 * 获取表格第一行第一列单元格的值
	 * @param table
	 * @return
	 */
	private String getFirstCellValue(XWPFTable table){
		String textValue = null;
		if(table==null||table.getRows().size()<1)return textValue;
		XWPFTableRow row = table.getRow(0);
		if(row==null||row.getTableCells().size()<1)return textValue;
		XWPFTableCell cell = row.getCell(0);
		textValue = cell.getText();
		return textValue;
	}

	/**
	 * 处理段落
	 * @param paragraphList
	 */
	public void processParagraphs(List<XWPFParagraph> paragraphList,WordParameterSet parameter,XWPFDocument doc) throws WordReplaceException{
		if(paragraphList != null && paragraphList.size() > 0){
			for(int i=0;i<paragraphList.size();i++){
				XWPFParagraph paragraph = paragraphList.get(i);
				CTR rArray = paragraph.getCTP().getRArray(0);
				rArray.toString();
				String paragraphText = paragraph.getParagraphText();
				List<String> keyNames = parameter.getAllNames();
				for (int j=0;j<keyNames.size();j++) {
					String key = keyNames.get(j);
					WordParameter<?> value = parameter.getParameter(key);
					if(paragraphText.indexOf(key)!=-1){
//						System.out.println("KV-->"+key+"="+value+",paragraph:"+paragraphText);
					    if(kitFlag){
					        paragraphReplaceKit(doc,paragraph,key,value);
					    }else{
					        paragraphReplace(doc,paragraph,key,value);
					    }
						
					}
				}
			}
		}
	}
	
	/**
	 * 段落替换功能:修复多个开头相同的关键字在同一段落无法顺利替换的问题
	 * @param paragraph
	 * @param key
	 * @param value
	 */
	private void paragraphReplaceKit(XWPFDocument doc,XWPFParagraph paragraph,String key,WordParameter<?> value) throws WordReplaceException{
		if(paragraph.isEmpty())return;
		List<XWPFRun> runList = paragraph.getRuns();
		XWPFRun firstRun = null;
		StringBuffer sbTmp = new StringBuffer();
		List<Integer> rmvIdx = new ArrayList<Integer>();
		boolean doReplace = false;
		List<Integer> tempRmvIdx = null;
		for(int i=0;i<runList.size();i++){
			XWPFRun run = runList.get(i);
			String runText = run.toString();
			if(runText==null)continue;
			
			//--------------------------------------------------------------------------------------------------
			//1.由于word的某种原因，导致一个单词被拆分到多个块中(Word2010有这个问题），那么需要对多个块进行拼接后替换
			//--------------------------------------------------------------------------------------------------
			if(firstRun==null&&key.startsWith(runText)&&!key.equals(runText)){			//[A]:::开始位置的处理
				sbTmp = new StringBuffer();
				firstRun = run;
				sbTmp.append(runText);
				tempRmvIdx = new ArrayList<Integer>();
				continue;
			}
			if(firstRun!=null){															//[B]:::中间位置的处理
				if(key.indexOf(runText)>-1){
					sbTmp.append(runText);
					tempRmvIdx.add(i);//记下需要移除的Run块
				}else{			//如果不连续，则放弃
					firstRun = null;
					tempRmvIdx = null;
				}
			}
			if(firstRun!=null&&key.endsWith(runText)&&key.equals(sbTmp.toString())){	//[C]:::末尾位置的处理
				doReplace = execTextReplace(doc,paragraph,firstRun,sbTmp.toString(),key,value);
				rmvIdx.addAll(tempRmvIdx);
				tempRmvIdx.clear();
			}
			
			//----------------------------------------------------
			//2.在一个小块内，本身就存在当前变量的情况，这种模式最简单，直接替换即可。
			//----------------------------------------------------
			if(firstRun==null&&runText.indexOf(key)>-1){
				doReplace = execTextReplace(doc,paragraph,run,runText,key,value);
			}
		}
		
		if(doReplace){
			int rmvCount = 0;
			for(int p:rmvIdx) {
				paragraph.removeRun(p+rmvCount);	//由于需要合并到第一个小块内，所以需要把当前Run移除掉
				rmvCount --;
			}
		}
		rmvIdx.clear();
	}
	
	/**
     * 段落替换功能
     * @param paragraph
     * @param key
     * @param value
     */
    private void paragraphReplace(XWPFDocument doc,XWPFParagraph paragraph,String key,WordParameter<?> value) throws WordReplaceException{
        if(paragraph.isEmpty())return;
        List<XWPFRun> runList = paragraph.getRuns();
        XWPFRun firstRun = null;
        StringBuffer sbTmp = new StringBuffer();
        List<Integer> rmvIdx = new ArrayList<Integer>();
        boolean doReplace = false;
        for(int i=0;i<runList.size();i++){
            XWPFRun run = runList.get(i);
            String runText = run.getText(0);
//          System.out.println("RunText===>"+runText);
            if(runText==null)continue;
            
            //--------------------------------------------------------------------------------------------------
            //1.由于word的某种原因，导致一个单词被拆分到多个块中(Word2010有这个问题），那么需要对多个块进行拼接后替换
            //--------------------------------------------------------------------------------------------------
            if(firstRun==null&&key.startsWith(runText)&&!key.equals(runText)){          //[A]:::开始位置的处理
                sbTmp = new StringBuffer();
                firstRun = run;
                sbTmp.append(runText);
                continue;
            }
            if(firstRun!=null){                                                         //[B]:::中间位置的处理
                if(key.indexOf(runText)>-1){
                    sbTmp.append(runText);
                    rmvIdx.add(i);//记下需要移除的Run块
                }else{          //如果不连续，则放弃
                    firstRun = null;
                }
            }
            if(firstRun!=null&&key.endsWith(runText)&&key.equals(sbTmp.toString())){    //[C]:::末尾位置的处理
                doReplace = execTextReplace(doc,paragraph,firstRun,sbTmp.toString(),key,value);
            }
            
            //----------------------------------------------------
            //2.在一个小块内，本身就存在当前变量的情况，这种模式最简单，直接替换即可。
            //----------------------------------------------------
            if(firstRun==null&&runText.indexOf(key)>-1){
                doReplace = execTextReplace(doc,paragraph,run,runText,key,value);
            }
        }
        
        if(doReplace){
            int rmvCount = 0;
            for(int p:rmvIdx) {
    //          System.out.println("RMV-----:"+paragraph.getRuns().get(p));
                paragraph.removeRun(p+rmvCount);    //由于需要合并到第一个小块内，所以需要把当前Run移除掉
                rmvCount --;
            }
        }
        rmvIdx.clear();
    }
    
	private boolean execTextReplace(XWPFDocument doc,XWPFParagraph paragraph,XWPFRun run,String runText,String key,WordParameter<?> value) throws WordReplaceException{
		String rt = replaceValue(doc,paragraph,runText,key,value);
		//对于文本段落进行处理
		if(rt!=null&&!runText.equals(rt)){
			String[] ss = rt.split("\r\n");
			for(int i=0; i< ss.length; i++){
				if(i==0){
					run.setText(ss[i],0);
					run.setColor("FF0000");
				}else{				
					run.addBreak();
					run.setText(ss[i],run.getTextPosition());
					run.setColor("FF0000");
				}
			}
			return true;
		}
		return false;
	}
	
	private void copyStyle(XWPFParagraph refParagraph,XWPFParagraph paragraph,XWPFRun refRun,XWPFRun run){
		paragraph.setAlignment(refParagraph.getAlignment());
		paragraph.setVerticalAlignment(refParagraph.getVerticalAlignment());
		paragraph.setIndentationFirstLine(refParagraph.getIndentationFirstLine());
		paragraph.setIndentationHanging(refParagraph.getIndentationHanging());
		paragraph.setIndentationLeft(refParagraph.getIndentationLeft());
		paragraph.setIndentationRight(refParagraph.getIndentationRight());
		paragraph.setPageBreak(refParagraph.isPageBreak());
		paragraph.setStyle(refParagraph.getStyle());
		paragraph.setPageBreak(refParagraph.isPageBreak());
//		paragraph.setWordWrap(refParagraph.isWordWrap());
//		paragraph.setSpacingBeforeLines(refParagraph.getSpacingBeforeLines());
//		paragraph.setSpacingAfterLines(refParagraph.getSpacingAfterLines());
//		paragraph.setSpacingBefore(refParagraph.getSpacingBefore());
//		paragraph.setSpacingAfter(refParagraph.getSpacingAfter());
		paragraph.setBorderTop(refParagraph.getBorderTop());
		paragraph.setBorderRight(refParagraph.getBorderRight());
		paragraph.setBorderBottom(refParagraph.getBorderBottom());
		paragraph.setBorderLeft(refParagraph.getBorderLeft());
		paragraph.setBorderBetween(refParagraph.getBorderBetween());
		
		if(run!=null){
			run.setBold(refRun.isBold());
			run.setItalic(refRun.isItalic());
			run.setFontFamily(refRun.getFontFamily());
			run.setColor(refRun.getColor());
			run.setFontSize(refRun.getFontSize());
			run.setStrike(refRun.isStrike());
			run.setUnderline(refRun.getUnderline());
		}
	}
	
	private String replaceValue(XWPFDocument doc,XWPFParagraph paragraph,String text,String key,WordParameter<?> value) throws WordReplaceException{
		if (value instanceof StringParameter) {			//字串或数字
			text = text.replace(key, value.getStringValue());
			//原处理段落的方式，因在表格中会造成生成之后模板出错，已停用
//			System.out.println("Replace:"+key+"="+text);
//			String[] ss = text.split("\r\n");
//			//处理新段落问题
//			XWPFParagraph refParagraph = paragraph;
//			for(int i=0;i<ss.length;i++){
//				if(i==0){
//					text = ss[i];
//				}else{
//					int newPIdx = doc.getParagraphs().indexOf(refParagraph)+1;
//					List<CTP> pList = doc.getDocument().getBody().getPList();
//					newPIdx = Math.min(newPIdx, pList.size());
//					
//					//System.out.println("NewPra:"+ss[i]+","+newPIdx+"<>"+pList.size()+","+doc.getParagraphs().indexOf(refParagraph));
//					if(newPIdx<pList.size()){
//						XmlCursor cursor = pList.get(newPIdx).newCursor();
//						XWPFParagraph p = doc.insertNewParagraph(cursor);
//						XWPFRun r = p.createRun();
//						r.setText(ss[i]);
//						copyStyle(paragraph,p,paragraph.getRuns().get(0),r);
//						refParagraph = p;
//					}
//					//如果是最后一段，特殊处理
//					if(newPIdx==pList.size()){
//						XmlCursor cursor = pList.get(pList.size()-1).newCursor();
//						XWPFParagraph p = doc.insertNewParagraph(cursor);
//						XWPFRun r = p.createRun();
//						r.setText(ss[i]);
//						copyStyle(paragraph,p,paragraph.getRuns().get(0),r);
//						refParagraph = p;
//					}
//				}
//			}
		}else{
//			text = text.replace(key, value.getStringValue());
//			System.out.println(key+"------>"+value.getStringValue());
			text = null;
		}
		return text;
	}
	
	/**
	 * 输出内容到输出流中
	 * @param outputStream
	 */
	public void write(OutputStream outputStream) throws WordReplaceException{
		try {
			document.write(outputStream);
		} catch (IOException e) {
			throw new WordReplaceException(e);
		}
	}
	
	
	
}
