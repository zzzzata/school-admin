/**
 * 授课老师弹窗js
 */
var statsticsStudentLay = {
    init: function () {
        // $("#qteacherName1").val("");
        var that = this;
        $("#statsticsStudent_jqGrid").jqGrid({
            url: "../layerdata/courseList",
            datatype: "local",
            colModel: [
                {label: "课程id", name: "courseId", key: true, hidden: true},
                {label: "课程名称", name: "courseName", width: 100},
                {label: '课程类别', name: 'courseLb', width: 150},
                {label: '课程类型', name: 'courseType', width: 150},
                {label: '产品线名称', name: 'productName', width: 150},
                {label: '创建时间', name: 'creationTime', width: 80}
            ],
            viewrecords: true,
            height: 400,
            rowNum: 10,
            rowList: [10, 30, 50],
            rownumbers: true,
            rownumWidth: 25,
            autowidth: true,
            multiselect: false,
            pager: "#statsticsStudent_jqGridPager",
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
            ondblClickRow: function (rowid, iRow, iCol, e) {
                that.select();
            },
            gridComplete: function () {
                //隐藏grid底部滚动条
                $("#statsticsStudent_jqGridPager").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            }
        });
    },
    scallback: null,//回调方法
    layerIndex: null,//浮层index
    team: null,//授课老师
    // classTeacher: null,//班主任
    show: function (scallback, type) {
        var that = this;
        that.scallback = scallback;
        // if (2 === type) {
        //     that.classTeacher = 1;
        // } else {
        //     that.teacher = 1;
        // }
        that.reload();
        var that = this;
        var title = "团队";
        // if (1 == that.classTeacher) {
        //     title = '团队'
        // }

        that.layerIndex = layer.open({
            type: 1,//
            area: ['800px', '650px'],
            title: title,
            closeBtn: 1,
            skin: "layui-layer-lan",
            content: $("#statsticsStudentDiv"),
            scrollbar: true,//是否允许浏览器出现滚动条
            fixed: false,//固定
            shadeClose: false,// 是否点击遮罩关闭
            resize: true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex: 19891014,
            btn: ['确认', '取消'],
            btn1: function (index) {
                that.select();
            }
        });

    },
    reload: function () {
        var that = this;
                $("#statsticsStudent_jqGrid").jqGrid('setGridParam', {
                    postData: {
                        courseName: $("#statsticsCourseName").val(), //授课老师名称
                        // teacher: that.teacher,
                        // classTeacher: that.classTeacher
                    },
                    page: 1,
                    datatype: 'json'
                }).trigger("reloadGrid");
    },
    select: function () {
        var selectDetail = getJqGridSelectedRow("statsticsStudent_jqGrid");
        if (selectDetail == null) {
            return;
        }
        //行数据
        var rowData = $("#statsticsStudent_jqGrid").jqGrid('getRowData', selectDetail);
        if ($.isFunction(this.scallback)) {
            this.scallback(selectDetail, rowData);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        // $("#qteacherName1").val("");
    }
}
statsticsStudentLay.init();