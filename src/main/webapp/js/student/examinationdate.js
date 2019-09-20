$(function () {

    $(".order-Time").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
//	        todayHighlight: 1,//如果为true, 高亮当前日期。
//	        startView: 2,//日期时间选择器打开之后首先显示的视图。
//	        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
//	        showMeridian: 1
    });
    $("#jqGrid").jqGrid({
        url: '../examinationdate/list',
        datatype: "local",
        colModel: [

														{ label: '姓名', name: 'nickName', width: 80 },
														{ label: '手机号码', name: 'mobile', width: 80 },
														{ label: '身份证号', name: 'idCard', width: 80 },
														{ label: '报读日期', name: 'payTimeStr', width: 80 },
														{ label: '报名单号', name: 'ncCode', width: 80 },
														{ label: '班型名称', name: 'classtypeName', width: 80 },
														{ label: '班级', name: 'className', width: 80 },
														{ label: '课程编号', name: 'courseNo', width: 80 },
														{ label: '课程科目', name: 'courseName', width: 80 },
														{ label: '报考时间', name: 'scheduleDate', width: 80 },
														{ label: '学习状态', name: 'learnStateStr', width: 80 },
														{ label: '考试状态', name: 'examStateStr', width: 80 },
														{ label: '班主任', name: 'teacherName', width: 80 },
														{ label: '省份', name: 'areaName', width: 80 },
														{ label: '专业', name: 'professionName', width: 80 },
														{ label: '层次', name: 'levelName', width: 80 },
														{ label: '产品线', name: 'productName', width: 80 }

        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,500,1000,3000,5000],
        rownumbers: true, 
        rownumWidth: 35, 
        autowidth:true,
        multiselect: true,
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		examinationDate: {},
        q: {
			examScheduleId: "",
            examScheduleName: "",
            mobile: "",
            nickName: "",
            courseNo: "",
            learnState: "",
            examState: "",
            productId: "",
            productName: "",
            classId: "",
            className: "",
            teacherId: "",
            teacherName: "",
            areaId: "",
            areaName: "",
            professionId: "",
            professionName: "",
            levelId: "",
            levelName: "",
            courseId: "",
            courseName: "",
            classTypeId: "",
            classTypeName: "",
            orderStartTime:"",
            orderEndTime:""
        },
        selectData : {//下拉初始化列表
            //异常类型状态
            learnStateList : [{value:0,name:'在读'} ,{value:1,name:'休学'} , {value:2,name:'失联'}],
            examStateList : [ {value:1,name:'报考'},{value:2,name:'弃考'},{value:3,name:'免考'}],
        },
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
        clearQuery: function () {
		    vm.q = {
                examScheduleId: "",
                examScheduleName: "",
                mobile: "",
                nickName: "",
                courseNo: "",
                learnState: "",
                examState: "",
                productId: "",
                productName: "",
                classId: "",
                className: "",
                teacherId: "",
                teacherName: "",
                areaId: "",
                areaName: "",
                professionId: "",
                professionName: "",
                levelId: "",
                levelName: "",
                courseId: "",
                courseName: "",
                classTypeId: "",
                classTypeName: "",
                orderStartTime:"",
                orderEndTime:""
            };
            $("#orderStartTime").val("");
            $("#orderEndTime").val("");
            vm.reload(null,1);
        },
		getInfo: function(id){
            $.ajax({
				type: "POST",
			    url: "/examinationdate/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.examinationDate = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (e , p) {
            vm.q.orderStartTime = $("#orderStartTime").val();
            vm.q.orderEndTime = $("#orderEndTime").val();
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                page:page,
                postData : vm.q
            }).trigger("reloadGrid");
		},
        selectExamSchedule: function(){
            examScheduleLay.show(function(index,opt){
                //考试时段id
                vm.q.examScheduleId = opt.id;
                //考试时段名称
                vm.q.examScheduleName = opt.scheduleDate;
            });
        },
        productShowQuery: function () {//产品线查询条件
            productLay.show(function (index, opt) {
                vm.q.productId = opt.productId;
                vm.q.productName = opt.productName;
            });
        },
        classLayerShowQuery: function () {//班级浮层
            classLay.show(function (index, opt) {
                vm.q.classId = opt.classId;
                vm.q.className = opt.className;
            });
        },
        professionLayerShow: function () {//专业浮层
            professionLay.show(function (index, opt) {
                vm.q.professionId = opt.professionId;
                vm.q.professionName = opt.professionName;

            });
        },
        areaLayerShow: function () {//省份浮层
            areaLay.show(function (index, opt) {
                vm.q.areaId = opt.areaId;
                vm.q.areaName = opt.areaName;
            });
        },
        levelLayerShow: function () {//层次浮层
            levelLay.show(function (index, opt) {
                vm.q.levelId = opt.levelId;
                vm.q.levelName = opt.levelName;
            });
        },
        classTypeLayerShow: function () {//班型浮层
            classTypeLay.show(function (index, opt) {
                vm.q.classTypeId = opt.classtypeId;
                vm.q.classTypeName = opt.classtypeName;
            });
        },
        //根据课程的id去查询资料库数据
        selectCourse: function () {
            courseLay.show(function (index, opt) {
                vm.q.courseId = opt.courseId;
                vm.q.courseName = opt.courseName;
            });
        },
        classTeacherLayerShow : function(){//班主任
            teacherLay.show(function(index,opt){
                vm.q.teacherId = opt.userId;
                vm.q.teacherName = opt.nickName;
            } , 2);
        },
	}
});