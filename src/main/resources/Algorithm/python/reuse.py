import argparse
import numpy as np
import joblib


def load_model(model_file_path):
    # 加载训练好的模型
    model = joblib.load(model_file_path)
    return model

def preprocess_new_data(feature_values):
    # 在预测新数据之前，需要进行与训练数据相同的预处理操作
    # 假设我们对新数据也进行特征标准化和特征选择
    X = np.array(feature_values).reshape(1, -1)
    return X

# def make_predictions_proba(model, X):
#     # 使用加载的模型进行预测，并获取预测概率
#     prediction = model.predict(X)
#     return prediction

def make_predictions_proba(model, X):
    # 使用加载的模型进行预测，并获取预测概率
    prediction_proba = model.predict_proba(X)
    # 返回预测为1的概率
    return prediction_proba[0, 1]
if __name__ == "__main__":

    parser = argparse.ArgumentParser()
    parser.add_argument("--model_file_path", type=str, default=None)
    parser.add_argument("--feature", nargs='+', type=str, default=None)
    args = parser.parse_args()
    # 加载训练好的模型
    model_file_path = args.model_file_path
    model = load_model(model_file_path)
    feature = args.feature[0].split()
    fea_float = [float(num_str) for num_str in feature]
    X = preprocess_new_data(fea_float)
    # prediction = make_predictions_proba(model, X)
    # print(prediction)
    prediction = make_predictions_proba(model, X)
    prediction_proba = [prediction]
    print(prediction_proba)
    # 使用加载的模型进行预测



