package io.renren.pojo.courseliverelaterecord;

import io.renren.entity.courseliverelaterecord.CourseLiveRelateRecordEntity;

import java.util.List;

/**
 * 直播课次与录播章节对照关系 PO类
 * @author chen xin yu
 * @date 2019-06-18 14:21
 */
public class CourseLiveRelateRecordPOJO extends CourseLiveRelateRecordEntity {

    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 直播课次名称
     */
    private String liveName;
    /**
     * 录播课次名称
     */
    private String recordName;

    /**
     * 录播课次名称字符串，以逗号隔开
     */
    private String recordNameGroup;

    /**录播课次id*/
    private String recordIds;

    public CourseLiveRelateRecordPOJO(){}

    public CourseLiveRelateRecordPOJO(Long id, Long courseId, String liveId, Long recordId, Integer state, Integer dr) {
        super(id, courseId, liveId, recordId, state, dr);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordNameGroup() {
        return recordNameGroup;
    }

    public void setRecordNameGroup(String recordNameGroup) {
        this.recordNameGroup = recordNameGroup;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }
}
