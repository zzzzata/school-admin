$(function () {
    $("#jqGrid").jqGrid({
        url: '../csknowledgerecord/list',
        datatype: "local",
        colModel: [		
            { label: '主键id', name: 'knowledgeRecordId',width: 50, key: true },
            { label: '修改人Id', name: 'userId', width: 50,hidden:true },
            { label: '课程id', name: 'courseId', width: 50,hidden:true},
            { label: '课程名称', name: 'courseName', width: 80,},
            { label: '知识点id', name: 'knowledgeId', width: 50,},
            { label: '名称', name: 'knowledgeName', width: 80 },
            { label: '编码', name: 'knowledgeNo', width: 80 },
            { label: '考点', name: 'keyPoint', width: 80,formatter(value, options, row){
                var text = '';
                if(value == 1)text='<span class="label label-success">正常</span>';
                else if(value == 2) text='<span class="label label-danger">考点</span>';
                return text;
            } },
            { label: '难度', name: 'levelId', width: 80 },
            { label: '所属节PK', name: 'sectionId', width: 80 },
            { label: '所属节编码', name: 'sectionNo', width: 80 },
            { label: '所属节名称', name: 'sectionName', width: 80 },
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
		csKnowledgeRecord: {},
        q:{
            courseName:"",
            knowledgeNo:"",
            knowledgeName:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.csKnowledgeRecord = {};
		},
		update: function (event) {
			var knowledgeId = getSelectedRow();
			if(knowledgeId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(knowledgeId)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../csknowledgerecord/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../csknowledgerecord/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.csKnowledgeRecord),
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
			var knowledgeIds = getSelectedRows();
			if(knowledgeIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../csknowledgerecord/delete",
				    data: JSON.stringify(knowledgeIds),
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
		getInfo: function(knowledgeId){
            $.ajax({
				type: "POST",
			    url: "/csknowledgerecord/info/" + knowledgeId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.csKnowledgeRecord = r.data;
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
                page:page,
                postData:vm.q,
                datatype:"json"
            }).trigger("reloadGrid");
		},
        clearQuery:function(){
		        var vm = this;
		        vm.q.courseName = "",
                vm.q.knowledgeName="",
                vm.q.knowledgeNo=""
        }
	}
});