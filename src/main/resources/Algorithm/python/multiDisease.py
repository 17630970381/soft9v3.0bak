import argparse
import numpy as np
import joblib
import torch.nn as nn
from torch import torch


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

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--model_file_path", type=str, default=None)
    parser.add_argument("--feature", nargs='+', type=str, default=None)
    args = parser.parse_args()
    # # # 加载训练好的模型
    model_file_path = args.model_file_path
    model = load_model(model_file_path)
    feature = args.feature[0].split()
    fea_float = [float(num_str) for num_str in feature]
    # fea_float = [8.4, 2.19, 1.62, 0.63, 0.05, 0.05, 48.2, 35.7, 13.9, 1.1, 1.1, 4.85, 152, 42.9, 88.5, 31.3, 354, 37.6, 11.8, 215, 0.21, 9.8, 23.4, 10.5, 1, 79]
    X = preprocess_new_data(fea_float)
    X = torch.tensor(X, dtype=torch.float32)
    with torch.no_grad():
        y_pred1, y_pred2 = model(X)

    prediction1 = y_pred1.item()
    prediction2 = y_pred2.item()

    # 打印浮点数值部分
    prediction = [prediction1, prediction2]
    # 打印浮点数值部分
    print(prediction)
