/**
 * 班级js
 */
var headTeacherLay = {
    init : function(){
        var that = this;
        $("#headTeacher_jqGrid").jqGrid({
            url: "../layerdata/headTeacherStudentList",
            datatype: "local",
            cache:false,
            colModel: [
                { label: "班主任id", name: "userId" , key: true , width: 80	  },
                { label: "姓名", name: "nickName" },
                { label: "邮箱", name: "email" },
                { label: "创建时间", name: "createTime" }
            ],
            viewrecords: true,
            height: 450,
            rowNum: 10,
            rowList : [10,30,50],
            rownumbers: true,
            rownumWidth: 35,
            autowidth:true,
            multiselect: true,
            pager: "#headTeacher_jqGridPager",
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
                $("#headTeacher_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
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
            content : $("#headTeacherDiv"),
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
        var title = "班级列表";
        that.layerIndex = layer.open({
            type : 1,//
            area : ['850px','700px'],
            title :title,
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#headTeacherDiv"),
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
        $("#headTeacher_jqGrid").jqGrid('setGridParam',{
            postData : {
                className : $("#classLayClassName").val() , //班级名称
                teacherName : $("#headTeacherName").val() , //班主任名称
            },
            page : 1,
            datatype : 'json'
        }).trigger("reloadGrid");
    },
    //单选
    select:function(){
        var selectDetail = getJqGridSelectedRow("headTeacher_jqGrid");
        if(selectDetail == null){
            return ;
        }
        //行数据
        var rowData = $("#headTeacher_jqGrid").jqGrid('getRowData',selectDetail);
        if($.isFunction(this.scallback)){
            this.scallback(selectDetail , rowData);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        $("#classLayClassName").val("");
    },
    //多选
    selectRows:function(){
        var selectArrays = getJqGridSelectedRows("classLay_jqGrid");
        if(selectArrays == null){
            return ;
        }
        var classIdList = new Array();
        var classNameList = new Array();
        for(var i=0; i<selectArrays.length; i++) {
            //行数据
            var rowData = $("#headTeacher_jqGrid").jqGrid('getRowData',selectArrays[i]);
            classIdList.push(rowData.classId);
            classNameList.push(rowData.className);
        }
        if($.isFunction(this.scallback)){
            this.scallback(classIdList,classNameList);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        $("#classLayClassName").val("");
    }
}
// headTeacherLay.init();
