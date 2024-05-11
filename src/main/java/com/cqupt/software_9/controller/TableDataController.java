package com.cqupt.software_9.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.FeatureMatch;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.entity.FilterDataCol;
import com.cqupt.software_9.entity.FilterDataInfo;
import com.cqupt.software_9.entity.UserLog;
import com.cqupt.software_9.mapper.*;
import com.cqupt.software_9.service.*;
import com.cqupt.software_9.vo.FilterConditionVo;
import com.cqupt.software_9.vo.FilterTableDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private UserLogMapper userLogMapper;
    @Autowired
    TableDescribeService tableDescribeService;

    @Resource
    private TableDataMapper tableDataMapper;

    @Autowired
    private UserLogService userLogService;

    @Resource
    private DataManagerMapper dataManagerMapper;
    @Resource
    private StasticOneService stasticOneService;

    @Resource
    private FilterDataInfoMapper filterDataInfoMapper;

    @Resource
    private FilterDataColMapper filterDataColMapper;

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
                             @RequestParam("uid") String userId,
                             @RequestParam("parentId") String parentId,
                             @RequestParam("parentType") String parentType,
                             @RequestParam("status") String status,
                             @RequestParam("size") Double size,
                             @RequestParam("is_upload") String is_upload,
                             @RequestParam("is_filter") String is_filter){
        // 保存表数据信息
        try {
            System.out.println(userId);
            List<String> featureList = tableDataService.ParseFileCol(file,tableName);
            tableDataService.uploadFile(file, tableName, type, user, userId, parentId, parentType,status, size, is_upload,is_filter);
            return Result.success("200",featureList); // 返回表头信息
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(500,"文件上传异常");
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
                filterTableDataVo.getAddDataForm().getCreateUser(),filterTableDataVo.getNodeData(),filterTableDataVo.getAddDataForm().getUid(),filterTableDataVo.getAddDataForm().getUsername(),filterTableDataVo.getAddDataForm().getIsFilter(),filterTableDataVo.getAddDataForm().getIsUpload());
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

    @GetMapping("/getInfoByTableName/{tableName}")
    public Result<List<Map<String,Object>>> getInfoByTableName(@PathVariable("tableName") String tableName){
        tableName = tableName.replace("\"", "");
        List<Map<String, Object>> res = tableDataService.getInfoByTableName(tableName);
        return Result.success(200, "成功", res);
    }


    /**
     * 检查数据库中是否已存在该表名存在
     * @param tablename
     * @return
     */
    @GetMapping("/checkRepeat/{tablename}")
    public Result checkRepeat(@PathVariable("tablename")String tablename){
        return Result.success(200,"成功",tableDataService.checkRepeat(tablename));
    }

    @GetMapping("/getFilterConditionInfos")
    public Result getFilterInfo(){
        ArrayList<FilterConditionVo> vos = new ArrayList<>();
        List<FilterDataInfo> filterDataInfos = filterDataInfoMapper.selectList(null);
        for (FilterDataInfo filterDataInfo : filterDataInfos) {
            FilterConditionVo filterConditionVo = new FilterConditionVo();
            filterConditionVo.setFilterDataInfo(filterDataInfo);
            List<FilterDataCol> filterDataCols = filterDataColMapper.selectList(new QueryWrapper<FilterDataCol>().eq("filter_data_info_id", filterDataInfo.getId()));
            filterConditionVo.setFilterDataCols(filterDataCols);
            vos.add(filterConditionVo);
        }
        return Result.success("200",vos);
    }

    //纳排
    @PostMapping("/createFilterBtnTable")
    public Result createFilterBtnTable(@RequestBody FilterTableDataVo filterTableDataVo,
                                       @RequestHeader("uid") String userId,
                                       @RequestHeader("username") String username,
                                       @RequestHeader("role") Integer role){
        tableDataService.createFilterBtnTable(filterTableDataVo.getAddDataForm().getDataName(),filterTableDataVo.getAddDataForm().getCharacterList(),
                filterTableDataVo.getAddDataForm().getCreateUser(),filterTableDataVo.getStatus(),
                filterTableDataVo.getAddDataForm().getUid(),
                filterTableDataVo.getAddDataForm().getUsername(),
                filterTableDataVo.getAddDataForm().getIsFilter(),
                filterTableDataVo.getAddDataForm().getIsUpload(),
                filterTableDataVo.getAddDataForm().getUid_list(),
                filterTableDataVo.getNodeid());
        UserLog userLog = new UserLog();
        // userLog.setId(1);
        userLog.setUsername(username);
        userLog.setUid(userId);
        userLog.setRole(role);
        userLog.setOpTime(new Date());
        userLog.setOpType("用户建表"+filterTableDataVo.getAddDataForm().getDataName());
        userLogMapper.insert(userLog);
        return Result.success(200,"SUCCESS");
    }

    @PostMapping("/getFilterDataByConditionsByDieaseId")
    public Result<List<Map<String,Object>>> getFilterDataByConditionsByDieaseId(@RequestBody FilterTableDataVo filterTableDataVo){
        List<LinkedHashMap<String, Object>> filterDataByConditions = tableDataService.getFilterDataByConditionsByDieaseId(filterTableDataVo.getAddDataForm().getCharacterList(), filterTableDataVo.getAddDataForm().getUid(),filterTableDataVo.getAddDataForm().getUsername(),filterTableDataVo.getNodeid());
        System.out.println("筛选数据长度为："+filterDataByConditions.size());
        return Result.success("200",filterDataByConditions);
    }



}
