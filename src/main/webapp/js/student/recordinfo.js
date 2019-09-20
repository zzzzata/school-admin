$(function () {
    $(".createMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView: 'year',
        maxView: 'decade',
        language: 'zh-CN',
    });
    $(".expectTime").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        todayHighlight: 1,//如果为true, 高亮当前日期。
        startView: 2,//日期时间选择器打开之后首先显示的视图。
        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2,//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
        showMeridian: 1
    });
    $(".approvalDate").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        todayHighlight: 1,//如果为true, 高亮当前日期。
        startView: 2,//日期时间选择器打开之后首先显示的视图。
        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2,//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
        showMeridian: 1
    });
    $(".Date").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 0,//一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn: 1,//如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
        todayHighlight: 1,//如果为true, 高亮当前日期。
        startView: 2,//日期时间选择器打开之后首先显示的视图。
        forceParse: 0,//当选择器关闭的时候，是否强制解析输入框中的值。
        minView: 2,//日期时间选择器所能够提供的最精确的时间选择视图。0:小时，1:分钟，2:秒
        showMeridian: 1
    });
    $("#jqGrid").jqGrid({
        url: '../record/recordInfo/list',
        datatype: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        colModel: [
            {label: '订单编号', name: 'orderNo', width: 80},
            {label: '学员ID', name: 'userId', width: 80},
            {label: '学员姓名', name: 'name', width: 80},
            {label: '商品类型', name: 'classType', width: 80},
            {label: '报读班型', name: 'courseName', width: 80},
            {label: '报名单号', name: 'ncCode', width: 80},
            {label: '报名日期', name: 'regDate', width: 80},
            {label: '审批日期', name: 'approvalDate', width: 80},
            {label: '报读专业', name: 'zy', width: 80},
            {label: '班级', name: 'className', width: 80},
            {
                label: '订单状态',
                name: 'dr',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == 0) {
                        return '<span class="label label-success">正常</span>';
                    } else if (value == 1) {
                        return '<span class="label label-success">删除</span>';
                    }else if (value == 2) {
                        return '<span class="label label-success">关闭</span>';
                    }else {
                        return '<span class="label label-success">关闭</span>';
                    }
                }
            },
            {label: '大区', name: 'regionDept', width: 80},
            {label: '省份', name: 'areaName', width: 80},
            {label: '校区', name: 'deptName', width: 80},
            {label: '校区电话', name: 'campusPhone', width: 80},
            {label: '班主任', name: 'teacherName', width: 80},
            {
                label: '性别', name: 'sex', align: "center", width: 80, formatter: function (value, options, row) {
                    if (value == 0) {
                        return '<span class="label label-success">女</span>';
                    } else if (value == 1) {
                        return '<span class="label label-success">男</span>';
                    } else if (value == 2) {
                        return '<span class="label label-success">保密</span>';
                    } else {
                        return '<span class="label label-success">保密</span>';
                    }
                }
            },
            {
                label: '身份证', name: 'idCard', width: 80, formatter: function (value, options, row) {
                    if (value == null) {
                        return '--';
                    } else {
                        return value;
                    }
                }
            },
            {
                label: '年龄', name: 'age', width: 80, formatter: function (value, options, row) {
                    if (value == null) {
                        return '--';
                    } else {
                        return value;
                    }
                }
            },
            {
                label: '学历', name: 'record', width: 80, formatter: function (value, options, row) {
                    if (value == null) {
                        return '--';
                    } else {
                        return value;
                    }
                }
            },
            {label: 'QQ', name: 'qq', width: 80},
            {label: '手机号码', name: 'mobile', width: 80},
            {label: '蓝鲸账号', name: 'mobile', width: 80},
            {label: '跟进状态', name: 'followStatusStr', width: 80},
            {
                label: '最新回访状态',
                name: 'rstatus',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == 0) {
                        return '<span class="label label-success">未回访</span>';
                    } else if (value == 1) {
                        return '<span class="label label-success">已回访</span>';
                    }
                    return '<span>-</span>';
                }
            },
            {
                label: '是否结婚',
                name: 'marriageStatus',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == 1) {
                        return '<span class="label label-success">是</span>';
                    } else if (value == 0) {
                        return '<span class="label label-success">否</span>';
                    }
                }
            },
            {
                label: '是否生育',
                name: 'fertilityStatus',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == 1) {
                        return '<span class="label label-success">是</span>';
                    } else if (value == 0) {
                        return '<span class="label label-success">否</span>';
                    }
                }
            },
            {label: '小孩数量', name: 'chirdCount', width: 80},
            {label: '已有证书', name: 'accountingCertificates', width: 80},
            {label: '现工作岗位', name: 'postName', width: 80},
            {label: '学员目标', name: 'studentTarget', width: 80},
            {label: '正备考证书', name: 'certificate', width: 80},
            {label: '教材寄送地址', name: 'sendAddress', width: 80,formatter: function (value, options, row) {
                    if (value == null) {
                        return '<span>-</span>';
                    }else {
                        return value;
                    }}},
            {label: '产品线', name: 'productName', width: 80},
            {label: '紧急联系人', name: 'emergencyContact', width: 80},
            {label: '紧急联系人电话', name: 'emergencyPhone', width: 80},
            {label: '初次课程顾问', name: 'consultant', width: 80},
            {
                label: '是否转介绍',
                name: 'isRecommend',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == "Y") {
                        return '<span class="label label-success">是</span>';
                    } else if (value == "N") {
                        return '<span class="label label-success">否</span>';
                    }else {
                        return '<span class="label label-success">否</span>';
                    }
                }
            },
            {label: '转介绍老师', name: 'recommendTeacher', width: 80},
            {label: '培训课程顾问', name: 'trainConsultant', width: 80},
            {label: '交齐款日期', name: 'cashDate', width: 80},
            {label: '报读大学', name: 'bdyx', width: 80},
            {label: '自考学习状态', name: 'zkStudyStatus', width: 80,
                formatter: function (value, options, row) {
                    if (value == 1) {
                        return '<span class="label label-success">在读</span>';
                    } else if (value == 2) {
                        return '<span class="label label-success">休学</span>';
                    }else if (value == 3) {
                        return '<span class="label label-success">毕业</span>';
                    }else if (value == 4) {
                        return '<span class="label label-success">暂停</span>';
                    }else if (value == 5) {
                        return '<span class="label label-success">坏账</span>';
                    }else {
                        return '';
                    }
                }},

            {label: '自考档案状态', name: 'zkRecordStatus', width: 80,
                formatter: function (value, options, row) {
                    if (value == 1) {
                        return '<span class="label label-success">正常</span>';
                    } else if (value == 2) {
                        return '<span class="label label-success">作废</span>';
                    }else {
                        return '';
                    }
                }},

            {label: '专科毕业院校', name: 'zkCollege', width: 80},
            {label: '专科毕业时间', name: 'zkGraduateDate', width: 80},
            {label: '专科毕业专业', name: 'zkMajor', width: 80},
            {label: '本科报读专业', name: 'bkMajor', width: 80},
            {label: '本科报读院校', name: 'bkCollege', width: 80},
            {label: '政治面貌', name: 'politicsStatus', width: 80},
            {label: '户籍', name: 'hj', width: 80},
            {label: '最后修改人', name: 'modifyPerson', width: 80},
            {label: '最后修改时间', name: 'modifyTime', width: 80},
            {
                label: '是否购买保险',
                name: 'isInsurant',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == "Y") {
                        return '<span class="label label-success">是</span>';
                    } else if (value == "N") {
                        return '<span class="label label-success">否</span>';
                    }else {
                        return '<span class="label label-success">否</span>';
                    }
                }
            },
            {
                label: '签署状态',
                name: 'cstatus',
                align: "center",
                width: 80,
                formatter: function (value, options, row) {
                    if (value == 0) {
                        return '<span class="label label-success">未签署</span>';
                    } else if (value == 1) {
                        return '<span class="label label-success">已签署</span>';
                    }else if (value == 2) {
                        return '<span class="label label-success">已驳回</span>';
                    }else {
                        return '<span class="label label-success">无需签约</span>';
                    }
                }
            },
            {label: '学员信息主键', name: 'recordId', hidden: true},
            {label: '报读主键', name: 'recordSignId', key: true, hidden: true},
            {label: 'ncStudentId', name: 'ncStudentId', hidden: true},
            {label: '跟进状态', name: 'followStatus', width: 80, hidden: true},
            {label: '跟进人', name: 'followPersonName', width: 80, hidden: true},
            {label: '跟进内容', name: 'followContent', width: 80, hidden: true},
            {label: '跟进时间', name: 'followTime', width: 80, hidden: true}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 100, 500, 1000],
        rownumbers: true,
        rownumWidth: 35,
        shrinkToFit: false,
        autoScroll: true,
        autowidth: true,
        multiselect: false,
        pager: "#jqGridPager",
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
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        q: {
            userId: '',
            name: '',
            mobile: '',
            teacherId: '',
            teacherName: '',
            areaName: '',
            areaId: '',
            deptIdList: "",
            deptNameList: "",
            classId: "",
            className: "",
            classplanId: null,//排课
            classplanName: null,
            createMonth: '',
            followStatus: '',
            returnType: '',
            expectTime: '',
            approvalDate: '',
            ncCode: '',
            dr: '',
            zy: '',
            courseName: '',
            startRegDate: '',
            endRegDate: '',
            consultant: '',
            productName: '',
            productId: '',
            cstatus: '',
            isInsurant: '',
            startReceiveDate: '',
            endReceiveDate: '',
            classType: ''

        },
        info: {
            ncStudentId: '',
            userId: '',
            recordId: '',
            name: '',
            sex: '',
            idCard: '',
            age: '',
            qq: '',
            marriageStatus: '',
            fertilityStatus: '',
            postName: '',
            accountingCertificates: '',
            studyTimeOfDay: '',
            followStatus: '0',
            followContent: '',
            followPersonName: '',
            chirdCount: '',
            studentTarget: '',
            certificate: '',
            sendAddress:'',
            productId: ''
        },
        learningSituation: {
            referenceDate: "",
            createDate: "",
            attendPer: "",
            jobPer: "",
            teacherAssess: "",
            schoolAssist: ""
        },
        returnVisit: {
            id: '',
            createPersonName: '',
            expectTime: '',
            returnTime: '',
            returnStatus: '',
            returnContent: '',
            mobile: ''
        }
    },
    methods: {
        query: function () {
            vm.reload(null, 1);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            var rowData = $("#jqGrid").jqGrid('getRowData', id);
            vm.getInfo(rowData.recordId);


        },
        getInfo: function (id) {
            $.ajax({
                type: "POST",
                url: "../record/recordInfo/info/" + id,
                success: function (r) {
                    if (r.code === 0) {
                        vm.info = r.recordInfo;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            console.log(vm.info.recordId);
            if (vm.info.recordId == null || vm.info.recordId == "") {
                url = "../record/recordInfo/save";
            } else if (vm.info.recordId != "") {
                url = "../record/recordInfo/update";
            } else {
                url = "";
            }
            if (vm.info.sendAddress != null &&(vm.info.sendAddress).length > 50) {
                alert("教材寄送地址在1-50字，请重新输入！")
                return;
            }
            // var param = {};
            // param.recordId = vm.info.recordId;
            // param.userId=vm.info.userId;
            // param.ncStudentId=vm.info.ncStudentId;
            // param.name = vm.info.name;
            // param.sex = vm.info.sex;
            // param.idCard = vm.info.idCard;
            // param.age = vm.info.age;
            // param.qq= vm.info.qq;
            // param.marriageStatus = vm.info.marriageStatus;
            // param.fertilityStatus = vm.info.fertilityStatus;
            // param.postName = vm.info.postName;
            // param.accountingCertificates = vm.info.accountingCertificates;
            // param.studyTimeOfDay=vm.info.studyTimeOfDay;

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.info),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },

        exportData: function () {
            hq.ui.exportData(null);
        },
        importData: function () {    //导入数据

            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: '导入Excel',
                area: ['300px', '200px'],
                shadeClose: false,
                content: jQuery("#uploadExcel"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    $.ajaxFileUpload({
                        url: '../record/recordInfo/getExcelRecordInfoImportData',
                        secureuri: true,
                        fileElementId: 'file_data',
                        dataType: 'json',
                        success: function (data) {
                            if (data.code == 0 && data.msg != null) {
                                alert("文件上传失败！！！" + "<br/>" + data.msg, function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                                layer.close(index);
                            } else if (data.code == 0 && data.msg == null) {
                                alert("文件上传成功！！！", function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                                layer.close(index);
                            } else if (data.code == 1) {
                                alert(data.msg);
                            }
                        }
                    });
                }
            });

        },
        classTeacherLayerShow: function () {//班主任
            teacherLay.show(function (index, opt) {
                vm.q.teacherId = opt.userId;
                vm.q.teacherName = opt.nickName;
                $('#query-classTeacherName').val(opt.nickName);
            }, 2);
        },

        exportTemplate: function (event) {  //下载模板
            /*var urlstr = "../record/recordInfo/exportExcelRecordInfoTemplate";
            window.location.href = urlstr;
            */
            var urlstr = "../../download/template/RecordInfoTemplate.xls";
            window.location.href = urlstr;

        },
        query: function () {
            vm.reload(null, 1);
        },
        queryClear: function () {
            vm.q.userId = '';
            vm.q.name = '';
            vm.q.mobile = '';
            vm.q.teacherId = '';
            vm.q.teacherName = '';
            vm.q.areaName = '';
            vm.q.areaId = '';
            vm.q.deptIdList = '';
            vm.q.deptNameList = '';
            vm.q.classId = '';
            vm.q.className = '';
            vm.q.classplanId = '';//排课
            vm.q.classplanName = '';
            vm.q.createMonth = '';
            vm.q.followStatus = '';
            vm.q.returnType = '';
            vm.q.expectTime = '';
            vm.q.approvalDate = '';
            vm.q.productName = '';
            vm.q.productId = '';
            vm.q.ncCode = '';
            vm.q.dr = '';
            vm.q.zy = '';
            vm.q.dr = '';
            vm.q.courseName = '';
            vm.q.consultant = '';
            vm.q.cstatus = '';
            vm.q.isInsurant = '';
            vm.q.classType = '';

            vm.q.expect_time = '';
            vm.q.createMonth = '';
            vm.q.approvalDate = '';
            vm.q.startRegDate = '';
            vm.q.endRegDate = '';
            vm.q.startReceiveDate = '';
            vm.q.endReceiveDate = '';


        },
        reload: function (e, p) {
            vm.q.expectTime = $("#expect_time").val();
            vm.q.approvalDate = $("#approval_date").val();
            vm.q.startRegDate = $("#startRegDate").val();
            vm.q.endRegDate = $("#endRegDate").val();
            vm.q.startReceiveDate = $("#startReceiveDate").val();
            vm.q.endReceiveDate = $("#endReceiveDate").val();
            vm.showList = true;
            if (vm.q.expectTime) {
                if (vm.q.returnType && vm.q.returnType != "0") {
                    alert("不可通过预计回访时间查询！");
                    return;
                }
            }
            var page = p || $("#jqGrid").jqGrid('getGridParam', 'page');
            var q = vm.q;
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: q
            }).trigger("reloadGrid");
        },
        updateFollow: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            //行数据
            var rowData = $("#jqGrid").jqGrid('getRowData', id);
            layer.open({
                type: 1,
                area: 'auto',
                title: '跟进',
                closeBtn: 1,
                skin: "layui-layer-lan",
                content: $("#followStatusLayDiv"),
                scrollbar: true,//是否允许浏览器出现滚动条
                fixed: false,//固定
                shadeClose: false,// 是否点击遮罩关闭
                resize: true,//是否允许拉伸
                maxmin: true, //开启最大化最小化按钮
                zIndex: 19891014,
                area: ['600px', '500px'],
                btn: ['确认', '取消'],
                btn1: function (index) {

                    if (vm.info.followStatus == "1" && vm.info.followContent == "") {
                        alert("请填写联系内容");
                        return;
                    }else if (vm.info.followStatus == "0") {
                        alert("未联系状态不允许保存，请选择已联系状态");
                        return;
                    }else if (vm.info.followStatus == null) {
                        alert("联系状态必选");
                        return;
                    }
                    var param = {
                        recordSignId: rowData.recordSignId,
                        followStatus: vm.info.followStatus,
                        userId: rowData.userId,
                        followContent: vm.info.followContent
                    }
                    $.ajax({
                        type: "POST",
                        url: '../record/recordSign/updateFollow',
                        data: JSON.stringify(param),
                        success: function (r) {
                            if (r.code === 0) {
                                layer.close(index);
                                alert('操作成功', function (index) {
                                    vm.reload();
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                },
                success: function () {
                    vm.info = rowData;
                }
            });

        },
        querySituation: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $("#situastion_showList").attr("style", "display:block;");
            situastionLay.show(function (index, opt) {

            }, id);
        },
        queryReturnVisit: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var rowData = $("#jqGrid").jqGrid('getRowData', id);
            returnVisitLay.show(function (index, opt) {
            }, rowData);
        },
        updateSituation: function () {

        },
        areaLayerShow: function () {//省份浮层
            areaLay.show(function (index, opt) {
                vm.q.areaId = opt.areaId;
                vm.q.areaName = opt.areaName;
            });
        },
        deptLayerShowQuery: function () {//部门查询条件
            deptQueryLay.show(function (opt, deptIdList, deptNameList) {
                //vm.q.deptId = id;
                //vm.q.deptName = name;
                vm.q.deptIdList = deptIdList.join(",");
                vm.q.deptNameList = deptNameList;
                //alert(vm.q.deptIdList+"======")
            });
        },
        productShowQuery: function () {//产品线查询条件
            productLay.show(function (index, opt) {
                vm.q.productId = opt.productId;
                vm.q.productName = opt.productName;
            });
        },
        classLayerShowQuery: function () {//班级浮层
            classLay.show(function (index, opt) {
                vm.q.classId = opt.classId;
                vm.q.className = opt.className;
            });
        },
        classplanLayerShow: function () {//排课
            classplanLay.show(function (index, opt) {
                vm.q.classplanId = opt.classplanId;
                vm.q.classplanName = opt.classplanName;
            });
        },
    }
});

