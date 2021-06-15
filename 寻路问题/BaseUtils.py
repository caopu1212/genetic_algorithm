import random
import numpy as np

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
