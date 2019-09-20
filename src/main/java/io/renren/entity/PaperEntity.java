package io.renren.entity;

public class PaperEntity {
	private Integer paperId;
	private String paperName;
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	@Override
	public String toString() {
		return "PaperEntity [paperId=" + paperId + ", paperName=" + paperName + "]";
	}
	
}
