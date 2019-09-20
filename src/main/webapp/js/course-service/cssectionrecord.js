$(function () {
    $("#jqGrid").jqGrid({
        url: '../cssectionrecord/list',
        datatype: "local",
        colModel: [		
            { label: '主键id', name: 'sectionRecordId', width: 50,key: true },
            { label: '修改人Id', name: 'userId', width: 80,hidden:true },
            { label: '课程名称', name: 'courseName', width: 50, },
            { label: '节id', name: 'sectionId', width: 50, },
            { label: '名称', name: 'sectionName', width: 80 },
            { label: '编码', name: 'sectionNo', width: 80 },
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
		csSectionRecord: {},
        q:{
            courseName:"",
            sectionNo:"",
            sectionName:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.csSectionRecord = {};
		},
		update: function (event) {
			var sectionId = getSelectedRow();
			if(sectionId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(sectionId)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../cssectionrecord/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../cssectionrecord/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.csSectionRecord),
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
			var sectionIds = getSelectedRows();
			if(sectionIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cssectionrecord/delete",
				    data: JSON.stringify(sectionIds),
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
		getInfo: function(sectionId){
            $.ajax({
				type: "POST",
			    url: "/cssectionrecord/info/" + sectionId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.csSectionRecord = r.data;
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
        clearQuery: function(){
		    vm.q.courseName="",
		    vm.q.sectionNo="",
		    vm.q.sectionName=""
        }
		
	}
});