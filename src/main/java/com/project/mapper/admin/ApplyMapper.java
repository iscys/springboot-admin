package com.project.mapper.admin;

import com.project.model.school.Apply;

/**
 * 学员信息录入mapper
 */
public interface ApplyMapper {

    void saveApply(Apply apply);

    Apply getDeatilApply(Apply apply);
}
