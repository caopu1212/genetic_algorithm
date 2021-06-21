import random
import numpy as np
from BaseUtils import *

list = [1, 23, 4, 5, 6, 4, 5, 6, 2, 4, 6, 8]
list2 = [1, 23, 4, 5, 6, 4, 5, 6, 2, 4, 6, 8]

a = first_time_appear(4, list)
b = last_time_appear(4,list2)

aa = list[0:a]
bb = list2[b:]

aabb = aa + bb
a = a
