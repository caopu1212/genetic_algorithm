import random


def creare_place():
    count = 0
    list = []
    while (count < 100):
        sub_list = []
        sub_list.append(random.randint(0, 100))
        sub_list.append(random.randint(0, 100))
        list.append(sub_list)
        count = count + 1
    return list
max()

if __name__ == '__main__':
    print(creare_place())
