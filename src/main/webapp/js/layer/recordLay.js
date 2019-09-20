/**
 * 录播课弹窗js
 */
var recordLay = {
	init : function(){
		$("#qrecordName").val("");
		var that = this;
		$("#recordLay_jqGrid").jqGrid({
			url: "../layerdata/recordList",
			datatype: "local",
			colModel: [
					{ label: '录播课ID', name: 'recordId', width: 100, key: true },
					{ label: '名称', name: 'name', width: 400 },
					{ label: '章节', name: 'type', width: 100 ,formatter: function(value, options, row){
						return value === 0 ? '章' : '节';
					}},
					{ label: '排序', name: 'orderNum', width: 100 },
		    ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 35, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#recordLay_jqGridPager",
		   jsonReader : {
			   root: "data.list",
			   page: "data.currPage",
			   total: "data.totalPage",
			   records: "data.totalCount"
		   },
		   prmNames : {
				page : "page",
				rows : "limit",
				order : "order"
			},
		   ondblClickRow : function(rowid,iRow,iCol,e){
			   that.select();
		   },
		   gridComplete:function(){
			   //隐藏grid底部滚动条
			   $("#recordLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	show : function(scallback){
		var that = this;
		that.scallback = scallback;
		that.reload();
		var title = "录播课列表";
		that.layerIndex = layer.open({
			type : 1,//
			area : ['900px', '700px'],
			title :title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#recordLayDiv"),
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
		$("#recordLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
				recordName : $("#qrecordName").val() , //录播课名称
            	courseId : vm.courseClassplan.courseId,
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("recordLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#recordLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
		$("#qrecordName").val("");
	}
}
recordLay.init();