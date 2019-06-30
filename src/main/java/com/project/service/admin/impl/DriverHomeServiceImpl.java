package com.project.service.admin.impl;

import com.project.config.ConfigProperties;
import com.project.mapper.admin.DriverHomeMapper;
import com.project.model.school.Album;
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
        List<HashMap<String,Object>> result= homeMapper.getHomeList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public SchoolModel getSchoolDetail(SchoolModel schoolModel) {
       SchoolModel model= homeMapper.getSchoolDetail(schoolModel);
        return model;
    }

    @Override
    public PageData save(PageData pd, MultipartFile school_icon, MultipartFile school_face, MultipartFile[] files) throws Exception{
        String destDir = properties.getDestdir();
        if(null!=school_icon){
        //保存驾校信息
        InputStream icon = school_icon.getInputStream();
        String icon_suffix=ToolsUtils.getFileSuffix(school_icon.getResource().getFilename());
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
        }

        if(null!=school_face) {
            String face_dir = "/faces/";
            destDir += face_dir;
            String face_uuid = ToolsUtils.idGenerate();
            File destFaceFile = new File(destDir);
            if (!destFaceFile.exists()) {
                boolean flag = destFaceFile.mkdirs();
                if (!flag) {
                    logger.error("创建文件夹：{}失败", destFaceFile);
                }
            }
            InputStream face = school_face.getInputStream();
            String face_suffix = ToolsUtils.getFileSuffix(school_face.getResource().getFilename());
            FileOutputStream faceOutputStream = new FileOutputStream(destDir + face_uuid + "." + face_suffix);
            IOUtils.copy(face, faceOutputStream);
            face.close();
            faceOutputStream.close();
            pd.put("school_face", properties.getImgUrl() + face_dir + face_uuid + "." + face_suffix);
        }
        if(null==pd.get("id")) {
            homeMapper.save(pd);
        }else{
            //todo update
            homeMapper.updateShool(pd);
        }


        String album_dir="/album/";
        String albums=properties.getDestdir()+album_dir;
        File album_file =new File(albums);
        if(!album_file.exists()){
            boolean flag= album_file.mkdirs();
            if(!flag){
                logger.error("创建文件夹：{}失败",album_file);
            }
        }

        String ids=pd.get("id").toString();

        for(int i=0;i<files.length;i++){
            MultipartFile file=files[i];
            Album album =new Album();
            album.setSchool_id(ids);
            album.setOrders(i);
            InputStream album_input = file.getInputStream();
            String album_suffix=ToolsUtils.getFileSuffix(file.getResource().getFilename());
            String album_uuid=ToolsUtils.idGenerate();
            FileOutputStream albumOutputStream =new FileOutputStream(albums+album_uuid+"."+album_suffix);
            IOUtils.copy(album_input,albumOutputStream);
            album_input.close();
            albumOutputStream.close();
            album.setImg_url(properties.getImgUrl()+album_dir+album_uuid+"."+album_suffix);
            homeMapper.saveSchoolAlbum(album);

        }
        return pd;
    }
}
