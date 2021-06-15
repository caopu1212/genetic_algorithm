from LogicalService import *
import numpy as np

"""
正如组合问题与动态规划的联系之应用提到的从起点(0,0)到终点(X,Y)一共有多少种走法。与之相似的另一个问题是如何找到从(0,0)到(X,Y)的路径？
首先对问题建模。使用一个矩阵(二维数组)的下标 表示 各个点的坐标。矩阵元素只取 0 或者 1，0 表示此坐标是一个可达的正常顶点；而 1 则表示这是一个不可达的障碍顶点

"""

"""
TODO LIST
# 1.想出如何映射,编码                           
路径上下左右对应00,01,10,11
问题: 随机生成的路线，长度不固定
解决方案：
1. 不处理， 在交叉时随机选取的gene snippet取值范围只取前面一部分的
2. 


# 2.随机连接起点到终点 路径的 函数                 ********（done）**********
# 3.写编码解码的函数                                ********（done）**********
# 4.计算fitness的函数（步数越短fitness越高，杂交完之后验证可否走通，走不通的fitness为0）   ********（done）**********
# 5.优化路线生成（防止重复左右横跳）
"""
blind_alleys = []

fitness_list = []

def initialize_species(cycle):
    get_blind_alley()  # 获得所有死路
    count = 0
    while count < cycle:
        species.append(get_new_individual([0, 0]))
        count = count + 1

    for i in species:
        fitness_list.append(calculate_fitness(i))



if __name__ == '__main__':
    chromosome = [['01'], ['10'], ['00'], ['01'], ['00'], ['11'], ['11']]
    temp = decode(chromosome)
    fitness = calculate_fitness(temp)

    initialize_species(10)
    print(fitness_list)
