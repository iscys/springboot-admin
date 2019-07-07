package com.project.service.driver.impl;


import com.project.mapper.admin.BannerMapper;
import com.project.mapper.admin.DriverHomeMapper;
import com.project.mapper.admin.SubjectMapper;
import com.project.mapper.admin.TeacherMapper;
import com.project.mapper.driver.UserMapper;
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
    @Autowired
    private MarkService mark;
    @Autowired
    private UserMapper userMapper;

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


         //驾校标签
         HashMap<String, String> cacheMarkList = mark.getCacheMarkList();
        String mark = schoolDetail.getMark();
        ArrayList<String> strL =new ArrayList<>();
         if(!StringUtils.isEmpty(mark)){
         String[] arr = mark.split(",");
         for(String m:arr){
         String cachMark= cacheMarkList.get(m);

         if(!StringUtils.isEmpty(cachMark)) {
         strL.add(cachMark);
         }

         }
         }
        schoolDetail.setTag(strL);

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

    /**
     * 驾校列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject driverList(PageData pd)throws Exception {
        String member_id = pd.getString("member_id");
        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Const.MEMBER_ERROR,Const.MEMBER_ERROR_MESSAGE,null);
        }


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
            su.setSubject_name("1");//科目二总课时套餐
            su.setSubject(StringUtils.isEmpty(userInfo.getSubject())?"C1":userInfo.getSubject());
            Subject subjectDetail = subjectMapper.getSubjectDetail(su);
            if(null !=subjectDetail){
                map.put("subject_name","科目二");
                map.put("subject",subjectDetail.getSubject());
                map.put("price",subjectDetail.getPrice());

            }

        }

        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("school_list",lists);

        return ResultObject.success(res);
    }

    /**
     * 驾校套餐列表,根据用户报考的驾照类型推荐出套餐
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject subjectList(PageData pd) throws Exception {
        String school_id = pd.getString("school_id");
        String member_id = pd.getString("member_id");
        if(StringUtils.isEmpty(school_id)){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        String subject = userInfo.getSubject();
        if(!StringUtils.isEmpty(subject)){
            pd.put("subject",subject);
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

    /**
     * 获取驾校教师全部列表信息
     * @param pd
     * @return
     * @throws Exception
     */
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
    /**
     * 获取教师详细信息
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject getTeacherDetail(Teacher teacher) throws Exception {

        Teacher te =teacherMapper.getTeacherDetail(teacher);
        return ResultObject.success(te);
    }

    /**
     * 保存学员对驾校的评论
     * @param fda
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject saveSchoolFeedback(FeedBackSchool fda)throws Exception {
        if(!StringUtils.isEmpty(fda.getTo_id())){
            if(StringUtils.isEmpty(fda.getTo_member_id())){
                return ResultObject.build(Const.TO_MEMBER_ID_NULL,Const.TO_MEMBER_ID_NULL_MESSAGE,null);
            }

        }


        if(!StringUtils.isEmpty(fda.getTo_member_id())){
            if(StringUtils.isEmpty(fda.getTo_id())){
                return ResultObject.build(Const.TO_ID_NULL,Const.TO_ID_NULL_MESSAGE,null);
            }

            fda.setType("2");//属于to-from之间的会话，不属于主评论的内容
        }else{
            String star = fda.getStar();
            if(StringUtils.isEmpty(star)){
                return ResultObject.build(Const.STAR_NULL,Const.STAR_NULL_MESSAGE,null);
            }

            Double int_star =Double.valueOf(star);
            if(int_star<=1<<1){
                fda.setLevel("1");
            }else if(int_star<=1<<2){
                fda.setLevel("2");
            }else{
                fda.setLevel("3");

            }
        }
        homeMapper.saveSchoolFeedBack(fda);
        return ResultObject.success(null);
    }

    /**
     * 驾校评论列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject getSchoolFeedbackList(PageData pd) throws Exception {
        String to_id = pd.getString("to_id");
        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }

        int pageSize = 10;
        int total =homeMapper.getSchoolFeedBackCount(pd);

        Page page =new Page(Integer.valueOf(pageNum),total,pageSize);
        int startIndex = page.getStartIndex();
        int pageSize1 = page.getPageSize();
        pd.put("startIndex",startIndex);
        pd.put("pageSize",pageSize1);
        List<HashMap<String,String>> lists;
        if(StringUtils.isEmpty(to_id)){
        lists =homeMapper.getSchoolFeedBackList(pd);
        }else{
            //to-from 之间的子评论
            lists= homeMapper.getSchoolSubFeedBackList(pd);
        }

        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("feedback_list",lists);

        return ResultObject.success(res);
    }

    /**
     * 保存学员对教练对评价
     * @param fda
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject saveTeacherFeedback(FeedBackTeacher fda) throws Exception {

        if(!StringUtils.isEmpty(fda.getTo_id())){
            if(StringUtils.isEmpty(fda.getTo_member_id())){
                return ResultObject.build(Const.TO_MEMBER_ID_NULL,Const.TO_MEMBER_ID_NULL_MESSAGE,null);
            }

        }

        if(!StringUtils.isEmpty(fda.getTo_member_id())){
            if(StringUtils.isEmpty(fda.getTo_id())){
                return ResultObject.build(Const.TO_ID_NULL,Const.TO_ID_NULL_MESSAGE,null);
            }

            fda.setType("2");//属于to-from之间的会话，不属于主评论的内容
        }else{
            String star = fda.getStar();
            if(StringUtils.isEmpty(star)){
                return ResultObject.build(Const.STAR_NULL,Const.STAR_NULL_MESSAGE,null);
            }
            Double int_star =Double.valueOf(star);
            if(int_star<=1<<1){
                fda.setLevel("1");
            }else if(int_star<=1<<2){
                fda.setLevel("2");
            }else{
                fda.setLevel("3");

            }
        }
        homeMapper.saveTeacherFeedBack(fda);
        return ResultObject.success(null);
    }

    /**
     * 教练评论列表
     * @param pd
     * @return
     * @throws Exception
     */

    @Override
    public ResultObject getTeacherFeedbackList(PageData pd) throws Exception {
        String to_id = pd.getString("to_id");
        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }

        int pageSize = 10;
        int total =homeMapper.getTeacherFeedBackCount(pd);

        Page page =new Page(Integer.valueOf(pageNum),total,pageSize);
        int startIndex = page.getStartIndex();
        int pageSize1 = page.getPageSize();
        pd.put("startIndex",startIndex);
        pd.put("pageSize",pageSize1);
        List<HashMap<String,String>> lists;
        if(StringUtils.isEmpty(to_id)){
            lists =homeMapper.getTeacherFeedBackList(pd);
        }else{
            //to-from 之间的子评论
            lists= homeMapper.getTeacherSubFeedBackList(pd);
        }

        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("feedback_list",lists);

        return ResultObject.success(res);




    }



}
