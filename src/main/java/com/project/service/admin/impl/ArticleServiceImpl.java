package com.project.service.admin.impl;

import com.project.config.ConfigProperties;
import com.project.mapper.admin.ArticleMapper;
import com.project.model.ResultObject;
import com.project.model.school.Article;
import com.project.model.school.Subject;
import com.project.service.admin.ArticleService;
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
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ConfigProperties properties;
    @Autowired
    private ArticleMapper articleMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public DataPager getArticleList(PageData pd) {
        Integer total = articleMapper.getArticleCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= articleMapper.getArticleList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public Article getArticleDetail(Article article) {
        return articleMapper.getArticleDetail(article);
    }

    @Override
    public ResultObject saveArticle(MultipartFile thumb,PageData pd) throws Exception {

        String destDir = properties.getDestdir();
        if(null!=thumb){
            //保存教练图片信息
            InputStream icon = thumb.getInputStream();
            String icon_suffix= ToolsUtils.getFileSuffix(thumb.getResource().getFilename());
            String icon_dir="/thumb/";
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

            IOUtils.closeQuietly(icon,iconOutputStream);
            pd.put("thumb",properties.getImgUrl()+icon_dir+icon_uuid+"."+icon_suffix);
        }
        if(null==pd.get("id")) {
            articleMapper.saveArticle(pd);
        }else{
            articleMapper.updateArticle(pd);
        }
        return ResultObject.success(null);
    }
}
