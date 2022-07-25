import sys

sys.setrecursionlimit(100000)


class disjointSet():
    def __init__(self, size):
        self.fatherNode = {}
        self.sizeOfFather = {}
        for i in range(size):
            self.fatherNode[i] = i
            self.sizeOfFather[i] = 1;

    def find_father(self, node):
        father = self.fatherNode[node]

        # 若目标是自己的父,说明自己是一个组
        if node != father:
            father = self.find_father(father)
        self.fatherNode[node] = father
        return father

    def same(self, node_a, node_b):
        # 查看两个节点是不是在一个集合里面
        if self.find_father(node_a) == self.find_father(node_b):
            return 1
        return 0

    """合并"""

    def union(self, node_a, node_b):
        if node_a is None or node_b is None:
            return

        a_head = self.find_father(node_a)
        b_head = self.find_father(node_b)

        if a_head != b_head:

            size_a = self.sizeOfFather[node_a]
            size_b = self.sizeOfFather[node_b]
            if size_a >= size_b:
                self.fatherNode[b_head] = a_head
                self.sizeOfFather[a_head] = size_a + size_b
            else:
                self.fatherNode[a_head] = b_head
                self.sizeOfFather[b_head] = size_a + size_b


size, amountOfOperation = input().split(" ")

disjointSet_ = disjointSet(int(size))

for i in range(int(amountOfOperation)):
    operation, node_a, node_b = input().split(" ")
    if int(operation) == 0:
        disjointSet_.union(int(node_a), int(node_b))
    else:
        print(disjointSet_.same(int(node_a), int(node_b)))
