import matplotlib.pyplot as plt
import numpy as np
#生成x步长为0.1的列表数据
x = np.arange(-15,15,0.1)
#生成sigmiod形式的y数据
y=1/(1+np.exp(-x))
#设置x、y坐标轴的范围
plt.xlim(-12,12)
plt.ylim(-1, 1)
#绘制图形
plt.plot(x,y, c='b')
plt.show()
