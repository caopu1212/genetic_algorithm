'''
背包问题
例;5个物品
价格[1,3,6,9,5]
重量[5,6,4,8,6]
总重量不能超过15，求最优解


比如5个物品：10110（1未选择，0为未选择）




'''
import copy
import random
import math
import time
import question

weight = copy.copy(question.data("M")[0])
price = copy.copy(question.data("M")[1])

# 验证修改后是否符合规则
def validate(list):
    total_capacity = 0
    for i in range(len(list)):
        if list[i] == 1:
            total_capacity += weight[i]
    if total_capacity > capacity:
        return False
    else:
        return True


# 随机修改N个值

def modify(list):
    origin_list = copy.copy(list)
    while (1):
        # print("test")
        for i in range(1):
            random_index = random.randint(0, len(list) - 1)
            if list[random_index] == 1:
                list[random_index] = 0
            else:
                list[random_index] = 1

        if validate(list):
            break
        else:
            # 优化：若超重了，则随机放放下一个
            index_list = []
            for i in range(0, len(list)):
                if list[i] == 1:
                    index_list.append(i)
            random_index = index_list[random.randint(0, len(index_list) - 1)]
            list[random_index] = 0

        # list = copy.copy(origin_list)
    return list


# 计算fitness
def calculate_fitness(list,price = price):
    total_price = 0
    for index in range(len(list)):

        if list[index] == 1:
            total_price += price[index]
    return total_price


def calculate_weight(list):
    total_weight = 0
    for index in range(len(list)):

        if list[index] == 1:
            total_weight += weight[index]
    return total_weight


# 扰动 方法
def perturbate(solution):
    initialize_solution = get_initialize_solution()
    result_solution = copy.copy(initialize_solution)
    '''
      扰乱策略：
      1.全部清零，随机生成一个符合规则的答案
      2.保持当前答案N个，剩下清零
      '''
    # 方法一：
    while (1):

        if calculate_fitness(result_solution) >= capacity / 4:
            break
        else:
            # random_num = random.randint(0, len(initialize_solution) - 1)
            modify(result_solution)

    return result_solution


def main_logic(round, localStable_count, strat_list):
    localBest_count = 0
    new_solution = strat_list

    localBest_solution = []
    globalBest_solution = []
    start = time.time()

    # 找局部最优，当持续N轮没变的话，判断为局部最优，进行扰乱~
    for i in range(1, round):
        while (1):
            new_solution = modify(new_solution)
            new_price = calculate_fitness(new_solution)
            locaBest_price = calculate_fitness(localBest_solution)
            # 找局部最优
            if (new_price > locaBest_price):
                localBest_solution = copy.copy(new_solution)
            else:
                localBest_count += 1
            # 若连续N轮，局部最优解没有更新，则认为已达到局部最优
            if localBest_count == localStable_count:
                localBest_count = 0

                # 在扰乱前，将局部最优和全局最优解比较，更新全局最优
                if calculate_fitness(localBest_solution) > calculate_fitness(globalBest_solution):
                    globalBest_solution = copy.copy(localBest_solution)
                # 扰动
                new_solution = copy.copy(perturbate(localBest_solution))
                break
        if calculate_fitness(globalBest_solution) >= 300:
            break
        end = time.time()
        # 运行时间
        # if end-start >= 1:
        #     break
        # print(i)
    return globalBest_solution


def get_initialize_solution():
    result = []
    for i in weight:
        result.append(0)
    return result


if __name__ == '__main__':
    initialize_solution = get_initialize_solution()
    # 背包容量
    capacity = 15


    time_list = []
    fitness_list = []
    for i in range(0, 20):
        start = time.time()
        '''param:循环轮数，判定为局部最优的阈值，初始解 '''
        temp = main_logic(100000, 30, initialize_solution)
        print("solution：" + str(temp))
        print("总价格：" + str(calculate_fitness(temp)))
        print("总重：" + str(calculate_weight(temp)))
        print("时间" + str(time.process_time()))

        end = time.time()
        time_list.append(end-start)
        fitness_list.append(calculate_fitness(temp))

    # for i in time_list:
    #     print (i)
    # for i in fitness_list:
    #     print (i)



























