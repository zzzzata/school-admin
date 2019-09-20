$(function () {
    $("#jqGrid").jqGrid({
        url: '../systeam/list',
        datatype: "json",
        colModel: [

            { label: 'id', name: 'id', key: true },
            { label: '团队名称', name: 'name', width: 80 },
            { label: '上级团队', name: 'parentName', width: 80 },
            { label: '团队负责人名称', name: 'teamLeaderName', width: 80 },
            { label: '团队状态', name: 'status', width: 80, formatter: function(value, options, row){
                    return value === 0 ? '<span class="label label-danger">停用</span>' : '<span class="label label-success">启用</span>';
                }},
            { label: '创建时间', name: 'createTime', width: 80 },
            { label: '创建人', name: 'creatorName', width: 80 },
            { label: '修改时间', name: 'updateTime', width: 80 },
            { label: '修改人', name: 'editorName', width: 80 },
            { label: '备注', name: 'remark', width: 80 }

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

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            name :"",
            teamLeaderId:"",
            teamLeaderName:""
        },
        showList: true,
        title: null,
        sysTeam: {
            name:"",
            status:1,
            parentId:null,
            parentName:null,
            teamLeaderName:"",
            remark:"",
            orderNum:""

        }
    },
    methods: {
        query: function () {
            vm.reload(null,1);
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.sysTeam = {
                name:"",
                status : 1 ,
                parentId:null,
                parentName:null,
                teamLeaderId:"",
                teamLeaderName:"",
                remark:"",
                orderNum:""
            };
            //获取团队
            vm.getTeam();
        },
        getTeam: function(){
            //加载团队树
            $.get("../systeam/list?status=1", function(r){
                ztree = $.fn.zTree.init($("#teamTree"), setting, r.data.list);
                var node = ztree.getNodeByParam("id", vm.sysTeam.parentId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.sysTeam.parentName = node.name;
                }
            })
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
            if(vm.title == "新增")
            {
                url = "../systeam/save";
            }
            else if(vm.title == "修改")
            {
                url = "../systeam/update";
            }else
            {
                url = "";
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.sysTeam),
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
                    url: "../systeam/delete",
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
                url: "/systeam/info/" + id,
                success: function(r){
                    if(r.code === 0){
                        vm.sysTeam = r.data;
                        vm.getTeam();
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function (e , p) {
            vm.showList = true;
            var page = p || $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData : vm.q,
                page:page
            }).trigger("reloadGrid");
        },
        resume:function(){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            confirm('确定要启用选中的记录？', function() {
                $.ajax({
                    type : "POST",
                    url : "/systeam/resume",
                    data : JSON.stringify(id),
                    success : function(r) {
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        pause:function () {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            confirm('确定要停用选中的记录？', function() {
                $.ajax({
                    type : "POST",
                    url : "/systeam/pause",
                    data : JSON.stringify(id),
                    success : function(r) {
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        teamTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择团队",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#teamLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.sysTeam.parentId = node[0].id;
                    vm.sysTeam.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        selectTeacher : function(){//编辑-班主任
            teacherLay.show(function(index,opt){
                vm.sysTeam.teamLeaderId = opt.userId;
                vm.sysTeam.teamLeaderName = opt.nickName;
            } , 2);
        },
        classTeacherLayerShow : function(){//选择班主任
            teacherLay.show(function(index,opt){
                vm.q.teamLeaderId = opt.userId;
                vm.q.classTeacherName = opt.nickName;
            } , 2);
        },

    }
});