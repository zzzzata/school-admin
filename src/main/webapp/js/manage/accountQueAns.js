$(function () {
    $(".datetimepicker").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00',
        zIndex : 999999999,
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
    });
    $(".datetimepicker_start").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 0,//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
        minuteStep:1//是选择分钟时显示的时间间隔，此处设置为1分钟
    });
    $(".datetimepicker_end").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 0,//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
        minuteStep:1//是选择分钟时显示的时间间隔，此处设置为1分钟
    });
    var defaultStartTime = new Date(new Date().setMonth(new Date().getMonth()-1)).format("yyyy-MM-dd 00:00:00");
    var defaultEndTime = new Date().format("yyyy-MM-dd 23:59:59");
    $(".datetimepicker_start").val(defaultStartTime);
    $(".datetimepicker_end").val(defaultEndTime);
    $("#jqGrid").jqGrid({
        url: '../accountQueAns/list',
        postData: {
            "startTime": defaultStartTime,
            "endTime": defaultEndTime
        },
        datatype: "local",
        colModel: [
            { label: 'tipId', name: 'tipId', hidden: true, key: true },
			{ label: '老师ID', name: 'teachId', width: 80, formatter: function(value, options, row){
                value = value === null ? '' : value;
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
            { label: '手机号', name: 'mobile', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
			{ label: '真实姓名', name: 'realName', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
            { label: '标签', name: 'tipName', width: 80 },
            { label: '问题数量', name: 'count', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
            { label: '很满意', name: 'henmangyi', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
            { label: '满意', name: 'mangyi', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
            { label: '不满意', name: 'bumangyi', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }},
            { label: '未评价量', name: 'weipingjia', width: 80, formatter: function(value, options, row){
                return row.tipId ===0 ? '<span style="color: red">'+value+'</span>' : value;
            }}
        ],
		viewrecords: true,
        height: 495,
        rowNum: -1,
        rowList : [200,500,1000,2000,5000,10000,20000,50000,100000,200000],
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
    el: '#rrapp',
    data: {
        q: {
            mobile: "",
            realName: "",
            teachId: "",
            parentTipIds: "",
            childTipIds: ""
        },
        showList: true,
        title: null,
        activityJoinInfo: {}
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.activityJoinInfo = {};
        },
        updateStatus: function (status, statusText) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要'+statusText+'选中的记录？',function(){
                $.ajax({
                    type: "POST",
                    url: "../accountQueAns/updateStatus?status="+status,
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

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            if (vm.title == "新增") {
                url = "../accountQueAns/save";
            }
            else if (vm.title == "查看") {
                url = "../accountQueAns/update";
            } else {
                url = "";
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.activityJoinInfo),
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
                    url: "../accountQueAns/delete",
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
        getInfo: function (id) {
            $.ajax({
                type: "POST",
                url: "/accountQueAns/info/" + id,
                success: function (r) {
                    if (r.code === 0) {
                        vm.activityJoinInfo = r.data;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function (event, p) {
            vm.showList = true;
            vm.q.startTime = $(".datetimepicker_start").val();
            vm.q.endTime = $(".datetimepicker_end").val();
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                datatype: "json",
                postData: {
                    "startTime": vm.q.startTime,
                    "endTime": vm.q.endTime,
                    "mobile": vm.q.mobile,
                    "realName": vm.q.realName,
                    "teachId": vm.q.teachId,
                    "parentTipIds": vm.q.parentTipIds,
                    "childTipIds": vm.q.childTipIds
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
        }
    }
});