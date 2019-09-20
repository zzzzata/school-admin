/**
 * 班级js
 */
var classLay = {
	init : function(){
		$("#classLayClassName").val("");
		var that = this;
		$("#classLay_jqGrid").jqGrid({
			url: "../insurancerecordcourse/list",
			datatype: "local",
			colModel: [
			           { label: "课程编码", name: "subjectCode", width: 120	  },
			           { label: "课程名称", name: "subjectName", width: 200	  },
			           { label: "标准课时", name: "subjectHour", width: 120	},
			           { label: "考期", name: "examDate", width: 120  }
		           ],
		   viewrecords: true,
		   height: 450,
		   rowNum: 100,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 35, 
		   autowidth:true,
		   pager: "#classLay_jqGridPager",
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
			   $("#classLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	show : function(insuranceRecordId,policyNo){
		var that = this;
		that.reload(insuranceRecordId,policyNo);
			var that = this;
			var title = "投保明细";
			that.layerIndex = layer.open({
				type : 1,//
				area : ['600px','700px'],
				title :title,
				closeBtn : 1,
				skin:"layui-layer-lan",
				content : $("#classLayDiv"),
				scrollbar : true,//是否允许浏览器出现滚动条
				fixed : false,//固定
				shadeClose : false,// 是否点击遮罩关闭
				resize : true,//是否允许拉伸
				maxmin: true, //开启最大化最小化按钮
				zIndex : 19891014,
				btn : ['取消' ]
			});
		
	},
	reload:function(insuranceRecordId,policyNo){
        $("#policyNo").text(policyNo);
		$("#classLay_jqGrid").jqGrid('setGridParam',{
			postData : {
                insuranceRecordId : insuranceRecordId
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	}
}
classLay.init();
