package com.cqupt.software_9.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.FeatureMatch;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.mapper.CategoryMapper;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.mapper.TableDataMapper;
import com.cqupt.software_9.service.*;
import com.cqupt.software_9.vo.FilterTableDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// TODO 公共模块新增类

@RestController()
@RequestMapping("/TableData")
public class TableDataController {

    @Autowired
    TableDataService tableDataService;
    @Autowired
    UserService userService;

    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryMapper categoryMapper;
    @Autowired
    TableDescribeService tableDescribeService;

    @Resource
    private TableDataMapper tableDataMapper;

    @Resource
    private DataManagerMapper dataManagerMapper;
    @Resource
    private StasticOneService stasticOneService;

    @GetMapping("/getTableData")
    public Result getTableData(@RequestParam("tableId") String tableId, @RequestParam("tableName") String tableName){
        System.out.println("tableId=="+tableId+"   tableName=="+tableName);
        List<LinkedHashMap<String, Object>> tableData = tableDataService.getTableData(tableId, tableName);
        return Result.success("100",tableData);
    }

    // 文件上传
    @PostMapping("/dataTable/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("newName") String tableName,
                             @RequestParam("disease") String type,
                             @RequestParam("user") String user,
                             @RequestParam("uid") int userId,
                             @RequestParam("parentId") String parentId,
                             @RequestParam("parentType") String parentType){
        // 保存表数据信息
        try {
            List<String> featureList = tableDataService.uploadFile(file, tableName, type, user, userId, parentId, parentType);
            return Result.success("200",featureList); // 返回表头信息
        }catch (Exception e){
            e.printStackTrace();
            return Result.success(500,"文件上传异常");
        }
    }

    // 检查上传文件是数据文件的表名在数据库中是否重复
    @GetMapping("/DataTable/inspection")
    public Result tableInspection(@RequestParam("newname") String name){
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
//        List<CategoryEntity> list = categoryService.list(wrapper);
        List<CategoryEntity> list = categoryMapper.selectList(wrapper);
        System.out.println(list);
        List<String>  nameList = new ArrayList<>();
        nameList = dataManagerMapper.getTableName();
        for (String tempName :nameList) {
            CategoryEntity tempCategoryEntity = new CategoryEntity();
            tempCategoryEntity.setLabel(tempName);
            list.add(tempCategoryEntity);
        }
        boolean flag = true;
        for (CategoryEntity categoryEntity : list) {
            if(categoryEntity.getLabel().equals(name)) {
                flag = false;
                break;
            }
        }
        return Result.success("200",flag); // 判断文件名是否重复
    }


    // 筛选数据后，创建表保存筛选后的数据
    @PostMapping("/createTable")
    public Result createTable(@RequestBody FilterTableDataVo filterTableDataVo){

         tableDataService.createTable(filterTableDataVo.getAddDataForm().getDataName(),filterTableDataVo.getAddDataForm().getCharacterList(),
                filterTableDataVo.getAddDataForm().getCreateUser(),filterTableDataVo.getNodeData());
        System.out.println("开始新建表："+JSON.toJSONString(filterTableDataVo));
        return Result.success(200,"SUCCESS");
    }

    // 根据条件筛选数据
    @PostMapping("/filterTableData")
    public Result<List<Map<String,Object>>> getFilterTableData(@RequestBody FilterTableDataVo filterTableDataVo){
        List<LinkedHashMap<String, Object>> filterDataByConditions = tableDataService.getFilterDataByConditions(filterTableDataVo.getAddDataForm().getCharacterList(), filterTableDataVo.getNodeData());
        System.out.println("筛选数据长度为："+filterDataByConditions.size());
        return Result.success("200",filterDataByConditions);
    }

    /**
     * 特征中文等匹配
     *
     *
     */
    @GetMapping("/FeatureMatch")
    public Result<List<FeatureMatch>> featruematch(){
        return Result.success("200",tableDataMapper.feamatch());
    }


}
