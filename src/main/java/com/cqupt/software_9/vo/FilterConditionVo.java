package com.cqupt.software_9.vo;

import com.cqupt.software_9.entity.FilterDataCol;
import com.cqupt.software_9.entity.FilterDataInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterConditionVo {
    private FilterDataInfo filterDataInfo;
    private List<FilterDataCol> filterDataCols;
}
