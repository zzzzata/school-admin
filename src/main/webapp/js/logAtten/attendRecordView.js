$(function () {
    $(".insertTime_start").datetimepicker({
        format: "yyyy-mm-dd 00:00:00",
        weekStart: 0, //代表的是一周从哪天开始，星期天（0）到星期六（6）
        todayBtn: 1,//表示是否选择显示当天
        autoclose: 1,//表示如果是选择了一个日期后是否自动关闭掉这个窗体
        minView: 2//表示的是展示的是0:分钟  1:小时  2:日
    });

    $(".insertTime_end").datetimepicker({
        format: "yyyy-mm-dd 00:00:00",
        weekStart: 0, //代表的是一周从哪天开始，星期天（0）到星期六（6）
        todayBtn: 1,//表示是否选择显示当天
        autoclose: 1,//表示如果是选择了一个日期后是否自动关闭掉这个窗体
        minView: 2//表示的是展示的是0:分钟  1:小时  2:日
    });

    $("#jqGrid").jqGrid({
        url: '',
        datatype: "local",
        colModel: [
            {label: '学员ID', name: 'userId', width: 30},
            {label: '录播课ID', name: 'recordId', hidden: true},
            {label: '学员姓名', name: 'userName', width: 30},
            {label: '手机号', name: 'mobile', width: 40},
            {label: '班型名称', name: 'classtypeName', width: 30},
            {label: '课程名称', name: 'courseName', width: 30},
            {label: '视频名称', name: 'videoName', width: 30},
            {label: '班级名称', name: 'className', width: 30},
            {label: '班主任', name: 'teacherName', width: 30},
            {label: '录播出勤率', name: 'recordPerTxt', width: 30}
        ],
        viewrecords: true,
        height: 495,
        rowNum: 10000,
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,//
        mulitiselect: false,//是否多选
        pager: "jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条

            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    
    $("#jqGridLogDetails").jqGrid({
        url: '../attendRecordView/logDetails',
        datatype: "local",
        colModel: [
        	    { label: '学员ID', name: 'userId', width: 80},
        		{ label: '学员名称', name: 'userName', width: 80 ,formatter:function(value, options, row){
                    if($.isNotNull(row.userName)){        //如果学员名称为空就显示学员手机
                        return value;
                    }else {
                        return row.mobile;
                    }
                }},
                {label: '手机号', name: 'mobile', width: 80},
                {label: '录播章节名称', name: 'recordName', width: 160},
                {label: '视频名称', name: 'videoName', width: 160},
                { label: '观看时长(毫秒)', name: 'playDuration', width: 80 },
                
        ],
		viewrecords: true,
        height: 495,
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
    el: "#rrapp",
    data: {
        q: {
            userId: null,
            userName: null,
            mobile: null,
            classtypeId: null,
            classtypeName: null,
            courseId: null,
            courseName: null,
            deptId: null,
            deptName: null,
            videoName: null,
            className: null,
            teacherName: null,
            recordPerTxt: null,
            startDate: null,
            endDate: null
        },
        showList: true,
        showLogList:false,
        //报表详情
		qd:{
			recordId:"",//录播课ID
			userId:"",//学员id
		},
    },
    methods: {
        query: function () {
            var startDate = $(".insertTime_start").val();
            var endDate = $(".insertTime_end").val();
            if($.isNull(startDate) || $.isNull(endDate)){
                alert("请选择时间范围");
                return;
            }
            vm.q.startDate = startDate;
            vm.q.endDate = endDate;
            vm.reload('list', 1);
        },

        reload: function (m, p) {
            vm.showList = true;
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.q,
                url: '../attendRecordView/' + m,
                page: page,
                datatype: "json"
            }).trigger("reloadGrid");
        },
        //班型浮层
        classTypeLayerShow: function () {
            classTypeLay.show(function (index, opt) {
                vm.q.classtypeId = opt.classtypeId;
                vm.q.classtypeName = opt.classtypeName;
                $('#query-classTypeName').val(opt.classtypeName);
            });
        },
        //课程浮层
        courseLayerShow: function () {
            courseLay.show(function (index, opt) {
                vm.q.courseId = opt.courseId;
                vm.q.courseName = opt.courseName;
            });
        },
        //校区浮层
        deptLayerShowQuery: function () {
            deptLay.show(function (id, name, opt) {
                vm.q.deptId = id;
                vm.q.deptName = name;
            });
        },
        clearQuery: function () {//清空查询条件
            var vm = this;
            vm.q = {
                userId: null,
                userName: null,
                mobile: null,
                classtypeId: null,
                classtypeName: null,
                courseId: null,
                courseName: null,
                deptId: null,
                deptName: null,
                videoName: null,
                className: null,
                teacherName: null,
                startDate: null,
                endDate: null
            };
            //重置
            $(".insertTime_start").val(vm.q.startDate = null);
            $(".insertTime_end").val(vm.q.endDate = null);
        },
        backMain : function(){//返回
			vm.showList = true;
			vm.showLogList = false;
		},
		logDetails : function(){  //查看学员录播日志
            var rowId = getSelectedRow();
            var rowData = $("#jqGrid").jqGrid('getRowData',rowId);
            if($.isNull(rowData.recordId)){
            	alert("请选择有效行");
                return;
            }
            if($.isNull(rowData.userId)){
            	alert("请选择有效行");
                return;
            }
            vm.showList = false;
            vm.showLogList = true;
            vm.qd.recordId = rowData.recordId;//录播课ID
            vm.qd.userId = rowData.userId;//学员ID
            vm.reloadLog();
        },
        reloadLog: function (e , p) {
			vm.showList = false;
			vm.showLogList = true;
			var page = p || $("#jqGridLogDetails").jqGrid('getGridParam','page');
			$("#jqGridLogDetails").jqGrid('setGridParam',{
				postData : vm.qd,
				page:page,
                datatype: "json"
            }).trigger("reloadGrid");
		},

    }

})