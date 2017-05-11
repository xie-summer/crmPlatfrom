$(document).ready(function() {
	loadGrid();
	loadKunnr();
	$('#hideFrame').bind('load', promgtMsg);
});

function loadGrid() {
	$('#datagrid')
			.datagrid(
					{
						iconCls : 'icon-list',
						title : '库存列表',		
						url : appUrl + '/stockReport/stockReportAction!searchStockReportSalesByCGList.jspa',
						loadMsg : '数据远程载入中,请等待...',
						singleSelect : false,
						pagination : true,
						nowrap : true,
						rownumbers : true,
						height : 420,
						width : 'auto',
						columns :[[ {
							field : 'orgRegion',
							title : '大区',
							width : 80,
							align : 'center',
							rowspan : 2
						}, {
							field : 'orgProvince',
							title : '省份',
							width : 80,
							align : 'center',
							rowspan : 2
						}, {
							field : 'orgCity',
							title : '城市',
							width : 80,
							align : 'center',
							rowspan : 2
						}, {
							field : 'kunnr',
							title : '经销商编号',
							width : 80,
							align : 'center',
							rowspan : 2
						}, {
							field : 'kunnrName',
							title : '经销商',
							width : 150,
							align : 'center',
							rowspan : 2
						}, {
							field : 'checkTime',
							title : '库存所属日期',
							width : 80,
							align : 'center',
							rowspan : 2
						}, {
							field : 'unitDesc',
							title : '单位',
							width : 80,
							align : 'center',
							rowspan : 2
						}, {
							title : '椰果单杯-30',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果联杯装-6*6',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果经典装-30',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果礼盒装-12',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果礼盒装-10',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果家庭分享装-16',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果家庭分享装-15',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '椰果联杯装-4*10',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '红豆单杯-30',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '红豆、椰果经典装-30',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '红豆家庭分享装-16',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '红豆联杯装-3*10',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '桂圆红枣单杯-30',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '红豆、桂圆红枣分享装-16',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '红豆、桂圆红枣分享装-15',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '桂圆红枣联杯装-3*10',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '豪华礼盒装15*6',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '超值组合装',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '豪华礼盒装6*12',
							width : 220,
							align : 'center',
							colspan : 4
						}, {
							title : '功夫奶茶单瓶-400ml*15',
							width : 220,
							align : 'center',
							colspan : 4
						}],[{
							field : 'lastStock1',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod1',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg1',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total1',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock2',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod2',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg2',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total2',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock3',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod3',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg3',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total3',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock4',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod4',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg4',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total4',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock5',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod5',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg5',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total5',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock6',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod6',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg6',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total6',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock7',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod7',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg7',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total7',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock8',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod8',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg8',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total8',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock9',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod9',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg9',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total9',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock10',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod10',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg10',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total10',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock11',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod11',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg11',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total11',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock12',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod12',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg12',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total12',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock13',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod13',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg13',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total13',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock14',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod14',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg14',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total14',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock15',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod15',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg15',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total15',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock16',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod16',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg16',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total16',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock17',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod17',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg17',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total17',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock18',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod18',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg18',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total18',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock19',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod19',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg19',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total19',
							title : '日分销',
							width : 60,
							align : 'center'
						}, {
							field : 'lastStock20',
							title : '上月末库存',
							width : 60,
							align : 'center'
						}, {
							field : 'pod20',
							title : 'POD',
							width : 60,
							align : 'center'
						}, {
							field : 'cg20',
							title : '最新库存',
							width : 60,
							align : 'center'
						}, {
							field : 'total20',
							title : '日分销',
							width : 60,
							align : 'center'
						}
						]]
					});
	// 分页控件
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
	
}

function loadKunnr(){

	$('#kunnr').combogrid({
		panelHeight : 250,
		panelWidth : 380,
		pagination : true,
		method : 'post',
		singleSelect : true,
		multiple : false,
		url : appUrl + '/kunnrAction!kunnrSearch.jspa?orgId='+$('#orgId').val()+'&bhxjFlag='+'C',
		idField : 'kunnr',
		textField : 'name1',
		// multiple:true,
		columns : [ 
					 [ {
			field : 'kunnr',
			title : '经销商编号',
			width : 120
		}, {
			field : 'name1',
			title : '名称',
			width : 200
		} ] ],
		toolbar : '#toolbarKonzs'
	});
}

/**
 * 所属经分销商下拉查询
 * 
 * @param name1
 */
function searcherKonzs(name1) {
	var regx = /^\d+$/;
	if(regx.test(name1)){
		var queryParams = $('#kunnr').combogrid("grid").datagrid('options').queryParams;
		queryParams.kunnrId = name1;
		queryParams.name1 = null;
		$('#kunnr').combogrid("grid").datagrid('reload');
	}else{
		var queryParams = $('#kunnr').combogrid("grid").datagrid('options').queryParams;
		queryParams.kunnrId = null;
		queryParams.name1 = encodeURIComponent(name1);
		$('#kunnr').combogrid("grid").datagrid('reload');
	}
}


function search() {
	var queryParams = $('#datagrid').datagrid('options').queryParams;
	queryParams.orgId = $('#orgId').val();
	queryParams.kunnr = $('#kunnr').combobox("getValue");
	queryParams.productionDate = $('#productionDate').val();
	$("#datagrid").datagrid('load');
}
function exportForExcel() {
	$.messager.progress();
	openTime();
	var form = window.document.forms[0];
	form.action = appUrl
			+ '/stockReport/stockReportAction!exportStockForExcel.jspa';
	form.target = "hideFrame";
	form.submit();
}
function openTime() {
	setTimeout(function() {
		var timer = setInterval(function() {
			$.ajax({
				type : "post",
				url : appUrl + "/stockReport/stockReportAction!checkDownLoadOver.jspa?",
				success : function(data) {
					if (data == 'Yes') {
						clearInterval(timer);
						$.messager.progress('close');
					}
				}
			});
		}, 100);
	}, 500);
}
function promgtMsg() {
	var hideFrame = document.getElementById("hideFrame");
	var failResult = hideFrame.contentWindow.failResult;
	var successResult = hideFrame.contentWindow.successResult;
	if (failResult) {
		$.messager.alert('Tips', failResult, 'warning');
	} else if (successResult) {
		$.messager.alert('Tips', successResult, 'info');
		$('#datagrid').datagrid('reload');
		// search();
	}
}

// 创建窗口对象
function initMaintAccount(ffit, wWidth, wHeight, title, url, l, t) {
	var url = appUrl + url;
	var WWidth = wWidth;
	var WHeight = wHeight;
	var Ffit = ffit;
	var $win = $("#maintActivityPlanInfo")
			.window(
					{
						title : title,
						width : WWidth,
						height : WHeight,
						content : '<iframe frameborder="no" width="100%" height="100%" src='
								+ url + '/>',
						shadow : true,
						fit : Ffit,
						modal : true,
						closed : true,
						closable : true,
						minimizable : false,
						maximizable : false,
						collapsible : false,
						draggable : true,
						left : l,
						top : t
					});

	$win.window('open');

}
function closeMaintActivityPlanRepoty() {
	$("#maintActivityPlanInfo").window('close');
}

function clearValue(){
	$('#skuId').combo("setValue","");
	$('#custId').combobox("setValue","");
	$('#custKunnr').combobox("setValue","");
	$('#startDate').val("");
	$('#endDate').val("");
}

function choseOrg() {
	initMaintAccount(false, '400', '400','组织选择', '/visitInfo/visitInfoAction!orgTreePage.jspa',353,70);
}
function returnValue(selectedId, selectedName) {
	$("#orgId").val(selectedId);
	$("#orgName").val(selectedName);
}

function closeOrgTree() {
	$("#maintActivityPlanInfo").window('close');
}
function reloadDatagrid() {
	self.location.reload(true);
}
function utcToDate(utcCurrTime) {
	utcCurrTime = utcCurrTime + "";
	var date = "";
	var month = new Array();
	month["Jan"] = 1;
	month["Feb"] = 2;
	month["Mar"] = 3;
	month["Apr"] = 4;
	month["May"] = 5;
	month["Jun"] = 6;
	month["Jul"] = 7;
	month["Aug"] = 8;
	month["Sep"] = 9;
	month["Oct"] = 10;
	month["Nov"] = 11;
	month["Dec"] = 12;
	var week = new Array();
	week["Mon"] = "一";
	week["Tue"] = "二";
	week["Wed"] = "三";
	week["Thu"] = "四";
	week["Fri"] = "五";
	week["Sat"] = "六";
	week["Sun"] = "日";

	str = utcCurrTime.split(" ");
	date = str[5] + "-";
	date = date + month[str[1]] + "-" + str[2];
	return date;
}

document.onkeydown = function(e) {// 这个事件在用户按下任何键盘键（包括系统按钮，如箭头键和功能键）时发生
	var theEvent = e || window.event;
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
	if (code == 13) {
		search();
		return false;
	}
	return true;
};