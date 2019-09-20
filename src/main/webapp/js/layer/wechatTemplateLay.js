/**
 * 产品线弹窗js
 */
var wechatTemplateLay = {
	init : function(){
		var that = this;
		$("#wechatTemplateLay_jqGrid").jqGrid({
			url: "../layerdata/wechatTemplateList",
			datatype: "local",
			colModel: [
                { label: '模板内容', name: 'content', width: 350 ,hidden:true},
                { label: '模板id', name: 'templateId', width: 350 },
                { label: '模板名称', name: 'title', width: 350 },
            ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 25, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#wechatTemplateLay_jqGridPager",
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
			   $("#wechatTemplateLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	layerTitle : "消息模板列表",
	layerHeigh : '650px',
	layerWeigh : '750px',
	show : function(scallback,appid){
		var that = this;
		that.scallback = scallback;
		that.reload(appid);
		that.layerIndex = layer.open({
			type : 1,//
			area : [that.layerWeigh,that.layerHeigh],
			title :that.layerTitle,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#wechatTemplateLayDiv"),
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
	reload:function(appid){
		$("#wechatTemplateLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
                appid : appid , //微信公众号id
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("wechatTemplateLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#wechatTemplateLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
	}
}
wechatTemplateLay.init();