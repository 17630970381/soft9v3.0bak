# 导入必要的库
import argparse
import os
from datetime import datetime
import csv
import sys
import psycopg2
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
from scikitplot.metrics import plot_roc_curve, plot_precision_recall_curve, plot_confusion_matrix
import joblib
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from sklearn.metrics import roc_curve, auc
from sklearn.model_selection import train_test_split
from sklearn.metrics import precision_recall_curve, average_precision_score, confusion_matrix
import seaborn as sns

from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier

# 加载数据
def load_data(tableName,output_file):
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

        print("Data from table '{}' written to '{}' successfully.".format(tableName, output_file))

    except (Exception, psycopg2.Error) as error:
        print("Error fetching data from PostgreSQL table:", error)

    finally:
        # 关闭游标和连接
        if connection:
            cursor.close()
            connection.close()

# 构建模型
def build_model(algorithmName, algorithmAttributes):
    if algorithmName == 'RF' :
        # 根据算法参数初始化随机森林模型
        model = RandomForestClassifier(**algorithmAttributes)
    elif algorithmName == 'DT':
        model = DecisionTreeClassifier(**algorithmAttributes)
    elif algorithmName == 'SVM':
        # 根据算法参数初始化支持向量机模型
        model = SVC(**algorithmAttributes)
    else:
        raise ValueError("Unsupported algorithm: {}".format(algorithmName))

    return model


# 模型训练
def train_model(model, X_train, y_train):
    # 使用训练数据对模型进行训练
    model.fit(X_train, y_train)
    return model


# 模型评估
def evaluate_model(model, X_test, y_test):

    # 预测测试数据的标签
    y_pred = model.predict(X_test)
    # 计算AUC

    # 计算准确率
    # accuracy = accuracy_score(y_test, y_pred)
    # # 计算精确率
    # precision = precision_score(y_test, y_pred)
    # # 计算召回率
    # recall = recall_score(y_test, y_pred)
    # # 计算F1分数
    # f1 = f1_score(y_test, y_pred)
    accuracy = round(accuracy_score(y_test, y_pred), 5)
    # 计算精确率
    precision = round(precision_score(y_test, y_pred), 5)
    # 计算召回率
    recall = round(recall_score(y_test, y_pred), 5)
    # 计算F1分数
    f1 = round(f1_score(y_test, y_pred), 5)


    return accuracy, precision, recall, f1





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

    model_file = '{}_{}.pkl'.format(datetime.now().strftime('%Y%m%d%H%M%S'), algorithmName)
    output_dir = 'E:\\soft\\software9-3\\software9\\src\\main\\resources\\Algorithm\\PKL'
    model_path = os.path.join(output_dir, model_file)
    joblib.dump(trained_model, model_path)

    # 创建文件夹（如果不存在）
    folder_name = datetime.now().strftime('%Y%m%d%H%M%S')+ '_' + algorithmName
    output_dir = 'E:\\soft\\Software9-v2.0-1220\\src\\assets\\{}'.format(folder_name)
    os.makedirs(output_dir, exist_ok=True)
    os.chdir(output_dir)



    # 获取概率预测结果
    y_proba = trained_model.predict_proba(X_test)
    # 提取预测结果的概率值（第二列）
    y_proba_positive = y_proba[:, 1]

    fpr, tpr, thresholds = roc_curve(y_test, y_proba_positive)

    roc_auc = auc(fpr, tpr)

    # 绘制 ROC 曲线
    plt.figure(figsize=(8, 6))
    plt.plot(fpr, tpr, color='darkorange', lw=2, label='ROC curve (area = %0.2f)' % roc_auc)
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title('Receiver Operating Characteristic (ROC)')
    plt.legend(loc="lower right")
    plt.savefig('roc_curve.png')  # 保存 ROC 曲线图形
    plt.close()

    # 2. 计算 PR 曲线
    precision, recall, _ = precision_recall_curve(y_test, y_proba_positive)
    average_precision = average_precision_score(y_test, y_proba_positive)

    # 绘制 PR 曲线并保存图形
    plt.figure(figsize=(8, 6))
    plt.step(recall, precision, color='b', alpha=0.2, where='post')
    plt.fill_between(recall, precision, step='post', alpha=0.2, color='b')
    plt.xlabel('Recall')
    plt.ylabel('Precision')
    plt.ylim([0.0, 1.05])
    plt.xlim([0.0, 1.0])
    plt.title('Precision-Recall curve: AP={0:0.2f}'.format(average_precision))
    plt.savefig('precision_recall_curve.png')  # 保存 PR 曲线图形
    plt.close()

    # 3. 计算混淆矩阵
    y_pred = model.predict(X_test)
    cm = confusion_matrix(y_test, y_pred)

    # 绘制混淆矩阵并保存图形
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted labels')
    plt.ylabel('True labels')
    plt.title('Confusion Matrix')
    plt.savefig('confusion_matrix.png')  # 保存混淆矩阵图形
    plt.close()
    # 绘制特征重要性

    feature_importances = trained_model.feature_importances_
    plt.figure(figsize=(10, 8))
    plt.barh(range(len(feature_importances)), feature_importances, tick_label=fea)
    plt.xlabel('Feature Importance')
    plt.ylabel('Feature')
    plt.title('Feature Importance')
    plt.savefig("feature_importance.png")


    # 模型评估
    accuracy, precision, recall, f1 = evaluate_model(trained_model, X_test, y_test)

    # 将结果保存到字典中
    results = {'accuracy': accuracy, 'precision': precision, 'recall': recall, 'f1': f1}

    return results,output_dir,model_path, y_test,  y_proba



def parse_algorithm_attributes(attr_list):
    attributes = {}
    for item in attr_list:
        key, value = item.split('=')
        attributes[key] = value
    return attributes

if __name__ == "__main__":

    # tableName = "heart1test"
    # target = "label"
    # # fea = ["age", "sex", "cp", "trestbps", "chol", "fbs", "restecg", "thalach", "exang", "oldpeak", "slope", "ca",
    # #        "thal"]
    # fea = ["Marital status at diagnosis","Sex","Race recode (White, Black, Other)","Race recode (W, B, AI, API)",
    # "Diagnostic Confirmation","Derived AJCC M, 6th ed (2004-2015)","RX Summ--Scope Reg LN Sur (2003+)",
    # "RX Summ--Surg Oth Reg/Dis (2003+)","RX Summ--Surg/Rad Seq","Reason no cancer-directed surgery",
    # "Radiation recode","Chemotherapy recode (yes, no/unk)","RX Summ--Systemic/Sur Seq","SEER Combined Mets at DX-bone (2010+)",
    # "SEER Combined Mets at DX-brain (2010+)","SEER Combined Mets at DX-liver (2010+)","SEER Combined Mets at DX-lung (2010+)",
    # "SEER cause-specific death classification","SEER other cause of death classification","Survival months flag",
    # "Vital status recode (study cutoff used)"]
    # # algorithmName = "RF"
    # algorithmAttributes = {"bootstrap": True,
    #                        "criterion": "gini",
    #                        "max_depth": None,
    #                        "class_weight": None,
    #                        "max_features": None,
    #                        "n_estimators": 100,
    #                        "random_state": None,
    #                        "min_samples_leaf": 1,
    #                        "min_samples_split": 2}
    # 获取命令行参数
    args = sys.argv[1:]  # 第一个参数是脚本文件路径，因此从第二个参数开始读取
    #
    # 解析参数
    tableName = args[0]
    target = args[1]
    fea = sys.argv[3].split(',')  # 将逗号分隔的字符串转换为列表
    algorithmName = sys.argv[4]
    algorithmAttributes_str = args[4:]  # 获取算法属性字符串列表

    # 解析算法属性为字典
    algorithmAttributes = {}
    for attr_str in algorithmAttributes_str:
        key, value = attr_str.split('=')
        if value.lower() == 'true':
            algorithmAttributes[key] = True
        elif value.lower() == 'false':
            algorithmAttributes[key] = False
        elif value.lower() == 'none':
            algorithmAttributes[key] = None
        elif value.isdigit():
            algorithmAttributes[key] = int(value)
        else:
            algorithmAttributes[key] = value

    results, output_dir,model_path,y_test,  y_proba = publicAl(tableName, target, fea, algorithmName, algorithmAttributes)
    y_proba_positive = y_proba[:, 1]
    y_proba_negative = y_proba[:, 0]
    data = np.column_stack((y_test, y_proba_positive, y_proba_negative))
    print(results, output_dir, model_path)
    # print(tableName, target, fea, algorithmName, algorithmAttributes)

    data_list = [{'y_test': row[0], 'y_proba_positive': row[1], 'y_proba_negative': row[2]} for row in data]
    # df = pd.DataFrame(data, columns=["y_test", "y_proba_positive", "y_proba_negative"])

    host = "10.16.48.219"  # 主机名或IP地址
    port = "5432"  # 端口号
    database = "software9"  # 数据库名
    user = "pg"  # 用户名
    password = "111111"  # 密码

    # 构建连接字符串
    conn_string = f"host='{host}' port='{port}' dbname='{database}' user='{user}' password='{password}'"
    # 构建删除数据的SQL语句
    if algorithmName == 'RF':
        delete_query = """
                DELETE FROM rf_test_data
            """
    elif algorithmName == 'DT':
        delete_query = """
                       DELETE FROM dt_test_data
                   """

    # 构建插入数据的SQL语句
    if algorithmName == 'RF':
        insert_query = """
                INSERT INTO rf_test_data (y_test, y_proba_positive, y_proba_negative)
                VALUES (%s, %s, %s)
            """
    elif algorithmName == 'DT':
        insert_query = """
                      INSERT INTO dt_test_data (y_test, y_proba_positive, y_proba_negative)
                      VALUES (%s, %s, %s)
                  """

    # 尝试执行操作
    try:
        # 连接到数据库
        conn = psycopg2.connect(conn_string)

        # 创建一个游标对象
        cursor = conn.cursor()

        # 删除表中的数据
        cursor.execute(delete_query)

        # 遍历数据列表，逐条插入数据
        for data in data_list:
            cursor.execute(insert_query, (data['y_test'], data['y_proba_positive'], data['y_proba_negative']))

        # 提交事务
        conn.commit()

        # print("数据插入成功！")

    except (Exception, psycopg2.Error) as error:
        print("数据插入失败:", error)
    finally:
        # 关闭游标和连接
        if cursor:
            cursor.close()
        if conn:
            conn.close()
