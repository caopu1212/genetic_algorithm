import random

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

            weights[i] = self._normalize_weights(weights[i])

        return weights

    def _normalize_weights(self, weights_row):
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

        self.weights[winner] = self._normalize_weights(self.weights[winner])




    def display_weights(self):
        for i in range(self.output_size):
            print(self.weights[i])


if __name__ == "__main__":
    patterns = [
        [0,1,0.4,0.8, 0.6],
        [0,1,0.4,0.1736, -0.9848],
        [0,1,0.4,0.707, 0.707],
        [0,1,0.4,0.342, -0.9397],
        [0,1,0.4,0.6, 0.8]
    ]
    model = KohonenNetwork(4, 3)
    print("Initial weights")
    model.display_weights()

n_iterations = 10
for iteration in range(n_iterations):
    for pattern in patterns:
        winner = model.find_winner_neuron(pattern)
        model.update(winner, pattern)

    print(f"Weights at {iteration}")
    model.display_weights()

for i, pattern in enumerate(patterns):
    winner = model.find_winner_neuron(pattern)
    print(f"Pattern {i}-th belongs to {winner}-th class")

