package io.renren.pojo;


import io.renren.entity.CourseClassplanLivesEntity;

public class CourseClassplanLivesPOJO extends CourseClassplanLivesEntity {
	private String studioName;
	private String liveRoomName;
	private String genseeLiveId;
	private String genseeLiveNum;
	private String teacherName;
	private String liveClassTypeNames;

	public String getStudioName() {
		return studioName;
	}
	public void setStudioName(String studioName) {
		this.studioName = studioName;
	}
	public String getLiveRoomName() {
		return liveRoomName;
	}
	public void setLiveRoomName(String liveRoomName) {
		this.liveRoomName = liveRoomName;
	}
	public String getGenseeLiveId() {
		return genseeLiveId;
	}
	public void setGenseeLiveId(String genseeLiveId) {
		this.genseeLiveId = genseeLiveId;
	}
	public String getGenseeLiveNum() {
		return genseeLiveNum;
	}
	public void setGenseeLiveNum(String genseeLiveNum) {
		this.genseeLiveNum = genseeLiveNum;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getLiveClassTypeNames() {
		return liveClassTypeNames;
	}
	public void setLiveClassTypeNames(String liveClassTypeNames) {
		this.liveClassTypeNames = liveClassTypeNames;
	}
}