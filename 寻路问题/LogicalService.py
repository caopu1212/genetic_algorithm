import random
import numpy as np

from BaseUtils import *

"""
具体实现逻辑
"""

"""level 1"""
# dungeon = np.array([[0, 0, 0, 0, 1],
#                     [1, 0, 0, 0, 1],
#                     [0, 0, 0, 1, 1],
#                     [0, 1, 0, 0, 0],
#                     [0, 0, 0, 1, 0]])
"""level 2"""

dungeon = np.array([[0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1],
                    [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1],
                    [0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1],
                    [0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1],
                    [1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1],
                    [0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1],
                    [0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1],
                    [1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1],
                    [0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1],
                    [0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0],
                    [0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1],
                    [0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1],
                    [0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1],
                    [1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                    [0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                    [0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
                    [0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0],
                    [0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0]])

species = []


dungeon_copy = []

# 找到所有的墙
def get_blind_alley():
    result_list = []
    my_dungeon = dungeon.tolist()
    for i in range(0, len(my_dungeon)):
        while 1:
            if 1 in my_dungeon[i]:
                index = my_dungeon[i].index(1)
                # temp = np.argwhere(dungeon[i])
                result_list.append([i, index])
                my_dungeon[i][index] = 0
            else:
                break
    return result_list


# 验证是否死路,返回可行的路
def validate_path(result_map, key_list, current_position):


    for key in key_list:
        if (dungeon_copy[result_map[key][0], result_map[key][1]] == 1):
            del result_map[key]

        #如果是死路，修改成墙
        if len(result_map) == 1:
            x = current_position[0]
            y = current_position[1]
            dungeon_copy[x][y] == 1

    return result_map


# 获得目标位置的周围可移动的地址（已考虑是否为边界）
def get_around_position(current_position):
    move = {}
    key_list = ['UP', 'DOWN', 'RIGHT', 'LEFT']
    move['UP'] = midify_postion("row", -1, current_position)
    move['DOWN'] = midify_postion("row", 1, current_position)
    move['RIGHT'] = midify_postion("column", 1, current_position)
    move['LEFT'] = midify_postion("column", -1, current_position)

    result_map = {}

    # 如果 在左上
    if current_position[1] == 0 and current_position[0] == 0:
        result_map['DOWN'] = move['DOWN']
        result_map['RIGHT'] = move['RIGHT']
        del key_list[key_list.index('LEFT')]
        del key_list[key_list.index('UP')]
        return validate_path(result_map, key_list, current_position)

    # 如果 在右上
    if current_position[1] == len(dungeon[0]) - 1 and current_position[0] == 0:
        result_map['LEFT'] = move['LEFT']
        result_map['DOWN'] = move['DOWN']
        del key_list[key_list.index('UP')]
        del key_list[key_list.index('RIGHT')]
        return validate_path(result_map, key_list, current_position)

    # 如果 在左下
    if current_position[1] == 0 and current_position[0] == len(dungeon[1]) - 1:
        result_map['RIGHT'] = move['RIGHT']
        result_map['UP'] = move['UP']
        del key_list[key_list.index('DOWN')]
        del key_list[key_list.index('LEFT')]
        return validate_path(result_map, key_list, current_position)
    # 如果在最后一行
    if current_position[0] == len(dungeon) - 1:
        result_map['LEFT'] = move['LEFT']
        result_map['UP'] = move['UP']
        result_map['RIGHT'] = move['RIGHT']
        del key_list[key_list.index('DOWN')]
        return validate_path(result_map, key_list, current_position)
    # 如果在最后一列
    if current_position[1] == len(dungeon[0]) - 1:
        result_map['LEFT'] = move['LEFT']
        result_map['DOWN'] = move['DOWN']
        result_map['UP'] = move['UP']
        del key_list[key_list.index('RIGHT')]
        return validate_path(result_map, key_list, current_position)
    # 如果在第一行
    if current_position[0] == 0:
        result_map['DOWN'] = move['DOWN']
        result_map['RIGHT'] = move['RIGHT']
        result_map['LEFT'] = move['LEFT']
        del key_list[key_list.index('UP')]
        return validate_path(result_map, key_list, current_position)
    # 如果列为0
    if current_position[1] == 0:
        result_map['UP'] = move['UP']
        result_map['RIGHT'] = move['RIGHT']
        result_map['DOWN'] = move['DOWN']
        del key_list[key_list.index('LEFT')]
        return validate_path(result_map, key_list, current_position)

    result_map['LEFT'] = move['LEFT']
    result_map['RIGHT'] = move['RIGHT']
    result_map['DOWN'] = move['DOWN']
    result_map['UP'] = move['UP']
    return validate_path(result_map, key_list, current_position)


# 获得一条随机通往终点的路径,返回一条染色体
def get_new_individual(start_position):
    last_move = [start_position]  # 起点
    record = []
    chromosome = []
    # 到了终点则停止
    global dungeon_copy
    dungeon_copy = dungeon

    while 1:
        if (last_move[0][0] == len(dungeon) - 1 and last_move[0][1] == len(dungeon[0]) - 1):
            break

        last_move = get_random_position(get_around_position(last_move[0]))

        # record.append(last_move[0])
        chromosome.append(act_to_binary(last_move[1]))
        # print("路径: " + last_move[1] + " 地址: " + str(last_move[0]))
    # print(record)
    # print(chromosome)
    print("success")
    return chromosome


# 将行动转为坐标
def decode(chromosome):
    current_position = [0, 0]
    decoded_chromosome = []
    for i in chromosome:
        if i == ['00']:
            current_position = midify_postion("row", -1, current_position)
            decoded_chromosome.append(current_position)
        elif i == ['01']:
            current_position = midify_postion("row", 1, current_position)
            decoded_chromosome.append(current_position)
        elif i == ['10']:
            current_position = midify_postion("column", -1, current_position)
            decoded_chromosome.append(current_position)
        elif i == ['11']:
            current_position = midify_postion("column", 1, current_position)
            decoded_chromosome.append(current_position)
    return decoded_chromosome


# 计算fitness ，步数越少，fitness越高
def calculate_fitness(decoded_chromosome):
    blind_alleys = get_blind_alley()
    # 如果路径中包含墙，则返回0
    for dna in decoded_chromosome:
        if dna in blind_alleys:
            return 0
    result_fitness = len(decoded_chromosome) ** -1 * 10 ** 5  # 反比例函数，x越小，y越大
    return result_fitness

# 轮盘赌 选择
# 先用个体适应度 / 总适应度 求出每个个体被选到的概率， 然后从0-1之间随机选数字，所在的区间对应 之前的得到的概率 得到结果。
# def selection():


# 交叉
# def crossover():


