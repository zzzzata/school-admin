package io.renren.entity;

import java.util.Date;

/**
 * 学员档案-报读信息
 *
 * @author lintf
 */
public class RecordSignEntity {
    @Override
    public String toString() {
        return "RecordSignEntity{" +
                "classtype='" + classtype + '\'' +
                ", approvalDate='" + approvalDate + '\'' +
                ", campusPhone='" + campusPhone + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyPhone='" + emergencyPhone + '\'' +
                ", consultant='" + consultant + '\'' +
                ", isRecommend='" + isRecommend + '\'' +
                ", recommendTeacher='" + recommendTeacher + '\'' +
                ", trainConsultant='" + trainConsultant + '\'' +
                ", cashDate='" + cashDate + '\'' +
                ", zkStudyStatus=" + zkStudyStatus +
                ", zkRecordStatus=" + zkRecordStatus +
                ", zkCollege='" + zkCollege + '\'' +
                ", zkGraduateDate='" + zkGraduateDate + '\'' +
                ", zkMajor='" + zkMajor + '\'' +
                ", bkMajor='" + bkMajor + '\'' +
                ", bkCollege='" + bkCollege + '\'' +
                ", politicsStatus='" + politicsStatus + '\'' +
                ", hj='" + hj + '\'' +
                ", isInsurant='" + isInsurant + '\'' +
                ", recordSignId=" + recordSignId +
                ", recordId=" + recordId +
                ", courseName='" + courseName + '\'' +
                ", regDate='" + regDate + '\'' +
                ", bdyx='" + bdyx + '\'' +
                ", zy='" + zy + '\'' +
                ", className='" + className + '\'' +
                ", level='" + level + '\'' +
                ", status=" + status +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", ncId='" + ncId + '\'' +
                ", createTime=" + createTime +
                ", ts=" + ts +
                ", syncTime=" + syncTime +
                ", followStatus=" + followStatus +
                ", followPerson=" + followPerson +
                ", followTime=" + followTime +
                ", followContent='" + followContent + '\'' +
                ", lastReturnTime=" + lastReturnTime +
                '}';
    }

    /**
     * 班型Id/商品类型
     */
    private String classtype;
    /**
     * 审批日期
     */
    private String approvalDate;
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
     * 是否转介绍(Y:是 N:否)
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
    private String cashDate;
    /**
     * 自考学习状态
     * (1:在读 2:休学 3:毕业 4:暂停 5:坏账)
     */
    private Integer zkStudyStatus;
    /**
     * 自考档案状态
     * (1:正常 2:作废)
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
     * 是否购买保险(Y是)
     */
    private String isInsurant;

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
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

    public String getCashDate() {
        return cashDate;
    }

    public void setCashDate(String cashDate) {
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
     * 报读信息表主键
     */
    private Long recordSignId;
    /**
     * 学员档案主键
     */
    private Long recordId;
    /**
     * 报读班型
     */
    private String courseName;
    /**
     * 报读时间
     */
    private String regDate;
    /**
     * 报读院校
     */
    private String bdyx;
    /**
     * 专业
     */
    private String zy;
    /**
     * 自考班级
     */
    private String className;
    /**
     * 层次
     */
    private String level;
    /**
     * 状态(1在读,2休学,3退学)
     */
    private Integer status;
    /**
     * 订单id
     */
    private Long orderId;

    private Long userId;

    private String name;
    private String mobile;


    /**
     * NC中的报名表主键
     */
    private String ncId;
    private Date createTime;
    private Date ts;
    private Long syncTime;
    /**
     * 跟进状态
     */
    private Integer followStatus;
    /**
     * 跟进人
     */
    private Long followPerson;
    /***
     * 跟进时间
     */
    private Date followTime;
    //跟进内容
    private String followContent;
    //最后一次回访时间
    private Date lastReturnTime;

    public RecordSignEntity() {
    }

    /**
     * 从订单处组装成学员档案报读信息
     *
     * @param order 队列中的NC报名表信息
     */
    public RecordSignEntity(OrderMessageConsumerEntity order, MallOrderEntity m) {
        this.status = 1;
        if (m != null) {
            this.orderId = m.getOrderId();
            this.userId = m.getUserId();
        }


        if (order != null) {

            this.isInsurant = order.getIsInsurant();
            this.mobile = order.getPhone();
            this.name = order.getUser_name();
            this.classtype = order.getClasstype();
            this.approvalDate = order.getApprovalDate();
            this.campusPhone = order.getCampusPhone();
            this.emergencyContact = order.getEmergencyContact();
            this.emergencyPhone = order.getEmergencyPhone();
            this.consultant = order.getConsultant();
            this.isRecommend = order.getIsRecommend();
            this.recommendTeacher = order.getRecommendTeacher();
            this.trainConsultant = order.getTrainConsultant();
            this.cashDate = order.getCashDate();
            this.zkStudyStatus = order.getZkStudyStatus();
            this.zkRecordStatus = order.getZkRecordStatus();
            this.zkCollege = order.getZkCollege();
            this.zkGraduateDate = order.getZkGraduateDate();
            this.zkMajor = order.getZkMajor();
            this.bkMajor = order.getBkMajor();
            this.bkCollege = order.getBkCollege();
            this.politicsStatus = order.getPoliticsStatus();
            this.hj = order.getHj();
            this.isRecommend = order.getIsRecommend();


            this.bdyx = order.getBdyx();
            this.className = order.getZkClassName();
            this.courseName = order.getClass_name();
            this.ncId = order.getNc_id();
            this.regDate = order.getRegistdate();
            this.level = order.getRecord();

            this.zy = order.getZy();
			/*1	在读	
			2	休学	
			3	毕业	
			4	暂停	
			6	坏账*/
            //功能变更为不从NC中取得状态了,改为以蓝鲸的休学考勤中取  默认为在读 有休学单审批的为休学 在NC中退费的为退学
            //此处只判断NC退学 其他的判断在io.renren.service.impl.RecordSignServiceImpl.getRecordSignStatus中判断
            if (order.getSign_status() == null || order.getSign_status() == 1 || order.getSign_status() == 3 || order.getSign_status() == 2) {
                this.status = 1;

            } else {
                this.status = 3;
            }
        }

    }

    public Long getRecordSignId() {
        return recordSignId;
    }

    public void setRecordSignId(Long recordSignId) {
        this.recordSignId = recordSignId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getBdyx() {
        return bdyx;
    }

    public void setBdyx(String bdyx) {
        this.bdyx = bdyx;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getNcId() {
        return ncId;
    }

    public void setNcId(String ncId) {
        this.ncId = ncId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Long getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Long syncTime) {
        this.syncTime = syncTime;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 从消息队列中来的数据更新旧的报读信息
     *
     * @param old
     * @param s
     * @param m
     * @author lintf
     * 2018年8月23日
     */
    public void signUpdate(RecordSignEntity old, RecordSignEntity s, MallOrderEntity m) {
        if (m != null) {
            if (m.getOrderId() != null && m.getOrderId() > 0) {
                old.setOrderId(m.getOrderId());
            }

            old.setUserId(m.getUserId());
        } else if (s != null) {
            if (s.getOrderId() != null && s.getOrderId() > 0) {
                old.setOrderId(s.getOrderId());
            }
            if (s.getUserId() != null && s.getUserId() > 0) {

                old.setUserId(s.getUserId());
            }


        }
        old.setClasstype(s.getClasstype());
        old.setApprovalDate(s.getApprovalDate());
        old.setCampusPhone(s.getCampusPhone());
        old.setEmergencyContact(s.getEmergencyContact());
        old.setEmergencyPhone(s.getEmergencyPhone());
        old.setConsultant(s.getConsultant());
        old.setIsRecommend(s.getIsRecommend());
        old.setRecommendTeacher(s.getRecommendTeacher());
        old.setTrainConsultant(s.getTrainConsultant());
        old.setCashDate(s.getCashDate());
        old.setZkStudyStatus(s.getZkStudyStatus());
        old.setZkRecordStatus(s.getZkRecordStatus());
        old.setZkCollege(s.getZkCollege());
        old.setZkGraduateDate(s.getZkGraduateDate());
        old.setZkMajor(s.getZkMajor());
        old.setBkMajor(s.getBkMajor());
        old.setBkCollege(s.getBkCollege());
        old.setPoliticsStatus(s.getPoliticsStatus());
        old.setHj(s.getHj());
        old.setIsInsurant(s.getIsInsurant());

        old.setBdyx(s.getBdyx());
        old.setClassName(s.getClassName());
        old.setCourseName(s.getCourseName());
        old.setLevel(s.getLevel());
        old.setNcId(s.getNcId());
        old.setRegDate(s.getRegDate());
        old.setZy(s.getZy());
        old.setSyncTime(s.getSyncTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public Long getFollowPerson() {
        return followPerson;
    }

    public void setFollowPerson(Long followPerson) {
        this.followPerson = followPerson;
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

    public Date getLastReturnTime() {
        return lastReturnTime;
    }

    public void setLastReturnTime(Date lastReturnTime) {
        this.lastReturnTime = lastReturnTime;
    }
}
