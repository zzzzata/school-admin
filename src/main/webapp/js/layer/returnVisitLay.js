/**
 * 回访信息弹窗js
 */
var returnVisitLay = {
    init : function(){
        var that = this;
        $("#returnVisitLay_jqGrid").jqGrid({
            url: "../returnvisit/list",
            datatype: "local",
            colModel: [
                { label: "主键", name: "id",width: 30 , key: true , hidden: true},
                { label: "预计回访日期", name: "expectTime", width: 120	  },
                { label: "回访状态", name: "returnStatus", width: 120,formatter : function(value, options, row){
                        if(value == 1){
                            return '已回访';
                        }else  {
                            return '未回访';
                        }
                    }  },
                { label: "回访日期", name: "returnTime", width: 120	  },
                { label: "回访人", name: "createPersonName", width: 120	  },
                { label: "回访内容", name: "returnContent", width: 200	  },
            ],
        viewrecords: true,
            height: 400,
            rowNum: 10,
            rowList : [10,30,50],
            rownumbers: true,
            rownumWidth: 25,
            autowidth:true,
            multiselect: false,
            pager: "#returnVisitLay_jqGridPager",
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
                $("#returnVisitLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            }
        });
    },
    scallback : null,//回调方法
    layerIndex : null,//浮层index
    show : function(scallback , studentData){
        var that = this;
        that.studentData = studentData;
        that.scallback = scallback;
        that.reload(studentData.recordSignId);
        that.layerIndex = layer.open({
            type : 1,//
            area: ['800px', '650px'],
            title :'回访信息',
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#returnVisitDiv"),
            scrollbar : true,//是否允许浏览器出现滚动条
            fixed : false,//固定
            shadeClose : false,// 是否点击遮罩关闭
            resize : true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex : 19891014,
            btn : ['取消' ]
        });

    },
    reload:function(recordSignId){
        var that = this;
        $("#returnVisitLay_jqGrid").jqGrid('setGridParam',{
            page : 1,
            datatype : 'json',
            postData:{recordSignId:recordSignId}
        }).trigger("reloadGrid");
    },
	update:function () {
    	var that = this;
        var selectDetail = getJqGridSelectedRow("returnVisitLay_jqGrid");
        if(selectDetail == null){
            alert("必须选择一条回访信息");
            return;
        }
        that.layerIndex = layer.open({
            type : 1,//
            area: ['600px', '450px'],
            title :'修改',
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#returnVisitLayDiv"),
            scrollbar : true,//是否允许浏览器出现滚动条
            fixed : false,//固定
            shadeClose : false,// 是否点击遮罩关闭
            resize : true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex : 19891014,
            btn : ['确认','取消' ],
            btn1: function (index) {
                layer.close(index);
                if(vm.returnVisit.returnStatus='未回访'){
                    vm.returnVisit.returnStatus=0;
                }else{
                    vm.returnVisit.returnStatus=1
                }
                $.ajax({
                    type: "POST",
                    url: '../returnvisit/update',
                    data: JSON.stringify(vm.returnVisit),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                that.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            },
			success:function () {
                var selectDetail = getJqGridSelectedRow("returnVisitLay_jqGrid");
                that.getInfo(selectDetail);
            }
        });
    },
    save:function () {
        var that = this;
        that.layerIndex = layer.open({
            type : 1,//
            area: ['600px', '450px'],
            title :'新增',
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#returnVisitSaveLayDiv"),
            scrollbar : true,//是否允许浏览器出现滚动条
            fixed : false,//固定
            shadeClose : false,// 是否点击遮罩关闭
            resize : true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex : 19891014,
            btn : ['确认','取消' ],
            btn1: function (index) {
                layer.close(index);
                $.ajax({
                    type: "POST",
                    url: '../returnvisit/save',
                    data: JSON.stringify(vm.returnVisit),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                that.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            },success:function () {
                vm.returnVisit = {
                    mobile:that.studentData.mobile,
                    userId:that.studentData.userId,
                    recordSignId:that.studentData.recordSignId
                }
            }
        });
    },
	getInfo:function (selectDetail) {
        $.ajax({
            type: "POST",
            url: "../returnvisit/info/" + selectDetail,
            success: function(r){
                if(r.code === 0){
                    vm.returnVisit = r.data;
                    if( vm.returnVisit.returnStatus==1){
                        vm.returnVisit.returnStatus = '已回访'
                    }else{
                        vm.returnVisit.returnStatus ='未回访'
                    }
                }else{
                    alert(r.msg);
                }
            }
        });
    }
}
returnVisitLay.init();
