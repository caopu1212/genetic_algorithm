import random
import math

class DeltaNeuron:
    def __init__(self, input_size, eta=0.5, lambd=1.0, desired_error=0.01):
        self.input_size = input_size
        self.eta = eta
        self.lambd = lambd
        self.desired_error = desired_error

        self.weights = self._init_weights()

    def _init_weights(self):
        weights = []
        for i in range(self.input_size):
            weights.append(random.randrange(0, 10000) / 10001)
        
        return weights
    
    def _sigmoid(self, x):
        return 2 / (1  + math.exp(-self.lambd * x)) - 1

    def forward(self, sample):
        h = sum([x * w for x, w in zip(sample, self.weights)]) # scalar
        out = self._sigmoid(h) # scalar

        return out
    
    def calculate_loss(self, label, out):
        return 0.5 * (label - out) ** 2
    
    def update(self, sample, label, out):
        delta = (label - out) * (1 - out ** 2) / 2
        for i, weight in enumerate(self.weights):
            self.weights[i] += self.eta * delta * sample[i]

    def display_weights(self):
        print('The connection weights of the neurons:')
        print(self.weights)
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

class PerceptronNeuron(DeltaNeuron):
    def __init__(self, input_size, eta=0.5, lambd=1.0, desired_error=0.01):
        super(PerceptronNeuron, self).__init__(input_size, eta=eta, lambd=lambd, desired_error=desired_error)
    
    def forward(self, sample):
        h = sum([x * w for x, w in zip(sample, self.weights)]) # scalar
        return 1 if h > 0 else -1
    
    def update(self, sample, label, out):
        delta = label - out
        for i, weight in enumerate(self.weights):
            self.weights[i] += self.eta * delta * sample[i]


if __name__ == "__main__":
    samples = [
        ((1, 1, 1, -1), 1),
        ((1, 1, -1, -1), 1),
        ((1, -1, 1, -1), 1),
        ((1, -1, -1, -1), -1),
        ((-1, 1, 1, -1), 1),
        ((-1, 1, -1, -1), -1),
        ((-1, -1, 1, -1), -1),
        ((-1, -1, -1, -1), -1)
    ]

    model = DeltaNeuron(input_size=4)
    print("Initial weights")
    model.display_weights()
    model.train(samples)

    for sample, label in samples:
        print(f"Sample {sample}, output {model.forward(sample)}, label {label}")


    model = PerceptronNeuron(input_size=4)
    print("Initial weights")
    model.display_weights()
    model.train(samples)

    for sample, label in samples:
        print(f"Sample {sample}, output {model.forward(sample)}, label {label}")