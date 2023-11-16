package com.crux.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询tag列表Dto")
public class TagListDto {

    /**
     * 标签名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
