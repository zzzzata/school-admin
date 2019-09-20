$(function () {
    $("#jqGrid").jqGrid({
        url: '../wechatclassplantemplate/list',
        datatype: "json",
        colModel: [		
            { label: 'id', name: 'id', key: true ,width:30},
            { label: '排课id', name: 'classplanId', width: 80 ,hidden:true},
            { label: '产品线id', name: 'productId', width: 80 ,hidden:true},
            { label: '创建用户Id', name: 'createUser', width: 80 ,hidden:true},
            { label: '排课名称', name: 'classplanName', width: 80 },
            { label: '消息模板id', name: 'templateId', width: 80 },
            { label: '消息模板名称', name: 'templateName', width: 80 },
            /*{ label: '模板类型', name: 'templateType', width: 80 },*/
            { label: '微信公众号id', name: 'appid', width: 80 },
            { label: '产品线名称', name: 'productName', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 },
            { label: '创建用户', name: 'createUserName', width: 80 },
            { label: '修改时间', name: 'updateTime', width: 80 },
            { label: '修改用户', name: 'updateUserName', width: 80 }
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
		wechatClassplanTemplate: {
            productId:"",
            productName:"",
            classplanId:"",
            classplanName:"",
            appid:"",
            wechatTitle:"",
            templateId:"",
            templateName:"",
            content:"",
            templateType: 1
        },
        q:{
		    classplanName:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wechatClassplanTemplate = {
                productId:"",
                productName:"",
                classplanId:"",
                classplanName:"",
                wechatAppId:"",
                wechatTitle:"",
                templateId:"",
                templateName:"",
                content:"",
                templateType:1
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
		       url = "../wechatclassplantemplate/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../wechatclassplantemplate/update";
		    }else
		    {
		       url = "";
		    }
		    if($.isNull(vm.wechatClassplanTemplate.templateId)){
		        alert("请选择微信消息模板")
                return;
            }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.wechatClassplanTemplate),
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
				    url: "../wechatclassplantemplate/delete",
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
			    url: "/wechatclassplantemplate/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.wechatClassplanTemplate = r.data;
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
                postData: vm.q
            }).trigger("reloadGrid");
		},
        clearQuery: function(){
		    vm.q.classplanName=""
        },
        productLayShow : function(){//产品线
            productLay.show(function(index, opt) {
                vm.wechatClassplanTemplate.productId = opt.productId;
                vm.wechatClassplanTemplate.productName = opt.productName;
            });
        },
        classplanLayShow : function(){//排课浮层
            classplanLay.show(function(index,opt){
                vm.wechatClassplanTemplate.classplanId = opt.classplanId;
                vm.wechatClassplanTemplate.classplanName = opt.classplanName;
            });
        },
        //微信公众号浮层
        wechatAccountLayShow: function () {
            var productId = vm.wechatClassplanTemplate.productId;
            if(productId== null || productId == ""){
                alert("请先选择产品线");
                return;
            }
            wechatAccountLay.show(function(index,opt){
                vm.wechatClassplanTemplate.appid = opt.appid;
                vm.wechatClassplanTemplate.wechatTitle = opt.title;
            },productId)
        },
        //微信消息模板浮层
        wechatTemplateLayShow:function(){
            var wechatAppId = vm.wechatClassplanTemplate.appid;
            if(wechatAppId== null || wechatAppId == ""){
                alert("请先选择微信公众号");
                return;
            }
            wechatTemplateLay.show(function(index,opt){
                vm.wechatClassplanTemplate.templateId = opt.templateId;
                vm.wechatClassplanTemplate.templateName = opt.title;
                vm.wechatClassplanTemplate.content = opt.content;
            },wechatAppId)
        },
        note: function () {
            alert("只有在这里绑定了微信公众号的排课的课次才会在直播课上课前2个小时自动推送到学员微信");
        }
	}
});