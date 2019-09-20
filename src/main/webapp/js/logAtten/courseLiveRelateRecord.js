$(function () {
    $("#jqGrid").jqGrid({
        url: '../courseLiveRelateRecord/list',
        datatype: "json",
        colModel: [
            { label: 'liveId', name: 'liveId', hidden: true,key: true},
            { label: 'recordIds', name: 'recordIds', hidden: true },
            { label: 'courseId', name: 'courseId', hidden: true },
            { label: '所属课程', name: 'courseName', width: 80 },
            { label: '课次名称', name: 'liveName', width: 80 },
            { label: '绑定录播课名称', name: 'recordNameGroup', width: 80 },
            { label: '状态', name: 'state', width: 80, formatter: function(value, options, row){
                    return row.state === 1 ? '启用' : '停用';
            }},
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
        relate: {
            courseId: "",
            liveName: "",
            courseName: "",
            recordName: "",
            state:""
        },
        editorRelate: {
            courseId: "",
            liveId: "",
            liveName: "",
            recordName: "",
            recordId:[],
            state:1,
            dr:0
        },
        course:{
            courseName:""
        },
        live:{
            courseName:"",
            liveName:""
        },
        record:{
            recordName:"",
            recordId:""
        },
        disabled:false
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        addRelate: function() {
            this.$data.disabled = false;
            this.$data.editorRelate = {
                courseId: "",
                liveId: "",
                liveName: "",
                recordName: "",
                recordId:[],
                state:1,
                dr:0
            },
            vm.showList = false;
            vm.title = "新增";
        },
        //弹出课程列表弹窗
        selCourse: function () {
            //初始化表格
            vm.initJqGridCourse();
            //显示浮层
            vm.showCourseLayer();
            //加载课程数据
            vm.reloadJqGridCourse();
        },
        //初始化课程列表
        initJqGridCourse: function () {//加载课程列表
            $("#jqGridCourse").jqGrid({
                url: '../mall/courses/listGrid',
                datatype: "local",
                colModel: [
                    {label: '课程ID', name: 'courseId', width: 60, key: true},
                    {label: '课程编号', name: 'courseNo', width: 100},
                    {label: '课程名称', name: 'courseName', width: 100},
                    {label: '课程类别', name: 'courseLb', width: 100},
                    {label: '课程类型', name: 'courseType', width: 100},
                    {label: '考试方式', name: 'examType', width: 100},
                    {label: '学分', name: 'courseCredit', width: 100},
                ],
                viewrecords: true,
                height: 300,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: false,
                pager: "#jqGridPagerCourse",
                jsonReader: {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
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
                    $("#jqGridCourse").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        },
        //显示课程列表
        showCourseLayer: function () {
            var that = this;
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '课程列表',
                area: ['891px', '633px'],
                shadeClose: false,
                content: jQuery("#courselayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    //获取选中行ID
                    var selectDetail = getJqGridSelectedRow("jqGridCourse");
                    if (selectDetail == null) {
                        return;
                    }
                    //行数据
                    var rowData = $("#jqGridCourse").jqGrid('getRowData', selectDetail);
					//课程ID
                    that.$data.relate.courseId = rowData.courseId;
					//课程名称
                    that.$data.relate.courseName = rowData.courseName;
                    layer.close(index);
                }
            });
        },
        //刷新课程列表
        reloadJqGridCourse: function () {
            var that = this;
            $("#jqGridCourse").jqGrid('setGridParam', {
                postData : {
                    courseName:that.$data.course.courseName
                },
                page: 1,
                datatype: 'json',
            }).trigger("reloadGrid");
        },

        //弹出直播列表弹窗
        selLive: function () {
            //初始化表格
            vm.initJqGridLive();
            //显示浮层
            vm.showLiveLayer();
            //加载课程数据
            vm.reloadJqGridLive();
        },
        //初始化直播列表
        initJqGridLive: function () {
            $("#jqGridLive").jqGrid({
                url: '../courseclassplanlives/list',
                datatype: "local",
                colModel: [
                    { label: '主键PK', name: 'classplanLiveId', hidden : true, key: true },
                    { label: '排课计划PK', name: 'classplanId', hidden : true },
                    { label: '课程ID', name: 'courseId', hidden : true, width: 80 },

                    { label: '直播课次名称', name: 'classplanLiveName', width: 150 },
                    { label: '排课计划名称', name: 'classplanName', width: 150 },
                    { label: '课程名称', name: 'courseName', width: 150 },
                    { label: '开始时间', name: 'startTime', width: 150 },
                    { label: '结束时间', name: 'endTime', width: 150 },
                    { label: '授课老师', name: 'teacherName', width: 80 },
                    { label: '考勤', name: 'attendance', width: 80 ,formatter: function(value, options1, row){
                            var text = '';
                            if(value == -1)text='<span class="label label-danger">否</span>';
                            else text='<span class="label label-danger">是 </span>';
                            return text;
                        }},
                    { label: '直播间', name: 'liveRoomName', width: 80 },
                ],
                viewrecords: true,
                height: 400,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: false,
                pager: "#jqGridPagerLive",
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
                    $("#jqGridLive").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        },
        //显示直播列表
        showLiveLayer: function () {
            var that = this;
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '直播课次列表',
                area: ['891px', '633px'],
                shadeClose: false,
                content: jQuery("#livelayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    //获取选中行ID
                    var selectDetail = getJqGridSelectedRow("jqGridLive");
                    if (selectDetail == null) {
                        return;
                    }
                    //行数据
                    var rowData = $("#jqGridLive").jqGrid('getRowData', selectDetail);
                    //直播id
                    that.$data.editorRelate.liveId = rowData.classplanLiveId;
                    //课程id
                    that.$data.editorRelate.courseId = rowData.courseId;
                    //课程名称
                    that.$data.editorRelate.liveName = rowData.classplanLiveName;
                    layer.close(index);
                }
            });
        },
        //刷新直播列表
        reloadJqGridLive: function () {
            var that = this;
            $("#jqGridLive").jqGrid('setGridParam', {
                postData : {
                    courseName:that.$data.live.courseName,
                    classplanLiveName:that.$data.live.liveName
                },
                page: 1,
                datatype: 'json'
            }).trigger("reloadGrid");
        },

        //弹出录播列表弹窗
        selRecord: function () {
            var courseId = this.$data.editorRelate.courseId;
            if (!courseId) {
                layer.msg("请先选择直播课次")
                return;
            }
            $('#jqGridRecord').jqGrid('clearGridData');
            //重置url
            $("#jqGridRecord").jqGrid('setGridParam', {
                url:'../mall/courses/recordList/' + courseId,
            })
            //初始化表格
            vm.initJqGridRecord(courseId);
            //显示浮层
            vm.showRecordLayer();
            //加载课程数据
            vm.reloadJqGridRecord(courseId);
        },
        //初始化录播列表
        initJqGridRecord: function (courseId) {//加载课程列表
            var that = this;
            $("#jqGridRecord").jqGrid({
                url: '../mall/courses/recordList/' + courseId,
                datatype: "local",
                colModel: [
                    { label: 'recordId', name: 'recordId', key: true,hidden:true },
                    { label: '录播课ID', name: 'vid', width: 200, hidden:true },
                    { label: '录播课名称', name: 'name', width: 200 },
                    { label: '时长', name: 'duration', width: 200 },
                    { label: '课程名称', name: 'courseName', width: 200 },
                    { label: '视频名', name: 'polyvName', width: 200 },
                ],
                viewrecords: true,
                height: 400,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                pager: "#jqGridPagerRecord",
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
                    $("#jqGridRecord").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            }).trigger('reloadGrid');
        },
        //显示录播列表
        showRecordLayer: function () {
            var that = this;
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '课程列表',
                area: ['891px', '633px'],
                shadeClose: false,
                content: jQuery("#recordlayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    //获取选中行ID
                    var selectDetail = getJqGridSelectedRows("jqGridRecord");
                    if (selectDetail == null) {
                        return;
                    }
                    that.$data.editorRelate.recordId = []
                    that.$data.editorRelate.recordName = []
                    //行数据
                    $(selectDetail).each(function (index, id){
                        //由id获得对应数据行
                        var row = $("#jqGridRecord").jqGrid('getRowData', id);

                        //课程ID
                        that.$data.editorRelate.recordId.push(row.recordId);
                        //课程名称
                        that.$data.editorRelate.recordName.push(row.name);
                    })
                    that.$forceUpdate();
                    layer.close(index);
                }
            });
        },
        //刷新录播列表
        reloadJqGridRecord: function (courseId) {
            $("#jqGridRecord").jqGrid('setGridParam', {
                page: 1,
                datatype: 'json'
            }).trigger("reloadGrid");
        },

        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            this.$data.disabled = true;
            vm.showList = false;
            vm.title = "编辑";
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            vm.getInfo(rowData);
        },
        updateStatus: function (dr,state) {
            var liveIdList = getSelectedRows("jqGridRecord");
            if (liveIdList == null) {
                return;
            }
            var params = {
                "liveIds":liveIdList.toString(),
                "state":state,
                "dr":dr
            };
            $.ajax({
                url: "../courseLiveRelateRecord/saveOrUpdateState",
                data:params,
                type: "post",
                contentType :"application/x-www-form-urlencoded",
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.editorRelate = {}
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            var that = this;
            var params = {
                "id":vm.editorRelate._id,
                "courseId":vm.editorRelate.courseId,
                "liveId":vm.editorRelate.liveId,
                "recordIds":vm.editorRelate.recordId.toString(),
                "state":vm.editorRelate.state,
                "dr":vm.editorRelate.dr
            };
            $.ajax({
                url: "../courseLiveRelateRecord/saveOrUpdate",
                data:params,
                type: "post",
                contentType :"application/x-www-form-urlencoded",
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.editorRelate = {}
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getInfo: function (rowData) {
            this.$data.editorRelate = rowData;
            this.$data.editorRelate.recordName = rowData.recordNameGroup.split(",")
            this.$data.editorRelate.recordId = rowData.recordIds.split(",")
            if (this.$data.editorRelate.state == '启用') {
                this.$data.editorRelate.state = 1
            } else {
                this.$data.editorRelate.state = 0
            }
        },
        reload: function (event, p) {
            vm.showList = true;
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                datatype: "json",
                postData: {
                    "courseId": vm.relate.courseId,
                    "liveName": vm.relate.liveName,
                    "recordName":  vm.relate.recordName,
                    "state":vm.relate.state
                },
                page: page
            }).trigger("reloadGrid");
        },
        clearQuery : function(){//清空查询条件
            var vm = this;
            vm.relate.courseId = "";
            vm.relate.courseName = "";
        },
        importData: function(){
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '导入Excel',
                area: ['300px', '200px'],
                shadeClose: false,
                content: jQuery("#uploadExcel"),
                btn: ['确定','取消'],
                btn1: function (index) {
                    $.ajaxFileUpload({
                        url:'../courseLiveRelateRecord/importData',
                        secureuri:true,
                        fileElementId:'file_data',
                        dataType:'json',
                        success:function(data){
                            if(data.code == 0 && data.msg != null) {
                                alert(data.msg, function(index){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                                layer.close(index);
                            }else if(data.code == 0){
                                alert(data.msg, function(index){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                                layer.close(index);
                            }else if (data.code == 1) {
                                alert(data.msg);
                            }
                        }
                    });
                }
            });
        },
    }
});