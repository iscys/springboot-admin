package com.project.service.admin.impl;


import com.project.config.ConfigProperties;
import com.project.mapper.admin.DriverHomeMapper;
import com.project.mapper.admin.TeacherMapper;
import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.model.school.Teacher;
import com.project.service.admin.TeacherService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import com.project.utils.ToolsUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private DriverHomeMapper homeMapper;
    @Autowired
    private ConfigProperties properties;


    @Override
    public DataPager getTeacherList(PageData pd) {
        Integer total = teacherMapper.getTeacherCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= teacherMapper.getTeacherList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public List<SchoolModel> getAllSchoolList(PageData pd) {

        return homeMapper.getAllSchoolList(pd);
    }

    /**
     * 保存教练信息
     * @param teacher_img
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject saveTeacher(MultipartFile teacher_img, PageData pd) throws Exception {

        String destDir = properties.getDestdir();
        if(null!=teacher_img){
            //保存教练图片信息
            InputStream icon = teacher_img.getInputStream();
            String icon_suffix= ToolsUtils.getFileSuffix(teacher_img.getResource().getFilename());
            String icon_dir="/teacher/";
            String icon_dirs=destDir+icon_dir;
            String icon_uuid=ToolsUtils.idGenerate();
            File destFile =new File(icon_dirs);
            if(!destFile.exists()){
                boolean flag= destFile.mkdirs();
                if(!flag){
                    logger.error("创建文件夹：{}失败",destDir);
                }
            }

            FileOutputStream iconOutputStream =new FileOutputStream(icon_dirs+icon_uuid+"."+icon_suffix);
            IOUtils.copy(icon,iconOutputStream);
            icon.close();
            iconOutputStream.close();
            pd.put("teacher_img",properties.getImgUrl()+icon_dir+icon_uuid+"."+icon_suffix);
        }
        if(null==pd.get("id")) {
            teacherMapper.saveTeacher(pd);
        }else{
            teacherMapper.updateTeacher(pd);
        }
        return ResultObject.success(null);
    }

    @Override
    public Teacher getTeacherDetail(Teacher teacher) {

        return teacherMapper.getTeacherDetail(teacher);
    }
}
