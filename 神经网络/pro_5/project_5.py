import math
import random
import re


def char_range(c1, c2):
    """Generates the characters from `c1` to `c2`, inclusive."""
    for c in range(ord(c1), ord(c2) + 1):
        yield chr(c)


class SOFM:
    def __init__(self, input_size, M1, M2) -> None:
        self.input_size = input_size
        self.M1 = M1
        self.M2 = M2
        self.output_size = M1 * M2

        self.weights = self._init_weights()

    def _init_weights(self):
        weights = []
        for i in range(self.output_size):
            weights.append([])
            for j in range(self.input_size):
                weights[i].append(random.random())

        return weights

    def find_closest_neuron(self, sample):
        #
        min_distance = None
        min_m1 = None
        min_m2 = None
        for m1 in range(self.M1):
            for m2 in range(self.M2):
                distance = sum([(self.weights[m1 * self.M2 + m2][i] - sample[i]) ** 2 for i in range(self.input_size)])
                if min_distance is None or distance < min_distance:
                    min_distance = distance
                    min_m1 = m1
                    min_m2 = m2

        return min_m1, min_m2

    def update(self, min_m1, min_m2, sample, alpha, r1, r2, c1, c2):
        current_learning_rate = alpha * (r2 - r1) + r1
        current_neighborhood_size = alpha * (c2 - c1) + c1

        for m1 in range(self.M1):
            x1 = m1
            for m2 in range(self.M2):
                x2 = m2
                if m1 % 2 == 0:
                    x2 += 0.5

                distance = (x1 - min_m1) ** 2 + (x2 - min_m2) ** 2
                distance = distance ** 0.5
                if int(distance) <= current_neighborhood_size:
                    for i in range(self.input_size):
                        self.weights[m1 * self.M2 + m2][i] += current_learning_rate * (
                                    sample[i] - self.weights[m1 * self.M2 + m2][i])

    def calibration(self, sample):
        min_distance = None
        min_neuron = None
        for n in range(self.output_size):
            distance = sum([(self.weights[n][i] - sample[i]) ** 2 for i in range(self.input_size)])
            if min_distance is None or distance < min_distance:
                min_distance = distance
                min_neuron = n

        return min_neuron

    def display(self, labels):
        for m1 in range(self.M1):
            if m1 % 2 == 0:
                print(" ", end='')

            for m2 in range(self.M2):
                print(f'{labels[m1 * self.M2 + m2]} ', end='')

            print()


if __name__ == "__main__":
    characters = [c for c in char_range('A', 'Z')]

    # lines = open('data.txt', 'r').readlines()
    lines = open('iris.txt', 'r').readlines()
    # lines = open('XOR.txt', 'r').readlines()
    # lines = open('test.txt', 'r').readlines()
    data = []
    count = 0
    for line in lines:
        # parsed_line = [int(e) for e in re.split(r'[\s]', line) if e]
        parsed_line = [float(e) for e in re.split(r'[\s]', line) if e]
        # data.append((parsed_line[:-1], parsed_line[-1]))
        data.append((parsed_line[:-1], int(parsed_line[-1])))

    features, labels = zip(*data)
    features = list(features)
    labels = list(labels)
    display_labels = ['*'] * len(labels)
    for i, label in enumerate(labels):
        display_labels[i] = characters[label] if label <= 25 else f'{label - 25}'

    print(features)
    print(labels)

    model = SOFM(input_size=4, M1=15, M2=15)
    n_updates = 1000
    for i in range(n_updates):
        # Step 1: Select a sample at random from the training set
        sample = random.choice(features)
        # Step 2: Find the neuron closest to the input
        min_m1, min_m2 = model.find_closest_neuron(sample)
        # Step 3: Update the weight of the neuron if it is in the neighborhood of the (min_m1, min_m2)-th neuron



        # Try alpha decay: currently it is linear, step, log (slowly at the end), exp (fast at the end)
        # Learning rate r1, r2 pairs: choose 2 more pairs
        # Neighbor hood size c1, c2: choose 2 more pairs

        alpha = i / n_updates


        model.update(min_m1, min_m2, sample, alpha, r1=0.5, r2=0.04, c1=10, c2=1)

    display_labels = ['*'] * model.output_size
    for sample, label in zip(features, labels):
        min_neuron = model.calibration(sample)
        display_labels[min_neuron] = characters[label] if label <= 25 else f'{label - 25}'

    print("Results after the first 1000 iterations")
    model.display(display_labels)

    n_updates = 9000
    for i in range(n_updates):
        # Step 1: Select a sample at random from the training set
        sample = random.choice(features)
        # Step 2: Find the neuron closest to the input
        min_m1, min_m2 = model.find_closest_neuron(sample)
        # Step 3: Update the weight of the neuron if it is in the neighborhood of the (min_m1, min_m2)-th neuron
        model.update(min_m1, min_m2, sample, alpha=i / n_updates, r1=0.04, r2=0.0, c1=1, c2=1)

    display_labels = ['*'] * model.output_size
    for sample, label in zip(features, labels):
        min_neuron = model.calibration(sample)
        display_labels[min_neuron] = characters[label] if label <= 25 else f'{label - 25}'

    print("Results after 10000 iterations")
    model.display(display_labels)
