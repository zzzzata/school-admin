
$(function () {
    //时间空间初始化
    $(".datetimepicker_sendTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 0//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
    });
    $(".datetimepicker_startTime").datetimepicker({
        format: 'yyyy-mm-dd 00:00:00',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
    });
    $(".datetimepicker_endTime").datetimepicker({
        format: 'yyyy-mm-dd 23:59:59',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        minView: 2//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
    });
    $(".datetimepicker_sendTime").val(new Date().format("yyyy-MM-dd hh:ii:ss"));
    // $(".datetimepicker_startTime").val(new Date().format("yyyy-MM-dd 00:00:00"));
    // $(".datetimepicker_endTime").val(new Date().format("yyyy-MM-dd 23:59:59"));
    $("#jqGrid").jqGrid({
        url: '../wechattemplatemsg/list',
        datatype: "local",
        colModel: [		
            { label: 'id', name: 'id', key: true,width: 100 },
            { label: '产品线id', name: 'productId', width: 50,hidden:true },
            { label: '发送的微信服务号', name: 'appid', width: 80,hidden:true },
            { label: '微信模板消息id', name: 'templateId', width: 80 ,hidden:true },
            { label: '创建用户', name: 'createUser', width: 80,hidden:true },
            { label: '产品线', name: 'productName', width: 50},
            { label: '微信服务号', name: 'title', width: 80 },
            { label: '微信模板名称', name: 'templateName', width: 80 },
            { label: '发送内容', name: 'sendContent', width: 80 , formatter: function (value, options, row) {
                return '<button style="width:100px;height:25px"  onclick="viewContent(\'' + row.sendContent  + '\' )">显示内容</button> '
            }},
            { label: '发送状态', name: 'sendStatus', width: 80, formatter: function (value, options, row) {
                if (value == 1){
                    if( row.sendTimeValue != null && new Date().getTime() > new Date(row.sendTimeValue).getTime()) {
                        return "已发送";
                    }else {
                      return "待发送"
                    }
                }else if (value == 2){
                    return "发送失败";
                }else {
                    return "暂不发送";
                }
            }},
            { label: '发送量', name: 'sendCount', width: 40 ,formatter: function (value, options, row) {
                if(row.sendStatus == 1 && row.sendTimeValue != null && new Date().getTime() > new Date(row.sendTimeValue).getTime()){
                    return value;
                }else {
                    return "--";
                }
            }},
            { label: '发送时间', name: 'sendTimeValue', width: 120 },
            { label: '创建用户', name: 'createUserName', width: 80 },
            { label: '创建时间', name: 'createTime', width: 120 },
            { label: '修改用户', name: 'updateUserName', width: 80 },
            { label: '修改时间', name: 'updateTime', width: 120 },
            { label: '操作', name: 'operation', width: 80,formatter: function (value, options, row) {
                if(row.sendStatus == 1 && row.sendTimeValue != null && new Date().getTime() > new Date(row.sendTimeValue).getTime()){
                    return '<button style="width:50px;height:25px;background: dodgerblue; color: white"   onclick="viewDetail(' + row.id  + ' )">查看</button> ' +
                        '<button style="width:50px;height:25px" disabled="disabled" onclick="delete(' + row.id + ')">删除</button>'
                }else
                    return '<button style="width:50px;height:25px" disabled="disabled" onclick="viewDetail(' + row.id  + ' )">查看</button> ' +
                        '<button style="width:50px;height:25px;background: red; color: white"  onclick="deleteMsg(' + row.id + ')">删除</button>'
            } },

        ],
		viewrecords: true,
        height: 385,
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

function viewDetail(id) {
    wechatMsgDetailLay.show(function (index,opt) {

    },id)
};
function deleteMsg(id) {
    confirm('确定要删除选中的记录？', function(){
        $.ajax({
            type: "POST",
            url: "../wechattemplatemsg/deleteMsg",
            data: JSON.stringify(id),
            success: function(r){
                if(r.code == 0){
                    alert('操作成功', function(index){
                        $("#jqGrid").trigger("reloadGrid");
                    });
                }else{
                    alert(r.msg);
                }
            }
        });
    });
};
function viewContent(sendContent) {
    alert(sendContent);
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		wechatTemplateMsg: {
            productId:"",
            productName:"",
            classplanId:"",
            classplanName:"",
            appid:"",
            wechatTitle:"",
            templateId:"",
            templateName:"",
            templateContent:"",
            templateType: 1,
            keyWord1:"",
            keyWord2:"",
            keyWord3:"",
            keyWord4:"",
            mobileList:[],
            sendTimeType:-1,
            sendTimeValue:""
        },
        q:{
		    productName:"",
            productId:"",
            startTime:"",
            endTime:"",
            sendStatus:-1
        }
	},
	methods: {
		query: function () {
			vm.reload(null,1);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
            $("#classplanLayerShow-div").css('display','none');
            $("#classLayerShow-div").css('display','none');
            $("#mobileShow-div").css('display','none');
            $("#sendTime-div").css('display','none');
            $("#classplanInput").val("");
            $("#classInput").val("");
            $("#mobileInput").val("");
            $(".datetimepicker_sendTime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
			vm.wechatTemplateMsg = {
                productId:"",
                productName:"",
                classplanId:"",
                classplanName:"",
                appid:"",
                wechatTitle:"",
                templateId:"",
                templateName:"",
                templateContent:"",
                templateType: 1,
                keyWord1:"",
                keyWord2:"",
                keyWord3:"",
                keyWord4:"",
                mobileList:[],
                sendTimeType:-1,
                sendTimeValue:""
            };
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            //拼装发送内容
            var sendContent = vm.wechatTemplateMsg.templateContent;
            var last = sendContent.lastIndexOf("{{");
            var sendContentVaule = "";
            sendContentVaule = vm.concatStr(sendContent,last,sendContentVaule,1);
            vm.wechatTemplateMsg.sendContent = sendContentVaule;
            vm.wechatTemplateMsg.sendTimeValue=$(".datetimepicker_sendTime").val();
            if ($.isNull(vm.wechatTemplateMsg.sendContent)){
                alert("发送内容不能为空");
                return;
            }
            if ($.isNull(vm.wechatTemplateMsg.sendObjValue)){
                alert("发送对象不能为空")
                return;
            }
            if ($.isNull(vm.wechatTemplateMsg.sendStatus)){
                alert("请点击选择发送时间")
                return;
            }
		    if(vm.title == "新增")
		    {
		       url = "../wechattemplatemsg/save";
		    }
		    else if(vm.title == "修改")
		    {
		       url = "../wechattemplatemsg/update";
		    }else
		    {
		       url = "";
		    }
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.wechatTemplateMsg),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../wechattemplatemsg/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
            $.ajax({
				type: "POST",
			    url: "/wechattemplatemsg/info/" + id,
			    success: function(r){
			    	if(r.code === 0){
						 vm.wechatTemplateMsg = r.data;
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (e , p) {
			vm.showList = true;
			vm.q.startTime =  $(".datetimepicker_startTime").val();
			vm.q.endTime =  $(".datetimepicker_endTime").val();
			var page = p || $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                datatype:"json",
                postData:vm.q
            }).trigger("reloadGrid");
		},
        productLayQueryShow : function(){//产品线
            productLay.show(function(index, opt) {
                vm.q.productId = opt.productId;
                vm.q.productName = opt.productName;
            });
        },
        productLayShow : function(){//产品线
            productLay.show(function(index, opt) {
                vm.wechatTemplateMsg.productId = opt.productId;
                vm.wechatTemplateMsg.productName = opt.productName;
            });
        },
        //微信公众号浮层
        wechatAccountLayShow: function () {
            var productId = vm.wechatTemplateMsg.productId;
            if(productId== null || productId == ""){
                alert("请先选择产品线");
                return;
            }
            wechatAccountLay.show(function(index,opt){
                vm.wechatTemplateMsg.appid = opt.appid;
                vm.wechatTemplateMsg.wechatTitle = opt.title;
            },productId)
        },
        //微信消息模板浮层
        wechatTemplateLayShow:function(){
            var wechatAppId = vm.wechatTemplateMsg.appid;
            if(wechatAppId== null || wechatAppId == ""){
                alert("请先选择微信公众号");
                return;
            }
            wechatTemplateLay.show(function(index,opt){
                vm.wechatTemplateMsg.templateId = opt.templateId;
                vm.wechatTemplateMsg.templateName = opt.title;
                vm.wechatTemplateMsg.templateContent = opt.content;
            },wechatAppId)
        },
        clearQuery:function () {
            vm.q.productId="";
            vm.q.productName="";
            vm.q.startTime="";
            vm.q.endTime="";
            vm.q.sendStatus=-1;
            $(".datetimepicker_startTime").val("");
            $(".datetimepicker_endTime").val("");
        },
        previewContent: function () {
            var sendContent = vm.wechatTemplateMsg.templateContent;
            var last = sendContent.lastIndexOf("{{");
            var sendContentVaule = "";
            console.log("模板内容:"+sendContent);
            sendContentVaule = vm.concatStr(sendContent,last,sendContentVaule,1);
            vm.wechatTemplateMsg.sendContent = sendContentVaule;
            alert(sendContentVaule)
        },
        concatStr: function (str,last,finnalStr,i) {
            var start = str.indexOf("{{");
            var end = str.indexOf("}}");
           console.log(last+ "==="+start+"=="+end)
            if (start <= last ){
               if (i == 1){
                   finnalStr += str.substring(0,start)+vm.wechatTemplateMsg.keyWord1;
               }else if (i == 2){
                   finnalStr += str.substring(0,start)+vm.wechatTemplateMsg.keyWord2;
               }else if(i == 3) {
                   finnalStr += str.substring(0,start)+vm.wechatTemplateMsg.keyWord3;
               }else if (i == 4){
                   finnalStr += str.substring(0,start)+vm.wechatTemplateMsg.keyWord4;
               }
            str = str.substring(end+2,str.length-1);
            console.log("第"+i+"次截取后字符串"+str);
            last = str.lastIndexOf("{{");
                if (last > 0) {
                    finnalStr = vm.concatStr(str, last,finnalStr,i+1);
                }
            }
            console.log("拼接后消息内容返回:"+finnalStr);
            return finnalStr;
        },
        sendObjType : function (type) {
            $("#classplanInput").val("");
            $("#classInput").val("");
            $("#mobileInput").val("");
            $("#classplanLayerShow-div").css('display','none');
            $("#classLayerShow-div").css('display','none');
            $("#mobileShow-div").css('display','none');
            if ("classplanLayShow" == type){
                $("#classplanLayerShow-div").css('display','block');
            }else if("classLayShow" == type) {
                $("#classLayerShow-div").css('display','block');
            }else if("mobile" == type) {
                $("#mobileShow-div").css('display','block');
            }
        },

        classplanLayerShow : function(){//排课浮层
            classplanLay.show(function(index,opt){
                vm.wechatTemplateMsg.sendObjType=1;
                vm.wechatTemplateMsg.sendObjValue = opt.classplanId;
                $("#classplanInput").val(opt.classplanName);
            });
        },

        classLayerShow: function () {//班级浮层
            classLay.show(function (index, opt) {
                vm.wechatTemplateMsg.sendObjType=2;
                vm.wechatTemplateMsg.sendObjValue = opt.classId;
                $("#classInput").val(opt.className);
            });
        },
        checkMobile: function () {
            var mobielStr = $("#mobileInput").val();
            //过滤换行
            mobielStr = mobielStr.replace(/\ +/g,"");
            //过滤回车
            mobielStr = mobielStr.replace(/[\r\n]/g,"");
            //过滤中文逗号
            mobielStr = mobielStr.replace("，",",")
            if (mobielStr != null){
                var mobileArr = mobielStr.split(",");
                vm.wechatTemplateMsg.mobileList = mobileArr;
                console.log(mobileArr);
                for (var i = 0; i < mobileArr.length; i++) {
                    console.log(i+"suoyin")
                var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
                    if (!myreg.test(mobileArr[i])){
                        alert("非法的手机号码:"+ mobileArr[i]);
                        return;
                    }
                }
            }
            vm.wechatTemplateMsg.sendObjType=3;
            vm.wechatTemplateMsg.sendObjValue = mobielStr;
        },
        deleteMobileItem:function (index) {
            vm.wechatTemplateMsg.mobileList.splice(index,1)
            vm.wechatTemplateMsg.sendObjValue = vm.wechatTemplateMsg.mobileList.join(",");
            $("#mobileInput").val(vm.wechatTemplateMsg.sendObjValue);
        },
        sendTimeType: function (type) {
            $("#sendTime-div").css('display','none');
            if ("laterSendShow" == type){
                $("#sendTime-div").css('display','block');
                vm.wechatTemplateMsg.sendStatus = 1;
                vm.wechatTemplateMsg.sendTimeType = 2;
                vm.wechatTemplateMsg.sendTimeValue=$("#sendTime").val();
            }else if ("noSendShow" ==type){
                //发送时间类型 -1暂不发送1立即发送2定时发送
                vm.wechatTemplateMsg.sendTimeType = -1;
                vm.wechatTemplateMsg.sendTimeValue = "";
                vm.wechatTemplateMsg.sendStatus = 0;
            }else if("nowSendShow" == type){
                vm.wechatTemplateMsg.sendTimeType = 1;
                vm.wechatTemplateMsg.sendTimeValue="";
                vm.wechatTemplateMsg.sendStatus = 1;
            }
        }
    }
});