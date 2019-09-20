// 导出大数据量，统一为文本格式
$.module("hq.ui.csv",function() {

    return {
        /**
         * 导出excel
         * @param jqGridId 表格id
         */
        exportDataCsv : function(jqGridId){//导出excel
            jqGridId = jqGridId || "jqGrid"
            if(getExplorer()=='ie')
            {
                var curTbl = document.getElementById(tableid);
                var oXL = new ActiveXObject("Excel.Application");
                var oWB = oXL.Workbooks.Add();
                var xlsheet = oWB.Worksheets(1);
                var sel = document.body.createTextRange();
                sel.moveToElementText(curTbl);
                sel.select();
                sel.execCommand("Copy");
                xlsheet.Paste();
                oXL.Visible = true;

                try {
                    //var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
                    var fname = oXL.Application.GetSaveAsFilename("将table导出到excel.xls", "Excel Spreadsheets (*.xls), *.xls");
                } catch (e) {
                    print("Nested catch caught " + e);
                } finally {
                    oWB.SaveAs(fname);
                    oWB.Close(savechanges = false);
                    oXL.Quit();
                    oXL = null;
                    idTmr = window.setInterval("Cleanup();", 1);
                }

            }
            else
            {
                tableToExcelCSV
                (jqGridId);
            }
        }
    }
});

var idTmr;
//获取当前浏览器类型
function getExplorer() {
    var explorer = window.navigator.userAgent ;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return 'ie';
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome
    else if(explorer.indexOf("Chrome") >= 0){
        return 'Chrome';
    }
    //Opera
    else if(explorer.indexOf("Opera") >= 0){
        return 'Opera';
    }
    //Safari
    else if(explorer.indexOf("Safari") >= 0){
        return 'Safari';
    }
}
function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}

//判断浏览器后调用的方法，把table的id传入即可
var tableToExcelCSV = function(table) {
    table = document.getElementById(table);
    var str = "";
    var tableHead = $('.ui-jqgrid-htable').html();
    var ths = $(tableHead).find('th');
    $.each(ths,function (index,k) {
        str += $(k).text()+',';
    })
    var table_trs = $(table).find('tr');
    $.each(table_trs,function (index,v) {
        var tds = $(v).find('td');
        $.each(tds,function (index,k) {
            str += $(k).text()+'\t,';
        })
        str += '\n';
    })
    var link= $('<a id="commonExcelDownload" ></a>');
    var blob = new Blob([str], {type: "text/plain;charset=utf-8"});
    //解决中文乱码问题
    blob =  new Blob([String.fromCharCode(0xFEFF), blob], {type: blob.type});
    link.get(0).href = window.URL.createObjectURL(blob);
    link.get(0).download = "文件导出.csv";
    link.get(0).click();
};
