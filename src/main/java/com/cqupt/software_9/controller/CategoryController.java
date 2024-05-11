package com.cqupt.software_9.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.Category2Entity;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.entity.TableDescribeEntity;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.mapper.CategoryMapper;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.mapper.TableDescribeMapper;
import com.cqupt.software_9.mapper.UserMapper;
import com.cqupt.software_9.service.Category2Service;
import com.cqupt.software_9.service.CategoryService;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.vo.AddDiseaseVo;
import com.cqupt.software_9.vo.DeleteDiseaseVo;
import com.cqupt.software_9.vo.UpdateDiseaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

// TODO 公共模块新增类
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    Category2Service category2Service;

    @Autowired
    private UserLogService logService;

    @Autowired
    private CategoryMapper categorymapper;

    @Resource
    private DataManagerMapper dataManagerMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Resource
    private TableDescribeMapper tableDescribeMapper;

    @Autowired
    private UserMapper userMapper;

    // 获取目录
//    @GetMapping("/category")
//    public Result<List<CategoryEntity>> getCatgory(){
//        List<CategoryEntity> list = categoryService.getCategory();
//        System.out.println(JSON.toJSONString(list));
//        return Result.success("200",list);
//    }


    // 字段管理获取字段
//    @GetMapping("/category2")
//    public Result<List<Category2Entity>> getCatgory2(){
//        List<Category2Entity> list = category2Service.getCategory2();
//        return Result.success("200",list);
//    }

    // 创建一种新的疾病
//    @PostMapping("/addDisease")
//    public Result addDisease(@RequestBody CategoryEntity categoryNode){
//        System.out.println("参数为："+ JSON.toJSONString(categoryNode));
////        categoryService.save(categoryNode);
//        categorymapper.savetest(categoryNode);
//        return Result.success(200,"新增目录成功");
//    }

    // 删除一个目录
//    @GetMapping("/category/remove")
//    public Result removeCate(CategoryEntity categoryEntity){
//        System.out.println("要删除的目录为："+JSON.toJSONString(categoryEntity));
//
//        categoryService.removeNode(categoryEntity.getId());
//        if(categoryEntity.getIsLeafs()==1){
//            String tablename = categoryEntity.getLabel();
//            Boolean b = dataManagerMapper.deleteByTableName(tablename);
//            if(b){
//                dataManagerMapper.deleteTable(tablename);
//            }
//            tableDescribeMapper.deleteByTableName(tablename);
//        }
//
//        return Result.success(200,"删除成功");
//    }

//    @GetMapping("/addParentDisease")
//    public Result addParentDisease(@RequestParam("diseaseName") String diseaseName){
//        System.out.println("name:"+diseaseName);
//        categoryService.addParentDisease(diseaseName);
//        return Result.success("200",null);
//    }

    /**
     * 获取数量
     */
    @GetMapping("/category/count")
    public List<Integer> count(){
        LinkedList<Integer> list = new LinkedList<>();
        Integer a = categorymapper.countDisease();
        Integer b = categorymapper.countTable();
        list.add(a);
        list.add(b);
        return list;
    }


    /**
     * 判断是否重名
     */
    /**
     * 查询数据库中模型吗是否重复
     */
    @GetMapping("/isRepeatCategory/{label}")
    public boolean isRepeatCategory(@PathVariable("label") String label) {
        String i = categorymapper.isRepeatCategory(label);
        System.out.println(i);
        if(i == null) {
            return true;
        }else {
            return false;
        }
    }

    //新的数据管理
    @GetMapping("/category")
    public Result<List<CategoryEntity>> getCatgory(@RequestParam String uid){
        List<CategoryEntity> list = categoryService.getCategory(uid);
//        System.out.println(JSON.toJSONString(list));
        return Result.success("200",list);
    }

    @GetMapping("/Taskcategory")
    public Result<List<CategoryEntity>> getCatgory(){
        List<CategoryEntity> list = categoryService.getTaskCategory();
//        System.out.println(JSON.toJSONString(list));
        return Result.success("200",list);
    }


    // 字段管理获取字段
    @GetMapping("/category2")
    public Result<List<Category2Entity>> getCatgory2(){
        List<Category2Entity> list = category2Service.getCategory2();
        return Result.success("200",list);
    }



    // 创建一种新的疾病
    @PostMapping("/addDisease")
    public Result addDisease(@RequestBody CategoryEntity categoryNode){
        System.out.println("参数为："+ JSON.toJSONString(categoryNode));
        categoryService.save(categoryNode);
        return Result.success(200,"新增目录成功");
    }

    // 删除一个目录
    @Transactional
    @GetMapping("/category/remove")
    public Result removeCate(CategoryEntity categoryEntity){
        System.out.println("要删除的目录为："+JSON.toJSONString(categoryEntity));
        if(categoryEntity.getIsLeafs()==0){
            categoryService.removeNode(categoryEntity.getId());
        }
        else {
            dataManagerMapper.remove(categoryEntity.getLabel());
            categoryService.removeNode(categoryEntity.getId(),categoryEntity.getLabel());
            TableDescribeEntity tableDescribeEntity = tableDescribeMapper.selectOne(new QueryWrapper<TableDescribeEntity>().eq("table_id",categoryEntity.getId()));
            if(tableDescribeEntity.getTableSize()!=0){
                userMapper.recoveryUpdateUserColumnById(tableDescribeEntity.getUid(),tableDescribeEntity.getTableSize());
            }
            tableDescribeMapper.delete(new QueryWrapper<TableDescribeEntity>().eq("table_id",categoryEntity.getId()));
//            tTableMapper.delete(new QueryWrapper<tTable>().eq("table_name",categoryEntity.getLabel()));

        }

        return Result.success(200,"删除成功");
    }

    @GetMapping("/addParentDisease")
    public Result addParentDisease(@RequestParam("diseaseName") String diseaseName){
        System.out.println("name:"+diseaseName);
        categoryService.addParentDisease(diseaseName);
        return Result.success("200",null);
    }

    @GetMapping("/changeStatus")
    public Result changeStatus(CategoryEntity categoryEntity){
        categoryService.changeStatus(categoryEntity);
        return Result.success(200,"修改成功",null);
    }


    @GetMapping("/inspectionOfIsNotCommon")
    public Result inspectionOfIsNotCommon(@RequestParam("newname") String name){
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("is_common", 0);
        List<CategoryEntity> list = categoryService.list(wrapper);
        List<String>  nameList = new ArrayList<>();

        for (CategoryEntity temp :list) {
            nameList.add(temp.getLabel());
        }
        boolean flag = true;
        for (String  tempName : nameList) {
            if(tempName.equals(name)) {
                flag = false;
                break;
            }
        }
        return Result.success("200",flag); // 判断文件名是否重复
    }

    @GetMapping("/inspectionOfIsCommon")
    public Result inspectionOfIsCommon(@RequestParam("newname") String name){
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("is_common", 1);
        List<CategoryEntity> list = categoryService.list(wrapper);
        List<String>  nameList = new ArrayList<>();

        for (CategoryEntity temp :list) {
            nameList.add(temp.getLabel());
        }
        boolean flag = true;
        for (String  tempName : nameList) {
            if(tempName.equals(name)) {
                flag = false;
                break;
            }
        }
        return Result.success("200",flag); // 判断文件名是否重复
    }
    //新的数据管理结束


    //    zongqing新增疾病管理模块
    @GetMapping("/category/getAllDisease")
    public Result<List<CategoryEntity>> getAllDisease(){
        List<CategoryEntity> list = categoryService.getAllDisease();
        System.out.println(JSON.toJSONString(list));
        return Result.success("200",list);
    }

    /**
     * 新增检查疾病名是否存在
     */
    @GetMapping("/category/checkDiseaseName/{diseaseName}")
    public Result checkDiseaseName(@PathVariable String diseaseName){
        QueryWrapper<CategoryEntity> queryWrapper = Wrappers.query();
        queryWrapper.eq("label", diseaseName)
                .eq("is_delete", 0)
                .isNull("status");
        CategoryEntity category = categoryMapper.selectOne(queryWrapper);
        return category==null?Result.success("200","病种名可用"):Result.fail("400","病种名已存在");
    }


    @PostMapping("/category/addCategory")
    public Result addCategory(@RequestBody AddDiseaseVo addDiseaseVo){
        System.out.println("==========================================");
        System.out.println(addDiseaseVo);
        System.out.println("==========================================");
        categoryService.addCategory(addDiseaseVo);
        return Result.success("200","新增成功");
    }
    @PostMapping("/category/updateCategory")
    public Result updateCategory(@RequestBody UpdateDiseaseVo updateDiseaseVo) {
        System.out.println(updateDiseaseVo);
        return categoryService.updateCategory(updateDiseaseVo);
    }
        @PostMapping("/category/deleteCategory")
    public Result deleteCategory(@RequestBody DeleteDiseaseVo deleteDiseaseVo){
        StringJoiner joiner = new StringJoiner(",");
        for (String str : deleteDiseaseVo.getDeleteNames()) {
            joiner.add(str);
        }
        logService.insertLog(deleteDiseaseVo.getUid(), 0, "删除病种："+joiner.toString());

        categoryService.removeCategorys(deleteDiseaseVo.getDeleteIds());
        return Result.success("删除成功");
    }


    @GetMapping("/getParentTYpeById/{id}")
    public Result getParentTYpeById(@PathVariable("id") String id){
        return Result.success(200, "成功", categorymapper.getParentTYpeById(id));
    }


    // 新增可共享用户列表
    @PostMapping("/category/changeToShare")
    public Result changeToShare(@RequestBody Map<String, Object> requestData){
        System.out.println("=========================新增可共享用户列表=======");
        System.out.println(requestData);
        String nodeid = (String) requestData.get("nodeid");
        String tablename = categoryMapper.getLabelByid(nodeid);
        String uid_list = (String) requestData.get("uid_list");
        CategoryEntity entity = new CategoryEntity();
        entity.setUidList(uid_list);
        entity.setStatus("1");
        UpdateWrapper<CategoryEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", nodeid);
        int res = categoryMapper.update(entity, updateWrapper);
        boolean b = dataManagerMapper.updateUserList(tablename, uid_list);
        System.out.println(b);
        if(res == 1){
            return Result.success(200,"修改成功");
        }
        else {
            return Result.fail(500,"修改失败");
        }
    }
    //新增可共享用户列表
    @PostMapping("/category/changeToPrivate")
    public Result changeToPrivate(@RequestBody Map<String, Object> requestData){
        String nodeid = (String) requestData.get("nodeid");
        CategoryEntity entity = new CategoryEntity();
        entity.setUidList("");
        entity.setStatus("0");
        UpdateWrapper<CategoryEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", nodeid);
        int res = categoryMapper.update(entity, updateWrapper);
        if(res == 1){
            return Result.success(200,"修改成功");
        }
        else {
            return Result.fail(500,"修改失败");
        }
    }
    //新增可共享用户列表
    @PostMapping("/category/getNodeInfo")
    public Result getNodeInfo(@RequestBody Map<String, Object> requestData){
        String nodeid = (String) requestData.get("nodeid");
        String uid = (String) requestData.get("uid");

        QueryWrapper<CategoryEntity> queryWrapper = Wrappers.query();
        queryWrapper.eq("id",nodeid);
        CategoryEntity categoryEntity = categoryMapper.selectOne(queryWrapper);
        String includedUids = categoryEntity.getUidList();
        //使用 split() 方法返回的数组是一个固定长度的数组，无法修改其大小。
        //可以使用 Arrays.asList() 方法将数组转换为 ArrayList，然后再添加额外的元素。
        List<String> includedUidList = new ArrayList<>(Arrays.asList(includedUids.split(",")));

        includedUidList.add(uid);

        QueryWrapper<User> userQueryWrapper1 = new QueryWrapper<>();
        userQueryWrapper1.notIn("uid", includedUidList);
        List<User> excludeUserList = userMapper.selectList(userQueryWrapper1);

        QueryWrapper<User> userQueryWrapper2 = new QueryWrapper<>();
        includedUidList.remove(uid);
        userQueryWrapper2.in("uid", includedUidList);
        List<User> includeUserList = userMapper.selectList(userQueryWrapper2);


        //
        List<String> tempRes = new ArrayList<>();
        List<Map<String, Object>> included = new ArrayList<>();
        for (User user : includeUserList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", user.getUid());
            resultMap.put("label", user.getUsername());
            tempRes.add(user.getUid());
            included.add(resultMap);
        }


        List<Map<String, Object>> excluded = new ArrayList<>();
        for (User user : excludeUserList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", user.getUid());
            resultMap.put("label", user.getUsername());
            excluded.add(resultMap);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("included", included);
        result.put("excluded", excluded);
        return  Result.success(200,"操作成功", tempRes);
    }


}
