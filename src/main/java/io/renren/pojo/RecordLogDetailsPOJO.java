package io.renren.pojo;

public class RecordLogDetailsPOJO {
	//用户ID
	private Long userId;
	//用户名
	private String userName;
	//手机号
	private String mobile;
	//录播课章节名称
	private String recordName;
	//视频名称
	private String videoName;
	//观看时长
	private Long playDuration;
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
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public Long getPlayDuration() {
		return playDuration;
	}
	public void setPlayDuration(Long playDuration) {
		this.playDuration = playDuration;
	}
	@Override
	public String toString() {
		return "RecordLogDetailsPOJO [userId=" + userId + ", userName=" + userName + ", mobile=" + mobile
				+ ", recordName=" + recordName + ", videoName=" + videoName + ", playDuration=" + playDuration + "]";
	}
	
}
