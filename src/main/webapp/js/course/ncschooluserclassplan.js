$(function () {
    $("#jqGrid").jqGrid({
        url: '../ncschooluserclassplan/list',
        datatype: "local",
        colModel: [		
            { label: 'id', name: 'id', key: true, width: 20},
            { label: 'NC学员排课关联pk', name: 'ncUserClassplanId', width: 120 },
            { label: '蓝鲸学员id', name: 'userId', width: 60 },
            { label: '学员手机号码', name: 'mobile', width: 80 },
            { label: 'nc学员名称', name: 'userName', width: 80 },
            { label: 'nc排课id', name: 'courseclassplanId', width: 80 },
            { label: 'nc排课入课时间', name: 'timeStamp', width: 80 },
            { label: 'nc班型名称', name: 'ncClassType', width: 80 },
            { label: '是否是自适应课程', name: 'isAdaptiveCourse', width: 80,formatter(value, options, row){
                if (value == 1){
                   return  '<span class="label label-success">自适应课程</span>'
                }else {
                   return  '<span class="label label-danger">非自适应课程</span>'
                }
            } },
            { label: '是否开通学习计划', name: 'flag', width: 30 ,formatter(value, options, row){
                var text = '';
                if(value == 1)text='<span class="label label-success">开通</span>';
                else if(value == 0) text='<span class="label label-danger">关闭</span>';
                return text;
            }},
            { label: 'nc修改排课时间', name: 'ncModifiedTime', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 },
            { label: 'nc校区id', name: 'ncSchoolId', width: 80 },
            { label: 'nc校区名称', name: 'ncSchoolName', width: 80 }
							
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
		ncSchoolUserclassplan: {},
        q:{
            ncUserClassplanId:"",
		    mobile:""
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.ncSchoolUserclassplan = {};
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
		       url = "../ncschooluserclassplan/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../ncschooluserclassplan/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.ncSchoolUserclassplan),
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
				    url: "../ncschooluserclassplan/delete",
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
			    url: "/ncschooluserclassplan/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.ncSchoolUserclassplan = r.data;
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
                postData:{
                    "mobile":vm.q.mobile,
                    "ncUserClassplanId":vm.q.ncUserClassplanId
                },
                datatype: "json"
            }).trigger("reloadGrid");
		},
        clearQuery:function() {
            var vm = this;
            vm.q.ncUserClassplanId="",
            vm.q.mobile = ""
        },

        notes:function(){
		    alert("蓝鲸接收到的NC的学员排课内容,可用于查询NC排课后,蓝鲸是否有收到该排课信息,如果是自适应课程并开通学习计划才会生成线下学习计划,如果是关闭排课记录,没有学员手机号等信息")
        }
	}
});