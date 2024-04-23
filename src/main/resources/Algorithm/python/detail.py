import argparse
import csv

import pandas as pd
import psycopg2
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sqlalchemy import create_engine


def load_data(tableName, output_file):
    try:
        # 连接到 PostgreSQL 数据库
        connection = psycopg2.connect(
            user="pg",
            password="111111",
            host="10.16.48.219",
            port="5432",
            database="software9"
        )

        # 创建游标对象
        cursor = connection.cursor()

        # 获取表的列名
        cursor.execute("SELECT column_name FROM information_schema.columns WHERE table_name = %s", (tableName,))
        columns = [row[0] for row in cursor.fetchall()]

        # 获取表的数据
        cursor.execute("SELECT * FROM {}".format(tableName))
        rows = cursor.fetchall()

        # 将数据和列名写入 CSV 文件
        with open(output_file, mode='w', newline='') as file:
            writer = csv.writer(file)
            writer.writerow(columns)  # 写入列名
            writer.writerows(rows)  # 写入数据



    except (Exception, psycopg2.Error) as error:

        print("Error fetching data from PostgreSQL table:", error)

    finally:
        # 关闭游标和连接
        if connection:
            cursor.close()
            connection.close()


# 构建模型
def build_model(algorithmName, algorithmAttributes):
    # 根据算法参数初始化随机森林模型
    model = RandomForestClassifier(**algorithmAttributes)
    return model


def train_model(model, X_train, y_train):
    # 使用训练数据对模型进行训练
    model.fit(X_train, y_train)
    return model


def publicAl(tableName, target, fea, algorithmName, algorithmAttributes):
    # 加载数据
    output_file = 'output.csv'
    fetch_data_and_columns = load_data(tableName, output_file)
    data = pd.read_csv(output_file)
    # 划分特征和标签
    X = data[fea]
    y = data[target]
    feature_names = len(fea)
    # 划分训练集和测试集
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    results = {}

    # 构建模型
    model = build_model(algorithmName, algorithmAttributes)

    # 模型训练
    trained_model = train_model(model, X_train, y_train)

    return trained_model


def get_feature_importance(features, importances):
    feature_importance_dict = dict(zip(features, importances))
    feature_importance_list = [{"feature": key, "importance": round(value, 5)} for key, value in
                               feature_importance_dict.items()]
    return feature_importance_list


def get_avage_value(feature, calculation_rates, table_name):
    # 结果列表
    results = []

    db_config = {
        'username': 'pg',
        'password': '111111',
        'host': '10.16.48.219',
        'port': '5432',
        'database': 'software9'
    }

    # 创建数据库引擎
    engine = create_engine(
        f"postgresql://{db_config['username']}:{db_config['password']}@{db_config['host']}:{db_config['port']}/{db_config['database']}")

    # 针对单个表进行操作
    for feature in feature:
        # 构建SQL查询，确保特征名被双引号包围
        query = f"SELECT \"{feature}\" FROM \"{table_name}\""
        df = pd.read_sql(query, engine)

        # 确保数据不为空
        if not df.empty:
            # 尝试将特征列转换为数值型，如果转换失败则使用 NaN 替代
            df[feature] = pd.to_numeric(df[feature], errors='coerce')

            # 对特征列进行排序
            sorted_df = df.sort_values(by=feature)

            # 计算前%几和后%几的均值
            rate_lower, rate_upper = calculation_rates
            count = len(sorted_df)
            lower_percentile_index = int(count * rate_lower)
            upper_percentile_index = int(count * (1 - rate_upper))

            # 计算均值，注意处理边界情况
            lower_values = round(sorted_df.iloc[0:lower_percentile_index][feature].dropna().mean(), 5)
            upper_values = round(sorted_df.iloc[upper_percentile_index:][feature].dropna().mean(), 5)

            # 将结果添加到列表中
            results.append(
                (task_name, table_name, feature, rate_lower, lower_values, rate_upper, upper_values))

    # 打印结果
    return results


def insert_data_to_db(integrated_info):
    try:
        # 连接到 PostgreSQL 数据库
        connection = psycopg2.connect(
            user="pg",
            password="111111",
            host="10.16.48.219",
            port="5432",
            database="software9"
        )

        # 创建游标对象
        cursor = connection.cursor()

        # 循环遍历数组的每个元素，并将其插入数据库
        for item in integrated_info:
            model_name, table_name, feature, importance, f_percentage, f_value, b_percentage, b_value = item
            # 将浮点型转换为字符串
            importance_str = str(importance)
            f_percentage_str = str(f_percentage)
            f_value_str = str(f_value)
            b_percentage_str = str(b_percentage)
            b_value_str = str(b_value)

            # 构建 SQL 插入语句
            insert_query = "INSERT INTO detail (modelname, tablename, feature, importance, fpercentage, fvalue, bpercentage, bvalue) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"
            record_to_insert = (
                model_name, table_name, feature, importance_str, f_percentage_str, f_value_str, b_percentage_str,
                b_value_str)

            # 执行 SQL 插入语句
            cursor.execute(insert_query, record_to_insert)

            delete_query = """
            DELETE FROM detail
            WHERE ctid IN (
                SELECT ctid
                FROM (
                    SELECT ctid,
                           ROW_NUMBER() OVER (PARTITION BY modelname, tablename, feature ORDER BY ctid) AS rnum
                    FROM detail
                ) t
                WHERE t.rnum > 1
            );
            """
            cursor.execute(delete_query)

        # 提交事务
        connection.commit()


    except (Exception, psycopg2.Error) as error:
        print("Error inserting data into your_table_name table:", error)

    finally:
        # 关闭游标和连接
        if connection:
            cursor.close()
            connection.close()


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--p_calculation_rates", type=str, default=None)
    parser.add_argument("--b_calculation_rates", type=str, default=None)
    parser.add_argument("--modelname", type=str, default=None)
    parser.add_argument("--feature", nargs='+', type=str, default=None)
    parser.add_argument("--tablename", type=str, default=None)
    parser.add_argument("--target", type=str, default=None)
    args = parser.parse_args()

    # task_name = "1"
    task_name = args.modelname
    # p_calculation_rates = 0.05  # 替换为你的计算率列表
    p_calculation_rates = float(args.p_calculation_rates)  # 替换为你的计算率列表
    # t_calculation_rates = 0.07  # 替换为你的计算率列表
    b_calculation_rates = float(args.b_calculation_rates) # 替换为你的计算率列表
    calculation_rates = [p_calculation_rates, b_calculation_rates]
    # tableName = "heart1test"
    tableName = args.tablename
    target = args.target
    # fea = ["age", "sex", "cp", "trestbps", "chol", "fbs", "restecg", "thalach", "exang", "oldpeak", "slope", "ca",
    #        "thal"]
    fea = args.feature[0].split(",")
    algorithmName = "RF"
    algorithmAttributes = {"bootstrap": True,
                           "criterion": "gini",
                           "max_depth": None,
                           "class_weight": None,
                           "max_features": None,
                           "n_estimators": 100,
                           "random_state": None,
                           "min_samples_leaf": 1,
                           "min_samples_split": 2}
    trained_model = publicAl(tableName, target, fea, algorithmName, algorithmAttributes)
    feature_importance_list = get_feature_importance(fea, trained_model.feature_importances_)
    # print(feature_importance_list)
    avage_value = get_avage_value(fea, calculation_rates, tableName)
    integrated_info = []
    for feature_dict in feature_importance_list:
        # print(feature_dict)
        for analysis_tuple in avage_value:
            # print(analysis_tuple)
            if feature_dict['feature'] == analysis_tuple[2]:
                integrated_info.append((task_name, tableName, feature_dict['feature'],
                                        feature_dict['importance'],) + analysis_tuple[3:])
    insert_data_to_db(integrated_info)
    result = [1]
    print(result)

