/**
 * 授课老师弹窗js
 */
var situastionLay = {
    init : function(){
        var that = this;
        $("#situastionLay_jqGrid").jqGrid({
            url: "../learningsituation/list",
            datatype: "local",
            colModel: [
                // { label: "主键", name: "id",width: 80 , key: true },
                { label: "已排课科目", name: "courseName", width: 140	 },
                { label: "排课计划名称", name: "classplanName", width: 200	  },
                { label: "参考日期", name: "referenceDate", width: 80	  },
                { label: "数据生成日期", name: "createMonth", width: 120	  },
                {
                    label: "听课完成率", name: "attendPer", width: 80, formatter: function (value, options, row) {
                        if (value == null) {
                            return "0%";
                        }
                        var str = Number(value * 100).toFixed(2);
                        str += "%";
                        return str;
                    }
                },
                {
                    label: "作业达标率", name: "jobPer", width: 80, formatter: function (value, options, row) {
                        if (value == null) {
                            return "0%";
                        }
                        return value + "%";
                    }
                },
                { label: "班主任评价", name: "teacherAssess", width: 160	  },
                // { label: "校区协助", name: "schoolAssist", width: 160	  }
            ],
            viewrecords: true,
            height: 400,
            rowNum: 10,
            rowList : [10,50,100],
            rownumbers: true,
            rownumWidth: 35,
            autowidth:true,
            multiselect: false,
            pager: "#situastionLay_jqGridPager",
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
                $("#situastionLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            }
        });
    },
    scallback : null,//回调方法
    layerIndex : null,//浮层index
    show : function(scallback , recordSignId){
        var that = this;
        that.scallback = scallback;
        that.recordSignId = recordSignId;
        that.reload(recordSignId);
        that.layerIndex = layer.open({
            type : 1,//
            area: ['1000px', '650px'],
            title :'学习情况',
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#situastionLayDiv"),
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
        $("#situastionLay_jqGrid").jqGrid('setGridParam',{
            page : 1,
            datatype : 'json',
            postData:{
                recordSignId:recordSignId,
                createMonth:$("#createMonth").val()
            }
        }).trigger("reloadGrid");
    },
	update:function () {
    	var that = this;
        var selectDetail = getJqGridSelectedRow("situastionLay_jqGrid");
        if(selectDetail == null){
            alert("必须选择一条学习情况信息");
            return;
        }
        that.layerIndex = layer.open({
            type : 1,//
            area: ['600px', '450px'],
            title :'修改',
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#situastionInfoLayDiv"),
            scrollbar : true,//是否允许浏览器出现滚动条
            fixed : false,//固定
            shadeClose : false,// 是否点击遮罩关闭
            resize : true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex : 19891014,
            btn : ['确认','取消' ],
            btn1: function (index) {
                if(vm.learningSituation.referenceDate != null){
                    var reg = /[0-9]{4}-[0-9]{2}/;
                    if (!reg.test(vm.learningSituation.referenceDate)) {
                        alert("参考日期格式不正确，请按照2019-05格式输入日期");
                        return;
                    }else if(vm.learningSituation.referenceDate.length != 7){
                        alert("参考日期格式不正确，请按照2019-05格式输入日期");
                        return;
                    }
                }
                layer.close(index);
                $.ajax({
                    type: "POST",
                    url: '../learningsituation/update',
                    data: JSON.stringify(vm.learningSituation),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                that.reload(that.recordSignId);
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            },
			success:function () {
                var selectDetail = getJqGridSelectedRow("situastionLay_jqGrid");
                that.getInfo(selectDetail);
            }
        });
    },
	getInfo:function (id) {
        $.ajax({
            type: "POST",
            url: "../learningsituation/info/" + id,
            success: function(r){
                if(r.code === 0){
                    vm.learningSituation = r.data;
                }else{
                    alert(r.msg);
                }
            }
        });
    }
}
situastionLay.init();
