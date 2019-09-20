$(function () {
    $("#jqGrid").jqGrid({
        url: '../teacherTipAuthority/list',
        datatype: "json",
        colModel: [
            { label: 'tuid', name: 'tuid', hidden: true, key: true },
            { label: 'isHq', name: 'isHq', hidden: true },
            { label: 'priv', name: 'priv', hidden: true },
            { label: 'answerPermission', name: 'answerPermission', hidden: true },
            { label: 'parentTipIdList', name: 'parentTipIdList', hidden: true },
            { label: 'businessId', name: 'businessId', hidden: true },
            { label: 'tipIdList', name: 'tipIdList', hidden: true },
            { label: '真实姓名', name: 'realName', width: 80 },
            { label: '手机号', name: 'mobile', width: 80 },
            { label: '校区', name: 'schoolName', width: 80 },
            { label: '标签', name: 'parentTipName', width: 80 },
            { label: '抢答权限', width: 80, formatter: function(value, options, row){
                return row.answerPermission ===0 ? '关' : '开';
            }}
        ],
		viewrecords: true,
        height: 495,
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
        q: {
            mobile: "",
            realName: "",
            teachId: "",
            parentTipIds: "",
            childTipIds: "",
            answerPermission: ""
        },
        showList: true,
        title: null,
        teacherTipAuthority: {}
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.teacherTipAuthority = {};
        },
        updateStatus: function (status, statusText) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要'+statusText+'选中的记录？',function(){
                $.ajax({
                    type: "POST",
                    url: "../teacherTipAuthority/updateStatus?status="+status,
                    data: JSON.stringify(ids),
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
            });
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
            if($.isNull(vm.teacherTipAuthority.isHq)){
                alert("请选择恒企员工！！！");
                return;
            }
            if($.isNull(vm.teacherTipAuthority.businessId)){
                alert("请选择教师类型！！！");
                return;
            }
            if (vm.title == "新增") {
                url = "../teacherTipAuthority/save";
            }
            else if (vm.title == "查看") {
                url = "../teacherTipAuthority/update";
            } else {
                url = "";
            }
            vm.laberTreeSelect();
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.teacherTipAuthority),
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
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../teacherTipAuthority/delete",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (rowData) {
            vm.teacherTipAuthority = rowData;
        },
        reload: function (event, p) {
            vm.showList = true;
            vm.q.startTime = $(".datetimepicker_start").val();
            vm.q.endTime = $(".datetimepicker_end").val();
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "startTime": vm.q.startTime,
                    "endTime": vm.q.endTime,
                    "mobile": vm.q.mobile,
                    "realName": vm.q.realName,
                    "teachId": vm.q.teachId,
                    "parentTipIds": vm.q.parentTipIds,
                    "childTipIds": vm.q.childTipIds,
                    "answerPermission": vm.q.answerPermission
                },
                page: page
            }).trigger("reloadGrid");
        },
        clearQuery : function(){//清空查询条件
            var vm = this;
            vm.q.mobile = "";
            vm.q.realName = "";
            vm.q.teachId = "";
            vm.q.parentTipIds = "";
            vm.q.childTipIds = "";
            vm.q.answerPermission = "";
            $("#tipInput").val("");
            var defaultStartTime = new Date(new Date().setMonth(new Date().getMonth()-1)).format("yyyy-MM-dd 00:00:00");
            var defaultEndTime = new Date().format("yyyy-MM-dd 23:59:59");
            $(".datetimepicker_start").val(defaultStartTime);
            $(".datetimepicker_end").val(defaultEndTime);
        },
        topicsLaberLayerShowQuery : function(){
            topicsLaberLay.show(function(opt,parentLaberIdList,laberIdList,laberNameList){
                vm.q.parentTipIds = parentLaberIdList.join(",");
                vm.q.childTipIds = laberIdList.join(",");
                $("#tipInput").val(laberNameList);
            });
        },
        getLaberTree: function() {
            $.get("../topics/topicsLaber/allLaberList", function(r){
                laber_ztree = $.fn.zTree.init($("#topicsLaberTree"), laber_setting, r.data);
                //展开所有节点
                laber_ztree.expandAll(true);

                var businessId = vm.teacherTipAuthority.businessId;
                laber_ztree.hideNodes(laber_ztree.getNodes());
                if(businessId == 'kuaiji') {
                    $("#businessText-input").val('会计');
                    $("#businessText-div").css('display','block');
                    $("#businessId-div").css('display','none');
                    laber_ztree = $.fn.zTree.init($("#topicsLaberTree"), laber_setting, laber_ztree.getNodeByParam("_id", -1));
                } else if(businessId == 'zikao') {
                    $("#businessText-input").val('自考');
                    $("#businessText-div").css('display','block');
                    $("#businessId-div").css('display','none');
                    laber_ztree = $.fn.zTree.init($("#topicsLaberTree"), laber_setting, laber_ztree.getNodeByParam("_id", -2));
                } else if (businessId == "qianyinli") {
                    $("#businessText-input").val('牵引力');
                    $("#businessText-div").css('display','block');
                    $("#businessId-div").css('display','none');
                    laber_ztree = $.fn.zTree.init($("#topicsLaberTree"), laber_setting, laber_ztree.getNodeByParam("_id", -3));
                } else {
                    $("#businessText-div").css('display','none');
                    $("#businessId-div").css('display','block');
                    laber_ztree = $.fn.zTree.init($("#topicsLaberTree"), laber_setting, laber_ztree.getNodes());
                }

                //勾选老师所拥有的标签
                var tipIds = vm.teacherTipAuthority.tipIdList;
                if (tipIds !== null && tipIds !== undefined && tipIds !== '') {
                    var tipIdArray = tipIds.split(',');
                    for(var i=0; i<tipIdArray.length; i++) {
                        var node1 = laber_ztree.getNodeByParam("_id", tipIdArray[i]);
                        if(null != node1) {
                            laber_ztree.checkNode(node1, true, true);
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
            vm.teacherTipAuthority.tipIdList = laberIdList;
        },
        topicsLaberLayerShowQuery : function(){
            topicsLaberLay.show(function(opt,parentLaberIdList,laberIdList,laberNameList){
                vm.q.parentTipIds = parentLaberIdList.join(",");
                vm.q.childTipIds = laberIdList.join(",");
                $("#tipInput").val(laberNameList);
            });
        },
    }
});