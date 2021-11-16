class Person(object):
    person_count = 0

    def __init__(self, name, age, gender):
        self.age = age
        self.name = name
        self.gender = gender
        Person.person_count += 1

    def display(self):
        print("姓名: " + self.name + "年龄: " + str(self.age) + "性别: " + self.gender)


class Person_son():

    def __init__(self, name, age, gender,salary):
        self.age = age
        self.name = name
        self.gender = gender
        self.salary  = salary


person1 = Person("张三", 11, "男")
person2 = Person("李四", 12, "女")
person3 = Person("王麻子", 13, "男女男")


def test():
    person3.gender = "aaaa"


test()

person3.display()

del person2

person_son1 = Person_son("王麻子", 13, "男女男",1234)
person_son2 = Person_son("王麻子", 13, "男女男",1234)
person1.son  = person_son1


a = 1

a = [1,23,4,5,6,23,4,5]
print(list(set(a)))





