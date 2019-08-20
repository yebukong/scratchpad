package pers.mine.scratchpad.util;

import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

/**
 * ICal4j 日历文本生成工具
 * 
 * @author mine
 * @since 2018年8月27日
 */
public class ICalHelper {
	/**
	 * 依据会议邀请信息生成标准格式的字符串
	 * 
	 * @param startTime      开始时间
	 * @param endTime        结束时间
	 * @param title          会议名称
	 * @param organizerName  组织者名称
	 * @param organizerEmail 组织者Email
	 * @param location       地点
	 * @param description    描述
	 * @param attendees      参与者列表
	 * @param valarm         提醒
	 * @return
	 */
	public static String getICalText(Date startTime, Date endTime, String title, String organizerName,
			String organizerEmail, String location, String description, List<Attendee> attendees, VAlarm valarm) {
		if (startTime != null && endTime != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String begin = format.format(startTime);
			String over = format.format(endTime);
			return getICalText(begin, over, title, organizerName, organizerEmail, location, description, attendees,
					valarm);
		}
		return "";

	}

	/**
	 * 依据会议邀请信息生成标准格式的字符串
	 * 
	 * @param begin          开始时间 -格式:yyyy-MM-dd-HH-mm-ss
	 * @param over           结束时间 -格式:yyyy-MM-dd-HH-mm-ss
	 * @param title          会议名称
	 * @param organizerName  组织者名称
	 * @param organizerEmail 组织者Email
	 * @param location       地点
	 * @param description    描述
	 * @param attendees      参与者列表
	 * @param valarm         提醒
	 * @return
	 */
	public static String getICalText(String begin, String over, String title, String organizerName,
			String organizerEmail, String location, String description, List<Attendee> attendees, VAlarm valarm) {
		if (!StringX.isEmpty(begin) && !StringX.isEmpty(over) && !StringX.isEmpty(organizerEmail)) {
			try {
//				Item item = CodeManager.getItem("UnShowConfig", "pacas.checkSecurity");
//				String icalVTimeZoneStr = item.getAttribute1();//日历时区
//				String meetVTimeZoneStr = item.getAttribute2();//会议时区
//				String aimsTimezoneStr = item.getAttribute3();//aims转会议时间时区
//				String meetDateUTCStr = item.getAttribute4();//会议时间时区

				String icalVTimeZoneStr = "Asia/Shanghai";// 日历时区
				String meetVTimeZoneStr = "Asia/Shanghai";// 会议时区
				String aimsTimezoneStr = "Asia/Shanghai";// aims转会议时间时区
				String meetDateUTCStr = "UTC";// 会议时间时区
				// 创建一个时区（TimeZone）
				TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();

				// aims转会议时间时区
				if (StringX.isEmpty(aimsTimezoneStr)) {
					aimsTimezoneStr = "Asia/Shanghai";
				}
				TimeZone aimsTimezone = registry.getTimeZone(aimsTimezoneStr);
				// 会议起始时间[]
				java.util.Calendar startDate = java.util.Calendar.getInstance(aimsTimezone);
				String[] beginArr = begin.split("-");
				startDate.set(java.util.Calendar.MONTH, Integer.parseInt(beginArr[1].trim()) - 1);// 月份从0开始
				startDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(beginArr[2].trim()));
				startDate.set(java.util.Calendar.YEAR, Integer.parseInt(beginArr[0].trim()));
				startDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(beginArr[3].trim()));
				startDate.set(java.util.Calendar.MINUTE, Integer.parseInt(beginArr[4].trim()));
				startDate.set(java.util.Calendar.SECOND, Integer.parseInt(beginArr[5].trim()));
				// 结束时间
				java.util.Calendar endDate = java.util.Calendar.getInstance(aimsTimezone);
				String[] overArr = over.split("-");
				endDate.set(java.util.Calendar.MONTH, Integer.parseInt(overArr[1].trim()) - 1);
				endDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(overArr[2].trim()));
				endDate.set(java.util.Calendar.YEAR, Integer.parseInt(overArr[0].trim()));
				endDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(overArr[3].trim()));
				endDate.set(java.util.Calendar.MINUTE, Integer.parseInt(overArr[4].trim()));
				endDate.set(java.util.Calendar.SECOND, Integer.parseInt(overArr[5].trim()));
				// 创建事件
				DateTime start = new DateTime(startDate.getTime());
				DateTime end = new DateTime(endDate.getTime());

				// 会议开始/结束时间时区
				if (!StringX.isEmpty(meetDateUTCStr)) {
					if ("UTC".equalsIgnoreCase(meetDateUTCStr)) {
						start.setUtc(true);
						end.setUtc(true);
					} else {
						TimeZone meetDateTZ = registry.getTimeZone(meetDateUTCStr);
						start.setTimeZone(meetDateTZ);
						end.setTimeZone(meetDateTZ);
					}
				}

				String eventTitle = StringX.nvl(title, "");
				VEvent meeting = new VEvent(start, end, eventTitle);
				// 会议添加时区
				if (!StringX.isEmpty(meetVTimeZoneStr)) {
					TimeZone meetVTimeZone = registry.getTimeZone(meetVTimeZoneStr);
					meeting.getProperties().add(meetVTimeZone.getVTimeZone().getTimeZoneId());
				}
				// 生成唯一标志符
				UidGenerator ug = new UidGenerator("uidGen");
				Uid uid = ug.generateUid();
				// Uid uid = new Uid(UUID.randomUUID().toString());
				meeting.getProperties().add(uid);
				// 组织者
				Organizer organizer = new Organizer(URI.create("mailto:" + organizerEmail));
				organizer.getParameters().add(new Cn(organizerName));
				meeting.getProperties().add(organizer);
				if (!StringX.isEmpty(location)) {
					meeting.getProperties().add(new Location(location));
				}
				if (!StringX.isEmpty(description)) {
					meeting.getProperties().add(new net.fortuna.ical4j.model.property.Description(description));
				}
				// 添加参加者 .
				for (Attendee attendee : attendees) {
					meeting.getProperties().add(attendee);
				}

				if (valarm != null) {
					meeting.getAlarms().add(valarm);// 将VAlarm加入VEvent
				}
				// 创建日历
				Calendar icsCalendar = new Calendar();
				icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
				icsCalendar.getProperties().add(Version.VERSION_2_0);
				icsCalendar.getProperties().add(Method.REQUEST);
				icsCalendar.getProperties().add(CalScale.GREGORIAN);

				// 日历对象添加时区设置
				if (!StringX.isEmpty(icalVTimeZoneStr)) {
					TimeZone icalVTimeZone = registry.getTimeZone(icalVTimeZoneStr);
					icsCalendar.getComponents().add(icalVTimeZone.getVTimeZone());
				}
				// 添加事件
				icsCalendar.getComponents().add(meeting);
				icsCalendar.validate();// 验证
				CalendarOutputter outputter = new CalendarOutputter();
				Writer writer = new StringWriter();
				outputter.output(icsCalendar, writer);
				return writer.toString();
			} catch (Exception e) {
				System.out.println("生成ICal日历异常");
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 生成日历所需的出席人Attendee
	 * 
	 * @param name  出席人员名称
	 * @param email 出席人员邮箱地址
	 * @param role  出席角色
	 *              <li>Role.REQ_PARTICIPANT 必须参会人员
	 *              <li>Role.OPT_PARTICIPANT 可选
	 *              <li>Role.NON_PARTICIPANT 非参会人员 outlook展示为可选
	 * @param rsvp  是否要求对会议邀请做出响应
	 *              <li>Rsvp.TRUE 是
	 *              <li>Rsvp.FALSE 否
	 * @return
	 */
	public static Attendee createAttendee(String name, String email, Role role, Rsvp rsvp) {
		if (role == null) {// 缺省可选
			role = Role.OPT_PARTICIPANT;
		}
		if (rsvp == null) {// 缺省无需响应
			rsvp = Rsvp.FALSE;
		}
		Attendee dev = new Attendee(URI.create("mailto:" + email));
		dev.getParameters().add(new Cn(name));
		dev.getParameters().add(role);
		dev.getParameters().add(rsvp);
		// PartStat状态 此属性在发送邀请阶段应该无需设置
		// dev.getParameters().add(PartStat.TENTATIVE);
		return dev;
	}

	/**
	 * 生成日历所需的提醒VAlarm
	 * 
	 * @param triggerTime  会议开始前几分钟提醒范围：-60 ~ 0
	 * @param durationTime 持续时间 0 ~ 60
	 * @param repeat       重复次数
	 * @param summary      摘要
	 * @param desc         详细
	 * @return
	 */
	public static VAlarm createVAlarm(int triggerTime, int durationTime, int repeat, String summary, String desc) {
		VAlarm valarm = new VAlarm(new Dur(0, 0, triggerTime, 0));// 提前XXX分钟
		valarm.getProperties().add(new Repeat(repeat));// 重复
		valarm.getProperties().add(new Duration(new Dur(0, 0, durationTime, 0)));// 持续时长
		// 提醒窗口显示的文字信息：outlook没有起效果
		valarm.getProperties().add(new Summary(StringX.nvl(summary, "")));
		valarm.getProperties().add(Action.DISPLAY);
		valarm.getProperties().add(new Description(StringX.nvl(desc, "")));
		return valarm;
	}

	/**
	 * 生成一个默认提前15分钟提醒，持续5分钟，循环1次的提醒
	 */
	public static VAlarm createDefaultVAlarm() {
		return createVAlarm(-15, 5, 1, "", "");
	}
//	//发送普通邮件
//			private void sendHtmlEmail(MessageExt m){
//				HtmlEmail email = new HtmlEmail();
//			    email.setHostName(InterfaceConfig.getMailConfigItem("mail.hostName"));
//			    email.setSmtpPort(Integer.parseInt(InterfaceConfig.getMailConfigItem("mail.smtpPort")));
//			    email.setAuthentication(InterfaceConfig.getMailConfigItem("mail.authenticationUser"),
//			    		InterfaceConfig.getMailConfigItem("mail.authenticationPass"));
//			    
//			    try {
//					email.addTo(getRecipientMailAddress(message.getRecipients()));
//					email.setFrom(InterfaceConfig.getMailConfigItem("mail.defaultMailForm"));
//				    email.setSubject(defaultSubjectHeader+m.getSubject());
//				    email.setHtmlMsg(m.getBody());
//				    String fileIDs = m.getAttachFileId();
//				    for(String fileID : fileIDs.split("@"))
//					    if(!StringX.isEmpty(fileID)){
//							email.attach(saveFileToDisk(fileID));
//					    }
//				    
//				    String sendResult = email.send();
//				    ARE.getLog().info("MAIL_SUCESS:"+sendResult);
//				} catch (EmailException e) {
//					String jboClass = "jbo.aims.MESSAGE_RECORD";
//					BizObject record = JBOHelper.querySingle(jboClass, "MSGNo=:MSGNo", m.getId());
//					JBOHelper.getAttribute(record, "MSGSTATUS").setValue("20");
//					JBOHelper.saveBizObject(jboClass, record);
//					
//					ARE.getLog().error("MAIL_FAIL:",e);
//				}			
//			}
//	//发送会议邮件
//	private void sendICalEmail(MessageExt m,String iCalText){
//		iCalText = StringHelper.nvl(iCalText, "");
//		MultiPartEmail email = new MultiPartEmail();
//		email.setHostName(InterfaceConfig.getMailConfigItem("mail.hostName"));
//		email.setSmtpPort(Integer.parseInt(InterfaceConfig.getMailConfigItem("mail.smtpPort")));
//		email.setAuthentication(InterfaceConfig.getMailConfigItem("mail.authenticationUser"),
//		InterfaceConfig.getMailConfigItem("mail.authenticationPass"));
//		
//		try {
//			email.addTo(getRecipientMailAddress(message.getRecipients()));
//			email.setFrom(InterfaceConfig.getMailConfigItem("mail.defaultMailForm"));
//			email.setSubject(defaultSubjectHeader+m.getSubject());
//			MimeMultipart multipart = new MimeMultipart("mixed");
//			//正文
//			MimeBodyPart content = new MimeBodyPart();
//	    	content.setContent(m.getBody(),"text/html;charset=UTF-8");
//	    	multipart.addBodyPart(content);
//	    	//日历附件
//	    	MimeBodyPart ical = new MimeBodyPart();
//	    	ical.setContent(iCalText, "text/calendar; method=REQUEST; charset=UTF-8" );
//	        ical.setDataHandler(new DataHandler(new ByteArrayDataSource(iCalText,
//	               "text/calendar;method=REQUEST;charset=UTF-8")));
//	        multipart.addBodyPart(ical);
//	        //附件
//	        String fileIDs = m.getAttachFileId();
//	        MimeBodyPart attach= null;
//	        DataHandler dh = null;
//		    for(String fileID : fileIDs.split("@")){
//			    if(!StringX.isEmpty(fileID)){
//			    	attach = new MimeBodyPart();
//			    	dh = new DataHandler(new FileDataSource(saveFileToDisk(fileID)));
//			    	attach.setDataHandler(dh);
//			        attach.setFileName(MimeUtility.encodeWord(dh.getName()));
//			        attach.setDisposition(EmailAttachment.ATTACHMENT); 
//			        multipart.addBodyPart(attach);
//			    }
//		    }
//            email.setContent(multipart);
//            String sendResult = email.send();
//            ARE.getLog().info("MAIL_SUCESS:"+sendResult);
//		} catch (Exception e) {
//			String jboClass = "jbo.aims.MESSAGE_RECORD";
//			BizObject record = JBOHelper.querySingle(jboClass, "MSGNo=:MSGNo", m.getId());
//			JBOHelper.getAttribute(record, "MSGSTATUS").setValue("20");
//			JBOHelper.saveBizObject(jboClass, record);
//			
//			ARE.getLog().error("MAIL_FAIL[会议邮件]:",e);
//		}
//	}
//}
//
//
//
//	/**
//	 * 附件信息
//	 * @param fileId
//	 * @return
//	 * @throws EmailException 
//	 */
//	private File saveFileToDisk(String fileId) throws EmailException{
//		File file = null;
//		File dir = new File(TMP_FILE_DIR);
//		if(!dir.exists())dir.mkdirs();
//		
//	    DocumentManager docManager = DocumentManagerFactory.getDefaultDocumentManager();
//	    InputStream inputStream = null;
//	    try {
//	    	DocumentFile docFile = docManager.findDocumentFile(fileId);
//			inputStream = docManager.getDocumentFileEvent().getInputStream(docManager, docFile);
//			
//			file = new File(dir.getAbsolutePath()+"/"+UUID.getRandomID()+docFile.getFileName());
//			FileTool.writeFile(inputStream, file, true);
//		} catch (DocumentException e) {
//			throw new EmailException("MAIL_FAIL:附件写入错误！");
//		} catch (IOException e) {
//			throw new EmailException("MAIL_FAIL:附件写入错误！");
//		}finally{
//			if(inputStream!=null){
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					ARE.getLog().error("MAIL_FAIL:附件写入错误！",e);
//				}
//			}
//		}
//	    return file;
//	}
}