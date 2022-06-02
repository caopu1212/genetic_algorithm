import random
import pandas as pd
class KohonenNetwork:
    def __init__(self, input_size, output_size, alpha=0.5):
        self.input_size = input_size
        self.output_size = output_size
        self.alpha = alpha

        self.weights = self._init_weights()

    def _init_weights(self):
        weights = []
        for i in range(self.output_size):
            weights.append([])
            for j in range(self.input_size):
                weights[i].append(random.random() - 0.5)

            weights[i] = self.normalize_weights(weights[i])

        return weights

    def normalize_weights(self, weights_row):
        norm = 0
        for j in range(self.input_size):
            norm += weights_row[j] ** 2

        norm = norm ** 0.5

        normalized_weights_row = []
        for j in range(self.input_size):
            normalized_weights_row.append(weights_row[j] / norm)

        return normalized_weights_row

    def find_winner_neuron(self, pattern):
        winner = 0
        max_h = None
        for i in range(self.output_size):
            h = sum([self.weights[i][j] * pattern[j] for j in range(self.input_size)])
            if max_h is None or h > max_h:
                winner = i
                max_h = h

        return winner

    def update(self, winner, pattern):
        for j in range(self.input_size):
            self.weights[winner][j] += self.alpha * (pattern[j] - self.weights[winner][j])

        self.weights[winner] = self.normalize_weights(self.weights[winner])

    def display_weights(self):
        for i in range(self.output_size):
            print(self.weights[i])

iris_data = pd.read_csv('C:\\Users\\Administrator\\Desktop\\iris.txt', header = None)
# print(iris_data)
iris_data_T = iris_data.T
# print(iris_data_T)
if __name__ == "__main__":
    patterns = []
    for i in range(0,150):
        patterns.append(iris_data_T[i][0:4].values.tolist())
    print(patterns)

    random.shuffle(patterns)

    model = KohonenNetwork(4,3)

    print("Initial weights")
    model.display_weights()

    for i, pattern in enumerate(patterns):
        patterns[i]= model.normalize_weights(pattern)


    print(patterns)

    # result = 0
    # for i in range(4):
    #     result = patterns[0][i] **2 + result



    n_iterations = 20
    for iteration in range(n_iterations):
        for pattern in patterns:
            winner = model.find_winner_neuron(pattern)
            # print(winner)
            model.update(winner, pattern)

        print(f"Weights at {iteration}")
        model.display_weights()

    for i, pattern in enumerate(patterns):
        winner = model.find_winner_neuron(pattern)
        print(f"Pattern {i}-th belongs to {winner}-th class")
