/**
 * 产品线弹窗js
 */
var wechatAccountLay = {
	init : function(){
		var that = this;
		$("#wechatAccountLay_jqGrid").jqGrid({
			url: "../layerdata/wechatAccountList",
            datatype: "local",
			colModel: [
                { label: 'id', name: 'id', key: true, width:30},
                { label: '产品线id', name: 'productId', width: 100,hidden:true },
                { label: '公众号标题', name: 'title', width: 170 },
                { label: '公众号id', name: 'appid', width: 170 },
                { label: '产品线名称', name: 'productName', width: 150 },
                { label: '备注说明', name: 'remark', width: 150 },
		           ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 25, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#wechatAccountLay_jqGridPager",
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
			   $("#wechatAccountLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	layerTitle : "微信公众号列表",
	layerHeigh : '650px',
	layerWeigh : '750px',
	show : function(scallback,productId){
		var that = this;
		that.scallback = scallback;
		that.reload(productId);
		that.layerIndex = layer.open({
			type : 1,//
			area : [that.layerWeigh,that.layerHeigh],
			title :that.layerTitle,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#wechatAccountLayDiv"),
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
	reload:function(productId){
		$("#wechatAccountLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
                productId : productId , //产品线名称
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("wechatAccountLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#wechatAccountLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
	}
}
wechatAccountLay.init();