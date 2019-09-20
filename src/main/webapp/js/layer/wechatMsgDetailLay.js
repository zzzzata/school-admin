/**
 * 产品线弹窗js
 */
var wechatMsgDetailLay = {
	init : function(){
		var that = this;
		$("#wechatMsgDetailLay_jqGrid").jqGrid({
			url: "../wechattemplatemsg/wechatMsgDetailList",
            datatype: "local",
			colModel: [
                { label: 'id', name: 'id', key: true, width:30,hidden:true},
                { label: 'openid', name: 'openid', width: 100,hidden:true },
                { label: '接收人', name: 'mobile', width: 170 },
                { label: '内容', name: 'msgContent', width: 250 },
                { label: '发送状态', name: 'state', width: 150,formatter(value, options, row){
			        if (value == 0){
			            return '<span class="label label-success">发送成功</span>'
                    }else {
			            return '<span class="label label-danger">发送失败</span>'
                    }
                } },
                { label: '发送时间', name: 'sendTime', width: 150 },
		           ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 25, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#wechatMsgDetailLay_jqGridPager",
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
			   $("#wechatMsgDetailLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	layerTitle : "消息发送详情",
	layerHeigh : '650px',
	layerWeigh : '750px',
	show : function(scallback,msgId){
		var that = this;
		that.scallback = scallback;
		that.reload(msgId);
		that.layerIndex = layer.open({
			type : 1,//
			area : [that.layerWeigh,that.layerHeigh],
			title :that.layerTitle,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#wechatMsgDetailLayDiv"),
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
	reload:function(msgId){
		$("#wechatMsgDetailLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
                id : msgId , //消息记录id
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
        $.ajax({
            type: "POST",
            url: "../wechattemplatemsg/wechatMsgCount?id="+msgId,
            success: function(r){
                if(r.code === 0){
                   $("#totalCount").html(r.data.totalCount);
                   $("#successCount").html(r.data.successCount);
                   $("#failedCount").html(r.data.failedCount);
                }else{
                    alert(r.msg);
                }
            }
        });
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("wechatMsgDetailLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#wechatMsgDetailLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
	}
}
wechatMsgDetailLay.init();