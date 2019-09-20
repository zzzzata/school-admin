var courseId = window.location.href.split('?')[1].split('&')[0].split('=')[1];
var courseNo = window.location.href.split('?')[1].split('&')[1].split('=')[1];
var liveAuditStats = window.location.href.split('?')[1].split('&')[2].split('=')[1];
$(function () {
    $("#jqGrid").jqGrid({
        url: '../cscourselivedetails/list',
        datatype: "json",
        postData:{courseId:courseId},
        colModel: [		
        	
														{ label: '课次id', name: 'liveId', key: true},
														{ label: '课次名称', name: 'liveName', width: 80 },
           												 { label: '序号', name: 'orderNum', width: 80 },
           												 { label: '上期复习文件', name: 'reviewName', width: 80 },
														{ label: '上期复习文件地址', name: 'reviewUrl', width: 80 },
														{ label: '本次预习文件', name: 'prepareName', width: 80 }, 						
														{ label: '本次预习文件地址', name: 'prepareUrl', width: 80 }, 						
														{ label: '课堂资料文件', name: 'coursewareName', width: 80 }, 						
														{ label: '课堂资料地址', name: 'coursewareUrl', width: 80 }, 						
														{ label: '阶段id', name: 'phaseId', width: 80 }, 						
														{ label: '阶段名称', name: 'phaseName', width: 80 }, 						
														// { label: '创建时间', name: 'createTime', width: 80 },
														// { label: '修改人', name: 'updatePerson', width: 80 },
														// { label: '课程id', name: 'courseId', width: 80 }
							
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
            order: "order",
            postData:vm.q
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
		csCourseLiveDetails: {
            reviewName:null,
            reviewUrl:null,
            prepareName:null,
            prepareUrl:null,
            coursewareName:null,
            coursewareUrl:null,
            liveName:null,
            phaseId:null,
            phaseName:null,
            orderNum:null,
            courseId:courseId
		}
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
            if(liveAuditStats==1){
                alert("课次已审核，请反审核后再操作");
                return;
            }
			vm.showList = false;
			vm.title = "新增";
			vm.csCourseLiveDetails = {
				reviewName:null,
                reviewUrl:null,
                prepareName:null,
                prepareUrl:null,
                coursewareName:null,
                coursewareUrl:null,
                liveName:null,
                phaseId:null,
                phaseName:null,
                orderNum:null,
                courseId:courseId
			};
		},
		update: function (event) {
            if(liveAuditStats==1){
                alert("课次已审核，请反审核后再操作");
                return;
            }
			var liveId = getSelectedRow();
			if(liveId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(liveId)
		},
		saveOrUpdate: function (event) {
			if(vm.csCourseLiveDetails.liveName == null || vm.csCourseLiveDetails.liveName.trim().length==0){
				alert("课次名称不允许为空");
				return;
			}
            if(vm.csCourseLiveDetails.orderNum == null || vm.csCourseLiveDetails.orderNum.length==0){
                alert("课次序号不允许为空");
                return;
            }
			console.log(vm.csCourseLiveDetails);
		    if(vm.title == "新增")
		    {
		       url = "../cscourselivedetails/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../cscourselivedetails/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.csCourseLiveDetails),
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
		del: function (event) {
            if(liveAuditStats==1){
                alert("课次已审核，请反审核后再操作");
                return;
            }
			var liveIds = getSelectedRows();
			if(liveIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../cscourselivedetails/delete",
				    data: JSON.stringify(liveIds),
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
		getInfo: function(liveId){
            $.ajax({
				type: "POST",
			    url: "/cscourselivedetails/info/" + liveId,
			    success: function(r){
			    	if(r.code === 0){
						 vm.csCourseLiveDetails = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (e , p) {
			vm.showList = true;
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        cancelCourse: function(){
            window.location.href="cscourse.html"
        },
        selectPhaseForDetail: function () {
            phaseLay.show(courseNo, function (index, opt) {
                /*alert(opt.phaseId);*/
                vm.csCourseLiveDetails.phaseId = opt.phaseId;
                vm.csCourseLiveDetails.phaseName = opt.phaseName;
            });
        },

	}
});