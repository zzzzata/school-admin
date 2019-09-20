/**
 * 班级js
 */
var roleLay = {
	init : function(){
		$("#roleName").val("");
		var that = this;
		$("#roleLay_jqGrid").jqGrid({
			url: "../layerdata/roleList",
			datatype: "local",
			colModel: [
			           { label: "角色id", name: "roleId" , key: true , width: 150	  },
			           
			           { label: "角色名称", name: "roleName" , width: 1000	},
		           ],
		   viewrecords: true,
		   height: 450,
		   rowNum: 30,
		   rowList : [50,100,150,200],
		   rownumbers: true, 
		   rownumWidth: 35, 
		   autowidth:true,
		   multiselect: true,
		   pager: "#roleLay_jqGridPager",
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
			   $("#roleLay_jqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
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
			var title = "系统角色列表";
			that.layerIndex = layer.open({
				type : 1,//
				area : ['850px','700px'],
				title :title,
				closeBtn : 1,
				skin:"layui-layer-lan",
				content : $("#roleLayDiv"),
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
		$("#roleLay_jqGrid").jqGrid('setGridParam',{
			postData : {
                roleName : $("#roleName").val() ,
            },
			page : 1,
			datatype : 'json'
		}).trigger("reloadGrid");
	},
    //多选
    selectRows:function(){
        var selectArrays = getJqGridSelectedRows("roleLay_jqGrid");
        if(selectArrays == null){
            return ;
        }
        var roleIdList = new Array();
        var roleNameList = new Array();
        var roleTempList = [];
        for(var i=0; i<selectArrays.length; i++) {
            //行数据
            var rowData = $("#roleLay_jqGrid").jqGrid('getRowData',selectArrays[i]);
            roleIdList.push(rowData.roleId);
            roleNameList.push(rowData.roleName);
            var roleTemp = {
                name:rowData.roleName,
                id:rowData.roleId
            }
            roleTempList.push(roleTemp);
        }
        //alert(roleIdList+"===");
        //alert(roleTempList.toString()+"==")
        if($.isFunction(this.scallback)){
            this.scallback(roleIdList,roleNameList,roleTempList);
        }
        //关闭浮层
        layer.close(this.layerIndex);
        $("#roleName").val("");
    }
}
roleLay.init();
