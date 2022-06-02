import math
import random
import time


class MLP:
    def __init__(self, input_size, hidden_size, output_size, eta=0.5, lambd=1.0, desired_error=0.01):
        self.input_size = input_size
        self.hidden_size = hidden_size
        self.output_size = output_size
        self.eta = eta
        self.lambd = lambd
        self.desired_error = desired_error
        self.hidden_layer, self.output_layer = self._init_weights()

    def _init_weights(self):
        output_layer = []
        for i in range(self.output_size):
            output_layer.append([])
            for j in range(self.hidden_size):
                output_layer[i].append(random.randrange(0, 10000) / 10001 - 0.5)

        hidden_layer = []
        for i in range(self.hidden_size):
            hidden_layer.append([])
            for j in range(self.input_size):
                hidden_layer[i].append(random.randrange(0, 10000) / 10001 - 0.5)

        weights = [hidden_layer, output_layer]

        return weights

    def _sigmoid(self, x):
        # print(x)
        return 1.0/(1.0+math.exp(-self.lambd*x))


        # return 2 / (1 + math.exp(-self.lambd * x)) - 1



    def forward(self, sample):
        # Forward through hidden layer
        hidden = []
        for i in range(self.hidden_size - 1):
            h = 0
            for j in range(self.input_size):
                h += self.hidden_layer[i][j] * sample[j]
            # TODO: change to other activation function
            hidden.append(self._sigmoid(h))
        # the last hidden state should be -1 like the input
        hidden.append(-1)

        # Forward through output layer
        out = []
        for i in range(self.output_size):
            h = 0
            for j in range(self.hidden_size):
                h += self.output_layer[i][j] * hidden[j]
            out.append(self._sigmoid(h))

        return out, hidden

    def calculate_loss(self, label, out):
        loss = 0.0
        for i in range(self.output_size):
            loss += 0.5 * (label[i] - out[i]) ** 2

        return loss

    def update(self, sample, label, out, hidden):
        delta_out = []
        for i in range(self.output_size):
            delta_out.append((label[i] - out[i]) * (1 - out[i] ** 2) / 2)

        delta_hidden = []
        for i in range(self.hidden_size):
            delta_hidden.append(0)
            for j in range(self.output_size):
                delta_hidden[i] += delta_out[j] * self.output_layer[j][i]

            delta_hidden[i] = (1 - hidden[i]) * hidden[i] * delta_hidden[i]

        for i in range(self.output_size):
            for j in range(self.hidden_size):
                self.output_layer[i][j] += self.eta * delta_out[i] * hidden[j]

        for i in range(self.hidden_size):
            for j in range(self.input_size):
                self.hidden_layer[i][j] += self.eta * delta_hidden[i] * sample[j]

    def display_weights(self):
        print('The hidden layer:')
        for i in range(self.hidden_size):
            print(self.hidden_layer[i])
        print()

        print('The output layer:')
        for i in range(self.output_size):
            print(self.output_layer[i])
        print()

    def train(self, samples):
        iter = 0
        while 1:
            iter += 1
            error = 0
            for sample, label in samples:
                out, hidden = self.forward(sample)
                error += self.calculate_loss(label, out)
                self.update(sample, label, out, hidden)
            print(f"Error in the {iter}-th learning cycle={error}")

            if error <= self.desired_error:
                break

        self.display_weights()


if __name__ == "__main__":
    # samples = [
    #     ((0, 0, -1), (0,)),
    #     ((0, 1, -1), (1,)),
    #     ((1, 0, -1), (1,)),
    #     ((1, 1, -1), (0,)),
    # ]

    samples = [
        ((0, 0, 0, 0, -1), (0,)),
        ((0, 0, 0, 1, -1), (1,)),
        ((0, 0, 1, 0, -1), (1,)),
        ((0, 0, 1, 1, -1), (0,)),
        ((0, 1, 0, 0, -1), (1,)),
        ((0, 1, 0, 1, -1), (0,)),
        ((0, 1, 1, 0, -1), (0,)),
        ((0, 1, 1, 1, -1), (1,)),
        ((1, 0, 0, 0, -1), (1,)),
        ((1, 0, 0, 1, -1), (0,)),
        ((1, 0, 1, 0, -1), (0,)),
        ((1, 0, 1, 1, -1), (1,)),
        ((1, 1, 0, 0, -1), (0,)),
        ((1, 1, 0, 1, -1), (1,)),
        ((1, 1, 1, 0, -1), (1,)),
        ((1, 1, 1, 1, -1), (0,)),
    ]

    model = MLP(input_size=5, hidden_size=20, output_size=1, desired_error=0.001)
    print("Initial weights")
    model.display_weights()
    model.train(samples)

    for sample, label in samples:
        print(f"Sample {sample}, output {model.forward(sample)[0]}, label {label}")

    print("time usage:", time.process_time())
