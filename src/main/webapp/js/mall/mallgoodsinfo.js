$(function() {
	$("#jqGrid").jqGrid({//主表数据初始化
		url : '../mall/mallgoodsinfo/list',
		datatype : "json",
		colModel : [
			{label : 'id', name : 'id', key : true , width:38 },
			
			{label : 'professionId', name : 'professionId', hidden: true },
			{label : 'levelId', name : 'levelId', hidden: true },
			{label : 'templateId', name : 'templateId', hidden: true },
			{label : 'insuranceTemplateId', name : 'insuranceTemplateId', hidden: true },
			
			{label : 'classTypeId', name : 'classTypeId', hidden: true },
			
			{label : '商品名称', name : 'name', width : 80 },
			{label : '班型', name : 'classTypeName', width : 80 },
			{label : '专业', name : 'professionName', width : 40 },
			{label : '层次', name : 'levelName', width : 50 },
			{label : 'NCID', name : 'ncId', width : 70 },
			{label : '售价', name : 'presentPrice', width : 50 },
			{label : '原价', name : 'originalPrice', width : 50 }, 
			{label : '小图', name : 'thumbPath', width: 50 , align : "center" ,formatter : function(value, options, row){return '<img height="32px" width="32px"  src="'+value+'"/>';}},
			{label : '大图', name : 'originPath', width: 50 , align : "center" ,formatter : function(value, options, row){return '<img height="32px" width="32px"  src="'+value+'"/>';}},
			
//			{label : '适用对象', name : 'suitableUser', width : 80 },
//			{label : '学习周期', name : 'learningTime', width : 80 },
//			{label : '上课方式', name : 'pattern', width : 80},
			{label : '产品线', name : 'productName', width : 80},
			
			/*{label : '修改人', name : 'modifyPersonName', width : 80 },
			{label : '修改时间', name : 'modifyTime', width : 100 },*/
			{label : '上架状态', name : 'status', width : 80,
				formatter : function(value, options, row) {
					return value === 1 ? '<span class="label label-success">上架</span>'
							: '<span class="label label-danger">下架</span>';
				}
			},
			{label : '审核状态', name : 'isAudited', width : 80,
				formatter : function(value, options, row) {
					return value === 1 ? '<span class="label label-success">通过</span>'
							: '<span class="label label-danger">驳回</span>';
				}
			},
			{label : '投保产品编码', name : 'productCode', width : 80},
			{ label: '投保类型', name: 'insuranceType', align : "center",width: 80 ,formatter : function(value, options, row){
				 
					if(value == 0){
						return '<span class="label label-success">全保</span>';
					}else if(value == 1){
						return '<span class="label label-success">单科</span>';
					 
					}  else {
						return '';
					}
				 
				
			}
		}
		],
		viewrecords : true,
		height : 385,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 35,
		autowidth : true,
		multiselect : true,
		pager : "#jqGridPager",
		jsonReader : {
			root : "page.list",
			page : "page.currPage",
			total : "page.totalPage",
			records : "page.totalCount"
		},
		prmNames : {
			page : "page",
			rows : "limit",
			order : "order"
		},
		gridComplete : function() {
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x" : "hidden"});
		}
	});
	/*--------------------------------------------初始化商品档案子表------------------------------------------*/
	$("#jqGridDetail").jqGrid({
		colModel : [ 
	         { label : "id", name : 'id', editable : false, hidden : true, key : true }, 
	        /* { label : "省份", name : 'areaName', index : 'areaName', width : 60 }, */
	         { label : "省份", name : 'mallAreaId', width : 80 , hidden : true}, 
	         { label : "课程", name : 'courseId', width : 80 , hidden : true }, 
	         { label : "isSubstituted", name : 'isSubstituted', width : 80 , hidden : true }, 
	         { label : "isSubstitute", name : 'isSubstitute', width : 80 , hidden : true }, 
	         { label : "isUnitedExam", name : 'isUnitedExam', width : 80 , hidden : true }, 
	         { label : "isSuitable", name : 'isSuitable', width : 80 , hidden : true }, 
	         
	         { label : "省份", name : 'areaName', width : 80 }, 
	         { label : "课程", name : 'courseName', width : 80 }, 
	         { label : "被替代课程", name : 'isSubstitutedName' , width : 50, formatter: function(value, options, row){return row.isSubstituted==1 ? "是" : "否"} }, 
	         { label : "替代课程", name: 'isSubstituteName', width : 40, formatter: function(value, options, row){return row.isSubstitute==1 ? "是" : "否"} },
	         { label : "统考", name : 'isUnitedExamName', width : 40, formatter: function(value, options, row){return row.isUnitedExam==1 ? "是" : "否"} },
	         { label : "专业不对口", name : 'isSuitableName', width : 40, formatter: function(value, options, row){return row.isSuitable==1 ? "是" : "否"} },
	         { label: '排课可冲突', name: 'courseEq', width: 40, formatter: function(value, options, row){
					return value === 0 ? 
						'<span class="label label-danger">不可冲突</span>' : 
						'<span class="label label-success">可以冲突</span>';
				}},
			//	 { label : "保险科目", name : 'insuranceCourseStatus', width : 40, hidden : true  },
				 { label : "保险科目", name : 'insuranceCourseStatusStr', width : 40,sortable:true,   },


			//	 { label : "课时", name : 'subjectHour', width : 80 }
		],
		height : 'auto',

		multiselect : true,
		pager : "#jqGridDetail",
		rowNum:5000, //每页显示记录数
		jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGridDetail").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
	});

});

var vm = new Vue(
{
	el : '#rrapp',
	data : {
		showList : true,
		title : null,
		q : { //商品查询条件
			id : "",
			name : "",
			suitableUser : "",
            ncId:"",
			status : "",
			levelId : "",
			levelName : "",
			professionId : "",
			professionName : "",
			classTypeId : "",
			classTypeName : "",
			productName : "",//产品线Name
			productId:"", 

			tuitionFee :"",
			premium:"",
			productCode:"",
			insuranceType:"",
			insuranceTypeName:"",
			insuranceInfoId:"",

		},
		mallGoodsInfo : {
			id:"",
			levelId : "",
			levelName : "",
			professionId : "",
			professionName : "",
			classTypeId : "",
			classTypeName : "",
			thumbPath:"",
			originPath:"",
			dayValidity : 1825,//有效期-默认五年
			productName : "",//产品线Name
			productId:"",//产品线ID
            onlyOne:"", //是否开通题库权限
            ncId:"",
            tkCourseCode:"",
            goodRecomment:"", //商品介绍
            
            	tuitionFee :"",
    			premium:"",
    			productCode:"",
    			insuranceType:"",
    			insuranceTypeName:"",
    			insuranceInfoId:"" 
		},
		detailTitle : "",
		detailItem : {
			id : "",
			mallAreaId : "",
			areaName : "",
			courseId : "",
			courseName : "",
			isSubstituted : 0,
			isSubstitute : 0,
			isSuitable : 0,
			//subjectHour:0,
			//insuranceCourseStatus:0,
			insuranceCourseStatusStr:"",
			courseMap:new Map(),
            courseEq:0,
			courseType : 0
		},
		copyArea : {//拷贝省份
			areaId1 : "",
			areaName1 : "",
			areaId2 : "",
			areaName2 : "",
			newGoodId : "",
			newGoodName : "",
		},
		copyGood : {//拷贝商品
			goodName : "",
			ncid: ""
		},
		levelSelections : [],
		professionSelections : [],
		classTypeSelections : [],
		areaSelections : [],
		courseSelections : [],
        tikuSubjectList: [], // 题库科目list
        tikuSubjectListTotal: 0, // 题库科目list 总条数
        tikuSubjectQueryName: "", // 题库科目list 查询条件name
        tikuSubjectQueryCode: "", // 题库科目list 查询条件code
        tikuSubjectListPage: 1, // 题库科目list 分页当前页数
        tikuSubjectListLimit: 10, // 题库科目list 分页一页多少调
        tikuSubjectListTotalPage: 0, // 题库科目list 分页总页数
        tikuSubjectListCurrCheck: [], // 题库科目list 当前选择
			},
			methods : {
				query : function() {
					vm.reload(null , 1);
				},
				add : function() {
					vm.showList = false;
					vm.title = "新增";
					vm.mallGoodsInfo = {
							levelId : "",
							levelName : "",
							professionId : "",
							professionName : "",
							classTypeId : "",
							classTypeName : "",
							templateName:"",
							insuranceTemplateName:"",
							thumbPath:"",
							originPath:"",
							dayValidity : 1825,//有效期-默认五年
							productName : "",//产品线Name
							productId:"",//产品线ID
                            onlyOne:1, //是否开通题库权限
                            ncId:"",
                            tkCourseCode:"",
                            goodRecomment:"", //商品介绍
                            	tuitionFee :"",
                    			premium:"",
                    			productCode:"",
                    			insuranceType:"",
                    			insuranceInfoId:"",
						};
					
					$("#auditButton").attr("style", "display: none");
					$("#commitButton").attr("style", "display: block");
				},
				update : function(event) {
					var id = getSelectedRow();
					if (id == null) {
						return;
					}
					vm.showList = false;
					vm.title = "修改";
					vm.getInfo(id);  
					$("#pjqGridDetail").attr("style", "display:block");
					
					$("#auditButton").attr("style", "display: none");
					$("#commitButton").attr("style", "display: block");
					$("#createPersonText").attr("readonly", "readonly");
					$("#createTimeText").attr("readonly", "readonly");
					$("#modifyPersonText").attr("readonly", "readonly");
					$("#modifyTimeText").attr("readonly", "readonly");
				},	
				saveOrUpdate : function(event) {
					if($.isNull(vm.mallGoodsInfo.ncId)){
						alert("请输入NCID！！！");
						return;
					}
					if(vm.mallGoodsInfo.onlyOne == 1){
						if($.isNull(vm.mallGoodsInfo.tkCourseCode)){
							alert("请输入题库课程编号！！！");
							return;
						}
					}
                    if(!$.isNull(vm.mallGoodsInfo.goodRecomment) && vm.mallGoodsInfo.goodRecomment.length > 100){
                        alert("商品介绍请控制在100字以内");
                        return;
                    }
					if(vm.title == "新增")
				    {
				       url = "../mall/mallgoodsinfo/save";
				    }
				    else if(vm.title == "修改")
				    {
				       url = "../mall/mallgoodsinfo/update";
				    }else
				    {
				       url = "";
				    }
				    
				    //子表所有数据
					var details = [] ;
					var ids = $("#jqGridDetail").jqGrid('getDataIDs');

					vm.detailItem.courseMap= new Map();
					for(var i = 0;i<ids.length;i++){
						var row = $('#jqGridDetail').jqGrid('getRowData',ids[i]);
						if(isNaN(row.id)){
		    				row.id = null;
		    			}
					 
/*						if (!$.isNull(vm.mallGoodsInfo.productCode)){
							 
						if (!$.isNull(row.insuranceCourseStatusStr)  ){
							var num=(row.insuranceCourseStatusStr=="是" ?  1: 0);
							if (num==1 &&$.isNull(row.courseName)){
								 alert("当前的为保险的商品，请确保投保科目不是空白的课程!</br>当前第"+(i+1)+"条记录的课程为空，不能投保。");
								  return  false;
							}
						vm.courseDetailAdd(row.areaName,num);
						
						}
						}*/
					    details.push(row);
					}
				//	console.log( vm.detailItem.courseMap);
	/*				if (!$.isNull(vm.mallGoodsInfo.productCode)){ 
						
						var insuranceOnly=0;
						if ( 							
							!$.isNull(vm.mallGoodsInfo.insuranceType) && vm.mallGoodsInfo.insuranceType == 1 ){
							insuranceOnly=1;
							
						} 
						var overEnableAreaName="";
						var notEnableAreaName="";
						var OnlyEnableAreaName="";
						 vm.detailItem.courseMap.forEach(function (value, key, map) {
							 	if (insuranceOnly==1&&value>1){
							 		OnlyEnableAreaName= key + "  省份投保的课程超过了1门,无法保存！保险类型为单科的每个省份的课程不能超过1门!" ;
							 	 
									  return false ;
							 	}
							 
							   if (value>18){
								   overEnableAreaName=overEnableAreaName+key+" ";
			                          
							   }else if(value==0){
								   notEnableAreaName=notEnableAreaName+key+" ";
							   }
							   
							}); 
						  
						 
						 if (!$.isNull(OnlyEnableAreaName)){
							 alert(OnlyEnableAreaName );
							  return ;
						 }
						 
						 
						if (!$.isNull(overEnableAreaName)){

							alert(overEnableAreaName + " <br>等省份投保的科目超过了18门！无法保存！");
							  return ;
						}else if (!$.isNull(notEnableAreaName)){
							 alert(notEnableAreaName + " <br>等省份没有选择投保科目！请认真核实一下这些省份是否要投保！");
							  return ;
						}
					}*/
					
				
					
					vm.mallGoodsInfo.detailList = details;
					
				    
					$.ajax({
						type: "POST",
					    url: url,
					    data: JSON.stringify(vm.mallGoodsInfo),
					    success: function(r){
					    	if(r.code === 0){
								alert('操作成功', function(index){
									vm.reload();
								});
							}else{
								alert(r.msg);
							}
						}
					});
				
				},
				del : function(event) {
					var id = getSelectedRow();
					if (id == null) {
						return;
					}

					confirm('确定要删除选中的记录？', function() {
						$.ajax({
							type : "POST",
							url : "../mall/mallgoodsinfo/delete/"+id,
							/*data : JSON.stringify(ids),*/
							success : function(r) {
								if (r.code == 0) {
									alert('操作成功', function(index) {
										$("#jqGrid").trigger("reloadGrid");
									});
								} else {
									alert(r.msg);
								}
							}
						});
					});
				},
				getInfo : function(id) {
					$.get("../mall/mallgoodsinfo/info/" + id,function(r) {
						if(!$.isNull(r.mallGoodsInfo)){
							if(!$.isNull(r.mallGoodsInfo.details)){
								for (var i = 0; i<r.mallGoodsInfo.details.length; i++) {
									for (var j = 0; j < vm.areaSelections.length; j++) {
										if (vm.areaSelections[j].value == r.mallGoodsInfo.details[i].mallAreaName) {
											r.mallGoodsInfo.details[i].mallAreaName = vm.areaSelections[j].name
										}
									}
									for (var j = 0; j < vm.courseSelections.length; j++) {
										if (vm.courseSelections[j].value == r.mallGoodsInfo.details[i].courseName) {
											r.mallGoodsInfo.details[i].courseName = vm.courseSelections[j].name
										}
									}
								}
							}
							
							vm.mallGoodsInfo = r.mallGoodsInfo;  
							
							
							if('templateName' in vm.mallGoodsInfo == false){
								Vue.set(vm.mallGoodsInfo,'templateName',''); 
 							}  
							if('insuranceTemplateName' in vm.mallGoodsInfo == false){
								Vue.set(vm.mallGoodsInfo,'insuranceTemplateName',''); 
 							}  
							if (vm.mallGoodsInfo.insuranceInfoId !=null){
								vm.getInsuranceInfo(vm.mallGoodsInfo.insuranceInfoId);
							}
							
							
							
							
							$('#jqGridDetail').setGridParam({
											datastr : JSON.stringify(vm.mallGoodsInfo.details),
											datatype : 'jsonstring'
										}).trigger('reloadGrid');

							}
							//分割题库科目 给弹窗使用
							if(vm.mallGoodsInfo && vm.mallGoodsInfo.tkCourseCode){
								vm.tikuSubjectListCurrCheck = vm.mallGoodsInfo.tkCourseCode.split(",");
							}

					});

				},
				
				getInsuranceInfo : function(id) { 
					$.ajax({
					type: "POST",
				    url: "../insurance/insuranceInfo/info/" + id,
				    success: function(r){
				    	if(r.code === 0){ 
				    		
				    		Vue.set(vm.mallGoodsInfo,'productCode',r.insuranceInfo.productCode); 
				    		Vue.set(vm.mallGoodsInfo,'tuitionFee',r.insuranceInfo.tuitionFee); 
				    	 
				    		Vue.set(vm.mallGoodsInfo,'premium',r.insuranceInfo.premium); /*
				    		vm.mallGoodsInfo.insuranceInfoId = r.insuranceInfo.insuranceInfoId;
							vm.mallGoodsInfo.productCode = r.insuranceInfo.productCode;
							vm.mallGoodsInfo.tuitionFee = r.insuranceInfo.tuitionFee;  
							vm.mallGoodsInfo.insuranceType = r.insuranceInfo.insuranceType;  
							vm.mallGoodsInfo.insuranceTypeName = r.insuranceInfo.insuranceTypeName;  
							vm.mallGoodsInfo.premium = r.insuranceInfo.premium;  */
				    		 
						}else{
							alert(r.msg);
						}
					}
				  });
					},
				
				
				
				reload : function(event , p) {
					vm.showList = true;
					//TODO 商品查询数据清空问题?
					$('#jqGridDetail').clearGridData(true);
					var page = p||$("#jqGrid").jqGrid('getGridParam', 'page');
					$("#jqGrid").jqGrid('setGridParam', {
						postData : vm.q,
						datatype: "json",
						page : page
					}).trigger("reloadGrid");
				},
				resume : function(event) {//启用
					var ids = getSelectedRows();
					if (ids == null) {
						return;
					}

					confirm('确定要启用选中的记录？', function() {
						$.ajax({
							type : "POST",
							url : "../mall/mallgoodsinfo/resume",
							data : JSON.stringify(ids),
							success : function(r) {
								if (r.code == 0) {
									alert('操作成功', function(index) {
										$("#jqGrid").trigger("reloadGrid");
									});
								} else {
									alert(r.msg);
								}
							}
						});
					});
				},
				pause : function(event) {//禁用
					var ids = getSelectedRows();
					if (ids == null) {
						return;
					}

					confirm('确定要禁用选中的记录？', function() {
						$.ajax({
							type : "POST",
							url : "../mall/mallgoodsinfo/pause",
							data : JSON.stringify(ids),
							success : function(r) {
								if (r.code == 0) {
									alert('操作成功', function(index) {
										$("#jqGrid").trigger("reloadGrid");
									});
								} else {
									alert(r.msg);
								}
							}
						});
					});
				},
				clearQuery : function(event) {//重置查询条件
					vm.q = {//商品查询条件
						id : "",
						name : "",
						suitableUser : "",
						ncId:"",
						status : "",
						levelId : "",
						levelName : "",
						professionId : "",
						professionName : "",
						classTypeId : "",
						classTypeName : ""
					}
				},
				audit : function(event) {//审核
					var id = getSelectedRow();
					if (id == null) {
						return;
					}
					vm.showList = false;
					vm.title = "审核";
					vm.getInfo(id);
					
					$("#pjqGridDetail").attr("style", "display:none");
					$("#auditButton").attr("style", "display: block");
					$("#commitButton").attr("style", "display: none");
					
					$("#createPersonText").attr("readonly", "readonly");
					$("#createTimeText").attr("readonly", "readonly");
					$("#modifyPersonText").attr("readonly", "readonly");
					$("#modifyTimeText").attr("readonly", "readonly");
					
					$("#levelContent").attr("readonly", "readonly");
					$("#levelContent").attr("disabled", "disabled");
					$("#professionContent").attr("readonly", "readonly");
					$("#professionContent").attr("disabled", "disabled");
					$("#classContent").attr("readonly", "readonly");
					$("#classContent").attr("disabled", "disabled");
					
					$("#patternTextContent").attr("readonly", "readonly");
					$("#learningTimeTextContent").attr("readonly", "readonly");
					$("#suitableUserTextContent").attr("readonly", "readonly");
					$("#originalPriceTextContent").attr("readonly", "readonly");
					$("#presentPriceTextContent").attr("readonly", "readonly");
					$("#nameTextContent").attr("readonly", "readonly");
				},
				accept : function(event) {//审核通过
					$.ajax({
						type : "POST",
						url : "../mall/mallgoodsinfo/accept",
						data : JSON.stringify(vm.mallGoodsInfo.id),
						success : function(r) {
							if (r.code === 0) {
								alert('操作成功', function(index) {
									vm.reload();
								});
							} else {
								alert(r.msg);
							}
						}
					});
				},
				reject : function(event) {//审核未通过
					$.ajax({
						type : "POST",
						url : "../mall/mallgoodsinfo/reject",
						data : JSON.stringify(vm.mallGoodsInfo.id),
						success : function(r) {
							if (r.code === 0) {
								alert('操作成功', function(index) {
									vm.reload();
								});
							} else {
								alert(r.msg);
							}
						}
					});
				},
				iadd:function(){//子表新增
//					courseLayer
					vm.detailTitle = "新增课程";
					vm.detailItem = {
						id : "",//id
						mallAreaId : "",//地区
						areaName : "",//地区
						courseId : "",//课程
						courseName : "",//课程名称
						isSubstituted : 0,//被代替
						isSubstitute : 0,//代替
						isSuitable : 0,//专业不对口
						courseType:0
					};
					//弹窗
					vm.detailLayer();
				},
				iupdate:function(){//子表修改
					var vm = this;
					//获取选中行ID
					var selectDetail = getJqGridSelectedRow("jqGridDetail");
					if(selectDetail == null){
						return ;
					}
					
					//行数据
					var rowData = $("#jqGridDetail").jqGrid('getRowData',selectDetail);
					//
					vm.detailItem = rowData;
					//console.log(vm.detailItem);
					if(vm.detailItem.isSubstituted == 1){//被代替
						vm.detailItem.courseType = 1;
					} else if (vm.detailItem.isSubstitute == 1) {
						vm.detailItem.courseType = 2;
					} else if (vm.detailItem.isSuitable == 1) {
						vm.detailItem.courseType = 3;
					}else{
						vm.detailItem.courseType = 0;
					}
					vm.detailTitle = "修改课程";
					//弹框
					vm.detailLayer();
				},
				detailLayer : function(){
					 
					var vm = this;
				/*	 $("#subjectHour").attr("disabled",true);
					 $("#subjectHour").attr("placeholder","不是单科保险的商品的不需要填课时");
					 
						if (!$.isNull(vm.mallGoodsInfo.insuranceType) && vm.mallGoodsInfo.insuranceType == 1 ){
						  $("#subjectHour").attr("disabled",false);
						  $("#subjectHour").attr("placeholder","保险的商品的请填课时");
						}*/
						
						
				/*		if ($.isNull(vm.mallGoodsInfo.productCode)){
							
							vm.detailItem.insuranceCourseStatus==0;
						//	$("icsid1").attr("style", "display: none");
							    $("#icsid1").attr({"disabled":"true","style":"display: none"});
							    $("#icsidl1").attr({"disabled":"true","style":"display: none"});
							    $("#icsid0").attr({"disabled":"true","style":"display: none"});
							    $("#icsidl0").attr({"disabled":"true","style":"display: none"}); 
							    $("#insuranceCourseStatusZone").attr({"disabled":"true","style":"display: none"}); 
							    
						   //    $("#icsid0").attr("disabled",true); 
						}else {*/
							    $("#icsid1").attr({"disabled":false,"style":"display: true"});
							    $("#icsidl1").attr({"disabled":false,"style":"display: true"});
							    $("#icsid0").attr({"disabled":false,"style":"display: true"});
							    $("#icsidl0").attr({"disabled":false,"style":"display: true"}); 
						 	  //  $("#insuranceCourseStatusZone").attr({"disabled":false,"style":"display: true"}); 
						//}
						
						
						
						
						
					layer.open({
						type: 1,
						skin: 'layui-layer-molv',
						title: vm.detailTitle,
						area: ['600px', '450px'],
						shadeClose: false,
						content: jQuery("#courseMaterialTypeLayer"),
						btn: ['确定','取消'],
						btn1: function (index) {
							if($.isNull(vm.detailItem.mallAreaId)){
								alert("请选择省份！");
								return
							}
							if($.isNull(vm.detailItem.courseId)){
								alert("请选择课程！");
								return;
							}
							if($.isNull(vm.detailItem.courseType)){
								alert("请选择课程类型！");
								return;
							}
							/*if(!$.isNull(vm.mallGoodsInfo.insuranceType) && vm.mallGoodsInfo.insuranceType == 1 && $.isNull(vm.detailItem.subjectHour)){
								alert("单科保险的商品请一定要填写课时!");
								return;
							}*/
						
							
							
							
							if( 0 == vm.detailItem.courseType){
								vm.detailItem.isSubstituted = 0;//被代替
								vm.detailItem.isSubstitute = 0;//代替
								vm.detailItem.isSuitable = 0;//专业不对口
							} else if(1 == vm.detailItem.courseType){
								vm.detailItem.isSubstituted = 1;//被代替
								vm.detailItem.isSubstitute = 0;//代替
								vm.detailItem.isSuitable = 0; //专业不对口
							} else if (2 == vm.detailItem.courseType) {
								vm.detailItem.isSubstituted = 0; //被代替
								vm.detailItem.isSubstitute = 1; //代替
								vm.detailItem.isSuitable = 0; //专业不对口
							} else if (3 == vm.detailItem.courseType) {
								vm.detailItem.isSubstituted = 0; //被代替
								vm.detailItem.isSubstitute = 0; //代替
								vm.detailItem.isSuitable = 1; //专业不对口
							}
							console.log(vm.detailItem);
							//全国统考 默认统考
							vm.detailItem.isUnitedExam = 1;
							// alert("操作完成，最终需点击页面底部的确认按钮才真正生效！");
							//console.log(vm.detailItem);
                           // var iStatusStr=(vm.detailItem.insuranceCourseStatus==1 ? '是':'否');
                           // vm.detailItem.insuranceCourseStatusStr = iStatusStr;
						if("新增课程" == vm.detailTitle){
								//行ID
								var rowId = new Date().getTime();
								//添加行
								$("#jqGridDetail").addRowData(rowId, vm.detailItem, "last");  
							}else if("修改课程" == vm.detailTitle){
								//修改
								$("#jqGridDetail").setRowData(getJqGridSelectedRow("jqGridDetail"),vm.detailItem);
								$.ajax({//仅添加日志功能，用于排查线上修改失效问题
									type: "POST",
									url: "../mall/mallgoodsinfo/updateCourse",
									data: JSON.stringify(vm.detailItem),
									success: function(r){
									}
								});
							}
							layer.close(index);
			            }
					});
				},
				idelete : function(){//子表删除
					var vm = this;
					//获取选中行ID
                    var selectDetail = getJqGridSelectedRow("jqGridDetail");
					if(selectDetail == null){
						return ;
					}
					confirm('确定要删除选中的记录？删除后立即生效', function(){
                        $.ajax({
                            type : "POST",
                            url : "../mall/mallgoodsinfo/deleteCourse/"+selectDetail,
                            /*data : JSON.stringify(ids),*/
                            success : function(r) {
                                if (r.code == 0) {
                                    alert('操作成功，已生效', function(index) {
                                        $("#jqGridDetail").jqGrid("delRowData", selectDetail);
                                    });
                                } else {
                                    alert(r.msg);
                                }
                            }
                        });
					});
				},
				
				insuranceAction : function(insuranceCourseStatus){//子表是否投保
					var result = true;
					var vm = this;
					var ids = [];
					//获取选中行ID
					var selectDetails = getJqGridSelectedRows("jqGridDetail");
					if(selectDetails == null){
						return ;
					}
					$.each(selectDetails , function(i , val){
						if ( $.isNull(vm.mallGoodsInfo.productCode) ){
							 alert("没有选择投保的商品不能使用本功能！")
							 return ;
							}
						
						
						
							var	row= $('#jqGridDetail').jqGrid('getRowData',val) ;
							//var confirm_str="";
							// var iStatus=row.insuranceCourseStatus==1 ?  1: 0;

							if ( insuranceCourseStatus==1&&$.isNull( row.courseName) ){
								 alert("空白名称的课程不允许投保！")
                                 result = false;
								 return ;
							}
							//var istatus1='';
						 //	var istatus0='';
							//	if (iStatus==1){

								//	iStatus=0
								//	istatus0=istatus0+ row.areaName+ " ";
							//	}else{
								//	iStatus=1;
								//	istatus1=istatus1+ row.areaName+ " ";
								//}
								var iStatusStr=(insuranceCourseStatus==1 ? '是':'否');

								//confirm('是否要将 <b>'+row.areaName+'</b> 的投保设置为  <b>'+iStatusStr +'</b> ?', function(){
						        $('#jqGridDetail').jqGrid('setRowData',val,{insuranceCourseStatus:insuranceCourseStatus,insuranceCourseStatusStr:iStatusStr});
								ids.push(val);
						        //	return true;
							//	});
					});
					if(result){
                        $("#jqGridDetail").jqGrid('resetSelection');
                        alert("操作完成，勾选课程已重置，最终需点击页面底部的确认按钮才真正生效！");
					}
                    var url = "../mall/mallgoodsinfo/insuranceAction";
                    if(insuranceCourseStatus == 0){
                        url = "../mall/mallgoodsinfo/insuranceActionCancel";
                    }
                    if(ids.length > 0){
                        $.ajax({//不执行数据库操作，仅记录日志，用于排查问题
                            type : "POST",
                            url : url,
                            data : JSON.stringify(ids),
                            success : function(r) {
                            }
                        });
                    }
				},
				
				getLevelSelections : function(event) {
					$.get("../mall/level/getSelectionList", function(r) {
						vm.levelSelections = r.levelSelections;
					});
				},
				 
				
				
				
				
				getProfessionSelections : function(event) {
					$.get("../common/professionList", function(r) {
						vm.professionSelections = r.professionSelections;
					});
				},
				getClassTypeSelections : function(event) {
					$.get("../mall/classtype/select", function(r) {
						vm.classTypeSelections = r.data;
					});
				},
				getAreaSelections : function(event) {
					var str = "";
					$.ajax({
						type : "get",
						url : "../mall/mallarea/getSelectionList",
						async : false,
						success : function(r) {
							vm.areaSelections = r.areaSelections;
							for (var i = 0; i < vm.areaSelections.length; i++) {
								str += vm.areaSelections[i].value + ":"
										+ vm.areaSelections[i].name;
								if (i != vm.areaSelections.length - 1) {
									str += ";"
								}
							}
						}
					});
					return str;
				},
			getCourseSelections : function(event) {
				var str = "";
				$.ajax({
					type : "get",
					url : "../mall/courses/getSelectionList",
					async : false,
					success : function(r) {
						vm.courseSelections = r.courseSelections;
						for (var i = 0; i < vm.courseSelections.length; i++) {
							str += vm.courseSelections[i].value + ":" + vm.courseSelections[i].name;
							if (i != vm.courseSelections.length - 1) {
								str += ";"
							}
						}
					}
				});
				return str;
			},
			////////////////////////////////查询条件浮层////////////////////////////////////
			classTypeLayerShowQuery : function() { //班型浮层
				classTypeLay.show(function(index, opt) {
					vm.q.classTypeId = opt.classtypeId;
					vm.q.classTypeName = opt.classtypeName;
				});
			},
			levelLayerShowQuery : function() { //层次浮层
				levelLay.show(function(index, opt) {
					vm.q.levelId = opt.levelId;
					vm.q.levelName = opt.levelName;
				});
			},
			professionLayerShowQuery : function() { //专业浮层
				professionLay.show(function(index, opt) {
					vm.q.professionId = opt.professionId;
					vm.q.professionName = opt.professionName;
				});
			},
			productLayerShowQuery : function(){
				productLay.show(function(index, opt) {
					vm.q.productId = opt.productId;
					vm.q.productName = opt.productName;
				});
				
			},
			////////////////////////编辑主表浮层//////////////////////////////////
			classTypeLayerShow : function() { //班型浮层
				classTypeLay.show(function(index, opt) {
					vm.mallGoodsInfo.classTypeId = opt.classtypeId;
					vm.mallGoodsInfo.classTypeName = opt.classtypeName;
				});
			},
			levelLayerShow : function() { //层次浮层
				levelLay.show(function(index, opt) {
					vm.mallGoodsInfo.levelId = opt.levelId;
					vm.mallGoodsInfo.levelName = opt.levelName; 
				});
			},
			professionLayerShow : function() { //专业浮层
				professionLay.show(function(index, opt) {
					vm.mallGoodsInfo.professionId = opt.professionId;
					vm.mallGoodsInfo.professionName = opt.professionName;
				});
			},
			templateLayerShow : function() { //模板浮层
				templateLay.show(function(index, opt) {
					vm.mallGoodsInfo.contractTemplateId = opt.id;
					vm.mallGoodsInfo.templateId = opt.templateId;
					vm.mallGoodsInfo.templateName = opt.templateName;  
				});
			},
			insuranceTemplateLayerShow : function() { //保险模板浮层
				templateLay.show(function(index, opt) { 
					vm.mallGoodsInfo.insuranceTemplateId = opt.id
					vm.mallGoodsInfo.insuranceTemplateName = opt.templateName;  
				});
			},
			insuranceInfoLayerShow: function() { //保险产品编号档案浮层
				insuranceInfoLay.show(function(index, opt) {
					
					if (!$.isNull(vm.mallGoodsInfo.insuranceType)&& opt.insuranceType!=vm.mallGoodsInfo.insuranceType){
						
						var ids = $("#jqGridDetail").jqGrid('getDataIDs');
						if (ids.length>0){
							alert("【投保类型】变更时，如果已经有课程详情子表的，则不允许变更！请删除课程详情子表后再变更【投保类型】。");
							return;
						}
						 
						
					}
					vm.mallGoodsInfo.insuranceInfoId = opt.insuranceInfoId;
					vm.mallGoodsInfo.productCode = opt.productCode;
					vm.mallGoodsInfo.tuitionFee = opt.tuitionFee;  
					vm.mallGoodsInfo.insuranceType = opt.insuranceType;  
					vm.mallGoodsInfo.insuranceTypeName = opt.insuranceTypeName;  
					vm.mallGoodsInfo.premium = opt.premium;  
				});
			},
			/////////////编辑子表浮层//////////////////
			areaLayerShowDetail : function(){//省份
				areaLay.show(function(index, opt) {
					vm.detailItem.mallAreaId = opt.areaId;
					vm.detailItem.areaName = opt.areaName;
				});
			},
			courseLayerShowDetail : function(){//课程
				courseLay.show(function(index, opt) {
					vm.detailItem.courseId = opt.courseId;
					vm.detailItem.courseName = opt.courseName;
					vm.detailItem.courseEq = opt.courseEq.indexOf("不") > -1 ? 0:1;
				});
			},
			productLayerShow : function(){//产品线
				productLay.show(function(index, opt) {
					vm.mallGoodsInfo.productId = opt.productId;
					vm.mallGoodsInfo.productName = opt.productName;
				});
				
			},

			tikuSubjectShow : function(){//题库科目
                vm.tikuSubjectListPage = 1; //初始化
                vm.tikuSubjectQueryName = ""; //初始化
                vm.tikuSubjectQueryCode = ""; //初始化
                vm.getTikuSubjectList();
                layer.open({
                    type: 1,
                    skin: 'layui-layer-molv',
                    title: vm.detailTitle,
                    area: ['750px', '650px'],
                    shadeClose: false,
                    content: jQuery("#tikuSubjectLayer"),
                    btn: ['确定','取消'],
                    btn1: function (index) {
                    	var temptikuSubjectStr = "";
                        vm.tikuSubjectListCurrCheck.forEach(item => {
                            temptikuSubjectStr = temptikuSubjectStr + item + ",";
						})
                        if(vm.tikuSubjectListCurrCheck && vm.tikuSubjectListCurrCheck.length > 0){
                            temptikuSubjectStr = temptikuSubjectStr.substring(0, temptikuSubjectStr.length - 1)
                        }
						vm.mallGoodsInfo.tkCourseCode = temptikuSubjectStr;
                        layer.close(index);
                    }
                });

			},
			//点击查询时初始化页数
			queryTikuSubjectList :function(){
				vm.tikuSubjectListPage = 1;
				vm.getTikuSubjectList();
			},
			//获取题库科目list
			getTikuSubjectList: function(){
				var params = {
                    'courseName' : vm.tikuSubjectQueryName,
                    'pageNum' : vm.tikuSubjectListPage,
                    'pageSize' : vm.tikuSubjectListLimit,
				}
				if (vm.tikuSubjectQueryCode != "") {
                    params.courseCode = vm.tikuSubjectQueryCode;
				}
                $.ajax({
                    type : "get",
                    //url : "http://tadmin.beta.hqjy.com/tiku_external/admin/common/getCourseList",
                    url : "../mall/mallgoodsinfo/getTKCourseList",
					data: params,
                    async : false,
                    success : function(r) {
                        if(r.code == 0){
                            console.log(vm.tikuSubjectList)
                            vm.tikuSubjectListTotal = r.count;
                            vm.tikuSubjectListTotalPage = r.pageTotal;
                            vm.tikuSubjectList = r.data;
                            vm.tikuSubjectList.forEach(item1 => {
                                item1.check = false;
                            	vm.tikuSubjectListCurrCheck.forEach(item2 => {
                            		if(item1.code == item2) {
                            			item1.check = true;
									}
								})
							})
                        }
                    }
                });
			},
			//上一页
            subjectLayerPrevious: function() {
				if (vm.tikuSubjectListPage <= 1) {
					alert("没有上一页了");
					return;
				}
                vm.tikuSubjectListPage--;
                vm.getTikuSubjectList();
			},
			//下一页
			subjectLayerNext: function() {
                if (vm.tikuSubjectListPage >= vm.tikuSubjectListTotalPage) {
                    alert("已经是最后一页了");
                    return;
                }
                vm.tikuSubjectListPage++;
                vm.getTikuSubjectList();
			},
			//题库科目勾选事件
            tikuSubjectCheck: function(item, obj) {
				if(obj.target.checked) {
                    vm.tikuSubjectListCurrCheck.push(item.code);
				} else {
                    vm.tikuSubjectListCurrCheck.forEach((item1, index) => {
						if (item.code == item1) {
                            vm.tikuSubjectListCurrCheck.splice(index, 1);
						}
					})
				}

			},
			//////////////////////////////////////  拷贝省份
			copyGoodAreaLayerShow : function(){//省份
				var id = getSelectedRow();
				if (id == null) {
					return;
				}
				var vm = this;
				var lindex = layer.open({
					type: 1,
					skin: 'layui-layer-molv',
					title: '拷贝省份',
					area: ['560px', '420px'],
					shadeClose: false,
					content: jQuery("#copyAreaLayer"),
					btn: ['确定','取消'],
					btn1: function (index) {
						if($.isNull(vm.copyArea.areaId1)){
							alert("请选择该商品的省份");
							return;
						}
						if($.isNull(vm.copyArea.areaId2)){
							alert("请选择该省份");
							return;
						}
//						if(vm.copyArea.areaId1 == vm.copyArea.areaId2){
//							alert("省份不能相同");
//							return;
//						}
						vm.copySave(lindex);
//						//关闭浮层
//						layer.close(index);
		            }
				});
			},
			copyArea1LayerShow : function(){//根據商品查詢省份浮层
				var id = getSelectedRow();
				if (id == null) {
					return;
				}
				areaGoodsLay.show(id,function(index,opt){
					vm.copyArea.areaId1 = opt.areaId;
					vm.copyArea.areaName1 = opt.areaName;
				});
			},
			copyArea2LayerShow : function(){//省份
				areaLay.show(function(index, opt) {
					vm.copyArea.areaId2 = opt.areaId;
					vm.copyArea.areaName2 = opt.areaName;
				});
			},
			copyArea3LayerShow : function(){//商品
				goodsInfoLay.show(function (index, opt) {
	                //商品ID
	                vm.copyArea.newGoodId = opt.id;
	                //商品名称
	                vm.copyArea.newGoodName = opt.name;
	            });
			},
			copySave:function(index){//copy保存方法
				var id = getSelectedRow();
				if (id == null) {
					return;
				}
				var pdata =	vm.copyArea;
				pdata.id = id;
//				console.log(pdata);
				hq.ajax({
					type: "POST",
				    url: "../mall/mallgoodsinfo/copyArea/",
				    data: pdata,
				    success: function(r){
						if(r.code == 0){
							alert('操作成功');
							//关闭浮层
							layer.close(index);
						}else{
							alert(r.msg);
						}
					}
				});
			},
			//////////////////////////////////////拷贝商品
			copyGoodLayerShow : function(){
				var id = getSelectedRow();
				if (id == null) {
					return;
				}
				var vm = this;
				vm.copyGood = {
						goodName : "",
						ncid : ""
				}
				var lindex = layer.open({
					type: 1,
					skin: 'layui-layer-molv',
					title: '拷贝商品',
					area: ['560px', '420px'],
					shadeClose: false,
					content: jQuery("#copyGoodLayer"),
					btn: ['确定','取消'],
					btn1: function (index) {
						if($.isNull(vm.copyGood.goodName)){
							alert("请输入新商品名称");
							return;
						}
						if($.isNull(vm.copyGood.ncid)){
							alert("请输入新商品ncid");
							return;
						}

						vm.copyGoodSave(lindex);

		            }
				});
			},
			copyGoodSave:function(index){//copyGood保存方法
				var id = getSelectedRow();
				if (id == null) {
					return;
				}
				var pdata =	vm.copyGood;
				pdata.id = id;
				hq.ajax({
					type: "POST",
				    url: "../mall/mallgoodsinfo/copyGood/",
				    data: pdata,
				    success: function(r){
						if(r.code == 0){
							alert('操作成功');
							//关闭浮层
							layer.close(index);
						}else{
							alert(r.msg);
						}
					}
				});
			},
			courseDetailAdd : function(areaName,num) {
				if($.isNull(vm.detailItem.courseMap)){
					vm.detailItem.courseMap.set(areaName,num);
				}else {
					var value=vm.detailItem.courseMap.get(areaName);
					if($.isNull(value)){
						value=0;
					}
					value=value+num;
					vm.detailItem.courseMap.set(areaName,value);
				}
				
				
			}, 
			clearInsurance: function(){//保险按钮 2019年3月8日 
				var id = getSelectedRow();
				if (id == null) {
					return;
				}
				confirm('是否要取消所选定商品的投保信息？取消的话则本商品不参与投保且本商品中的全部课程的投保状变为否，之前已经生成投保的不会受影响。', function() {
 					$.ajax({
						type : "POST",
						url : "../mall/mallgoodsinfo/clearInsurance",
						data : JSON.stringify(id),
						success : function(r) {
							if (r.code == 0) {
								alert('操作成功', function(index) {
									$("#jqGrid").trigger("reloadGrid");
								});
							} else {
								alert(r.msg);
							}
						}
					});  
				});        
				
					
				 
			},
			insuranceActionShow : function(){//保险按钮 2019年3月8日  
					var id = getSelectedRow();
					if (id == null) {
						return;
					}
					var goodsName = null;
					var classTypeName=null;
					var levelName=null;
					var professionName=null;
					$.get("../mall/mallgoodsinfo/info/" + id,function(r) {
										if (!$.isNull(r.mallGoodsInfo)) {
											if ($.isNull(r.mallGoodsInfo.details)) {
												alert("没有取到商品的课程，无法进行投保设置！");
												return;
											}
											if ($.isNull(r.mallGoodsInfo.insuranceInfoId)) {
												alert("商品没有设置投保产品编号，无法进行投保设置！");
												return;
											}
											if ($.isNull(r.mallGoodsInfo.insuranceTemplateName)) {
												alert("商品没有设置投保模板，无法进行投保设置！");
												return;
											}
											goodsName=r.mallGoodsInfo.name;
											classTypeName=r.mallGoodsInfo.classTypeName;
											levelName=r.mallGoodsInfo.levelName;
											professionName=r.mallGoodsInfo.professionName;
											console.log(id,name,professionName,levelName,classTypeName);
											//投保设置的弹框设置
											insuranceActionLay.show(id,goodsName,professionName,levelName,classTypeName, function(index, opt) {
												//这个弹框的不回写任何内容 

											});
										} else {
											alert("没有取到商品，请确认一下商品是否已经被删除了！");
											return;
										}
					});
					
				
					
				 
			},
			
			
		},
		created : function() {
//				this.getLevelSelections();
//				this.getProfessionSelections();
//				this.getClassTypeSelections();
			}
});

