 /**
  * 产品线弹窗js
  */
 var liveRoomGenseeInfoLay = {
 	init : function(){
 		$("#qproductName").val("");
 		var that = this;
 		$("#liveRoomGenseeInfoLay_jqGrid").jqGrid({
 			url: "../layerdata/liveRoomGenseeList", 
 			datatype: "local",
 			colModel: [
 					{ label: 'id', name: 'id', key: true , width: 80},
 					{ label: '展视互动账号', name: 'genseeLoginname', width: 120 }, 						
 					{ label: '展视互动密码', name: 'genseePassword', width: 120 },
 					{ label: '展视互动回放记录地址', name: 'genseeWebcastVodLog', width: 120 }, 						
 					{ label: '展视互动直播记录', name: 'genseeWebcastLogUrl', width: 120 },
 					{ label: '展视互动域名', name: 'genseeDomain', width: 120 },
 					{ label: '备注', name: 'remark', width: 120 }
 		           ],
 		   viewrecords: true,
 		   height: 400,
 		   rowNum: 10,
 		   rowList : [10,30,50],
 		   rownumbers: true, 
 		   rownumWidth: 25, 
 		   autowidth:true,
 		   multiselect: false,
 		   pager: "#liveRoomGenseeInfoLay_jqGridPager",
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
 			   $("#liveRoomGenseeInfoLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
 		   }
 			});
 	},
 	scallback : null,//回调方法
 	layerIndex : null,//浮层index
 	layerTitle : "站点信息",
 	layerHeigh : '650px',
 	layerWeigh : '750px',
 	show : function(scallback){
 		var that = this;
 		that.scallback = scallback;
 		that.reload();
 		that.layerIndex = layer.open({
 			type : 1,//
 			area : [that.layerWeigh,that.layerHeigh],
 			title :that.layerTitle,
 			closeBtn : 1,
 			skin:"layui-layer-lan",
 			content : $("#liveRoomGenseeInfoLayDiv"),
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
 		$("#liveRoomGenseeInfoLay_jqGrid").jqGrid('setGridParam',{ 
 			postData : {
 				productName : $("#qproductName").val() , //产品线名称
             },
 			page : 1,
 			datatype : 'json'
 		}).trigger("reloadGrid");
 	},
 	select:function(){
 		var selectDetail = getJqGridSelectedRow("liveRoomGenseeInfoLay_jqGrid");
 		if(selectDetail == null){
 			return ;
 		}
 		//行数据
 		var rowData = $("#liveRoomGenseeInfoLay_jqGrid").jqGrid('getRowData',selectDetail);
 		if($.isFunction(this.scallback)){
 			this.scallback(selectDetail , rowData);
 		}
 		//关闭浮层
 		layer.close(this.layerIndex);
 		$("#qproductName").val("");
 	}
 }
 liveRoomGenseeInfoLay.init();