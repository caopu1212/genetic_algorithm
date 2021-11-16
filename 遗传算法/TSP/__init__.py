import numpy as np

# 生成城市坐标
city_num = 100  # 城市数量
name = ["city's name"] * city_num  # 这个并没什么用，但是不要省，省了的话还要修改代码
x = [np.random.randint(0, 100) for i in range(city_num)]
y = [np.random.randint(0, 100) for i in range(city_num)]
with open("cities.csv", "w") as f:
    for i in range(city_num):
        f.write(name[i] + "," + str(x[i]) + "," + str(y[i]) + "\n")
    f.write(name[0] + "," + str(x[0]) + "," + str(y[0]) + "\n")  # 最后一个节点即为起点
