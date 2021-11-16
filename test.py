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
def temp(T):
    return T0/math.log10(10+T)

T0 = 100000
y = []
T = 100000
for i in range(0,50):
    T = temp(T)
    y.append(T)

print(y)



print(math.exp(-(5/1)))


testList1 = []
for i in range(0,100):
    testList1.append(random.randint(1,5))

print(testList1)

for i in range(1):
    print("a")
