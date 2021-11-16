import random
import numpy as np
from PathSearching import *

"""
基础工具模块，如字符操作，数据类型转换等
"""


# column_or_row: 列或行 calculation：运算数 （1 or -1）  target_position 目标位置
def midify_postion(column_or_row, calculation, target_position):
    result = [1] * 2  # 初始化数组长度为2
    result[0] = target_position[0]
    result[1] = target_position[1]
    if column_or_row == "row":
        result[0] = result[0] + calculation
    if column_or_row == "column":
        result[1] = result[1] + calculation
    return result


# 获得随机的一步移动，返回map{移动方向：坐标}
def get_random_position(target_map):
    result_map = {}
    result_list = []
    temp_list = []
    for key in target_map:
        temp_list.append(key)
    # temp_list = {"UP", "DOWN", "LEFT", "RIGHT"}
    random_num = random.randint(0, len(temp_list) - 1)
    random_move = temp_list[random_num]
    # result_map[random_move] = target_map[random_move]
    result_list.append(target_map[random_move])
    result_list.append(random_move)
    return result_list


# 转义:上下左右转为00，01,10,11
def act_to_binary(act):
    binarys = {
        "UP": '00',
        "DOWN": '01',
        "LEFT": '10',
        "RIGHT": '11'
    }
    return binarys.get(act, None)


# 轮盘赌 选择
# 先用个体适应度 / 总适应度 求出每个个体被选到的概率， 然后从0-1之间随机选数字，所在的区间对应 之前的得到的概率 得到结果。
def russian_roulette_select(fitness_list):
    total_fitness = sum(fitness_list)  # 所有适应度的总和
    possibilities_list = []  # 个体概率list
    cumulative_possibility_list = []  # 累计概率list
    cumulative_possibility = 0  # 累计概率
    random_list = np.random.random(2)  # 获得两个0-1之间的随机数
    result_index_list = []
    # 获得可能性list for each chromosome
    for i in fitness_list:
        individual_possibilities = i / total_fitness  # 个体概率
        possibilities_list.append(individual_possibilities)
        cumulative_possibility = cumulative_possibility + individual_possibilities
        cumulative_possibility_list.append(cumulative_possibility)
    test = sum(possibilities_list)

    # 随机获得了两个染色体的index
    for random in random_list:
        index = 0
        while 1:
            if random < cumulative_possibility_list[index]:
                result_index_list.append(index)
                break
            index = index + 1
    return result_index_list


# 找一个点第一次出现的位置
def first_time_appear(target_element, target_list):
    result_index = target_list.index(target_element)
    return result_index


# 找一个点最后一次出现的位置
def last_time_appear(target_element, target_list):
    reversed_list = []
    # 若不存在目标坐标，则返回0
    if target_element in target_list:
        positive_order_index = target_list.index(target_element)
        # 若出现不止一次，则倒序list后获得最后一次出现的index, 返回倒序索引（负数）
        # if target_list.count(target_element) > 1:
        for i in reversed(target_list):
            reversed_list.append(i)
        negative_order_index = reversed_list.index(target_element)
        return negative_order_index * -1 - 1
        # return positive_order_index
    else:
        return 0
