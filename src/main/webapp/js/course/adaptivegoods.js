$(function () {
    $("#jqGrid").jqGrid({
        url: '../adaptivegoods/list',
        datatype: "json",
        colModel: [		
            { label: 'id', name: 'id', key: true,width: 20 },
            { label: '产品线id', name: 'productId', width: 80,hidden:true },
            { label: '商品id', name: 'mallGoodId', width: 50 },
            { label: '商品名称', name: 'mallGoodName', width: 80 },
            { label: '产品线', name: 'productName', width: 80},
            { label: '是否推送卡片', name: 'dr', width: 80,formatter(value, options, row){
                var text = '';
                if(value == 0)text='<span class="label label-success">推送</span>';
                else if(value == 1) text='<span class="label label-danger">不推送</span>';
                return text;
            } },
            { label: '是否是ios内购商品', name: 'isIapGood', width: 80,formatter(value, options, row){
                var text = '';
                if(value == 1)text='<span class="label label-success">是</span>';
                else if(value == 0) text='<span class="label label-danger">否</span>';
                return text;
            } }
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
		adaptiveGoods: {
		    mallGoodId:"",
            mallGoodName:"",
            productId:"",
            productName:"",
            dr:0,
            isIapGood:0
        },
        q:{
		    goodName:"",
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.adaptiveGoods = {
                mallGoodId:"",
                mallGoodName:"",
                productId:"",
                productName:"",
                dr:0,
                isIapGood:0
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
		       url = "../adaptivegoods/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../adaptivegoods/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.adaptiveGoods),
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
				    url: "../adaptivegoods/delete",
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
			    url: "../adaptivegoods/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.adaptiveGoods = r.data;
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
                postData:{
                    "goodName":vm.q.goodName
                }
            }).trigger("reloadGrid");
		},

        notes:function(){
		    alert("目前蓝鲸只有部分商品是有自适应推送学习卡片功能的,要想推送学习卡片,必须在这里新增要推送卡片的商品,这样学员app才会展示【学习卡片】模块，才能收到学习卡片")
        },

        clearQuery: function(){
            vm.q.goodsName = ""
        },

        selecGoods:function(){
            goodsInfoLay.show(function(index,opt){
                vm.adaptiveGoods.mallGoodId = opt.id;
                vm.adaptiveGoods.mallGoodName=opt.name;
            })
        },
        productLayerShow: function () {
            productLay.show(function(index, opt) {
                vm.adaptiveGoods.productId = opt.productId;
                vm.adaptiveGoods.productName = opt.productName;
            });
        }
	}
});