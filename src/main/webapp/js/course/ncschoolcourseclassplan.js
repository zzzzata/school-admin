$(function () {

    $(".datetimepicker_end").datetimepicker({
        format: 'yyyy-mm-dd 23:59:59',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
//	        todayHighlight: 1,//如果为true, 高亮当前日期。
//	        startView: 2,//日期时间选择器打开之后首先显示的视图。
//	        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
//	        showMeridian: 1
    });
    $(".datetimepicker_start").datetimepicker({
        format: 'yyyy-mm-dd 00:00:00',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
//	        todayHighlight: 1,//如果为true, 高亮当前日期。
//	        startView: 2,//日期时间选择器打开之后首先显示的视图。
//	        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
//	        showMeridian: 1
    });
    // $(".datetimepicker_start").val(new Date().format("yyyy-MM-dd 00:00:00"));
    // $(".datetimepicker_end").val(new Date().format("yyyy-MM-dd 23:59:59"));

    $("#jqGrid").jqGrid({
        url: '../ncschoolcourseclassplan/list',
        datatype: "local",
        colModel: [
            { label: 'id', name: 'id', key: true ,hidden:true},
            { label: 'nc校区id', name: 'ncSchoolId', width: 80,hidden:true },
            { label: 'nc排课id', name: 'courseclassplanId', width: 80 ,hidden:true },
            { label: 'nc课程id', name: 'courseId', width: 80,hidden:true },
            { label: 'nc授课老师用户编码', name: 'courseTeacherUsercode', width: 80,hidden:true  },
            { label: '授课老师id', name: 'courseTeacherId', width: 80 ,hidden:true },
            { label: 'nc考核老师用户编码', name: 'auditTeacherUsercode', width: 80 ,hidden:true },
            { label: 'nc考核老师id', name: 'auditTeacherId', width: 80,hidden:true  },
            { label: '校区名称', name: 'ncSchoolName', width: 80 },
            { label: 'nc排课名称', name: 'courseclassplanName', width: 80 },
            { label: 'nc课程名称', name: 'courseName', width: 80 },
            { label: '上课时点', name: 'scheduleTime', width: 80 },
            { label: '排课类型', name: 'classplanType', width: 80 },
            { label: '开课日期', name: 'dateTime', width: 80 },
            { label: '授课老师', name: 'courseTeacherName', width: 80 },
            { label: '考核老师', name: 'auditTeacherName', width: 80 },
            { label: '状态', name: 'status', width: 80,formatter(value, options, row){
                var text = '';
                if(value == 0)text='<span class="label label-success">正常</span>';
                else if(value == 1) text='<span class="label label-danger">结课</span>';
                return text;
            } },
            { label: '创建时间', name: 'createTime', width: 80 },
            { label: '修改时间', name: 'modifiedTime', width: 80 },
            { label: '产品线', name: 'productName', width: 80 },
            {label: '直播课数量', name: 'classplanLiveNum', width: 80},
            {label: '学生数量', name: 'studentNum', width: 80},
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
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

    $("#jqGridDetail").jqGrid({
        url: '../ncschoolcourseclassplan/classplanDetail',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', key: true ,hidden:true,width:80},
            { label: '课次id', name: 'courseclassplanLiveId', width: 80,hidden:true },
            { label: '课次内容', name: 'courseclassplanLiveName', width: 150 },
            { label: '上课时间', name: 'startTime', width: 150 },
            { label: '上课时点', name: 'scheduleTime', width: 80 },
            { label: '授课老师', name: 'courseTeacherName', width: 80 },
            { label: '上期复习', name: 'reviewUrl', width: 150 },
            { label: '上期复习文件名称', name: 'reviewName', width: 150 },
            { label: '本次预习', name: 'prepareUrl', width: 150 },
            { label: '本次预习文件名称', name: 'prepareName', width: 150 },
            { label: '课堂资料', name: 'coursewareUrl', width: 150 },
            { label: '课堂资料文件名称', name: 'coursewareName', width: 150 },
            {label: '阶段id', name: 'phaseId', width: 80},
            {label: '阶段名称', name: 'phaseName', width: 150},
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPagerDetail",
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
        	$("#jqGridDetail").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });


});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q: {
            classplanName: "",
            courseName: "",
            schoolName: "",
            courseTeacherName: "",
            startTime: "",
            endTime: "",
        },
		classplan:{
            classplanId:"",
        },
		showList: true,
		showDetailList:false,
        showSaveOrUpdate: false,
		title: null,
		ncSchoolCourseclassplan: {}
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.ncSchoolCourseclassplan = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../ncschoolcourseclassplan/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../ncschoolcourseclassplan/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.ncSchoolCourseclassplan),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../ncschoolcourseclassplan/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
            $.ajax({
				type: "POST",
			    url: "/ncschoolcourseclassplan/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.ncSchoolCourseclassplan = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (e , p) {
			vm.showList = true;
            vm.showSaveOrUpdate = false;
			vm.showDetailList = false;
            var startDate = $(".datetimepicker_start").val();
            var endDate = $(".datetimepicker_end").val();
            vm.q.startTime = startDate;
            vm.q.endTime = endDate;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                datatype:"json",
                postData: {
                    "classplanName":vm.q.classplanName,
                    "courseName":vm.q.courseName,
                    "schoolName":vm.q.schoolName,
                    "courseTeacherName":vm.q.courseTeacherName,
                    "starTime":vm.q.startTime,
                    "endTime":vm.q.endTime
                }
            }).trigger("reloadGrid");
		},
        look: function(){
            //获取选中行ID
            var id = getJqGridSelectedRow("jqGrid");
            if(id == null){
                return ;
            }
            //行数据
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            vm.classplan.classplanId = rowData.courseclassplanId;
            vm.showList = false;
            vm.showSaveOrUpdate=false,
            vm.showDetailList = true;
            vm.title = "线下排课详情";

            vm.detailRload(id, 1);
        },
        detailRload:function(id,p){
            //清空表格数据
            jQuery("#jqGridDetail").jqGrid("clearGridData");
            var page = p||$("#jqGridDetail").jqGrid('getGridParam','page');
            $("#jqGridDetail").jqGrid('setGridParam',{
                datatype: "json",
                postData : {
                    "classplanId" :vm.classplan.classplanId,
                },
                page:page
            }).trigger("reloadGrid");
        },
        goList: function(){
            vm.reload();
        },
        clearQuery:function(){
            var vm = this;
                vm.q.classplanName= "",
                vm.q.courseName= "",
                vm.q.schoolName="",
                vm.q.courseTeacherName= "",
                vm.q.startTime= "",
                vm.q.endTime= ""
        },
        userList: function (event) {//排课下的学员列表
            //获取选中行ID
            var id = getJqGridSelectedRow("jqGrid");
            if(id == null){
                return ;
            }
            //行数据
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            var classplanId = rowData.courseclassplanId;
            vm.title = "学员列表";

            vm.getUserListInfo(classplanId);
        },

        getUserListInfo: function (classplanId) {//获取学员列表信息
            ncUsersByclassplanLay.show(classplanId, null);
        },
		
	}
});