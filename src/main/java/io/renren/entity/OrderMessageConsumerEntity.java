package io.renren.entity;

import io.renren.entity.manage.StudentCourse;

import java.util.Date;
import java.util.List;

public class OrderMessageConsumerEntity {

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
     *是否购买保险(Y是)
     */
    private String isInsurant;

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

    /**
     * 业务线
     */
    private String company_type;
    /**
     * 报名表号(编号)
     */
    private String code;
    /**
     * 学校编号
     */
    private String school_code;
    /**
     * 用户名字
     */
    private String user_name;
    /**
     * 对应部门表的nc_id
     */
    private String nc_school_pk;
    /**
     * 班级Id
     */
    private String class_id;

    /**
     *
     */
    private String predict_open_time;
    /**
     *
     */
    private Integer dr;
    /**
     * 判断同步订单是否来自自考用的(我也不知道是什么鬼)
     */
    private String class_type_id;
    /**
     * 对应学员表的nc_id
     */
    private String nc_user_id;
    /**
     * 对应省份表的nc_id
     */
    private String province_pk;
    /**
     *
     */
    private Integer sign_status;
    /**
     * 0：删除订单 1：同步订单
     */
    private Integer spec_status;
    /**
     * 对应订单表的nc_id
     */
    private String nc_id;
    /**
     * 班级名称
     */
    private String class_name;
    /**
     * 创建时间
     */
    private Long create_time;
    /**
     *
     */
    private String ks_name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 学校名称
     */
    private String school_name;
    /**
     * 省份名称
     */
    private String province_name;
    /**
     * 产品线
     */
    private Long product_type;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 同步时间
     */
    private String syn_time;
    /**
     * 对应商品表的nc_id
     */
    private String nc_commodity_id;
    /**
     * 对应省份表的nc_id
     */
    private String zk_province_pk;
    /**
     * 推送时间
     */
    private String push_time;
    /**
     *
     */
    private String classtime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * ts 订单支付时间
     */
    private Long ts;
    /**
     * 订单号
     */
    private String order_no;

    //nc审核状态,-1审核关闭,1取消审核恢复
    private Integer vbill_status;

    /**
     * 在线协议记录主表
     */
    private ContractRecord contractHead;
    /**
     * 在线协议记录子表
     */
    private List<ContractDetail> contractBody;

    /**
     * 学员报读班型科目子表
     */
    private List<StudentCourse> studentCourses;

    /**
     * nc商品类型
     */
    private Integer item_type;

    private String zkClassName;
    private String record;
    private Integer recordCode;
    private String bdyx;
    private String idCard;
    private String zy;
    private String zyId;
    private String qq;
    private String registdate;
    private RecordReFundsEntity recordRefunds;

    //在线协议的公司名称
    private String contractCompanyName;

    //考期
    private String examDate;

    //原来的报名表主键
    private String sourceNcid;

    //原来的班型
    private String sourceClassId;
    //报名表金额
    private String regMoney;
    //保险金额
    private String insuranceCost;
    //退单来源   0 .无 1.nc删除订单 2.NC退费订单 3.贷款过期
    private Integer sourceType = 0;
    //手动推送的类型   3：投保直接变更的
    private String handType = "0";

    // 招生老师
    private String saleTeacherName;
    // 招生老师电话
    private String saleTeacherMobile;
    // 从Nc过来的userid id
    private Long userId;
    // 是否交齐款
    private String paystatus;
    // 是否贷款
    private String isloans;
    //备注
    private String remark;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getZyId() {
        return zyId;
    }

    public void setZyId(String zyId) {
        this.zyId = zyId;
    }

    public Integer getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(Integer recordCode) {
        this.recordCode = recordCode;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getIsInsurant() {
        return isInsurant;
    }

    public void setIsInsurant(String isInsurant) {
        this.isInsurant = isInsurant;
    }

    public String getSourceNcid() {
        return sourceNcid;
    }

    public void setSourceNcid(String sourceNcid) {
        this.sourceNcid = sourceNcid;
    }

    public String getSourceClassId() {
        return sourceClassId;
    }

    public void setSourceClassId(String sourceClassId) {
        this.sourceClassId = sourceClassId;
    }

    public Integer getItem_type() {
        return item_type;
    }

    public void setItem_type(Integer item_type) {
        this.item_type = item_type;
    }

    public Integer getVbill_status() {
        return vbill_status;
    }

    public void setVbill_status(Integer vbill_status) {
        this.vbill_status = vbill_status;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNc_school_pk() {
        return nc_school_pk;
    }

    public void setNc_school_pk(String nc_school_pk) {
        this.nc_school_pk = nc_school_pk;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getPredict_open_time() {
        return predict_open_time;
    }

    public void setPredict_open_time(String predict_open_time) {
        this.predict_open_time = predict_open_time;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public String getClass_type_id() {
        return class_type_id;
    }

    public void setClass_type_id(String class_type_id) {
        this.class_type_id = class_type_id;
    }

    public String getNc_user_id() {
        return nc_user_id;
    }

    public void setNc_user_id(String nc_user_id) {
        this.nc_user_id = nc_user_id;
    }

    public String getProvince_pk() {
        return province_pk;
    }

    public void setProvince_pk(String province_pk) {
        this.province_pk = province_pk;
    }

    public Integer getSign_status() {
        return sign_status;
    }

    public void setSign_status(Integer sign_status) {
        this.sign_status = sign_status;
    }

    public Integer getSpec_status() {
        return spec_status;
    }

    public void setSpec_status(Integer spec_status) {
        this.spec_status = spec_status;
    }

    public String getNc_id() {
        return nc_id;
    }

    public void setNc_id(String nc_id) {
        this.nc_id = nc_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public String getKs_name() {
        return ks_name;
    }

    public void setKs_name(String ks_name) {
        this.ks_name = ks_name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSyn_time() {
        return syn_time;
    }

    public void setSyn_time(String syn_time) {
        this.syn_time = syn_time;
    }

    public String getNc_commodity_id() {
        return nc_commodity_id;
    }

    public void setNc_commodity_id(String nc_commodity_id) {
        this.nc_commodity_id = nc_commodity_id;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClasstime() {
        return classtime;
    }

    public void setClasstime(String classtime) {
        this.classtime = classtime;
    }

    public String getZk_province_pk() {
        return zk_province_pk;
    }

    public void setZk_province_pk(String zk_province_pk) {
        this.zk_province_pk = zk_province_pk;
    }

    public Long getProduct_type() {
        return product_type;
    }

    public void setProduct_type(Long product_type) {
        this.product_type = product_type;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }


    public ContractRecord getContractHead() {
        return contractHead;
    }

    public void setContractHead(ContractRecord contractHead) {
        this.contractHead = contractHead;
    }

    public List<ContractDetail> getContractBody() {
        return contractBody;
    }

    public void setContractBody(List<ContractDetail> contractBody) {
        this.contractBody = contractBody;
    }

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public String getZkClassName() {
        return zkClassName;
    }

    public void setZkClassName(String zkClassName) {
        this.zkClassName = zkClassName;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
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

    public String getidCard() {
        return idCard;
    }

    public void setidCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "OrderMessageConsumerEntity{" +
                "company_type='" + company_type + '\'' +
                ", code='" + code + '\'' +
                ", school_code='" + school_code + '\'' +
                ", user_name='" + user_name + '\'' +
                ", nc_school_pk='" + nc_school_pk + '\'' +
                ", class_id='" + class_id + '\'' +
                ", classtype='" + classtype + '\'' +
                ", predict_open_time='" + predict_open_time + '\'' +
                ", dr=" + dr +
                ", class_type_id='" + class_type_id + '\'' +
                ", nc_user_id='" + nc_user_id + '\'' +
                ", province_pk='" + province_pk + '\'' +
                ", sign_status=" + sign_status +
                ", spec_status=" + spec_status +
                ", nc_id='" + nc_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", create_time=" + create_time +
                ", ks_name='" + ks_name + '\'' +
                ", sex=" + sex +
                ", school_name='" + school_name + '\'' +
                ", province_name='" + province_name + '\'' +
                ", product_type=" + product_type +
                ", phone='" + phone + '\'' +
                ", syn_time='" + syn_time + '\'' +
                ", nc_commodity_id='" + nc_commodity_id + '\'' +
                ", zk_province_pk='" + zk_province_pk + '\'' +
                ", push_time='" + push_time + '\'' +
                ", classtime='" + classtime + '\'' +
                ", status=" + status +
                ", ts=" + ts +
                ", order_no='" + order_no + '\'' +
                ", vbillstatus=" + vbill_status +
                ",registdate='" + registdate + "'"
                + ", contractHead='" + contractHead + "',recordRefunds='" + recordRefunds + "', contractBody='" + contractBody + "', zkClassName='" + zkClassName
                + "', record='" + record + "', bdyx='" + bdyx + "', zy='" + zy + "', qq='" + qq + "'"
                + "', idCard='" + idCard + "'"
                + "', contractCompanyName='" + contractCompanyName + "'"
                + '}';
    }

    public RecordReFundsEntity getRecordRefunds() {
        return recordRefunds;
    }

    public void setRecordRefunds(RecordReFundsEntity recordRefunds) {
        this.recordRefunds = recordRefunds;
    }

    public String getRegistdate() {
        return registdate;
    }

    public void setRegistdate(String registdate) {
        this.registdate = registdate;
    }

    public String getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(String regMoney) {
        this.regMoney = regMoney;
    }

    public String getContractCompanyName() {
        return contractCompanyName;
    }

    public void setContractCompanyName(String contractCompanyName) {
        this.contractCompanyName = contractCompanyName;
    }

    public String getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }


    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getHandType() {
        return handType;
    }

    public void setHandType(String handType) {
        this.handType = handType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;

    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getIsloans() {
        return isloans;
    }

    public void setIsloans(String isloans) {
        this.isloans = isloans;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 取得Nc订单中的一些关键信息（报名表号，ncid,phone,paystatus,syncTime
     *
     * @return
     */
    public String getSignTag() {


        return "code:" + this.code + ",ncId:" + this.nc_id + ",phone:" + this.phone + ",classId:" + this.class_id + ",paystatus:" + this.paystatus + ",syncTime:" + this.syn_time + ".";

    }
}
