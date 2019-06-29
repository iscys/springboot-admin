package com.project.controller.admin.dirverHome;

import com.project.config.ConfigProperties;
import com.project.controller.BaseController;
import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.service.admin.DriverHomeService;
import com.project.utils.DataPager;
import com.project.utils.GsonUtils;
import com.project.utils.PageData;
import com.project.utils.ToolsUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 驾校列表以及配置
 * @author iscys
 * qq:1074171884
 */
@Controller
@RequestMapping("/home")
public class DriverHomeController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DriverHomeService homeService;
    @Autowired
    private ConfigProperties properties;

    @RequestMapping("/index")
    public ModelAndView homeList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= homeService.getHomeList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/school/school_list");
        return mv;
    }

    @RequestMapping("/to_add")
    public ModelAndView addSchool(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/school/school_add");
        return mv;
    }

    @RequestMapping("/to_update")
    public ModelAndView to_update(SchoolModel school){
        ModelAndView mv = this.getModelAndView();
        //PageData pd = this.getPageData();
        SchoolModel schoolModel=homeService.getSchoolDetail(school);
        mv.addObject("pd",schoolModel);
        mv.setViewName("page/school/school_update");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(MultipartFile school_icon, MultipartFile school_face, MultipartFile[] files){
        PageData pd = this.getPageData();
        try {
            PageData result = homeService.save(pd,school_icon,school_face,files);

            return ResultObject.success(null);
        }catch (Exception e){
            return ResultObject.error(null);
        }
    }
    /**
     * kindeditor 图片上传
     */
    @PostMapping("/img")
    @ResponseBody
    public String save(MultipartFile imgFile){
        FileOutputStream kindOutputStream =null;
        InputStream kind =null;

            try {
                String destDir = properties.getDestdir();
                //保存驾校信息
                kind = imgFile.getInputStream();
                String kind_suffix = ToolsUtils.getFileSuffix(imgFile.getResource().getFilename());
                String kind_dir = "/kindeditor/";
                String kind_dirs = destDir + kind_dir;
                String kind_uuid = ToolsUtils.idGenerate();
                File destFile = new File(kind_dirs);
                if (!destFile.exists()) {
                    boolean flag = destFile.mkdirs();
                    if (!flag) {
                        logger.error("创建文件夹：{}失败", destDir);
                    }
                }

                kindOutputStream = new FileOutputStream(kind_dirs + kind_uuid + "." + kind_suffix);
                IOUtils.copy(kind, kindOutputStream);
                String url = properties.getImgUrl() + kind_dir + kind_uuid + "." + kind_suffix;
                HashMap<String, Object> map = new HashMap<>();
                map.put("error", 0);
                map.put("url", url);
                return GsonUtils.toJson(map);
            }catch (Exception e){
                HashMap<String, Object> map = new HashMap<>();
                map.put("error", 1);
                map.put("message", "图片上传异常");
                return GsonUtils.toJson(map);
            }finally {
                try {
                    if (kind != null)
                        kind.close();
                    if (kindOutputStream != null)
                        kindOutputStream.close();
                }catch (Exception e){
                    logger.error("流关闭异常");
                }
            }

    }

}
