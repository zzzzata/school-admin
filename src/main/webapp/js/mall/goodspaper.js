$(function () {
    $("#jqGrid").jqGrid({
        url: '../goodspaper/list',
        datatype: "json",
        colModel: [		
				{ label: 'id', name: 'id', key: true ,width: 40},
				{ label: '商品id', name: 'goodId', hidden: true ,width: 80 },
				{ label: '商品名称', name: 'goodName', width: 150 },
				{ label: '题库试卷id', name: 'paperId', hidden: true ,width: 80 }, 						
				{ label: '题库试卷名称', name: 'paperName', width: 150 }, 						
				{ label: '创建时间', name: 'createdTime', width: 80 }, 						
				{ label: '修改时间', name: 'updatedTime', width: 80 }, 						
				{ label: '创建者id', name: 'creator', hidden: true ,width: 80 },
				{ label: '创建者', name: 'creatorName', width: 80 },
				{ label: '修改者id', name: 'updater', hidden: true ,width: 80 },
				{ label: '修改者', name: 'updaterName', width: 80 },
							
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
		q: {
			goodName: null,
			paperName: null
		},
		showList: true,
		title: null,
		goodsPaper: {
			goodId: "",
			goodName: "",
			paperId: "",
			paperName: ""
		}
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.goodsPaper = {
					dr: 0,
					goodId: "",
					goodName: "",
					paperId: "",
					paperName: ""
			};
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
			if($.isNull(vm.goodsPaper.goodId)){
				alert("请选择商品！！！");
				return;
			}
			if($.isNull(vm.goodsPaper.paperId)){
				alert("请选择题库试卷！！！");
				return;
			}
			if(vm.title == "新增")
		    {
		       url = "../goodspaper/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../goodspaper/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.goodsPaper),
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
				    url: "../goodspaper/delete",
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
				type: "GET",
			    url: "../goodspaper/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.goodsPaper = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		goodsInfoLayerShow: function () {//商品信息浮层
            goodsInfoLay.show(function (index, opt) {
                //商品ID
                vm.goodsPaper.goodId = opt.id;
                //商品名称
                vm.goodsPaper.goodName = opt.name;
            });
        },
        goodsPaperLayerShow: function () {//题库试卷信息浮层
        	goodsPaperLay.show(function (index, opt) {
                //题库试卷ID
                vm.goodsPaper.paperId = opt.paperId;
                //题库试卷名称
                vm.goodsPaper.paperName = opt.paperName;
            });
        },
		reload: function (e , p) {
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData : vm.q,
				page:page
            }).trigger("reloadGrid");
		}
		
	}
});