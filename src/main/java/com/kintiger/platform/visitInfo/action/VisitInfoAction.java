package com.kintiger.platform.visitInfo.action;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kintiger.platform.base.action.BaseAction;
import com.kintiger.platform.base.pojo.StringResult;
import com.kintiger.platform.framework.annotations.Decode;
import com.kintiger.platform.framework.annotations.JsonResult;
import com.kintiger.platform.framework.annotations.PermissionSearch;
import com.kintiger.platform.order.pojo.Sku;
import com.kintiger.platform.org.service.IOrgService;
import com.kintiger.platform.visitInfo.pojo.Stock;
import com.kintiger.platform.visitInfo.pojo.VisitInfo;
import com.kintiger.platform.visitInfo.service.IVisitInfoService;

public class VisitInfoAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6850150921762033618L;
	
	private IVisitInfoService visitInfoService;
	private IOrgService orgServiceHessian;
	
	private VisitInfo visitInfo;
	private List<VisitInfo> visitInfoList;
	private String month;
	private String download;
	private String orgName;
	private String orgId;
	private String kunnr;
	@Decode
	private String userName;
	@Decode
	private String stationName;
	
	private Stock stock;
	private List<Stock> stockList;
	private String skuId;
	private String custId;
	private String custKunnr;
	private String startDate;
	private String endDate;
	private int total;
	
	private Sku sku;
	private List<Sku> skuList;
	private String skuName;
	private int skuSize;
	private String isOffice;
	

	private List<StringResult> theYearList;
	private List<StringResult> theMonthList;
	
	private String s_month;
	private String s_year;
	
	public String toVisitInfo(){
		orgId=this.getUser().getOrgId();
		orgName = orgServiceHessian
				.getOrgNameByOrgid(orgId);
		return "toVisitInfo";
	}
	
	public String toVisitInfoUser(){
		orgId=this.getUser().getOrgId();
		orgName = orgServiceHessian
				.getOrgNameByOrgid(orgId);
		return "toVisitInfoUser";
	}
	
	public String toVisitInfoTotal(){
		orgId=this.getUser().getOrgId();
		orgName = orgServiceHessian
				.getOrgNameByOrgid(orgId);
		return "toVisitInfoTotal";
	}
	
	public String toOrderInfo(){
		orgId=this.getUser().getOrgId();
		orgName = orgServiceHessian
				.getOrgNameByOrgid(orgId);
		return "toOrderInfo";
	}
	
	public String toStock(){
		orgId=this.getUser().getOrgId();
		orgName = orgServiceHessian
				.getOrgNameByOrgid(orgId);
		
		Calendar cal = Calendar.getInstance();
		theYearList = new ArrayList<StringResult>();
		theMonthList = new ArrayList<StringResult>();
		for (int i = -4; i < 5; i++) {// ��ȡ��ǰ��ݵĺ�5��
			StringResult stringResult = new StringResult();
			stringResult.setResult(String.valueOf(cal.get(Calendar.YEAR) + i));
			if (cal.get(Calendar.YEAR) + i == cal.get(Calendar.YEAR)) {
				stringResult.setCode("1");
			}
			theYearList.add(stringResult);
		}
		for (int i = 1; i < 13; i++) {// ��ȡ�·�
			StringResult stringResult = new StringResult();
			if (cal.get(Calendar.MONTH) + 1 == i) {
				stringResult.setCode("1");
			}
			stringResult.setResult(String.valueOf(i).length() == 1 ? "0"
					+ String.valueOf(i) : String.valueOf(i));
			theMonthList.add(stringResult);
		}
		if(null!=this.getUser().getIsOffice()){
			isOffice=this.getUser().getIsOffice();
			return "toStockKunnr";
		}else{
			isOffice="";
			return "toStock";
		}
	}
	
	@PermissionSearch
	@JsonResult(field = "visitInfoList", include = { "custCount", "orgName", "kunnrName",
			"kunnr", "empCount", "day1", "day2", "day3", "day4", "day5", "day6", "day7", "day8", "day9", "day10",
			"day11", "day12", "day13", "day14", "day15", "day16", "day17", "day18", "day19", "day20",
			"day21", "day22", "day23", "day24", "day25", "day26", "day27", "day28", "day29", "day30", "day31"},total = "total")
	public String searchVisitInfo(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			if(StringUtils.isEmpty(month)){
				month=sdf.format(new Date());
			}
			if(StringUtils.isEmpty(orgId)){
				orgId=this.getUser().getOrgId();
			}
			Calendar cal = Calendar.getInstance();
			Date time=sdf.parse(month);
			cal.setTime(time);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			String beginDate=sdf2.format(time);
			String endDate=sdf2.format(cal.getTime());
			visitInfo = new VisitInfo();
			visitInfo.setUserName(userName);
			visitInfo.setBeginDate(beginDate);
			visitInfo.setEndDate(endDate);
			visitInfo.setOrgId(Integer.valueOf(orgId));
			visitInfo.setKunnr(kunnr);
			visitInfo.setStart(getStart());
			visitInfo.setEnd(getEnd());
			total=visitInfoService.getVisitInfoCount(visitInfo);
			if(total>0){
				visitInfoList = visitInfoService.getVisitInfo(visitInfo);
			}
	        return JSON;
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@PermissionSearch
	@JsonResult(field = "visitInfoList", include = { "custCount", "orgName", "userName","stationName","orgRegion","orgProvince",
			"userId", "empCount","empIdCard", "day1", "day2", "day3", "day4", "day5", "day6", "day7", "day8", "day9", "day10",
			"day11", "day12", "day13", "day14", "day15", "day16", "day17", "day18", "day19", "day20",
			"day21", "day22", "day23", "day24", "day25", "day26", "day27", "day28", "day29", "day30", "day31","daySum"},total = "total")
	public String searchVisitInfoUser(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			if(StringUtils.isEmpty(month)){
				month=sdf.format(new Date());
			}
			if(StringUtils.isEmpty(orgId)){
				orgId=this.getUser().getOrgId();
			}
			Calendar cal = Calendar.getInstance();
			Date time=sdf.parse(month);
			cal.setTime(time);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			String beginDate=sdf2.format(time);
			String endDate=sdf2.format(cal.getTime());
			visitInfo = new VisitInfo();
			visitInfo.setUserName(userName);
			visitInfo.setStationName(stationName);
			visitInfo.setBeginDate(beginDate);
			visitInfo.setEndDate(endDate);
			visitInfo.setOrgId(Integer.valueOf(orgId));
			visitInfo.setStart(getStart());
			visitInfo.setEnd(getEnd());
			total=visitInfoService.getVisitInfoUserCount(visitInfo);
			if(total>0){
				visitInfoList = visitInfoService.getVisitInfoUser(visitInfo);
			}
	        return JSON;
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@PermissionSearch
	@JsonResult(field = "visitInfoList", include = { "custCount", "orgName", "userName","stationName","orgRegion","orgProvince",
			"userId", "empCount","empIdCard","daySum","empUserId","custNum","usePercent","lineNum","visitPercent"},total = "total")
	public String searchVisitInfoTotal(){
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df = new DecimalFormat("0.00");
			
			if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
				//��������·ά��ÿ�����ڼ����ֵĴ���
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf1.parse(startDate));
				int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
				int days=Integer.parseInt((sdf1.parse(endDate).getTime()-sdf1.parse(startDate).getTime())/(1000 * 60 * 60 * 24)+"");
				int[] weeks=new int[7];
				for(int i=0;i<7;i++){
					weeks[i]=days/7;
				}
				int num=0;
				if(week==0){
					num=6;
				}else{
					num=week-1;
				}
				for(int i=num;i<days%7+num;i++){
					weeks[i%7]=weeks[i%7]+1;
				}
				
				VisitInfo vWeeks = new VisitInfo();
				vWeeks.setWeek1(weeks[0]);
				vWeeks.setWeek2(weeks[1]);
				vWeeks.setWeek3(weeks[2]);
				vWeeks.setWeek4(weeks[3]);
				vWeeks.setWeek5(weeks[4]);
				vWeeks.setWeek6(weeks[5]);
				vWeeks.setWeek7(weeks[6]);
				vWeeks.setBeginDate(startDate);
				vWeeks.setEndDate(endDate);
				
				if(StringUtils.isEmpty(orgId)){
					orgId=this.getUser().getOrgId();
				}
				visitInfo = new VisitInfo();
				visitInfo.setUserName(userName);
				visitInfo.setStationName(stationName);
				visitInfo.setBeginDate(startDate);
				visitInfo.setEndDate(endDate);
				visitInfo.setOrgId(Integer.valueOf(orgId));
				visitInfo.setStart(getStart());
				visitInfo.setEnd(getEnd());
//				total=visitInfoService.getVisitInfoUserCount(visitInfo);
//				if(total>0){
					visitInfoList = visitInfoService.getVisitInfoTotal(visitInfo);
					for(VisitInfo v:visitInfoList){
						vWeeks.setUserId(v.getUserId());
						Integer lineNum=visitInfoService.getVisitInfoTotalLineNum(vWeeks);
						v.setLineNum(lineNum);
						if(lineNum!=null && lineNum!=0 && v.getDaySum()!=null){
							v.setVisitPercent(Double.parseDouble(df.format(Double.parseDouble(v.getDaySum())/lineNum*100)));
						}
					}
//				}
			}
	        return JSON;
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@PermissionSearch
	@JsonResult(field = "visitInfoList", include = { "custCount", "orgName", "kunnrName",
			"kunnr", "empCount", "day1", "day2", "day3", "day4", "day5", "day6", "day7", "day8", "day9", "day10",
			"day11", "day12", "day13", "day14", "day15", "day16", "day17", "day18", "day19", "day20",
			"day21", "day22", "day23", "day24", "day25", "day26", "day27", "day28", "day29", "day30", "day31"},total = "total")
	public String searchOrderInfo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if(StringUtils.isEmpty(month)){
			month=sdf.format(new Date());
		}
		visitInfo = new VisitInfo();
		visitInfo.setUserName(userName);
		visitInfo.setBeginDate(month);
		if(StringUtils.isNotEmpty(orgId)){
			visitInfo.setOrgId(Integer.valueOf(orgId));
		}
		visitInfo.setKunnr(kunnr);
		visitInfo.setStart(getStart());
		visitInfo.setEnd(getEnd());
		total=visitInfoService.getOrderInfoCount(visitInfo);
		if(total>0){
			visitInfoList = visitInfoService.getOrderInfo(visitInfo);
		}
		return JSON;
	}
	
	@PermissionSearch
	@JsonResult(field = "stockList", include = { "custId","custName", "empName", "skuName",
			"quantity", "unitDesc", "year", "month", "createDate",
			"RCOrgName","POrgName","orgName","kunnrName","empOrgName","stationName"},total="total")
	public String searchStock(){
		stock = new Stock();
		if(null!=this.getUser().getIsOffice()){
			isOffice=this.getUser().getIsOffice();
			}
		if(StringUtils.isNotEmpty(custId)){
			stock.setCustId(custId);
		}
		if(StringUtils.isNotEmpty(isOffice)){
			stock.setCustKunnr(isOffice);
		}
		if(StringUtils.isNotEmpty(custKunnr)){
			stock.setCustKunnr(custKunnr);
		}
		if(StringUtils.isNotEmpty(skuId)){
			stock.setSkuId(skuId);
		}
		if(StringUtils.isNotEmpty(startDate)){
			stock.setStartDate(startDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			stock.setEndDate(endDate);
		}
		if(StringUtils.isNotEmpty(orgId)){
			stock.setOrgId(orgId);
		}
		if(StringUtils.isNotEmpty(s_month)){
			stock.setMonth(s_month);
		}
		if(StringUtils.isNotEmpty(s_year)){
			stock.setYear(s_year);
		}
		stock.setStart(getStart());
		stock.setEnd(getEnd());
		
		total = visitInfoService.getStockCount(stock);
		if(total>0){
			stockList = visitInfoService.getStockList(stock);
		}
		
		return JSON;
	}
	
	public void exportStockForExcel(){
		ServletActionContext.getRequest().getSession()
		.setAttribute("DownLoad", "Ing");
		stock = new Stock();
		if(null!=this.getUser().getIsOffice()){
			isOffice=this.getUser().getIsOffice();
			}
		if(StringUtils.isNotEmpty(custId)){
			stock.setCustId(custId);
		}
		if(StringUtils.isNotEmpty(isOffice)){
			stock.setCustKunnr(isOffice);
		}
		if(StringUtils.isNotEmpty(custKunnr)){
			stock.setCustKunnr(custKunnr);
		}
		if(StringUtils.isNotEmpty(skuId)){
			stock.setSkuId(skuId);
		}
		if(StringUtils.isNotEmpty(startDate)){
			stock.setStartDate(startDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			stock.setEndDate(endDate);
		}
		if(StringUtils.isNotEmpty(orgId)){
			stock.setOrgId(orgId);
		}
		if(StringUtils.isNotEmpty(s_month)){
			stock.setMonth(s_month);
		}
		if(StringUtils.isNotEmpty(s_year)){
			stock.setYear(s_year);
		}
		stock.setStart(0);
		stock.setEnd(65530);
		stockList = visitInfoService.getStockList(stock);
		exportStock(stockList);
	}
	
	public void exportForExcel(){
		ServletActionContext.getRequest().getSession()
		.setAttribute("DownLoad", "Ing");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			if(StringUtils.isEmpty(month)){
				month=sdf.format(new Date());
			}
			if(StringUtils.isEmpty(orgId)){
				orgId=this.getUser().getOrgId();
			}
			Calendar cal = Calendar.getInstance();
			Date time=sdf.parse(month);
			cal.setTime(time);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			String beginDate=sdf2.format(time);
			String endDate=sdf2.format(cal.getTime());
			visitInfo = new VisitInfo();
			visitInfo.setUserName(userName);
			visitInfo.setBeginDate(beginDate);
			visitInfo.setEndDate(endDate);
			visitInfo.setOrgId(Integer.valueOf(orgId));
			visitInfo.setKunnr(kunnr);
			visitInfo.setStart(0);
			visitInfo.setEnd(9999);
			visitInfoList = visitInfoService.getVisitInfo(visitInfo);
			
	        export(visitInfoList);
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PermissionSearch
	@JsonResult(field = "skuList",include = { "skuId",
			"skuName","skuUnit" },total = "skuSize")
	public String getSkuName(){
		if (sku == null) {
			sku = new Sku();
		}
		if (StringUtils.isNotEmpty(skuName)) {
			sku.setSkuName(skuName);
		}
		
		sku.setStart(getStart());
		sku.setEnd(getEnd());
		skuSize = visitInfoService.getSkuCount(sku);
		if (skuSize != 0) {
			skuList = visitInfoService.getSkuNameList(sku);
		}
		return JSON;
	}
	
	public void export(List<VisitInfo> list){
		OutputStream os = null;
		WritableWorkbook wbook = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			// ȡ�������
			os = response.getOutputStream();
			// ��������
			response.reset();
			// �趨����ļ�ͷ
			String fileName = "�ݷ���Ϣͳ��.xls";
			fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			// �����������
			response.setContentType("application/msexcel");
			// ����excel�ļ�
			wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet("��һҳ", 0);
			wsheet.setRowView(0, 300);
			wsheet.setRowView(1, 300);
			wsheet.setColumnView(0, 10);
			wsheet.setColumnView(1, 50);
			wsheet.setColumnView(2, 10);
			wsheet.setColumnView(3, 10);
			wsheet.setColumnView(4, 10);
			wsheet.setColumnView(5, 10);
			wsheet.setColumnView(6, 10);
			wsheet.setColumnView(7, 10);
			wsheet.setColumnView(8, 10);
			wsheet.setColumnView(9, 10);
			wsheet.setColumnView(10, 10);
			wsheet.setColumnView(11, 10);
			wsheet.setColumnView(12, 10);
			wsheet.setColumnView(13, 10);
			wsheet.setColumnView(14, 10);
			wsheet.setColumnView(15, 10);
			wsheet.setColumnView(16, 10);
			wsheet.setColumnView(17, 10);
			wsheet.setColumnView(18, 10);
			wsheet.setColumnView(19, 10);
			wsheet.setColumnView(20, 10);
			wsheet.setColumnView(21, 10);
			wsheet.setColumnView(22, 10);
			wsheet.setColumnView(23, 10);
			wsheet.setColumnView(24, 10);
			wsheet.setColumnView(25, 10);
			wsheet.setColumnView(26, 10);
			wsheet.setColumnView(27, 10);
			wsheet.setColumnView(28, 10);
			wsheet.setColumnView(29, 10);
			wsheet.setColumnView(30, 10);
			wsheet.setColumnView(31, 10);
			wsheet.setColumnView(32, 10);
			wsheet.setColumnView(33, 10);
			wsheet.setColumnView(34, 10);
			
			wsheet.mergeCells(0, 0, 0, 1);
			wsheet.mergeCells(1, 0, 1, 1);
			wsheet.mergeCells(2, 0, 2, 1);
			wsheet.mergeCells(3, 0, 3, 1);
			wsheet.mergeCells(4, 0, 34, 0);
			
			wsheet.addCell(new Label(0, 0, "��֯"));
			wsheet.addCell(new Label(1, 0, "������"));
			wsheet.addCell(new Label(2, 0, "�����̱��"));
			wsheet.addCell(new Label(3, 0, "��Ա����"));
            wsheet.addCell(new Label(4, 0, "�ݷ��ŵ�����"));
			wsheet.addCell(new Label(4, 1, "1��"));
			wsheet.addCell(new Label(5, 1, "2��"));
			wsheet.addCell(new Label(6, 1, "3��"));
			wsheet.addCell(new Label(7, 1, "4��"));
			wsheet.addCell(new Label(8, 1, "5��"));
			wsheet.addCell(new Label(9, 1, "6��"));
			wsheet.addCell(new Label(10, 1, "7��"));
			wsheet.addCell(new Label(11, 1, "8��"));
			wsheet.addCell(new Label(12, 1, "9��"));
			wsheet.addCell(new Label(13, 1, "10��"));
			wsheet.addCell(new Label(14, 1, "11��"));
			wsheet.addCell(new Label(15, 1, "12��"));
			wsheet.addCell(new Label(16, 1, "13��"));
			wsheet.addCell(new Label(17, 1, "14��"));
			wsheet.addCell(new Label(18, 1, "15��"));
			wsheet.addCell(new Label(19, 1, "16��"));
			wsheet.addCell(new Label(20, 1, "17��"));
			wsheet.addCell(new Label(21, 1, "18��"));
			wsheet.addCell(new Label(22, 1, "19��"));
			wsheet.addCell(new Label(23, 1, "20��"));
			wsheet.addCell(new Label(24, 1, "21��"));
			wsheet.addCell(new Label(25, 1, "22��"));
			wsheet.addCell(new Label(26, 1, "23��"));
			wsheet.addCell(new Label(27, 1, "24��"));
			wsheet.addCell(new Label(28, 1, "25��"));
			wsheet.addCell(new Label(29, 1, "26��"));
			wsheet.addCell(new Label(30, 1, "27��"));
			wsheet.addCell(new Label(31, 1, "28��"));
			wsheet.addCell(new Label(32, 1, "29��"));
			wsheet.addCell(new Label(33, 1, "30��"));
			wsheet.addCell(new Label(34, 1, "31��"));
			
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10 ,WritableFont.NO_BOLD, false ,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
	        WritableCellFormat wcfF = new  WritableCellFormat(wfc);   
	        wcfF.setAlignment(jxl.format.Alignment.CENTRE);
	        wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        wcfF.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.HAIR);
			for(int i=1;i<= list.size(); i++) {
				wsheet.addCell(new Label(0,i+1,list.get(i-1).getOrgName(),wcfF));
				wsheet.addCell(new Label(1,i+1,list.get(i-1).getKunnrName(),wcfF));
				wsheet.addCell(new Label(2,i+1,list.get(i-1).getKunnr(),wcfF));
				wsheet.addCell(new Label(3,i+1,list.get(i-1).getEmpCount(),wcfF));
				wsheet.addCell(new Label(4,i+1,list.get(i-1).getDay1(),wcfF));
				wsheet.addCell(new Label(5,i+1,list.get(i-1).getDay2(),wcfF));
				wsheet.addCell(new Label(6,i+1,list.get(i-1).getDay3(),wcfF));
				wsheet.addCell(new Label(7,i+1,list.get(i-1).getDay4(),wcfF));
				wsheet.addCell(new Label(8,i+1,list.get(i-1).getDay5(),wcfF));
				wsheet.addCell(new Label(9,i+1,list.get(i-1).getDay6(),wcfF));
				wsheet.addCell(new Label(10,i+1,list.get(i-1).getDay7(),wcfF));
				wsheet.addCell(new Label(11,i+1,list.get(i-1).getDay8(),wcfF));
				wsheet.addCell(new Label(12,i+1,list.get(i-1).getDay9(),wcfF));
				wsheet.addCell(new Label(13,i+1,list.get(i-1).getDay10(),wcfF));
				wsheet.addCell(new Label(14,i+1,list.get(i-1).getDay11(),wcfF));
				wsheet.addCell(new Label(15,i+1,list.get(i-1).getDay12(),wcfF));
				wsheet.addCell(new Label(16,i+1,list.get(i-1).getDay13(),wcfF));
				wsheet.addCell(new Label(17,i+1,list.get(i-1).getDay14(),wcfF));
				wsheet.addCell(new Label(18,i+1,list.get(i-1).getDay15(),wcfF));
				wsheet.addCell(new Label(19,i+1,list.get(i-1).getDay16(),wcfF));
				wsheet.addCell(new Label(20,i+1,list.get(i-1).getDay17(),wcfF));
				wsheet.addCell(new Label(21,i+1,list.get(i-1).getDay18(),wcfF));
				wsheet.addCell(new Label(22,i+1,list.get(i-1).getDay19(),wcfF));
				wsheet.addCell(new Label(23,i+1,list.get(i-1).getDay20(),wcfF));
				wsheet.addCell(new Label(24,i+1,list.get(i-1).getDay21(),wcfF));
				wsheet.addCell(new Label(25,i+1,list.get(i-1).getDay22(),wcfF));
				wsheet.addCell(new Label(26,i+1,list.get(i-1).getDay23(),wcfF));
				wsheet.addCell(new Label(27,i+1,list.get(i-1).getDay24(),wcfF));
				wsheet.addCell(new Label(28,i+1,list.get(i-1).getDay25(),wcfF));
				wsheet.addCell(new Label(29,i+1,list.get(i-1).getDay26(),wcfF));
				wsheet.addCell(new Label(30,i+1,list.get(i-1).getDay27(),wcfF));
				wsheet.addCell(new Label(31,i+1,list.get(i-1).getDay28(),wcfF));
				wsheet.addCell(new Label(32,i+1,list.get(i-1).getDay29(),wcfF));
				wsheet.addCell(new Label(33,i+1,list.get(i-1).getDay30(),wcfF));
				wsheet.addCell(new Label(34,i+1,list.get(i-1).getDay31(),wcfF));
			}
			wbook.write();
			ServletActionContext.getRequest().getSession()
			.setAttribute("DownLoad", "Over");
		} catch (Exception e) {
//			logger.error(e);
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				wbook = null;
			}
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				os = null;
			}
		}
	}
	
	public void exportForVisitInfoUser(){
		OutputStream os = null;
		WritableWorkbook wbook = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			if(StringUtils.isEmpty(month)){
				month=sdf.format(new Date());
			}
			if(StringUtils.isEmpty(orgId)){
				orgId=this.getUser().getOrgId();
			}
			Calendar cal = Calendar.getInstance();
			Date time=sdf.parse(month);
			cal.setTime(time);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			String beginDate=sdf2.format(time);
			String endDate=sdf2.format(cal.getTime());
			visitInfo = new VisitInfo();
			visitInfo.setUserName(userName);
			visitInfo.setStationName(stationName);
			visitInfo.setBeginDate(beginDate);
			visitInfo.setEndDate(endDate);
			visitInfo.setOrgId(Integer.valueOf(orgId));
			visitInfo.setStart(0);
			visitInfo.setEnd(999999);
			List<VisitInfo> list = visitInfoService.getVisitInfoUser(visitInfo);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			// ȡ�������
			os = response.getOutputStream();
			// ��������
			response.reset();
			// �趨����ļ�ͷ
			String fileName = "��Ա�ݷ���Ϣͳ��_"+month+".xls";
			fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			// �����������
			response.setContentType("application/msexcel");
			// ����excel�ļ�
			wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet("��һҳ", 0);
			wsheet.setRowView(0, 300);
			wsheet.setRowView(1, 300);
			wsheet.setColumnView(0, 10);
			wsheet.setColumnView(1, 10);
			wsheet.setColumnView(2, 10);
			wsheet.setColumnView(3, 10);
			wsheet.setColumnView(4, 10);
			wsheet.setColumnView(5, 10);
			wsheet.setColumnView(6, 10);
			wsheet.setColumnView(7, 10);
			wsheet.setColumnView(8, 10);
			wsheet.setColumnView(9, 10);
			wsheet.setColumnView(10, 10);
			wsheet.setColumnView(11, 10);
			wsheet.setColumnView(12, 10);
			wsheet.setColumnView(13, 10);
			wsheet.setColumnView(14, 10);
			wsheet.setColumnView(15, 10);
			wsheet.setColumnView(16, 10);
			wsheet.setColumnView(17, 10);
			wsheet.setColumnView(18, 10);
			wsheet.setColumnView(19, 10);
			wsheet.setColumnView(20, 10);
			wsheet.setColumnView(21, 10);
			wsheet.setColumnView(22, 10);
			wsheet.setColumnView(23, 10);
			wsheet.setColumnView(24, 10);
			wsheet.setColumnView(25, 10);
			wsheet.setColumnView(26, 10);
			wsheet.setColumnView(27, 10);
			wsheet.setColumnView(28, 10);
			wsheet.setColumnView(29, 10);
			wsheet.setColumnView(30, 10);
			wsheet.setColumnView(31, 10);
			wsheet.setColumnView(32, 10);
			wsheet.setColumnView(33, 10);
			wsheet.setColumnView(34, 10);
			wsheet.setColumnView(35, 10);
			wsheet.setColumnView(36, 10);
			
			wsheet.mergeCells(0, 0, 0, 1);
			wsheet.mergeCells(1, 0, 1, 1);
			wsheet.mergeCells(2, 0, 2, 1);
			wsheet.mergeCells(3, 0, 3, 1);
			wsheet.mergeCells(4, 0, 4, 1);
			wsheet.mergeCells(5, 0, 5, 1);
			wsheet.mergeCells(6, 0, 36, 0);
			
			wsheet.addCell(new Label(0, 0, "����"));
			wsheet.addCell(new Label(1, 0, "ʡ��"));
			wsheet.addCell(new Label(2, 0, "����"));
			wsheet.addCell(new Label(3, 0, "�ݷ���"));
			wsheet.addCell(new Label(4, 0, "����λ"));
			wsheet.addCell(new Label(5, 0, "�ϼ�"));
            wsheet.addCell(new Label(6, 0, "�ݷ��ŵ�����"));
			wsheet.addCell(new Label(6, 1, "1��"));
			wsheet.addCell(new Label(7, 1, "2��"));
			wsheet.addCell(new Label(8, 1, "3��"));
			wsheet.addCell(new Label(9, 1, "4��"));
			wsheet.addCell(new Label(10, 1, "5��"));
			wsheet.addCell(new Label(11, 1, "6��"));
			wsheet.addCell(new Label(12, 1, "7��"));
			wsheet.addCell(new Label(13, 1, "8��"));
			wsheet.addCell(new Label(14, 1, "9��"));
			wsheet.addCell(new Label(15, 1, "10��"));
			wsheet.addCell(new Label(16, 1, "11��"));
			wsheet.addCell(new Label(17, 1, "12��"));
			wsheet.addCell(new Label(18, 1, "13��"));
			wsheet.addCell(new Label(19, 1, "14��"));
			wsheet.addCell(new Label(20, 1, "15��"));
			wsheet.addCell(new Label(21, 1, "16��"));
			wsheet.addCell(new Label(22, 1, "17��"));
			wsheet.addCell(new Label(23, 1, "18��"));
			wsheet.addCell(new Label(24, 1, "19��"));
			wsheet.addCell(new Label(25, 1, "20��"));
			wsheet.addCell(new Label(26, 1, "21��"));
			wsheet.addCell(new Label(27, 1, "22��"));
			wsheet.addCell(new Label(28, 1, "23��"));
			wsheet.addCell(new Label(29, 1, "24��"));
			wsheet.addCell(new Label(30, 1, "25��"));
			wsheet.addCell(new Label(31, 1, "26��"));
			wsheet.addCell(new Label(32, 1, "27��"));
			wsheet.addCell(new Label(33, 1, "28��"));
			wsheet.addCell(new Label(34, 1, "29��"));
			wsheet.addCell(new Label(35, 1, "30��"));
			wsheet.addCell(new Label(36, 1, "31��"));
			wsheet.addCell(new Label(37, 1, "����֤"));
			
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10 ,WritableFont.NO_BOLD, false ,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
	        WritableCellFormat wcfF = new  WritableCellFormat(wfc);   
	        wcfF.setAlignment(jxl.format.Alignment.CENTRE);
	        wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        wcfF.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.HAIR);
			for(int i=1;i<= list.size(); i++) {
				wsheet.addCell(new Label(0,i+1,list.get(i-1).getOrgRegion(),wcfF));
				wsheet.addCell(new Label(1,i+1,list.get(i-1).getOrgProvince(),wcfF));
				wsheet.addCell(new Label(2,i+1,list.get(i-1).getOrgName(),wcfF));
				wsheet.addCell(new Label(3,i+1,list.get(i-1).getUserName(),wcfF));
				wsheet.addCell(new Label(4,i+1,list.get(i-1).getStationName(),wcfF));
				wsheet.addCell(new Label(5,i+1,list.get(i-1).getDaySum(),wcfF));
				wsheet.addCell(new Label(6,i+1,list.get(i-1).getDay1(),wcfF));
				wsheet.addCell(new Label(7,i+1,list.get(i-1).getDay2(),wcfF));
				wsheet.addCell(new Label(8,i+1,list.get(i-1).getDay3(),wcfF));
				wsheet.addCell(new Label(9,i+1,list.get(i-1).getDay4(),wcfF));
				wsheet.addCell(new Label(10,i+1,list.get(i-1).getDay5(),wcfF));
				wsheet.addCell(new Label(11,i+1,list.get(i-1).getDay6(),wcfF));
				wsheet.addCell(new Label(12,i+1,list.get(i-1).getDay7(),wcfF));
				wsheet.addCell(new Label(13,i+1,list.get(i-1).getDay8(),wcfF));
				wsheet.addCell(new Label(14,i+1,list.get(i-1).getDay9(),wcfF));
				wsheet.addCell(new Label(15,i+1,list.get(i-1).getDay10(),wcfF));
				wsheet.addCell(new Label(16,i+1,list.get(i-1).getDay11(),wcfF));
				wsheet.addCell(new Label(17,i+1,list.get(i-1).getDay12(),wcfF));
				wsheet.addCell(new Label(18,i+1,list.get(i-1).getDay13(),wcfF));
				wsheet.addCell(new Label(19,i+1,list.get(i-1).getDay14(),wcfF));
				wsheet.addCell(new Label(20,i+1,list.get(i-1).getDay15(),wcfF));
				wsheet.addCell(new Label(21,i+1,list.get(i-1).getDay16(),wcfF));
				wsheet.addCell(new Label(22,i+1,list.get(i-1).getDay17(),wcfF));
				wsheet.addCell(new Label(23,i+1,list.get(i-1).getDay18(),wcfF));
				wsheet.addCell(new Label(24,i+1,list.get(i-1).getDay19(),wcfF));
				wsheet.addCell(new Label(25,i+1,list.get(i-1).getDay20(),wcfF));
				wsheet.addCell(new Label(26,i+1,list.get(i-1).getDay21(),wcfF));
				wsheet.addCell(new Label(27,i+1,list.get(i-1).getDay22(),wcfF));
				wsheet.addCell(new Label(28,i+1,list.get(i-1).getDay23(),wcfF));
				wsheet.addCell(new Label(29,i+1,list.get(i-1).getDay24(),wcfF));
				wsheet.addCell(new Label(30,i+1,list.get(i-1).getDay25(),wcfF));
				wsheet.addCell(new Label(31,i+1,list.get(i-1).getDay26(),wcfF));
				wsheet.addCell(new Label(32,i+1,list.get(i-1).getDay27(),wcfF));
				wsheet.addCell(new Label(33,i+1,list.get(i-1).getDay28(),wcfF));
				wsheet.addCell(new Label(34,i+1,list.get(i-1).getDay29(),wcfF));
				wsheet.addCell(new Label(35,i+1,list.get(i-1).getDay30(),wcfF));
				wsheet.addCell(new Label(36,i+1,list.get(i-1).getDay31(),wcfF));
				wsheet.addCell(new Label(37,i+1,list.get(i-1).getEmpIdCard(),wcfF));
			}
			wbook.write();
			ServletActionContext.getRequest().getSession()
			.setAttribute("DownLoad", "Over");
		} catch (Exception e) {
//			logger.error(e);
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				wbook = null;
			}
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				os = null;
			}
		}
	}
	
	public void exportOrderInfo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if(StringUtils.isEmpty(month)){
			month=sdf.format(new Date());
		}
		visitInfo = new VisitInfo();
		visitInfo.setUserName(userName);
		visitInfo.setBeginDate(month);
		visitInfo.setOrgId(Integer.valueOf(orgId));
		visitInfo.setKunnr(kunnr);
		visitInfo.setStart(0);
		visitInfo.setEnd(999999);
		visitInfoList = visitInfoService.getOrderInfo(visitInfo);
		
		OutputStream os = null;
		WritableWorkbook wbook = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			// ȡ�������
			os = response.getOutputStream();
			// ��������
			response.reset();
			// �趨����ļ�ͷ
			String fileName = "�µ���Ϣͳ��.xls";
			fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			// �����������
			response.setContentType("application/msexcel");
			// ����excel�ļ�
			wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet("��һҳ", 0);
			wsheet.setRowView(0, 300);
			wsheet.setRowView(1, 300);
			wsheet.setColumnView(0, 10);
			wsheet.setColumnView(1, 50);
			wsheet.setColumnView(2, 10);
			wsheet.setColumnView(3, 10);
			wsheet.setColumnView(4, 10);
			wsheet.setColumnView(5, 10);
			wsheet.setColumnView(6, 10);
			wsheet.setColumnView(7, 10);
			wsheet.setColumnView(8, 10);
			wsheet.setColumnView(9, 10);
			wsheet.setColumnView(10, 10);
			wsheet.setColumnView(11, 10);
			wsheet.setColumnView(12, 10);
			wsheet.setColumnView(13, 10);
			wsheet.setColumnView(14, 10);
			wsheet.setColumnView(15, 10);
			wsheet.setColumnView(16, 10);
			wsheet.setColumnView(17, 10);
			wsheet.setColumnView(18, 10);
			wsheet.setColumnView(19, 10);
			wsheet.setColumnView(20, 10);
			wsheet.setColumnView(21, 10);
			wsheet.setColumnView(22, 10);
			wsheet.setColumnView(23, 10);
			wsheet.setColumnView(24, 10);
			wsheet.setColumnView(25, 10);
			wsheet.setColumnView(26, 10);
			wsheet.setColumnView(27, 10);
			wsheet.setColumnView(28, 10);
			wsheet.setColumnView(29, 10);
			wsheet.setColumnView(30, 10);
			wsheet.setColumnView(31, 10);
			wsheet.setColumnView(32, 10);
			wsheet.setColumnView(33, 10);
			wsheet.setColumnView(34, 10);
			
			wsheet.mergeCells(0, 0, 0, 1);
			wsheet.mergeCells(1, 0, 1, 1);
			wsheet.mergeCells(2, 0, 2, 1);
			wsheet.mergeCells(3, 0, 3, 1);
			wsheet.mergeCells(4, 0, 34, 0);
			
			wsheet.addCell(new Label(0, 0, "��֯"));
			wsheet.addCell(new Label(1, 0, "������"));
			wsheet.addCell(new Label(2, 0, "�����̱��"));
			wsheet.addCell(new Label(3, 0, "��Ա����"));
            wsheet.addCell(new Label(4, 0, "�µ��ŵ�����"));
			wsheet.addCell(new Label(4, 1, "1��"));
			wsheet.addCell(new Label(5, 1, "2��"));
			wsheet.addCell(new Label(6, 1, "3��"));
			wsheet.addCell(new Label(7, 1, "4��"));
			wsheet.addCell(new Label(8, 1, "5��"));
			wsheet.addCell(new Label(9, 1, "6��"));
			wsheet.addCell(new Label(10, 1, "7��"));
			wsheet.addCell(new Label(11, 1, "8��"));
			wsheet.addCell(new Label(12, 1, "9��"));
			wsheet.addCell(new Label(13, 1, "10��"));
			wsheet.addCell(new Label(14, 1, "11��"));
			wsheet.addCell(new Label(15, 1, "12��"));
			wsheet.addCell(new Label(16, 1, "13��"));
			wsheet.addCell(new Label(17, 1, "14��"));
			wsheet.addCell(new Label(18, 1, "15��"));
			wsheet.addCell(new Label(19, 1, "16��"));
			wsheet.addCell(new Label(20, 1, "17��"));
			wsheet.addCell(new Label(21, 1, "18��"));
			wsheet.addCell(new Label(22, 1, "19��"));
			wsheet.addCell(new Label(23, 1, "20��"));
			wsheet.addCell(new Label(24, 1, "21��"));
			wsheet.addCell(new Label(25, 1, "22��"));
			wsheet.addCell(new Label(26, 1, "23��"));
			wsheet.addCell(new Label(27, 1, "24��"));
			wsheet.addCell(new Label(28, 1, "25��"));
			wsheet.addCell(new Label(29, 1, "26��"));
			wsheet.addCell(new Label(30, 1, "27��"));
			wsheet.addCell(new Label(31, 1, "28��"));
			wsheet.addCell(new Label(32, 1, "29��"));
			wsheet.addCell(new Label(33, 1, "30��"));
			wsheet.addCell(new Label(34, 1, "31��"));
			
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10 ,WritableFont.NO_BOLD, false ,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
	        WritableCellFormat wcfF = new  WritableCellFormat(wfc);   
	        wcfF.setAlignment(jxl.format.Alignment.CENTRE);
	        wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        wcfF.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.HAIR);
			for(int i=1;i<= visitInfoList.size(); i++) {
				wsheet.addCell(new Label(0,i+1,visitInfoList.get(i-1).getOrgName(),wcfF));
				wsheet.addCell(new Label(1,i+1,visitInfoList.get(i-1).getKunnrName(),wcfF));
				wsheet.addCell(new Label(2,i+1,visitInfoList.get(i-1).getKunnr(),wcfF));
				wsheet.addCell(new Label(3,i+1,visitInfoList.get(i-1).getEmpCount(),wcfF));
				wsheet.addCell(new Label(4,i+1,visitInfoList.get(i-1).getDay1(),wcfF));
				wsheet.addCell(new Label(5,i+1,visitInfoList.get(i-1).getDay2(),wcfF));
				wsheet.addCell(new Label(6,i+1,visitInfoList.get(i-1).getDay3(),wcfF));
				wsheet.addCell(new Label(7,i+1,visitInfoList.get(i-1).getDay4(),wcfF));
				wsheet.addCell(new Label(8,i+1,visitInfoList.get(i-1).getDay5(),wcfF));
				wsheet.addCell(new Label(9,i+1,visitInfoList.get(i-1).getDay6(),wcfF));
				wsheet.addCell(new Label(10,i+1,visitInfoList.get(i-1).getDay7(),wcfF));
				wsheet.addCell(new Label(11,i+1,visitInfoList.get(i-1).getDay8(),wcfF));
				wsheet.addCell(new Label(12,i+1,visitInfoList.get(i-1).getDay9(),wcfF));
				wsheet.addCell(new Label(13,i+1,visitInfoList.get(i-1).getDay10(),wcfF));
				wsheet.addCell(new Label(14,i+1,visitInfoList.get(i-1).getDay11(),wcfF));
				wsheet.addCell(new Label(15,i+1,visitInfoList.get(i-1).getDay12(),wcfF));
				wsheet.addCell(new Label(16,i+1,visitInfoList.get(i-1).getDay13(),wcfF));
				wsheet.addCell(new Label(17,i+1,visitInfoList.get(i-1).getDay14(),wcfF));
				wsheet.addCell(new Label(18,i+1,visitInfoList.get(i-1).getDay15(),wcfF));
				wsheet.addCell(new Label(19,i+1,visitInfoList.get(i-1).getDay16(),wcfF));
				wsheet.addCell(new Label(20,i+1,visitInfoList.get(i-1).getDay17(),wcfF));
				wsheet.addCell(new Label(21,i+1,visitInfoList.get(i-1).getDay18(),wcfF));
				wsheet.addCell(new Label(22,i+1,visitInfoList.get(i-1).getDay19(),wcfF));
				wsheet.addCell(new Label(23,i+1,visitInfoList.get(i-1).getDay20(),wcfF));
				wsheet.addCell(new Label(24,i+1,visitInfoList.get(i-1).getDay21(),wcfF));
				wsheet.addCell(new Label(25,i+1,visitInfoList.get(i-1).getDay22(),wcfF));
				wsheet.addCell(new Label(26,i+1,visitInfoList.get(i-1).getDay23(),wcfF));
				wsheet.addCell(new Label(27,i+1,visitInfoList.get(i-1).getDay24(),wcfF));
				wsheet.addCell(new Label(28,i+1,visitInfoList.get(i-1).getDay25(),wcfF));
				wsheet.addCell(new Label(29,i+1,visitInfoList.get(i-1).getDay26(),wcfF));
				wsheet.addCell(new Label(30,i+1,visitInfoList.get(i-1).getDay27(),wcfF));
				wsheet.addCell(new Label(31,i+1,visitInfoList.get(i-1).getDay28(),wcfF));
				wsheet.addCell(new Label(32,i+1,visitInfoList.get(i-1).getDay29(),wcfF));
				wsheet.addCell(new Label(33,i+1,visitInfoList.get(i-1).getDay30(),wcfF));
				wsheet.addCell(new Label(34,i+1,visitInfoList.get(i-1).getDay31(),wcfF));
			}
			wbook.write();
			ServletActionContext.getRequest().getSession()
			.setAttribute("DownLoad", "Over");
		} catch (Exception e) {
//			logger.error(e);
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				wbook = null;
			}
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				os = null;
			}
		}
	}
	
	public void exportStock(List<Stock> list){
		OutputStream os = null;
		WritableWorkbook wbook = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			// ȡ�������
			os = response.getOutputStream();
			// ��������
			response.reset();
			// �趨����ļ�ͷ
			String fileName = "������Ϣ.xls";
			fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			// �����������
			response.setContentType("application/msexcel");
			// ����excel�ļ�
			wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet("��һҳ", 0);
			wsheet.setRowView(0, 300);
			wsheet.setRowView(1, 300);
			wsheet.setColumnView(0, 10);
			wsheet.setColumnView(1, 10);
			wsheet.setColumnView(2, 10);
			wsheet.setColumnView(3, 30);
			wsheet.setColumnView(4, 10);
			wsheet.setColumnView(5, 20);
			wsheet.setColumnView(6, 10);
			wsheet.setColumnView(7, 10);
			wsheet.setColumnView(8, 10);
			wsheet.setColumnView(9, 20);
			wsheet.setColumnView(10, 10);
			wsheet.setColumnView(11, 8);
			wsheet.setColumnView(12, 8);
			wsheet.setColumnView(13, 8);
			wsheet.setColumnView(14, 20);
	
			
			wsheet.addCell(new Label(0, 0, "ʡ��"));
			wsheet.addCell(new Label(1, 0, "ʡ��"));
			wsheet.addCell(new Label(2, 0, "����"));
			wsheet.addCell(new Label(3, 0, "������"));
			wsheet.addCell(new Label(4, 0, "�ŵ�Id"));
			wsheet.addCell(new Label(5, 0, "�ŵ�"));
			wsheet.addCell(new Label(6, 0, "��������֯"));
			wsheet.addCell(new Label(7, 0, "�����˸�λ"));
			wsheet.addCell(new Label(8, 0, "������"));
			wsheet.addCell(new Label(9, 0, "Ʒ��"));
			wsheet.addCell(new Label(10, 0, "����"));
            wsheet.addCell(new Label(11, 0, "��λ"));
			wsheet.addCell(new Label(12, 0, "��"));
			wsheet.addCell(new Label(13, 0, "��"));
			wsheet.addCell(new Label(14, 0, "��������"));
			
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10 ,WritableFont.NO_BOLD, false ,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
	        WritableCellFormat wcfF = new  WritableCellFormat(wfc);   
	        wcfF.setAlignment(jxl.format.Alignment.CENTRE);
	        wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        wcfF.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.HAIR);
			for(int i=1;i<= list.size(); i++) {
				wsheet.addCell(new Label(0,i,list.get(i-1).getRCOrgName(),wcfF));
				wsheet.addCell(new Label(1,i,list.get(i-1).getPOrgName(),wcfF));
				wsheet.addCell(new Label(2,i,list.get(i-1).getOrgName(),wcfF));
				wsheet.addCell(new Label(3,i,list.get(i-1).getKunnrName(),wcfF));
				wsheet.addCell(new Label(4,i,list.get(i-1).getCustId(),wcfF));
				wsheet.addCell(new Label(5,i,list.get(i-1).getCustName(),wcfF));
				wsheet.addCell(new Label(6,i,list.get(i-1).getEmpOrgName(),wcfF));
				wsheet.addCell(new Label(7,i,list.get(i-1).getStationName(),wcfF));
				wsheet.addCell(new Label(8,i,list.get(i-1).getEmpName(),wcfF));
				wsheet.addCell(new Label(9,i,list.get(i-1).getSkuName(),wcfF));
				wsheet.addCell(new Label(10,i,list.get(i-1).getQuantity(),wcfF));
				wsheet.addCell(new Label(11,i,list.get(i-1).getUnitDesc(),wcfF));
				wsheet.addCell(new Label(12,i,list.get(i-1).getYear(),wcfF));
				wsheet.addCell(new Label(13,i,list.get(i-1).getMonth(),wcfF));
				wsheet.addCell(new Label(14,i,list.get(i-1).getCreateDate(),wcfF));
			}
			wbook.write();
			ServletActionContext.getRequest().getSession()
			.setAttribute("DownLoad", "Over");
		} catch (Exception e) {
//			logger.error(e);
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				wbook = null;
			}
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
//					logger.error(e);
				}
				os = null;
			}
		}
	}
	
	/**
	 * У�������Ƿ��������
	 * 
	 * @return
	 */
	@PermissionSearch
	@JsonResult(field = "download")
	public String checkDownLoadOver() {
		Object obj = ServletActionContext.getRequest().getSession()
				.getAttribute("DownLoad");
		if (obj == null || "Ing".equals(obj)) {
			download = "No";
		} else {
			download = "Yes";
		}
		return JSON;
	}
	
	public String orgTreePage() {
		return "orgTree";
	}

	public IVisitInfoService getVisitInfoService() {
		return visitInfoService;
	}

	public void setVisitInfoService(IVisitInfoService visitInfoService) {
		this.visitInfoService = visitInfoService;
	}

	public VisitInfo getVisitInfo() {
		return visitInfo;
	}

	public void setVisitInfo(VisitInfo visitInfo) {
		this.visitInfo = visitInfo;
	}
	
	 public List<VisitInfo> getVisitInfoList() {
		return visitInfoList;
	}
	public void setVisitInfoList(List<VisitInfo> visitInfoList) {
		this.visitInfoList = visitInfoList;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
    public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
		public void addActionError(String anErrorMessage){
		 String s=anErrorMessage;
		 System.out.println(s);
		 }
		 public void addActionMessage(String aMessage){
		 String s=aMessage;
		 System.out.println(s);
		
		 }
		 public void addFieldError(String fieldName, String errorMessage){
		 String s=errorMessage;
		 String f=fieldName;
		 System.out.println(s);
		 System.out.println(f);
		
		 }
	public IOrgService getOrgServiceHessian() {
		return orgServiceHessian;
	}
	public void setOrgServiceHessian(IOrgService orgServiceHessian) {
		this.orgServiceHessian = orgServiceHessian;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustKunnr() {
		return custKunnr;
	}

	public void setCustKunnr(String custKunnr) {
		this.custKunnr = custKunnr;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public List<Sku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<Sku> skuList) {
		this.skuList = skuList;
	}

	public int getSkuSize() {
		return skuSize;
	}

	public void setSkuSize(int skuSize) {
		this.skuSize = skuSize;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getIsOffice() {
		return isOffice;
	}

	public void setIsOffice(String isOffice) {
		this.isOffice = isOffice;
	}

	public List<StringResult> getTheYearList() {
		return theYearList;
	}

	public void setTheYearList(List<StringResult> theYearList) {
		this.theYearList = theYearList;
	}

	public List<StringResult> getTheMonthList() {
		return theMonthList;
	}

	public void setTheMonthList(List<StringResult> theMonthList) {
		this.theMonthList = theMonthList;
	}

	public String getS_month() {
		return s_month;
	}

	public void setS_month(String s_month) {
		this.s_month = s_month;
	}

	public String getS_year() {
		return s_year;
	}

	public void setS_year(String s_year) {
		this.s_year = s_year;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}