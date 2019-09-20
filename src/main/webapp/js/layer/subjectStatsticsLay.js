/**
 * 授课老师弹窗js
 */
var subjectStatsticsLay = {
    init: function () {
        $("#teamNameShow").val("");
        localStorage.clear();
        var that = this;
        $("#subjectStatstics_jqGrid").jqGrid({
            url: "../layerdata/teamList",
            datatype: "local",
            colModel: [
                {label: "团队id", name: "id", key: true, hidden: true},
                {label: "名称", name: "name", width: 100},
                // {label: '父id', name: 'parentId', width: 150},
                {label: '层级', name: 'level', width: 150},
                {label: '团队领导', name: 'teamLeaderName', width: 150},
                {label: '创建时间', name: 'createTime', width: 80}
            ],
            viewrecords: true,
            height: 400,
            rowNum: 10,
            rowList: [10, 30, 50],
            rownumbers: true,
            rownumWidth: 80,
            autowidth: true,
            multiselect: false,
            pager: "#subjectStatstics_jqGridPager",
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
                $("#subjectStatstics_jqGridPager").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
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
            content: $("#subjectStatsticsDiv"),
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
    reload:function(){
        $("#subjectStatstics_jqGrid").jqGrid('setGridParam',{
            postData : {
                teamName : $("#teamNameShow").val() , //排课名称
            },
            page : 1,
            datatype : 'json'
        }).trigger("reloadGrid");
    },

    select: function () {
        var selectDetail = getJqGridSelectedRow("subjectStatstics_jqGrid");
        if (selectDetail == null) {
            return;
        }
        //行数据
        var rowData = $("#subjectStatstics_jqGrid").jqGrid('getRowData', selectDetail);
        if ($.isFunction(this.scallback)) {
            this.scallback(selectDetail, rowData);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        // $("#qteacherName1").val("");
    }
}
subjectStatsticsLay.init();