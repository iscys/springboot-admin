package com.project.service.admin.impl;


import com.project.config.ConfigProperties;
import com.project.mapper.admin.BannerMapper;
import com.project.model.ResultObject;
import com.project.model.school.Banner;
import com.project.model.school.Mark;
import com.project.service.admin.BannerService;
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
public class BannerServiceImpl implements BannerService{
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private ConfigProperties properties;

    @Override
    public DataPager getBannerList(PageData pd) {
        Integer total = bannerMapper.getBannerCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= bannerMapper.getBannerList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public Banner getBannerDetail(Banner banner) {
        return bannerMapper.getBannerDetail(banner);
    }

    @Override
    public ResultObject saveBanner(MultipartFile banner,PageData pd) throws Exception {
        String destDir = properties.getDestdir();
        if(null!=banner){
            //保存教练图片信息
            InputStream icon = banner.getInputStream();
            String icon_suffix= ToolsUtils.getFileSuffix(banner.getResource().getFilename());
            String icon_dir="/banner/";
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
            pd.put("banner",properties.getImgUrl()+icon_dir+icon_uuid+"."+icon_suffix);
        }

        if(null ==pd.get("id")) {
            bannerMapper.saveBanner(pd);
        }else{
            bannerMapper.updateBanner(pd);
        }
        return ResultObject.success(null);
    }
}