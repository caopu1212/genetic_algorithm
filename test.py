import numpy as np
import math
import random
import matplotlib.pyplot as plt
# x = np.arange(-10, 10, 0.1)
# y = []
# for t in x:
#     y_1 = 1 / (1 + math.exp(-t))
#     y.append(y_1)
#
# x = [1,2,3,4,5]
# y = [123,5225,3,777,8]
#
# plt.plot(x, y, label="sigmoid")
# plt.xlabel("x")
# plt.ylabel("y")
# plt.ylim(0, 1)
# plt.legend()
# plt.show()
# def temp(T):
#     return T0/math.log10(10+T)
#
# T0 = 100000
# y = []
# T = 100000
# for i in range(0,50):
#     T = temp(T)
#     y.append(T)
#
# print(y)
#
#
#
# print(math.exp(-(5/1)))
#
#
# testList1 = []
# for i in range(0,100):
#     testList1.append(random.randint(1,5))
#
# print(testList1)
#
# for i in range(1):
#     print("a")
#
# def test():
#     return 1,2,3
# a,b,c = test()
# print(a,b,c)


input_A = input()
list_A = input_A.split(" ");
count = 1
inputList = []
while (1):
    inputList.append(input())
    if (count == int(list_A[0])):
        break;
    count += 1;
inputList_A = []
for i in inputList:
    inputList_A.append(i.split(" "))

target = int(list_A[2])-1

result =  int(inputList_A[0][target ])

def calculateLine(start, end, param):
    result = 0
    if end == len(param):
        for i in range(start, end ):
            result = result + int(param[i])
    else:
        for i in range(start, end +1):
            result = result + int(param[i])
    return result
for i in range(1, len(inputList_A)):
    start = 0
    end = int(list_A[1])
    if (target + i <= int(list_A[1])):
        end = target + i
    if (target - i >= 0):
        start = target - i
    result = result + calculateLine(start, end, inputList_A[i])
print(result)



