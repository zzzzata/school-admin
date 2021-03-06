/**
 * 知识点难度列表js
 */
var csConfigLay = {
	init : function(){
		var that = this;
		$("#csConfigLay_jqGrid").jqGrid({
			url: "../courses/layer/knowledgeLevel",
			datatype: "local",
			colModel: [
			           { label: "主键", name: "cvalue" , index: 'cvalue',key: true , hidden: false},
			           { label: "名称", name: "cname" , index: 'cname' , width: 250},
			           { label: "备注", name: "remark" , index: 'remark', hidden: true}
		           ],
		   viewrecords: true,
		   height: 320,
		   rowNum: 100,
		   rowList : [10,30,50,100],
		   rownumbers: true, 
//		   rownumWidth: 35, 
		   width:500,
		   autowidth:true,
		   multiselect: false,
		   pager: "#csConfigLay_jqGridPager",
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
			   $("#csConfigLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
		});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	title:null,
	knowldgeLevelShow : function(scallback){
		var that = this;
		that.scallback = scallback || null;
		that.url = "../courses/layer/knowledgeLevel";
		that.title = "知识点难度列表";
		that.show();
	},
	knowledgeQuestionTypeShow : function(scallback){
		var that = this;
		that.scallback = scallback || null;
		that.url = "../courses/layer/knowledgeQuestionType";
		that.title = "知识点题型列表";
		that.show();
	},
	knowledgeKeyPointShow : function(scallback){
		var that = this;
		that.scallback = scallback || null;
		that.url = "../courses/layer/knowledgeKeyPoint";
		that.title = "知识点考点类型列表";
		that.show();
	},
    knowledgeIsDifficult : function(scallback){
        var that = this;
        that.scallback = scallback || null;
        that.url = "../courses/layer/knowledgeIsDifficult";
        that.title = "知识点重难点类型列表";
        that.show();
    },
	show : function(){
		var that = this;
		that.reload();
		that.layerIndex = layer.open({
			type : 1,//
			area : ['600px','550px'],
			title :that.title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#csConfigLayDiv"),
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
		console.log(that.url);
		var csConfigLay_cname = $("#csConfigLay_cname").val();
		$("#csConfigLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
				cname : csConfigLay_cname 
            },
            url : that.url,
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("csConfigLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#csConfigLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
        //关闭浮层
		layer.close(this.layerIndex);
	}
}
csConfigLay.init();