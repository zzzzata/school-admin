$(function () {
    $("#jqGrid").jqGrid({
        url: '../unlimitedAskCourse/list',
        datatype: "json",
        colModel: [
            { label: '_id', name: '_id', hidden: true },
            { label: 'NC班型ID', name: 'courseId', width: 80 },
            { label: '班型名称', name: 'courseName', width: 80 },
            { label: '创建人', name: 'createUserName', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 },
            { label: '修改人', name: 'updateUserName', width: 80 },
            { label: '修改时间', name: 'updateTime', width: 80 },
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


var vm = new Vue({
    el: '#app',
    data: {
        showList: true,
        title: null,
        course: {
            courseId: "",
            courseName: "",
        },
        editorCourse: {}
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        addCourse: function() {
            vm.showList = false;
            vm.title = "新增";
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "查看";
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            vm.getInfo(rowData);
        },
        saveOrUpdate: function (event) {
            var params = {
                "id":vm.editorCourse._id,
                "courseId":vm.editorCourse.courseId,
                "courseName":vm.editorCourse.courseName,
                "dr":vm.editorCourse.dr
            };
            $.ajax({
                url: "../unlimitedAskCourse/addOrUpdate",
                data:params,
                type: "post",
                contentType :"application/x-www-form-urlencoded",
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.editorCourse = {}
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        deleteCourse:function () {
            var that = this
            confirm('是否删除该记录', function() {
                var id = getSelectedRow();
                if (id == null) {
                    return;
                }
                var rowData = $("#jqGrid").jqGrid('getRowData',id);
                vm.getInfo(rowData);
                vm.editorCourse.dr = 1;
                that.saveOrUpdate();
            })
        },
        getInfo: function (rowData) {
            vm.editorCourse = rowData;
        },
        reload: function (event, p) {
            vm.showList = true;
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "courseId": vm.course.courseId,
                    "courseName": vm.course.courseName,
                },
                page: page
            }).trigger("reloadGrid");
        },
        clearQuery : function(){//清空查询条件
            var vm = this;
            vm.course.courseId = "";
            vm.course.courseName = "";
        },
    }
});