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

## 数据集：
# S ：10
# M ：100
# L ：1000
weight = copy.copy(question.data("S")[0])
price = copy.copy(question.data("S")[1])

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
            for i in range(0,len(list)):
                if list[i] == 1:
                    index_list.append(i)
            random_index =index_list[random.randint(0,len(index_list)-1)]
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


def main_logic(round, strat_list, T):
    last_solutions = []
    new_solution = strat_list

    best_solution = []
    start = time.time()
    for i in range(0, round):
        new_solution = modify(new_solution)

        new_price = calculate_fitness(new_solution)
        last_price = calculate_fitness(last_solutions)

        if (new_price >= last_price):
            last_solutions = copy.copy(new_solution)
        else:
            # exp(-(f_old-f_new)/T)
            # 退火计算
            temp = math.exp(-( last_price-new_price) / T)
            if temp > random.random():
                last_solutions = copy.copy(new_solution)

            # print(new_price)
            # print("T: " + str(T))
            # print(temp)
        if new_price >= calculate_fitness(best_solution):
            best_solution = copy.copy(new_solution)
        # print(new_price)
        # 降温
        # T = 0.9 * T
        T = initialized_temperature/(1+T)
        if calculate_fitness(best_solution) >= 200:
            break
        end = time.time()
        # 运行时间
        # if end-start >= 1:
        #     break
    return best_solution


def get_initialize_solution():
    result = []
    for i in weight:
        result.append(0)
    return result


if __name__ == '__main__':
    capacity = 15

    initialized_temperature = 1000

    count = 0

    initialize_solution = get_initialize_solution()
    time_list = []
    fitness_list = []
    for i in range(0, 20):
        start = time.time()
        temp = main_logic(10000000, initialize_solution, initialized_temperature)
        print("solution：" + str(temp))
        print("总价格：" + str(calculate_fitness(temp)))
        print("总重：" + str(calculate_weight(temp)))
        print("时间" + str(time.process_time()))

        end = time.time()
        time_list.append(end-start)
        fitness_list.append(calculate_fitness(temp))

    for i in time_list:
        print (i)
    for i in fitness_list:
        print (i)
