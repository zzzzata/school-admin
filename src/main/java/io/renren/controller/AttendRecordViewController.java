
package io.renren.controller;

import io.renren.pojo.AttendRecordViewPOJO;
import io.renren.pojo.RecordLogDetailsPOJO;
import io.renren.pojo.log.LogStudentAttentLiveLogDetails;
import io.renren.service.AttendRecordViewService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
    @RequestMapping("attendRecordView")
public class AttendRecordViewController extends AbstractController {
    @Autowired
    private AttendRecordViewService attendRecordViewService;

    @ResponseBody
    @RequestMapping("/list")
    public R list(HttpServletRequest request) {
        Map<String, Object> map = getMapPage(request);
        stringQuery(map, request, "startDate");
        stringQuery(map, request, "endDate");
        stringQuery(map, request, "courseId");
        stringQuery(map, request, "classtypeId");
        stringQuery(map, request, "deptId");
        stringQuery(map, request, "videoName");
        stringQuery(map, request, "className");
        stringQuery(map, request, "teacherName");
        stringQuery(map, request, "userName");
        stringQuery(map, request, "mobile");
        List<AttendRecordViewPOJO> attendRecordViewPOJOList = attendRecordViewService.queryListPOJO(map);
        int total = attendRecordViewPOJOList.size();
        PageUtils pageUtil = new PageUtils(attendRecordViewPOJOList, total, request);
        return R.ok().put("data", pageUtil);
    }
    
    @RequestMapping(value = "logDetails")
    @ResponseBody
    public R logDetalis(HttpServletRequest request){
        Long userId = null;//学员ID
        Long recordId = null;//录播课ID
        recordId = ServletRequestUtils.getLongParameter(request, "recordId", 0);
        userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        if(recordId == 0 || userId == 0){
            return R.error("请选择一条有效信息!");
        }
        List<RecordLogDetailsPOJO> logDetailsList = attendRecordViewService.queryLogDetailsByUserIdAndRecordId(userId, recordId);
        //查询列表数据
        int total = 0;
        if(null != logDetailsList && !logDetailsList.isEmpty()){
            total = logDetailsList.size();
        }
        PageUtils pageUtil = new PageUtils(logDetailsList, total, request);
        return R.ok().put(pageUtil);
    }
}

