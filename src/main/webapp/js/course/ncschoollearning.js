$(function () {
    $("#jqGrid").jqGrid({
        url: '../ncschoollearning/list',
        datatype: "json",
        colModel: [
            { label: 'learningId', name: 'learningId', key: true,hidden: true },
            { label: '蓝鲸学员id', name: 'userId', width: 80,hidden: true },
            { label: '部门id', name: 'deptId', width: 80,hidden: true  },
            { label: '线下学习计划编码', name: 'learningNo', width: 80 },
            { label: '学员姓名', name: 'userName', width: 80 },
            { label: '手机号', name: 'mobile', width: 80 },
            { label: '所属校区', name: 'deptName', width: 80, },
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
        url: '../ncschoollearning/learningDetail',
        datatype: "json",
        colModel: [
            { label: 'learningDetailId', name: 'learningDetailId', key: true ,hidden:true,width:800},
            { label: '排课id', name: 'classplanId', width: 150,hidden:true },
            { label: 'nc课程id', name: 'courseId', width: 150 ,hidden:true},
            { label: '课程', name: 'courseName', width: 200 },
            { label: '排课计划名称', name: 'classplanName', width: 200 },
            { label: '入课时间', name: 'timeStamp', width: 200 },
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
		showList: true,
        showDetailList: false,
        showSaveOrUpdate: false,
		title: null,
		ncSchoolLearning: {
		    learningId:"",
            learningNo:"",
            userName:""
        },
		q:{
		    learningNo:"",
            userName:"",
            mobile:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.ncSchoolLearning = {};
		},
		update: function (event) {
			var learningId = getSelectedRow();
			if(learningId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(learningId)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../ncschoollearning/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../ncschoollearning/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.ncSchoolLearning),
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
			var learningIds = getSelectedRows();
			if(learningIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../ncschoollearning/delete",
				    data: JSON.stringify(learningIds),
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
		getInfo: function(learningId){
            $.ajax({
				type: "POST",
			    url: "/ncschoollearning/info/" + learningId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.ncSchoolLearning = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (e , p) {
			vm.showList = true;
            vm.showSaveOrUpdate = false;
			vm.showDetailList= false;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData : {
                    "learningNo" :vm.q.learningNo,
                    "userName" :vm.q.userName,
                    "mobile" :vm.q.mobile,
                },
                page:page
            }).trigger("reloadGrid");
		},

        clearQuery: function(){
            var vm = this;
            vm.q.learningNo= "";
            vm.q.userName="";
            vm.q.mobile="";
        },

        look: function(){
            //获取选中行ID
            var id = getJqGridSelectedRow("jqGrid");
            if(id == null){
                return ;
            }
            //行数据
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            vm.ncSchoolLearning.learningId = rowData.learningId;
            vm.ncSchoolLearning.learningNo = rowData.learningNo;
            vm.ncSchoolLearning.userName = rowData.userName;
            vm.showList = false;
            vm.showSaveOrUpdate = false;
            vm.showDetailList = true;
            vm.title = "线下学习计划详情";

            vm.detailRload(id, 1);
        },
        detailRload:function(id,p){
            //清空表格数据
            jQuery("#jqGridDetail").jqGrid("clearGridData");
            var page = p||$("#jqGridDetail").jqGrid('getGridParam','page');
            $("#jqGridDetail").jqGrid('setGridParam',{
                datatype: "json",
                postData : {
                    "learningId" :vm.ncSchoolLearning.learningId,
                },
                page:page
            }).trigger("reloadGrid");
        },
        goList: function(){
            vm.reload();
        },

	}
});