/**
 * 查询团队js
 */
var teamLay ={
    init : function(){
        $("#teamName").val("");
        var that = this;
        $("#teamLay_jqGrid").jqGrid({
            url: "../systeam/list?status=1",
            datatype: "local",
            colModel: [
                { label: "团队id", name: "id" , key: true , width: 150	  },

                { label: "团队名称", name: "name" , width: 300	},

                { label: "上级团队", name: "parentName" , width: 300	},
            ],
            viewrecords: true,
            height: 450,
            rowNum: 30,
            rowList : [50,100,150,200],
            rownumbers: true,
            rownumWidth: 35,
            autowidth:true,
            multiselect: true,
            pager: "#teamLay_jqGridPager",
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
                $("#teamLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
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
        var title = "团队列表";
        that.layerIndex = layer.open({
            type : 1,//
            area : ['850px','700px'],
            title :title,
            closeBtn : 1,
            skin:"layui-layer-lan",
            content : $("#teamLayDiv"),
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
        $("#teamLay_jqGrid").jqGrid('setGridParam',{
            postData : {
                name : $("#teamName").val() ,
            },
            page : 1,
            datatype : 'json'
        }).trigger("reloadGrid");
    },

    //多选
    selectRows:function(){
        var selectArrays = getJqGridSelectedRows("teamLay_jqGrid");
        if(selectArrays == null){
            return ;
        }
        var teamIdList = new Array();
        var teamNameList = new Array();
        var teamTempList = [];
        for(var i=0; i<selectArrays.length; i++) {
            //行数据
            var rowData = $("#teamLay_jqGrid").jqGrid('getRowData',selectArrays[i]);
            teamIdList.push(rowData.id);
            teamNameList.push(rowData.name);
            var teamTemp = {
                name:rowData.name,
                id:rowData.id
            }
            teamTempList.push(teamTemp);
        }
        if($.isFunction(this.scallback)){
            this.scallback(teamIdList,teamNameList,teamTempList);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        $("#teamName").val("");
    }
}
teamLay.init();