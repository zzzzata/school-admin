package io.renren.dao.manage;

import io.renren.entity.manage.NcCourse;
import io.renren.entity.manage.NcCourseExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface NcCourseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int countByExample(NcCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int deleteByExample(NcCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int deleteByPrimaryKey(Integer courseid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int insert(NcCourse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int insertSelective(NcCourse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    List<NcCourse> selectByExampleWithRowbounds(NcCourseExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    List<NcCourse> selectByExample(NcCourseExample example);

    NcCourse selectByExampleFetchOne(NcCourseExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    NcCourse selectByPrimaryKey(Integer courseid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int updateByExampleSelective(@Param("record") NcCourse record, @Param("example") NcCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int updateByExample(@Param("record") NcCourse record, @Param("example") NcCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int updateByPrimaryKeySelective(NcCourse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nc_course
     *
     * @mbggenerated Thu Aug 10 14:22:20 CST 2017
     */
    int updateByPrimaryKey(NcCourse record);
}