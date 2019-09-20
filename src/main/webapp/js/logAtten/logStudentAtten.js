
$(function () {
	//时间空间初始化
	$(".datetimepicker_date").datetimepicker({
		format: 'yyyy-mm-dd',
	    weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
	    todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
	    autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
	    minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
	});
	$(".datetimepicker_start").datetimepicker({
		format: 'yyyy-mm-dd 00:00:00',
	    weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
	    todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
	    autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
	    minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
	});
	$(".datetimepicker_end").datetimepicker({
		format: 'yyyy-mm-dd 23:59:59',
		weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
		todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
		autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
		minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
	});
	$(".datetimepicker_date").val(new Date().format("yyyy-MM-dd"));
	$(".datetimepicker_start").val(new Date().format("yyyy-MM-dd 00:00:00"));
	$(".datetimepicker_end").val(new Date().format("yyyy-MM-dd 23:59:59"));
	
	$("#jqGrid").jqGrid({
        url: '../logStudentAtten/list',
        datatype: "local",
        colModel: [	
        		{ label: '学员规划ID', name: 'userplanId' , width: 30,hidden: true},
                { label: '学员ID', name: 'userId' , width: 30 },
                { label: '姓名', name: 'userName', width: 30 },
            	{ label: '校区', name: 'deptName', width: 30 },
                { label: '学员状态', name: 'userStatus', width: 30 },
                { label: '开课时间', name: 'startClassTime', width: 40},
                { label: '手机号', name: 'mobile', width: 40 },
                { label: '地区', name: 'areaName', width: 30 },
                { label: '班级', name: 'className', width: 60 },
                { label: '班主任', name: 'teacherName', width: 40 },
                { label: '直播课次名称', name: 'classplanLiveName', width: 30 },
                { label: '直播课次ID', name: 'classplanLiveId' , width: 30,hidden: true},
                { label: '实际出勤时间(min)', name: 'minWatchDurTxt', width: 30 },
                { label: '应出勤时间(min)', name: 'minFullDurTxt', width: 30 },
                { label: '直播出勤率', name: 'livePerTxt', width: 30 },
                { label: '总出勤率', name: 'attendPerTxt', width: 30 }
        ],
		viewrecords: true,
        height: 495,
        rowNum: 10000,
        rowList : [200,500,1000,2000,5000,10000,20000,50000,100000,200000],
        rownumbers: true,
        rownumWidth: 35, 
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
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

	$("#jqGridLogDetails").jqGrid({
        url: '../logStudentAtten/logDetails',
        datatype: "local",
        colModel: [
                { label: '学员名称', name: 'userName', width: 100 ,formatter:function(value, options, row){
                    if($.isNotNull(row.userName)){        //如果学员名称为空就显示学员手机
                        return value;
                    }else {
                        return row.mobile;
                    }
                }},
                { label: '学员手机', name: 'moblie', width: 80 ,hidden: true },
                { label: '课次名称', name: 'courseName', width: 200 },
                { label: '讲师', name: 'teacherName', width: 80 },
                { label: '开课时间', name: 'startTime', width: 200 },
                { label: '结束时间', name: 'endTime', width: 200 },
                { label: '观看设备', name: 'devices', width: 80 ,formatter:function(value, options, row){
                    if ($.isNotNull(row.devices) && ((row.devices).indexOf("app") >= 0)){
                        return "APP";
                    }else {
                        return "PC";
                    }
                }},
                { label: '类型', name: 'type', width: 80 ,formatter: function(value, options, row) {
                    if ($.isNotNull(row.type) && row.type == 1) {
                        return "直播";
                    } else if($.isNotNull(row.type) && row.type == 2){
                        return "回放";
                    } else if($.isNotNull(row.type) && row.type == 3){
                        return "离线";}
                    else {
                        return value;
                    }
                }},
                { label: '日志进入时间', name: 'inTime', width: 200 },
                { label: '进入观看时间', name: 'inLiveTime', width: 200},
                { label: '退出观看时间', name: 'endLiveTime', width: 200 },
                
        ],
		viewrecords: true,
        height: 495,
        width:1500,
        rowNum: 10000,
        rowList : [200,500,1000,2000,5000,10000,20000,50000,100000,200000],
        rownumbers: true,
        rownumWidth: 40,
        autowidth:false,
        multiselect: true,
        pager: "#jqGridPagerLogDetails",
        jsonReader : {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGridLogDetails").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			areaId:null,//地区
		    areaName:null,
			classId:null,//班级
			className:null,
			classTeacherId:null,//班主任
			classTeacherName:null,
			deptId:null,//部门
			deptName:null,
			statusId:'0',
			statusName:'在读',
			date:null,
			startDate:null,
			endDate:null,
			mobile:null
		},
		showList: true,
		showList2: false,
		showLogList:false,
		title: null,
		//报表详情
		qd:{
		    queryType:1, //查询类型: 1按学员规划查询 2按课次查询
			userplanId:"",//学员规划ID
			classplanId:"",//排课
			classplanName:"",
			classplanLiveId:"",//直播课次ID
			userId:"",//学员id
			attenType:0 //出勤状态-1.已出勤 2.未出勤 其余的没有该查询条件-非必填
		},
	},
	methods: {
		query: function () {
			var date = $(".datetimepicker_date").val();
			if($.isNull(date)){
				alert("请选择日期");
				return;
			}
			vm.q.date = date;
			vm.qd.startDate = date + ' 00:00:00';
            vm.qd.endDate = date + ' 23:59:59';
			if($.isNull(vm.q.classTeacherId) && $.isNull(vm.q.classId)){
				alert("请选择班主任或班级");
				return;
			} else if(!$.isNull(vm.q.classTeacherId) && !$.isNull(vm.q.classId)){
				alert("班主任或班级只能选择其一");
				return;
			}
			if(!$.isNull(vm.q.areaId) && !$.isNull(vm.q.deptId)){
				alert("省份或校区只能选择其一");
				return;
			}
            var minTimeStamp = vm.getTimeStemp(-1);
            var nowTimeStamp = vm.getTimeStemp(0);
            var maxTimeStamp = vm.getTimeStemp(1);
            if (minTimeStamp < nowTimeStamp && nowTimeStamp < maxTimeStamp){
                alert("18:30:00 至 20:00:00 该功能暂不开放");
                return;
            }
			vm.reload('list',1);
		},
		queryByMobile: function () {
			var startDate = $(".datetimepicker_start").val();
			var endDate = $(".datetimepicker_end").val();
			if($.isNull(startDate) || $.isNull(endDate)){
				alert("请选择时间范围");
				return;
			}
			vm.q.startDate = startDate;
			vm.q.endDate = endDate;
			vm.qd.startDate = startDate;
            vm.qd.endDate = endDate;
			if($.isNull(vm.q.mobile)){
				alert("请输入学员手机号");
				return;
			}
            var minTimeStamp = vm.getTimeStemp(-1);
            var nowTimeStamp = vm.getTimeStemp(0);
            var maxTimeStamp = vm.getTimeStemp(1);
            if (minTimeStamp < nowTimeStamp && nowTimeStamp < maxTimeStamp){
                alert("18:30:00 至 20:00:00 该功能暂不开放");
                return;
            }
			vm.reload('listByMobile',1);
		},
		reload: function (m , p) {
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData : vm.q,
				url : '../logStudentAtten/' + m,
				page:page,
                datatype: "json"
            }).trigger("reloadGrid");
		},
		reloadLog: function (e , p) {
			vm.showList = false;
			vm.showList2 = false;
			vm.showLogList = true;
			var page = p || $("#jqGridLogDetails").jqGrid('getGridParam','page');
			$("#jqGridLogDetails").jqGrid('setGridParam',{
				postData : vm.qd,
				page:page,
                datatype: "json"
            }).trigger("reloadGrid");
		},
		classTeacherLayerShow : function(){//班主任
			teacherLay.show(function(index,opt){
				vm.q.classTeacherId = opt.userId;
				vm.q.classTeacherName = opt.nickName;
			} , 2);
		},
		areaLayerShow : function(){//省份浮层
			areaLay.show(function(index,opt){
				vm.q.areaId = opt.areaId;
				vm.q.areaName = opt.areaName;
			});
		},
		classLayerShow : function(){//班级浮层
			classLay.show(function(index,opt){
				vm.q.classId = opt.classId;
				vm.q.className = opt.className;
			});
		},
		studentStatusLayerShow : function(){//状态浮层
			studentStatusLay.show(function(id,name){
				vm.q.statusId = id.substring(0,id.length-1);
				vm.q.statusName = name.substring(0,name.length-1);;
			});
		},
		clearQuery : function(){//清空查询条件
			var vm = this;
			vm.q = {
				areaId:null,//地区
			    areaName:null,
				classId:null,//班级
				className:null,
				classTeacherId:null,//班主任
				classTeacherName:null,
				deptId:null,
				deptName:null,
				statusId:'0',
				statusName:'在读',
				date:null,
				startDate:null,
				endDate:null,
				mobile:null,
			};
			//重置
			$(".datetimepicker_date").val(new Date().format("yyyy-MM-dd"));
			$(".datetimepicker_start").val(new Date().format("yyyy-MM-dd 00:00:00"));
			$(".datetimepicker_end").val(new Date().format("yyyy-MM-dd 23:59:59"));
		},
		////////////////////////////报表详情
		detailsList : function(){//报表详情
			var userPlanId = getSelectedRow();
			if(userPlanId == null){
				return ;
			}
			vm.showList = false;
			vm.showList2 = true;
			vm.qd.userplanId = userPlanId;//学员规划ID
			//初始化时间组件值
			$(".datetimepicker_detail_start").val($(".datetimepicker_start").val());
			$(".datetimepicker_detail_end").val($(".datetimepicker_end").val());
			//查询
			vm.queryDetail();
		},
		logDetails : function(type){  //查看学员直播日志
            var rowId = getSelectedRow();
            var rowData = $("#jqGrid").jqGrid('getRowData',rowId);
            if($.isNull(rowData.userplanId)){
            	alert("请选择有效行(学员规划)");
                return;
            }
            if($.isNull(rowData.classplanLiveId)){
            	alert("请选择有效行(课次)");
                return;
            }
            if($.isNull(rowData.userId)){
            	alert("请选择有效行(用户)");
                return;
            }
            var minTimeStamp = vm.getTimeStemp(-1);
            var nowTimeStamp = vm.getTimeStemp(0);
            var maxTimeStamp = vm.getTimeStemp(1);
            if (minTimeStamp < nowTimeStamp && nowTimeStamp < maxTimeStamp){
                alert("18:30:00 至 20:00:00 日志查询功能暂不开放");
                return;
            }
            vm.showList = false;
            vm.showLogList = true;
            vm.qd.userplanId = rowData.userplanId;//学员规划ID
            vm.qd.classplanLiveId = rowData.classplanLiveId;//直播课次ID
            vm.qd.userId = rowData.userId;//直播课次ID
            vm.qd.queryType = type;
            vm.reloadLog();
        },
		clearQueryDetail : function(){//清空子查询条件
			vm.qd.classplanId="";//排课
			vm.qd.classplanName="";//排课名称
			vm.qd.attenType=0; //出勤状态-1.已出勤 2.未出勤 其余的没有该查询条件-非必填
			//初始化时间组件值
			$(".datetimepicker_detail_start").val($(".datetimepicker_start").val());
			$(".datetimepicker_detail_end").val($(".datetimepicker_end").val());
		},
		deptLayerShowQuery : function(){//校区查询条件
			deptLay.show(function(id , name ,opt){
				vm.q.deptId = id;
				vm.q.deptName = name;
			});
		},
		queryDetail:function(fu,p){//查询
			vm.qd.startDate = $(".datetimepicker_detail_start").val();
			vm.qd.endDate = $(".datetimepicker_detail_end").val();
			if($.isNull(vm.qd.startDate) || $.isNull(vm.qd.endDate)){
				alert("请选择时间范围！");
				return ;
			}
			var page = p || $("#jqGridDetail").jqGrid('getGridParam','page');
			$("#jqGridDetail").jqGrid('setGridParam',{ 
				postData : vm.qd,
				page:page,
                datatype: "json"
            }).trigger("reloadGrid");
		},
		backMain : function(){//返回
			vm.showList = true;
			vm.showList2 = false;
			vm.showLogList = false;
		},
        note: function () {
            alert("【日志进入时间】:学员打开学习视频的时间<p>【进入观看时间】:第三方平台记录的学员开始观看视频的时间 <p>" +
                "【退出观看时间】:第三方平台记录的学员结束关闭视频或视频结束的时间<p> 【退出观看时间】和【进入观看时间】的时间差就是学员的时间出勤时长<p>" +
                "日志进入时间是实时记录的,进入观看时间和退出观看时间是第二天更新的,理论" +
                "上一个日志进入时间对应一个进入观看时间和退出观看时间,如果数据对应不上,说明日记记录有问题,请反馈到运维处处理");
        },
        getTimeStemp:function (type) {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
            var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
            var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
                + " "  + date.getHours()  + seperator2  + date.getMinutes()
                + seperator2 + date.getSeconds();

            var minDate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
                + " 18:30:00"

            var maxDate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
                + " 20:00:00"
            if(type == -1){
                return Date.parse(minDate);
            }else if (type == 1){
                return Date.parse(maxDate);
            }else {
                return Date.now();
            }
        }
	},
	created : function(){
	}
});