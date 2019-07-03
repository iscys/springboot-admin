package com.project.service.driver.impl;


import com.project.mapper.admin.BannerMapper;
import com.project.mapper.admin.DriverHomeMapper;
import com.project.mapper.admin.SubjectMapper;
import com.project.mapper.admin.TeacherMapper;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.*;
import com.project.service.admin.MarkService;
import com.project.service.driver.SchoolService;
import com.project.utils.Page;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DriverHomeMapper homeMapper;
    @Autowired
    private MarkService markService;
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 查看驾校具体信息
     * @param schoolModel
     * @return
     * @throws Exception
     */

    @Override
    public ResultObject getSchoolDetail(SchoolModel schoolModel) throws Exception {

        SchoolModel schoolDetail = homeMapper.getSchoolDetail(schoolModel);

        schoolDetail.setStar("5");
        return ResultObject.success(schoolDetail);
    }

    @Override
    public ResultObject getSchoolAlbum(SchoolModel schoolModel) throws Exception {
        Album album =new Album();
        album.setSchool_id(schoolModel.getId());
        List<Album> albums=homeMapper.getSchoolAlbum(album);
        return ResultObject.success(albums);
    }

    @Override
    public ResultObject index(PageData pd) throws Exception {

        Banner banner =new Banner();
        banner.setType("0");
        List<Banner> carousel=bannerMapper.getAllBanner(banner);
        banner =new Banner();
        banner.setType("1");
        //广告位图片
        List<Banner> ad_bannerList=bannerMapper.getAllBanner(banner);
        banner=null;
        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("carousel",carousel);
        res.put("ad",ad_bannerList);


        return ResultObject.success(res);
    }

    @Override
    public ResultObject driverList(PageData pd)throws Exception {
        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }
        int pageSize = Const.DEFAULT_PAGESIZE;
        int total =homeMapper.getHomeCount(pd);

        Page page =new Page(Integer.valueOf(pageNum),total,pageSize);
        int startIndex = page.getStartIndex();
        int pageSize1 = page.getPageSize();
        pd.put("startIndex",startIndex);
        pd.put("pageSize",pageSize1);
        List<HashMap<String,Object>> lists =homeMapper.getHomeList(pd);


        for(HashMap<String,Object> map :lists){

            //驾校科目
            String id = map.get("id").toString();
            Subject su =new Subject();
            su.setSchool_id(id);
            su.setSubject_name("1");
            su.setSubject("C1");
            Subject subjectDetail = subjectMapper.getSubjectDetail(su);
            if(null !=subjectDetail){
                map.put("subject_name","科目二");
                map.put("subject",subjectDetail.getSubject());
                map.put("price",subjectDetail.getPrice());

            }

            //驾校标签
            Object mark=map.get("mark");
            List<String> strL =new ArrayList<>();
            if(null !=mark){
                String marks = mark.toString();
                String[] arr = marks.split(",");
                for(String m:arr){
                    Mark mark1 =new Mark();
                    mark1.setId(m);
                    Mark markDetail = markService.getMarkDetail(mark1);
                    mark1=null;
                    if(null !=markDetail) {
                        strL.add(markDetail.getMark());
                    }

                }
            }
            map.put("tag",strL);
        }
        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("school_list",lists);

        return ResultObject.success(res);
    }

    /**
     * 驾校套餐列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject subjectList(PageData pd) throws Exception {
        String school_id = pd.getString("school_id");
        if(StringUtils.isEmpty(school_id)){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }

        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }

        int pageSize = Const.DEFAULT_PAGESIZE;
        int total =subjectMapper.getSubjectCount(pd);

        Page page =new Page(Integer.valueOf(pageNum),total,pageSize);
        int startIndex = page.getStartIndex();
        int pageSize1 = page.getPageSize();
        pd.put("startIndex",startIndex);
        pd.put("pageSize",pageSize1);
        List<HashMap<String,String>> lists =subjectMapper.getSubjectList(pd);
        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("subject_list",lists);

        return ResultObject.success(res);
    }

    @Override
    public ResultObject teacherList(PageData pd) throws Exception {
        String school_id = pd.getString("school_id");
        if(StringUtils.isEmpty(school_id)){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }

        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }

        int pageSize = Const.DEFAULT_PAGESIZE;
        int total =teacherMapper.getTeacherCount(pd);

        Page page =new Page(Integer.valueOf(pageNum),total,pageSize);
        int startIndex = page.getStartIndex();
        int pageSize1 = page.getPageSize();
        pd.put("startIndex",startIndex);
        pd.put("pageSize",pageSize1);
        List<HashMap<String,String>> lists =teacherMapper.getTeacherList(pd);
        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("teacher_list",lists);

        return ResultObject.success(res);
    }

    @Override
    public ResultObject getTeacherDetail(Teacher teacher) throws Exception {

        Teacher te =teacherMapper.getTeacherDetail(teacher);
        return ResultObject.success(te);
    }
}
