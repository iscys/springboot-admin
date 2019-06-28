package com.project.service.admin.impl;

import com.project.config.ConfigProperties;
import com.project.mapper.admin.DriverHomeMapper;
import com.project.model.school.SchoolModel;
import com.project.service.admin.DriverHomeService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import com.project.utils.ToolsUtils;
import org.apache.commons.io.IOUtils;
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
public class DriverHomeServiceImpl implements DriverHomeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DriverHomeMapper homeMapper;
    @Autowired
    private ConfigProperties properties;

    /**
     * 驾校列表信息
     * @param pd
     * @return
     */
    @Override
    public DataPager getHomeList(PageData pd) {
        Integer total =homeMapper.getHomeCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= homeMapper.getHomeList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public SchoolModel getSchoolDetail(PageData pd) {
       SchoolModel model= homeMapper.getSchoolDetail(pd);
        return model;
    }

    @Override
    public PageData save(PageData pd, MultipartFile school_icon, MultipartFile school_face, MultipartFile[] files) throws Exception{
        //保存驾校信息
        InputStream icon = school_icon.getInputStream();
        InputStream face = school_face.getInputStream();
        String icon_suffix=ToolsUtils.getFileSuffix(school_icon.getResource().getFilename());
        String face_suffix=ToolsUtils.getFileSuffix(school_face.getResource().getFilename());
        String destDir = properties.getDestdir();
        String icon_dir="/icons/";
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
        pd.put("school_icon",properties.getImgUrl()+icon_dir+icon_uuid+"."+icon_suffix);


        String face_dir="/faces/";
        destDir+=face_dir;
        String face_uuid=ToolsUtils.idGenerate();
        File destFaceFile =new File(destDir);
        if(!destFaceFile.exists()){
            boolean flag= destFaceFile.mkdirs();
            if(!flag){
                logger.error("创建文件夹：{}失败",destDir);
            }
        }

        FileOutputStream faceOutputStream =new FileOutputStream(destDir+icon_uuid+"."+icon_suffix);
        IOUtils.copy(face,faceOutputStream);
        face.close();
        faceOutputStream.close();
        pd.put("school_face",properties.getImgUrl()+face_dir+face_uuid+"."+face_suffix);
        homeMapper.save(pd);
        return pd;
    }
}
