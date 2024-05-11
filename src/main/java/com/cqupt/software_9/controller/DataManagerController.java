package com.cqupt.software_9.controller;

import com.cqupt.software_9.common.R;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.mapper.CkdManagerMapper;
import com.cqupt.software_9.mapper.CkdTableManagerMapper;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.mapper.DatabaseManagerMapper;
import com.cqupt.software_9.entity.DataManager;
import com.cqupt.software_9.entity.DatabaseManager;
import com.cqupt.software_9.service.DataManagerService;
import com.cqupt.software_9.service.TableManagerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DataManager")
public class DataManagerController {
    @Autowired
    private DataManagerService dataManagerService;

    @Autowired
    private TableManagerService tableManagerService;

    @Resource
    private DataManagerMapper dataManagerMapper;

    private CkdManagerMapper ckdManagerMapper;

    private DatabaseManagerMapper databaseManagerMapper;

    private CkdTableManagerMapper ckdTableManagerMapper;

    public DataManagerController(DataManagerMapper dataManagerMapper,CkdManagerMapper ckdManagerMapper,DatabaseManagerMapper databaseManagerMapper,CkdTableManagerMapper ckdTableManagerMapper) {
        this.dataManagerMapper = dataManagerMapper;
        this.ckdManagerMapper=ckdManagerMapper;
        this.databaseManagerMapper=databaseManagerMapper;
        this.ckdTableManagerMapper=ckdTableManagerMapper;
    }

    @GetMapping("/heart")
    public List<DataManager> getheartDataWithoutresult() {
        return dataManagerService.getDatawithoutresult();
    }

    //    分页查询heart
    @GetMapping("/data/heart")
    public PageInfo<DataManager> getheartdatawithoutresult(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        return dataManagerService.getDatawithoutresult(page, pageSize);
    }

    @GetMapping("/ckd")
    public List<DataManager> getckdDataWithoutresult() {
        return ckdManagerMapper.getckdManagerwithoutresult();
    }

    @GetMapping("/database")
    public  List<DatabaseManager> getall(){
        return databaseManagerMapper.getall();
    }

    @PostMapping("/data")
    public List<DataManager> getDetail(@RequestBody Map<String,String> map) {
        String diseasename=map.get("diseaseName");
        return dataManagerService.getDetail(diseasename);
    }

//    @GetMapping("/data")
//    public List<DataManager> getDetails(@RequestParam("databasename") String databasename) {
//        if (databasename.equals("dataandresult")) {
//            return getheartDataWithoutresult();
//        } else if (databasename.equals("ckd")) {
//            return getckdDataWithoutresult();
//        }
//        throw new IllegalArgumentException("Invalid databasename: " + databasename);
//    }

    @GetMapping("/getInfoByTableID/{id}")
    public R<List<Map<String,String>>> getInfoByTableName(@PathVariable Integer id){
        String tableName = dataManagerService.getTableNameByID(id);
        tableName = tableName + "_result";
        List<Map<String,String>> result = dataManagerService.getInfoByTableName(tableName);

        return new R<>(200,"查找成功",result);
    }


    /*
    * 获取疾病名称
    *@return
    *
    * */
    @GetMapping("/getDiseaseName")
    public List<String> getDiseaseName(){
        return dataManagerMapper.getDiseaseName();
    }


    /**
     * 获取表名
     */
    @GetMapping("/getTableName")
    public Result getTableName(){
        return Result.success("200",dataManagerMapper.getTableName());
    }

    /**
     * 获取疾病和表名的对应关系
     */
    @GetMapping("/getDiseaseTableName/{uid}")
    public List<Map<String,String>> getDiseaseTableName(@PathVariable("uid")String uid ){
        return  dataManagerMapper.getDiseaseTableName(uid);
    }
}
