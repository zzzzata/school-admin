package io.renren.pojo;

public class AttendRecordViewPOJO {
    private Long userId;
    private String userName;
    private String mobile;
    private String classtypeName;
    private String courseName;
    private String videoName;
    private String className;
    private String teacherName;
    private String recordPerTxt;
    private Long recordId;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClasstypeName() {
        return classtypeName;
    }

    public void setClasstypeName(String classtypeName) {
        this.classtypeName = classtypeName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRecordPerTxt() {
        return recordPerTxt;
    }

    public void setRecordPerTxt(String recordPerTxt) {
        this.recordPerTxt = recordPerTxt;
    }

	@Override
	public String toString() {
		return "AttendRecordViewPOJO [userId=" + userId + ", userName=" + userName + ", mobile=" + mobile
				+ ", classtypeName=" + classtypeName + ", courseName=" + courseName + ", videoName=" + videoName
				+ ", className=" + className + ", teacherName=" + teacherName + ", recordPerTxt=" + recordPerTxt
				+ ", recordId=" + recordId + "]";
	}
    
}
