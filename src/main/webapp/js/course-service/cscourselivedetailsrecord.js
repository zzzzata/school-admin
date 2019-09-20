$(function () {
    $("#jqGrid").jqGrid({
        url: '../cscourselivedetailsrecord/list',
        datatype: "json",
        colModel: [		
        	
											{ label: 'liveRecordId', name: 'liveRecordId', key: true,hidden:true },
            											{ label: '课程id', name: 'courseId', width: 80 },
														{ label: '原课次id', name: 'liveId', width: 80 },
														{ label: '课次名称', name: 'liveName', width: 80 },
            											{ label: '序号', name: 'orderNum', width: 80 },
														{ label: '上期复习文件', name: 'reviewName', width: 80 }, 						
														{ label: '上期复习文件地址', name: 'reviewUrl', width: 80 }, 						
														{ label: '本次预习文件', name: 'prepareName', width: 80 }, 						
														{ label: '本次预习文件地址', name: 'prepareUrl', width: 80 }, 						
														{ label: '课堂资料文件', name: 'coursewareName', width: 80 }, 						
														{ label: '课堂资料地址', name: 'coursewareUrl', width: 80 }, 						
														{ label: '阶段id', name: 'phaseId', width: 80 }, 						
														{ label: '阶段名称', name: 'phaseName', width: 80 },
														{ label: '修改时间', name: 'updateTime', width: 80 }, 						
														{ label: '修改人', name: 'nickName', width: 80 }
							
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
        csCourseLiveDetailsRecord:{},
		q:{
			courseId:null,
			liveId:null,
			liveName:null
		}
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
        clearQuery:function(){
			vm.q = {courseId:null,
                liveId:null,
                liveName:null};
            vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.csCourseLiveDetailsRecord = {};
		},
		update: function (event) {
			var liveRecordId = getSelectedRow();
			if(liveRecordId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(liveRecordId)
		},
		saveOrUpdate: function (event) {
		    if(vm.title == "新增")
		    {
		       url = "../cscourselivedetailsrecord/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../cscourselivedetailsrecord/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.csCourseLiveDetailsRecord),
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
			var liveRecordIds = getSelectedRows();
			if(liveRecordIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cscourselivedetailsrecord/delete",
				    data: JSON.stringify(liveRecordIds),
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
		getInfo: function(liveRecordId){
            $.ajax({
				type: "POST",
			    url: "/cscourselivedetailsrecord/info/" + liveRecordId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.csCourseLiveDetailsRecord = r.data;
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
				postData:vm.q
            }).trigger("reloadGrid");
		}
		
	}
});