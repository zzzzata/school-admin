/**
 * 商品弹窗js
 */
var insuranceInfoLay = {
	init : function(){
		var that = this;
		$("#insuranceInfoLay_jqGrid").jqGrid({
			url: "../insurance/insuranceInfo/list",
			datatype: "local",
			  colModel: [		
		        	
					{ label: 'id', name: 'insuranceInfoId', key: true, width: 80  },

					{ label: '誉好产品编码', name: 'productCode',    width: 180 }, 
					{ label: '投保类型编号', name: 'insuranceType',    width: 180,hidden: true  }, 
					{ label: '投保类型', name: 'insuranceTypeName', align : "center",width: 80 ,formatter : function(value, options, row){
					 
							return '<span class="label label-success">'+value+'</span>';
						 
					}
				},
					{ label: '学费金额', name: 'tuitionFee', width: 80 }, 						
					{ label: '投保金额', name: 'premium', width: 80 },  	
					 
            ],
     	   viewrecords: true,
		   height: 300,
		   rowNum: 100,
		   rowList : [200],
		   rownumbers: true, 
		   rownumWidth: 25, 
		   autowidth:true,
		   multiselect: false,
		   pager: "#insuranceInfoLay_jqGridPager",
		   jsonReader : {
			   root: "data.list",
			   page: "data.currPage",
			   total: "data.totalPage",
			   records: "data.totalCount"
		   },
		   ondblClickRow : function(rowid,iRow,iCol,e){
			   that.select();
		   },
		   gridComplete:function(){
			   //隐藏grid底部滚动条
			   $("#insuranceInfoLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	scallback : null,//回调方法
	layerIndex : null,//浮层index
	show : function(scallback){
		var that = this;
		that.scallback = scallback;
		that.reload();
		var title = "商品-投保金额-产品编码档案 列表";
		that.layerIndex = layer.open({
			type : 1,//
			//area : 'auto',
			area : ['600px', '500px'],
			title :title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#insuranceInfoLayDiv"),
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
		/*var page = $("#insuranceInfoLay_jqGrid").jqGrid('getGridParam','page');*/
		$("#insuranceInfoLay_jqGrid").jqGrid('setGridParam',{ postData : {
		 
			
			tuitionFee : $("#tuitionFee").val() ,
			premium :  $("#premium").val() ,
			productCode :  $("#productCode").val() ,
			insuranceType :  $("#insuranceType").val() ,
			insuranceTypeName :  $("#insuranceTypeName").val() ,
			insuranceInfoId :  $("#insuranceInfoId").val() ,
			
			
			
    	    },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(){
		var selectDetail = getJqGridSelectedRow("insuranceInfoLay_jqGrid");
		if(selectDetail == null){
			return ;
		} 
		
		//行数据
		var rowData = $("#insuranceInfoLay_jqGrid").jqGrid('getRowData',selectDetail);
		if($.isFunction(this.scallback)){
			this.scallback(selectDetail , rowData);
		}
		//关闭浮层
		layer.close(this.layerIndex);
	}
}
insuranceInfoLay.init();
