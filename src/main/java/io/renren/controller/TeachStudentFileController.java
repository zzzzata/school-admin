package io.renren.controller;

import io.renren.entity.TeachStudentFilesEntity;
import io.renren.service.TeachStudentFilesService;
import io.renren.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 弹窗基础数据
 * @class io.renren.controller.CommonController.java
 * @Description:
 * @author chenlinfeng
 * @dete 2018年12月26日
 */
@Controller
@RequestMapping("/teachStudentFile")
public class TeachStudentFileController extends AbstractController {
    @Autowired
    private TeachStudentFilesService teachStudentFilesService;


    /**
     * 获取所有的资料库资料
     * 从teach_student_files查询
     * @return  返回资料的列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(){
        List<TeachStudentFilesEntity> list = teachStudentFilesService.getList();

        return R.ok().put("data", list);
    }


    /**
     * 根据课程的id去查询相应的资料库资料,注意:是从新表查询的
     * 从teach_student_files查询
     * @param courseId  查看的id
     * @return  返回资料的列表
     */
    @ResponseBody
    @RequestMapping("/selectMaterialListByCourseId/{courseId}")
//    @RequiresPermissions("layerdata:selectMaterialListByCourseId")
    public R selectMaterialListByCourseId(@PathVariable("courseId") Long courseId){
        List<TeachStudentFilesEntity> list = teachStudentFilesService.selectMaterialListByCourseId(courseId);

        return R.ok().put("data", list);
    }


}
