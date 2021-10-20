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

dungeon1 = np.array([[0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1],
                     [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1],
                     [0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1],
                     [0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1],
                     [0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1],
                     [0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1],
                     [0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1],
                     [0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1],
                     [0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1],
                     [0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0],
                     [0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1],
                     [0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1],
                     [0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0],
                     [0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1],
                     [0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                     [0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                     [0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
                     [0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                     [0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0],
                     [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]])

dungeon = np.array([[0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1],
                    [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1],
                    [0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1],
                    [0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1],
                    [0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1],
                    [0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1],
                    [0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1],
                    [0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1],
                    [0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1],
                    [0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0],
                    [0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1],
                    [0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1],
                    [0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1],
                    [0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                    [0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                    [0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
                    [0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0],
                    [0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1],
                    [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    ])


species = []
blind_alleys = []  # 所有的墙
fitness_list = []
dungeon_copy = []
new_generation = []  # 新世代


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
    global blind_alleys
    blind_alleys = result_list
    return result_list


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
    if current_position[1] == 0 and current_position[0] == len(dungeon) - 1:
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


# 验证是否死路,返回可行的路
def validate_path(result_map, key_list, current_position):
    for key in key_list:
        # print(len(result_map))
        # print(result_map)
        if (dungeon_copy[result_map[key][0], result_map[key][1]] == 1 or result_map[key][1] <= -1):
            del result_map[key]

        # 如果是死路，修改成墙
        if len(result_map) == 1:
            x = current_position[0]
            y = current_position[1]
            dungeon_copy[x][y] == 1
    # print(dungeon_copy)
    return result_map


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
        chromosome.append(last_move[0])  # 坐标
        # chromosome.append(act_to_binary(last_move[1])) # 行动方向
        # 标记路径
        # dungeon_copy[last_move[0][0]][last_move[0][1]] += 2
        # print("路径: " + last_move[1] + " 地址: " + str(last_move[0]))
    # print(record)
    # print(chromosome)

    return chromosome


# 将行动转为坐标
def decode(chromosome):
    current_position = [0, 0]
    decoded_chromosome = []
    for i in chromosome:
        if i == '00':
            current_position = midify_postion("row", -1, current_position)
            decoded_chromosome.append(current_position)
        elif i == '01':
            current_position = midify_postion("row", 1, current_position)
            decoded_chromosome.append(current_position)
        elif i == '10':
            current_position = midify_postion("column", -1, current_position)
            decoded_chromosome.append(current_position)
        elif i == '11':
            current_position = midify_postion("column", 1, current_position)
            decoded_chromosome.append(current_position)
    return decoded_chromosome


# 计算fitness ，步数越少，fitness越高
def calculate_fitness(decoded_chromosome):
    # 如果路径中包含墙，则返回0
    for dna in decoded_chromosome:
        if dna in blind_alleys:
            return 0
    result_fitness = len(decoded_chromosome) ** -1 * 10 ** 5  # 反比例函数，x越小，y越大
    return result_fitness


# 轮盘赌 选择 指定数量的物种进入新世代
def selection(amount_of_inheritance):
    # amount_of_heritage: 继承到新世代的个体的个数
    for i in range(0, amount_of_inheritance):
        temp_list = russian_roulette_select(fitness_list)
        if fitness_list[temp_list[0]] < fitness_list[temp_list[1]]:
            new_generation.append(species[temp_list[0]])
        else:
            new_generation.append(species[temp_list[1]])


"""√4.父中随机找一个点，取这个点第一次出现的位置（同3），剩下同1"""


# 杂交
def crossover():
    global new_generation
    # 获得最强的个体
    most_strong_chromosome = species[fitness_list.index(max(fitness_list))]  # 先获得目标的index
    # 用最强一个的填满新世代
    while 1:
        if len(new_generation) == len(species):
            break
        new_generation.append(most_strong_chromosome)

    temp_generation = []
    # 偶数个体和奇数个体杂交
    odd_chromosome_list = new_generation[::2]  # 偶数
    even_chromosome_list = new_generation[1::2]  # 奇数
    for index in range(0, len(odd_chromosome_list)):

        father_chromosome = odd_chromosome_list[index]
        random_element = random.choice(father_chromosome)  # 随机list里选一个元素

        father_index = first_time_appear(random_element, father_chromosome)

        mother_chromosome = even_chromosome_list[index]
        mother_index = last_time_appear(random_element, mother_chromosome)
        if mother_index != 0:
            father_chromosome_part = father_chromosome[0:father_index]
            mother_chromosome_part = mother_chromosome[mother_index:]
        else:
            mother_index = last_time_appear(random_element, father_chromosome)
            father_chromosome_part = father_chromosome[0:father_index]
            mother_chromosome_part = father_chromosome[mother_index:]

        # 父的前半+母的后半组成子代
        posterity = father_chromosome_part + mother_chromosome_part
        posterity = mutation(5, posterity)  # 突变
        temp_generation.append(posterity)

    new_generation = temp_generation

    # 获得最强的个体
    most_strong_chromosome = species[fitness_list.index(max(fitness_list))]  # 先获得目标的index
    # 用最强一个的填满新世代
    while 1:
        if len(new_generation) == len(species):
            break
        new_generation.append(most_strong_chromosome)


# 变异  随机选一个点，随机生成这个点到终点的路径
def mutation(rate, target_chromosome):
    mutation_rate = random.randint(0, 100)
    random_index = random.randint(0, len(target_chromosome) - 1)
    if mutation_rate <= rate:
        new_way = get_new_individual(target_chromosome[random_index])
        target_chromosome = target_chromosome[0:random_index + 1] + new_way
    return target_chromosome


def initialize():
    global fitness_list
    fitness_list = []
    global new_generation
    global species
    species = new_generation
    new_generation = []
    for i in species:
        fitness_list.append(calculate_fitness(i))


def initialize_species(cycle):
    get_blind_alley()  # 获得所有墙
    count = 0
    while count < cycle:
        species.append(get_new_individual([0, 0]))
        count = count + 1
    for i in species:
        fitness_list.append(calculate_fitness(i))


if __name__ == '__main__':
    # chromosome = [['01'], ['10'], ['00'], ['01'], ['00'], ['11'], ['11']]
    # temp = decode(chromosome)
    # fitness = calculate_fitness(temp)

    initialize_species(50)  # 入参：每一代的物种个数
    rounds = 5000
    count = 0
    while count < rounds:
        selection(int(len(species) / 1.2))  # 入参：保留到下一代的个体数量
        crossover()

        initialize()
        # 获得最强的个体
        # print(species[fitness_list.index(max(fitness_list))])  # 先获得目标的index
        # print("fitness : " + str(max(fitness_list)))
        print("路径长度：" + str(len(species[fitness_list.index(max(fitness_list))])))
        count = count + 1
