import time
import numpy as np
import math


def normalize(x):  # 单位化向量
    norm = math.sqrt(sum(e ** 2 for e in x))
    return x / norm


def sign(a):  # 符号函数
    if a > 0:
        return 1
    elif a < 0:
        return -1
    else:
        return 0


def f(input):  # 测试函数（目标为求得函数最小值，此函数极值点为0）
    x = input[0]

    y = input[1]
    result = -20 * np.exp(-0.2 * np.sqrt((x * x + y * y) / 2)) - np.exp(
        (np.cos(2 * np.pi * x) + np.cos(2 * np.pi * y)) / 2) + 20 + np.exp(1)
    return result


time_start = time.time()  # 计时器

eta = 0.95  # 步长调整比例
iter = 100 # 迭代次数
step = 1 # 初始搜索步长
d0 = 5 # 触须间距
k = 2 # 变量维数
x = np.random.rand(k)  # 随机生成天牛质心坐标
xl = x  # 左触须坐标
xr = x # 右触须坐标
for i in range(iter):   # 开始迭代
    dir = np.random.rand(k)
    dir = normalize(dir)
    xl = x + d0 * dir / 2
    xr = x - d0 * dir / 2
    fl = f(xl)
    fr = f(xr)
    x = x - step * dir * sign(fl - fr)
    step *= eta
    print(x)

time_end = time.time()
print('time cost', time_end - time_start, 's')
