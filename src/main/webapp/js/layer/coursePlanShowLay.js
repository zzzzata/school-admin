/**
 * 班级js
 */
var coursePlanShowLay = {
    init : function(){
        var that = this;
        $("#coursePlanShow_jqGrid").jqGrid({
            url: "../layerdata/classplanList",
            datatype: "local",
            colModel: [
                { label: "排课计划id", name: "classplanId" , key: true , width: 80	  },
                { label: "排课计划名称", name: "classplanName" },
                { label: "开课日期", name: "startTime" },
                { label: "创建时间", name: "creationTime" }
            ],
            viewrecords: true,
            height: 450,
            rowNum: 10,
            rowList : [10,30,50],
            rownumbers: true,
            rownumWidth: 35,
            autowidth:true,
            multiselect: true,
            pager: "#coursePlanShow_jqGridPager",
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
                $("#coursePlanShow_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            }
        });
    },
    scallback : null,//回调方法
    layerIndex : null,//浮层index
    show : function(scallback){
        var that = this;
        that.scallback = scallback;
        that.reload();
        var that = this;
        var title = "班级列表";
        that.layerIndex = layer.open({
            type : 1,//
            area : ['850px','700px'],
            title :title,
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#coursePlanShowDiv"),
            scrollbar : true,//是否允许浏览器出现滚动条
            fixed : false,//固定
            shadeClose : false,// 是否点击遮罩关闭
            resize : true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex : 19891014,
            btn : ['确认','取消' ],
            btn1: function (index) {
                that.select();
            }
        });

    },
    showRows : function(scallback){
        var that = this;
        that.scallback = scallback;
        that.reload();
        var that = this;
        var title = "排课列表";
        that.layerIndex = layer.open({
            type : 1,//
            area : ['850px','700px'],
            title :title,
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#coursePlanShowDiv"),
            scrollbar : true,//是否允许浏览器出现滚动条
            fixed : false,//固定
            shadeClose : false,// 是否点击遮罩关闭
            resize : true,//是否允许拉伸
            maxmin: true, //开启最大化最小化按钮
            zIndex : 19891014,
            btn : ['确认','取消' ],
            btn1: function (index) {
                that.selectRows();
            }
        });

    },
    reload:function(){
        $("#coursePlanShow_jqGrid").jqGrid('setGridParam',{
            postData : {
                classplanName : $("#coursePlanShow").val() , //排课名称
                courseId: localStorage.getItem("courseId"),
            },
            page : 1,
            datatype : 'json'
        }).trigger("reloadGrid");
    },
    //单选
    select:function(){
        var selectDetail = getJqGridSelectedRow("coursePlanShow_jqGrid");
        if(selectDetail == null){
            return ;
        }
        //行数据
        var rowData = $("#coursePlanShow_jqGrid").jqGrid('getRowData',selectDetail);
        if($.isFunction(this.scallback)){
            this.scallback(selectDetail , rowData);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        $("#coursePlanShow").val("");
    },
    //多选
    selectRows:function(){
        var selectArrays = getJqGridSelectedRows("coursePlanShow_jqGrid");
        if(selectArrays == null){
            return ;
        }
        var classIdList = new Array();
        var classNameList = new Array();
        for(var i=0; i<selectArrays.length; i++) {
            //行数据
            var rowData = $("#coursePlanShow_jqGrid").jqGrid('getRowData',selectArrays[i]);
            classIdList.push(rowData.classplanId);
            classNameList.push(rowData.classplanName);
        }
        if($.isFunction(this.scallback)){
            this.scallback(classIdList,classNameList);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        $("#coursePlanShow").val("");
    }
}
coursePlanShowLay.init();