import numpy as np

class LinearRegression:
    def __init__(self, dataset):
        self.dataset = dataset
        self.alpha = 0.0001  # alpha 是学习率

    def get_theta(self, theta):
        num_params = len(self.dataset[0])
        new_gradients = [0] * num_params
        m = len(self.dataset)
        for i in range(0, len(self.dataset)):
            predicted = self.get_prediction(theta, self.dataset[i])
            actual = self.dataset[i][-1]
            for j in range(0, num_params):
                x_j = 1 if j == 0 else self.dataset[i][j - 1]
                new_gradients[j] += (predicted - actual) * x_j

        new_theta = [0] * num_params
        for j in range(0, num_params):
            new_theta[j] = theta[j] - self.alpha * (1/m) * new_gradients[j]

        return new_theta

    def get_prediction(self, theta, data_point):
        # 使用点乘
        # y = mx + b 可以重写为 [b m] dot [1 x]
        # [b m] 是参数
        # 代入x的值
        values = [0]*len(data_point)
        for i in range(0, len(values)):
            values[i] = 1 if i == 0 else data_point[i-1]

        prediction = np.dot(theta, values)
        return prediction

    def calc_cost(self, theta):
        sum = 0
        for i in range(0, len(self.dataset)):
            predicted = self.get_prediction(theta, self.dataset[i])
            actual = self.dataset[i][-1]
            diff_sq = (predicted - actual) ** 2
            sum += diff_sq

        cost = sum / (2*len(self.dataset))
        return cost

    def iterate(self):
        num_iteration = 0
        current_cost = None
        current_theta = [0] * len(self.dataset[0])  # initialize to 0

        while num_iteration < 500:
            if num_iteration % 10 == 0:
                print('current iteration: ', num_iteration)
                print('current cost: ', current_cost)
                print('current theta: ', current_theta)
            new_cost = self.calc_cost(current_theta)
            current_cost = new_cost
            new_theta = self.get_theta(current_theta)
            current_theta = new_theta
            num_iteration += 1

        print(f'After {num_iteration}, total cost is {current_cost}. Theta is {current_theta}')



x = [58, 62, 60, 64, 67, 70] # 妈妈的身高
y = [60, 60, 58, 60, 70, 72] # 女儿的身高


class LinearRegression1:
    def __init__(self, x_set, y_set):
        self.x_set = x_set
        self.y_set = y_set
        self.alpha = 0.0001  # alpha 是学习率

    def get_theta(self, theta):
        intercept, slope = theta
        intercept_gradient = 0
        slope_gradient = 0
        m = len(self.y_set)
        for i in range(0, len(self.y_set)):
            x_val = self.x_set[i]
            y_val = self.y_set[i]
            y_predicted = self.get_prediction(slope, intercept, x_val)
            intercept_gradient += (y_predicted - y_val)
            slope_gradient += (y_predicted - y_val) * x_val

        new_intercept = intercept - self.alpha * intercept_gradient
        new_slope = slope - self.alpha * (1/m) * slope_gradient
        return [new_intercept, new_slope]

    def get_prediction(self, slope, intercept, x_val):
        return slope * x_val + intercept

    def calc_cost(self, theta):
        intercept, slope = theta
        sum = 0
        for i in range(0, len(self.y_set)):
            x_val = self.x_set[i]
            y_val = self.y_set[i]
            y_predicted = self.get_prediction(slope, intercept, x_val)
            diff_sq = (y_predicted - y_val) ** 2
            sum += diff_sq

        cost = sum / (2*len(self.y_set))
        return cost

    def iterate(self):
        num_iteration = 0
        current_cost = None
        current_theta = [0, 0]  # 初始化为0

        while num_iteration < 500:
            if num_iteration % 10 == 0:
                print('current iteration: ', num_iteration)
                print('current cost: ', current_cost)
                print('current theta: ', current_theta)
            new_cost = self.calc_cost(current_theta)
            current_cost = new_cost
            new_theta = self.get_theta(current_theta)
            current_theta = new_theta
            num_iteration += 1

        print(f'After {num_iteration}, total cost is {current_cost}. Theta is {current_theta}')

LinearRegression1(x,y)
LinearRegression1.iterate()