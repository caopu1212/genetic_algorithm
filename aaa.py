import sys
import math
lineList = []
angleList = []

target_line_list = []
target_angle_list = []

front_angle_list = []
back_angle_list = []


def main(lines):
    # このコードは標準入力と標準出力を用いたサンプルコードです。
    # このコードは好きなように編集・削除してもらって構いません。
    # ---
    # This is a sample code to use stdin and stdout.
    # Edit and remove this code as you like.

    for i, v in enumerate(lines):
        temp_list = str(v).split(" ")
        if len(temp_list) == 2:
            # print(temp_list)
            angleList.append(float(temp_list[0]))
            lineList.append(float(temp_list[1]))


def getTarget():
    tempCount = 0
    biggest_lines = [0,0]
    temp_angles = [0,0]
    for i in range(1, len(lineList)):
        if (lineList[i] != 1000.00000):
            if (biggest_lines[0] < lineList[i]):
                biggest_lines[0] = lineList[i]
                temp_angles[0] = angleList[i]
            else:
                if (biggest_lines[1] < lineList[i]):
                    biggest_lines[1] = lineList[i]
                    temp_angles[1] = angleList[i]

            if (lineList[i] > lineList[i - 1] and tempCount == 0):
                target_line_list.append(lineList[i - 1])
                target_angle_list.append(angleList[i - 1])
                tempCount = 1;
            if (abs(lineList[i] - lineList[i + 1]) > 1 or lineList[i + 1] == 1000):
                # print(lineList[i])
                front_angle_list.append(temp_angles[0])
                back_angle_list.append(temp_angles[1])
                biggest_lines = [0,0]
                temp_angles = [0,0]

                tempCount = 0;


def calculatte():
    for i in (range(len(target_line_list))):
        long_line = target_line_list[i] + 1
        angle_O = (back_angle_list[i] - front_angle_list[i])/2 +abs(front_angle_list[i])

        y = math.sin(angle_O) * long_line
        x = math.sqrt(long_line**2 - y**2 )
        print(x,y)



if __name__ == '__main__':
    lines = []
    for l in sys.stdin:
        lines.append(l.rstrip('\r\n'))
    main(lines)
    getTarget()
    # print(angleList)
    # print(lineList )
    # print(target_angle_list)
    # print(target_line_list)
    # print(front_angle_list)
    # print(back_angle_list)

    print(len(target_angle_list))
    calculatte()