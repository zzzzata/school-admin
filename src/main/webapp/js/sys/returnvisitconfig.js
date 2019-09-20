$(function () {
    $("#jqGrid").jqGrid({
        url: '../returnvisitconfig/list',
        datatype: "json",
        colModel: [

            {label: 'id', name: 'id', key: true},
            {label: '产品线', name: 'productName', width: 80},
            {label: '生成频率天数', name: 'returnNum', width: 80},
            {label: '更新人', name: 'updatePersonName', width: 80},
            {label: '更新时间', name: 'updateTime', width: 80}

        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        returnVisitConfig: {
            //编号
            id: "",
            //产品线id
            productId: "",
            //产品线名称
            productName: "",
            //生成频率天数
            returnNum: ""
        }
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.returnVisitConfig = {
                //编号
                id: "",
                //产品线id
                productId: "",
                //产品线名称
                productName: "",
                //生成频率天数
                returnNum: ""
            };
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            if (vm.title == "新增") {
                url = "../returnvisitconfig/save";
            } else if (vm.title == "修改") {
                url = "../returnvisitconfig/update";
            } else {
                url = "";
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.returnVisitConfig),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }else if (r.code === -1){
                        alert(r.msg)
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
                    url: "../returnvisitconfig/delete",
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
                url: "../returnvisitconfig/info/" + id,
                success: function (r) {
                    if (r.code === 0) {
                        vm.returnVisitConfig = r.data;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        selectProduct: function () {//编辑-产品线
            productLay.show(function (index, opt) {
                vm.returnVisitConfig.productId = opt.productId;
                vm.returnVisitConfig.productName = opt.productName;

                $("#prodectName").val(opt.productName);
            });
        },
        reload: function (e, p) {
            vm.showList = true;
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }

    }
});