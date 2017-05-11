package com.kintiger.platform.kunnr.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.kintiger.platform.allUser.pojo.AllUsers;
import com.kintiger.platform.base.action.BaseAction;
import com.kintiger.platform.base.pojo.BooleanResult;
import com.kintiger.platform.base.pojo.StringResult;
import com.kintiger.platform.channel.pojo.Bchannel;
import com.kintiger.platform.channel.service.AllIChannelService;
import com.kintiger.platform.channel.service.IChannelService;
import com.kintiger.platform.crmdict.pojo.CrmDict;
import com.kintiger.platform.crmdict.service.ICrmDictService;
import com.kintiger.platform.framework.annotations.Decode;
import com.kintiger.platform.framework.annotations.JsonResult;
import com.kintiger.platform.framework.annotations.PermissionSearch;
import com.kintiger.platform.framework.util.DateUtil;
import com.kintiger.platform.framework.util.ExcelUtil;
import com.kintiger.platform.framework.util.FileUtil;
import com.kintiger.platform.framework.util.JavaBeanXMLUtil;
import com.kintiger.platform.framework.util.SuperCSV;
import com.kintiger.platform.framework.util.XMLInfo;
import com.kintiger.platform.goal.pojo.BCustomerTarget;
import com.kintiger.platform.goal.service.IGoalService;
import com.kintiger.platform.kunnr.pojo.Kunnr;
import com.kintiger.platform.kunnr.pojo.KunnrAcount;
import com.kintiger.platform.kunnr.pojo.KunnrAddress;
import com.kintiger.platform.kunnr.pojo.KunnrApplySave;
import com.kintiger.platform.kunnr.pojo.KunnrBrand;
import com.kintiger.platform.kunnr.pojo.KunnrBusiness;
import com.kintiger.platform.kunnr.pojo.KunnrLicense;
import com.kintiger.platform.kunnr.pojo.KunnrLogisticsArea;
import com.kintiger.platform.kunnr.pojo.KunnrSalesArea;
import com.kintiger.platform.kunnr.pojo.KunnrSapCodeObject;
import com.kintiger.platform.kunnr.service.IKunnrService;
import com.kintiger.platform.master.pojo.Materiel;
import com.kintiger.platform.org.pojo.Borg;
import com.kintiger.platform.org.service.IOrgService;
import com.kintiger.platform.stockReport.pojo.Goal;
import com.kintiger.platform.stockReport.service.IStockReportService;
import com.kintiger.platform.wfe.pojo.ProEventDetail;
import com.kintiger.platform.wfe.pojo.ProEventTotal;
import com.kintiger.platform.wfe.pojo.UserUtil;
import com.kintiger.platform.wfe.service.IEventService;
import com.kintiger.platform.wfe.service.IWfeService;

public class KunnrAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -638516699213585547L;
	private static Log logger = LogFactory.getLog(KunnrAction.class);
	private static String key4open = "fix_dealerAccount1";
	private static String key4update = "fix_dealerModified";
	private static String key4freeze = "fix_dealerOffHouseholdsNew01";//�ػ�modle_id
	private static String key4close = "fix_dealerOffHouseholdsNew01";// fix_dealerOffHouseholds
	private String type; // �ػ������Ƿ�������֪ͨ
	private static String customerManagement = "customerManagement"; //  ��ɫ�����ܲ��ͻ�������
	private IWfeService wfeServiceHessian; // ���̽ӿ�
	private IOrgService orgServiceHessian; // ����֯�ӿ�
	private IKunnrService kunnrService;
	private IStockReportService stockReportService;
	private String appUrl;
	private Kunnr kunnr;
	private String kunnrId;// kunnrSAP����
	private String[] kunnrs;
	private Long channelId;
	private String status;
	private @Decode
	String businessManager;
	private @Decode
	String businessCompetent;
	private KunnrBusiness kunnrBusiness;
	private KunnrSalesArea kunnrSalesArea;
	private BCustomerTarget bCustomerTarget;
	private List<Kunnr> kunnrList;
	private List<KunnrBusiness> kunnrBusinessList;
	private List<KunnrSalesArea> kunnrSalesAreaList;
	private List<KunnrAddress> kunnrAddressList;
	private List<KunnrBrand> kunnrBrandList;
	private List<KunnrAcount> kunnrAcountList;
	private List<KunnrLicense> kunnrLicenseList;
	private List<BCustomerTarget> bCustomerTargetList;
	private List<Goal> bSalesTargetList;
	
	private String brand;
	private String brands;
	
	private int addressListSize;
	private int brandListSize;
	private int acountListSize;
	private int licenseListSize;
	private int kunnrSalesAreaListSize;
	private int bCustomerTargetListSize;
	private int bSalesTargetListSize;

	private UserUtil userUtil; // �¸��������б�
	private String userId;
	private String nextUserId;// �¸�������Id
	private String eventId;
	private String title;
	private String xmlFilePath;
	private File[] upload;
	private String[] uploadFileName;
	private StringResult stringResult;
	private String subFolders;
	private int total = 0;
	private String attachmentsName;// ����Name
	private String attachmentsPath;// ����·��
	private IChannelService channelService;
	private AllIChannelService allchannelService;
	private List<Bchannel> channelList;// �ͻ�����,����
	private String opera;
	private @Decode
	String name1;
	private String freezeOrClose;// ���� �رչ��ñ�־
	private BooleanResult executeResult;// ����������������Ϣ
	private String kunnrCode;// kunnrcode
	private ICrmDictService crmdictService;
	private @Decode
	String dictTypeName;//�ֵ��������� ����˰������
	private List<CrmDict> dictList;
	private String[] licenseName;
	private File[] license;
	private String[] licenseFileName;
	private Date[] licenseValid;
	private @Decode
	String dictTypeValue;
	private int num;
	private String orgId;
	@Decode
	private String orgName;
	private String orgIds;

	private List<KunnrLogisticsArea> lAreaList;
	private String vkgrp;
	private String bzirk;
	private String vkbur;
	private List<KunnrLogisticsArea> areaList;
	private List<KunnrLogisticsArea> areaLists;
	private IGoalService goalService;

	private String resultJson;

	private @Decode
	String channelName;
	private String updateFlag;
	private String curStaId;
	private String modifyFlag;

	private String bhxjFlag;
	private String zgFlag;
	private String uploadFileFileName;
	private File uploadFile;
	private String updateType;
	private String nameUpdateFile;
	private String nameUpdatePath;// ����·��

	private String freezeButton;
	// private String[] nameUpdate;
	private File[] nameUpload;
	private String[] nameUpdateFileName;
	private String locco;
	private List<KunnrSalesArea> killSalesArea;
	private String codes;
	
	private String curStaIdFlag;
	private KunnrApplySave kunnrApplyObject;
	private List<KunnrApplySave> kunnrApplyObjectList;
	private String crm_xmlFilePath;
	private String xmlId;
	@Decode
	private String xmlName;
	
	private String wefUpdateFlag;        //�������޸��˻�  'Y'
	private String printType;
	private IEventService eventService;
	
	private ProEventTotal proEventTotal;
	private List<ProEventDetail> eventDetailList;
	private String downloadFromPath;
	
	private Integer staffNumber;
	private String bukrs;
	@Decode
	private String allChannelName;
	

	/**
	 * ����ѡ���¸�������
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "userUtil", include = { "processInstanceId", "result" })
	public String selectNexUser() {
		userId=userId!=null?userId:this.getUser().getUserId();
		String role = "";//��ɫȨ��
		if (kunnrService.getOfficeRole(userId,"WFE-XS-GJJL")){ //�߼�����
			role = "WFE-XS-GJJL";
		} else if (kunnrService.getOfficeRole(userId,"WFE-XS-WY")){ //���´���Ա
			role = "WFE-XS-WY";
		} else if (kunnrService.getOfficeRole(userId,"WFE-XS-KHJL")){ //�ͻ�����
			role = "WFE-XS-KHJL";
		}else if (kunnrService.getOfficeRole(userId,"WFE-XS-YD")) { //ҵ��
			role = "WFE-XS-YD";
		}
		
		Object[] res = new Object[4];
		res[1] = userId;
		// ����
		if ("freeze".equals(freezeOrClose)) {
			res[0] = key4freeze;
			res[2] = "kunnrId,type,executeAction,refuseAction";
			res[3] = kunnrId+ "," + type + "," + appUrl + "/kunnrAction!kunnrFreeze.jspa"+","+appUrl+"/kunnrAction!kunnrFreezeRefused.jspa";
		} // �ػ�
		else if ("close".equals(freezeOrClose)) {
			res[0] = key4close;
			res[2] = "type,executeAction";
			res[3] = appUrl + "/kunnrAction!kunnrClose.jspa";
		}
		// �޸�
		else if ("update".equals(freezeOrClose)) {
			res[0] = key4update;
			res[2] = "updateFlag,executeAction,role";
			res[3] = updateFlag + "," + appUrl
					+ "/kunnrAction!kunnrUpdate.jspa"+","+role;

		}
		//����
		else {
			res[0] = key4open;
			res[2] = "executeAction,refuseAction,dataLack";
			res[3] = appUrl + "/kunnrAction!kunnrOpen.jspa"
			+","+appUrl+"/kunnrAction!kunnrOpenRefused.jspa"+","+"N";
		}
		userUtil = wfeServiceHessian.startWorkflowFix(res);
		return JSON;
	}

	/**
	 *ȡ���¸�������
	 * 
	 * @return
	 */
	@PermissionSearch
	public String cancelNextUser() {
		try {
			wfeServiceHessian.cancelEvent(eventId);
		} catch (Exception e) {
			logger.error(e);
		}
		this.setSuccessMessage("ok");
		return RESULT_MESSAGE;
	}

	/**
	 * �����̿���������ת
	 * 
	 * @return
	 */
	@PermissionSearch
	public String kunnrApplyPre() {
		if(StringUtils.isNotEmpty(xmlId)&&StringUtils.isNotEmpty(subFolders)){
			kunnrCode=xmlId;
			userId=this.getUser().getUserId();
			String pathFile = crm_xmlFilePath + File.separator + subFolders
					+ File.separator + xmlId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				kunnrBusiness = kunnr.getKunnrbusinessList().get(0);
				kunnrAddressList = kunnr.getKunnrAddressList();
				kunnrBrandList = kunnr.getKunnrBrandList();
				kunnrAcountList = kunnr.getKunnrAcountList();
				kunnrLicenseList = kunnr.getKunnrLicenseList();
				kunnrSalesAreaList = kunnr.getKunnrSalesAreaList();
				bCustomerTargetList = kunnr.getTargetList();
				acountListSize = kunnrAcountList != null ? kunnrAcountList
						.size() : 0;
				kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
						.size() : 0;
				bCustomerTargetListSize = bCustomerTargetList != null ? bCustomerTargetList
						.size() : 0;
				addressListSize = kunnrAddressList != null ? kunnrAddressList
						.size() : 0;
				licenseListSize = kunnrLicenseList != null ? kunnrLicenseList
						.size() : 0;
			}
		
		}else{
		kunnrCode = kunnrService.getRanKunnrCode();
		/* kunnr.setLocco("DM" + kunnrCode); */
		AllUsers user = this.getUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		kunnr = new Kunnr();
		kunnr.setCreateUserId(Long.parseLong(user.getUserId()));
		kunnr.setCreateUser(user.getUserName());
		kunnr.setCreateDate(sdf.format(new Date()));
		userId = user.getUserId();
		Borg org = orgServiceHessian.getOrgByUserId(userId);
		orgId = org.getOrgId().toString();
		orgName = org.getOrgName();
		kunnr.setOrgId(orgId);
		kunnr.setOrgName(orgName);
		}
		return SUCCESS;
	}
	/**
	 * ���ѡ�����֯�Ƿ��ǳ���
	 * @return
	 */
	@JsonResult(field = "stringResult", include = { "result", "code" })
	public String checkonly(){
		stringResult = new StringResult();
		if(StringUtils.isNotEmpty(orgId)){
			Borg borg = new Borg();
			borg.setOrgId(Long.parseLong(orgId));
			borg.setOrgCity("I");
			String orgId1 = kunnrService.getCityOrgId(borg);
			
			if(StringUtils.isEmpty(orgId1)){
				stringResult.setResult("��ѡ�񵽳��м���!");
			}
		}else{
			stringResult.setResult("��ѡ�񵽳��м���!");
		}
		return JSON;
	}
	/**
	 * 
	 * ȡ�ͻ�����,�������������β����еĵײ�������Ϣ ��"T"����׵�����
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "channelList", include = { "channelId", "channelName" })
	public String getChannel() {
		Bchannel channel = new Bchannel();
		if (StringUtils.isNotEmpty(channelName)) {
			if ("T".equals(channelName)) {
				channel.setChannelName("����");
			}
			if ("Z".equals(channelName)) {
				channel.setChannelName("һ��");
			}
			if ("K".equals(channelName)) {
				channel.setChannelName("����");
			}
		}
		try {
		    
		    // start  ͨ���ۺ��������ҿͻ����� by zpf 
		    if (null!=allChannelName && !"".equals(allChannelName)){
			
			channel.setAllChannelName(new String(allChannelName.getBytes("ISO-8859-1"), "UTF-8"));
			channelList = allchannelService.getAllUnderChannel(channel);
			 // end 
		    }else {			
			channelList = channelService.getUnderChannel(channel);
		    }
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		
		return JSON;
	}
	
	// ͨ���ۺ��������ҿͻ����� by zpf 
	
	@PermissionSearch
	@JsonResult(field = "channelList", include = { "channelId", "channelName" })
	public String getAllUnderChannel() {
	    Bchannel channel = new Bchannel();
		
		    
		    // start  ͨ���ۺ��������ҿͻ����� by zpf 
		    if (null!=allChannelName && !"".equals(allChannelName)){
			
			try {
			    channel.setAllChannelName(new String(allChannelName.getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			channelList = allchannelService.getAllUnderChannel(channel);
			 // end 
		    }else {			
			channelList = channelService.getUnderChannel(channel);
		    }
		
		
		return JSON;
	}
	
	
	/**
	 * 
	 * ȡ�ۺ�����
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "channelList", include = { "allChannelName" })
	public String getAllChannel() {
		Bchannel channel = new Bchannel();
		List<Bchannel> lsit = new ArrayList<Bchannel>();
		channelList= new ArrayList<Bchannel>();
		lsit= channelService.getAllChannel(channel);
		for (Bchannel bchannel : lsit) {
		    if (bchannel.getAllChannelName()!=null)
			channelList.add(bchannel);
		}
		
		return JSON;
	}

	/**
	 * ȡ�ͻ���Ŀ��
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "dictList", include = { "itemName", "itemValue" })
	public String getCrmDictList() {
		CrmDict dict = new CrmDict();
		if (StringUtils.isNotEmpty(dictTypeValue)) {
			if (dictTypeValue.equals("carType")) {
				dict.setDictTypeValue("����ͨ�г�������");
			}
			if (dictTypeValue.equals("zyc")) {
				dict.setDictTypeValue("װ����������");
			}

		}
		if (StringUtils.isNotEmpty(dictTypeName)) {
			if (dictTypeName.equals("KHKM"))
				dict.setDictTypeName("�ͻ���Ŀ��");
		}
		dictList = crmdictService.getCrmDictByType(dict);
		return JSON;
	}

	/**
	 * �ύ�����̿�������
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String kunnrApply() {
		Object[] res = new Object[7];
		res[0] = eventId;
		res[1] = userId;
		res[2] = nextUserId;
		res[3] = title;
		res[4] = appUrl + "/kunnrAction!kunnrFormView.jspa";
		res[5] = key4open;
		res[6] = "";
		try {
			kunnrBusinessList = new ArrayList<KunnrBusiness>();
			//���渽��
			if (uploadFileName != null && uploadFileName.length > 0) {
				kunnrService.saveAttachments(kunnrBusiness, upload,
						uploadFileName, key4open);
			}
			// ����֤��
			if (licenseFileName != null && licenseFileName.length > 0) {
				kunnrLicenseList = new ArrayList<KunnrLicense>();
				kunnrService.saveLicenses(kunnrLicenseList, licenseName,
						license, licenseFileName, licenseValid);
			}
			// Ʒ�������пո�,Ŀ��ؼ����⡣���Ѳ���Ʒ���ˣ�
			if (kunnrBusiness.getCategories() != null) {
				kunnrBusiness.setCategories(kunnrBusiness.getCategories()
						.replace(" ", ""));
			}
			//ȡ��������ģ��ӱ����xml�ļ���ȡ
			KunnrBusiness business=new KunnrBusiness();
			List<KunnrLicense> licenseList=new ArrayList<KunnrLicense>();
			List<BCustomerTarget> targetList=new ArrayList<BCustomerTarget>();
			String km="";
			if(StringUtils.isNotEmpty(xmlId)&&StringUtils.isNotEmpty(subFolders)){
				String pathFile = crm_xmlFilePath + File.separator + subFolders
						+ File.separator + xmlId + ".xml";
				XMLInfo infoOld = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
				if (infoOld != null) {
					Kunnr kunnrOld = (Kunnr) infoOld.getObject();
					business = kunnrOld.getKunnrbusinessList().get(0);
					licenseList = kunnrOld.getKunnrLicenseList();
					targetList = kunnrOld.getTargetList();
					km=kunnrOld.getKverm();
				}}
			if(StringUtils.isEmpty(kunnrBusiness.getAccessFile())&&StringUtils.isNotEmpty(business.getAccessFile())){
				kunnrBusiness.setAccessFile(business.getAccessFile());
				kunnrBusiness.setAccessFilePath(business.getAccessFilePath());
			}
			if(StringUtils.isEmpty(kunnrBusiness.getExplainFile())&&StringUtils.isNotEmpty(business.getExplainFile())){
				kunnrBusiness.setExplainFile(business.getExplainFile());
				kunnrBusiness.setExplainFilePath(business.getExplainFilePath());
			}
			if(StringUtils.isEmpty(kunnrBusiness.getCustomerFile())&&StringUtils.isNotEmpty(business.getCustomerFile())){
				kunnrBusiness.setCustomerFile(business.getCustomerFile());
				kunnrBusiness.setCustomerFilePath(business.getCustomerFilePath());
			}
			if(licenseList.size()>0){
				if(!"".equals(km)&&kunnr.getKverm().equals(km)){
					if(null!=kunnrLicenseList&&kunnrLicenseList.size()!=0){
				for (int i = 0; i < licenseList.size(); i++) {
					for (int j = 0; j < kunnrLicenseList.size(); j++) {
						KunnrLicense  lice=new KunnrLicense();
						lice=kunnrLicenseList.get(j);
						if(lice.getLicenseName().equals(licenseList.get(i).getLicenseName())){
							licenseList.set(i, lice);
						}
					}
			      }
				}
					kunnrLicenseList=licenseList;
			 }
		}
			
			
			kunnrBusinessList.add(kunnrBusiness);
			kunnr.setKunnrbusinessList(kunnrBusinessList);
			kunnr.setKunnrAddressList(kunnrAddressList);
			kunnr.setKunnrBrandList(kunnrBrandList);
			Map<String, KunnrAcount> mapAcount=new HashMap<String, KunnrAcount>();
			for (int i = 0; i < kunnrAcountList.size(); i++) {
				String b=kunnrAcountList.get(i).getBonus();
				if (null!=mapAcount.get(b)) {
					this.setFailMessage("��ҵ�ۿ��ظ��������ԣ�");
					return RESULT_MESSAGE;
				}else{
					mapAcount.put(b, kunnrAcountList.get(i));
				}
			}
			kunnr.setKunnrAcountList(kunnrAcountList);
			kunnr.setKunnrLicenseList(kunnrLicenseList);
			kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
			//�������ٵ���Ŀ���� by cg.jiang20161129
//			bCustomerTargetList = (List<BCustomerTarget>) this.getSession()
//					.getAttribute(kunnr.getKunnrCode()
//									+ "kunnrApplyGoalList");
			/**
			 *ȡ��������ģ��ӱ����xml�ļ���ȡ
			 */
//			if(null==bCustomerTargetList||bCustomerTargetList.size()==0){
//				bCustomerTargetList=targetList;
//			}
			/**
			//��֤Ŀ�����Ƿ��㹻
			String rcs1="";
			if (null!=bCustomerTargetList) {
				for (int i = 0; i < bCustomerTargetList.size(); i++) {
					BCustomerTarget bcust = new BCustomerTarget();
					bcust = bCustomerTargetList.get(i);
					// Ԥ��Ŀ�������ܳ�����֯����Ŀ����
					BCustomerTarget tar = new BCustomerTarget();
					tar.setOrgId(bcust.getOrgId());
					tar.setTheMonth(bcust.getTheMonth());
					tar.setTheYear(bcust.getTheYear());
					tar.setMatter(bcust.getMatter());
					BCustomerTarget b=new BCustomerTarget();
					b=tar;
					if(StringUtils.isNotEmpty(kunnr.getKunnr())&&!"generating...".equals(kunnr.getKunnr()))
					{
						b.setCustId(kunnr.getKunnr());
					}
					b.setEventId(eventId);
					b.setPagination("false");
					List<BCustomerTarget> blist=new ArrayList<BCustomerTarget>();
					blist=goalService.getGoalList(b);
					if(null!=blist&&blist.size()>0){
						this.setFailMessage("�ÿͻ��Ѿ�ռ��Ŀ����,�벻Ҫ�ظ��ύ!");
						return RESULT_MESSAGE;
					}
					// ��֯����Ŀ����
					tar = goalService.getGoalMessege(tar);
					if (null != tar) {
						if (Double.valueOf(tar.getBox()) <= 0) {
							rcs1 += "������" + bcust.getTheYear()
									+ "��" + bcust.getTheMonth()
									+ "��" + bcust.getMatter()
									+ "����" + "��֯����Ŀ��������,�����������!!</br>";
						} else {
							if (Double.valueOf(bcust.getBox())>Double.valueOf(tar.getBox())) {

								rcs1 += "������" + bcust.getTheYear()
										+ "��" + bcust.getTheMonth()
										+ "��" + bcust.getMatter()
										+ "����Ŀ����������"
										+ "Ŀ������������֯����Ŀ����,�����������!</br>";
							}
						}
					} else {
						rcs1 += "������" + bcust.getTheYear() + "��"
								+ bcust.getTheMonth() + "��"
								+ bcust.getMatter() + "����"
								+ "���������ڵ���֯��û��ά����֯Ŀ����!</br>";
					}
				}
				if (!"".equals(rcs1)) {
					this.setFailMessage("�ύʧ��</br>��ش�����Ϣ��</br>"
							+ rcs1.toString());
					return RESULT_MESSAGE;
				}else{
					//ռ��Ŀ����
					kunnr.setEventId(eventId);
					//������sap���
					KunnrSapCodeObject obj=new KunnrSapCodeObject();
					obj.setVkgrp(kunnr.getCodeSales().substring(0, 2));
					obj.setBzirk(kunnr.getCodeSales().substring(2, 4));
					obj.setEventId(eventId);
					String kunnrNumStr=kunnrService.getRanKunnrCodeNew(obj);
					StringBuffer bl=new StringBuffer();
					bl.append(kunnr.getCodeSales());//ǰ��λ����������λ+ʡ����λ���� 
					bl.append(kunnrNumStr);
					kunnr.setKunnr(bl.toString());
					BooleanResult result =kunnrService.createTarget(bCustomerTargetList, kunnr);
					if(!result.getResult()){
						this.setFailMessage(result.getCode());
						return RESULT_MESSAGE;
					}
				} 
			}
			kunnr.setTargetList(bCustomerTargetList);
			*/
			
			//sl.zhu   ���� ����sap���
			kunnr.setEventId(eventId);
			//������sap���
			KunnrSapCodeObject obj=new KunnrSapCodeObject();
			obj.setVkgrp(kunnr.getCodeSales().substring(0, 2));
			obj.setBzirk(kunnr.getCodeSales().substring(2, 4));
			obj.setEventId(eventId);
			String kunnrNumStr=kunnrService.getRanKunnrCodeNew(obj);
			StringBuffer bl=new StringBuffer();
			bl.append(kunnr.getCodeSales());//ǰ��λ����������λ+ʡ����λ���� 
			bl.append(kunnrNumStr);
			kunnr.setKunnr(bl.toString());
			if (StringUtils.isNotEmpty(kunnr.getBrands())){
				brands = kunnr.getBrands().replace(",", "");
				brand = "";
				
				if(brands.contains("X")){
					brand += "��ƮƮ,";
				}
				if(brands.contains("L")){
					brand += "����԰,";
				}
				if(brands.contains("M")){
					brand += "MECO,";
				}
				brand = brand.substring(0,brand.length()-1);
				kunnr.setBrands(brand);
			}
			//�������ٵ���Ŀ���� by cg.jiang20161129
			/**
			BooleanResult booleanResult =kunnrService.createTarget(bCustomerTargetList, kunnr);
			if(!booleanResult.getResult()){
				this.setFailMessage(booleanResult.getCode());
				return RESULT_MESSAGE;
			}
			//�������Ŀ����
			bSalesTargetList=(List<Goal>)this.getSession().
					getAttribute(kunnr.getKunnrCode()+ "kunnrApplySalesGoalList");
			for(int j=0;j<bSalesTargetList.size();j++){
					stockReportService.createSalesGoal(bSalesTargetList.get(j));
			}
			*/
			String result = wfeServiceHessian.processWorkflowFix(res);
			if ("success".endsWith(result)) {
				if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", kunnr, getUser().getUserId(), getUser()
						.getUserName(), null)&&!JavaBeanXMLUtil.JavaBean2XML(crm_xmlFilePath + "/" + xmlId
								+ ".xml", kunnr, getUser().getUserId(), getUser()
								.getUserName(), null)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					//�����������Ϣ���湦����ϢΪ���ύ״̬
					KunnrApplySave kunnrApply=new KunnrApplySave();
					kunnrApply.setXmlId(xmlId);
					kunnrApply.setState("S");
					kunnrApply.setEventId(eventId);
					kunnrService.updateKunnrApplySave(kunnrApply);
					this.setSuccessMessage("���������ɹ�,�����Ϊ��" + eventId);
				}
			} else {
				this.setFailMessage("��������ʧ��,������");
			}
		} catch (Exception e) {
			wfeServiceHessian.cancelEvent(eventId);
			logger.error(e);
			e.printStackTrace();
		}
		return RESULT_MESSAGE;
	}
	
	 public String toKunnrApplySaveSearch(){
		 return "toKunnrApplySaveSearch";
	 }
	/**
	 * �����̿����ᱨ��ѯ
	 * @return
	 */
	@JsonResult(field="kunnrApplyObjectList",include={"xmlId","xmlPath","xmlName","subfolders","orgId","orgName","empId","empName","createDate","modifyDate","eventId","state"},total="total")
    public String kunnrApplySaveSearch(){
		kunnrApplyObject=new KunnrApplySave();
		kunnrApplyObject.setStart(this.getStart());
		kunnrApplyObject.setEnd(this.getEnd());
		userId=this.getUser().getUserId();
		Borg borg=orgServiceHessian.getOrgByUserId(userId);
		if (!"B".equals(borg.getOrgCity())) {
			kunnrApplyObject.setEmpId(userId);
		}
		
		if(StringUtils.isNotEmpty(xmlId)){
			kunnrApplyObject.setXmlId(xmlId);
		}
		if(StringUtils.isNotEmpty(xmlName)){
			kunnrApplyObject.setXmlName(xmlName);
		}
		if(StringUtils.isNotEmpty(eventId)){
			kunnrApplyObject.setEventId(eventId);
		}
		if(StringUtils.isNotEmpty(status)){
			kunnrApplyObject.setState(status);
		}else{
			kunnrApplyObject.setState("Y");
		}
		total=kunnrService.kunnrApplySaveSearchCount(kunnrApplyObject);
		if(total!=0){
			kunnrApplyObjectList=kunnrService.kunnrApplySaveSearch(kunnrApplyObject);
		}
		
    	return JSON;
    }
	/**
	 * �������뱣��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String kunnrApplySave(){
		BooleanResult result=new BooleanResult();
		try {
			kunnrBusinessList = new ArrayList<KunnrBusiness>();
			// ���渽��
			if (uploadFileName != null && uploadFileName.length > 0) {
				kunnrService.saveAttachments(kunnrBusiness, upload,
						uploadFileName, key4open);
			}
			// ����֤��
			if (licenseFileName != null && licenseFileName.length > 0) {
				kunnrLicenseList = new ArrayList<KunnrLicense>();
				kunnrService.saveLicenses(kunnrLicenseList, licenseName,
						license, licenseFileName, licenseValid);
			}
			KunnrBusiness business=new KunnrBusiness();
			List<KunnrLicense> licenseList=new ArrayList<KunnrLicense>();
			List<BCustomerTarget> targetList=new ArrayList<BCustomerTarget>();
			String km="";
			if(StringUtils.isNotEmpty(xmlId)&&StringUtils.isNotEmpty(subFolders)){
				String pathFile = crm_xmlFilePath + File.separator + subFolders
						+ File.separator + xmlId + ".xml";
				XMLInfo infoOld = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
				if (infoOld != null) {
					Kunnr kunnrOld = (Kunnr) infoOld.getObject();
					business = kunnrOld.getKunnrbusinessList().get(0);
					licenseList = kunnrOld.getKunnrLicenseList();
					targetList = kunnrOld.getTargetList();
					km=kunnrOld.getKverm();
				}}
			if(StringUtils.isEmpty(kunnrBusiness.getAccessFile())&&StringUtils.isNotEmpty(business.getAccessFile())){
				kunnrBusiness.setAccessFile(business.getAccessFile());
				kunnrBusiness.setAccessFilePath(business.getAccessFilePath());
			}
			if(StringUtils.isEmpty(kunnrBusiness.getExplainFile())&&StringUtils.isNotEmpty(business.getExplainFile())){
				kunnrBusiness.setExplainFile(business.getExplainFile());
				kunnrBusiness.setExplainFilePath(business.getExplainFilePath());
			}
			if(StringUtils.isEmpty(kunnrBusiness.getCustomerFile())&&StringUtils.isNotEmpty(business.getCustomerFile())){
				kunnrBusiness.setCustomerFile(business.getCustomerFile());
				kunnrBusiness.setCustomerFilePath(business.getCustomerFilePath());
			}
			if(licenseList.size()>0){
				if(!"".equals(km)&&kunnr.getKverm().equals(km)){
					if(null!=kunnrLicenseList&&kunnrLicenseList.size()!=0){
				for (int i = 0; i < licenseList.size(); i++) {
					for (int j = 0; j < kunnrLicenseList.size(); j++) {
						KunnrLicense  lice=new KunnrLicense();
						lice=kunnrLicenseList.get(j);
						if(lice.getLicenseName().equals(licenseList.get(i).getLicenseName())){
							licenseList.set(i, lice);
						}
					}
			      }
				}
					kunnrLicenseList=licenseList;
			 }
		}
				
			
			kunnrBusinessList.add(kunnrBusiness);
			kunnr.setKunnrbusinessList(kunnrBusinessList);
			kunnr.setKunnrAddressList(kunnrAddressList);
			kunnr.setKunnrBrandList(kunnrBrandList);
			kunnr.setKunnrAcountList(kunnrAcountList);
			kunnr.setKunnrLicenseList(kunnrLicenseList);
			kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
			bCustomerTargetList = (List<BCustomerTarget>) this.getSession()
					.getAttribute(kunnr.getKunnrCode()
									+ "kunnrApplyGoalList");
			if(null==bCustomerTargetList||bCustomerTargetList.size()==0){
				bCustomerTargetList=targetList;
			}
			kunnr.setTargetList(bCustomerTargetList);
			if(StringUtils.isEmpty(xmlId)&&StringUtils.isEmpty(subFolders)){
			kunnrApplyObject=new KunnrApplySave();
			kunnrApplyObject.setXmlId(kunnr.getKunnrCode());
			kunnrApplyObject.setId(Long.valueOf(kunnr.getKunnrCode()));
			kunnrApplyObject.setXmlPath(crm_xmlFilePath);
			kunnrApplyObject.setXmlName(kunnr.getName1()+"_���������ᱨ");
			String subFolders = DateUtil.datetime("yyyyMM");
			kunnrApplyObject.setSubfolders(subFolders);
			kunnrApplyObject.setEmpId(this.getUser().getUserId());
			kunnrApplyObject.setOrgId(this.getUser().getOrgId());
			if (StringUtils.isNotEmpty(kunnr.getBrands())){
				String brandsOfDM = "";
				
				brands = kunnr.getBrands().replace(",", "");
				brand = "";
				if(brands.contains("X")){
					brand += "��ƮƮ,";
					brandsOfDM +="X";
				}
				if(brands.contains("L")){
					brand += "����԰,";
					brandsOfDM +="L";
				}
				if(brands.contains("M")){
					brand += "MECO,";
					brandsOfDM +="M";
				}
				brand = brand.substring(0,brand.length()-1);
				kunnr.setBrandsOfDM(brandsOfDM);
				kunnr.setBrands(brand);
			}
			result=kunnrService.kunnrApplySave(kunnrApplyObject);
				if (!JavaBeanXMLUtil.JavaBean2XML(crm_xmlFilePath + "/" + kunnr.getKunnrCode()
						+ ".xml", kunnr, getUser().getUserId(), getUser()
						.getUserName(), null)&&kunnrService.DB_SUCCESS.equals(result)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					this.setSuccessMessage("����ɹ�,���Ϊ" + kunnr.getKunnrCode());
				}}else{
					if (!JavaBeanXMLUtil.JavaBean2XML(crm_xmlFilePath + "/" + kunnr.getKunnrCode()
							+ ".xml", kunnr, getUser().getUserId(), getUser()
							.getUserName(), subFolders)) {
						this.setFailMessage("��Ϣд��XML�ļ�����,������");
					} else {
						this.setSuccessMessage("����ɹ�,���Ϊ��" + kunnr.getKunnrCode());
					}
				}
		} catch (Exception e) {
			logger.error(e);
		}
		return RESULT_MESSAGE;
	}
	/**
	 *���뾭����Ŀ��By CSV
	 * 
	 * @return
	 */
	@PermissionSearch
	public String importModel() {
		StringBuffer result = new StringBuffer();
		String custnumberp2 = "^\\d{1,}$";
		BooleanResult result1 = new BooleanResult();
		try {
			bCustomerTargetList = new ArrayList<BCustomerTarget>();
			String rcs = "";
			String rcs1 = "";
			if (StringUtils.isNotEmpty(uploadFileFileName)) {
				String end = StringUtils.substring(uploadFileFileName,
						StringUtils.lastIndexOf(uploadFileFileName, '.'));
				if ((end != null && (end.equals(".csv")))
						|| (end != null && (end.equals(".CSV")))) {
					List<String[]> content = SuperCSV
							.getContentFromFile(new File(uploadFile.toString())); // ��ȡString����
					List<BCustomerTarget> blist = new ArrayList<BCustomerTarget>();
					Map<String, BCustomerTarget> tempMap = new HashMap<String, BCustomerTarget>();
					for (int j = 0; j < content.size(); j++) {
						String[] s = content.get(j);
						int ij;
						if (s.length == 5) {
							BCustomerTarget bct = new BCustomerTarget();
							Materiel mat = new Materiel();
							String userId = getUser().getUserId().toString();
							bct.setOpId(userId);
							bct.setOpName(this.getUser().getUserName());
							ij = 0;
							String i0 = s[ij++];
							String i1 = s[ij++];
							String i2 = s[ij++];
							String i20 = s[ij++];
							String i3 = s[ij++];
							
							bct.setKunnrGoalType("B");
							bct.setMark("Y");
							if (null != orgId) {
								bct.setOrgId(orgId);
							}

							if (null != i0) {
								if (i0.matches(custnumberp2)) {
									bct.setTheYear(Long.valueOf(i0));
								} else {
									rcs += "��" + (j + 2) + "��Ŀ���겻������.</br>";
								}
							} else {
								rcs = rcs + "��" + (j + 2) + "��Ŀ����Ϊ��.</br>";
							}
							if (null != i1) {
								if (i1.matches(custnumberp2)) {
									bct.setTheMonth(StringUtils.leftPad(
											String.valueOf(i1), 2, '0'));
								} else {
									rcs += "��" + (j + 2) + "��Ŀ���²�������.</br>";
								}
							} else {
								rcs += "��" + (j + 2) + "��Ŀ����Ϊ��.</br>";
							}
							if (null != i3) {
								i3=String.valueOf(i3).replace(",", "");
									bct.setBox(i3);
							} else {
								bct.setBox("0.00");
							}
							if (null != i2&&null!=i20) {
								mat.setMvgr1(i2);
								mat.setBezei(i20);
								mat.setPagination("false");
								int size1 = goalService
										.getMaterielViewListCount(mat);
								if (size1 == 0) {
									BigDecimal big=new  BigDecimal(0);
									bct.setTargetNum(big);
									rcs += "��" + (j + 2)
											+ "�ж�Ӧ��������Ч���ڲ�����ϵͳ.</br>";
								} else {
									List<Materiel> mList = goalService
											.getMaterielViewList(mat);
									bct.setMatter(i2);
									bct.setBezei(i20);
									if (mList.size() == 0) {
										BigDecimal big=new  BigDecimal(0);
										bct.setTargetNum(big);
										rcs += "��" + (j + 2)
												+ "�ж�Ӧ���ϲ��ڵ�ǰ��Ч����.</br>";
									} else {
										  // b1.multiply(b2).doubleValue(); 
									        
										BigDecimal big=new  BigDecimal(mList
												.get(0).getKbetr());
										BigDecimal big1=new  BigDecimal(bct.getBox());
										bct.setTargetNum(big.multiply(big1));
										/*bct.setTargetNum(Double.valueOf()
												* Double.valueOf(bct.getBox()));*/
									}
								}
							} else {
								rcs += "��" + (j + 2) + "��Ԥ��ھ�������߶�Ӧ���ϲ���Ϊ��.</br>";
							}
							StringBuilder builder = new StringBuilder(i0);
							builder.append(i1).append(i2);
							String key = builder.toString();

							if (null != tempMap.get(key)) {
								rcs += "��" + (j + 2) + "�������ĵ��д����ظ�����.</br>";
							} else {
								tempMap.put(key, bct);
							}
							blist.add(bct);
						} else {
							rcs += "��" + (j + 2) + "����������.</br>";
						}
					}
					if (!"".equals(rcs)) {
						result.append(rcs.toString() + "</br>");
						this.setFailMessage("����ʧ��</br></br>  ��ش�����Ϣ��</br></br></br>"
								+ result.toString());
						return RESULT_MESSAGE;
					} else {
						if (blist.size() > 0) {
								bCustomerTargetList = blist;
								this.getSession().setAttribute(kunnrCode
												+ "kunnrApplyGoalList",
										bCustomerTargetList);
								result1.setResult(true);
								result1.setCode("����ɹ�!");// ��������Ϊ:"+ bCustomerTargetList.size() + "��"
								this.setSuccessMessage(result1.getCode());
						}
					}
					return RESULT_MESSAGE;
				}
			} else {// �ļ�������
				this.setFailMessage("�ļ�������");
				return RESULT_MESSAGE;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return RESULT_MESSAGE;
	}

	public void exportGoals() {
		bCustomerTargetList = new ArrayList<BCustomerTarget>();
		//�鿴����ʱ�����Ŀ����
		if(StringUtils.isNotEmpty(xmlId)&& StringUtils.isNotEmpty(subFolders)){

			String pathFile = crm_xmlFilePath + File.separator + subFolders
					+ File.separator + xmlId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				bCustomerTargetList = kunnr.getTargetList();
			}
		
		}else if (StringUtils.isNotEmpty(eventId)                             // ���������в鿴Ŀ������Ϣ
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				bCustomerTargetList = kunnr.getTargetList();
				// ���xml�ļ���ȡ������ȥ���ݻ�ȡ
				if (null == bCustomerTargetList) {
					bCustomerTargetList = kunnrService
							.getBCustomerTargetList(kunnr);
				}
			}
		} else {
			// ������Ϣ�鿴���޸Ĳ鿴Ŀ������Ϣ
			bCustomerTargetList = kunnrService.getBCustomerTargetList(kunnr);
		}
		OutputStream os = null;
		WritableWorkbook wbook = null;
		List<String> props = new ArrayList<String>();
		HttpServletResponse response = getServletResponse();
		try {
			props.add("theYear");
			props.add("theMonth");
			props.add("matter");
			props.add("bezei");
			props.add("box");
			os = response.getOutputStream();
			response.reset();
			String b = "������Ŀ������Ϣ�鿴";
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(b.getBytes("GBK"), ("ISO8859-1")) + ".xls\"");
			response.setContentType("application/msexcel");
			wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet("������Ŀ��������", 0);
			WritableCellFormat cellFormat_top = new WritableCellFormat();
			WritableFont font = new WritableFont(WritableFont.TIMES, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.DARK_RED);
			cellFormat_top.setAlignment(Alignment.CENTRE);
			cellFormat_top.setFont(font);
			cellFormat_top.setBackground(Colour.YELLOW);
			cellFormat_top.setBorder(Border.ALL, BorderLineStyle.THIN);

			Label label_0 = new Label(0, 0, "���");
			label_0.setCellFormat(cellFormat_top);
			wsheet.addCell(label_0);
			Label label_1 = new Label(1, 0, "�·�");
			label_1.setCellFormat(cellFormat_top);
			wsheet.addCell(label_1);
			Label label_2 = new Label(2, 0, "��ӦԤ��ھ�����");
			label_2.setCellFormat(cellFormat_top);
			wsheet.addCell(label_2);
			Label label_3 = new Label(3, 0, "��������");
			label_3.setCellFormat(cellFormat_top);
			wsheet.addCell(label_3);
			Label label_4 = new Label(4, 0, "Ŀ������(����)");
			label_4.setCellFormat(cellFormat_top);
			wsheet.addCell(label_4);
			ExcelUtil.createExcelWithBook(wbook, props, bCustomerTargetList);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 *��֤Ŀ����(����)
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "resultJson")
	public String checkKunnrGoal() {
		String str = "";
		if (0 < bCustomerTargetList.size()) {
			for (int i = 0; i < bCustomerTargetList.size(); i++) {
				BCustomerTarget bc1 = new BCustomerTarget();
				bc1 = bCustomerTargetList.get(i);
				// ����Ŀ����
				BCustomerTarget bc = new BCustomerTarget();
				bc.setOrgId(orgId); // kunnr.getOrgId()
				bc.setTheMonth(StringUtils.leftPad(bc1.getTheMonth(), 2, "0"));
				bc.setTheYear(Long.valueOf(bc1.getTheYear()));
				bc.setMatter(bc1.getMatter());
				bc = goalService.getGoalMessege(bc);
				if (null != bc) {
					BigDecimal b0=new BigDecimal(0);
					if (bc.getTargetNum().compareTo(b0)==-1 ||bc.getTargetNum().compareTo(b0)==0 ) {
						str += bc.getOrgName() + bc.getTheYear() + "��"
								+ bc.getTheMonth() + "��" + bc.getBezei()
								+ "Ŀ������������֯Ŀ�����������ԣ�" + "\n";
					} else {
						BigDecimal b = bc.getTargetNum();
						BigDecimal target = bc1.getTargetNum();
						if (b.compareTo(target) == -1) {
							str += "������" + bc.getOrgName() + bc.getTheYear()
									+ "��" + bc.getTheMonth() + "��"
									+ bc.getBezei() + "Ŀ������������֯����Ŀ�����������ԣ�"
									+ "\n";
						}
					}

				} else {
					str += "����֯" + bc1.getTheYear() + "��" + bc1.getTheMonth()
							+ "��" + "����Ϊ" + bc1.getMatter() + "������"
							+ "û����֯Ŀ�����������ԣ�" + "\n";
				}
			}

		}
		if ("".equals(str)) {
			str = "success";
		}
		resultJson = str;
		return JSON;
	}

	/**
	 * �鿴�����̿���XML������Ϣ
	 * 
	 * @return
	 */
	@PermissionSearch
	public String kunnrFormView() {
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				kunnrBusiness = kunnr.getKunnrbusinessList().get(0);
				kunnrAddressList = kunnr.getKunnrAddressList();
				kunnrBrandList = kunnr.getKunnrBrandList();
				kunnrAcountList = kunnr.getKunnrAcountList();
				kunnrLicenseList = kunnr.getKunnrLicenseList();
				kunnrSalesAreaList = kunnr.getKunnrSalesAreaList();
				bCustomerTargetList = kunnr.getTargetList();
				Goal goal = new Goal();
				goal.setKunnr(kunnr.getKunnrCode());
				goal.setGoalType("A1");
				goal.setGoalMark("Y");
				goal.setStart(0);
				goal.setEnd(999999);
				bSalesTargetList = stockReportService.searchSalesGoalCGDetailList(goal);
				if(bSalesTargetList==null || bSalesTargetList.size()==0){
					goal.setKunnr(kunnr.getKunnr());
					bSalesTargetList = stockReportService.searchSalesGoalCGDetailList(goal);
				}
				acountListSize = kunnrAcountList != null ? kunnrAcountList
						.size() : 0;
				kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
						.size() : 0;
				bCustomerTargetListSize = bCustomerTargetList != null ? bCustomerTargetList
						.size() : 0;
				bSalesTargetListSize = bSalesTargetList !=null ? bSalesTargetList
						.size() : 0;
				addressListSize = kunnrAddressList != null ? kunnrAddressList
						.size() : 0;
				licenseListSize = kunnrLicenseList != null ? kunnrLicenseList
						.size() : 0;
						// �ͻ��������޸�sapЭ��š���Ч��ʼʱ�䡢����ʱ�䡢���۷�Χ
						//customerManagement --- �˽�ɫ��Ϊ�������� by cg.jiang 201607
//				if ("Y".equals(modifyFlag)
//						&& customerManagement.equals(curStaId)) {
//					if(null==kunnrSalesAreaList||kunnrSalesAreaList.size()==0){
//						kunnrSalesAreaList=kunnrService.getKunnrSalesAreaList(kunnr);
//						kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
//								.size() : 0;
//					}
//					kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
//					return "applySales";
//				}
				String roleId = "khglz";
				if ("Y".equals(modifyFlag) && roleId.equals(curStaId)) {
					kunnrAcountList = kunnrService.getKunnrAcountList(kunnr);
					acountListSize = kunnrAcountList != null ? kunnrAcountList
							.size() : 0;
					kunnr.setKunnrAcountList(kunnrAcountList);
					if(null==kunnrSalesAreaList||kunnrSalesAreaList.size()==0){
						kunnrSalesAreaList=kunnrService.getKunnrSalesAreaList(kunnr);
						kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
								.size() : 0;
					}
					kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
					//��Ϊ�޸Ĳ���
					return "applySales";
					//return "applySapMeg";
				}
				if("start".equals(curStaId)){     //�������޸��ᱨ��Ϣ
					return "applyUpdateByStart";
				}
				if(null==curStaId){            //�����в鿴�������
					curStaIdFlag="N";     
				}
			}
		}
		return "viewopen";
	}

	/**
	 * ���ܿͻ���ά��sapЭ���
	 * 
	 * @return
	 */
	public String updateKunnrApplySapMeg() {
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				userId = String.valueOf(kunnr.getCreateUserId());
				String userName = kunnr.getCreateUser();
				kunnr.setKunnrAcountList(kunnrAcountList);
				//�������۷�Χ by cg.jiang 201607
				if(kunnrSalesAreaList !=null){
					kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
					if(!kunnrService.createKunnrSalesArea(kunnr)){
						this.setFailMessage("�������۷�Χ����!");
						return RESULT_MESSAGE;
					}
				}
				if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", kunnr, userId, userName, subFolders)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					this.setSuccessMessage("����ɹ�!");
				}
				
			}
		}
		return RESULT_MESSAGE;
	}

	/**
	 *  �����̿��������ܿͻ����ɫ��д�����ݿ�
	 * 
	 * @return
	 */
	public String updateKunnrApplySales() {
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				Kunnr k = new Kunnr();
				if(null!=kunnr.getKunnr()&&!"generating...".equals(kunnr.getKunnr())){
				k.setKunnr(kunnr.getKunnr());
				k.setPagination("false");
				List<Kunnr> kList = new ArrayList<Kunnr>();
				kList = kunnrService.kunnrSearch(k);
				if (kList.size() > 0) {
					this.setFailMessage("�������Ѵ���,����Ҫ�ظ�����!");
					return RESULT_MESSAGE;
				} 
					userId = String.valueOf(kunnr.getCreateUserId());
					String userName = kunnr.getCreateUser();
					kunnrCode = kunnrService.getRanKunnrDMCode();
					if(null==kunnrCode){
					kunnr.setLocco("DM" + "1");
					}else{
					kunnr.setLocco("DM" + kunnrCode);
					}
					
					kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
					kunnr.setBukrs(bukrs);
					kunnr.setKunnrAcountList(kunnrAcountList);
					if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/"
							+ eventId + ".xml", kunnr, userId, userName, subFolders)) {
						this.setFailMessage("��Ϣд��XML�ļ�����,������");
					} else {
						String pathFile1 = xmlFilePath + File.separator
								+ subFolders + File.separator + eventId
								+ ".xml";
						XMLInfo info1 = JavaBeanXMLUtil.XML2JavaBean(pathFile1,
								new Kunnr());
						if (info1 != null) {
							try {
								kunnr.setCheckOpId(this.getUserId());
								result = kunnrService.kunnrOpen(kunnr);
								this.setSuccessMessage(result.getCode());
							} catch (Exception e) {
								logger.error(e);
							    //result.setResult(false);
								result.setCode(result.getCode() + "'\n"
										+ "+'���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
								this.setFailMessage(result.getCode());
							}
						}
					
					}
			}
		}}
		return RESULT_MESSAGE;
	}
	/**
	 *���ڵ���˷�����ʱ�޸����̲���
	 * 
	 * @return
	 */
	public String modifyProcessVariable() {
		if(StringUtils.isEmpty(eventId)){
			this.setFailMessage("false");
			return RESULT_MESSAGE;
		}
		if(kunnrService.modifyProcessVariable(eventId)){
			this.setSuccessMessage("true");
		}else{
			this.setFailMessage("false");
		}
		return RESULT_MESSAGE;
	}

	/**
	 ** �����������޸Ŀ�����Ϣ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String kunnrApplyUpdateByStart(){
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			  Kunnr xmlKunnr= new Kunnr();
			if (info != null) {
		        xmlKunnr = (Kunnr) info.getObject();
				userId = String.valueOf(xmlKunnr.getCreateUserId());
				String userName = xmlKunnr.getCreateUser();
				kunnrBusinessList = new ArrayList<KunnrBusiness>();
				// ���渽��
				if (uploadFileName != null && uploadFileName.length > 0) {
					kunnrService.saveAttachments(kunnrBusiness, upload,
							uploadFileName, key4open);
				}
				// ����֤��
				if (licenseFileName != null && licenseFileName.length > 0) {
					kunnrLicenseList = new ArrayList<KunnrLicense>();
					kunnrService.saveLicenses(kunnrLicenseList, licenseName,
							license, licenseFileName, licenseValid);
				}
				KunnrBusiness business=new KunnrBusiness();
				List<KunnrLicense> licenseList=new ArrayList<KunnrLicense>();
				//List<BCustomerTarget> targetList=new ArrayList<BCustomerTarget>();
				Map<String, KunnrLicense> map=new HashMap<String, KunnrLicense>();
				String km="";
				business = xmlKunnr.getKunnrbusinessList().get(0);
				licenseList = xmlKunnr.getKunnrLicenseList();
				//targetList = xmlKunnr.getTargetList();
				km=xmlKunnr.getKverm();
				if(StringUtils.isEmpty(kunnrBusiness.getAccessFile())&&StringUtils.isNotEmpty(business.getAccessFile())){
					kunnrBusiness.setAccessFile(business.getAccessFile());
					kunnrBusiness.setAccessFilePath(business.getAccessFilePath());
				}
				if(StringUtils.isEmpty(kunnrBusiness.getExplainFile())&&StringUtils.isNotEmpty(business.getExplainFile())){
					kunnrBusiness.setExplainFile(business.getExplainFile());
					kunnrBusiness.setExplainFilePath(business.getExplainFilePath());
				}
				if(StringUtils.isEmpty(kunnrBusiness.getCustomerFile())&&StringUtils.isNotEmpty(business.getCustomerFile())){
					kunnrBusiness.setCustomerFile(business.getCustomerFile());
					kunnrBusiness.setCustomerFilePath(business.getCustomerFilePath());
				}
				if(null!=licenseList){
					for (int i = 0; i < licenseList.size(); i++) {
						map.put(licenseList.get(i).getLicenseName(), licenseList.get(i));
					}
				if(licenseList.size()>0){
					if(!"".equals(km)&&kunnr.getKverm().equals(km)){
						if(null!=kunnrLicenseList&&kunnrLicenseList.size()!=0){
							 List<KunnrLicense> newList=new ArrayList<KunnrLicense>();
						for (int j = 0; j < kunnrLicenseList.size(); j++) {
							KunnrLicense  lice=new KunnrLicense();
							lice=kunnrLicenseList.get(j);
							  if(map.get(lice.getLicenseName())!=null){
					              map.get(lice.getLicenseName()).setFileType(lice.getFileType());
					              map.get(lice.getLicenseName()).setId(lice.getId());
					              map.get(lice.getLicenseName()).setKunnr(lice.getKunnr());
					              map.get(lice.getLicenseName()).setLicenseFile(lice.getLicenseFile());
					              map.get(lice.getLicenseName()).setLicensePath(lice.getLicensePath());
					              map.get(lice.getLicenseName()).setLicenseValid(lice.getLicenseValid());
					            }else{
					              map.put(lice.getLicenseName(), lice);
					            }
				      }
						 Iterator it=map.keySet().iterator();
				          while (it.hasNext()) {
				            String key=(String) it.next();
				            KunnrLicense licse=map.get(key);
				            newList.add(licse);
				          }
						kunnrLicenseList=newList;
					}else{
						kunnrLicenseList=licenseList;
					}
				 }
			   }
				}
				//�������ٵ���Ŀ������ע��������� cg.jiang20161130
				/**
				  bCustomerTargetList = (List<BCustomerTarget>) this.getSession()
				           .getAttribute(kunnr.getKunnrCode()
								+ "kunnrApplyGoalList");
		          if(null==bCustomerTargetList||bCustomerTargetList.size()==0){
			         bCustomerTargetList=targetList;
		            }else{
		            	 //��֤Ŀ�����Ƿ��㹻
					String rcs1="";
					if (null!=bCustomerTargetList) {
						for (int i = 0; i < bCustomerTargetList.size(); i++) {
							BCustomerTarget bcust = new BCustomerTarget();
							bcust = bCustomerTargetList.get(i);
							// Ԥ��Ŀ�������ܳ�����֯����Ŀ����
							BCustomerTarget tar = new BCustomerTarget();
							tar.setOrgId(bcust.getOrgId());
							tar.setTheMonth(bcust.getTheMonth());
							tar.setTheYear(bcust.getTheYear());
							tar.setMatter(bcust.getMatter());
							BCustomerTarget b=new BCustomerTarget();
							b=tar;
							b.setCustId(kunnr.getKunnr());
							b.setEventId(eventId);
							//b.setKunnrCode(kunnr.getCodeSales());
							b.setPagination("false");
							List<BCustomerTarget> blist=new ArrayList<BCustomerTarget>();
							blist=goalService.getGoalList(b);
							if(null!=blist&&blist.size()>0){
								//ɾ���ᱨʱ��Ŀ����
								BCustomerTarget del=new BCustomerTarget();
								StringBuffer codes=new StringBuffer();
								for (int j = 0; j < blist.size(); j++) {
									BCustomerTarget target=new BCustomerTarget();
									target=blist.get(j);
									codes.append(target.getCtId()).append(",");
								}
								String[] code=codes.toString().substring(0,codes.toString().length()).split(",");
								del.setCodes(code);
								goalService.deleteKunnrGoalAppply(del);
							}
							// ��֯����Ŀ����
							tar = goalService.getGoalMessege(tar);
							if (null != tar) {
								if (Double.valueOf(tar.getBox()) <= 0) {
									rcs1 += "������" + bcust.getTheYear()
											+ "��" + bcust.getTheMonth()
											+ "��" + bcust.getMatter()
											+ "����" + "��֯����Ŀ��������,�����������!</br>";
								} else {
									if (Double.valueOf(bcust.getBox())>Double.valueOf(tar.getBox())) {
										rcs1 += "������" + bcust.getTheYear()
												+ "��" + bcust.getTheMonth()
												+ "��" + bcust.getMatter()
												+ "����Ŀ����������"
												+ "Ŀ������������֯����Ŀ����,�����������!</br>";
									}
								}
							} else {
								rcs1 += "������" + bcust.getTheYear() + "��"
										+ bcust.getTheMonth() + "��"
										+ bcust.getMatter() + "����"
										+ "���������ڵ���֯��û��ά����֯Ŀ����!</br>";
							}
						}
						if (!"".equals(rcs1)) {
							this.setFailMessage("�ύʧ��</br>��ش�����Ϣ��</br>"
									+ rcs1.toString());
							return RESULT_MESSAGE;
						}else{
							kunnr.setEventId(eventId);
							BooleanResult result =kunnrService.createTarget(bCustomerTargetList, kunnr);
							if(!result.getResult()){
								this.setFailMessage(result.getCode());
								return RESULT_MESSAGE;
							}
						} 
					}}*/
        			kunnrBusinessList.add(kunnrBusiness);
        			kunnr.setKunnrbusinessList(kunnrBusinessList);
        			kunnr.setKunnrAddressList(kunnrAddressList);
        			kunnr.setKunnrBrandList(kunnrBrandList);
        			kunnr.setKunnrAcountList(kunnrAcountList);
        			kunnr.setKunnrLicenseList(kunnrLicenseList);
        			kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
        			//Ʒ��
        			if (StringUtils.isNotEmpty(kunnr.getBrands())){
        				brands = kunnr.getBrands().replace(",", "");
        				brand = "";
        				if(brands.contains("X")||brands.contains("��ƮƮ")){
        					brand += "��ƮƮ,";
        				}
        				if(brands.contains("L")||brands.contains("����԰")){
        					brand += "����԰,";
        				}
        				if(brands.contains("M")||brands.contains("MECO")){
        					brand += "MECO,";
        				}
        				brand = brand.substring(0,brand.length()-1);
        				kunnr.setBrands(brand);
        			}
        			if(StringUtils.isEmpty(kunnr.getBrands())&&StringUtils.isNotEmpty(kunnr.getBrandsC())){
        				kunnr.setBrands(kunnr.getBrandsC());
        			}
        			//kunnr.setTargetList(bCustomerTargetList);
        			
        			//�������Ŀ����
        			/**bSalesTargetList=(List<Goal>)this.getSession().
        					getAttribute(kunnr.getKunnrCode()+ "kunnrApplySalesGoalList");
 		          if(null!=bSalesTargetList){
 		        	  Goal g= new Goal();
 		        	  g.setKunnr(kunnr.getKunnrCode());
 		        	  stockReportService.deleteGoalCGDetail(g);
        			 for(int j=0;j<bSalesTargetList.size();j++){
        				 stockReportService.createSalesGoal(bSalesTargetList.get(j));
        			 }
        		}
        			*/
			  if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", kunnr, userId, userName, subFolders)) {
				  this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					this.setSuccessMessage("����ɹ�!");
				}
			}
		}
		return RESULT_MESSAGE;
	}
	/**
	 * ���ظ���
	 * 
	 * @return
	 */
	@PermissionSearch
	public void downLoadAttachments() {
		try {
			attachmentsName = new String(attachmentsName.getBytes("ISO8859-1"),
					"GBK");
			File source = new File(attachmentsPath);
			if (source.exists()) {
				FileUtil.downFile(source, attachmentsName,
						this.getServletResponse());
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
	}

	/**
	 * �������������,��Э���д�����ݿ�
	 * 
	 * @return
	 */

	@JsonResult(field = "executeResult", responseFormat = "jsonp")
	public String kunnrOpen() {
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				stockReportService.updateGoalCGDetailBoxNumDToKunnr(kunnr.getKunnr(), kunnr.getKunnrCode());//�����ɹ�����������Ŀ�����Ϊ�����̷���Ŀ��
				String str = "";
				kunnrSalesAreaList = kunnrService.getKunnrSalesAreaList(kunnr);
				if (null != kunnrSalesAreaList) {
//					if (null != kunnr.getKunnrAcountList()) {
//						for (int i = 0; i < kunnr.getKunnrAcountList().size(); i++) {
//							if (null == kunnr.getKunnrAcountList().get(i)
//									.getAcountSapA()
//									&&null == kunnr.getKunnrAcountList().get(i)
//									.getAcountSapB()&&null == kunnr.getKunnrAcountList().get(i)
//									.getAcountSapC()&& null == kunnr.getKunnrAcountList()
//											.get(i).getStartDate()
//									&& null == kunnr.getKunnrAcountList()
//											.get(i).getEndDate()) {
//								str += "δά����ҵ�ۿ۵�Э����Ϣ!";
//							}
//						}
//						if (!"".equals(str)) {
//							result = new BooleanResult();
//							result.setResult(false);
//							str = "δά����ҵ�ۿ۵�Э����Ϣ!";
//							result.setCode(str);
//						} else {
							try {
								result = kunnrService.updateKunnrAcount(
										kunnr.getKunnrAcountList(),
										kunnr.getKunnr());
							} catch (Exception e) {
								logger.error(e);
								result.setResult(false);
								result.setCode("���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
							}
//						}
//					}
				} else {
					result = new BooleanResult();
					result.setResult(false);
					str = "δά�����۷�Χ��Ϣ!";
					result.setCode(str);
				}
			}
			else{
				result = new BooleanResult();
				result.setResult(false);
				result.setCode("XML�ļ���ȡʧ��,����ϵϵͳ����Ա!");
			}
		}else{
			result = new BooleanResult();
			result.setResult(false);
			result.setCode("XML�ļ���ȡʧ��,����ϵϵͳ����Ա!");
		}
		setExecuteResult(result);
		return JSON;
	}

	/**
	 * * ���񱻾ܣ��۳��Ѿ�ռ�õ�Ԥ��Ŀ�꣬ct_state ��Ϊ2
	 * @return
	 */
	@JsonResult(field = "executeResult", responseFormat = "jsonp")
	public String kunnrOpenRefused(){
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				
				Goal goal = new Goal();
				goal.setKunnr(kunnr.getKunnrCode());
				stockReportService.deleteGoalCGDetail(goal);
				
				bCustomerTargetList = kunnrService.getBCustomerTargetList(kunnr);
				if (null != bCustomerTargetList) {
						try {
							if(null!=kunnr.getKunnr()&&!"generating...".equals(kunnr.getKunnr())){
								result = kunnrService.updateKunnrTarget(kunnr);
							}
							} catch (Exception e) {
								logger.error(e);
								result.setResult(false);
								result.setCode("���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
							}
						}
				} 
			try {
				//sap������ɱ�״̬��Ϊ��Ч
				KunnrSapCodeObject obj=new KunnrSapCodeObject();
				obj.setEventId(eventId);
				obj.setStatus("N");
				kunnrService.updateKunnrSapCodeStatus(obj);
				result.setResult(true);
				result.setCode("�����ɹ�!");
			} catch (Exception e) {
				logger.error(e);
				result.setResult(false);
				result.setCode("����SAP���ʧ��.������!");
			}
			}
		setExecuteResult(result);
		return JSON;
	
	}
	/**
	 * �ύ�����̶�������(ѡ����һ��������֮��)
	 * 	����kunnr status=3  �ػ���
	 * @return
	 */
	public String kunnrFreezeApply() {
		Object[] res = new Object[7];
		res[0] = eventId;
		res[1] = userId;
		res[2] = nextUserId;
		res[3] = title;
		res[4] = appUrl + "/kunnrAction!kunnrFreezeFormView.jspa";
		res[5] = key4freeze;
		res[6] = "";
		try {
			// ���渽��
			if (uploadFileName != null && uploadFileName.length > 0) {
				kunnrService.saveAttachments(kunnr, upload, uploadFileName,key4open);
			}
			kunnr.setKunnr(kunnrId);
			Kunnr k = kunnrService.getKunnrEntity(kunnr);
			res[3] = k.getName1()+"_"+title;
			k.setCreateReason(kunnr.getCreateReason());
			k.setCreateUser(kunnr.getCreateUser());
			k.setCreateUserId(kunnr.getCreateUserId());
			k.setCreateDate(kunnr.getCreateDate());

			k.setNoticeFile1(kunnr.getNoticeFile1());
			k.setNoticeFilePath1(kunnr.getNoticeFilePath1());
			k.setNoticeFile2(kunnr.getNoticeFile2());
			k.setNoticeFilePath2(kunnr.getNoticeFilePath2());
			k.setNoticeFile3(kunnr.getNoticeFile3());
			k.setNoticeFilePath3(kunnr.getNoticeFilePath3());
			k.setNoticeFile4(kunnr.getNoticeFile4());
			k.setNoticeFilePath4(kunnr.getNoticeFilePath4());
			k.setKunnrAcountList(kunnrService.getKunnrAcountList(kunnr));
			String result = wfeServiceHessian.processWorkflowFix(res);
			if ("success".endsWith(result)) {
				if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", k, getUser().getUserId(), getUser().getUserName(), null)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} 
//�ĳ������۲��ܼ���޸�״̬
				else {
//					//�������������ɹ���������״̬��Ϊ�ػ���
//					Kunnr kk = new Kunnr();
//					kk.setKunnr(kunnrId);
//					kk.setStatus("3");
//					kunnrService.updateKunnrStatus(kk);
					this.setSuccessMessage("���������ɹ�,�����Ϊ��" + eventId);
				}
			} else {
				this.setFailMessage("��������ʧ��,������");
			}
		} catch (Exception e) {
			wfeServiceHessian.cancelEvent(eventId);
			logger.error(e);
		}
		return RESULT_MESSAGE;
	}

	/**
	 * �鿴�����̶���XML������Ϣ
	 * 
	 * @return
	 */
	@PermissionSearch
	public String kunnrFreezeFormView() {
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				kunnrBusiness = kunnrService.getKunnrBusinessEntity(kunnr);
				kunnrAddressList = kunnrService.getKunnrAddressList(kunnr);
				addressListSize = kunnrAddressList != null ? kunnrAddressList
						.size() : 0;
				kunnrBrandList = kunnrService.getKunnrBrandList(kunnr);
				brandListSize = kunnrBrandList != null ? kunnrBrandList.size()
						: 0;
				kunnrAcountList = kunnrService.getKunnrAcountList(kunnr);
				acountListSize = kunnrAcountList != null ? kunnrAcountList
						.size() : 0;
				kunnrLicenseList = kunnrService.getKunnrLicenseList(kunnr);
				licenseListSize = kunnrLicenseList != null ? kunnrLicenseList
						.size() : 0;
				kunnrSalesAreaList = kunnrService.getKunnrSalesAreaList(kunnr);
				bCustomerTargetList = kunnrService
						.getBCustomerTargetList(kunnr);
				kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
						.size() : 0;
				bCustomerTargetListSize = bCustomerTargetList != null ? bCustomerTargetList
						.size() : 0;
				if ("Y".equals(modifyFlag)
						&& customerManagement.equals(curStaId)) {
					freezeButton = "Y";
				}
			}
		}
		return "viewfreeze";
	}

	/**
	 * ����������ͼ
	 * 	KUNNR status=2  �ػ�
	 * @return
	 */
	public String freezeKunnrXview() {
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				try {
					result = kunnrService.kunnrFreezeXview(kunnr);
					this.setSuccessMessage(result.getCode());
				} catch (Exception e) {
					logger.error(e);
					result.setResult(false);
					result.setCode(result.getCode() + "'\n" + "+'����ʧ��.����ϵϵͳ����Ա");
					this.setFailMessage(result.getCode());
				}
			}
		}
		return RESULT_MESSAGE;
	}

	/**
	 * �����̶����������,���ݿ��޸�Ϊ����״̬
	 * KUNNR status=2  �ػ�
	 * @return
	 */
	@JsonResult(field = "executeResult", responseFormat = "jsonp")
	public String kunnrFreeze() {
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId) && StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				try {
					result = kunnrService.kunnrFreeze(kunnr);
				} catch (Exception e) {
					logger.error(e);
					result.setResult(false);
					result.setCode(result.getCode() + "'\n"
							+ "+'���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
				}
			}
		}
		setExecuteResult(result);
		return JSON;
	}

	
	/**
	 * ���񱻾ܣ��ͻ���ԭΪ�����ͻ���state ��Ϊ1
	 * @return
	 */
	@JsonResult(field = "executeResult", responseFormat = "jsonp")
	public String kunnrFreezeRefused(){
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId) && StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				kunnr.setStatus("1");
			    try {
					boolean b=kunnrService.updateKunnrStatus(kunnr);
					kunnrService.kunnrFreezeCallCancelRFC(kunnr);
					if(b){
						result=new BooleanResult();
						result.setResult(b);
						result.setCode("�����ɹ�!");
					}else{
						result=new BooleanResult();
						result.setResult(false);
						result.setCode("����ʧ��!");
					}
				} catch (Exception e) {
					result=new BooleanResult();
					result.setResult(false);
					result.setCode("����ʧ��!");
				}
				} 
			}
		setExecuteResult(result);
		return JSON;
	
	}
	/**
	 *�ύ�����̹ػ�����(����)
	 * 
	 * @return
	 */
	public String kunnrCloseApply() {

		Object[] res = new Object[7];
		res[0] = eventId;
		res[1] = userId;
		res[2] = nextUserId;
		res[3] = title;
		res[4] = appUrl + "/kunnrAction!kunnrCloseFormView.jspa";
		res[5] = key4close;
		res[6] = "";
		try {
			// ���渽��
			if (uploadFileName != null && uploadFileName.length > 0) {
				kunnrService.saveAttachments(kunnr, upload, uploadFileName,
						key4open);
			}
			kunnr.setKunnr(kunnrId);
			Kunnr k = kunnrService.getKunnrEntity(kunnr);
			k.setCreateReason(kunnr.getCreateReason());
			k.setCreateUser(kunnr.getCreateUser());
			k.setCreateUserId(kunnr.getCreateUserId());
			k.setCreateDate(kunnr.getCreateDate());
			String result = wfeServiceHessian.processWorkflowFix(res);
			if ("success".endsWith(result)) {
				if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", k, getUser().getUserId(), getUser()
						.getUserName(), null)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					this.setSuccessMessage("���������ɹ�,�����Ϊ��" + eventId);
				}
			} else {
				this.setFailMessage("��������ʧ��,������");
			}
		} catch (Exception e) {
			wfeServiceHessian.cancelEvent(eventId);
			logger.error(e);
		}
		return RESULT_MESSAGE;

	}

	/**
	 * �鿴�����̹ػ�XML������Ϣ�����ã�
	 * 
	 * @return
	 */
	@PermissionSearch
	public String kunnrCloseFormView() {
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
			}
		}
		return "viewclose";
	}

	/**
	 * �����̹ػ��������,���ݿ��޸�Ϊ�ر�״̬�����ã�
	 * 
	 * @return
	 */
	@JsonResult(field = "executeResult", responseFormat = "jsonp")
	public String kunnrClose() {
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				try {
					result = kunnrService.kunnrClose(kunnr);
				} catch (Exception e) {
					logger.error(e);
					result.setResult(false);
					result.setCode(result.getCode() + "\n"+ "���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
				}
			}
		}
		setExecuteResult(result);
		return JSON;
	}

	/**
	* �ύ�������޸�����
	 * 
	 * @return
	 */
	public String kunnrUpdateApply() {
		Object[] res = new Object[7];
		res[0] = eventId;
		res[1] = userId;
		res[2] = nextUserId;
		res[3] = title;
		res[4] = appUrl + "/kunnrAction!kunnrUpdateFormView.jspa";
		res[5] = key4update;
		res[6] = "";
		try {

			// ���渽��
			if (uploadFileName != null && uploadFileName.length > 0) {
				kunnrService.saveAttachments(kunnrBusiness, upload,
						uploadFileName, res[5].toString());
			}
			KunnrBusiness business= new KunnrBusiness();
			business=kunnrService.getKunnrBusinessEntity(kunnr);
			//Ʒ��
			if(StringUtils.isEmpty(kunnr.getBrands())&&StringUtils.isNotEmpty(kunnr.getBrandsC())){
				kunnr.setBrands(kunnr.getBrandsC());
			}
			if (StringUtils.isNotEmpty(kunnr.getBrands())){
				brands = kunnr.getBrands().replace(",", "");
				brand = "";
				if(brands.contains("X")||brands.contains("��ƮƮ")){
					brand += "��ƮƮ,";
				}
				if(brands.contains("L")||brands.contains("����԰")){
					brand += "����԰,";
				}
				if(brands.contains("M")||brands.contains("MECO")){
					brand += "MECO,";
				}
				brand = brand.substring(0,brand.length()-1);
				kunnr.setBrands(brand);
			}
			if(StringUtils.isEmpty(kunnrBusiness.getAccessFile())){
			kunnrBusiness.setAccessFile(business.getAccessFile());
			kunnrBusiness.setAccessFilePath(business.getAccessFilePath());
			}
			if(StringUtils.isEmpty(kunnrBusiness.getExplainFile())&&StringUtils.isNotEmpty(business.getExplainFile())){
				kunnrBusiness.setExplainFile(business.getExplainFile());
				kunnrBusiness.setExplainFilePath(business.getExplainFilePath());
			}
			if(StringUtils.isEmpty(kunnrBusiness.getCustomerFile())){
			kunnrBusiness.setCustomerFile(business.getCustomerFile());
			kunnrBusiness.setCustomerFilePath(business.getCustomerFilePath());
			}
			// ����֤��
			if (licenseFileName != null && licenseFileName.length > 0) {
				kunnrLicenseList = new ArrayList<KunnrLicense>();
				kunnrService.saveLicenses(kunnrLicenseList, licenseName,
						license, licenseFileName, licenseValid);
			}
			// ������֤��
			if (nameUpdateFileName != null) {
				if (!"".equals(nameUpdateFileName[0])) {
					kunnrService.saveCustNameFlie(kunnr, nameUpload,
							nameUpdateFileName, res[5].toString());
				}
			}
			Kunnr oldKunnr= new Kunnr();
			oldKunnr=kunnrService.getKunnrEntity(kunnr);
			if(StringUtils.isEmpty(kunnr.getNameUpdateFile())){
				kunnr.setNameUpdateFile(oldKunnr.getNameUpdateFile());
				kunnr.setNameUpdatePath(oldKunnr.getNameUpdatePath());
			}
			kunnrBusinessList = new ArrayList<KunnrBusiness>();
			List<KunnrLicense> licenseList = new ArrayList<KunnrLicense>();
			licenseList=kunnrService.getKunnrLicenseList(kunnr);
			String km="";
			km=oldKunnr.getKverm();
			if(null!=licenseList){
			if(licenseList.size()>0){
				if(!"".equals(km)&&kunnr.getKverm().equals(km)){
					if(null!=kunnrLicenseList&&kunnrLicenseList.size()!=0){
						Map<String,KunnrLicense> map=new HashMap<String,KunnrLicense>();
						List<KunnrLicense> newList=new ArrayList<KunnrLicense>();
				for (int i = 0; i < licenseList.size(); i++) {
					map.put(licenseList.get(i).getLicenseName(), licenseList.get(i));
				}
					for (int j = 0; j < kunnrLicenseList.size(); j++) {
						KunnrLicense  lice=new KunnrLicense();
						lice=kunnrLicenseList.get(j);
						if(map.get(lice.getLicenseName())!=null){
							map.get(lice.getLicenseName()).setFileType(lice.getFileType());
							map.get(lice.getLicenseName()).setId(lice.getId());
							map.get(lice.getLicenseName()).setKunnr(lice.getKunnr());
							map.get(lice.getLicenseName()).setLicenseFile(lice.getLicenseFile());
							map.get(lice.getLicenseName()).setLicensePath(lice.getLicensePath());
							map.get(lice.getLicenseName()).setLicenseValid(lice.getLicenseValid());
						}else{
							map.put(lice.getLicenseName(), lice);
						}
			      }
					Iterator it=map.keySet().iterator();
					while (it.hasNext()) {
						String key=(String) it.next();
						KunnrLicense licse=map.get(key);
						newList.add(licse);
					}
							kunnrLicenseList=newList;
				}
			 }
		}}
			kunnrBusinessList.add(kunnrBusiness);
			kunnr.setKunnrbusinessList(kunnrBusinessList);
			kunnr.setKunnrAddressList(kunnrAddressList);
			kunnr.setKunnrBrandList(kunnrBrandList);
			kunnr.setKunnrAcountList(kunnrAcountList);
			kunnr.setKunnrLicenseList(kunnrLicenseList);
			Map<String, KunnrSalesArea> map=new HashMap<String, KunnrSalesArea>();
			for (int i = 0; i < kunnrSalesAreaList.size(); i++) {
				StringBuilder b=new StringBuilder();
				b.append(kunnrSalesAreaList.get(i).getVkorg());
				b.append(kunnrSalesAreaList.get(i).getSpart());
				b.append(kunnrSalesAreaList.get(i).getVtweg());
				if (null!=map.get(b.toString())) {
					this.setFailMessage("���۷�Χ�ظ��������ԣ�");
					return RESULT_MESSAGE;
				}else{
				map.put(b.toString(), kunnrSalesAreaList.get(i));
				}
			}
			kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
			bCustomerTargetList = kunnrService.getBCustomerTargetList(kunnr);
			kunnr.setTargetList(bCustomerTargetList);
			String result = wfeServiceHessian.processWorkflowFix(res);
		//	String result="";
			//boolean b=false;
			if ("success".endsWith(result)) {
				if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", kunnr, getUser().getUserId(), getUser()
						.getUserName(), null)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					this.setSuccessMessage("���������ɹ�,�����Ϊ��" + eventId);
				}
			} else {
				this.setFailMessage("��������ʧ��,������");
			}
		} catch (Exception e) {
			wfeServiceHessian.cancelEvent(eventId);
			logger.error(e);
		}
		return RESULT_MESSAGE;
	}

	/**
	 * �������޸Ļ��˵������ˣ������˿����޸�
	 * @return
	 */
	public String kunnrUpdateApplyWfe(){
	    Kunnr oldKunnr=new Kunnr();
		KunnrBusiness business= new KunnrBusiness();
		List<KunnrLicense> licenseList = new ArrayList<KunnrLicense>();
		String km="";
	
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				oldKunnr=(Kunnr)info.getObject();
				business=oldKunnr.getKunnrbusinessList().get(0);
				licenseList=oldKunnr.getKunnrLicenseList();
				km=oldKunnr.getKverm();
				bCustomerTargetList = oldKunnr.getTargetList();
				kunnrAcountList=oldKunnr.getKunnrAcountList();
			}
			}

		// ���渽��
		if (uploadFileName != null && uploadFileName.length > 0) {
			kunnrService.saveAttachments(kunnrBusiness, upload,
					uploadFileName, key4update);
		}
		
		
		if(StringUtils.isEmpty(kunnrBusiness.getAccessFile())){
		kunnrBusiness.setAccessFile(business.getAccessFile());
		kunnrBusiness.setAccessFilePath(business.getAccessFilePath());
		}
		if(StringUtils.isEmpty(kunnrBusiness.getExplainFile())&&StringUtils.isNotEmpty(business.getExplainFile())){
			kunnrBusiness.setExplainFile(business.getExplainFile());
			kunnrBusiness.setExplainFilePath(business.getExplainFilePath());
		}
		if(StringUtils.isEmpty(kunnrBusiness.getCustomerFile())){
		kunnrBusiness.setCustomerFile(business.getCustomerFile());
		kunnrBusiness.setCustomerFilePath(business.getCustomerFilePath());
		}
		// ����֤��
		if (licenseFileName != null && licenseFileName.length > 0) {
			kunnrLicenseList = new ArrayList<KunnrLicense>();
			kunnrService.saveLicenses(kunnrLicenseList, licenseName,
					license, licenseFileName, licenseValid);
		}
		//Ʒ��
		if(StringUtils.isEmpty(kunnr.getBrands())&&StringUtils.isNotEmpty(kunnr.getBrandsC())){
			kunnr.setBrands(kunnr.getBrandsC());
		}
		if (StringUtils.isNotEmpty(kunnr.getBrands())){
			brands = kunnr.getBrands().replace(",", "");
			brand = "";
			if(brands.contains("X")||brands.contains("��ƮƮ")){
				brand += "��ƮƮ,";
			}
			if(brands.contains("L")||brands.contains("����԰")){
				brand += "����԰,";
			}
			if(brands.contains("M")||brands.contains("MECO")){
				brand += "MECO,";
			}
			brand = brand.substring(0,brand.length()-1);
			kunnr.setBrands(brand);
		}
		// ������֤��
		if (nameUpdateFileName != null) {
			if (!"".equals(nameUpdateFileName[0])) {
				kunnrService.saveCustNameFlie(kunnr, nameUpload,
						nameUpdateFileName, key4update);
			}
		}
		
		if(StringUtils.isEmpty(kunnr.getNameUpdateFile())){
			kunnr.setNameUpdateFile(oldKunnr.getNameUpdateFile());
			kunnr.setNameUpdatePath(oldKunnr.getNameUpdatePath());
		}
		kunnrBusinessList = new ArrayList<KunnrBusiness>();
		if(null!=licenseList){
		if(licenseList.size()>0){
			if(!"".equals(km)&&kunnr.getKverm().equals(km)){
				if(null!=kunnrLicenseList&&kunnrLicenseList.size()!=0){
					 Map<String,KunnrLicense> map=new HashMap<String,KunnrLicense>();
			            List<KunnrLicense> newList=new ArrayList<KunnrLicense>();
			        for (int i = 0; i < licenseList.size(); i++) {
			          map.put(licenseList.get(i).getLicenseName(), licenseList.get(i));
			        }
			          for (int j = 0; j < kunnrLicenseList.size(); j++) {
			            KunnrLicense  lice=new KunnrLicense();
			            lice=kunnrLicenseList.get(j);
			            if(map.get(lice.getLicenseName())!=null){
			              map.get(lice.getLicenseName()).setFileType(lice.getFileType());
			              map.get(lice.getLicenseName()).setId(lice.getId());
			              map.get(lice.getLicenseName()).setKunnr(lice.getKunnr());
			              map.get(lice.getLicenseName()).setLicenseFile(lice.getLicenseFile());
			              map.get(lice.getLicenseName()).setLicensePath(lice.getLicensePath());
			              map.get(lice.getLicenseName()).setLicenseValid(lice.getLicenseValid());
			            }else{
			              map.put(lice.getLicenseName(), lice);
			            }
			            }
			          Iterator it=map.keySet().iterator();
			          while (it.hasNext()) {
			            String key=(String) it.next();
			            KunnrLicense licse=map.get(key);
			            newList.add(licse);
			          }
			              kunnrLicenseList=newList;
			        }else{
			        	 kunnrLicenseList=licenseList;
			        }
			       }
			    }}
		kunnrBusinessList.add(kunnrBusiness);
		kunnr.setKunnrbusinessList(kunnrBusinessList);
		kunnr.setKunnrAddressList(kunnrAddressList);
		kunnr.setKunnrBrandList(kunnrBrandList);
		kunnr.setKunnrAcountList(kunnrAcountList);
		kunnr.setKunnrLicenseList(kunnrLicenseList);
		Map<String, KunnrSalesArea> map=new HashMap<String, KunnrSalesArea>();
		for (int i = 0; i < kunnrSalesAreaList.size(); i++) {
			StringBuilder b=new StringBuilder();
			b.append(kunnrSalesAreaList.get(i).getVkorg());
			b.append(kunnrSalesAreaList.get(i).getSpart());
			b.append(kunnrSalesAreaList.get(i).getVtweg());
			if (null!=map.get(b.toString())) {
				this.setFailMessage("���۷�Χ�ظ��������ԣ�");
				return RESULT_MESSAGE;
			}else{
			map.put(b.toString(), kunnrSalesAreaList.get(i));
			}
		}
		if (null != oldKunnr.getKillSalesArea()) {
			if (null != kunnr.getKillSalesArea()) {
				for (int i = 0; i < kunnr.getKillSalesArea().size(); i++) {
					oldKunnr.getKillSalesArea().add(kunnr.getKillSalesArea().get(i));
				}
			}
			killSalesArea=oldKunnr.getKillSalesArea();
			kunnr.setKillSalesArea(killSalesArea);
		}
		kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
		kunnr.setTargetList(bCustomerTargetList);
		if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId+ ".xml", kunnr, getUser().getUserId(), getUser()
					.getUserName(), subFolders)) {
			    this.setFailMessage("��Ϣд��XML�ļ�����,������");
			} else {
				this.setSuccessMessage("����ɹ�!");
		}
		return RESULT_MESSAGE;
	}
	/**
	 * �鿴�������޸�XML������Ϣ
	 * 
	 * @return
	 */
	public String kunnrUpdateFormView() {
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				kunnrBusiness = kunnr.getKunnrbusinessList().get(0);
				kunnrAddressList = kunnr.getKunnrAddressList();
				kunnrBrandList = kunnr.getKunnrBrandList();
				kunnrAcountList = kunnr.getKunnrAcountList();
				kunnrSalesAreaList = kunnr.getKunnrSalesAreaList();
				// ɾ�������۷�Χ
				// bCustomerTargetList = kunnr.getTargetList();
				bCustomerTargetList = kunnrService
						.getBCustomerTargetList(kunnr);
				addressListSize = kunnrAddressList != null ? kunnrAddressList
						.size() : 0;
				acountListSize = kunnrAcountList != null ? kunnrAcountList
						.size() : 0;
						// ֤�ռ������ȴ�xmlȡ ľ�д����ݿ�ȡ
				kunnrLicenseList = kunnr.getKunnrLicenseList();
				kunnrLicenseList = (kunnrLicenseList != null&&kunnrLicenseList.size()!=0) ? kunnrLicenseList
						: kunnrService.getKunnrLicenseList(kunnr);
				licenseListSize = kunnrLicenseList != null ? kunnrLicenseList
						.size() : 0;
				kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
						.size() : 0;
				bCustomerTargetListSize = bCustomerTargetList != null ? bCustomerTargetList
						.size() : 0;
				if (StringUtils.isEmpty(kunnrBusiness.getAccessFile())
						&& StringUtils.isEmpty(kunnrBusiness.getCustomerFile())) {
					KunnrBusiness tempBusiness = kunnrService
							.getKunnrBusinessEntity(kunnr);
					kunnrBusiness.setAccessFile(tempBusiness.getAccessFile());
					kunnrBusiness.setAccessFilePath(tempBusiness
							.getAccessFilePath());
					kunnrBusiness.setCustomerFile(tempBusiness
							.getCustomerFile());
					kunnrBusiness.setCustomerFilePath(tempBusiness
							.getCustomerFilePath());
				}
			}
			if ("Y".equals(modifyFlag) && customerManagement.equals(curStaId)) {
				return "updateSapMeg";
			}
			if ("start".equals(curStaId)) {   //���������޸�ҳ��
				wefUpdateFlag="Y";
				return "editapply";
			}
		}
		return "viewUpdate";
	}

	/**
	 * �޸ľ�����ʱά��sap��Ϣ
	 * 
	 * @return
	 */
	public String updateKunnrSapMeg() {
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				String str=kunnr.getStceg();
				kunnr = (Kunnr) info.getObject();
				kunnr.setStceg(str);
				//kunnrBusiness = kunnr.getKunnrbusinessList().get(0);
				kunnrBusinessList=new ArrayList<KunnrBusiness>();
				kunnrBusinessList.add(kunnrBusiness);
				kunnr.setBukrs(bukrs);
				kunnr.setKunnrbusinessList(kunnrBusinessList);
				kunnr.setKunnrAcountList(kunnrAcountList);
				Map<String, KunnrSalesArea> map=new HashMap<String, KunnrSalesArea>();
				for (int i = 0; i < kunnrSalesAreaList.size(); i++) {
					StringBuilder b=new StringBuilder();
					b.append(kunnrSalesAreaList.get(i).getVkorg());
					b.append(kunnrSalesAreaList.get(i).getSpart());
					b.append(kunnrSalesAreaList.get(i).getVtweg());
					if (null!=map.get(b.toString())) {
						this.setFailMessage("���۷�Χ�ظ��������ԣ�");
						return RESULT_MESSAGE;
					}else{
					map.put(b.toString(), kunnrSalesAreaList.get(i));
					}
				}
				kunnr.setKunnrSalesAreaList(kunnrSalesAreaList);
				if (null != kunnr.getKillSalesArea()) {
					if (null != killSalesArea) {
						for (int i = 0; i < killSalesArea.size(); i++) {
							kunnr.getKillSalesArea().add(killSalesArea.get(i));
						}
					}
				}
				userId = String.valueOf(kunnr.getCreateUserId());
				String userName = kunnr.getCreateUser();
				if (!JavaBeanXMLUtil.JavaBean2XML(xmlFilePath + "/" + eventId
						+ ".xml", kunnr, userId, userName, subFolders)) {
					this.setFailMessage("��Ϣд��XML�ļ�����,������");
				} else {
					this.setSuccessMessage("����ɹ�!");
				}
			}
		}
		return RESULT_MESSAGE;
	}

	/**
	 * �������޸��������,���ݿ��޸���Ϣ
	 * 
	 * @return
	 */
	@JsonResult(field = "executeResult", responseFormat = "jsonp")
	public String kunnrUpdate() {
		BooleanResult result = null;
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
//				String str = "";
//				if (null != kunnr.getKunnrAcountList()) {
//					for (int i = 0; i < kunnr.getKunnrAcountList().size(); i++) {
//						if (null == kunnr.getKunnrAcountList().get(i)
//								.getAcountSapA()
//								&&null == kunnr.getKunnrAcountList().get(i)
//								.getAcountSapB()
//								&&null == kunnr.getKunnrAcountList().get(i)
//								.getAcountSapC()
//								&& null == kunnr.getKunnrAcountList().get(i)
//										.getStartDate()
//								&& null == kunnr.getKunnrAcountList().get(i)
//										.getEndDate()) {
//							str += "δά����ҵ�ۿ۵�Э����Ϣ!";
//						}
//					}
//					if (!"".equals(str)) {
//						result = new BooleanResult();
//						result.setResult(false);
//						str += "δά����ҵ�ۿ۵�Э����Ϣ!";
//					} else {
						try {
							result = kunnrService.kunnrUpdate(kunnr);
						} catch (Exception e) {
							logger.error(e);
							result.setResult(false);
							result.setCode(result.getCode() + "'\n"
									+ "+'���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
						}
//					}
//				}
			}
		}
		setExecuteResult(result);
		return JSON;
	}

	/**
	 * �����̲�ѯά����ת
	 * 
	 * @return
	 */
	@PermissionSearch
	public String kunnrSearchPre() {
		AllUsers user = this.getUser();
		Borg borg = new Borg();
		borg = orgServiceHessian.getOrgByUserId(user.getUserId());
		orgId = String.valueOf(borg.getOrgId());
		orgName = borg.getOrgName();
		return "search";

	}

	/**
	 * 
	 * ���N�̲�ѯ���
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "kunnrList", include = { "kunnr", "name1", "name3",
			"mobNumber", "bukrs", "channelName", "konzs", "street1","street",
			"telNumber", "businessManager", "businessCompetent", "status",
			"orgId", "orgName","staffNumber","staffNumberY","type","maximum","maximumTxt","brands" }, total = "total")
	public String kunnrSearch() {
		kunnr = new Kunnr();
		kunnr.setStart(getStart());
		kunnr.setEnd(getEnd());
		if (StringUtils.isNotEmpty(kunnrId)) {
			kunnr.setKunnr(kunnrId);
		}else if(kunnrs!=null&&!kunnrs[0].equals("")&&kunnrs[0]!=null){
			if(kunnrs.length==1 && kunnrs[0].contains(",")){
				kunnrs=kunnrs[0].split(",");
			}
			kunnr.setKunnrs(kunnrs);
		}
		if (StringUtils.isNotEmpty(name1)) {
			kunnr.setName1(name1);
		}
		if (StringUtils.isNotEmpty(businessManager)) {
			kunnr.setBusinessManager(businessManager);
		}
		if (StringUtils.isNotEmpty(businessCompetent)) {
			kunnr.setBusinessCompetent(businessCompetent);
		}
		if (channelId != null) {
			kunnr.setChannelId(channelId);
		}
		if (StringUtils.isNotEmpty(status)) {
			kunnr.setStatus(status);
		}
		if (StringUtils.isNotEmpty(brands)){
			kunnr.setBrands(brands);
			if(brands.contains("X")){
				kunnr.setBrandOfXpp("��ƮƮ");
			}
			if(brands.contains("L")){
				kunnr.setBrandOfLfy("����԰");
			}
			if(brands.contains("M")){
				kunnr.setBrandOfMeco("MECO");
			}
		}
		if (StringUtils.isNotEmpty(orgId)) {
			String[] str = orgId.split(", ");
			if (str.length > 1) {
				kunnr.setOrgId(str[1]);
			} else {
				kunnr.setOrgId(orgId);
			}
		}
		if(StringUtils.isNotEmpty(orgIds)){
			kunnr.setOrgIds(orgIds);
		}
		if (StringUtils.isNotEmpty(bhxjFlag)) {
			kunnr.setBhxjFlag(bhxjFlag);
		}
		if (StringUtils.isNotEmpty(codes)) {
			String[] str = codes.split(", ");
			kunnr.setCodes(str);
		}
		total = kunnrService.kunnrSearchCount(kunnr);
		if (total != 0) {
			kunnrList = kunnrService.kunnrSearch(kunnr);
		}
		return JSON;
	}

	/**
	 *���������ݵ���
	 * 
	 * @return
	 */
	public void kunnrExport() {
		OutputStream os = null;
		WritableWorkbook wbook = null;
		List<String> props = new ArrayList<String>();
		kunnr = new Kunnr();
		kunnr.setPagination("false");// ��ʹ�÷�ҳ ȫ�������
		HttpServletResponse response = getServletResponse();
		try {
			if (StringUtils.isNotEmpty(kunnrId)) {
				kunnr.setKunnr(kunnrId);
			}
			if (StringUtils.isNotEmpty(name1)) {
				kunnr.setName1(name1);
			}
			if (StringUtils.isNotEmpty(businessManager)) {
				kunnr.setBusinessManager(businessManager);
			}
			if (StringUtils.isNotEmpty(businessCompetent)) {
				kunnr.setBusinessCompetent(businessCompetent);
			}
			if (channelId != null) {
				kunnr.setChannelId(channelId);
			}
			if (StringUtils.isNotEmpty(status)) {
				kunnr.setStatus(status);
			}
			if (StringUtils.isNotEmpty(orgId)) {
				String[] str = orgId.split(", ");
				if (str.length > 1) {
					kunnr.setOrgId(str[1]);
				} else {
					kunnr.setOrgId(orgId);
				}
			}
			if (StringUtils.isNotEmpty(bhxjFlag)) {
				kunnr.setBhxjFlag(bhxjFlag);
			}
			kunnrList = kunnrService.kunnrSearch(kunnr);

			props.add("kunnr");
			props.add("name1");
			props.add("name3");
			props.add("mobNumber");
			props.add("channelName");
			props.add("warehouseArea");
			props.add("natureEnterprise");
			props.add("orgName");
			props.add("brands");
			props.add("street1");
			props.add("telNumber");
			props.add("faxNumber");
			props.add("locco");
			props.add("bukrs");
			props.add("werks");
			props.add("bank");
			props.add("bankAccount");
			props.add("stceg");
			props.add("zcAddress");
			props.add("fpRecipient");
			props.add("fpAddress");
			props.add("fpContactPhone");
			props.add("healthNumber");
			props.add("businessLicense");
			props.add("businessManager");
			props.add("managerMobile");
			props.add("businessCompetent");
			props.add("competentMobile");
			props.add("kunnrLeader");
			props.add("kunnrPhone");
			props.add("street");
			props.add("maximumTxt");
			props.add("name102");
			props.add("name102tel");
			props.add("name102mob");
			props.add("coverArea");
			props.add("konzs");
			props.add("kunag");
			props.add("createDate");
			props.add("endDate");
			props.add("status");
			
			os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String("���������ݵ�����".getBytes("GBK"), ("ISO8859-1"))
					+ ".xls\"");
			response.setContentType("application/msexcel");
			wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet("����������", 0);
			WritableCellFormat cellFormat_top = new WritableCellFormat();
			WritableFont font = new WritableFont(WritableFont.TIMES, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.DARK_RED);
			cellFormat_top.setAlignment(Alignment.CENTRE);
			cellFormat_top.setFont(font);
			cellFormat_top.setBackground(Colour.YELLOW);
			cellFormat_top.setBorder(Border.ALL, BorderLineStyle.THIN);

			Label label_0 = new Label(0, 0, "������SAP����");
			label_0.setCellFormat(cellFormat_top);
			wsheet.addCell(label_0);
			Label label_1 = new Label(1, 0, "����������");
			label_1.setCellFormat(cellFormat_top);
			wsheet.addCell(label_1);
			Label label_2 = new Label(2, 0, "����");
			label_2.setCellFormat(cellFormat_top);
			wsheet.addCell(label_2);
			Label label_3 = new Label(3, 0, "��ϵ�绰");
			label_3.setCellFormat(cellFormat_top);
			wsheet.addCell(label_3);
			Label label_4 = new Label(4, 0, "�ͻ�����");
			label_4.setCellFormat(cellFormat_top);
			wsheet.addCell(label_4);
			Label label_5 = new Label(5, 0, "�ֿ����");
			label_5.setCellFormat(cellFormat_top);
			wsheet.addCell(label_5);
			Label label_6 = new Label(6, 0, "��ҵ����");
			label_6.setCellFormat(cellFormat_top);
			wsheet.addCell(label_6);
			Label label_7 = new Label(7, 0, "������֯");
			label_7.setCellFormat(cellFormat_top);
			wsheet.addCell(label_7);
			Label label_40 = new Label(8, 0, "��ӪƷ��");
			label_40.setCellFormat(cellFormat_top);
			wsheet.addCell(label_40);
			Label label_8 = new Label(9, 0, "��˾��ַ");
			label_8.setCellFormat(cellFormat_top);
			wsheet.addCell(label_8);
			Label label_9 = new Label(10, 0, "��˾�绰");
			label_9.setCellFormat(cellFormat_top);
			wsheet.addCell(label_9);
			Label label_10 = new Label(11, 0, "����");
			label_10.setCellFormat(cellFormat_top);
			wsheet.addCell(label_10);
			Label label_11 = new Label(12, 0, "�����");
			label_11.setCellFormat(cellFormat_top);
			wsheet.addCell(label_11);
			Label label_12 = new Label(13, 0, "��˾����");
			label_12.setCellFormat(cellFormat_top);
			wsheet.addCell(label_12);
			Label label_13 = new Label(14, 0, "��������");
			label_13.setCellFormat(cellFormat_top);
			wsheet.addCell(label_13);
			Label label_14 = new Label(15, 0, "��������");
			label_14.setCellFormat(cellFormat_top);
			wsheet.addCell(label_14);
			Label label_15 = new Label(16, 0, "�����˺�");
			label_15.setCellFormat(cellFormat_top);
			wsheet.addCell(label_15);
			Label label_16 = new Label(17, 0, "˰��Ǽ�֤");
			label_16.setCellFormat(cellFormat_top);
			wsheet.addCell(label_16);
			Label label_17 = new Label(18, 0, "ע���ַ");
			label_17.setCellFormat(cellFormat_top);
			wsheet.addCell(label_17);
			Label label_18 = new Label(19, 0, "��Ʊ�ļ���");
			label_18.setCellFormat(cellFormat_top);
			wsheet.addCell(label_18);
			Label label_19 = new Label(20, 0, "��Ʊ���͵�ַ");
			label_19.setCellFormat(cellFormat_top);
			wsheet.addCell(label_19);
			Label label_20 = new Label(21, 0, "��Ʊ��ϵ�˵绰");
			label_20.setCellFormat(cellFormat_top);
			wsheet.addCell(label_20);
			Label label_21 = new Label(22, 0, "��������֤");
			label_21.setCellFormat(cellFormat_top);
			wsheet.addCell(label_21);

			Label label_22 = new Label(23, 0, "Ӫҵִ��ע���");
			label_22.setCellFormat(cellFormat_top);
			wsheet.addCell(label_22);
			Label label_23 = new Label(24, 0, "���о���");
			label_23.setCellFormat(cellFormat_top);
			wsheet.addCell(label_23);
			Label label_24 = new Label(25, 0, "��ϵ�绰");
			label_24.setCellFormat(cellFormat_top);
			wsheet.addCell(label_24);
			Label label_25 = new Label(26, 0, "�ͻ�����");
			label_25.setCellFormat(cellFormat_top);
			wsheet.addCell(label_25);
			Label label_26 = new Label(27, 0, "��ϵ�绰");
			label_26.setCellFormat(cellFormat_top);
			wsheet.addCell(label_26);

			Label label_27 = new Label(28, 0, "�����̸�����");
			label_27.setCellFormat(cellFormat_top);
			wsheet.addCell(label_27);
			Label label_28 = new Label(29, 0, "�����̸������ֻ�");
			label_28.setCellFormat(cellFormat_top);
			wsheet.addCell(label_28);

			Label label_29 = new Label(30, 0, "�ջ��ֿ�");
			label_29.setCellFormat(cellFormat_top);
			wsheet.addCell(label_29);
			Label label_30 = new Label(31, 0, "����ͨ�г���");
			label_30.setCellFormat(cellFormat_top);
			wsheet.addCell(label_30);
			Label label_31 = new Label(32, 0, "�ջ���");
			label_31.setCellFormat(cellFormat_top);
			wsheet.addCell(label_31);
			Label label_32 = new Label(33, 0, "�ջ�����ϵ�绰");
			label_32.setCellFormat(cellFormat_top);
			wsheet.addCell(label_32);
			Label label_33 = new Label(34, 0, "�ջ�����ϵ�ֻ�");
			label_33.setCellFormat(cellFormat_top);
			wsheet.addCell(label_33);
			Label label_34 = new Label(35, 0, "��Ӫ���Ƿ�Χ");
			label_34.setCellFormat(cellFormat_top);
			wsheet.addCell(label_34);
			Label label_35 = new Label(36, 0, "�ϼ������̴���");
			label_35.setCellFormat(cellFormat_top);
			wsheet.addCell(label_35);
			Label label_36 = new Label(37, 0, "���������̴���");
			label_36.setCellFormat(cellFormat_top);
			wsheet.addCell(label_36);
			Label label_37 = new Label(38, 0, "��ʼ����ʱ��");
			label_37.setCellFormat(cellFormat_top);
			wsheet.addCell(label_37);
			Label label_38 = new Label(39, 0, "��������ʱ��");
			label_38.setCellFormat(cellFormat_top);
			wsheet.addCell(label_38);
			Label label_39 = new Label(40, 0, "������״̬��1������ 2���ػ� 3���ػ��У�");
			label_39.setCellFormat(cellFormat_top);
			wsheet.addCell(label_39);
			ExcelUtil.createExcelWithBook(wbook, props, kunnrList);

		} catch (Exception e) {
			logger.error(e);
		}

	}


	/**
	 * �鿴��������Ϣ
	 * 
	 * @return
	 */
	public String kunnrViewInfo() {
		kunnr = new Kunnr();
		kunnr.setKunnr(kunnrId);
		kunnr = kunnrService.getKunnrEntity(kunnr);
		kunnrBusiness = kunnrService.getKunnrBusinessEntity(kunnr);
		kunnrAddressList = kunnrService.getKunnrAddressList(kunnr);
		addressListSize = kunnrAddressList != null ? kunnrAddressList.size()
				: 0;
		kunnrBrandList = kunnrService.getKunnrBrandList(kunnr);
		brandListSize = kunnrBrandList != null ? kunnrBrandList.size() : 0;
		kunnrAcountList = kunnrService.getKunnrAcountList(kunnr);
		acountListSize = kunnrAcountList != null ? kunnrAcountList.size() : 0;
		kunnrLicenseList = kunnrService.getKunnrLicenseList(kunnr);
		licenseListSize = kunnrLicenseList != null ? kunnrLicenseList.size()
				: 0;
		kunnrSalesAreaList = kunnrService.getKunnrSalesAreaList(kunnr);
		kunnrSalesAreaListSize = kunnrSalesAreaList != null ? kunnrSalesAreaList
				.size() : 0;
		bCustomerTargetList = kunnrService.getBCustomerTargetList(kunnr);
		bCustomerTargetListSize = bCustomerTargetList != null ? bCustomerTargetList
				.size() : 0;
		//�޸������޸�ҳ��
		if ("edit".equals(opera)) {
			AllUsers user = this.getUser();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			kunnr.setCreateUserId(Long.parseLong(user.getUserId()));
			kunnr.setCreateUser(user.getUserName());
			kunnr.setCreateDate(sdf.format(new Date()));
			userId = user.getUserId();
			return "editapply";
		}
		return "viewinfo";
	}

	/**
	 *  ���Y��ر�ҳ����ת
	 * 
	 * @return
	 */
	public String kunnrFreezeOrClosePre() {
		AllUsers user = this.getUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		kunnr = new Kunnr();
		kunnr.setCreateUserId(Long.parseLong(user.getUserId()));
		kunnr.setCreateUser(user.getUserName());
		kunnr.setCreateDate(sdf.format(new Date()));
		userId = user.getUserId();
		return "freezeOrClose";
	}

	/**
	 * ֤���ϴ�ҳ����ת
	 * 
	 * @return
	 */
	public String displayLicensePre() {
		CrmDict dict = new CrmDict();
		try {
			dictTypeName = new String(dictTypeName.getBytes("ISO8859-1"), "GBK");
			dict.setDictTypeName(dictTypeName);
			dictList = crmdictService.getCrmDictByType(dict);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return "licensepre";
	}

	/**
	 * ֤�ղ鿴
	 * 
	 * @return
	 */
	public String kunnrViewLicense() {
		kunnr = new Kunnr();
		kunnr.setKunnr(kunnrId);
		kunnrLicenseList = kunnrService.getKunnrLicenseList(kunnr);
		return "viewlicense";
	}

	/**
	 * ��ַѡ��
	 * 
	 * @return
	 */
	public String searchAddress() {
		return "toSearchAddress";
	}

	/**
	 * �������������޸�ҳ����ת
	 * 
	 * @return
	 */
	public String toKunnrLogisticsArea() {
		return "toKunnrLogisticsArea";
	}

	/**
	 * ��ѯ���еľ�������������
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "lAreaList", include = { "kunnr", "vkgrp", "vkgrpTxt",
			"bzirk", "bzirkTxt", "vkbur", "vkburTxt" }, total = "total")
	public String searchLogisticsArea() {
		lAreaList = new ArrayList<KunnrLogisticsArea>();
		KunnrLogisticsArea area = new KunnrLogisticsArea();
		if (StringUtils.isNotEmpty(kunnrCode))
			area.setKunnr(kunnrCode);
		if (StringUtils.isNotEmpty(vkgrp))
			area.setVkgrp(vkgrp);
		if (StringUtils.isNotEmpty(bzirk))
			area.setBzirk(bzirk);
		if (StringUtils.isNotEmpty(vkbur))
			area.setVkbur(vkbur);
		area.setStart(getStart());
		area.setEnd(getEnd());
		total = kunnrService.getKunnrLogisticsAreaCount(area);
		if (total != 0)
			lAreaList = kunnrService.getKunnrLogisticsArea(area);
		return JSON;
	}

	/**
	 * �鿴�޸ĵ�����������Ϣ
	 * 
	 * @return
	 */
	/*
	 * @PermissionSearch
	 * 
	 * @JsonResult(field = "areaList", include = { "kunnr", "vkgrp",
	 * "vkgrpTxt","bzirk","bzirkTxt","vkbur","vkburTxt"}) public String
	 * checkLogisticArea(){ areaList = this.areaList; return JSON; }
	 */
	public String saveLogisticArea() {
		BooleanResult result = null;
		try {
			for (int i = 0; i < areaLists.size(); i++) {
				Kunnr kunnr = new Kunnr();
				kunnr.setKunnr(areaLists.get(i).getKunnr());
				Kunnr k = new Kunnr();
				k = kunnrService.getKunnrEntity(kunnr);
				kunnrBusiness = kunnrService.getKunnrBusinessEntity(kunnr);
				kunnrBusiness.setBzirk(areaLists.get(i).getBzirk());// ����ʡ��
				kunnrBusiness.setVkbur(areaLists.get(i).getVkbur());// ���۲���
				kunnrBusiness.setVkgrp(areaLists.get(i).getVkgrp());// ���۴���
				kunnrAddressList = kunnrService.getKunnrAddressList(kunnr);
				kunnrBrandList = kunnrService.getKunnrBrandList(kunnr);
				kunnrAcountList = kunnrService.getKunnrAcountList(kunnr);
				kunnrSalesAreaList = kunnrService.getKunnrSalesAreaList(kunnr);
				bCustomerTargetList = kunnrService
						.getBCustomerTargetList(kunnr);
				kunnrBusinessList = new ArrayList<KunnrBusiness>();
				kunnrBusinessList.add(kunnrBusiness);
				k.setKunnrbusinessList(kunnrBusinessList);
				k.setKunnrAddressList(kunnrAddressList);
				k.setKunnrBrandList(kunnrBrandList);
				k.setKunnrAddressList(kunnrAddressList);
				k.setKunnrSalesAreaList(kunnrSalesAreaList);
				k.setKunnrAcountList(kunnrAcountList);
				k.setTargetList(bCustomerTargetList);
				areaLists.get(i).setKunnrObject(k);
				areaLists.set(i, areaLists.get(i));
			}
			result = kunnrService.updateLogisticArea(areaLists);
			this.setSuccessMessage(result.getCode());
		} catch (Exception e) {
			logger.error(e);
			result.setResult(false);
			result.setCode(result.getCode() + "\n" + "���ݱ������ݿ�ʧ��.����ϵϵͳ����Ա");
			this.setFailMessage(result.getCode());
		}
		return RESULT_MESSAGE;
	}

	/**
	 * ��ת����ӡҳ��
	 * @return
	 */
	public String toKunnrPrintPre(){
		if (StringUtils.isNotEmpty(eventId)
				&& StringUtils.isNotEmpty(subFolders)) {
			String pathFile = xmlFilePath + File.separator + subFolders
					+ File.separator + eventId + ".xml";
			XMLInfo info = JavaBeanXMLUtil.XML2JavaBean(pathFile, new Kunnr());
			if (info != null) {
				kunnr = (Kunnr) info.getObject();
				if("close".equals(printType)){//�ػ�
				kunnrBusiness = kunnrService.getKunnrBusinessEntity(kunnr);
				}else{
					kunnrBusiness = kunnr.getKunnrbusinessList().get(0);
					kunnrAcountList = kunnr.getKunnrAcountList();
					acountListSize = kunnrAcountList != null ? kunnrAcountList
							.size() : 0;
				    kunnrAddressList = kunnr.getKunnrAddressList();
				    addressListSize =kunnrAddressList!=null?kunnrAddressList.size():0;
				}
			}
		 proEventTotal=eventService.getEventTotalById(Long.valueOf(eventId));
	     eventDetailList=eventService.getEventDetailListAndSort(Long.valueOf(eventId));
		}
		return "toKunnrPrintPre";
	}
	/**
	 * �����̹�Ա�����޸�
	 * @return
	 */
	public String updateKunnrUserStaff(){
		boolean bool=false;
		kunnr=new Kunnr();
		kunnr.setKunnr(kunnrCode);
		kunnr.setStaffNumber(staffNumber);
		bool=kunnrService.updateKunnrUserStaff(kunnr);
		if (bool) {
			this.setSuccessMessage("�����ɹ�!");
		}else{
			this.setFailMessage("����ʧ��!");
		}
		return RESULT_MESSAGE;
	}
	//�����ĵ�

		// ����Excelģ��
	@SuppressWarnings("unused")
	public void exportDoc() {
		try {
			// �������ļ��������ļ�·���ַ���
			String fileStr = new String(downloadFromPath.getBytes(), "GBK")+ new String("kunnrNotes.docx".getBytes(), "GBK");
			// ����·��
			String filepath = this.getClass().getResource(fileStr).toURI().getPath();
			File source = new File(filepath);
			if (!source.exists()) {
				source.mkdirs();
			}
			if (source != null) {
					display(source, "kunnrNotes.docx",
						ServletActionContext.getResponse());

			} else {
				this.setFailMessage("�ĵ����س���");
			}
		} catch (Exception e) {
			logger.error(e);
			this.setFailMessage("�ĵ����س���");
		}
	}
	
	@SuppressWarnings("finally")
	@PermissionSearch
	private boolean display(File file, String fileName, HttpServletResponse response) {
		FileInputStream in = null;
		OutputStream out = null;
		try {
			fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName);
			in = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			response.flushBuffer();
		} catch (Exception ex) {
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final Exception e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (final Exception e) {
				}
			}
			return true;
		}
	}
	public List<KunnrLogisticsArea> getlAreaList() {
		return lAreaList;
	}

	public void setlAreaList(List<KunnrLogisticsArea> lAreaList) {
		this.lAreaList = lAreaList;
	}

	public String getVkgrp() {
		return vkgrp;
	}

	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}

	public String getBzirk() {
		return bzirk;
	}

	public void setBzirk(String bzirk) {
		this.bzirk = bzirk;
	}

	public String getVkbur() {
		return vkbur;
	}

	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}

	public Kunnr getKunnr() {
		return kunnr;
	}

	public void setKunnr(Kunnr kunnr) {
		this.kunnr = kunnr;
	}

	public IWfeService getWfeServiceHessian() {
		return wfeServiceHessian;
	}

	public void setWfeServiceHessian(IWfeService wfeServiceHessian) {
		this.wfeServiceHessian = wfeServiceHessian;
	}

	public UserUtil getUserUtil() {
		return userUtil;
	}

	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getNextUserId() {
		return nextUserId;
	}

	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}

	public List<KunnrBusiness> getKunnrBusinessList() {
		return kunnrBusinessList;
	}

	public void setKunnrBusinessList(List<KunnrBusiness> kunnrBusinessList) {
		this.kunnrBusinessList = kunnrBusinessList;
	}

	public List<KunnrAddress> getKunnrAddressList() {
		return kunnrAddressList;
	}

	public void setKunnrAddressList(List<KunnrAddress> kunnrAddressList) {
		this.kunnrAddressList = kunnrAddressList;
	}

	public List<KunnrBrand> getKunnrBrandList() {
		return kunnrBrandList;
	}

	public void setKunnrBrandList(List<KunnrBrand> kunnrBrandList) {
		this.kunnrBrandList = kunnrBrandList;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public IKunnrService getKunnrService() {
		return kunnrService;
	}

	public void setKunnrService(IKunnrService kunnrService) {
		this.kunnrService = kunnrService;
	}

	public String getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(String subFolders) {
		this.subFolders = subFolders;
	}

	public KunnrBusiness getKunnrBusiness() {
		return kunnrBusiness;
	}

	public void setKunnrBusiness(KunnrBusiness kunnrBusiness) {
		this.kunnrBusiness = kunnrBusiness;
	}

	public List<Kunnr> getKunnrList() {
		return kunnrList;
	}

	public void setKunnrList(List<Kunnr> kunnrList) {
		this.kunnrList = kunnrList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getKunnrId() {
		return kunnrId;
	}

	public void setKunnrId(String kunnrId) {
		this.kunnrId = kunnrId;
	}

	public String getAttachmentsName() {
		return attachmentsName;
	}

	public void setAttachmentsName(String attachmentsName) {
		this.attachmentsName = attachmentsName;
	}

	public String getAttachmentsPath() {
		return attachmentsPath;
	}

	public void setAttachmentsPath(String attachmentsPath) {
		this.attachmentsPath = attachmentsPath;
	}

	public IChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}

	public List<Bchannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Bchannel> channelList) {
		this.channelList = channelList;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getFreezeOrClose() {
		return freezeOrClose;
	}

	public void setFreezeOrClose(String freezeOrClose) {
		this.freezeOrClose = freezeOrClose;
	}

	public List<KunnrAcount> getKunnrAcountList() {
		return kunnrAcountList;
	}

	public void setKunnrAcountList(List<KunnrAcount> kunnrAcountList) {
		this.kunnrAcountList = kunnrAcountList;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	public String getBusinessCompetent() {
		return businessCompetent;
	}

	public void setBusinessCompetent(String businessCompetent) {
		this.businessCompetent = businessCompetent;
	}

	public BooleanResult getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(BooleanResult executeResult) {
		this.executeResult = executeResult;
	}

	public String getKunnrCode() {
		return kunnrCode;
	}

	public void setKunnrCode(String kunnrCode) {
		this.kunnrCode = kunnrCode;
	}

	public String getOpera() {
		return opera;
	}

	public void setOpera(String opera) {
		this.opera = opera;
	}

	public int getAcountListSize() {
		return acountListSize;
	}

	public void setAcountListSize(int acountListSize) {
		this.acountListSize = acountListSize;
	}

	public int getAddressListSize() {
		return addressListSize;
	}

	public void setAddressListSize(int addressListSize) {
		this.addressListSize = addressListSize;
	}

	public int getBrandListSize() {
		return brandListSize;
	}

	public void setBrandListSize(int brandListSize) {
		this.brandListSize = brandListSize;
	}

	public ICrmDictService getCrmdictService() {
		return crmdictService;
	}

	public void setCrmdictService(ICrmDictService crmdictService) {
		this.crmdictService = crmdictService;
	}

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public List<CrmDict> getDictList() {
		return dictList;
	}

	public void setDictList(List<CrmDict> dictList) {
		this.dictList = dictList;
	}

	public String[] getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String[] licenseName) {
		this.licenseName = licenseName;
	}

	public File[] getLicense() {
		return license;
	}

	public void setLicense(File[] license) {
		this.license = license;
	}

	public String[] getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFileName(String[] licenseFileName) {
		this.licenseFileName = licenseFileName;
	}

	public Date[] getLicenseValid() {
		return licenseValid;
	}

	public void setLicenseValid(Date[] licenseValid) {
		this.licenseValid = licenseValid;
	}

	public List<KunnrLicense> getKunnrLicenseList() {
		return kunnrLicenseList;
	}

	public void setKunnrLicenseList(List<KunnrLicense> kunnrLicenseList) {
		this.kunnrLicenseList = kunnrLicenseList;
	}

	public int getLicenseListSize() {
		return licenseListSize;
	}

	public void setLicenseListSize(int licenseListSize) {
		this.licenseListSize = licenseListSize;
	}

	public String getDictTypeValue() {
		return dictTypeValue;
	}

	public void setDictTypeValue(String dictTypeValue) {
		this.dictTypeValue = dictTypeValue;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public KunnrSalesArea getKunnrSalesArea() {
		return kunnrSalesArea;
	}

	public void setKunnrSalesArea(KunnrSalesArea kunnrSalesArea) {
		this.kunnrSalesArea = kunnrSalesArea;
	}

	public List<KunnrSalesArea> getKunnrSalesAreaList() {
		return kunnrSalesAreaList;
	}

	public void setKunnrSalesAreaList(List<KunnrSalesArea> kunnrSalesAreaList) {
		this.kunnrSalesAreaList = kunnrSalesAreaList;
	}

	public List<BCustomerTarget> getbCustomerTargetList() {
		return bCustomerTargetList;
	}

	public void setbCustomerTargetList(List<BCustomerTarget> bCustomerTargetList) {
		this.bCustomerTargetList = bCustomerTargetList;
	}

	public BCustomerTarget getbCustomerTarget() {
		return bCustomerTarget;
	}

	public void setbCustomerTarget(BCustomerTarget bCustomerTarget) {
		this.bCustomerTarget = bCustomerTarget;
	}

	public int getKunnrSalesAreaListSize() {
		return kunnrSalesAreaListSize;
	}

	public void setKunnrSalesAreaListSize(int kunnrSalesAreaListSize) {
		this.kunnrSalesAreaListSize = kunnrSalesAreaListSize;
	}

	public int getbCustomerTargetListSize() {
		return bCustomerTargetListSize;
	}

	public void setbCustomerTargetListSize(int bCustomerTargetListSize) {
		this.bCustomerTargetListSize = bCustomerTargetListSize;
	}

	public List<KunnrLogisticsArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<KunnrLogisticsArea> areaList) {
		this.areaList = areaList;
	}

	public List<KunnrLogisticsArea> getAreaLists() {
		return areaLists;
	}

	public void setAreaLists(List<KunnrLogisticsArea> areaLists) {
		this.areaLists = areaLists;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public IGoalService getGoalService() {
		return goalService;
	}

	public void setGoalService(IGoalService goalService) {
		this.goalService = goalService;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public IOrgService getOrgServiceHessian() {
		return orgServiceHessian;
	}

	public void setOrgServiceHessian(IOrgService orgServiceHessian) {
		this.orgServiceHessian = orgServiceHessian;
	}

	public static String getCustomerManagement() {
		return customerManagement;
	}

	public static void setCustomerManagement(String customerManagement) {
		KunnrAction.customerManagement = customerManagement;
	}

	public String getCurStaId() {
		return curStaId;
	}

	public void setCurStaId(String curStaId) {
		this.curStaId = curStaId;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getBhxjFlag() {
		return bhxjFlag;
	}

	public void setBhxjFlag(String bhxjFlag) {
		this.bhxjFlag = bhxjFlag;
	}

	public String getZgFlag() {
		return zgFlag;
	}

	public void setZgFlag(String zgFlag) {
		this.zgFlag = zgFlag;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getNameUpdateFile() {
		return nameUpdateFile;
	}

	public void setNameUpdateFile(String nameUpdateFile) {
		this.nameUpdateFile = nameUpdateFile;
	}

	public String getNameUpdatePath() {
		return nameUpdatePath;
	}

	public void setNameUpdatePath(String nameUpdatePath) {
		this.nameUpdatePath = nameUpdatePath;
	}

	public String getFreezeButton() {
		return freezeButton;
	}

	public void setFreezeButton(String freezeButton) {
		this.freezeButton = freezeButton;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public File[] getNameUpload() {
		return nameUpload;
	}

	public void setNameUpload(File[] nameUpload) {
		this.nameUpload = nameUpload;
	}

	public String[] getNameUpdateFileName() {
		return nameUpdateFileName;
	}

	public void setNameUpdateFileName(String[] nameUpdateFileName) {
		this.nameUpdateFileName = nameUpdateFileName;
	}

	public String getLocco() {
		return locco;
	}

	public void setLocco(String locco) {
		this.locco = locco;
	}

	public List<KunnrSalesArea> getKillSalesArea() {
		return killSalesArea;
	}

	public void setKillSalesArea(List<KunnrSalesArea> killSalesArea) {
		this.killSalesArea = killSalesArea;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getCurStaIdFlag() {
		return curStaIdFlag;
	}

	public void setCurStaIdFlag(String curStaIdFlag) {
		this.curStaIdFlag = curStaIdFlag;
	}

	public KunnrApplySave getKunnrApplyObject() {
		return kunnrApplyObject;
	}

	public void setKunnrApplyObject(KunnrApplySave kunnrApplyObject) {
		this.kunnrApplyObject = kunnrApplyObject;
	}

	public String getCrm_xmlFilePath() {
		return crm_xmlFilePath;
	}

	public void setCrm_xmlFilePath(String crm_xmlFilePath) {
		this.crm_xmlFilePath = crm_xmlFilePath;
	}

	public List<KunnrApplySave> getKunnrApplyObjectList() {
		return kunnrApplyObjectList;
	}

	public void setKunnrApplyObjectList(List<KunnrApplySave> kunnrApplyObjectList) {
		this.kunnrApplyObjectList = kunnrApplyObjectList;
	}

	public String getXmlId() {
		return xmlId;
	}

	public void setXmlId(String xmlId) {
		this.xmlId = xmlId;
	}

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public String getWefUpdateFlag() {
		return wefUpdateFlag;
	}

	public void setWefUpdateFlag(String wefUpdateFlag) {
		this.wefUpdateFlag = wefUpdateFlag;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public IEventService getEventService() {
		return eventService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}

	public ProEventTotal getProEventTotal() {
		return proEventTotal;
	}

	public void setProEventTotal(ProEventTotal proEventTotal) {
		this.proEventTotal = proEventTotal;
	}

	public List<ProEventDetail> getEventDetailList() {
		return eventDetailList;
	}

	public void setEventDetailList(List<ProEventDetail> eventDetailList) {
		this.eventDetailList = eventDetailList;
	}

	public String getDownloadFromPath() {
		return downloadFromPath;
	}

	public void setDownloadFromPath(String downloadFromPath) {
		this.downloadFromPath = downloadFromPath;
	}

	public Integer getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(Integer staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String[] getKunnrs() {
		return kunnrs;
	}

	public void setKunnrs(String[] kunnrs) {
		this.kunnrs = kunnrs;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public IStockReportService getStockReportService() {
		return stockReportService;
	}

	public void setStockReportService(IStockReportService stockReportService) {
		this.stockReportService = stockReportService;
	}

	public List<Goal> getbSalesTargetList() {
		return bSalesTargetList;
	}

	public void setbSalesTargetList(List<Goal> bSalesTargetList) {
		this.bSalesTargetList = bSalesTargetList;
	}

	public int getbSalesTargetListSize() {
		return bSalesTargetListSize;
	}

	public void setbSalesTargetListSize(int bSalesTargetListSize) {
		this.bSalesTargetListSize = bSalesTargetListSize;
	}

	public String getAllChannelName() {
	    return allChannelName;
	}

	public void setAllChannelName(String allChannelName) {
	    this.allChannelName = allChannelName;
	}

	public AllIChannelService getAllchannelService() {
	    return allchannelService;
	}

	public void setAllchannelService(AllIChannelService allchannelService) {
	    this.allchannelService = allchannelService;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public StringResult getStringResult() {
		return stringResult;
	}

	public void setStringResult(StringResult stringResult) {
		this.stringResult = stringResult;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}
	
}