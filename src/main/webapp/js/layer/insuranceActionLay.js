/**
 * 保险投保所用的弹框js
 */ 
var insuranceActionLay = {
	init : function(){
		var that = this;  
		$("#insuranceActionLay_jqGrid").jqGrid({
			url: "../mall/mallgoodsinfo/insuranceGoodsDetail",
			datatype: "local",
			  colModel: [		
		        	//商品子表id
					{ label: 'id', name: 'id', hidden : true,key: true, width: 180  },
					{ label: 'mallGoodsId', name: 'mallGoodsId', hidden : true,  width: 180  },					
                	{ label: 'areaId', name: 'mallAreaId',width: 180,hidden: true  }, 
                	{ label: "状态", name : 'insuranceCourseStatusStr', width : 80/*,sortable:true*/,    },
                	{ label: '省份', name: 'areaName', width: 80  }, 
                	{ label: '课程', name: 'courseName',width: 300  }, 
                	{ label: '课时', name: 'subjectHour', width : 80 }, 
                	{ label : "保险科目", name : 'insuranceCourseStatus', width : 140, hidden : true  },
   				   
					 
					 
            ],
     	   viewrecords: true,
		   height: 500,
		   rowNum: 5000,
		   rowList : [200],
		   rownumbers: true, 
		   rownumWidth: 25, 
		   autowidth:true,
		   multiselect: true,
		   pager: "#insuranceActionLay_jqGridPager",
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
			   $("#insuranceActionLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		   }
			});
	},
	detailItem : {
		id : "",
		mallAreaId : "",
		mallGoodsId: "",
		areaId:"",
		areaName : "",
		courseId : "",
		courseName : "",
		isSubstituted : 0,
		isSubstitute : 0,
		isSuitable : 0,
		subjectHour:0,
		insuranceCourseStatus:0,
		insuranceCourseStatusStr:"",
		courseMap:new Map(),
		courseType : 0
	},
	
	
	
	
	scallback : null,//回调方法
	layerIndex : null,//浮层index 
	goodsId: null,
	show : function(id,name,professionName,levelName,classTypeName,scallback){ 
		var that = this;
		that.goodsId=id;
		$("#goodsId").val(id);
		$("#courseId").val(''); 
		$("#courseName").val(''); 
		$("#areaId").val('');
		$("#areaName").val('');
		that.scallback = scallback;
		that.reload(); 
		var title = "商品 :"+name+" "+ professionName+" "+levelName+" 的投保设置";
		that.layerIndex = layer.open({
			type : 1,//
			 //area : 'auto',
			 area : ['650px', '700px'],
			title :title,
			closeBtn : 1,
			skin:"layui-layer-lan",
			content : $("#insuranceActionLayDiv"),
			scrollbar : true,//是否允许浏览器出现滚动条
			fixed : false,//固定
			shadeClose : false,// 是否点击遮罩关闭
			resize : true,//是否允许拉伸
			maxmin: true, //开启最大化最小化按钮
			zIndex : 19891014,
			//content: '<div class="pay_lose">'+name+' '+professionName+' '+levelName+'</div>',
			/*btn : ['确认','取消' ],
			btn1: function (index) {
			 that.select();
			}*/
		});
		
	},
	clear:function(){
		$("#courseId").val(''); 
		$("#courseName").val(''); 
		$("#areaId").val('');
		$("#areaName").val('');
	},
	reload:function(){
		/*var page = $("#insuranceActionLay_jqGrid").jqGrid('getGridParam','page');*/
		$("#insuranceActionLay_jqGrid").jqGrid('setGridParam',{ postData : {
		 
			
			courseId : $("#courseId").val() ,
			areaId :  $("#areaId").val() ,
			goodsId :  $("#goodsId").val() , 
			sortableInsurance: $("#sortableInsurance").val() , 
    	    },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
	select:function(type){
		var details=[];
 
		//获取选中行ID
		var selectDetails = getJqGridSelectedRows("insuranceActionLay_jqGrid");
		if(selectDetails == null){
			return ;
		}
		 
		var checkedFalseNum=0;
		 var checked=true;//是否为勾投保
		  var postUrl = "../mall/mallgoodsinfo/insuranceActionUpdate";
		   if(type==0){
			   checked=false;
			   postUrl = "../mall/mallgoodsinfo/insuranceActionUpdateCancel";
		   }
		$.each(selectDetails , function(i , val){
			  
			
			 
				var	row= $('#insuranceActionLay_jqGrid').jqGrid('getRowData',val) ;
				 

				if ( checked&&$.isNull( row.courseName) ){
					 alert("空白名称的课程不允许投保！")
                     result = false;
					 return ;
				}
				//已经是勾选的不会再进行添加
				if (!$.isNull( row.insuranceCourseStatus)&&checked&&row.insuranceCourseStatus==1){
					checkedFalseNum=checkedFalseNum+1;
				}else {
					row.areaId=row.mallAreaId; 
					details.push(row);
				}
 
				
		     
		});
			if (checked&&selectDetails.length<=checkedFalseNum){
				 alert("勾保险时请不要勾已经投保的课程！请至少勾选一门没有投保的课程来投保。")
                
				 return ;
			}
		
		
		
		if(details.length > 0){
           $.ajax({
				type : "POST",
				url : postUrl,
				data : JSON.stringify(details),
				success : function(r) {
					if (r.code == 500) {
						alert(r.msg)
					} else if (r.code == 0) {

					} else {
						alert("未知错误！" + r.msg)
					}

					insuranceActionLay.reload();
				}

			});         
            
        }
		
		
		 
	},
// ///////////编辑子表浮层//////////////////
	areaLayerShowDetail : function(){// 省份
		 
		 areaLay.show(function(index, opt) { 
			 
			$("#areaName").val( opt.areaName); 
			$("#areaId").val( opt.areaId); 
		}); 
	},
	courseLayerShowDetail : function(){//课程
		 courseLay.show(function(index, opt) {
			//vm.detailItem.courseId = opt.courseId;
			$("#courseName").val(opt.courseName); 
			$("#courseId").val(opt.courseId); 
		}); 
	},
}
insuranceActionLay.init();


