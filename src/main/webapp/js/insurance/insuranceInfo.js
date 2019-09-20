$(function () {
    $("#jqGrid").jqGrid({
        url: '../insurance/insuranceInfo/list',
        datatype: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        colModel: [		
        	
					{ label: 'id', name: 'insuranceInfoId', key: true, width: 20  },

					{ label: '誉好产品编码', name: 'productCode',    width: 50 }, 
					{ label: '投保类型', name: 'insuranceType', align : "center",width: 20 ,formatter : function(value, options, row){
						if(value == 0){
							return '<span class="label label-success">全保</span>';
						}else if(value == 1){
							return '<span class="label label-success">单科</span>';
						 
						}  else {
							return '<span class="label label-success">保密</span>';
						}
					}
				},
					{ label: '学费金额', name: 'tuitionFee', width: 30 }, 						
					{ label: '投保金额', name: 'premium', width: 30 },  
					{ label: '赔付金额', name: 'compensationAmountName', width: 30 }, 
					 
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 35, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
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
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		q: {
			tuitionFee : '',
			premium : '', 
			productCode : '',
			insuranceType : '',
			compensationAmount : '',
			insuranceInfoId : '',
			
			
			
			
			
		}, 
		info:{
			tuitionFee : '',
			premium : '',
			productCode : '',
			insuranceType : '', 
			compensationAmount : '',
			insuranceInfoId : '',
	 
		}

	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},  
		add: function(id){
			vm.showList = false;
            vm.title = "新增";
            vm.info= {
    			tuitionFee : '',
    			premium : '',
    			productCode : '',
    			compensationAmount : '',
    			insuranceType : '',
    			insuranceInfoId : '',
    	 
    		}
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id)
            
           
		},
		getInfo: function(id){
            $.ajax({
				type: "POST",
			    url: "../insurance/insuranceInfo/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.info = r.insuranceInfo;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		saveOrUpdate: function (event) {
			
			if ($.isNull(vm.info.insuranceType)){
				alert("请选择保险类型！");
				return;
			}
			
			
		    if(vm.info.insuranceInfoId == null || vm.info.insuranceInfoId == "")
		    {
		       url = "../insurance/insuranceInfo/save";
		    }
		    else if(vm.info.insuranceInfoId != "")
		    {
		       url = "../insurance/insuranceInfo/update";
		    }else
		    {
		       url = "";
		    }
		    
		    
		    if(vm.info.insuranceType == 1 && $.isNull(vm.info.compensationAmount )){
				alert("单科保险必须填写【赔付金额】！");
				return;
			}
		    if ($.isNull(vm.info.compensationAmount )){
		    	vm.info.compensationAmount=0;
		    }
		    
		    if(vm.info.insuranceType == 0   && vm.info.compensationAmount > 0){
				alert("投保类型为全保时，【赔付金额】不用填写！");
				return;
			}
		    
		    
		    
		    
		    var param = {};
		    param.insuranceInfoId = vm.info.insuranceInfoId;
		    param.tuitionFee=vm.info.tuitionFee;
		    param.premium=vm.info.premium;
		    param.productCode = vm.info.productCode;
		    param.insuranceType = vm.info.insuranceType;
		    param.compensationAmount = vm.info.compensationAmount;
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(param),
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
		
	 
		query: function(){
			
		
			vm.reload(null , 1);
		},
		queryClear: function(){ 
			vm.q.tuitionFee= '';
			vm.q.premium = '';
			vm.q.productCode= '';
			vm.q.compensationAmount ='';
			vm.q.insuranceType = '';
			vm.q.insuranceInfoId= '';
		},   
		del: function (event) {
			var insuranceInfoIds = getSelectedRows();
			if(insuranceInfoIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../insurance/insuranceInfo/delete",
				    data: JSON.stringify(insuranceInfoIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		
		reload: function (e , p) {
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			var q = vm.q; 
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData: { 
                'tuitionFee':q.tuitionFee,
                'premium':q.premium,
                'productCode': q.productCode,
                'insuranceType':q.insuranceType,
                'compensationAmount':q.compensationAmount,
                'insuranceInfoId':q.insuranceInfoId
                }
            }).trigger("reloadGrid");
		}
		
	}
});