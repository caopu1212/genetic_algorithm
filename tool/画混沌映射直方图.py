import random

import matplotlib.pyplot as plt

import numpy as np
import matplotlib.pyplot as plt


def logistic_map(x0, r, n):
    """
    计算logistic map序列
    """
    x = np.zeros(n)
    x[0] = x0
    for i in range(1, n):
        x[i] = r * x[i - 1] * (1 - x[i - 1])
    return x


# 设定参数
x0 = 0.1
r = 4
n = 500000

# 计算logistic map序列
x = logistic_map(x0, r, n)

# 绘制直方图
plt.hist(x, bins=100, density=True, alpha=0.7, color='navy')
plt.title('Logistic Mapping Distribution')
plt.xlabel('x')
plt.ylabel('Frequency')
plt.savefig('Logistic_Mapping.png')
plt.show()



# def sine_map(r, n):
#     """
#     计算sine map序列
#     """
#     result = []
#     for i in range(1, n):
#         x0 = random.random()
#         result.append(abs(np.sin(np.pi * x0)))
#     return result

def sine_map(x0, r, n):
    """
    计算sine map序列
    """
    x = np.zeros(n)
    x[0] = x0
    for i in range(1, n):
        x[i] = abs(r * np.sin(np.pi * x[i-1]))
    return x



# 设定参数

x0 = 0.152
r = 1
n = 500000

# 计算sine map序列
x = sine_map(x0,r, n)

# 绘制分布直方图

plt.hist(x, bins=100, density=True, alpha=0.7, color='navy')
plt.title('Sine Mapping Distribution')
plt.xlabel('x')
plt.ylabel('Frequency')
plt.savefig('Sine_Mapping.png')
plt.show()


# tent mapping
def tent_map1(r, n):
    """
    计算tent map序列
    """
    result = []
    for i in range(1, n):
        x0 = random.random()
        if x0 <= 0.5:
            result.append(r * x0)
        else:
            result.append(r * (1 - x0))
    return result


# 设定参数
# x0 = 0.1
r = 2
n = 500000
# 计算tent map序列
x = tent_map1(r, n)
# 绘制分布直方图
plt.hist(x, bins=100, density=True, alpha=0.7, color='navy')
plt.title('tent Mapping Distribution')
plt.xlabel('x')
plt.ylabel('Frequency')
plt.savefig('Tent_Mapping.png')
plt.show()


