# path = "C:\Users\Administrator\Desktop\temp_code\BackePropagation-master\data\mnistData.txt"
# path = "C:\Users\Administrator\Desktop\temp_code\BackePropagation-master\data\mnistLabel.txt"

import numpy as np

data = open("C:\\Users\\Administrator\\Desktop\\temp_code\\BackePropagation-master\\data\\testLabel.txt")

lines = data.readlines()
line_lists = []
result_list = []
for line in lines:
    line_lists.append(line.split(","))

for list in line_lists:
    for lable_index in range(len(list)):
        if "1" in list[lable_index]:
            result_list.append(int(lable_index))

# with open("C:\\Users\\Administrator\\Desktop\\temp_code\\BackePropagation-master\\data\\demo.txt", "w") as f:
#     f.writelines(result_list)

np.savetxt("C:\\Users\\Administrator\\Desktop\\temp_code\\BackePropagation-master\\data\\demo1111.txt", result_list)

print(123)
