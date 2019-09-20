$(function () {
    $("#jqGrid").jqGrid({
        url: '../cscourserecord/list',
        datatype: "local",
        colModel: [		
            { label: '主键id', name: 'courseRecordId', width: 50, key: true },
            { label: '课程id', name: 'courseId', width: 50 },
            { label: '公司', name: 'deptId', width: 80 ,hidden: true },
            { label: '修改人Id', name: 'userId', width: 80 ,hidden:true},
            { label: '编号', name: 'courseNo', width: 80 },
            { label: '名称', name: 'courseName', width: 80 },
            { label: '产品线', name: 'productName', width: 80 },
            { label: '公司名称', name: 'deptName', width: 80 ,hidden: true },
            { label: '修改人', name: 'userName', width: 80 },
            { label: '修改时间', name: 'updateTime', width: 80 },
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		csCourseRecord: {},
        q:{
		    courseId:"",
            courseNo:"",
            courseName:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.csCourseRecord = {};
		},
		update: function (event) {
			var courseId = getSelectedRow();
			if(courseId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(courseId)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../cscourserecord/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../cscourserecord/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.csCourseRecord),
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
			var courseIds = getSelectedRows();
			if(courseIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cscourserecord/delete",
				    data: JSON.stringify(courseIds),
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
		getInfo: function(courseId){
            $.ajax({
				type: "POST",
			    url: "/cscourserecord/info/" + courseId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.csCourseRecord = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (e , p) {
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
			    postData:vm.q,
                datatype: "json",
                page:page
            }).trigger("reloadGrid");
		},
        clearQuery: function() {
            var vm = this;
            vm.q.courseId = "",
                vm.q.courseNo = "",
                vm.q.courseName = ""
        }
	}
});