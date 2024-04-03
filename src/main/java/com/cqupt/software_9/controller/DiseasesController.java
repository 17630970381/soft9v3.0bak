package com.cqupt.software_9.controller;

import com.cqupt.software_9.entity.Diseases;
import com.cqupt.software_9.entity.HeartManager;
import com.cqupt.software_9.mapper.DiseasesMapper;
import com.cqupt.software_9.service.DiseasesService;
import com.cqupt.software_9.service.HeartManagerService;
import com.cqupt.software_9.service.Request.Query;
import com.cqupt.software_9.service.Response.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/Diseases")
@RestController
public class DiseasesController {

    @Resource
    private DiseasesService diseasesService;

    @Resource
    private HeartManagerService heartManagerService;

    @Resource
    private DiseasesMapper diseasesMapper;
    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    @PostMapping("/api/diseases/list")
    public Result diseasesList(@RequestBody Query queryDTO){
        //System.out.println(queryDTO.toString());
        // 调用userService.selectUserPage(queryDTO)方法获取返回结果
        // 调用userService.selectUserPage(queryDTO)方法获取返回结果
//        IPage<User> userPage = userService.selectUserPage(queryDTO);
//        System.out.println("userPage: " + userPage);
//
//        if (userPage != null && userPage.getRecords() != null) {
//            // 返回结果和记录列表均不为空，可以创建Result对象并返回
//            return new Result(200, "", userPage.getRecords());
//        } else {
//            // 返回结果或记录列表为空，可以创建适当的错误信息或处理方式
//            return new Result(500, "User list is empty.", null);
//        }
        return new Result(200,"",diseasesService.selectDiseasesPage(queryDTO));
    }

    @GetMapping("/api/diseases/listcode")
    public Result diseasesList1(String code){
        return new Result(200,"",diseasesService.getInfobycode(code));
    }

    @GetMapping("/api/diseases/loadall")
    public List<Diseases> getAllDiseases() {
        return diseasesService.getAllDiseases();
    }

    @GetMapping("/api/diseases/findbycode")
    public Diseases getDiseaseByCode(String code) {
        System.out.println(code);
        return diseasesService.findByCode(code);
    }

    @GetMapping("/api/diseases/findbypart_name/{part_name}")
    public List<Diseases> getDiseaseBypart_name(@PathVariable String partName) {
        return diseasesService.findBypart_name(partName);
    }

    @GetMapping("/api/diseases/findpartNamebycode/{code}")
    public String getpartNamebycode(@PathVariable String code)
    {
        return diseasesService.findpartNamebyCode(code);
    }

    @GetMapping("/api/diseases/findnamebycode/{code}")
    public String getnamebycode(@PathVariable String code)
    {
        return diseasesService.findnamebyCode(code);
    }

    @GetMapping("/api/diseases/findsympbycode/{code}")
    public String getsympbycode(@PathVariable String code)
    {
        return diseasesService.findsympbyCode(code);
    }

    @GetMapping("/api/diseases/finddptmentbycode/{code}")
    public String getdptmentbycode(@PathVariable String code)
    {
        return diseasesService.finddptmentbyCode(code);
    }

    @GetMapping("/api/diseases/findpreventbycode/{code}")
    public String getpreventbycode(@PathVariable String code)
    {
        return diseasesService.findpreventbyCode(code);
    }

    @PostMapping("/api/diseases/findbypartNameAndName")
    public List<Map<String, Object>>groupDiseases() {
//        // 创建一个空的Map，用于按partName分组的数据存储
//        Map<String, List<String>> groupedData = new HashMap<>();
//        List<Diseases> diseases = diseasesService.getAllDiseases();
//        // 遍历diseases，将partName和name添加到对应的列表中
//        for (Diseases disease : diseases) {
//            String partName = disease.getPartName();
//            String name = disease.getName();
//
//            if (!groupedData.containsKey(partName)) {
//                groupedData.put(partName, new ArrayList<>());
//            }
//            groupedData.get(partName).add(name);
//        }
//
//        // 创建一个空的List，用于存储最终的封装结果
//        List<Map<String, Object>> resultList = new ArrayList<>();
//
//        // 遍历groupedData，将每个partName和对应的name列表封装到Map中，然后添加到resultList中
//        for (Map.Entry<String, List<String>> entry : groupedData.entrySet()) {
//            String partName = entry.getKey();
//            List<String> nameList = entry.getValue();
//
//            Map<String, Object> partObject = new HashMap<>();
//            partObject.put("partName", partName);
//            partObject.put("name", nameList);
//
//            resultList.add(partObject);
//        }
//
//        return resultList;
        List<Diseases> diseases = diseasesService.getAllDiseases();
        Map<String, List<String>> groupedData = new HashMap<>();
        // 将疾病按partName分组
        for (Diseases disease : diseases) {
            String partName = disease.getPartName();
            String name = disease.getName();

            groupedData.computeIfAbsent(partName, k -> new ArrayList<>()).add(name);
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 将分组后的数据封装到结果列表中
        groupedData.forEach((partName, nameList) -> {
            Map<String, Object> partObject = new HashMap<>();
            partObject.put("partName", partName);
            partObject.put("name", nameList);
            resultList.add(partObject);
        });
        return resultList;
    }

    @PostMapping("/api/diseases/findbypartNameAndNameAndCode")
    public List<Map<String, Object>>groupDiseasesbypartNameAndNameAndCode() {
        // 创建一个空的Map，用于按partName分组的数据存储
        Map<String, List<Map<String, String>>> groupedData = new HashMap<>();
        List<Diseases> diseases = diseasesService.getAllDiseases();
        // 遍历diseases，将partName和name添加到对应的列表中
        for (Diseases disease : diseases) {
            String partName = disease.getPartName();
            String name = disease.getName();
            String code = disease.getCode();
            if (!groupedData.containsKey(partName)) {
                groupedData.put(partName, new ArrayList<>());
            }
            Map<String, String> nameCodeMap = new HashMap<>();
            nameCodeMap.put("name", name);
            nameCodeMap.put("code", code);

            groupedData.get(partName).add(nameCodeMap);

        }

        // 创建一个空的List，用于存储最终的封装结果
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 遍历groupedData，将每个partName和对应的name列表封装到Map中，然后添加到resultList中
        for (Map.Entry<String,  List<Map<String, String>>> entry : groupedData.entrySet()) {
            String partName = entry.getKey();
            List<Map<String, String>> nameCodeList = entry.getValue();

            Map<String, Object> partObject = new HashMap<>();
            partObject.put("partName", partName);
            partObject.put("symp", nameCodeList);

            resultList.add(partObject);
        }
        return resultList;
    }


    @GetMapping("/getall")
    public List<HeartManager> getall(){
        return heartManagerService.getall();
    }



    @PostMapping("/add")
    public Integer add(@RequestBody Diseases diseases){
        return diseasesMapper.insert(diseases);
    }
}