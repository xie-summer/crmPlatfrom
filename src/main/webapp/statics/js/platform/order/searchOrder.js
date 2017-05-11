$(document).ready(function() {
	loadGrid();
	$('#hideFrame').bind('load', promgtMsg);
});

function loadGrid() {
	//$.messager.alert('��ʾ', 'δѡ���κ�Ȩ�޵㣡', '��ʾ');
	if($("#loginId").val()=="admin" || ($("#isOffice").val()!=null&&$("#isOffice").val()!="")){
		$('#order_list')
		.datagrid(
				{
					iconCls : 'icon-list',
					title : '��ѯ���',
					height : height,
					striped : true,
					url : appUrl
							+ '/orderAction!searchOrder.jspa',
							queryParams: {
								orgId: $("#orgId").val(),
								date: new Date()
							},
					loadMsg : '����Զ��������,��ȴ�...',
					singleSelect : false,
					nowrap : true,
					pagination : true,
					rownumbers : true,
					columns : [ [
//							{
//								field : 'ck',
//								align : 'center',
//								checkbox : true
//							},
							/*
								 * , { id : 'dictTypeId', title : '���',
								 * field : 'dictTypeId', width :
								 * setColumnWidth(0.1), align : 'center',
								 * sortable : true }
								 */
							{
									field : 'oper',
									title : '����',
									width : 100,
									align : 'center',
									formatter : function(value, row, rec) {
										var detailId = row.orderDetailId;
										var id = row.orderId;
										var linkA = '<img style="cursor:pointer" onclick="edit('
											        + id
											        + ')" title="�鿴����" src='
											        + imgUrl
											        + '/images/actions/action_view.png align="absMiddle"></img>';
										var linkB = '<a href="javascript:printView('
											        + id
											        + ')">��ӡ</a>';
										return linkA + '&nbsp;&nbsp;' + linkB;
									}
							},
							{
								title : '������',
								field : 'orderId',
								sortable : true,
								width : 70,
								align : 'center'
							},
			                {
			                	title : '�ͻ�����',
			                	field : 'custName',
			                	sortable : true,
			                	width : 150,
								align : 'center',
			                },{
			                	title : '�µ���',
			                	field : 'userName',
			                	sortable : true,
			                	width : 130,
								align : 'center'
			                },{
			                	title : '�µ�����֯',
			                	field : 'orgName',
			                	sortable : true,
			                	width : 100,
								align : 'center'
			                },{
			                	title : '����״̬',
			                	field : 'status',
			                	width :70,
								align : 'center',
								formatter : function(value) {
			                		if(value=="U"){
			                			return "����";
			                		}else if(value=="D"){
			                			return "����";
			                		}
			                		
			                	}
			                },{
			                	title : '�Ƿ��ͻ�',
			                	field : 'orderStatus',
			                	sortable : true,
			                	width :80,
								align : 'center',
								formatter : function(value) {
			                		if(value=="Y"){
			                			return "����";
			                		}else if(value=="N"){
			                			return "δ��";
			                		}
			                		
			                	}
			                },{
			                	title : '�Ƿ��տ�',
			                	field : 'orderFundsStatus',
			                	sortable : true,
			                	width :80,
								align : 'center',
								formatter : function(value) {
			                		if(value=="Y"){
			                			return "�Ѵ��";
			                		}else if(value=="N"){
			                			return "δ���";
			                		}
			                		
			                	}
			                },{
			                	title : '��������',
			                	field : 'orderDesc',
			                	sortable : true,
			                	width :150,
								align : 'center'
			                },
							{
			                	title : '��������ʱ��',
			                	field : 'createDate',
								width : 220,
								sortable : true,
								align : 'center'
//								formatter : function(value) {
//									return utcToDate(value.replace(
//										       /\/Date\((\d+)\+\d+\)\//gi,
//								               "new Date($1)"));
//								}
							}
							] ],
					toolbar : [ "-", {
						text : '����',
						iconCls : 'icon-add',
						handler : function() {
							add();
						}
					}, "-"
					, 
					           {
						text : '��������',
						iconCls : 'icon-download ',
						handler : function() {
							exportOrder();
						}
					}
					, "-" 
					]
				});	
	}else{
		$('#order_list')
		.datagrid(
				{
					iconCls : 'icon-list',
					title : '��ѯ���',
					height : height,
					striped : true,
					url : appUrl
							+ '/orderAction!searchOrder.jspa',
							queryParams: {
								orgId: $("#orgId").val(),
								date: new Date()
							},
					loadMsg : '����Զ��������,��ȴ�...',
					singleSelect : false,
					nowrap : true,
					pagination : true,
					rownumbers : true,
					columns : [ [
//							{
//								field : 'ck',
//								align : 'center',
//								checkbox : true
//							},
							/*
								 * , { id : 'dictTypeId', title : '���',
								 * field : 'dictTypeId', width :
								 * setColumnWidth(0.1), align : 'center',
								 * sortable : true }
								 */
							{
									field : 'oper',
									title : '����',
									width : 100,
									align : 'center',
									formatter : function(value, row, rec) {
										var detailId = row.orderDetailId;
										var id = row.orderId;
										var linkA = '<img style="cursor:pointer" onclick="edit('
											        + id
											        + ')" title="�鿴����" src='
											        + imgUrl
											        + '/images/actions/action_view.png align="absMiddle"></img>';
										var linkB = '<a href="javascript:printView('
											        + id
											        + ')">��ӡ</a>';
										return linkA + '&nbsp;&nbsp;' + linkB;
									}
							},
							{
								title : '������',
								field : 'orderId',
								sortable : true,
								width : 70,
								align : 'center'
							},
			                {
			                	title : '�ͻ�����',
			                	field : 'custName',
			                	sortable : true,
			                	width : 150,
								align : 'center',
			                },{
			                	title : '�µ���',
			                	field : 'userName',
			                	sortable : true,
			                	width : 130,
								align : 'center'
			                },{
			                	title : '�µ�����֯',
			                	field : 'orgName',
			                	sortable : true,
			                	width : 100,
								align : 'center'
			                },{
			                	title : '����״̬',
			                	field : 'status',
			                	width :70,
								align : 'center',
								formatter : function(value) {
			                		if(value=="U"){
			                			return "����";
			                		}else if(value=="D"){
			                			return "����";
			                		}
			                		
			                	}
			                },{
			                	title : '�Ƿ��ͻ�',
			                	field : 'orderStatus',
			                	sortable : true,
			                	width :80,
								align : 'center',
								formatter : function(value) {
			                		if(value=="Y"){
			                			return "����";
			                		}else if(value=="N"){
			                			return "δ��";
			                		}
			                		
			                	}
			                },{
			                	title : '�Ƿ��տ�',
			                	field : 'orderFundsStatus',
			                	sortable : true,
			                	width :80,
								align : 'center',
								formatter : function(value) {
			                		if(value=="Y"){
			                			return "�Ѵ��";
			                		}else if(value=="N"){
			                			return "δ���";
			                		}
			                		
			                	}
			                },{
			                	title : '��������',
			                	field : 'orderDesc',
			                	sortable : true,
			                	width :150,
								align : 'center'
			                },
							{
			                	title : '��������ʱ��',
			                	field : 'createDate',
								width : 220,
								sortable : true,
								align : 'center'
//								formatter : function(value) {
//									return utcToDate(value.replace(
//										       /\/Date\((\d+)\+\d+\)\//gi,
//								               "new Date($1)"));
//								}
							}
							] ],
					toolbar : [
					           {
						text : '��������',
						iconCls : 'icon-download ',
						handler : function() {
							exportOrder();
						}
					}
					, "-" 
					]
				});
	}

	// ��ҳ�ؼ�
	var p = $('#order_list').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		beforePageText : '��',
		afterPageText : 'ҳ    �� {pages} ҳ',
		displayMsg : '��ǰ��ʾ {from} - {to} ����¼   �� {total} ����¼'
	});
}

function search() {
	var queryParams = $('#order_list').datagrid('options').queryParams;
	queryParams.orgId = $("#orgId").val();
	queryParams.custId = $("#custId").val();
	queryParams.custName = encodeURIComponent($("#custName").val());
	queryParams.beginDate = $("#beginDate").datebox("getValue");
	queryParams.endDate = $("#endDate").datebox("getValue");
	queryParams.custState = $("#custState").combobox("getValue");
	queryParams.contactName = encodeURIComponent($("#contactName").val());
	queryParams.userName = encodeURIComponent($("#userName").val());
	$("#order_list").datagrid('load');
}
/**
 * ����֯��
 */
function selectOrgTree2() {
	initMaintWindow('ѡ����֯', '/customerAction!orgTreePage.jspa', 400, 460);
}
/**
 * ��֯��ѡ��֯���ص������
 * 
 * @param selectedId
 * @param selectedName
 */
function returnValue(selectedId, selectedName) {
	$("#orgId").val(selectedId);
	$("#orgName").val(selectedName);
	 
}
// �������ڶ���
function initMaintWindow(title, url, width, height) {
	var url = appUrl + url;
	var WWidth = width;
	var WHeight = height;
	var $win = $("#maintWindow")
			.window(
					{
						title : title,
						width : WWidth,
						height : WHeight,
						content : '<iframe frameborder="no" width="100%" height="100%" src='
								+ url + '/>',
						shadow : true,
						modal : true,
						closed : true,// /
						closable : true,//
						minimizable : false,
						maximizable : true,
						collapsible : true,
						draggable : true
					//
					});

	$win.window('open');
}

function add() {
 
	initMaintWindow('��������', '/orderAction!toCreateOrder.jspa', 1100, 520);
}

function edit(orderId) {

//	initMaintWindow('������Ϣ�޸�',
//			'/orderAction!toUpdateOrder.jspa?orderId='+ orderId, 1170, 520);
	var WWidth = 1200;
	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);
	window.open(appUrl + '/orderAction!toUpdateOrder.jspa?orderId='+orderId, "������Ϣ�޸�",
			"left=" + WLeft + ",top=0" + ",width=" + WWidth + ",height="
			+ (window.screen.height - 100)
			+ ",toolbar=no,rolebar=no,scrollbars=yes,location=no,menubar=no,resizable=yes,titlebar=no,channelmode=yes,fullscreen=yes");
}

function toPay(orderId) {
	initMaintWindow('֧����Ϣ',
			'/orderNewAction!toPay.jspa?orderId='+ orderId, 400, 520);
}

function exportOrder(){
	$.messager.progress();
	openTime();
	var form = window.document.forms[0];
	form.action = appUrl
			+ '/orderAction!exportForExcel.jspa';
	form.target = "hideFrame";
	form.submit();
}

function openTime() {
	setTimeout(function() {
		var timer = setInterval(function() {
			$.ajax({
				type : "post",
				url : appUrl + "/orderAction!checkDownLoadOver.jspa?",
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

function selectOrgTree() {
	initMaintWindow('ѡ����֯', '/questionAction!orgTreePage.jspa', 400, 460);
}
function returnValue(selectedId, selectedName) {
	document.getElementById('orgId').value = selectedId;
	document.getElementById('orgName').value = selectedName;
}

function closeOrgTree() {
	closeDtPlan();
}
function closeDtPlan() {
	$("#maintWindow").window('close');
}

function printView(orderId){
	var WWidth = 1000;
	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);
	window.open(appUrl + '/financialCheck/orderAction!toPrintOrder.jspa?orderId='+orderId, "������Ϣ",
			"left=" + WLeft + ",top=20" + ",width=" + WWidth + ",height="
			+ (window.screen.height - 100)
			+ ",toolbar=no,rolebar=no,scrollbars=yes,location=no,menubar=no,resizable=yes,titlebar=no");
}

//function remove() {
//	var ids = new Array();
//	var rows = $('#question_list').datagrid('getSelections');
//	for ( var i = 0; i < rows.length; i++) {
//		ids[i] = rows[i].qId;
//	}
////	if (ids == '') {
//	if (ids.length==0) {
//		$.messager.alert('��ʾ', 'δѡ���κ�Ȩ�޵㣡', '��ʾ');
//		return;
//	} else {
//		$.messager
//				.confirm(
//						'Confirm',
//						'�Ƿ�ȷ������ɾ��?',
//						function(r) {
//							if (r) {
//								var idsJson="[" + ids + "]";
//								var form = window.document.forms[0];
//								form.action = appUrl
//										+ '/question/questionAction!deleteQuestion.jspa?deleteQuestionId='
//										+ idsJson;
//								form.target = "hideFrame";
//								form.submit();
//							}
//						});
//	}
//
//}

function clearValue(){
	$("#orgName").val("");
	$("#orgId").val("");
	$("#custName").val("");
	$("#beginDate").datebox().next('span').find('input').val("");
	$("#endDate").datebox().next('span').find('input').val("");
	$('#custId').val("");
	$("#custState").combobox().next('span').find('input').val("");
	$("#contactName").val("");
	$("#userName").val("");
}

// �رմ���ҳ��
function closeMaintWindow() {
	$("#maintWindow").window('close');
}

function promgtMsg() {
	var hideFrame = document.getElementById("hideFrame");
	var failResult = hideFrame.contentWindow.failResult;
	var successResult = hideFrame.contentWindow.successResult;
	if (failResult) {
		$.messager.alert('Tips', failResult, 'warning');
	} else if (successResult) {
		$.messager.alert('Tips', successResult, 'info');
		search();
	}
}

/*
 * document.onkeydown = function(e) { var theEvent = e || window.event; var code =
 * theEvent.keyCode || theEvent.which || theEvent.charCode; if (code == 13) {
 * search(); return false; } return true; };
 */

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
	week["Mon"] = "һ";
	week["Tue"] = "��";
	week["Wed"] = "��";
	week["Thu"] = "��";
	week["Fri"] = "��";
	week["Sat"] = "��";
	week["Sun"] = "��";

	str = utcCurrTime.split(" ");
	date = str[5] + "-";
	date = date + month[str[1]] + "-" + str[2] + " " + str[3];
	return date;
}

function setColumnWidth(percent) {
	return $(this).width() * percent;
}

document.onkeydown = function(e) {
	var theEvent = e || window.event;
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
	if (code == 13) {
		search();
		return false;
	}
	return true;
};