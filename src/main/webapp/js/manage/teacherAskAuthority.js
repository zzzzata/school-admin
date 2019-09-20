$(function () {
    $(".datetimepicker").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00',
        zIndex : 999999999,
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
    });
    $(".datetimepicker_start").datetimepicker({
        format: 'yyyy-mm-dd 00:00:00',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
    });
    $(".datetimepicker_end").datetimepicker({
        format: 'yyyy-mm-dd 23:59:59',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
    });
    var defaultStartTime = new Date(new Date().setMonth(new Date().getMonth()-1)).format("yyyy-MM-dd 00:00:00");
    var defaultEndTime = new Date().format("yyyy-MM-dd 23:59:59");
    $(".datetimepicker_start").val(defaultStartTime);
    $(".datetimepicker_end").val(defaultEndTime);
    $("#jqGrid").jqGrid({
        url: '../teacherTipAuthority/classTeacherAskList',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', hidden: true },
            { label: 'unlimitedAsk', name: 'unlimitedAsk', hidden: true },
            { label: 'tipIds', name: 'tipIds', hidden: true },
            { label: '用户id', name: 'userId',key: true,width: 80},
            { label: '班主任', name: 'nickName', width: 80 },
            { label: '手机号', name: 'mobile', width: 80 },
            { label: '无限次提问权限', width: 80, formatter: function(value, options, row){
                    return row.unlimitedAsk === 1 ? '开' : '关';
                }},
            { label: '父级标签', name: 'parentTipNames', width: 80,},
            { label: '子级标签', name: 'childTipNames', width: 80},
            { label: '修改人', name: 'updateUserName', width: 80 },
            { label: '修改时间', name: 'updateTime', width: 80 },
        ],
        viewrecords: true,
        height: 495,
        rowNum: 30,
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

//菜单树
var laber_ztree;
var laber_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "_id",
            pIdKey: "parent_tip_id",
            rootPId: -100
        },
        key: {
            url:"nourl",
            name : "tip_name",
        },

    },
    check: {
        enable: true
    },
    view: {
        showIcon: true
    }
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        teacher: {
            mobile: "",
            nickName: "",
            unlimitedAsk: ""
        },
        showList: true,
        title: null,
        teacherTipAuthority: {}
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "查看";
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            vm.getLaberTree(rowData);
            vm.getInfo(rowData);
        },
        saveOrUpdate: function (event) {
            vm.laberTreeSelect();
            var params = {
                "id":vm.teacherTipAuthority.id,
                "userId":vm.teacherTipAuthority.userId,
                "unlimitedAsk":vm.teacherTipAuthority.unlimitedAsk,
                "tipIds":vm.teacherTipAuthority.tipIds.toString()
            };
            $.ajax({
                url: "../teacherTipAuthority/updateClassTeacherAskAuthority",
                data:params,
                type: "post",
                contentType :"application/x-www-form-urlencoded",
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getInfo: function (rowData) {
            vm.teacherTipAuthority = rowData;
            if (!vm.teacherTipAuthority.unlimitedAsk) {
                vm.teacherTipAuthority.unlimitedAsk = 0;
            }
            if (!vm.teacherTipAuthority.tipIds) {
                vm.teacherTipAuthority.tipIds = "";
            }
        },
        reload: function (event, p) {
            vm.showList = true;
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "mobile": vm.teacher.mobile,
                    "nickName": vm.teacher.nickName,
                    "unlimitedAsk": vm.teacher.unlimitedAsk
                },
                page: page
            }).trigger("reloadGrid");
        },
        clearQuery : function(){//清空查询条件
            var vm = this;
            vm.teacher.mobile = "";
            vm.teacher.nickName = "";
            vm.teacher.unlimitedAsk = "";
        },
        getLaberTree: function() {
            $.get("../topics/topicsLaber/allLaberList", function(r){
                laber_ztree = $.fn.zTree.init($("#topicsLaberTree"), laber_setting, r.data);
                //展开所有节点
                laber_ztree.expandAll(false);
                $("#businessText-div").css('display','none');
                $("#businessId-div").css('display','block');

                //勾选老师所拥有的标签
                var tipIds = vm.teacherTipAuthority.tipIds;
                if (tipIds !== null && tipIds !== undefined && tipIds !== '') {
                    var tipIdArray = tipIds.split(',');
                    for(var i=0; i<tipIdArray.length; i++) {
                        var node1 = laber_ztree.getNodeByParam("_id", tipIdArray[i]);
                        if(null != node1) {
                            laber_ztree.checkNode(node1, true, true);
                            var parent = node1.getParentNode();
                            if(!parent.open){
                                laber_ztree.expandNode(parent,true,true);
                            }
                        }
                    }
                }
            });
        },
        laberTreeSelect: function(){
            var nodes = laber_ztree.getCheckedNodes(true);
            if(nodes == null){
                return ;
            }
            var laberIdList = new Array();
            for(var i=0; i<nodes.length; i++) {
                if(0 < nodes[i]["parent_tip_id"]) {
                    laberIdList.push(nodes[i]["_id"]);
                }
            }
            vm.teacherTipAuthority.tipIds = laberIdList;
        },
        importData: function(){
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '导入Excel',
                area: ['300px', '200px'],
                shadeClose: false,
                content: jQuery("#uploadExcel"),
                btn: ['确定','取消'],
                btn1: function (index) {
                    $.ajaxFileUpload({
                        url:'../teacherTipAuthority/importData',
                        secureuri:true,
                        fileElementId:'file_data',
                        dataType:'json',
                        success:function(data){
                            if(data.code == 0 && data.msg != null) {
                                alert(data.msg, function(index){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                                layer.close(index);
                            }else if(data.code == 0){
                                alert(data.msg, function(index){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                                layer.close(index);
                            }else if (data.code == 1) {
                                alert(data.msg);
                            }
                        }
                    });
                }
            });
        },
    }
});