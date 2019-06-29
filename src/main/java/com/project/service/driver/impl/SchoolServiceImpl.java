package com.project.service.driver.impl;


import com.project.mapper.admin.DriverHomeMapper;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.Album;
import com.project.model.school.SchoolModel;
import com.project.service.driver.SchoolService;
import com.project.utils.Page;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
    
    @Autowired
    private DriverHomeMapper homeMapper;

    /**
     * 查看驾校具体信息
     * @param schoolModel
     * @return
     * @throws Exception
     */

    @Override
    public ResultObject getSchoolDetail(SchoolModel schoolModel) throws Exception {

        SchoolModel schoolDetail = homeMapper.getSchoolDetail(schoolModel);


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
        List<HashMap<String,String>> lists =homeMapper.getHomeList(pd);
        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",Integer.valueOf(pageNum));//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("school_list",lists);
        return ResultObject.success(res);
    }
}
