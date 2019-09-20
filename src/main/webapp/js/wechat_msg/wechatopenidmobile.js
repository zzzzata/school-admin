$(function () {
    $("#jqGrid").jqGrid({
        url: '../wechatopenidmobile/list',
        datatype: "local",
        colModel: [		
            { label: 'id', name: 'id', key: true,width: 20 },
            { label: '微信openId', name: 'openid', width: 80,hidden:true },
            { label: '用户手机', name: 'mobile', width: 60 },
            { label: '蓝鲸用户id', name: 'userId', width: 60 },
            { label: '用户名称', name: 'userName', width: 80 },
            { label: '绑定状态', name: 'status', width: 80,formatter: function (value, options, row) {
                if($.isNull(row.openid)){
                    return '<span class="label label-danger">暂未绑定</span>'
                }else {
                    return '<span class="label label-success">绑定</span>'
                }
            } },
            { label: '学员微信昵称', name: 'wechatNickname', width: 80 },
            { label: '微信公众号id', name: 'appid', width: 100 },
            { label: '微信公众号名称', name: 'wechatTitle', width: 100 },
            { label: '学员微信id', name: 'wechatId', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 }
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
		wechatOpenidMobile: {},
        q:{
		    //查询维度: 1按手机号码 2按排课 3按班级
		    queryType:1,
		    mobile:"",
            classplanId:"",
            classplanName:"",
            classId:"",
            className:"",
            status:-1
        }
	},

	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wechatOpenidMobile = {};
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
		       url = "../wechatopenidmobile/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../wechatopenidmobile/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.wechatOpenidMobile),
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
				    url: "../wechatopenidmobile/delete",
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
			    url: "/wechatopenidmobile/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.wechatOpenidMobile = r.data;
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

        classplanLayShow : function(){//排课浮层
            vm.q.mobile="";
            vm.q.classId="";
            vm.q.className=""
            classplanLay.show(function(index,opt){
                vm.q.classplanId = opt.classplanId;
                vm.q.classplanName = opt.classplanName;
                vm.q.queryType=2;
            });
        },

        classLayShow: function () {//班级浮层
            vm.q.mobile="";
            vm.q.classplanId="";
            vm.q.classplanName="";
            classLay.show(function (index, opt) {
                vm.q.classId = opt.classId;
                vm.q.className = opt.className;
                vm.q.queryType=3;
            });
        },

        clearQuery: function () {
            vm.q.queryType=1;
            vm.q.mobile="";
            vm.q.classplanId="";
            vm.q.classplanName="";
            vm.q.classId="";
            vm.q.className="";
            vm.q.status=-1
        },

        note:function () {
            alert("手机号码,排课,班级是3个独立的查询维度,每次查询【不要同时】选择2个或2个以上的查询条件,不然可能造成数据不准确 \n 微信公众号id列数据为空则说明没有关注公众号")
        }
	}
});