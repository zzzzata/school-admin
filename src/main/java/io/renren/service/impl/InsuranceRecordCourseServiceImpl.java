package io.renren.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import io.renren.dao.InsuranceRecordCourseDao;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.entity.InsuranceRecordCourseEntity;
import io.renren.entity.InsuranceRecordEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.OrderMessageConsumerEntity;
import io.renren.pojo.MallGoodsDetailsPOJO;
import io.renren.service.InsuranceInfoService;
import io.renren.service.InsuranceRecordCourseService;
import io.renren.service.MallGoodsDetailsService;
import io.renren.utils.R;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service("insuranceRecordCourseService")
public class InsuranceRecordCourseServiceImpl implements InsuranceRecordCourseService {
@Autowired
private MallGoodsDetailsService mallGoodsDetailsService;

@Autowired
private InsuranceRecordCourseDao insuranceRecordCourseDao;


	@Override
	public void saveInsuranceRecordCourse(InsuranceRecordCourseEntity e) {
		Date ts= new Date();
		
		if (e.getSubjectHour()==null) {
			e.setSubjectHour(0.0);
		}
		if (e.getTs()==null) {
			e.setTs(ts);
		}
		if (e.getCreationTime()==null) {
			e.setCreationTime(ts);
		}
		
		
		insuranceRecordCourseDao.save(e);
	}

	@Override
	public void updateInsuranceRecordCourse(InsuranceRecordCourseEntity e) {
		Date ts= new Date();
		if (e.getSubjectHour()==null) {
			e.setSubjectHour(0.0);
		}
		if (e.getTs()==null) {
			e.setTs(ts);
		}
		if (e.getCreationTime()==null) {
			e.setCreationTime(ts);
		}
		insuranceRecordCourseDao.update(e);

	}

	@Override
	public List<InsuranceRecordCourseEntity> queryList(Map<String, Object> queryMap) {
		return insuranceRecordCourseDao.queryList(queryMap);
	}
	
	
	
	@Override
	public void updateDrByinsuranceRecordId(Long insuranceRecordId) {
	 
		 insuranceRecordCourseDao.updateDrByinsuranceRecordId(insuranceRecordId);
	}
	
	
	
	@Override
	public List<InsuranceRecordCourseEntity> queryGooodsCourse(Long areaId, Long goodsId,Long insuranceInfoId) {
	 
		return insuranceRecordCourseDao.queryGooodsCourse(areaId, goodsId, insuranceInfoId);
	}
	
	@Override
	public List<Map<String, Object>> countGooodsCourseByArea(Long areaId, Long goodsId,Long insuranceInfoId) {
	 
		return insuranceRecordCourseDao.countGooodsCourseByArea(areaId, goodsId, insuranceInfoId);
	}
	
	
	
	

	@Override
	public InsuranceRecordCourseEntity queryObject(Long id) {
		return insuranceRecordCourseDao.queryObject(id);
	}

	@Override
	public List<InsuranceRecordCourseEntity> InsuranceRecordCourseUpdateCheck(List<InsuranceRecordCourseEntity> detail,
			List<InsuranceRecordCourseEntity> courseDetail) {
		//1.如果没有旧课程的 直接返回新课程
		if (detail==null||detail.size()==0) {
			return courseDetail;
		} 
		 //2.遍历新课程 组成map
		Map<String,InsuranceRecordCourseEntity> courseDetailMap= new HashMap<String,InsuranceRecordCourseEntity>();
		if (courseDetail!=null&&courseDetail.size()>0) {
			for (InsuranceRecordCourseEntity d:courseDetail) {
				courseDetailMap.put(d.getSubjectCode(), d);
			}
			 
		}
		
		
		
		//3.遍历旧课程 不能在新中找到的 设置为dr=1
		for (int i=0;i<detail.size();i++) {
			if (courseDetailMap.size()==0||courseDetailMap.get(detail.get(i).getSubjectCode())==null) {
			 detail.get(i).setDr(1);
			}else {
			  detail.get(i).setDr(0);
			InsuranceRecordCourseEntity temp = courseDetailMap.get(detail.get(i).getSubjectCode());
				 detail.get(i).updateEntity(temp);
				 temp.setDr(0);
				 temp.setInsuranceRecordId(detail.get(i).getInsuranceRecordId());
				 courseDetailMap.put(temp.getSubjectCode(), temp);
			
			}
			 
		} 
		for (Entry<String, InsuranceRecordCourseEntity> m:courseDetailMap.entrySet()) {
			if (m.getValue()!=null&&m.getValue().getInsuranceRecordId()==null) {
				detail.add(m.getValue());
			}
		}
		
		
		
		
		 return detail;
		
	}
	@Override
	public R insuranceActionUpdate(   List<MallGoodsDetailsPOJO> mallGoodsDetailsList,int type) {
		boolean iscancel=false;
		if (type==0) {
			iscancel=true;
		}
		List<Long> cancelIds=new ArrayList<Long> ();//用于存取消的id
	    int maxCouseCount=18;
	    StringBuilder maxCountStr= new StringBuilder();
	    StringBuilder maxCountNowStr= new StringBuilder();
	    StringBuilder nullCountNowStr= new StringBuilder();
	    StringBuilder errorMessage= new StringBuilder();
		Map<Long,List<Long>> areaCount = new HashMap<Long,List<Long>>();
		
		 Long mallGoodsId=0L;
		 for (MallGoodsDetailsPOJO m :mallGoodsDetailsList) {
			if (mallGoodsId.intValue()==0&&m.getMallGoodsId()>0) {
				mallGoodsId=m.getMallGoodsId();
			}
			if (iscancel) {
				cancelIds.add(m.getId());
			}else {
				List<Long> ids= areaCount.get(m.getMallAreaId())==null? new ArrayList<Long>(): areaCount.get(m.getMallAreaId());
				ids.add(m.getId());
				areaCount.put(m.getMallAreaId(), ids);
			}
			
		 } 
		 
		 if (iscancel) {//如果是取消的则执行取消操作
			  if (cancelIds!=null&&cancelIds.size()>0) {
				  mallGoodsDetailsService.insuranceAction(0, cancelIds);
				  List<Map<String, Object>> detailList =  countGooodsCourseByArea(null, mallGoodsId, null);
				  if (detailList==null||detailList.size()==0) {
					  return R.error(500,"错误！没有取到商品中的课程信息！无法进行取消投保！");
				  }
				  for (Map<String, Object> dmap : detailList) {
						 if (dmap.get("areaId") != null && dmap.get("rs") != null ) {
							 int resultSize = dmap.get("rs") == null ? 0 : Integer.parseInt(dmap.get("rs").toString());
								if (resultSize == 0) {
									nullCountNowStr.append(dmap.get("areaName")).append("、");
								}
						 }
					 }
					 if (nullCountNowStr.length()>0) {
					 	  
					 		return R.error(500,"严重提醒 ：【"+nullCountNowStr.toString().substring(0,nullCountNowStr.toString().length()-1)+"】等省份还没有设置投保，请务必为这些省份勾选择投保课程" );
						  
					  }
				  
			  }
			  return R.ok(); //取消的跳出
		 }
		 
		 
		 List<Map<String, Object>> detailList =  countGooodsCourseByArea(null, mallGoodsId, null);		  
		 if (detailList==null||detailList.size()==0) {
			  return R.error(500,"错误！没有取到商品中的课程信息！无法进行投保！");
		  }
		 for (Map<String, Object> dmap : detailList) {
			// 先判断本次勾选择的
			if (dmap.get("areaId") != null && areaCount.get(dmap.get("areaId")) != null) {
				//resultSize：从库中取得的勾选的数量
				int resultSize = dmap.get("rs") == null ? 0 : Integer.parseInt(dmap.get("rs").toString()); 
				//ids：本次勾选择的数量
				List<Long> ids = areaCount.get(dmap.get("areaId"));
				if (ids != null && ids.size() > 0) {
					int courseSize = ids.size();
					if (resultSize >= maxCouseCount) {// 1.如果库里已经超过18次了这次还勾超过0次时
						maxCountStr.append(dmap.get("areaName")).append("、");
					} else if (courseSize + resultSize > maxCouseCount) {// 如果库里的勾选和本次勾选之和大于18次时
						maxCountNowStr.append(dmap.get("areaName")).append("、");
					} else {
						mallGoodsDetailsService.insuranceAction(1, ids);
					}

				}
			} else { // 本次没有勾选的，判断次数是否有至少一次
				int resultSize = dmap.get("rs") == null ? 0 : Integer.parseInt(dmap.get("rs").toString());
				if (resultSize == 0) {
					nullCountNowStr.append(dmap.get("areaName")).append("、");
				}
			}

		}
		  if (maxCountStr.length()>0||maxCountNowStr.length()>0||nullCountNowStr.length()>0) {
			  errorMessage.append(  maxCountStr.length()>0?"【"+maxCountStr.toString().substring(0,maxCountStr.toString().length()-1)+"】等省份之前已经勾满"+maxCouseCount+"次了，本次不允许再勾选择。":"");
			  errorMessage.append(  maxCountNowStr.length()>0?"【"+maxCountNowStr.toString().substring(0,maxCountNowStr.toString().length()-1)+"】等省份勾选超过"+maxCouseCount+"次了，请重新勾选择。":"");
			  errorMessage.append(  nullCountNowStr.length()>0?"严重提醒 ：【"+nullCountNowStr.toString().substring(0,nullCountNowStr.toString().length()-1)+"】等省份还没有设置投保，请务必为这些省份勾选择投保课程":"");
			  return R.error(500,errorMessage.toString());
			  
		  }
		  return R.ok(); 
		
	}
	
}
