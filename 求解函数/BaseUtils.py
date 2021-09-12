import random

"""
基础工具模块，如字符操作，数据类型转换等
"""

"""
# 二转十 入str 出int
"""
def binary_to_decimal(binary_num):
    return int(str(binary_num), 2)


"""
# 十转二 入参int， return String
"""
def decimal_to_binary(decimal_num):
    return bin(decimal_num).replace("0b", "").zfill(4)  # 高位补零到4位