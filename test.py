import random
import numpy as np
import GoodGameHaveFun

list1 = [1, 23, 4, 5, 6, 4, 5, 6, 2, 4, 6, 8]
list2 = [1, 23, 4, 5, 6, 4, 5, 6, 2, 4, 6, 8]

# testList = map(lambda x, y: x + y, list1, list2)
#
# testList = map(lambda x: x * 2, testList)

testList = filter(lambda x: x * 2,
                  map(lambda x: x * 2,
                      map(lambda x, y: x + y, list1,
                          map(lambda x: x ** 2, list1))))

print(list(testList))

GoodGameHaveFun.test()


def test(a, b):
    return a + b


def test2(function, b):
    return function(b, b)


print(test2(test, 1))
