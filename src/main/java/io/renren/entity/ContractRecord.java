package io.renren.entity;

import java.util.Date;
import java.util.List;

public class ContractRecord {

    private Long id;//pk
    private Long orderId;//订单pk
    private Long userId;//学员pk
    private String realName;//学员姓名
    private String idCard;//学员身份证
    private String mobile;//手机
    private int status;//状态(0-未签订 1-已签订 2-已驳回)
    private Long contractId;//在线协议id
    private Long templateId;//模板id
    private Long signerId;//在线用户pk
    private String teacherName;//课程顾问老师
    private String ncId;//订单主键
    private int sex;//性别
    private String bdyx;//报读院校
    private String companyName;//签协议的公司名称
    private int companyId;//在线协议的公司id

    private String record;//学历
    private String zy;//专业
    private String qq;//QQ
    private String emergencyPro;//紧急联系人
    private String emergencyCall;//紧急联系电话
    private String provinceName;//报考省份
    private String vbillCode;//报名表号
    private String className;//班级名称
    private String courseName;//班型名称
    private String payName;//支付方式
    private String regdate;//报读日期
    private int dr;//是否删除0 为未删除, 1 为删除
    private Date createTime;
    private Date ts;
    private Double regMoney;//报名表金额

    private int idcardLocation;
    private int mobileLocation;
    //产品线PK
    private Long productId;
    private List<ContractDetail> contractDetail;
    /**
     * NC中的Ts
     */
    private long syncTime;

    private String saleTeacherName;//招生老师
    private String saleTeacherMobile;//招生老师电话
    private String bkZy;//本科专业
    private String bkBdyx;//本科报读院校
    private String bkClassId;//本科报读班型
    /**
     * 民族
     */
    private String nation;
    /**
     * 当前学历
     */
    private String currentRecord;
    /**
     * 备注
     */
    private String remark;
    /**
     * 专科毕业时间
     */
    private String graduateTime;
    /**
     * 专科毕业院校
     */
    private String graduateSchool;
    /**
     * 专科毕业专业
     */
    private String major;
    /**
     * 政治面貌
     */
    private String polity;
    /**
     * 户籍
     */
    private String residence;
    /**
     * 报读类型
     */
    private String enrollmentType;
    /**
     * 日志更新时间
     */
    private Date logUpdateTime;

    public String getBkClassId() {
        return bkClassId;
    }

    public void setBkClassId(String bkClassId) {
        this.bkClassId = bkClassId;
    }

    private String bkProvinceName;//本科报考省份

    private Integer groupGoodStatus = 0;//用于是否组合班型的 0 不是专本连读 1是

    public ContractRecord() {
    }

    /**
     * 本科的专业
     *
     * @return
     */
    public String getBkZy() {
        return bkZy;
    }

    /**
     * 本科的专业
     *
     * @param bkZy
     */
    public void setBkZy(String bkZy) {
        this.bkZy = bkZy;
    }


    /**
     * 本科的报读院校
     *
     * @return
     */
    public String getBkBdyx() {
        return bkBdyx;
    }


    /**
     * 本科的报读院校
     *
     * @param bkBdyx
     */

    public void setBkBdyx(String bkBdyx) {
        this.bkBdyx = bkBdyx;
    }


    public String getBkProvinceName() {
        return bkProvinceName;
    }


    public void setBkProvinceName(String bkProvinceName) {
        this.bkProvinceName = bkProvinceName;
    }


    /**
     * 用于是否组合班型的 0 不是专本连读 1是
     *
     * @return
     */
    public Integer getGroupGoodStatus() {
        return groupGoodStatus;
    }


    /**
     * 用于是否组合班型的 0 不是专本连读 1是
     *
     * @param groupGoodStatus
     */
    public void setGroupGoodStatus(Integer groupGoodStatus) {
        this.groupGoodStatus = groupGoodStatus;
    }


    public String getSaleTeacherName() {
        return saleTeacherName;
    }


    public void setSaleTeacherName(String saleTeacherName) {
        this.saleTeacherName = saleTeacherName;
    }


    public String getSaleTeacherMobile() {
        return saleTeacherMobile;
    }


    public void setSaleTeacherMobile(String saleTeacherMobile) {
        this.saleTeacherMobile = saleTeacherMobile;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public int getCompanyId() {
        return companyId;
    }


    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


    public long getSyncTime() {
        return syncTime;
    }


    public void setSyncTime(long syncTime) {
        this.syncTime = syncTime;
    }


    public ContractRecord getContractRecord(OrderMessageConsumerEntity or, MallOrderEntity mallOrderEntity, Long tempid) {
        ContractRecord cd = new ContractRecord();
        if (or.getContractHead() != null) {
            cd = or.getContractHead();
            cd.setProductId(mallOrderEntity.getProductId());
            cd.setOrderId(mallOrderEntity.getOrderId());
            cd.setUserId(mallOrderEntity.getUserId());
            cd.setTemplateId(tempid);
            cd.setCompanyName(or.getContractCompanyName());
            cd.setSaleTeacherMobile(or.getSaleTeacherMobile());
            cd.setSaleTeacherName(or.getSaleTeacherName());
        }

        if (or.getContractBody() != null) {
            cd.setContractDetail(or.getContractBody());
        }
        return cd;

    }

    public void setNew(ContractRecord oldOne, ContractRecord newOne) {

        oldOne.setBdyx(newOne.getBdyx());
        oldOne.setClassName(newOne.getClassName());
        //	oldOne.setContractDetail(newOne.getContractDetail());
        oldOne.setContractId(newOne.getContractId());
        oldOne.setCourseName(newOne.getCourseName());
        oldOne.setCreateTime(newOne.getCreateTime());
        oldOne.setDr(newOne.getDr());
        oldOne.setEmergencyCall(newOne.getEmergencyCall());
        oldOne.setEmergencyPro(newOne.getEmergencyPro());
        //oldOne.setId(newOne.getId());
        oldOne.setIdCard(newOne.getIdCard());
        //	oldOne.setIdcardLocation(newOne.getIdcardLocation());
        oldOne.setMobile(newOne.getMobile());
        //oldOne.setMobileLocation(newOne.getMobileLocation());
        //oldOne.setNcId(newOne.getNcId());

        //	oldOne.setOrderId(newOne.getOrderId());
        oldOne.setPayName(newOne.getPayName());
        //	oldOne.setProductId(newOne.getProductId());
        oldOne.setProvinceName(newOne.getProvinceName());
        oldOne.setQq(newOne.getQq());
        oldOne.setRealName(newOne.getRealName());
        oldOne.setRecord(newOne.getRecord());
        oldOne.setRegdate(newOne.getRegdate());
        oldOne.setRegMoney(newOne.getRegMoney());
        oldOne.setSex(newOne.getSex());
        //	oldOne.setSignerId(newOne.getSignerId());
        //	oldOne.setStatus(newOne.getStatus());
        oldOne.setSyncTime(newOne.getSyncTime());
        oldOne.setTeacherName(newOne.getTeacherName());
        //	oldOne.setTemplateId(newOne.getTemplateId());
        //	oldOne.setTs(newOne.getTs());
        //	oldOne.setUserId(newOne.getUserId());
        oldOne.setVbillCode(newOne.getVbillCode());
        oldOne.setZy(newOne.getZy());

        oldOne.setCompanyId(newOne.getCompanyId());
        oldOne.setSaleTeacherMobile(newOne.getSaleTeacherMobile());
        oldOne.setSaleTeacherName(newOne.getSaleTeacherName());
        oldOne.setDr(newOne.getDr());
        //增加本科的字段的赋值
        oldOne.setBkBdyx(newOne.getBkBdyx());
        oldOne.setBkZy(newOne.getBkZy());
        oldOne.setBkProvinceName(newOne.getBkProvinceName());
        oldOne.setGroupGoodStatus(newOne.getGroupGoodStatus());
        oldOne.setNation(newOne.getNation());
        oldOne.setCurrentRecord(newOne.getCurrentRecord());
        oldOne.setRemark(newOne.getRemark());
        oldOne.setGraduateTime(newOne.getGraduateTime());
        oldOne.setGraduateSchool(newOne.getGraduateSchool());
        oldOne.setMajor(newOne.getMajor());
        oldOne.setPolity(newOne.getPolity());
        oldOne.setResidence(newOne.getResidence());
        oldOne.setEnrollmentType(newOne.getEnrollmentType());
    }


    public String getRecord() {
        return record;
    }


    public void setRecord(String record) {
        this.record = record;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }


    public Double getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(Double regMoney) {
        this.regMoney = regMoney;
    }

    public List<ContractDetail> getContractDetail() {
        return contractDetail;
    }

    public void setContractDetail(List<ContractDetail> contractDetail) {
        this.contractDetail = contractDetail;
    }

    public String getNcId() {
        return ncId;
    }

    public void setNcId(String ncId) {
        this.ncId = ncId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getIdcardLocation() {
        return idcardLocation;
    }

    public void setIdcardLocation(int idcardLocation) {
        this.idcardLocation = idcardLocation;
    }

    public int getMobileLocation() {
        return mobileLocation;
    }

    public void setMobileLocation(int mobileLocation) {
        this.mobileLocation = mobileLocation;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;

    }

    public void setStatus(int status) {
        this.status = status;

    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getSignerId() {
        return signerId;
    }

    public void setSignerId(Long signerId) {
        this.signerId = signerId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmergencyPro() {
        return emergencyPro;
    }

    public void setEmergencyPro(String emergencyPro) {
        this.emergencyPro = emergencyPro;
    }

    public String getEmergencyCall() {
        return emergencyCall;
    }

    public void setEmergencyCall(String emergencyCall) {
        this.emergencyCall = emergencyCall;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getVbillCode() {
        return vbillCode;
    }

    public void setVbillCode(String vbillCode) {
        this.vbillCode = vbillCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }


    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(String currentRecord) {
        this.currentRecord = currentRecord;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPolity() {
        return polity;
    }

    public void setPolity(String polity) {
        this.polity = polity;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getEnrollmentType() {
        return enrollmentType;
    }

    public void setEnrollmentType(String enrollmentType) {
        this.enrollmentType = enrollmentType;
    }

    @Override
    public String toString() {
        return "ContractRecord{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", contractId=" + contractId +
                ", templateId=" + templateId +
                ", signerId=" + signerId +
                ", teacherName='" + teacherName + '\'' +
                ", ncId='" + ncId + '\'' +
                ", sex=" + sex +
                ", bdyx='" + bdyx + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                ", record='" + record + '\'' +
                ", zy='" + zy + '\'' +
                ", qq='" + qq + '\'' +
                ", emergencyPro='" + emergencyPro + '\'' +
                ", emergencyCall='" + emergencyCall + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", vbillCode='" + vbillCode + '\'' +
                ", className='" + className + '\'' +
                ", courseName='" + courseName + '\'' +
                ", payName='" + payName + '\'' +
                ", regdate='" + regdate + '\'' +
                ", dr=" + dr +
                ", createTime=" + createTime +
                ", ts=" + ts +
                ", regMoney=" + regMoney +
                ", idcardLocation=" + idcardLocation +
                ", mobileLocation=" + mobileLocation +
                ", productId=" + productId +
                ", contractDetail=" + contractDetail +
                ", syncTime=" + syncTime +
                ", saleTeacherName='" + saleTeacherName + '\'' +
                ", saleTeacherMobile='" + saleTeacherMobile + '\'' +
                ", bkZy='" + bkZy + '\'' +
                ", bkBdyx='" + bkBdyx + '\'' +
                ", bkClassId='" + bkClassId + '\'' +
                ", nation='" + nation + '\'' +
                ", currentRecord='" + currentRecord + '\'' +
                ", remark='" + remark + '\'' +
                ", graduateTime='" + graduateTime + '\'' +
                ", graduateSchool='" + graduateSchool + '\'' +
                ", major='" + major + '\'' +
                ", polity='" + polity + '\'' +
                ", residence='" + residence + '\'' +
                ", enrollmentType='" + enrollmentType + '\'' +
                ", logUpdateTime=" + logUpdateTime +
                ", bkProvinceName='" + bkProvinceName + '\'' +
                ", groupGoodStatus=" + groupGoodStatus +
                '}';
    }

    public Date getLogUpdateTime() {
        return logUpdateTime;
    }

    public void setLogUpdateTime(Date logUpdateTime) {
        this.logUpdateTime = logUpdateTime;
    }
}
