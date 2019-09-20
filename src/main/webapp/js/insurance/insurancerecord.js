$(function () {
    $("#jqGrid").jqGrid({
        url: '../insurancerecord/list',
        datatype: "json",
        colModel: [	{ label: 'id', name: 'insuranceRecordId', width: 80 ,hidden: true},
                        { label: 'id', name: 'insuranceStatus', width: 80 ,hidden: true},
						{ label: '姓名', name: 'nickName', width: 60 },
                        { label: '身份证', name: 'idNumber', width: 150 },
                        { label: '号码', name: 'mobile', width: 120 },
                        { label: '所属校区', name: 'deptName', width: 140 },
						{ label: 'NC报名表号', name: 'ncCode', width: 150 },
						{ label: '班型', name: 'ncCommodityName', width: 150 },
						{ label: '科目数量', name: 'subjectQty', width:90 },
						{ label: '所属专业', name: 'majorName', width: 120 },
						{ label: '学费', name: 'tuitionFee', width: 60 },
						{ label: '保费', name: 'premium', width: 60 },
                        { label: '报读日期', name: 'creationTime', width: 120 },
						{ label: '投保日期', name: 'attendanceDays', width: 120 },
			            { label: '单科赔付金额', name: 'compensationAmount', width: 60 },
                        { label: '保单号', name: 'policyNo', width: 80 },
						{ label: '保险要求天数', name: 'totalDays', width: 60 },
						{ label: '已达标天数', name: 'watchDays', width: 60 },
						{ label: '未完成天数', name: 'unfinishedDays', width: 60 },
						{ label: '保险生效日期', name: 'effectDateStr', width: 120 },
						{ label: '保险截止日期', name: 'expireDateStr', width: 120 },
                        { label: '通过状态', name: 'passStatusStr', width: 80 },
                        { label: '通过时间', name: 'passTime', width: 80 },
                        { label: '投保状态', name: 'insuranceStatusStr', width: 60 },
                        { label: '班主任', name: 'teacherNickName', width: 80 },
						{
							label: '签署状态', name: 'contractStatus', width: 60, formatter(value, options, row) {
								if (value == 2) {
									return '<span class="label label-danger">已驳回</span>';
								}
								if (value == 1) {
									return '<span class="label label-success">已签署</span>';
								}
								return '<span class="label label-danger">未签署</span>';
							}
						},
						{
							label: '在线签约时间', name: 'contractTs', width: 120, formatter(value, options, row) {
								if (row.contractStatus == 1) {
									return value;
								} else {
									return "";
								}
							}
						},
                        { label: '投保明细', name: '查看', width: 60 ,formatter: function(value, options, row){
                                return '<button    style="width:50px;height:25px"  onclick="viewCourse(' + row.insuranceRecordId + ',' +row.insuranceStatus+ ',\'' +row.policyNo+ '\')">查看</button>';
                        }},
                        {
                            label: '操作', name: 'viewDetail', width: 200, formatter: function (value, options, row) {
                                var html = '';
                                if(row.insuranceStatus != null && row.insuranceStatus == 1){
                                    html += '<button style="width:50px;height:25px;" onclick="passCancel(' + row.insuranceRecordId +')" disabled="true">未通过</button> ';
                                }else{
                                    if(row.passStatus==1){
                                        html += '<button style="width:50px;height:25px" onclick="passCancel(' + row.insuranceRecordId +')">未通过</button> ';
                                    }else{
                                        html += '<button style="width:50px;height:25px" onclick="pass(' + row.insuranceRecordId +')">通过</button> ';
                                    }
                                }

                                if(row.contractId != null && row.contractStatus == 1){
                                    html +=  '<button style="width:80px;height:25px" onclick="viewDetail(' + row.contractId + ',' + row.signeId + ' )">查看协议</button> <button style="width:50px;height:25px" onclick="refuseSign(' + row.contractId + ')">驳回</button>';
                                }
                                return html;
                            }
                        }

							
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,100,500,1000,5000],
        rownumbers: true, 
        rownumWidth: 35,
        shrinkToFit:false,
        autoScroll: true,
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order",

        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	// $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		insuranceRecord: {},
		q:{
            orderNo:"",
            mobile:"",
            nickName:"",
            ncCode:"",
            deptIdList:"",
            deptNameList:"",
            insuranceStatus:"",
            contractStatus: "",
            teacherName:"",
            teacherId:"",
            passStatus:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		reload: function (e , p) {
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:vm.q
            }).trigger("reloadGrid");
		},
        clearQuery:function () {
			vm.q = {
                orderNo:"",
                mobile:"",
                nickName:"",
                ncCode:"",
                deptIdList:"",
                deptNameList:"",
                insuranceStatus:"",
                contractStatus: "",
                teacherName:"",
                teacherId:"",
                passStatus:""
			};
            vm.reload(null,1);
        },
        classTeacherLayerShow : function(){//班主任
            teacherLay.show(function(index,opt){
                vm.q.teacherId = opt.userId;
                vm.q.teacherName=opt.nickName;
            } , 2);
        },
        deptLayerShowQuery : function(){//部门查询条件
            deptQueryLay.show(function(opt,deptIdList,deptNameList){
                //vm.q.deptId = id;
                //vm.q.deptName = name;
                vm.q.deptIdList = deptIdList.join(",");
                vm.q.deptNameList = deptNameList;
                console.log(deptIdList);
                console.log(deptNameList);
                //alert(vm.q.deptIdList+"======")
            });
        }
		
	}
});

var viewCourse = function(insuranceRecordId,insuranceStatus,policyNo){
    // if(insuranceStatus==0){
    //     alert("该学员未投保，请选择已投保的记录");
    //     return;
    // }
    // if(insuranceStatus==2){
    //     alert("该学员投保失败，请选择已投保的记录");
    //     return;
    // }
    if(policyNo=="null"){
        policyNo = "--";
    }
    classLay.show(insuranceRecordId,policyNo);
};

function viewDetail(contractId, signeId) {
    var netTab = window.open('about:blank');
    var params = {"companyId": signeId};
    var tokenUnableListener = function (obj) {//当token不合法时，SDK会回调此方法
        $.ajax({
            type: 'POST',
            url: '/contract/getToken',
            cache: false,
            dataType: 'json',
            data: JSON.stringify(params), //第三方获取token需要的参数
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
            },
            success: function (data, textStatus, request) {
                YHT.setToken(data.msg); //重新设置token，从请求头获取
                YHT.do(obj);//调用此方法，会继续执行上次未完成的操作
            },
            error: function (data) {
                alert(data);
            }
        });
    }

    YHT.init("AppID", tokenUnableListener); //必须初始化YHT

    console.log(contractId);
    //获取预览合同的URL
    var url = 'www.baidu.com'
    console.log(YHT)
    YHT.queryContract(
        function successFun(url) {
            netTab.location.href = url;
            YHT.setToken('eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJiR0JLbGM2Z0pSczRnUXFsNkpIc2xRQ2lwZkswSjRVOVRNRHZqOHBYdCs4VlczSW5mK1B0U2I4aVgwdC9ZdlBPdWNQYzNVKzdFUkR3bFlaZld4aVNIZz09IiwiZXhwIjoxNTQzMzk1OTY3fQ.p-tx3pzVxjUjjcmXq07GE7bZSxqgZ2ilT-KpFbRDp_KhABFB2KhXgbn5SwNUnUmnTRz3oycmL8eXiy-Pepx1-w');
        },
        function failFun(data) {
            console.log(data);
        },
        contractId
    );

}

function refuseSign(contractId) {
    var params = {"contractId": contractId, "contract_status": 2};
    console.log(params)
    confirm('驳回后学员需重签，确认驳回？', function () {
        $.ajax({
            type: "POST",
            url: "../insurancerecord/updateByContract",
            data: JSON.stringify(params),
            success: function (r) {
                if (r.code == 0) {
                    alert('操作成功', function (index) {
                        $("#jqGrid").trigger("reloadGrid");
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    });
}

function pass(insuranceRecordId) {
    var details=[];
    details.push(insuranceRecordId);
    // confirm('确定通过该学员的保险信息？', function () {
    //     $.ajax({
    //         type: "POST",
    //         url: "../insurancerecord/pass",
    //         data: JSON.stringify(details),
    //         success: function (r) {
    //             if (r.code == 0) {
    //                 alert('操作成功', function (index) {
    //                     $("#jqGrid").trigger("reloadGrid");
    //                 });
    //             } else {
    //                 alert(r.msg);
    //             }
    //         }
    //     });
    // });


    $.ajax({
        type: "POST",
        url: "../insurancerecord/checkCount",
        data: JSON.stringify(details),
        success: function (r) {
            var tip ='确定通过该学员的保险信息？';
            if (r.count > 0) {
                tip ='该学员已有其他保险信息通过，请慎重检查，确定通过该学员的保险信息?';
            }
            passAjax(tip,details);
        }
    });
}
function passCancel(insuranceRecordId) {
    var details=[];
    details.push(insuranceRecordId);
    confirm('确定不通过该学员的保险信息？', function () {
        $.ajax({
            type: "POST",
            url: "../insurancerecord/passCancel",
            data: JSON.stringify(details),
            success: function (r) {
                if (r.code == 0) {
                    alert('操作成功', function (index) {
                        $("#jqGrid").trigger("reloadGrid");
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    });
}

function passAjax(tip,details) {
    confirm(tip, function () {
        $.ajax({
            type: "POST",
            url: "../insurancerecord/pass",
            data: JSON.stringify(details),
            success: function (r) {
                if (r.code == 0) {
                    alert('操作成功', function (index) {
                        $("#jqGrid").trigger("reloadGrid");
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    });
}
