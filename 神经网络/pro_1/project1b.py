
import random
import math

class DeltaLayer:
    def __init__(self, input_size, output_size, eta=0.5, lambd=1.0, desired_error=0.01):
        self.input_size = input_size
        self.output_size = output_size
        self.eta = eta
        self.lambd = lambd
        self.desired_error = desired_error

        self.weights = self._init_weights()

    def _init_weights(self):
        weights = []
        for i in range(self.output_size):
            weights.append([])
            for j in range(self.input_size):
                weights[i].append(random.randrange(0, 10000) / 10001 - 0.5)
        
        return weights
    
    def _sigmoid(self, x):
        return 2 / (1  + math.exp(-self.lambd * x)) - 1

    def forward(self, sample):
        out = []
        for i in range(self.output_size):
            h = 0
            for j in range(self.input_size):
                h += self.weights[i][j] * sample[j]
            out.append(self._sigmoid(h))

        return out

    def calculate_loss(self, label, out):
        loss = 0.0
        for i in range(self.output_size):
            loss += 0.5 * (label[i] - out[i]) ** 2
        
        return loss

    def update(self, sample, label, out):
        for i in range(self.output_size):
            delta = (label[i] - out[i]) * (1 - out[i] ** 2) / 2
            for j in range(self.input_size):
                self.weights[i][j] += self.eta * delta * sample[j]

    def display_weights(self):
        print('The connection weights are:')
        for i in range(self.output_size):
            print(self.weights[i])
        print()
    
    def train(self, samples):
        iter = 0
        while 1:
            iter += 1
            error = 0
            for sample, label in samples:
                out = self.forward(sample)
                error += self.calculate_loss(label, out)
                self.update(sample, label, out)
            print(f"Error in the {iter}-th learning cycle={error}")
            
            if error <= self.desired_error:
                break
        
        self.display_weights()

class PerceptronLayer(DeltaLayer):
    def __init__(self, input_size, output_size, eta=0.5, lambd=1, desired_error=0.01):
        super(PerceptronLayer, self).__init__(input_size, output_size, eta, lambd, desired_error)

    def forward(self, sample):
        out = []
        for i in range(self.output_size):
            h = 0
            for j in range(self.input_size):
                h += self.weights[i][j] * sample[j]
            out.append(1 if h > 0 else -1)

        return out
    
    def update(self, sample, label, out):
        for i in range(self.output_size):
            delta = label[i] - out[i]
            for j in range(self.input_size):
                self.weights[i][j] += self.eta * delta * sample[j]


if __name__ == "__main__":
    samples = [
        ((10, 2, -1), (1, -1, -1)),
        ((2, -5, -1), (-1, 1, -1)),
        ((-5, 5, -1), (-1, -1, 1)),
    ]

    model = DeltaLayer(input_size=3, output_size=3, desired_error=0.1)
    print("Initial weights")
    model.display_weights()
    model.train(samples)

    for sample, label in samples:
        print(f"Sample {sample}, output {model.forward(sample)}, label {label}")

    model = PerceptronLayer(input_size=3, output_size=3, desired_error=0.1)
    print("Initial weights")
    model.display_weights()
    model.train(samples)

    for sample, label in samples:
        print(f"Sample {sample}, output {model.forward(sample)}, label {label}")