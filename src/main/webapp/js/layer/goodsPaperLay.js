/**
 * 题库试卷弹窗js
 */
var goodsPaperLay = {
	init : function(){
		$("#qpaperName").val("");
		var that = this;
		$("#goodsPaperLay_jqGrid").jqGrid({
			url: "../layerdata/goodsPaperList",
			datatype: "local",
			colModel: [
			           { label: "试卷Id", name: "paperId",width: 300 , key: true},
			           { label: "试卷名称", name: "paperName",width: 300},
		           ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 35, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#goodsPaperLay_jqGridPager",
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
			   $("#goodsPaperLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	show : function(scallback){
		var that = this;
		that.scallback = scallback;
		that.reload();
		var title = "题库试卷列表";
		that.layerIndex = layer.open({
			type : 1,//
			area : ['730px', '650px'],
			title :title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#goodsPaperLayDiv"),
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
		$("#goodsPaperLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
				paperName : $("#qpaperName").val() , //商品名称
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("goodsPaperLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#goodsPaperLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
		$("#qpaperName").val("");
	}
}
goodsPaperLay.init();
