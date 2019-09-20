/**
 * 授课老师弹窗js
 */
var teacherLay_asst = {
	init : function(){
		$("#qteacherName_asst").val("");
		var that = this;
		$("#teacherLay_asst_jqGrid").jqGrid({
			url: "../layerdata/teacherList",
			datatype: "local",
			colModel: [
	           { label: "主键", name: "userId" , key: true , hidden: true},
	           { label: "助教名称", name: "nickName" , width: 80	  },
	           
				{ label: '邮箱', name: 'email', width: 150 },
				{ label: '手机号', name: 'mobile', width: 120 },
				{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
					return value === 0 ? '<span class="label label-danger">禁用</span>' : '<span class="label label-success">正常</span>';
				}},
				{ label: '班主任', name: 'classTeacher', width: 80, formatter: function(value, options, row){
					return value === 1 ? '<span class="label label-success">是</span>' : '<span class="label label-danger">否</span>';
				}},
                { label: '助教', name: 'teacher', width: 60, formatter: function(value, options, row){
                        return '<span class="label label-success">是</span>';
                	}
                },
				{ label: '教学老师', name: 'teacher', width: 80, formatter: function(value, options, row){
					return value === 1 ? '<span class="label label-success">是</span>' : '<span class="label label-danger">否</span>';
				}},
				{ label: '创建时间', name: 'createTime', width: 80} 
		    ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 25, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#teacherLay_asst_jqGridPager",
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
		   ondblClickRow : function(rowid,iRow,iCol,e){
			   that.select();
		   },
		   gridComplete:function(){
			   //隐藏grid底部滚动条
			   $("#teacherLay_asst_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	show : function(scallback){
		var that = this;
		that.scallback = scallback;
		that.reload();
		var that = this;
		var title = "助教";
		if(1 == that.classTeacher){
			title = '班主任'
		}
		
		that.layerIndex = layer.open({
			type : 1,//
			area: ['900px', '650px'],
			title :title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#teacherLay_asstDiv"),
			scrollbar : true,//是否允许浏览器出现滚动条
			fixed : false,//固定
			shadeClose : false,// 是否点击遮罩关闭
			resize : true,//是否允许拉伸
			maxmin: true, //开启最大化最小化按钮
			zIndex : 19891014,
			btn : ['确认','取消' ],
			btn1: function (index) {
				that.select();
			}
		});
		
	},
	reload:function(){
		var that = this;
		$("#teacherLay_asst_jqGrid").jqGrid('setGridParam',{
			postData : {
				nickName : $("#qteacherName_asst").val() , //授课老师名称
				teacher : 0 ,
				classTeacher : 0,
				asst : 1
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("teacherLay_asst_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#teacherLay_asst_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
		$("#qteacherName_asst").val("");
	}
}
teacherLay_asst.init();
