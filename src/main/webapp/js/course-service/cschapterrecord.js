$(function () {
    $("#jqGrid").jqGrid({
        url: '../cschapterrecord/list',
        datatype: "local",
        colModel: [		
            { label: '主键id', name: 'chapterRecordId', width: 50,key: true },
            { label: '课程id', name: 'courseId', hidden:true,width: 50, },
            { label: '课程名称', name: 'courseName', width: 80, },
            { label: '章id', name: 'chapterId', width: 50, },
            { label: '修改人Id', name: 'userId', width: 80,hidden:true },
            { label: '名称', name: 'chapterName', width: 80 },
            { label: '编码', name: 'chapterNo', width: 80 },
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
		csChapterRecord: {},
        q:{
		    courseName:"",
            chapterNo:"",
            chapterName:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.csChapterRecord = {};
		},
		update: function (event) {
			var chapterId = getSelectedRow();
			if(chapterId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(chapterId)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../cschapterrecord/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../cschapterrecord/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.csChapterRecord),
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
			var chapterIds = getSelectedRows();
			if(chapterIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cschapterrecord/delete",
				    data: JSON.stringify(chapterIds),
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
		getInfo: function(chapterId){
            $.ajax({
				type: "POST",
			    url: "/cschapterrecord/info/" + chapterId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.csChapterRecord = r.data;
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
                datatype:"json",
                postData:vm.q
            }).trigger("reloadGrid");
		},

        clearQuery: function(){
		    vm.q.courseName="";
		    vm.q.chapterNo="";
		    vm.q.chapterName=""
        }
	}
});