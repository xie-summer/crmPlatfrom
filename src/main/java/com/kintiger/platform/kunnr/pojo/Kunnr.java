package com.kintiger.platform.kunnr.pojo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.kintiger.platform.base.pojo.SearchInfo;
import com.kintiger.platform.goal.pojo.BCustomerTarget;
import com.kintiger.platform.stockReport.pojo.Goal;

/**
 * �������̻��A��Ϣ
 * 
 * @author xxping
 * 
 */

public class Kunnr extends SearchInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5575029897477626479L;
	private Long id;
	private String kunnr;
	private String kunnrCode;
	private String locco;
	private String name1;
	private String name3;
	private String sex;
	private Integer age;
	private String mobNumber;
	private String bukrs;// ��˾����
	private String vkorg;// ������֯
	private String vtweg;// ��������
	private String spart;// ��Ʒ��
	private String ktokd;// �ͻ���Ŀ��
	private Long channelId;// �ͻ�����
	private String channelName;// �ͻ��������� �ͻ�����
	private String konzs;// �ϼ�������
	private String konzsTxt;//����������
	private String kunag;// �����ϼ�������
	private String kunagTxt;//����������
	
	private String bank;
	private String bankAccount;
	private String healthNumber;
	private String stceg;// ˰��Ǽ�֤
	private String businessLicense;
	private String kverm;// ��˰������(�˻���ע)
	private Date lastAnnual;
	private String province;
	private String city;
	private String area;
	private String town;
	private String street1;// �ο�λ��
	private String kpPhone;//��Ʊ�绰
	private String telNumber;// ��˾��ϵ�绰
	private String street;// �ջ���ַ
	private String name102;// �ջ���
	private String name102tel;// �ջ��˵绰
	private String name102mob;// �ջ����ֻ�
	private String maximum;      //���ͨ�г���      add 2013/08/12   VSART
	
	private String maximumTxt;
	private String faxNumber;
	private String zip;
	private Integer businessLife;
	private Date cooperationStart;
	private Date cooperationEnd;
	private String createReason;
	private Long createUserId;
	private String createUser;
	private String createDate;
	private String endDate;
	private String status;
	private List<KunnrBusiness> kunnrbusinessList;// ��ϸ��Ϣ
	private List<KunnrAddress> kunnrAddressList;// �ջ���ַ�б�
	private List<KunnrBrand> kunnrBrandList;// ��ӪƷ���б�
	private List<KunnrAcount> kunnrAcountList;// �ۿ��б�
	private List<KunnrLicense> kunnrLicenseList;//֤���б�
	private List<KunnrSalesArea> kunnrSalesAreaList; //���۷�Χ
	private List<BCustomerTarget> targetList;  //Ŀ����
	private List<Goal> salesTargetList;
	private String businessCompetent;
	private String competentMobile;
	private String businessManager;
	private String managerMobile;
	private String businessHead;
	private String headMobile;
	private String businessAgent;
	private String agentMobile;
	private String werks;// ����
	private Double lastyearSales;
	private Double theyearSales;
	private String coverArea;
	
	private String brands; //Ʒ��
	private String brandsC;//�������޸�ʱ�ݴ�Ʒ��
	private String brandOfXpp; //��ƮƮ
	private String brandOfLfy; //����԰
	private String brandOfMeco; //meco
	private String brandsOfDM;//Ʒ�ƴ���
	
	private String killBrand;// �޸�ʱ��ɾ����Ʒ��
	private String killAcount;// �޸�ʱ��ɾ�����ۿ�
	private List<KunnrSalesArea> killSalesArea; //�޸�ʱ��ɾ�������۷�Χ
	
	private String warehouseArea;            //�ֿ����
	private String natureEnterprise;            //��ҵ����
	private String fpRecipient;       //��Ʊ�ļ���
	private String fpAddress;      //��Ʊ���͵�ַ
	private String fpContactPhone;     //��Ʊ��ϵ�˵绰
	private String orgName;             //������֯
	private String zcAddress;      //��˾ע���ַ
	
	private String noticeFile1;// ����֪ͨ��1
	private String noticeFilePath1;
	private String noticeFile2;// ����֪ͨ��2
	private String noticeFilePath2;
	private String noticeFile3;// ����֪ͨ��3
	private String noticeFilePath3;
	private String noticeFile4;// ����֪ͨ��4
	private String noticeFilePath4;
	private String nameUpdateFile;// ���Ʊ��֤��
	private String nameUpdatePath;
	
	private String checkOpId;      //������Ŀ��������� 
	
	private String orgId;
	private String orgIds;
	private String bhxjFlag;
	
	private String kunnrLeader;
	private String kunnrPhone;
	
	private String updateType;
	private String[] kunnrs;
    
	private String type; //����
	

	/**
	 * �ջ�����������
	 * 
	 * @return
	 */
	private String shProvince;
	private String shCity;
	private String shArea;
	private String shTown;
	private String shXzAddress;     //�ջ�������������
	
	private String bgXzAddress;    //�칫��������������ʡ+��+��+��
	
	private String codeSales;      //������ͼ�µ�ʡ�к���λ���ƴ���ַ���
	private String eventId;        //�����
	
	private Integer staffNumber;      //�����̹�Ա��������
	private Integer staffNumberY;     //�����̹�Ա��ռ��������
	
	public String getKillBrand() {
		return killBrand;
	}

	public void setKillBrand(String killBrand) {
		this.killBrand = killBrand;
	}

	public String getKillAcount() {
		return killAcount;
	}

	public void setKillAcount(String killAcount) {
		this.killAcount = killAcount;
	}

	public String getCompetentMobile() {
		return competentMobile;
	}

	public void setCompetentMobile(String competentMobile) {
		this.competentMobile = competentMobile;
	}

	public String getManagerMobile() {
		return managerMobile;
	}

	public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}

	public String getBusinessCompetent() {
		return businessCompetent;
	}

	public void setBusinessCompetent(String businessCompetent) {
		this.businessCompetent = businessCompetent;
	}

	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		if(null!=name1){
			name1=name1.replace(" ", "");
		}
		this.name1 = name1;
	}


	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		if(null!=name3){
			name3=name3.replace(" ", "");
		}
		this.name3 = name3;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMobNumber() {
		return mobNumber;
	}

	public void setMobNumber(String mobNumber) {
		this.mobNumber = mobNumber;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getVkorg() {
		return vkorg;
	}

	public void setVkorg(String vkorg) {
		this.vkorg = vkorg;
	}

	public String getVtweg() {
		return vtweg;
	}

	public void setVtweg(String vtweg) {
		this.vtweg = vtweg;
	}

	public String getSpart() {
		return spart;
	}

	public void setSpart(String spart) {
		this.spart = spart;
	}

	public String getKtokd() {
		return ktokd;
	}

	public void setKtokd(String ktokd) {
		this.ktokd = ktokd;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getKonzs() {
		return konzs;
	}

	public void setKonzs(String konzs) {
		this.konzs = konzs;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		if(null!=bank){
			bank=bank.replace(" ", "");
		}
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		if(null!=bankAccount){
			bankAccount=bankAccount.replace(" ", "");
		}
		this.bankAccount = bankAccount;
	}

	public String getHealthNumber() {
		return healthNumber;
	}

	public void setHealthNumber(String healthNumber) {
		if(null!=healthNumber){
			healthNumber=healthNumber.replace(" ", "");
		}
		this.healthNumber = healthNumber;
	}

	public String getStceg() {
		return stceg;
	}

	public void setStceg(String stceg) {
		this.stceg = stceg;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		if(null!=businessLicense){
			businessLicense=businessLicense.replace(" ", "");
		}
		this.businessLicense = businessLicense;
	}

	public String getKverm() {
		return kverm;
	}

	public void setKverm(String kverm) {
		this.kverm = kverm;
	}

	public Date getLastAnnual() {
		return lastAnnual;
	}

	public void setLastAnnual(Date lastAnnual) {
		this.lastAnnual = lastAnnual;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		if(null!=street1){
			street1=street1.replace(" ", "");
		}
		this.street1 = street1;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if(null!=street){
			street=street.replace(" ", "");
		}
		this.street = street;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getBusinessLife() {
		return businessLife;
	}

	public void setBusinessLife(Integer businessLife) {
		this.businessLife = businessLife;
	}

	public Date getCooperationStart() {
		return cooperationStart;
	}

	public void setCooperationStart(Date cooperationStart) {
		this.cooperationStart = cooperationStart;
	}

	public Date getCooperationEnd() {
		return cooperationEnd;
	}

	public void setCooperationEnd(Date cooperationEnd) {
		this.cooperationEnd = cooperationEnd;
	}

	public String getCreateReason() {
		return createReason;
	}

	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<KunnrBusiness> getKunnrbusinessList() {
		return kunnrbusinessList;
	}

	public void setKunnrbusinessList(List<KunnrBusiness> kunnrbusinessList) {
		this.kunnrbusinessList = kunnrbusinessList;
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

	public List<KunnrAcount> getKunnrAcountList() {
		return kunnrAcountList;
	}

	public void setKunnrAcountList(List<KunnrAcount> kunnrAcountList) {
		this.kunnrAcountList = kunnrAcountList;
	}

	public String getName102() {
		return name102;
	}

	public void setName102(String name102) {
		if(null!=name102){
			name102=name102.replace(" ", "");
		}
		this.name102 = name102;
	}

	public String getName102tel() {
		return name102tel;
	}

	public void setName102tel(String name102tel) {
		this.name102tel = name102tel;
	}

	public String getName102mob() {
		return name102mob;
	}

	public void setName102mob(String name102mob) {
		this.name102mob = name102mob;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLocco() {
		return locco;
	}

	public void setLocco(String locco) {
		this.locco = locco;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public Double getLastyearSales() {
		return lastyearSales;
	}

	public void setLastyearSales(Double lastyearSales) {
		this.lastyearSales = lastyearSales;
	}

	public Double getTheyearSales() {
		return theyearSales;
	}

	public void setTheyearSales(Double theyearSales) {
		this.theyearSales = theyearSales;
	}

	public String getCoverArea() {
		return coverArea;
	}

	public void setCoverArea(String coverArea) {
		this.coverArea = coverArea;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getKpPhone() {
		return kpPhone;
	}

	public void setKpPhone(String kpPhone) {
			if(null!=kpPhone){
				kpPhone=kpPhone.replace(" ", "");
			}
			
		this.kpPhone = kpPhone;
	}


	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<KunnrLicense> getKunnrLicenseList() {
		return kunnrLicenseList;
	}

	public void setKunnrLicenseList(List<KunnrLicense> kunnrLicenseList) {
		this.kunnrLicenseList = kunnrLicenseList;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getWarehouseArea() {
		return warehouseArea;
	}

	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}

	public String getNatureEnterprise() {
		return natureEnterprise;
	}

	public void setNatureEnterprise(String natureEnterprise) {
		this.natureEnterprise = natureEnterprise;
	}

	public String getFpRecipient() {
		return fpRecipient;
	}

	public void setFpRecipient(String fpRecipient) {
		if(null!=fpRecipient){
			fpRecipient=fpRecipient.replace(" ", "");
		}
		this.fpRecipient = fpRecipient;
	}

	public String getFpAddress() {
		return fpAddress;
	}

	public void setFpAddress(String fpAddress) {
		if(null!=fpAddress){
			fpAddress=fpAddress.replace(" ", "");
		}
		this.fpAddress = fpAddress;
	}

	public String getFpContactPhone() {
		return fpContactPhone;
	}

	public void setFpContactPhone(String fpContactPhone) {
		if(null!=fpContactPhone){
			fpContactPhone=fpContactPhone.replace(" ", "");
		}
		this.fpContactPhone = fpContactPhone;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getZcAddress() {
		return zcAddress;
	}

	public void setZcAddress(String zcAddress) {
		if(null!=zcAddress){
			zcAddress=zcAddress.replace(" ", "");
		}
		this.zcAddress = zcAddress;
	}

	public List<KunnrSalesArea> getKunnrSalesAreaList() {
		return kunnrSalesAreaList;
	}

	public void setKunnrSalesAreaList(List<KunnrSalesArea> kunnrSalesAreaList) {
		this.kunnrSalesAreaList = kunnrSalesAreaList;
	}

	
	public List<BCustomerTarget> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<BCustomerTarget> targetList) {
		this.targetList = targetList;
	}

	public String getCheckOpId() {
		return checkOpId;
	}

	public void setCheckOpId(String checkOpId) {
		this.checkOpId = checkOpId;
	}

	

	public List<KunnrSalesArea> getKillSalesArea() {
		return killSalesArea;
	}

	public void setKillSalesArea(List<KunnrSalesArea> killSalesArea) {
		this.killSalesArea = killSalesArea;
	}

	public String getNoticeFile1() {
		return noticeFile1;
	}

	public void setNoticeFile1(String noticeFile1) {
		this.noticeFile1 = noticeFile1;
	}

	public String getNoticeFilePath1() {
		return noticeFilePath1;
	}

	public void setNoticeFilePath1(String noticeFilePath1) {
		this.noticeFilePath1 = noticeFilePath1;
	}

	public String getNoticeFile2() {
		return noticeFile2;
	}

	public void setNoticeFile2(String noticeFile2) {
		this.noticeFile2 = noticeFile2;
	}

	public String getNoticeFilePath2() {
		return noticeFilePath2;
	}

	public void setNoticeFilePath2(String noticeFilePath2) {
		this.noticeFilePath2 = noticeFilePath2;
	}

	public String getNoticeFile3() {
		return noticeFile3;
	}

	public void setNoticeFile3(String noticeFile3) {
		this.noticeFile3 = noticeFile3;
	}

	public String getNoticeFilePath3() {
		return noticeFilePath3;
	}

	public void setNoticeFilePath3(String noticeFilePath3) {
		this.noticeFilePath3 = noticeFilePath3;
	}

	public String getNoticeFile4() {
		return noticeFile4;
	}

	public void setNoticeFile4(String noticeFile4) {
		this.noticeFile4 = noticeFile4;
	}

	public String getNoticeFilePath4() {
		return noticeFilePath4;
	}

	public void setNoticeFilePath4(String noticeFilePath4) {
		this.noticeFilePath4 = noticeFilePath4;
	}

	public String getKunnrLeader() {
		return kunnrLeader;
	}

	public void setKunnrLeader(String kunnrLeader) {
		this.kunnrLeader = kunnrLeader;
	}

	public String getKunnrPhone() {
		return kunnrPhone;
	}

	public void setKunnrPhone(String kunnrPhone) {
		this.kunnrPhone = kunnrPhone;
	}

	public String getMaximumTxt() {
		return maximumTxt;
	}

	public void setMaximumTxt(String maximumTxt) {
		this.maximumTxt = maximumTxt;
	}

	public String getBhxjFlag() {
		return bhxjFlag;
	}

	public void setBhxjFlag(String bhxjFlag) {
		this.bhxjFlag = bhxjFlag;
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

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getKonzsTxt() {
		return konzsTxt;
	}

	public void setKonzsTxt(String konzsTxt) {
		this.konzsTxt = konzsTxt;
	}

	public String getShProvince() {
		return shProvince;
	}

	public void setShProvince(String shProvince) {
		this.shProvince = shProvince;
	}

	public String getShCity() {
		return shCity;
	}

	public void setShCity(String shCity) {
		this.shCity = shCity;
	}

	public String getShArea() {
		return shArea;
	}

	public void setShArea(String shArea) {
		this.shArea = shArea;
	}

	public String getShTown() {
		return shTown;
	}

	public void setShTown(String shTown) {
		this.shTown = shTown;
	}

	public String getShXzAddress() {
		return shXzAddress;
	}

	public void setShXzAddress(String shXzAddress) {
		this.shXzAddress = shXzAddress;
	}

	public String getBgXzAddress() {
		return bgXzAddress;
	}

	public void setBgXzAddress(String bgXzAddress) {
		this.bgXzAddress = bgXzAddress;
	}

	public String getKunnrCode() {
		return kunnrCode;
	}

	public void setKunnrCode(String kunnrCode) {
		this.kunnrCode = kunnrCode;
	}

	public String getCodeSales() {
		return codeSales;
	}

	public void setCodeSales(String codeSales) {
		this.codeSales = codeSales;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Integer getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(Integer staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Integer getStaffNumberY() {
		return staffNumberY;
	}

	public void setStaffNumberY(Integer staffNumberY) {
		this.staffNumberY = staffNumberY;
	}

	public String[] getKunnrs() {
		return kunnrs;
	}

	public void setKunnrs(String[] kunnrs) {
		this.kunnrs = kunnrs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessHead() {
		return businessHead;
	}

	public void setBusinessHead(String businessHead) {
		this.businessHead = businessHead;
	}

	public String getHeadMobile() {
		return headMobile;
	}

	public void setHeadMobile(String headMobile) {
		this.headMobile = headMobile;
	}

	public String getBusinessAgent() {
		return businessAgent;
	}

	public void setBusinessAgent(String businessAgent) {
		this.businessAgent = businessAgent;
	}

	public String getAgentMobile() {
		return agentMobile;
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public List<Goal> getSalesTargetList() {
		return salesTargetList;
	}

	public void setSalesTargetList(List<Goal> salesTargetList) {
		this.salesTargetList = salesTargetList;
	}

	public String getKunag() {
		return kunag;
	}

	public void setKunag(String kunag) {
		this.kunag = kunag;
	}

	public String getKunagTxt() {
		return kunagTxt;
	}

	public void setKunagTxt(String kunagTxt) {
		this.kunagTxt = kunagTxt;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getBrandOfXpp() {
		return brandOfXpp;
	}

	public void setBrandOfXpp(String brandOfXpp) {
		this.brandOfXpp = brandOfXpp;
	}

	public String getBrandOfLfy() {
		return brandOfLfy;
	}

	public void setBrandOfLfy(String brandOfLfy) {
		this.brandOfLfy = brandOfLfy;
	}

	public String getBrandOfMeco() {
		return brandOfMeco;
	}

	public void setBrandOfMeco(String brandOfMeco) {
		this.brandOfMeco = brandOfMeco;
	}

	public String getBrandsOfDM() {
		return brandsOfDM;
	}

	public void setBrandsOfDM(String brandsOfDM) {
		this.brandsOfDM = brandsOfDM;
	}

	public String getBrandsC() {
		return brandsC;
	}

	public void setBrandsC(String brandsC) {
		this.brandsC = brandsC;
	}
	
}