/**
 * 直播明细弹窗js
 */
var classplanliveLay = {
	init : function(){
		$("#qclassplanliveName").val("");
		var that = this;
		$("#classplanliveLay_jqGrid").jqGrid({
			url: "../layerdata/classplanlivesList",
			datatype: "local",
			colModel: [
					{ label: '主键PK', name: 'classplanLiveId', hidden : true, key: true },
					{ label: '排课计划PK', name: 'classplanId', hidden : true }, 	
					{ label: '课程ID', name: 'courseId', hidden : true, width: 80 }, 
					{ label: '直播课ID', name: 'courseLiveDetailId', hidden : true },
					{ label: '直播室ID', name: 'studioId', hidden : true },
					{ label: '授课老师ID', name: 'teacherId', hidden : true },
					{ label: '班型权限ids', name: 'liveClassTypeIds', hidden : true},
					{ label: '回放ID', name: 'backId', hidden : true, width: 80 },
					
					{ label: '直播课次名称', name: 'classplanLiveName', width: 150 },
					{ label: '排课计划名称', name: 'classplanName', width: 150 },
					{ label: '课程名称', name: 'courseName', width: 150 },
					{ label: '即将开始时间', name: 'readyTime', width: 150 },	
					{ label: '开始时间', name: 'startTime', width: 150 },	
					
					{ label: '结束时间', name: 'endTime', width: 150 },
					{ label: '进入直播间结束时间', name: 'closeTime', width: 150 },
					{ label: '授课老师', name: 'teacherName', width: 80 },
		    ],
		   viewrecords: true,
		   height: 400,
		   rowNum: 10,
		   rowList : [10,30,50],
		   rownumbers: true, 
		   rownumWidth: 35, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#classplanliveLay_jqGridPager",
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
			   $("#classplanliveLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	show : function(scallback){
		var that = this;
		that.scallback = scallback;
		that.reload();
		var title = "直播明细列表";
		that.layerIndex = layer.open({
			type : 1,//
			area : ['1300px', '700px'],
			title :title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#classplanliveLayDiv"),
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
		$("#classplanliveLay_jqGrid").jqGrid('setGridParam',{ 
			postData : {
				classplanLiveName : $("#qclassplanliveName").val() , //直播课次名称
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("classplanliveLay_jqGrid");
		if(selectDetail == null){
			return ;
		}
		//行数据
		var rowData = $("#classplanliveLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
		$("#qclassplanliveName").val("");
	}
}
classplanliveLay.init();