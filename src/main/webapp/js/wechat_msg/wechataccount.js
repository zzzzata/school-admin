$(function () {
    $("#jqGrid").jqGrid({
        url: '../wechataccount/list',
        datatype: "local",
        colModel: [		
            { label: 'id', name: 'id', key: true, width:30},
            { label: '产品线id', name: 'productId', width: 80,hidden:true },
            { label: '创建用户id', name: 'createUser', width: 80,hidden:true },
            { label: '公众号标题', name: 'title', width: 80 },
            { label: '公众号id', name: 'appid', width: 80 },
            { label: '公众号密钥', name: 'appSecret', width: 80 },
            { label: '产品线名称', name: 'productName', width: 80 },
            { label: '备注说明', name: 'remark', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 },
            { label: '创建用户', name: 'createUserName', width: 80 }
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
		wechatAccount: {
		    productId:"",
            productName:""
        },
        q:{
		    title:"",
            appid:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wechatAccount = {
			    productId:"",
                productName:"",
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
		    if(vm.title == "新增")
		    {
		       url = "../wechataccount/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../wechataccount/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.wechatAccount),
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
				    url: "../wechataccount/delete",
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
			    url: "/wechataccount/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.wechatAccount = r.data;
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
        clearQuery: function () {
            var vm = this;
            vm.q.title="";
            vm.q.appid=""
        },
        productLayerShow : function(){//产品线
            productLay.show(function(index, opt) {
                vm.wechatAccount.productId = opt.productId;
                vm.wechatAccount.productName = opt.productName;
            });
        },
	}
});