package com.cqupt.software_9.service.imp;

import com.cqupt.software_9.common.UploadResult;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.entity.DataManager;
import com.cqupt.software_9.entity.tTableManager;
import com.cqupt.software_9.service.Adapter.FileServiceAdapter;
import com.cqupt.software_9.service.DataTableManagerService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl extends FileServiceAdapter {

    @Resource
    private DataTableManagerService dataTableManagerService;

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private DataManagerMapper dataManagerMapper;
    @Resource
    private com.cqupt.software_9.mapper.tTableManagerMapper tTableManagerMapper;

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;


    @Override
    public UploadResult fileUpload(MultipartFile file, String newName, String disease) throws IOException {
        System.out.println(file.getOriginalFilename());
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are supported.");
        }

        String tableName = newName;

        List<String> tableHeaders;
        List<String[]> rows = new ArrayList<>(); // 存储处理后的数据行

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] columnNames = csvReader.readNext();
            //去空格
            tableHeaders = new ArrayList<>();
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].trim();
                if (!columnName.isEmpty()) {
                    tableHeaders.add(columnName);
                }
            }
            String[] tableHeadersArray = tableHeaders.toArray(new String[0]);
            System.out.println(tableHeaders);
            // 读取数据行并删除空表头对应的整列数据
//            csvReader.readNext();
            String[] row;
            while ((row = csvReader.readNext()) != null) {

                String[] newRow = new String[tableHeaders.size()];
                int newIndex = 0;
                for (int i = 0; i < row.length; i++) {
                    if (!columnNames[i].trim().isEmpty()) {
                        newRow[newIndex++] = row[i];
                    }
                }
                rows.add(newRow);
            }


/*            try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                createTable(connection, tableName, tableHeaders, rows);

                String insertQuery = generateInsertQuery(tableName, tableHeadersArray);
                System.out.println(insertQuery);


//                String[] data1;
                int batchCount = 0;

//                CSVReader csvReaderForData = new CSVReader(new InputStreamReader(file.getInputStream()));
//                csvReaderForData.readNext();
                for (String[] data : rows) {
                    batchCount++;
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {

                        for (int i = 0; i < data.length; i++) {
                            // 去除列名中的空格
                            String columnName = tableHeaders.get(i).trim();
                            statement.setString(i + 1, data[i]);
                        }
//                        for (int i = 0; i < data.length; i++) {
//                            statement.setString(i + 1, data[i]);
//                        }
                        statement.addBatch();
                        if (batchCount % 1000 == 0) {
                            statement.executeBatch();
                        }
                        statement.executeUpdate();

                    } catch (SQLException e) {
                        throw new RuntimeException("Failed to insert data into the database.", e);
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Failed to create table or establish connection to the database.", e);
            }*/
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file.", e);
        }

//        return tableName;
        // 创建并返回UploadResult对象
        UploadResult result = new UploadResult();
        result.setTableName(tableName);
        result.setTableHeaders(tableHeaders);
        result.setCode(200);
        return result;

    }

    @Override
    public UploadResult creatUpTable(MultipartFile file, String newName,String disease,String user, Integer uid, String chinesename) {
        System.out.println(file.getOriginalFilename());
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are supported.");
        }

        String tableName = newName;

        List<String> tableHeaders;
        List<String[]> rows = new ArrayList<>(); // 存储处理后的数据行

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] columnNames = csvReader.readNext();
            //去空格
            tableHeaders = new ArrayList<>();
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].trim();
                if (!columnName.isEmpty()) {
                    tableHeaders.add(columnName);
                }
            }
            String[] tableHeadersArray = tableHeaders.toArray(new String[0]);
            System.out.println(tableHeaders);
            // 读取数据行并删除空表头对应的整列数据
//            csvReader.readNext();
            String[] row;
            while ((row = csvReader.readNext()) != null) {

                String[] newRow = new String[tableHeaders.size()];
                int newIndex = 0;
                for (int i = 0; i < row.length; i++) {
                    if (!columnNames[i].trim().isEmpty()) {
                        newRow[newIndex++] = row[i];
                    }
                }
                rows.add(newRow);
            }
            System.out.println(tableName+"1111********************************************");
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                createTable(connection, tableName, tableHeaders, rows);

                String insertQuery = generateInsertQuery(tableName, tableHeadersArray);
                System.out.println(insertQuery);


//                String[] data1;
                int batchCount = 0;

//                CSVReader csvReaderForData = new CSVReader(new InputStreamReader(file.getInputStream()));
//                csvReaderForData.readNext();
                for (String[] data : rows) {
                    batchCount++;
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {

                        for (int i = 0; i < data.length; i++) {
                            // 去除列名中的空格
                            String columnName = tableHeaders.get(i).trim();
                            statement.setString(i + 1, data[i]);
                        }
//                        for (int i = 0; i < data.length; i++) {
//                            statement.setString(i + 1, data[i]);
//                        }
                        statement.addBatch();
                        if (batchCount % 1000 == 0) {
                            statement.executeBatch();
                        }
                        statement.executeUpdate();

                    } catch (SQLException e) {
                        throw new RuntimeException("Failed to insert data into the database.", e);
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Failed to create table or establish connection to the database.", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file.", e);
        }
        System.out.println(tableName+"********************************************");
        dataTableManagerService.updateDataTable(tableName,disease,user,uid,chinesename);

        UploadResult result = new UploadResult();
        result.setTableName(tableName);
        result.setTableHeaders(tableHeaders);
        result.setCode(200);
//        result.setRes(res);
        return result;
    }

    private void createTable(Connection connection, String tableName, List<String> tableHeaders, List<String[]> rows) throws SQLException {
        StringBuilder createTableQuery = new StringBuilder();
//        createTableQuery.append("CREATE TABLE ").append(tableName).append(" (");
        createTableQuery.append("CREATE TABLE ");
        createTableQuery.append("`"); // 添加反引号（`）开始包围列名
        createTableQuery.append(tableName);
        createTableQuery.append("`"); // 添加反引号（`）结束包围列名
        createTableQuery.append(" (");
        for (int i = 0; i < tableHeaders.size(); i++) {


            String columnName = tableHeaders.get(i);
            String columnType = determineColumnType(columnName, rows,tableHeaders);


            // 使用反引号包裹列名
            createTableQuery.append("`").append(columnName).append("`").append(" ").append(columnType);

            if (i < tableHeaders.size() - 1) {
                createTableQuery.append(", ");
            }
        }

        createTableQuery.append(")");

        try (PreparedStatement statement = connection.prepareStatement(createTableQuery.toString())) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table.", e);
        }
    }
/*    private void createTable(Connection connection, String tableName, String[] columnNames, CSVReader csvReader) throws SQLException, IOException {
        StringBuilder createTableQuery = new StringBuilder();
        createTableQuery.append("CREATE TABLE ").append(tableName).append(" (");

        Map<String, String> columnTypes = determineColumnTypes(columnNames, csvReader);

        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i];
            String columnType = columnTypes.get(columnName);

            // 使用反引号包裹列名
            createTableQuery.append("`").append(columnName).append("`").append(" ").append(columnType);

            if (i < columnNames.length - 1) {
                createTableQuery.append(", ");
            }
        }

        createTableQuery.append(")");

        try (PreparedStatement statement = connection.prepareStatement(createTableQuery.toString())) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table.", e);
        }
    }*/

//    //load data方法
//    private void createTable(Connection connection, String tableName, String[] columnNames, Map<String, String> columnTypes) throws SQLException {
//        StringBuilder createTableQuery = new StringBuilder();
//        createTableQuery.append("CREATE TABLE ").append(tableName).append(" (");
//
//        for (int i = 0; i < columnNames.length; i++) {
//            String columnName = columnNames[i];
//            String columnType = columnTypes.get(columnName);
//
//            // 使用反引号包裹列名
//            createTableQuery.append("`").append(columnName).append("`").append(" ").append(columnType);
//
//            if (i < columnNames.length - 1) {
//                createTableQuery.append(", ");
//            }
//        }
//
//        createTableQuery.append(")");
//
//        try (PreparedStatement statement = connection.prepareStatement(createTableQuery.toString())) {
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to create table.", e);
//        }
//    }
//
////loda data fangfa
//    private Map<String, String> determineColumnTypes(String[] columnNames, InputStream inputStream) throws IOException {
//        Map<String, String> columnTypes = new HashMap<>();
//
//        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
//            String[] sampleData = csvReader.readNext(); // 获取第一行作为样本数据
//
//            // 确保样本数据的长度和 columnNames 的长度一致
//            int sampleLength = Math.min(sampleData.length, columnNames.length);
//
//            for (int i = 0; i < sampleLength; i++) {
//                String columnName = columnNames[i];
//                String columnType = determineColumnType(sampleData[i]);
//                columnTypes.put(columnName, columnType);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read CSV file.", e);
//        }
//
//        return columnTypes;
//    }


/*    private Map<String, String> determineColumnTypes(String[] columnNames, CSVReader csvReader) throws IOException {
        Map<String, String> columnTypes = new HashMap<>();

        try {
            List<String[]> allLines = csvReader.readAll();
            if (allLines.size() > 1) {
                String[] sampleData = allLines.get(1); // 获取第二行作为样本数据

                // 确保样本数据的长度和 columnNames 的长度一致
                int sampleLength = Math.min(sampleData.length, columnNames.length);

                System.out.println("ee" + Arrays.toString(sampleData));
                System.out.println(sampleData.length);
                System.out.println(columnNames.length);

                for (int i = 0; i < sampleLength; i++) {
                    String columnName = columnNames[i];
                    String columnType = determineColumnType(sampleData[i]);
                    columnTypes.put(columnName, columnType);
                }
            }
        } catch (IOException e) { // 添加对 IOException 的处理
            throw new RuntimeException("Failed to read CSV file.", e);
        }

        return columnTypes;
    }*/
//
//
    //    private String determineColumnType(String data) {
//        // Add your logic here to determine the appropriate column type based on the data
//        return "VARCHAR(255)";
//    }

    private String determineColumnType(String columnName, List<String[]> rows,List<String> tableHeaders) {
        // 判断数据是否为整数
        boolean isInt = true;
        // 判断数据是否为浮点数
        boolean isDouble = true;
        // 判断数据是否为日期，这里假设日期格式为"yyyy-MM-dd"，你可以根据实际情况修改格式
        boolean isDate = true;

        for (String[] row : rows) {
            String data = row[tableHeaders.indexOf(columnName)];
            // 判断是否为整数
            try {
                Integer.parseInt(data);
            } catch (NumberFormatException e) {
                isInt = false;
            }

            // 判断是否为浮点数
            try {
                Double.parseDouble(data);
            } catch (NumberFormatException e) {
                isDouble = false;
            }

            // 判断是否为日期
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(data, dateFormatter);
            } catch (DateTimeParseException e) {
                isDate = false;
            }

            // 如果有一行不符合整数、浮点数、或日期的格式，则退出循环
            if (!isInt && !isDouble && !isDate) {
                break;
            }
        }

        // 根据判断结果返回对应的列类型
        if (isInt) {
            return "INT";
        } else if (isDouble) {
            return "DOUBLE";
        } else if (isDate) {
            return "DATE";
        } else {
            // 如果以上条件都不满足，则返回默认的VARCHAR类型，长度为255
            return "VARCHAR(255)";
        }
    }
/*    private String determineColumnType(String data) {
        // 判断数据是否为整数
        try {
            Integer.parseInt(data);
            return "INT"; // 如果是整数，返回INT类型
        } catch (NumberFormatException e) {
            // 不是整数，继续判断其他类型
        }

        // 判断数据是否为浮点数
        try {
            Double.parseDouble(data);
            return "DOUBLE"; // 如果是浮点数，返回DOUBLE类型
        } catch (NumberFormatException e) {
            // 不是浮点数，继续判断其他类型
        }

        // 判断数据是否为日期，这里假设日期格式为"yyyy-MM-dd"，你可以根据实际情况修改格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(data, dateFormatter);
            return "DATE"; // 如果是日期，返回DATE类型
        } catch (DateTimeParseException e) {
            // 不是日期，继续判断其他类型
        }

        // 如果以上条件都不满足，则返回默认的VARCHAR类型，长度为255
        return "VARCHAR(255)";
    }*/


    private String generateInsertQuery(String tableName, String[] columnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");

        for (int i = 0; i < columnNames.length; i++) {
            sb.append("`"); // 添加反引号（`）开始包围列名
            sb.append(columnNames[i]);
            sb.append("`"); // 添加反引号（`）结束包围列名

            if (i < columnNames.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(") VALUES (");

        for (int i = 0; i < columnNames.length; i++) {
            sb.append("?");

            if (i < columnNames.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }





    //在线训练
    @Override
    public UploadResult fileUpload(MultipartFile file, String modelname, String diseasename,String publisher,String uid) throws IOException {
        System.out.println(file.getOriginalFilename());
        DataManager dataManager = new DataManager();
        tTableManager tTableManager = new tTableManager();
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are supported.");
        }

        String tableName = file.getOriginalFilename().replace(".csv", "");

        List<String> tableHeaders = null;
        List<String[]> rows = new ArrayList<>(); // 存储处理后的数据行

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
             Connection connection = jdbcTemplate.getDataSource().getConnection();
             BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))
        ) {
            String[] columnNames = csvReader.readNext();
            //去空格
            tableHeaders = new ArrayList<>();
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].trim();
                if (!columnName.isEmpty()) {
                    tableHeaders.add(columnName);
                    String fea = columnNames[i];
                    tTableManager.setTable_name(tableName);
                    tTableManager.setField_name(fea);
                    tTableManager.setUserid(uid);
                    tTableManagerMapper.insertTableNameAndFeature(tTableManager);

                }
            }
            String[] tableHeadersArray = tableHeaders.toArray(new String[0]);
            System.out.println(tableHeaders);
            // 读取数据行并删除空表头对应的整列数据
//            csvReader.readNext();
            String[] row;
            while ((row = csvReader.readNext()) != null) {

                String[] newRow = new String[tableHeaders.size()];
                int newIndex = 0;
                for (int i = 0; i < row.length; i++) {
                    if (!columnNames[i].trim().isEmpty()) {
                        newRow[newIndex++] = row[i];

                    }

                }
                rows.add(newRow);
            }

            /*向data_manager中插入数据*/
            dataManager.setTablename(tableName);
            dataManager.setDiseasename(diseasename);
            dataManager.setDatanumber(rows.size());
            dataManager.setAffiliationdatabase("dataandresult");
            dataManager.setOperators(publisher);
            dataManager.setTabletype("数据表");
            dataManager.setUploadmethod("手动上传");
            dataManager.setChinesename("111");
            dataManager.setFeaturenumber(columnNames.length);
            dataManager.setUid(uid);
            dataManagerMapper.insertDataManager(dataManager);

            /*根据传入数据在数据库中创建一张新表*/
            createTable(connection,tableName,tableHeaders,rows);
            /*将剩余数据插入到表中*/
            for (int i = 1; i < rows.size(); i++) {
                String[] myRow = rows.get(i);
                String insertSql = "INSERT INTO " + tableName + " VALUES " + toInsertSql(myRow);
                jdbcTemplate.update(insertSql);
            }
            System.out.println("数据导入成功");

            /*将数据表的名称和标签，插入到数据表t_table_manager中*/

        } catch (IOException  e) {
            throw new RuntimeException("Failed to read CSV file.", e);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据导入失败");
        }

        UploadResult result = new UploadResult();
        result.setTableName(tableName);
        result.setTableHeaders(tableHeaders);
        result.setCode(200);

//        result.setRes(res);
        return result;
    }

    private String toInsertSql(String[] myRow) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < myRow.length; i++) {
            sb.append(myRow[i]);
            if (i < myRow.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
