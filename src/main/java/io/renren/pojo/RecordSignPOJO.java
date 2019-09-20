package io.renren.pojo;

import io.renren.service.SysDeptService;
import io.renren.service.SysProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecordSignPOJO {
    @Autowired
    private SysDeptService sysDeptService;

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    /**
     * 教材寄送地址
     */
    private String sendAddress;

    /**
     * 商品类型
     */
    private String classType;
    /**
     * 审批日期
     */
    private Date approvalDate;
    /**
     * 校区电话
     */
    private String campusPhone;
    /**
     * 紧急联系人
     */
    private String emergencyContact;
    /**
     * 紧急联系人电话
     */
    private String emergencyPhone;
    /**
     * 初次课程顾问
     */
    private String consultant;
    /**
     * 是否转介绍
     */
    private String isRecommend;
    /**
     * 转介绍老师
     */
    private String recommendTeacher;
    /**
     * 培训课程顾问
     */
    private String trainConsultant;
    /**
     * 交齐款日期
     */
    private Date cashDate;
    /**
     * 自考学习状态
     */
    private Integer zkStudyStatus;
    /**
     * 自考档案状态
     */
    private Integer zkRecordStatus;
    /**
     * 专科毕业院校
     */
    private String zkCollege;
    /**
     * 专科毕业时间
     */
    private String zkGraduateDate;
    /**
     * 专科毕业专业
     */
    private String zkMajor;
    /**
     * 本科报读专业
     */
    private String bkMajor;
    /**
     * 本科报读院校
     */
    private String bkCollege;
    /**
     * 政治面貌
     */
    private String politicsStatus;
    /**
     * 户籍
     */
    private String hj;
    /**
     *是否购买保险
     * Y：是 N：否
     */
    private String isInsurant;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getCampusPhone() {
        return campusPhone;
    }

    public void setCampusPhone(String campusPhone) {
        this.campusPhone = campusPhone;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getRecommendTeacher() {
        return recommendTeacher;
    }

    public void setRecommendTeacher(String recommendTeacher) {
        this.recommendTeacher = recommendTeacher;
    }

    public String getTrainConsultant() {
        return trainConsultant;
    }

    public void setTrainConsultant(String trainConsultant) {
        this.trainConsultant = trainConsultant;
    }

    public Date getCashDate() {
        return cashDate;
    }

    public void setCashDate(Date cashDate) {
        this.cashDate = cashDate;
    }

    public Integer getZkStudyStatus() {
        return zkStudyStatus;
    }

    public void setZkStudyStatus(Integer zkStudyStatus) {
        this.zkStudyStatus = zkStudyStatus;
    }

    public Integer getZkRecordStatus() {
        return zkRecordStatus;
    }

    public void setZkRecordStatus(Integer zkRecordStatus) {
        this.zkRecordStatus = zkRecordStatus;
    }

    public String getZkCollege() {
        return zkCollege;
    }

    public void setZkCollege(String zkCollege) {
        this.zkCollege = zkCollege;
    }

    public String getZkGraduateDate() {
        return zkGraduateDate;
    }

    public void setZkGraduateDate(String zkGraduateDate) {
        this.zkGraduateDate = zkGraduateDate;
    }

    public String getZkMajor() {
        return zkMajor;
    }

    public void setZkMajor(String zkMajor) {
        this.zkMajor = zkMajor;
    }

    public String getBkMajor() {
        return bkMajor;
    }

    public void setBkMajor(String bkMajor) {
        this.bkMajor = bkMajor;
    }

    public String getBkCollege() {
        return bkCollege;
    }

    public void setBkCollege(String bkCollege) {
        this.bkCollege = bkCollege;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

    public String getIsInsurant() {
        return isInsurant;
    }

    public void setIsInsurant(String isInsurant) {
        this.isInsurant = isInsurant;
    }

    /**
     * 学员档案主键
     */
    private Long recordId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 学员名称
     */
    private String name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 年龄
     */
    private Integer age;


    private String ageStr;
    /**
     * 学历
     */
    private String record;
    /**
     * 电话号码/蓝鲸账号
     */
    private String mobile;
    private String qq;
    /**
     * 是否已婚
     */
    private Integer marriageStatus;
    /**
     * 是否生育
     */
    private Integer fertilityStatus;
    /**
     * 每天学习时间
     */
    private Integer studyTimeOfDay;
    /**
     * 现在岗位
     */
    private String postName;
    private String accountingCertificates;
    /**
     * NC中学员id
     */
    private String ncStudentId;
    /**
     * 小孩数量
     */
    private int chirdCount;
    /**
     * 学员目标
     */
    private String studentTarget;
    /**
     * 正备考证书
     */
    private String certificate;
    /**
     * 主键
     */
    private Long recordSignId;
    /**
     * 报读班型
     */
    private String courseName;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 跟进状态
     */
    private Integer followStatus;
    /**
     * 跟进状态
     */
    private String followStatusStr;
    /**
     * 跟进人
     */
    private String followPersonName;
    /**
     * 跟进时间
     */
    private Date followTime;
    /**
     * 跟进内容
     */
    private String followContent;
    /**
     * 大区
     */
    private String regionDept;
    /**
     * 省份
     */
    private String areaName;
    /**
     * 校区
     */
    private String deptName;
    /**
     * 校区编码
     */
    private String deptCode;
    /**
     * 班主任
     */
    private String teacherName;

    private String productName;

    private String orderNo;
    /*
     * 回访学员项目新增字段
     * */
    /**
     * 报名单号
     */
    private String ncCode;
    /**
     * 报名日期
     */
    private String regDate;
    /**
     * 报读专业
     */
    private String zy;
    /**
     * 班级
     */
    private String className;
    /**
     * 订单状态
     * dr 0.正常 1.删除 2.关闭
     */
    private Integer dr;

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    /**
     * 回访状态 0:未回访 1：已回访
     */
    private Integer rstatus;

    public Integer getrCount() {
        return rCount;
    }

    public void setrCount(Integer rCount) {
        this.rCount = rCount;
    }

    /**
     * 未回访次数
     */
    private Integer rCount;

    /**
     * 报读大学
     */
    private String bdyx;
    /**
     * 最后修改人
     */
    private String modifyPerson;
    /**
     * 最后修改时间
     */
    private Date modifyTime;
    /**
     * 签署状态
     * (0-未签订 1-已签订  2-驳回)
     */
    private Integer cstatus;

    public String getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public Integer getCstatus() {
        return cstatus;
    }

    public void setCstatus(Integer cstatus) {
        this.cstatus = cstatus;
    }


    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBdyx() {
        return bdyx;
    }

    public void setBdyx(String bdyx) {
        this.bdyx = bdyx;
    }

    public Integer getRstatus() {
        return rstatus;
    }

    public void setRstatus(Integer rstatus) {
        this.rstatus = rstatus;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeStr() {
        return ageStr;
    }

    public void setAgeStr(String ageStr) {
        this.ageStr = ageStr;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public Integer getFertilityStatus() {
        return fertilityStatus;
    }

    public void setFertilityStatus(Integer fertilityStatus) {
        this.fertilityStatus = fertilityStatus;
    }

    public Integer getStudyTimeOfDay() {
        return studyTimeOfDay;
    }

    public void setStudyTimeOfDay(Integer studyTimeOfDay) {
        this.studyTimeOfDay = studyTimeOfDay;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getAccountingCertificates() {
        return accountingCertificates;
    }

    public void setAccountingCertificates(String accountingCertificates) {
        this.accountingCertificates = accountingCertificates;
    }

    public String getNcStudentId() {
        return ncStudentId;
    }

    public void setNcStudentId(String ncStudentId) {
        this.ncStudentId = ncStudentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRecordSignId() {
        return recordSignId;
    }

    public void setRecordSignId(Long recordSignId) {
        this.recordSignId = recordSignId;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public String getFollowStatusStr() {
        if (followStatus != null && followStatus.intValue() == 1) {
            followStatusStr = "已联系";
        } else {
            followStatusStr = "未联系";
        }
        return followStatusStr;
    }

    public void setFollowStatusStr(String followStatusStr) {
        this.followStatusStr = followStatusStr;
    }

    public String getFollowPersonName() {
        return followPersonName;
    }

    public void setFollowPersonName(String followPersonName) {
        this.followPersonName = followPersonName;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public String getFollowContent() {
        return followContent;
    }

    public void setFollowContent(String followContent) {
        this.followContent = followContent;
    }

    public String getRegionDept() {
//        if(regionDept==null){
////            Map<String,Object> map = new HashMap<String,Object>();
////            map.put("ncCode",deptCode);
//            regionDept = sysDeptService.queryObjectByNcId("0001A510000000000KYI").getName();
//        }
        return regionDept;
    }

    public void setRegionDept(String regionDept) {
        this.regionDept = regionDept;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getChirdCount() {
        return chirdCount;
    }

    public void setChirdCount(int chirdCount) {
        this.chirdCount = chirdCount;
    }

    public String getStudentTarget() {
        return studentTarget;
    }

    public void setStudentTarget(String studentTarget) {
        this.studentTarget = studentTarget;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
