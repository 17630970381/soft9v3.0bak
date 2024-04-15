import argparse
import os
from datetime import datetime
import csv

import numpy as np
import psycopg2
import joblib
import torch.nn as nn
from torch import torch


# 定义命令行参数解析函数
# def parse_args():
#     parser = argparse.ArgumentParser(description="Fetch data from PostgreSQL database and save as CSV.")
#     parser.add_argument('patient_ids', nargs='+', help='A list of patient IDs separated by commas')
#     parser.add_argument('features', nargs='+', help='A list of features separated by commas')
#     return parser.parse_args()


def load_model(model_file_path):
    # 加载训练好的模型
    model = joblib.load(model_file_path)
    return model


def preprocess_new_data(feature_values):
    # 在预测新数据之前，需要进行与训练数据相同的预处理操作
    # 假设我们对新数据也进行特征标准化和特征选择
    X = np.array(feature_values).reshape(1, -1)
    return X


class MultiTaskModel(nn.Module):
    def __init__(self, input_size, shared_hidden_size, task_hidden_sizes, task_output_sizes):
        super(MultiTaskModel, self).__init__()
        # 共享层
        self.shared_layer = nn.Linear(input_size, shared_hidden_size)
        self.shared_activation = nn.ReLU()

        # 任务特定的层
        self.tasks = nn.ModuleList()
        for hidden_size, output_size in zip(task_hidden_sizes, task_output_sizes):
            task_layers = [
                nn.Linear(shared_hidden_size, hidden_size),
                nn.ReLU(),
                nn.Linear(hidden_size, output_size),
                nn.Sigmoid()  # 假设是二分类任务，使用sigmoid激活函数
            ]
            self.tasks.append(nn.Sequential(*task_layers))

    def forward(self, x):
        shared_output = self.shared_activation(self.shared_layer(x))

        task_outputs = []
        for task in self.tasks:
            task_outputs.append(task(shared_output))
        return task_outputs


# 定义一些超参数
input_size = 26  # 假设我们的输入特征是10维的
shared_hidden_size = 256  # 共享层的隐藏单元数量
task_hidden_sizes = [128, 128]  # 每个任务特定层的隐藏单元数量
task_output_sizes = [1, 1]  # 每个任务的输出大小


def load_data(patient_ids, features, table_name,model_file_path):
    try:
        # 连接到 PostgreSQL 数据库
        conn = psycopg2.connect(
            user="pg",
            password="111111",
            host="10.16.48.219",
            port="5432",
            database="software9"
        )
        cur = conn.cursor()

        # 定义 SQL 查询语句
        query = "SELECT " + ", ".join(
            ['"' + f + '"' for f in features]) + " FROM " + '"' + table_name + '"' + " WHERE patient_id IN %s"

        # 执行查询
        cur.execute(query, (tuple(patient_ids),))

        # 提取查询结果
        results = cur.fetchall()

        # 打印查询结果

        rows = []
        for row in results:
            rows.append(row)



        if len(patient_ids) == 1:
            data_float = []
            for tup in rows:
                row = []
                for val in tup:
                    if val is None or val == 'None':
                        row.append(np.nan)
                    else:
                        row.append(float(val))
                data_float.append(row)

                for i, row in enumerate(data_float):
                    for j, val in enumerate(row):
                        if np.isnan(val):
                            data_float[i][j] = 0


        else:
            data_float = []
            for tup in rows:
                row = []
                for val in tup:
                    if val is None or val == 'None':
                        row.append(np.nan)
                    else:
                        row.append(float(val))
                data_float.append(row)

        # 计算每列的均值
        column_means = np.nanmean(data_float, axis=0)

        # 使用均值进行插补
        for i, row in enumerate(data_float):
            for j, val in enumerate(row):
                if np.isnan(val):
                    data_float[i][j] = 0





        model = load_model(model_file_path)
        predictions = []
        for sample in data_float:
            X = preprocess_new_data(sample)
            X = torch.tensor(X, dtype=torch.float32)
            with torch.no_grad():
                y_pred1, y_pred2 = model(X)

            prediction1 = y_pred1.item()
            prediction2 = y_pred2.item()
            prediction = [prediction1, prediction2]
            predictions.append(prediction)
            # 打印浮点数值部分
        print(predictions)



    except (Exception, psycopg2.Error) as error:
        print("Error fetching data from PostgreSQL table:", error)
    finally:
        # 关闭游标和连接
        if conn:
            cur.close()
            conn.close()
    # 创建游标对象


if __name__ == "__main__":
    # 解析命令行参数
    # args = ["801080447,801030382", "WBC", "NEUT_num", "LYMPH_num", "MONO_num", "EO_num", "BASO_num", "NEUT_per",
    #         "LYMPH_per", "MONO_per", "EO_per", "BASO_per", "RBC", "HGB", "HCT", "MCV", "MCH", "MCHC",
    #         "RDW_SD", "RDW_CV", "PLT", "PCT", "MPV", "P_LCR", "PDW", "sexcode", "age"]

    # # 解析args列表为两个单独的列表
    # # patient_ids = [id.strip() for id in args[0].split(",")]
    # patient_ids = ["801080447", "801030382"]
    # # features = [fea.strip() for fea in args[1].split(",")]
    # features = ["WBC", "NEUT_num", "LYMPH_num", "MONO_num", "EO_num", "BASO_num", "NEUT_per",
    #             "LYMPH_per", "MONO_per", "EO_per", "BASO_per", "RBC", "HGB", "HCT", "MCV", "MCH", "MCHC",
    #             "RDW_SD", "RDW_CV", "PLT", "PCT", "MPV", "P_LCR", "PDW", "sexcode", "age"]

    parser = argparse.ArgumentParser()
    parser.add_argument("--model_file_path", type=str, default=None)
    parser.add_argument("--patient_ids", type=str, default=None)

    parser.add_argument("--feature", nargs='+', type=str, default=None)
    args = parser.parse_args()
    # print(patient_ids)
    # print(features)
    model_file_path = args.model_file_path
    features = args.feature
    patient_ids = args.patient_ids

    # 去除方括号

    # 提取列表中的字符串
    feature_str = features[0]

    # 使用 split() 方法将字符串分割成多个特征名称，并存储在列表中
    features = feature_str.split(',')
    # 使用 split() 方法将字符串分割成多个特征名称，并存储在列表中
    patient_ids = patient_ids.split(',')
    # 调用load_data函数，传入患者ID和特征列表，以及输出文件夹路径
    load_data(patient_ids, features, 'merge',model_file_path)
