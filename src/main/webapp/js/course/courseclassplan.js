$(function () {
    $(".datetimepicker_detailTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00',
        zIndex: 999999999,
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
    });
    $(".courseClassplan-startTime").datetimepicker({
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
    $(".datetimepicker_detailTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
//	        todayHighlight: 1,//如果为true, 高亮当前日期。
//	        startView: 2,//日期时间选择器打开之后首先显示的视图。
//	        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
//	        showMeridian: 1
    });
    $("#jqGrid").jqGrid({//主表数据初始化
        url: '../course/classplan/list',
        datatype: "local",
        colModel: [
            {label: '排课计划Id', name: 'classplanId', hidden: true, width: 50, key: true},
            {label: '上课时点ID', name: 'timetableId', hidden: true},
            {label: '直播室ID', name: 'studioId', hidden: true},
            {label: '直播间ID', name: 'liveRoomId', hidden: true},
            {label: '授课老师ID', name: 'teacherId', hidden: true},
            {label: '课程ID', name: 'courseId', hidden: true},
            {label: '资料库ID', name: 'materialId', hidden: true},

            {label: '排课计划名称', name: 'classplanName', width: 120},
            {label: '产品线', name: 'productName', width: 80},
            {label: '课程名称', name: 'courseName', width: 80},
            {label: '资料库名', name: 'materialIds', width: 80, formatter: materialNameFormatter},
            {label: '时间说明', name: 'classplanLiveDetail', width: 80},
            {label: '开课日期', name: 'startTime', width: 80},
            {label: '上课时点', name: 'timetableName', width: 80},

            {label: '直播室', name: 'studioName', width: 80},
            {label: '直播间', name: 'liveRoomName', width: 80},
            {label: '授课老师', name: 'teacherName', width: 80},
//			{ label: '创建用户', name: 'creationName', width: 80 }, 			
//			{ label: '创建时间', name: 'creationTime', width: 80 }, 			

            {label: '最近修改用户', name: 'modifiedName', width: 80},
            {label: '最近修改日期', name: 'modifiedTime', width: 80},
            {label: '状态', name: 'status', hidden: true},
            {
                label: '状态', name: 'statusName', width: 80, formatter: function (v, options, row) {
                var value = row.status;
                var text = '';
                if (value == 0) text = '<span class="label label-danger">作废</span>';
                else if (value == 1) text = '<span class="label label-success">正常</span>';
                else if (value == 2) text = '<span class="label label-danger">结课</span>';
                return text;
            }
            },
            {
                label: '审核状态', name: 'isAudited', width: 80, formatter: function (value, options, row) {
                var value = row.isAudited;
                var text = '';
                if (value == 0) text = '<span class="label label-danger">未通过</span>';
                else if (value == 1) text = '<span class="label label-success">通过</span>';
                else if (value == 2) text = '<span class="label label-primary">待审核</span>';
                return text;
            }
            },
//			{ label: '是否公开课', name: 'isOpen', width: 80, formatter: function(value, options, row){
//				return value === 1 ? 
//					'<span class="label label-danger">否</span>' : 
//					'<span class="label label-success">是</span>';
//			}},	
            {label: '直播课数量', name: 'liveNumber', width: 80},
            {label: '学生数量', name: 'studentNumber', width: 80},
//			{ label: '备注', name: 'remark', hidden: true, width: 80 },
        ],
        viewrecords: true,
        height: 500,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
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
    
    /*--------------------------------------------初始化排课计划子表------------------------------------------*/
    $("#detailGrid").jqGrid({
        url: '../course/classplan/initItem',
//    	datatype: "json",
        datatype: "local",
        colModel: [
            {label: '助教列表', name: 'asst', hidden: true, width: 50},
            {label: '助教列表', name: 'atids', hidden: true, width: 50},
            {label: '子表ID', name: 'classplanLiveId', hidden: true, width: 50, key: true},
            {label: '主表ID', name: 'classplanId', hidden: true, width: 80},
            {label: '课程ID', name: 'courseId', hidden: true, width: 80},
            {label: '直播课名称', name: 'classplanLiveName', width: 80},
            {label: '直播课ID', name: 'courseLiveDetailId', hidden: true},
//			{ label: '直播时间说明', name: 'classplanLiveTimeDetail', width: 200 }, 
            {label: '即将开始时间', name: 'readyTime', width: 80},
            {label: '开始时间', name: 'startTime', width: 80},
            {label: '结束时间', name: 'endTime', width: 80},
            {label: '进入直播间结束时间', name: 'closeTime', width: 80},
            {label: '上课时段', name: 'timeBucket', hidden: true},
            {
                label: '上课时段', name: 'timeBucketName', width: 80, formatter: function (v, options, row) {
                var value = row.timeBucket;
                var text = '';
                if (value == 0) text = '上午';
                else if (value == 1) text = '下午';
                else if (value == 2) text = '晚上';
                return text;
            }
            },
            {label: '考勤', name: 'attendance', hidden: true},
            {
                label: '考勤', name: 'attendanceName', width: 80, formatter: function (v, options1, row) {
                var value = row.attendance;
                var text = '';
                if (value == -1) text = '否';
                else text = '是';
                return text;
            }
            },
            {label: '直播室', name: 'studioName', width: 80},//formatter: studioNameFormatter},
            {label: '直播室ID', name: 'studioId', hidden: true},
            {label: '直播间', name: 'liveRoomName', width: 80},//formatter: liveRoomNameFormatter},
            /*{ label: '直播间频道号', name: 'liveRoomChannelId', width: 80 },
             { label: '直播间频道密码', name: 'liveRoomChannelPassword', width: 80 },*/
            {label: '直播间ID', name: 'liveroomId', hidden: true},
            {label: '展示互动直播ID', name: 'genseeLiveId', width: 80},
            {label: '展示互动直播房间号', name: 'genseeLiveNum', width: 80},
            {label: '回放地址', name: 'backUrl', width: 80},
            {label: '回放ID', name: 'backId', hidden: true, width: 80},
            {label: '回放类型', name: 'backType', hidden: true, width: 80},
            {label: '授课老师', name: 'teacherName', width: 80},//formatter: teacherNameFormatter},
            {label: '授课老师ID', name: 'teacherId', hidden: true},
            {label: '班型权限', name: 'liveClassTypeIds', hidden: true},
            {label: '班型权限', name: 'liveClassTypeNames', width: 80, formatter: classTypeNameFormatter},
            {label: '文件上传', name: 'fileUrl', width: 80},
            /*courseId : vm.courseClassplan.courseId,//课程id
             startTime : $("#datetimepicker").val(),//开课日期
             timetableId : vm.courseClassplan.timetableId,//上课时段id
             studioId : vm.courseClassplan.studioId,//直播间id
             liveRoomId : vm.courseClassplan.liveRoomId,//直播室id

             teacherId : vm.courseClassplan.teacherId //授课老师id*/
            //app4.0.1字段
            {label: '文件上传名', name: 'fileName', width: 60},
            /*{label: '上期复习', name: 'review', width: 60},
            {label: '上期复习文件名', name: 'reviewName', width: 60},
            {label: '本次预习', name: 'prepare', width: 60},
            {label: '本次预习文件名', name: 'prepareName', width: 60},
            {label: '课堂资料', name: 'courseware', width: 120},
            {label: '课堂资料文件名', name: 'coursewareName', width: 60},*/
            {label: '阶段ID', name: 'examStageId', width: 120},
            {label: '阶段名称', name: 'phaseName', width: 120},


            {label: '是否禁言', name: 'banSpeaking', width: 120,hidden: true},
            {label: '是否禁止问答', name: 'banAsking', width: 120,hidden: true},
            {label: '是否隐藏讨论模块', name: 'hideDiscussion', width: 120,hidden: true},
            {label: '是否隐藏问答模块', name: 'hideAsking', width: 120,hidden: true},
            
            {label: '是否挂录播课', name: 'type', width: 120,hidden: true},
            {label: '录播课id', name: 'recordId', width: 120,hidden: true},
            {label: '录播课名称', name: 'recordName', width: 120},
            {label: '商品id', name: 'goodId', width: 120,hidden: true},
            {label: '商品名称', name: 'goodName', width: 120},

            /*{label : '练习阶段ID',name : 'practiceStageId',width : 120,hidden : true},
             {label : '练习阶段名称',name : 'practiceStageName',width : 120,hidden : true},
             {label : '考试阶段ID',name : 'examStageId',width : 120,hidden : true},
             {label : '考试阶段名称',name : 'examStageName',width : 120,hidden : true},
             */

        ],
        viewrecords: true, //定义是否要显示总记录数
        height: 285,
        rowNum: 1000,
        rowList: [10, 30, 50],
        rownumbers: true, //如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。
        rownumWidth: 25,
        autowidth: true, //自动宽度
        shrinkToFit:false,
        multiselect: true, //定义是否可以多选
        pager: "#detailGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#detailGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    }); 
    
    $("#detailGrid1").jqGrid({
        url: '../course/classplan/initItem',
//    	datatype: "json",
        datatype: "local",
        colModel: [
            {label: '子表ID', name: 'classplanLiveId', hidden: true, width: 50, key: true},
            {label: '主表ID', name: 'classplanId', hidden: true, width: 80},
            {label: '课程ID', name: 'courseId', hidden: true, width: 80},
            {label: '直播课名称', name: 'classplanLiveName', width: 80},
            {label: '直播课ID', name: 'courseLiveDetailId', hidden: true},
//			{ label: '直播时间说明', name: 'classplanLiveTimeDetail', width: 200 },
            {label: '即将开始时间', name: 'readyTime', width: 80},
            {label: '开始时间', name: 'startTime', width: 80},
            {label: '结束时间', name: 'endTime', width: 80},
            {label: '进入直播间结束时间', name: 'closeTime', width: 80},
            {label: '上课时段', name: 'timeBucket', hidden: true},
            {
                label: '上课时段', name: 'timeBucketName', width: 80, formatter: function (v, options, row) {
                var value = row.timeBucket;
                var text = '';
                if (value == 0) text = '上午';
                else if (value == 1) text = '下午';
                else if (value == 2) text = '晚上';
                return text;
            }
            },
            {label: '考勤', name: 'attendance', hidden: true},
            {
                label: '考勤', name: 'attendanceName', width: 80, formatter: function (v, options1, row) {
                var value = row.attendance;
                var text = '';
                if (value == -1) text = '否';
                else text = '是';
                return text;
            }
            },
            {label: '直播室', name: 'studioName', width: 80},//formatter: studioNameFormatter},
            {label: '直播室ID', name: 'studioId', hidden: true},
            {label: '直播间', name: 'liveRoomName', width: 80},//formatter: liveRoomNameFormatter},
            /*{ label: '直播间频道号', name: 'liveRoomChannelId', width: 80 },
             { label: '直播间频道密码', name: 'liveRoomChannelPassword', width: 80 },*/
            {label: '直播间ID', name: 'liveroomId', hidden: true},
            {label: '展示互动直播ID', name: 'genseeLiveId', width: 80},
            {label: '展示互动直播房间号', name: 'genseeLiveNum', width: 80},
            {label: '回放地址', name: 'backUrl', width: 80},
            {label: '回放ID', name: 'backId', hidden: true, width: 80},
            {label: '回放类型', name: 'backType', hidden: true, width: 80},
            {label: '授课老师', name: 'teacherName', width: 80},//formatter: teacherNameFormatter},
            {label: '授课老师ID', name: 'teacherId', hidden: true},
            {label: '班型权限', name: 'liveClassTypeIds', hidden: true},
            {label: '班型权限', name: 'liveClassTypeNames', width: 80, formatter: classTypeNameFormatter},
            {label: '文件上传', name: 'fileUrl', width: 80},
            /*courseId : vm.courseClassplan.courseId,//课程id
             startTime : $("#datetimepicker").val(),//开课日期
             timetableId : vm.courseClassplan.timetableId,//上课时段id
             studioId : vm.courseClassplan.studioId,//直播间id
             liveRoomId : vm.courseClassplan.liveRoomId,//直播室id

             teacherId : vm.courseClassplan.teacherId //授课老师id*/

            //app4.0.1字段
            {label: '文件名', name: 'fileName', width: 60},
            /*{label: '上期复习', name: 'review', width: 60},
            {label: '上期复习文件名', name: 'reviewName', width: 60},
            {label: '本次预习', name: 'prepare', width: 60},
            {label: '本次预习文件名', name: 'prepareName', width: 60},
            {label: '课堂资料', name: 'courseware', width: 120},
            {label: '课堂资料文件名', name: 'coursewareName', width: 60},*/
            {label: '阶段ID', name: 'examStageId', width: 120},
            {label: '阶段名称', name: 'phaseName', width: 120},
            {label: '是否禁言', name: 'banSpeaking', width: 120,hidden: true},
            {label: '是否禁止问答', name: 'banAsking', width: 120,hidden: true},
            {label: '是否隐藏讨论模块', name: 'hideDiscussion', width: 120,hidden: true},
            {label: '是否隐藏问答模块', name: 'hideAsking', width: 120,hidden: true},
            
            {label: '是否挂录播课', name: 'type', width: 120,hidden: true},
            {label: '录播课id', name: 'recordId', width: 120,hidden: true},
            {label: '录播课名称', name: 'recordName', width: 120},
            {label: '商品id', name: 'goodId', width: 120,hidden: true},
            {label: '商品名称', name: 'goodName', width: 120},

            /*{label : '练习阶段ID',name : 'practiceStageId',width : 120,hidden : true},
             {label : '练习阶段名称',name : 'practiceStageName',width : 120,hidden : true},
             {label : '考试阶段ID',name : 'examStageId',width : 120,hidden : true},
             {label : '考试阶段名称',name : 'examStageName',width : 120,hidden : true},
             */

        ],
        viewrecords: true, //定义是否要显示总记录数
        height: 285,
        rowNum: 1000,
        rowList: [10, 30, 50],
        rownumbers: true, //如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。
        rownumWidth: 25,
        autowidth: true, //自动宽度
        shrinkToFit:false,
        autoScroll: true,  
        multiselect: true, //定义是否可以多选
        pager: "#detailGridPager1",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#detailGrid1").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    

    //资料库名称格式化
    function materialNameFormatter(cellvalue, options, rowObject) {
        //	alert(rowObject.classTypeIds);
        if (null != rowObject && null != rowObject.materialIds && rowObject.materialIds.length > 0) {
            return vm.getmaterialNames(rowObject.materialIds);
        }
        return "";
    };

    //班型权限格式化
    function classTypeNameFormatter(cellvalue, options, rowObject) {
        if (null != rowObject && null != rowObject.liveClassTypeIds && rowObject.liveClassTypeIds.length > 0) {
            return vm.getLiveClassTypeNames(rowObject.liveClassTypeIds);
        }
        return "";
    };

    //直播间格式化
    function liveRoomNameFormatter(cellvalue, options, rowObject) {
        return vm.getLiveRoomName(rowObject.liveroomId);
    };

    //直播室格式化
    function studioNameFormatter(cellvalue, options, rowObject) {
        return vm.getStudioName(rowObject.studioId);
    };

    //授课老师格式化
    function teacherNameFormatter(cellvalue, options, rowObject) {
        return vm.getTeacherName(rowObject.teacherId);
    };

    //阶段格式化
    function phaseNameFormatter(cellvalue, options, rowObject) {
        return vm.getPhaseName(rowObject.phaseId);
    }

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            classplanName: "",
        },
        showList: 1,
        showList1: 1,
        title: null,
        classTypeList: [],
        classTypeMap: {},
        materialList: [],
        materialMap: {},
        courseClassplan: {
            classplanId: "",
            classplanName: "",
            classplanLiveDetail: "",
            status: 1,
            isOpen: 1,
            courseId: "",
            courseName: "",//课程名称
            liveRoomId: "",
            checkType: true,
            liveRoomName: "",
            studioId: "",
            studioName: "",
            timetableId: "",
            timetableName: "",
            teacherId: "",
            teacherName: "",
            startTime: "",
            materialId: "",
            materialIds: "",//资料库ids
            materialIdArray: [],
            materialName: "",
            productId: "",
            productName: "",
            readyTime: 30,//即将开始时间单位min
            closeTime: 30,//进入直播间结束时间单位min
            materialCourseList: []
        },
        detail: {
            title: null,
            obj: {
                liveClassTypeIds: [],
                classplanLiveName: '',
//				classplanLiveTimeDetail:'',
                timeBucket: '',
                studioId: '',
                studioName: "",
                liveroomId: '',
                liveRoomName: '',
                backUrl: '',
                teacherId: '',
                teacherName: "",
                startTime: "",
                endTime: "",
                fileUrl: "",
                readyTime: "",//即将开始时间
                closeTime: "",//进入直播间结束时间
                //liveRoomId:{}
                review: "",//上期复习
                prepare: "",//本期预习
                courseware: "",//课堂资料
                fileName: "",//资料文件名
                reviewName: "",//上期复习文件名
                prepareName: "",//本期预习文件名
                coursewareName: "",//课堂资料文件名
                /*practiceStageId:"",//练习阶段ID
                 practiceStageName:"",//练习阶段名称*/
                examStageId: "",//考试阶段ID
                phaseName: "",//阶段ID名称
                /*practiceStageId:"",//练习阶段ID
                 practiceStageName:"",//练习阶段名称*/
                attendance: '',//是否考勤
                banSpeaking: 0,
                banAsking: 0,
                hideDiscussion: 0,
                hideAsking: 0,
                type: 1,
                recordId: "",
                recordName: "",
                goodId: "",
                goodName: ""
            },
        },
        selData: {
            timetableList: [{number: "", name: "数据加载中。。。"}],
            timetableDetailList: [{id: "", timeBucket: "数据加载中。。。"}],
            teacherList: [{userId: "", userName: "数据加载中。。。"}],
            studioList: [{studioId: "", studioName: "数据加载中。。。"}],
            liveRoomList: [{liveRoomId: "", liveRoomName: "数据加载中。。。"}]
        },
        options: [
            {text: '上午', value: '0'},
            {text: '下午', value: '1'},
            {text: '晚上', value: '2'}
        ],
        options1: [
            {text: '是', value: '0'},
            {text: '否', value: '-1'}
        ]
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },

        add: function () {
            vm.showList = 2;
            vm.title = "新增";
            vm.courseClassplan = {
                classplanId: "",
                classplanName: "",
                classplanLiveDetail: "",
                status: 1,
                isOpen: 1,
                courseId: "",
                courseName: "",//课程名称
                liveRoomId: "",
                liveRoomName: "",//直播间名字
                studioId: "",
                checkType: true,
                studioName: "",
                timetableId: "",
                timetableName: "",
                teacherId: "",
                teacherName: "",
                timetableId: "",
                attendance: "",//是否考勤
                startTime: "",
                materialId: "",
                materialIds: "",//资料库ids
                materialIdArray: [],
                materialName: "",
                productId: "",
                productName: "",
                readyTime: 30,//即将开始时间单位min
                closeTime: 30//进入直播间结束时间单位min

            };
            vm.courseClassplan.materialCourseList = [];

            $("#auditButton").attr("style", "display: none");
            $("#unAuditButton").attr("style", "display: none");
            $("#commitButton").attr("style", "display: block");
            $("#lookButton").attr("style", "display: none");
            $("#initButton").attr("style", "");
            $("#itemButton").attr("style", "display: block");
            $("#ready_time").attr("style", "");
            $("#close_time").attr("style", "");

            $("#classplanName").attr("disabled", false);
            $("#productName").attr("disabled", false);
            $("#courseName").attr("disabled", false);
            $("#classplanLiveDetail").attr("disabled", false);
            // $("#meteriaName").attr("disabled",false);
            $("#courseClassplanStartTime").attr("disabled", false);
            $("#timetableName").attr("disabled", false);
            $("#studioName").attr("disabled", false);
            $("#liveRoomName").attr("disabled", false);
            $("#teacherName").attr("disabled", false);
            $("#status1").attr("disabled", false);
            $("#status2").attr("disabled", false);
            $("#status3").attr("disabled", false);
            $("#readyTime").attr("disabled", false);
            $("#closeTime").attr("disabled", false);
            $("#checkType").attr("disabled", false);
            $("#remark").attr("disabled", false);
            
            $("#autoLoad").attr("style", "display: none");
            $("#autoLoad").attr("disabled", true);

            $("#courseClassplanStartTime").val("");//新增时让开课日期置空
            jQuery("#detailGrid").jqGrid("clearGridData");//新增的时候清空Grid子表

            $('#atdiv').html('');
        },
        update: function (event) {

            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            var id = $("#jqGrid").jqGrid('getGridParam', 'selrow');
            var rowData = $('#jqGrid').jqGrid('getRowData', id);
            var startTime = rowData.startTime;
            var isAudited = rowData.isAudited;
            if (new Date(startTime).getTime() < new Date().getTime()) {
                alert("排课已经开课,不允许修改!")
                return;
            }
            if (isAudited.indexOf("success") > 0) {
                alert("排课已经审核通过,不允许再次修改!")
                return;
            }
            vm.showList = 2;
            vm.showList1 = 1;
            vm.title = "修改";
            jQuery("#detailGrid").jqGrid("clearGridData");

            $("#auditButton").attr("style", "display: none");
            $("#commitButton").attr("style", "display: block");
            $("#unAuditButton").attr("style", "display: none");

            $("#itemButton").attr("style", "display: block");
            $("#lookButton").attr("style", "display: none");
            $("#initButton").attr("style", "display: none");
            $("#ready_time").attr("style", "display: none");
            $("#close_time").attr("style", "display: none");

            $("#classplanName").attr("disabled", false);
            $("#productName").attr("disabled", false);
            $("#courseName").attr("disabled", false);
            $("#classplanLiveDetail").attr("disabled", false);
            //  $("#meteriaName").attr("disabled",false);
            $("#courseClassplanStartTime").attr("disabled", false);
            $("#timetableName").attr("disabled", false);
            $("#studioName").attr("disabled", false);
            $("#liveRoomName").attr("disabled", false);
            $("#teacherName").attr("disabled", false);
            $("#status1").attr("disabled", false);
            $("#status2").attr("disabled", false);
            $("#status3").attr("disabled", false);
            $("#readyTime").attr("disabled", false);
            $("#closeTime").attr("disabled", false);
            $("#checkType").attr("disabled", false);
            $("#remark").attr("disabled", false);
            
            $("#autoLoad").attr("style", "display: block");
            $("#autoLoad").attr("disabled", false);
            
            $("#exportTemplate").attr("style", "display: inline");
            $("#exportTemplate").attr("disabled", false);
            $("#uploadExcelMethod").attr("style", "display: inline");
            $("#uploadExcelMethod").attr("disabled", false);

            vm.getInfo(classplanId, true);
        },
        baseUpdate: function (event) {
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            var id = $("#jqGrid").jqGrid('getGridParam', 'selrow');
            var rowData = $('#jqGrid').jqGrid('getRowData', id);
            var startTime = rowData.startTime;
            var isAudited = rowData.isAudited;
            // if (new Date(startTime).getTime() < new Date().getTime()){
            //     alert("排课已经开课,不允许修改!")
            //     return;
            // }
            // if (isAudited.indexOf("待审核") < 0){
            //     alert("排课已经审核,不允许再次修改!")
            //     return;
            // }
            vm.showList = 3;
            vm.showList1 = 2;
            vm.title = "基础信息修改";
            jQuery("#detailGrid1").jqGrid("clearGridData");

            $("#auditButton1").attr("style", "display: none");
            $("#commitButton1").attr("style", "display: block");
            $("#unAuditButton1").attr("style", "display: none");

            $("#itemButton1").attr("style", "display: block");
            $("#lookButton1").attr("style", "display: none");
            $("#initButton1").attr("style", "display: none");
            $("#ready_time1").attr("style", "display: none");
            $("#close_time1").attr("style", "display: none");


            $("#classplanName1").attr("disabled", false);
            $("#productName1").attr("disabled", false);
            $("#courseName1").attr("disabled", false);
            $("#classplanLiveDetail1").attr("disabled", false);
            //  $("#meteriaName1").attr("disabled",false);
            $("#courseClassplanStartTime1").attr("disabled", false);
            $("#timetableName1").attr("disabled", false);
            $("#studioName1").attr("disabled", false);
            $("#liveRoomName1").attr("disabled", false);
            $("#teacherName1").attr("disabled", false);
            $("#status11").attr("disabled", false);
            $("#status21").attr("disabled", false);
            $("#status31").attr("disabled", false);
            $("#readyTime1").attr("disabled", false);
            $("#closeTime1").attr("disabled", false);
            $("#checkType1").attr("disabled", false);
            $("#remark1").attr("disabled", false);

            vm.getInfoBase(classplanId);
        },
        updateApply: function (event) {
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            var rowData = $("#jqGrid").jqGrid('getRowData', classplanId);
            var startTime = new Date(rowData.startTime);
            var date = new Date();
            if (startTime > date) {
                alert("未开课，无需填写变更申请单，请联系审核人反审核！！！");
                return;
            }
            vm.showList = 2;
            vm.title = "变更申请";
            jQuery("#detailGrid").jqGrid("clearGridData");

            $("#auditButton").attr("style", "display: none");
            $("#commitButton").attr("style", "display: block");
            $("#unAuditButton").attr("style", "display: none");

            $("#itemButton").attr("style", "display: block");
            $("#lookButton").attr("style", "display: none");
            $("#initButton").attr("style", "display: none");

            $("#classplanName").attr("disabled", false);
            $("#productName").attr("disabled", false);
            $("#courseName").attr("disabled", false);
            $("#classplanLiveDetail").attr("disabled", false);
            //  $("#meteriaName").attr("disabled",false);
            $("#courseClassplanStartTime").attr("disabled", false);
            $("#timetableName").attr("disabled", false);
            $("#studioName").attr("disabled", false);
            $("#liveRoomName").attr("disabled", false);
            $("#teacherName").attr("disabled", false);
            $("#status1").attr("disabled", false);
            $("#status2").attr("disabled", false);
            $("#status3").attr("disabled", false);
            $("#checkType").attr("disabled", false);
            $("#remark").attr("disabled", true);
            $("#autoLoad").attr("style", "display: inline");
            $("#autoLoad").attr("disabled", false);
            
            $("#exportTemplate").attr("style", "display: inline");
            $("#exportTemplate").attr("disabled", false);
            $("#uploadExcelMethod").attr("style", "display: inline");
            $("#uploadExcelMethod").attr("disabled", false);
            vm.getInfo(classplanId, true);
        },
        audit: function (event) {
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            var id = $("#jqGrid").jqGrid('getGridParam', 'selrow');
            var rowData = $('#jqGrid').jqGrid('getRowData', id);
            var isAudited = rowData.isAudited;
            if (isAudited.indexOf("待审核") < 0) {
                alert("排课已经审核,不允许再次审核!")
                return;
            }
            vm.showList = 2;
            vm.title = "审核";
            jQuery("#detailGrid").jqGrid("clearGridData");

            $("#auditButton").attr("style", "display: block");
            $("#unAuditButton").attr("style", "display: none");
            $("#commitButton").attr("style", "display: none");

            $("#itemButton").attr("style", "display: none");
            $("#lookButton").attr("style", "display: none");


            $("#classplanName").attr("disabled", true);
            $("#productName").attr("disabled", true);
            $("#courseName").attr("disabled", true);
            $("#classplanLiveDetail").attr("disabled", true);
            //  $("#meteriaName").attr("disabled",true);
            $("#courseClassplanStartTime").attr("disabled", true);
            $("#timetableName").attr("disabled", true);
            $("#studioName").attr("disabled", true);
            $("#liveRoomName").attr("disabled", true);
            $("#teacherName").attr("disabled", true);
            $("#status1").attr("disabled", true);
            $("#status2").attr("disabled", true);
            $("#status3").attr("disabled", true);
            $("#readyTime").attr("disabled", true);
            $("#closeTime").attr("disabled", true);
            $("#checkType").attr("disabled", true);
            $("#remark").attr("disabled", true);

            vm.getInfo(classplanId,true);
        },
        unAudit: function (event) {
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            var id = $("#jqGrid").jqGrid('getGridParam', 'selrow');
            var rowData = $('#jqGrid').jqGrid('getRowData', id);
            var startTime = rowData.startTime;
            var isAudited = rowData.isAudited;
            if (new Date(startTime).getTime() < new Date().getTime()) {
                alert("排课已经开课,不允许反审核!")
                return;
            }
            if (isAudited.indexOf("待审核") > 0) {
                alert("排课处于待审核状态,请先审核!")
                return;
            }
            vm.showList = 2;
            vm.title = "反审核";
            jQuery("#detailGrid").jqGrid("clearGridData");

            $("#auditButton").attr("style", "display: none");
            $("#commitButton").attr("style", "display: none");
            $("#unAuditButton").attr("style", "display: block");
            $("#itemButton").attr("style", "display: none");
            $("#lookButton").attr("style", "display: none");

            $("#classplanName").attr("disabled", true);
            $("#productName").attr("disabled", true);
            $("#courseName").attr("disabled", true);
            $("#classplanLiveDetail").attr("disabled", true);
            //   $("#meteriaName").attr("disabled",true);
            $("#courseClassplanStartTime").attr("disabled", true);
            $("#timetableName").attr("disabled", true);
            $("#studioName").attr("disabled", true);
            $("#liveRoomName").attr("disabled", true);
            $("#teacherName").attr("disabled", true);
            $("#status1").attr("disabled", true);
            $("#status2").attr("disabled", true);
            $("#status3").attr("disabled", true);
            $("#readyTime").attr("disabled", true);
            $("#closeTime").attr("disabled", true);
            $("#checkType").attr("disabled", true);
            $("#remark").attr("disabled", true);

            vm.getInfo(classplanId,true);
        },
        look: function (event) {
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            vm.showList = 2;
            vm.title = "查看详情";
            jQuery("#detailGrid").jqGrid("clearGridData");

            $("#auditButton").attr("style", "display: none");
            $("#commitButton").attr("style", "display: none");
            $("#unAuditButton").attr("style", "display: none");
            $("#itemButton").attr("style", "display: none");
            $("#lookButton").attr("style", "display: block");

            $("#classplanName").attr("disabled", true);
            $("#productName").attr("disabled", true);
            $("#courseName").attr("disabled", true);
            $("#classplanLiveDetail").attr("disabled", true);
            //   $("#meteriaName").attr("disabled",true);
            $("#courseClassplanStartTime").attr("disabled", true);
            $("#timetableName").attr("disabled", true);
            $("#studioName").attr("disabled", true);
            $("#liveRoomName").attr("disabled", true);
            $("#teacherName").attr("disabled", true);
            $("#status1").attr("disabled", true);
            $("#status2").attr("disabled", true);
            $("#status3").attr("disabled", true);
            $("#readyTime").attr("disabled", true);
            $("#closeTime").attr("disabled", true);
            $("#checkType").attr("disabled", true);
            $("#remark").attr("disabled", true);

            $("#classplanName").attr("disabled", true);
            $("#productName").attr("disabled", true);
            $("#courseName").attr("disabled", true);
            $("#classplanLiveDetail").attr("disabled", true);
            //   $("#meteriaName").attr("disabled",true);
            $("#courseClassplanStartTime").attr("disabled", true);
            $("#timetableName").attr("disabled", true);
            $("#studioName").attr("disabled", true);
            $("#liveRoomName").attr("disabled", true);
            $("#teacherName").attr("disabled", true);
            $("#status1").attr("disabled", true);
            $("#status2").attr("disabled", true);
            $("#status3").attr("disabled", true);
            $("#readyTime").attr("disabled", true);
            $("#closeTime").attr("disabled", true);
            $("#checkType").attr("disabled", true);
            $("#remark").attr("disabled", true);

            vm.getInfo(classplanId,true);
        },
        userList: function (event) {//排课下的学员列表
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            //vm.showList = false;
            vm.title = "学员列表";

            vm.getUserListInfo(classplanId);
        },

        getUserListInfo: function (classplanId) {//获取学员列表信息
            usersByclassplanLay.show(classplanId, null);
        },
        accept: function (event) {//审核通过
            confirm('您确定通过审核吗', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/accept",
                    data: JSON.stringify(vm.courseClassplan.classplanId),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        reject: function (event) {//审核未通过
            confirm('您确定不通过审核吗', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/reject",
                    data: JSON.stringify(vm.courseClassplan.classplanId),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        waitAudit: function (event) {//反审核
            confirm('您确定反审核通过吗', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/waitAudit",
                    data: JSON.stringify(vm.courseClassplan.classplanId),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            //做新增或保存时获取开课日期的值
            vm.courseClassplan.startTime = $("#courseClassplanStartTime").val();
            //前台校验
            if ($.isNull(vm.courseClassplan.classplanName)) {
                alert("排课计划名称不能为空！！！");
                return;
            }
            if ($.isNull(vm.courseClassplan.productId)) {
                alert("请选择产品线！！！");
                return;
            }
            if ($.isNull(vm.courseClassplan.courseId)) {
                alert("请选择课程！！！");
                return;
            }
            if($.isNull(vm.courseClassplan.readytime)){
            	vm.courseClassplan.readytime = 0;
            }
            if($.isNull(vm.courseClassplan.closeTime)){
            	vm.courseClassplan.closeTime = 0;
            }
            if (!$.isNull(vm.courseClassplan.classplanLiveDetail) && vm.courseClassplan.classplanLiveDetail.length > 100) {
                alert("时间说明不能超过100个字！！！");
                return;
            }
//		    if($.isNull(vm.courseClassplan.materialId)){
//		    	alert("请选择资料库！！！");
//		    	return;
//		    }
            if ($.isNull(vm.courseClassplan.startTime)) {
                alert("请输入开课日期！！！");
                return;
            }
//		    if($.isNull(vm.courseClassplan.timetableId)){
//		    	alert("请选择上课时点！！！");
//		    	return;
//		    }
//		    if($.isNull(vm.courseClassplan.studioId)){
//		    	alert("请选择直播室！！！");
//		    	return;
//		    }
//		    if($.isNull(vm.courseClassplan.liveRoomId)){
//		    	alert("请选择直播间！！！");
//		    	return;
//		    }
            if ($.isNull(vm.courseClassplan.teacherId)) {
                alert("请选择授课老师！！！");
                return;
            }
            if (!$.isNull(vm.courseClassplan.remark) && vm.courseClassplan.remark.length > 50) {
                alert("备注信息不能超过50个字！！！");
                return;
            }
            if (vm.title == "新增") {
                url = "../course/classplan/save";
            }
            else if (vm.title == "修改") {
                url = "../course/classplan/update";
            } else if (vm.title == "变更申请") {
                url = "../course/classplan/updateApply";
            } else if (vm.title == "基础信息修改") {
                url = "../course/classplan/update";
            } else {
                url = "";
            }
            //子表所有数据
            var details = [];
            var ids = $("#detailGrid").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var row = $('#detailGrid').jqGrid('getRowData', ids[i]);
                details.push(row);
            }
            vm.courseClassplan.detailList = details;
//			console.log(vm.courseClassplan.detailList);

            //资料库字段转换
            //vm.courseClassplan.materialIds = vm.courseClassplan.materialIdArray.toString();
            if (vm.courseClassplan && vm.courseClassplan.materialCourseList) {
                vm.courseClassplan.materialIds = "";
                vm.courseClassplan.materialCourseList.forEach(item => {
                    if(item.materialChecked){
                        vm.courseClassplan.materialIds = vm.courseClassplan.materialIds + item.id + ","
                    }
                })
                if(vm.courseClassplan.materialIds.length>1){
                    vm.courseClassplan.materialIds = vm.courseClassplan.materialIds.substring(0,vm.courseClassplan.materialIds.length-1);
                }
            }
            //处理助教列表
            if(vm.courseClassplan.asst){
                var atids = '';
                for(var id in vm.courseClassplan.asst){
                    atids+=id+',';
                }
                if(atids.length>1){
                    vm.courseClassplan.atids = atids.substring(0,atids.length-1);
                }
            }

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.courseClassplan),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate1: function (event) {
            if (vm.title == "新增") {
                url = "../course/classplan/save";
            }
            else if (vm.title == "修改") {
                url = "../course/classplan/update";
            } else if (vm.title == "变更申请") {
                url = "../course/classplan/updateApply";
            } else if (vm.title == "基础信息修改") {
                url = "../course/classplan/update";
            } else {
                url = "";
            }
            //子表所有数据
            var details = [];
            var ids = $("#detailGrid1").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var row = $('#detailGrid1').jqGrid('getRowData', ids[i]);
                details.push(row);
            }
            vm.courseClassplan.detailList = details;

            //资料库字段转换
            //vm.courseClassplan.materialIds = vm.courseClassplan.materialIdArray.toString();
            if (vm.courseClassplan && vm.courseClassplan.materialCourseList) {
                vm.courseClassplan.materialIds = "";
                vm.courseClassplan.materialCourseList.forEach(item => {
                    if(item.materialChecked){
                        vm.courseClassplan.materialIds = vm.courseClassplan.materialIds + item.id + ","
                    }
                })
                if(vm.courseClassplan.materialIds.length>1){
                    vm.courseClassplan.materialIds = vm.courseClassplan.materialIds.substring(0,vm.courseClassplan.materialIds.length-1);
                }
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.courseClassplan),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        checkItem: function (event) {
            /*//子表所有数据
             var details = [] ;
             var ids = $("#detailGrid").jqGrid('getDataIDs');
             for(var i = 0;i<ids.length;i++){
             var row = $('#detailGrid').jqGrid('getRowData',ids[i]);
             if(isNaN(row.id)){
             row.id = null;
             }
             details.push(row);
             }
             vm.courseClassplan.detailList = details;*/

            //子表所有数据
            var details = [];
            var ids = $("#detailGrid").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var row = $('#detailGrid').jqGrid('getRowData', ids[i]);
                details.push(row);
            }
            vm.courseClassplan.detailList = details;
            //做新增或保存时获取开课日期的值
            vm.courseClassplan.startTime = $("#courseClassplanStartTime").val();

            $.ajax({
                type: "POST",
                url: "../course/classplan/checkItem",
                data: JSON.stringify(vm.courseClassplan),
                success: function (r) {
                    if (r.code === 0) {
                        alert('校验成功');
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var classplanIds = getSelectedRows();
            if (classplanIds == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/delete",
                    data: JSON.stringify(classplanIds),
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
        },
        getInfo: function (classplanId, isUpdate) {

            var productId = vm.courseClassplan.productId;
            this.getclassTypeList(productId);//班型权限初始化
            $.get("../course/classplan/info/" + classplanId, function (r) {

                if (r.code == 0) {
                    if(r.materialList){
                        vm.materialList = r.materialList;
                        $.each(vm.materialList, function (i, v) {
                            vm.materialMap[v.id] = v.name;
                        });
                    }

                    vm.courseClassplan = r.courseClassplan;
                    vm.courseClassplan.checkType = true;
                    //查询信息时获取并显示开课日期的值
                    $("#courseClassplanStartTime").val(vm.courseClassplan.startTime);
                    $("#ready_time").val(30);
                    $("#close_time").val(30);
                    var detailList = r.detailList;
                    //开课日期
                    if (null != detailList && detailList.length > 0) {
                        $.each(detailList, function (i, v) {
                            //添加行
                            $("#detailGrid").addRowData(v.classplanLiveId, v, "last");
                        });
                    }
                    //开始转materialIdArray
                    vm.courseClassplan.materialIdArray = [];
                    if (null != r.courseClassplan && null != r.courseClassplan.materialIds) {
                        vm.courseClassplan.materialIds = r.courseClassplan.materialIds;
                        vm.courseClassplan.materialIdArray = r.courseClassplan.materialIds.split(",");
                    }
                    //加载助教
                    if(vm.courseClassplan.asst){
                        var asstStr = vm.courseClassplan.asst;
                        vm.courseClassplan.asst = {};
                        vm.loadAsst('atdiv',asstStr,vm.addAsstItem);
                    }
                    //如果是修改的时候默认加载资料库
                    if(isUpdate){
                        vm.selectTeacherFile(vm.courseClassplan.courseId, isUpdate);
                    }
                }
            });
        },
        getInfoBase: function (classplanId) {

            var productId = vm.courseClassplan.productId;
            this.getclassTypeList(productId);//班型权限初始化
            $.get("../course/classplan/info/" + classplanId, function (r) {
                if (r.code == 0) {
                    //开始转materialIdArray
                    r.courseClassplan.materialIdArray = [];
                    if (null != r.courseClassplan && null != r.courseClassplan.materialIds) {
                        // vm.courseClassplan.materialIds=r.courseClassplan.materialIds;
                        r.courseClassplan.materialIdArray = r.courseClassplan.materialIds.split(",");
                    }

                    vm.courseClassplan = r.courseClassplan;


                    vm.courseClassplan.checkType = true;
                    //查询信息时获取并显示开课日期的值
                    $("#courseClassplanStartTime").val(vm.courseClassplan.startTime);
                    $("#ready_time").val(30);
                    $("#close_time").val(30);
                    var detailList = r.detailList;
                    //开课日期
                    if (null != detailList && detailList.length > 0) {
                        $.each(detailList, function (i, v) {
                            //添加行
                            $("#detailGrid1").addRowData(v.classplanLiveId, v, "last");
                        });
                    }
                }

            });
        },

        reload: function (event, p) {
           /* vm.materialList = [];
            vm.materialMap = {};*/
            vm.showList = 1;
            vm.showList1 = 3;
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {"classplanName": vm.q.classplanName},
                datatype: "json",
                page: page
            }).trigger("reloadGrid");
        },
        //正常
        resume: function (event) {
            var classplanIds = getSelectedRows();
            if (classplanIds == null) {
                return;
            }
            confirm('确定要正常选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/resume",
                    data: JSON.stringify(classplanIds),
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
        },
        //作废
        pause: function (event) {
            var classplanIds = getSelectedRows();
            if (classplanIds == null) {
                return;
            }
            confirm('确定要作废选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/pause",
                    data: JSON.stringify(classplanIds),
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
        },
        //结课
        end: function (event) {
            var classplanIds = getSelectedRows();
            if (classplanIds == null) {
                return;
            }
            confirm('确定要结课选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../course/classplan/end",
                    data: JSON.stringify(classplanIds),
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
        },
        selCourse: function (classplanId) {
            //清空查询条件
//			vm.clearQueryOrderParams();
            //初始化表格
            vm.initJqGridCourse();
            //显示浮层
            vm.showCourseLayer();
            //加载课程数据
            vm.reloadJqGridCourse();
        },
        initJqGridCourse: function () {//加载课程列表
            $("#jqGridCourse").jqGrid({
                url: '../mall/courses/listGrid',
                datatype: "local",
//		        datatype: "json",
                colModel: [
                    {label: '课程ID', name: 'courseId', width: 60, key: true},
                    {label: '课程编号', name: 'courseNo', width: 100},
                    {label: '课程名称', name: 'courseName', width: 100},
                    {label: '课程类别', name: 'courseLb', width: 100},
                    {label: '课程类型', name: 'courseType', width: 100},
                    {label: '考试方式', name: 'examType', width: 100},
                    {label: '学分', name: 'courseCredit', width: 100},
                    {
                        label: '排课可冲突', name: 'courseEq', width: 100, formatter: function (value, options, row) {
                        return value === 0 ?
                            '<span class="label label-danger">不可冲突</span>' :
                            '<span class="label label-success">可以冲突</span>';
                    }
                    },
                    /*{ label: '试听地址', name: 'listenUrl', width: 80 },
                     { label: '创建用户', name: 'creationName', width: 80 },
                     { label: '创建时间', name: 'creationTime', width: 80 },
                     { label: '修改用户', name: 'modifiedName', width: 80 },
                     { label: '修改时间', name: 'modifiedTime', width: 80 }*/
                ],
                viewrecords: true,
                height: 300,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: false,
                pager: "#jqGridPagerCourse",
                jsonReader: {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
                },
                prmNames: {
                    page: "page",
                    rows: "limit",
                    order: "order"
                },
                ondblClickRow: function (rowid, iRow, iCol, e) {
                    that.select();
                },
                gridComplete: function () {
                    //隐藏grid底部滚动条
                    $("#jqGridCourse").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        },
        showCourseLayer: function () {//显示课程列表
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '课程列表',
                area: ['891px', '633px'],
                shadeClose: false,
                content: jQuery("#courselayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    //获取选中行ID
                    var selectDetail = getJqGridSelectedRow("jqGridCourse");
                    if (selectDetail == null) {
                        return;
                    }
                    //行数据
                    var rowData = $("#jqGridCourse").jqGrid('getRowData', selectDetail);
//					//课程ID
                    vm.courseClassplan.courseId = rowData.courseId;
//					//课程名称
                    vm.courseClassplan.courseName = rowData.courseName;
                    layer.close(index);
                }
            });
        },
        reloadJqGridCourse: function () {//刷新课程列表
            $("#jqGridCourse").jqGrid('setGridParam', {
                page: 1,
                datatype: 'json'
            }).trigger("reloadGrid");
        },

        iadd: function () {
            //弹框标题
            vm.detail.title = "新增";
            //初始化弹窗数据
            vm.detail.obj = {
                liveClassTypeIds: [],
                classplanLiveName: '',
//				classplanLiveTimeDetail:'',
                timeBucket: '',
                attendance: '',
                studioId: '',
                studioName: "",
                liveroomId: '',
                liveRoomName: '',
                backUrl: '',
                teacherId: '',
                teacherName: "",
                startTime: "",
                endTime: "",
                fileUrl: "",
                readyTime: "",
                closeTime: "",
                review: "",//上期复习
                prepare: "",//本期预习
                courseware: "",//课堂资料
                fileName: "",//资料文件名
                reviewName: "",//上期复习文件名
                prepareName: "",//本期预习文件名
                coursewareName: "",//课堂资料文件名
                banSpeaking: 0, // 是否禁言
                banAsking: 0, // 是否禁止问答
                hideDiscussion: 0, // 是否隐藏讨论模块
                hideAsking: 0,// 是否隐藏问答模块
                /*practiceStageId:"",//练习阶段ID
                 practiceStageName:"",//练习阶段名称*/
                examStageId: "",//考试阶段ID
                phaseName: "",//阶段名称
                type: 1,//是否挂录播课程1：否 2：是
                recordId: "",//录播课id
                recordName: "",
                goodId: "",//商品id
                goodName: ""
            };
            $("#detailReadyTime").val("");
            $("#detailCloseTime").val("");
            $("#detailStartTime").val("");
            $("#detailEndTime").val("");
            vm.ishow();
        },
        iupdate: function () {
            var productId = vm.courseClassplan.productId;
            this.getclassTypeList(productId);//班型权限初始化
            //获取选中行ID
            //var classplanId = getSelectedRow();
            var selectDetail = getJqGridSelectedRow("detailGrid");
            if (selectDetail == null) {
                return;
            }
            //行数据
            var rowData = $("#detailGrid").jqGrid('getRowData', selectDetail);
            //str=>array
            rowData.liveClassTypeIds = rowData.liveClassTypeIds.split(",");
            vm.detail.obj = rowData;
            //弹框标题
            vm.detail.title = "修改";

            $("#detailReadyTime").val(vm.detail.obj.readyTime);
            $("#detailCloseTime").val(vm.detail.obj.closeTime);
            $("#detailStartTime").val(vm.detail.obj.startTime);
            $("#detailEndTime").val(vm.detail.obj.endTime);
//			弹框
            vm.ishow();

        },
        iupdate1: function () {
            var productId = vm.courseClassplan.productId;
            this.getclassTypeList(productId);//班型权限初始化
            //获取选中行ID
            //var classplanId = getSelectedRow();
            var selectDetail = getJqGridSelectedRow("detailGrid1");
            if (selectDetail == null) {
                return;
            }
            //行数据
            var rowData = $("#detailGrid1").jqGrid('getRowData', selectDetail);
            //str=>array
            rowData.liveClassTypeIds = rowData.liveClassTypeIds.split(",");
            vm.detail.obj = rowData;
            //弹框标题
            vm.detail.title = "修改";

            $("#detailReadyTime1").val(vm.detail.obj.readyTime);
            $("#detailCloseTime1").val(vm.detail.obj.closeTime);
            $("#detailStartTime1").val(vm.detail.obj.startTime);
            $("#detailEndTime1").val(vm.detail.obj.endTime);
//			弹框
            vm.ishow1();

        },

        ishow: function () {//弹出新增或者修改窗口
            if ($.isNull(vm.courseClassplan.productId)) {
                alert("请选择产品线！！！");
                return;
            }
            if ($.isNull(vm.courseClassplan.courseId)) {
                alert("请选择课程！！！");
                return;
            }
            if ("新增" == vm.detail.title) {
                var productId = vm.courseClassplan.productId
                this.getclassTypeList(productId);//班型权限初始化
            }
            layer.open({
                //非空校验
                //课次名称
                type: 1,
                skin: 'layui-layer-molv',
                title: vm.detail.title,
                area: ['600px', '699px'],
                shadeClose: false,
                content: jQuery("#liveLayer"),
                btn: ['提交', '取消'],
                btn1: function (index) {

                    vm.detail.obj.readyTime = $("#detailReadyTime").val();//即将开始时间
                    vm.detail.obj.closeTime = $("#detailCloseTime").val();//进入直播间结束时间
                    vm.detail.obj.startTime = $("#detailStartTime").val();//开始时间
                    vm.detail.obj.endTime = $("#detailEndTime").val();//结束时间
                    //非空校验
                    if ($.isNull(vm.detail.obj.classplanLiveName)) {
                        alert("直播课名称不能为空！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.readyTime)) {
                        alert("请输入即将开始时间！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.startTime)) {
                        alert("请输入开始时间！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.endTime)) {
                        alert("请输入结束时间！！！");
                        return;
                    }
                    if (vm.detail.obj.startTime > vm.detail.obj.endTime) {
                        alert("结束时间不能小于开始时间！！！");
                        return;
                    }
                    if (vm.detail.obj.readyTime > vm.detail.obj.startTime) {
                        alert("开始时间不能小于即将开始时间！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.timeBucket)) {

                        alert("请选择上课时段！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.attendance)) {

                        alert("请选择是否考勤！！！");
                        return;
                    }
//					if($.isNull(vm.detail.obj.studioId)){
//						alert("请选择直播室！！！");
//						return;
//					}
                    if ($.isNull(vm.detail.obj.liveroomId)) {
                        alert("请选择直播间！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.teacherId)) {
                        alert("请选择授课老师！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.banSpeaking)) {
                        alert("请选择是否禁言！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.banAsking)) {
                        alert("请选择是否禁止问答！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.hideDiscussion)) {
                        alert("请选择是否隐藏讨论模块！！！");
                        return;
                    }
                    if ($.isNull(vm.detail.obj.hideAsking)) {
                        alert("请选择是否隐藏问答模块！！！");
                        return;
                    }
                    if (vm.detail.obj.type == 2) {
                    	if ($.isNull(vm.detail.obj.goodId)) {
	                         alert("请选择商品！！！");
	                         return;
	                     }
                    	if ($.isNull(vm.detail.obj.recordId)) {
	                         alert("请选择录播课！！！");
	                         return;
	                     }
                    }
                    var liveClassTypeIds = vm.detail.obj.liveClassTypeIds;
                    if (null == liveClassTypeIds || liveClassTypeIds.length == 0) {
                        alert("请选择班型权限");
                        return;
                    }
                    //处理助教列表
                    if(vm.detail.obj){
                        var atids = '';
                        for(var id in vm.detail.obj.asst){
                            atids+=id+',';
                        }
                        if(atids.length>1){
                            vm.detail.obj.atids = atids.substring(0,atids.length-1);
                        }
                        vm.detail.obj.asst = JSON.stringify(vm.detail.obj.asst);
                    }

                    if ("新增" == vm.detail.title) {
                        //行ID
                        var rowId = new Date().getTime();
                        console.log(vm.detail.obj);
                        //添加行
                        $("#detailGrid").addRowData(rowId, vm.detail.obj, "last");
                    } else if ("修改" == vm.detail.title) {
                        //修改
                        $("#detailGrid").setRowData(getJqGridSelectedRow("detailGrid"), vm.detail.obj);
                    }
                    layer.close(index);

                },
            });
            if (vm.title == "变更申请") {
                $("#fileUrl_id").attr("disabled", true);
                $("#review_id").attr("disabled", true);
                $("#prepare_id").attr("disabled", true);
                $("#courseware_id").attr("disabled", true);
            } else {
                $("#fileUrl_id").attr("disabled", false);
                $("#review_id").attr("disabled", false);
                $("#prepare_id").attr("disabled", false);
                $("#courseware_id").attr("disabled", false);
            }

            var asstStr = vm.detail.obj.asst;
            vm.detail.obj.asst = {};
            vm.loadAsst('atdiv_detail',asstStr,vm.addAsstItemForDetail);
        },
        ishow1: function () {//弹出新增或者修改窗口
            if ($.isNull(vm.courseClassplan.productId)) {
                alert("请选择产品线！！！");
                return;
            }
            if ("新增" == vm.detail.title) {
                var productId = vm.courseClassplan.productId
                this.getclassTypeList(productId);//班型权限初始化
            }
            layer.open({
                //非空校验
                //课次名称
                type: 1,
                skin: 'layui-layer-molv',
                title: vm.detail.title,
                area: ['600px', '699px'],
                shadeClose: false,
                content: jQuery("#liveLayer1"),
                btn: ['提交', '取消'],
                btn1: function (index) {

                    if ("新增" == vm.detail.title) {
                        //行ID
                        var rowId = new Date().getTime();
                        //添加行
                        $("#detailGrid1").addRowData(rowId, vm.detail.obj, "last");
                    } else if ("修改" == vm.detail.title) {
                        //修改
                        $("#detailGrid1").setRowData(getJqGridSelectedRow("detailGrid1"), vm.detail.obj);
                    }
                    layer.close(index);

                }
            });
        },
        exportTemplate: function (event) {
            var urlstr = "../course/classplan/exportLivesTemplate";
            window.location.href = urlstr;
        },
        uploadExcelMethod: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '导入Excel',
                area: ['300px', '200px'],
                shadeClose: false,
                content: jQuery("#uploadExcel"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    $.ajaxFileUpload({
                        url: '../course/classplan/importLivesTemplate',
                        secureuri: true,
                        fileElementId: 'file_data',
                        dataType: 'json',
                        success: function (data) {
                            if (data.code == 0) { 
                            	var allData = $("#detailGrid").getRowData();
                            	var allOrgId = $("#detailGrid").jqGrid('getDataIDs');
                            	allData.push($("#detailGrid").jqGrid('getRowData', allOrgId[allOrgId.length - 1]));
                            	for(var i = 0 ; i < data.list.length; i++){
                            		var isRepeat = false;
                            		for(var j = 0 ; j < allData.length; j++){
                            			if(allData[j].classplanLiveName == data.list[i].classplanLiveName){
                            				console.log(allData[j].classplanLiveName)
                            				isRepeat = true;
                            				break;
                            			}
                            		}
                            		if(isRepeat == false){
		                                //行ID
		                                var rowId = new Date().getTime();
		                                $("#detailGrid").addRowData(rowId, data.list[i], "last");
                            		}
                            	}
                            	console.log($("#detailGrid").getRowData());
                                alert("文件上传成功！！！", function (index) {
                                    
                                });
                                layer.close(index);
                            } else{
                                alert(data.msg);
                            }
                        }
                    });
                }
            });
        },
        //初始化上课时点列表
        autoLoad: function () {
            if($.isNull(vm.courseClassplan.readyTime)){
            	vm.courseClassplan.readyTime = 0;
            }
            if($.isNull(vm.courseClassplan.closeTime)){
            	vm.courseClassplan.closeTime = 0;
            }
            var that = this;
            var classplanId = getSelectedRow();
            console.log($("#detailGrid").getRowData());
            var classplanLiveId = getJqGridSelectedRows("detailGrid");
            if (classplanLiveId == null) {
                return;
            } 
            var url = "../course/classplan/autoLoad?classplanId=" + classplanId; 
            //课次被选中则会使用选择课次的开始时间作为开课日期
            if(classplanLiveId != null){
            	var classplanLive = $("#detailGrid").jqGrid('getRowData',classplanLiveId);
            	url = url + "&startTime=" + classplanLive.startTime + "&classplanLiveId=" + classplanLive.classplanLiveId;
        	}  
            
            $.getJSON(url, vm.courseClassplan, function (data) {
                if (data.code == 0) {
                	for(var i = 0 ; i < data.page.list.length; i++){
                		if(classplanLive == null || (classplanLive.classplanLiveId != data.page.list[i].classplanLiveId  && data.page.list[i].startTime > classplanLive.startTime)){
                			$("#detailGrid").jqGrid("delRowData",data.page.list[i].classplanLiveId);
                			$("#detailGrid").addRowData(data.page.list[i].classplanLiveId, data.page.list[i], "last");
                		}
                	}
                } else{
                    alert(data.msg);
                }
            });
        },
        idel: function () {
            //获取选中行ID
            var selectDetails = getJqGridSelectedRows("detailGrid");
            if (selectDetails == null) {
                return;
            }
            var len = selectDetails.length;
            confirm('确定要删除选中的记录？', function () {
                /*$.each(selectDetails , function(i , val){
                 $("#detailGrid").jqGrid("delRowData", val);
                 });*/
                for (var i = 0; i < len; i++) {
                    $("#detailGrid").jqGrid("delRowData", selectDetails[0]);
                }
                return true;
            });
        },
        //初始化授课老师列表
        initTeacher: function () {
            var that = this;
            $.getJSON("../common/teacherList", function (r) {
                if (r.code === 0) {
                    that.selData.teacherList = r.data;
                    //console.log(r.data);
                } else {
                    alert(r.msg);
                }
            });
        },
        //初始化上课时点列表
        initTimetable: function () {
            var that = this;
            $.getJSON("../common/timetableList", function (r) {
                if (r.code === 0) {
                    that.selData.timetableList = r.data;
                    //console.log(r.data);
                } else {
                    alert(r.msg);
                }
            });
        },
        //初始化直播室列表
        initStudio: function () {
            var that = this;
            $.getJSON("../common/studioList", function (r) {
                if (r.code === 0) {
                    that.selData.studioList = r.data;
                    //console.log(r.data);
                } else {
                    alert(r.msg);
                }
            });
        },
        //初始化直播间列表
        initLiveRoom: function () {
            var that = this;
            $.getJSON("../common/liveRoomList", function (r) {
                if (r.code === 0) {
                    that.selData.liveRoomList = r.data;
                } else {
                    alert(r.msg);
                }
            });
        },
        //获取班型列表
        getclassTypeList: function (productId) {
            $.get("../mall/classtype/select?productId=" + productId, function (data) {
                vm.classTypeList = data.data;
                $.each(vm.classTypeList, function (i, v) {
                    vm.classTypeMap[v.value] = v.name;
                });
            });
        },
        //资料库ids转名称
        getmaterialNames: function (ids) {
            var str = "";
            if (null == ids || ids.length == 0) {
                return str;
            }
            if (!$.isArray(ids)) {
                ids = ids.split(",");
            }
            $.each(ids, function (i, val) {
                if (window.parent.materialMap[val]) {
                    str += window.parent.materialMap[val] + ";";
                }
            });
            return str.length > 0 ? str.substring(0, str.length - 1) : "";
        },
        getLiveClassTypeNames: function (ids) {
            var str = "";
            if (null == ids || ids.length == 0) {
                return str;
            }
            if (!$.isArray(ids)) {
                ids = ids.split(",");
            }
            $.each(ids, function (i, val) {
                str += vm.classTypeMap[val] + ";";
            });
            return str.length > 0 ? str.substring(0, str.length - 1) : "";
        },
        initItem: function () {//加载子表
            var vm = this;
            //加载时将开课日期赋值
            vm.courseClassplan.startTime = $("#courseClassplanStartTime").val();
            //check
            if ($.isNull(vm.courseClassplan.courseId)) {
                alert("请选择课程");
                return;
            }
            ;
            if ($.isNull(vm.courseClassplan.startTime)) {
                alert("请选择开课日期");
                return;
            }
            ;
            if ($.isNull(vm.courseClassplan.timetableId)) {
                alert("请选择上课时点");
                return;
            }
            ;
//			if($.isNull(vm.courseClassplan.studioId)){
//				alert("请选择直播室");
//				return;
//			};
            if ($.isNull(vm.courseClassplan.liveRoomId)) {
                alert("请选择直播间");
                return;
            }
            ;
            if ($.isNull(vm.courseClassplan.teacherId)) {
                alert("请选择授课老师");
                return;
            }
            ;
            $("#detailGrid").jqGrid('setGridParam', {
                postData: {
                    //加载生成列表需要的参数（传到后台）
                    courseId: vm.courseClassplan.courseId,//课程id
                    startTime: vm.courseClassplan.startTime,//开课日期
                    timetableId: vm.courseClassplan.timetableId,//上课时段id
                    attendance: vm.courseClassplan.attendance,//考勤id
                    studioId: vm.courseClassplan.studioId,//直播间id
                    liveRoomId: vm.courseClassplan.liveRoomId,//直播室id
                    teacherId: vm.courseClassplan.teacherId, //授课老师id
                    readyTime: vm.courseClassplan.readyTime, //即将开始时间
                    closeTime: vm.courseClassplan.closeTime, //进入直播间结束时间
                    timetableId: vm.courseClassplan.timetableId,//上课时段id
                    attendance: vm.courseClassplan.attendance,//考勤id
                    studioId: vm.courseClassplan.studioId,//直播间id
                    liveRoomId: vm.courseClassplan.liveRoomId,//直播室id
                    teacherId: vm.courseClassplan.teacherId, //授课老师id
                    readyTime: vm.courseClassplan.readyTime, //即将开始时间
                    closeTime: vm.courseClassplan.closeTime, //进入直播间结束时间
                    banSpeaking: vm.courseClassplan.banSpeaking, // 是否禁言
                    banAsking: vm.courseClassplan.banAsking, // 是否禁止问答
                    hideDiscussion: vm.courseClassplan.hideDiscussion, // 是否隐藏讨论模块
                    hideAsking: vm.courseClassplan.hideAsking // 是否隐藏问答模块
                },
//                page : 1,
//                url : vm.userplanDetail.url,
                datatype: 'json'
            }).trigger("reloadGrid");
        },
        checkout: function () {//排课校验

        },
        getLiveRoomName: function (id) {
            var vm = this;
            /*console.log(id);
             console.log(vm.selData.liveRoomList);*/
            var str = "";
            if (id != null && id != "") {
                for (var i = 0; i < vm.selData.liveRoomList.length; i++) {
                    var item = vm.selData.liveRoomList[i];
                    if (id == item.liveRoomId) {
                        str = item.liveRoomName
                        break;
                    }

                }
            }
            return str;
        },
        getStudioName: function (id) {
            var vm = this;
            var str = "";
            if (id != null && id != "") {
                for (var i = 0; i < vm.selData.phaseList.length; i++) {
                    var item = vm.selData.phaseList[i];
                    if (id == item.phaseId) {
                        str = item.phaseName
                        break;
                    }

                }
            }
            return str;
        },
        getPhaseName: function (id) {
            var vm = this;
            var str = "";
            if (id != null && id != "") {
                for (var i = 0; i < vm.selData.studioList.length; i++) {
                    var item = vm.selData.studioList[i];
                    if (id == item.studioId) {
                        str = item.studioName
                        break;
                    }

                }
            }
            return str;
        },
        getTeacherName: function (id) {
            var vm = this;
            /*console.log(id);
             console.log(vm.selData.teacherList);*/
            var str = "";
            if (id != null && id != "") {
                for (var i = 0; i < vm.selData.teacherList.length; i++) {
                    var item = vm.selData.teacherList[i];
                    if (id == item.userId) {
                        str = item.username
                        break;
                    }

                }
            }
            return str;
        },
        //资料库全部名称
        getMaterialList: function () {
            $.get("../teachStudentFile/list", function (data) {
                vm.materialList = data.data;
                $.each(vm.materialList, function (i, v) {
                    vm.materialMap[v.id] = v.name;
                });
                window.parent.materialMap = vm.materialMap;
            });
        },

        //根据课程的id去查询资料库数据
        selectCourse: function () {
            courseLay.show(function (index, opt) {
                vm.courseClassplan.materialCourseList = [];
                vm.materialMap = {};
                vm.courseClassplan.courseId = opt.courseId;
                vm.courseClassplan.courseName = opt.courseName;
                vm.selectTeacherFile(vm.courseClassplan.courseId)
            });
        },
        selectTeacherFile :function(courseId, isUpdate) {
            //根据课程的id去查询资料库数据
            $.get("../teachStudentFile/selectMaterialListByCourseId/"+courseId, function (data) {
                vm.courseClassplan.materialCourseList = data.data;
                //如果是修改的时候默认勾选
                if(isUpdate) {
                    vm.courseClassplan.materialCourseList.forEach(item => {
                        item.materialChecked = false;
                        if(vm.courseClassplan.materialIds && vm.courseClassplan.materialIds.split(",").length > 0){
                            vm.courseClassplan.materialIds.split(",").forEach(item1 => {
                                if(item1 == item.id){
                                    item.materialChecked = true;
                                }
                            })
                        }
                    })
                }
                vm.$forceUpdate();
            });
        },
        selectTeacher: function (flag) {
            if(flag===1){
                teacherLay_asst.show(function (index, opt) {
                    if(!vm.courseClassplan.asst){
                        vm.courseClassplan.asst = {};
                    }
                    vm.addAsstItem(opt);
                });
            }else {
                teacherLay.show(function (index, opt) {
                    vm.courseClassplan.teacherId = opt.userId;
                    vm.courseClassplan.teacherName = opt.nickName;
                });
            }
        },
        loadAsst:function(did,data,callback){
            if(data){
                data = JSON.parse(data);
            }
            $('#'+did).html('');
            if(data){
                for(var i in data){
                    callback({userId:i,nickName:data[i]});
                }
            }
        },
        addAsstItemForDetail:function(opt){
            if(!vm.detail.obj.asst[opt.userId]){
                vm.detail.obj.asst[opt.userId]=opt.nickName;
                $('#atdiv_detail').show();
                var h = $('#atdiv_detail').html()
                    +'<div id="atdiv_detail_item'+opt.userId+'" style="padding-top: 5px;font-size: 20px;margin-left: 10px;">'+opt.nickName
                    +'<span style="color: red;font-size: 14px;cursor: pointer;font-weight:bold;padding-left: 5px;" ' +
                    'onclick="vm.rmAsstItemForDetail('+opt.userId+')">X</span>'
                    +'</div>';

                $('#atdiv_detail').html(h);
            }
        },
        rmAsstItemForDetail:function(id){
            $('#atdiv_detail_item'+id).remove();
            delete vm.detail.obj.asst[id];
        },
        addAsstItem:function(opt){
            if(!vm.courseClassplan.asst[opt.userId]){
                vm.courseClassplan.asst[opt.userId]=opt.nickName;
                $('#atdiv').show();
                var h = $('#atdiv').html()
                    +'<div id="atdiv_item'+opt.userId+'" style="padding-top: 5px;font-size: 20px;margin-left: 10px;">'+opt.nickName
                    +'<span style="color: red;font-size: 14px;cursor: pointer;font-weight:bold;padding-left: 5px;" ' +
                    'onclick="vm.rmAsstItem('+opt.userId+')">X</span>'
                    +'</div>';

                $('#atdiv').html(h);
            }
        },
        rmAsstItem:function(id){
            $('#atdiv_item'+id).remove();
            delete vm.courseClassplan.asst[id];
        },
        selectTeacherForDetail: function (flag) {
            if(flag===1){
                teacherLay_asst.show(function (index, opt) {
                    if(!vm.detail.obj.asst){
                        vm.detail.obj.asst = {};
                    }
                    vm.addAsstItemForDetail(opt);
                });
            }else {
                teacherLay.show(function (index, opt) {
                    vm.detail.obj.teacherId = opt.userId;
                    vm.detail.obj.teacherName = opt.nickName;
                });
            }
        },
        goodsInfoLayerShow: function () {//商品信息浮层
            goodsInfoLay.show(function (index, opt) {
                //商品ID
                vm.detail.obj.goodId = opt.id;
                //商品名称
                vm.detail.obj.goodName = opt.name;
                
            });
        },
        recordList: function (event) {//某个课程下的录播课列表
            var courseId = vm.courseClassplan.courseId;
            if (courseId == null) {
            	alert("系统异常");
                return;
            }
            vm.recordLayerShow();
        },
        recordLayerShow: function () {//录播课浮层
            recordLay.show(function (index, opt) {
                //录播课ID
                vm.detail.obj.recordId = opt.recordId;
                //录播课名称
                vm.detail.obj.recordName = opt.name;
                

            }, vm.courseClassplan.courseId);
        },
        materialLayerShow: function () { //资料库浮层
            materialLay.show(function (index, opt) {
                vm.courseClassplan.materialId = opt.materialId;
                vm.courseClassplan.materialName = opt.materialName;
            });
        },
        selLiveroom: function () {
            liveroomLay.show(function (index, opt) {
                vm.courseClassplan.liveRoomId = opt.liveRoomId;
                vm.courseClassplan.liveRoomName = opt.liveRoomName;
            });
        },
        selectStudio: function () {
            studioLay.show(function (index, opt) {
                vm.courseClassplan.studioId = opt.studioId;
                vm.courseClassplan.studioName = opt.studioName;
            });
        },
        selectProduct: function () {
            productLay.show(function (index, opt) {
                vm.courseClassplan.productId = opt.productId;
                vm.courseClassplan.productName = opt.productName;
            });
        },
        selectStudioForDetail: function () {
            studioLay.show(function (index, opt) {
                vm.detail.obj.studioId = opt.studioId;
                vm.detail.obj.studioName = opt.studioName;
            });
        },
        selectPhaseForDetail: function () {
            phaseLay.show(vm.courseClassplan.courseId, function (index, opt) {
                /*alert(opt.phaseId);*/
                vm.detail.obj.examStageId = opt.phaseId;
                vm.detail.obj.phaseName = opt.phaseName;
            });
        },
        selectTimetable: function () {
            timetableLay.show(function (index, opt) {
                console.log(vm.courseClassplan)
                vm.courseClassplan.timetableId = opt.number;
                vm.courseClassplan.timetableName = opt.name;
                console.log(vm.courseClassplan)
            });
        },
        selLiveroomForDetail: function () {
            liveroomLay.show(function (index, opt) {
                console.log(opt);
                vm.detail.obj.liveroomId = opt.liveRoomId;
                vm.detail.obj.liveRoomName = opt.liveRoomName;
                vm.detail.obj.liveRoomChannelId = opt.liveRoomChannelId;
                vm.detail.obj.liveRoomChannelPassword = opt.liveRoomChannelPassword;
                vm.detail.obj.genseeLiveId = opt.genseeLiveId;
                vm.detail.obj.genseeLiveNum = opt.genseeLiveNum;
            });
        },
        selectVideo: function () {
            polyvVideoLay.show(function (index, opt) {
                console.log(opt);
            });
        },

        updateMaterialShow: function () {//修改资料库
            var vm = this;
            var classplanId = getSelectedRow();
            if (classplanId == null) {
                return;
            }
            vm.courseClassplan = {
                classplanId: "",
                classplanName: "",
                classplanLiveDetail: "",
                status: 1,
                isOpen: 1,
                courseId: "",
                courseName: "",//课程名称
                liveRoomId: "",
                liveRoomName: "",//直播间名字
                studioId: "",
                studioName: "",
                timetableId: "",
                timetableName: "",
                teacherId: "",
                teacherName: "",
                timetableId: "",
                startTime: "",
                materialId: "",
                meterialIds: "",
                materialIdArray: [],
                materialName: "",
                attendance: ""
            };
            vm.getInfo(classplanId, true);
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '资料库关联',
                area: ['491px', '333px'],
                shadeClose: false,
                content: jQuery("#courseMaterialDiv"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    //vm.courseClassplan.materialIds = vm.courseClassplan.materialIdArray.toString();
                    vm.courseClassplan.classplanId = classplanId;

                    //获取选中行ID

// 					if(selectDetail == null){
// 							return ;
// 					}
//						//行数据
//						var rowData = $("#jqGridCourse").jqGrid('getRowData',selectDetail);
////						//课程ID
//						vm.courseClassplan.courseId = rowData.courseId;
////						//课程名称
                    //根据选择的资料组装id
                    vm.courseClassplan.materialIds = "";
                    vm.courseClassplan.materialCourseList.forEach(item => {
                        if(item.materialChecked){
                            vm.courseClassplan.materialIds = vm.courseClassplan.materialIds + item.id + ","
                        }
                    })
                    if(vm.courseClassplan.materialIds.length>1){
                        vm.courseClassplan.materialIds = vm.courseClassplan.materialIds.substring(0,vm.courseClassplan.materialIds.length-1);
                    }

                    $.ajax({
                        type: "POST",
                        url: "../course/classplan/updateMaterialData",
                        data: JSON.stringify(vm.courseClassplan),
                        success: function (r) {
                            if (r.code === 0) {
                                alert('操作成功', function (index) {
                                    vm.reload();
                                });
                                layer.close(index);
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
//					//获取选中行ID
//					var selectDetail = getJqGridSelectedRow("jqGridCourse");
//					if(selectDetail == null){
//						return ;
//					}
//					//行数据
//					var rowData = $("#jqGridCourse").jqGrid('getRowData',selectDetail);
////					//课程ID
//					vm.courseClassplan.courseId = rowData.courseId;
////					//课程名称
//					vm.courseClassplan.courseName = rowData.courseName;
//					layer.close(index);
                }
            });
        },
        //资料库勾选框改变
        checkboxOnclick:function (obj, index) {
            if (obj.target.checked == true){
                vm.courseClassplan.materialCourseList[index].materialChecked = true;
            } else {
                vm.courseClassplan.materialCourseList[index].materialChecked = false;
            }
            vm.$forceUpdate();
        }
    },
    created: function () {
        this.initTeacher();
        this.initTimetable();
        this.initStudio();
        this.initLiveRoom()//改为使用弹窗
        //this.getclassTypeList(-1)//班型权限初始化
        this.getMaterialList();//资料库初始化
    }
});
