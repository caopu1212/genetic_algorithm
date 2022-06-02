import random


class HNN:
    def __init__(self, input_size):
        if isinstance(input_size, tuple):
            state_size = 1
            for size in input_size:
                state_size *= size
        else:
            state_size = input_size


        self.input_size = input_size
        self.state_size = state_size

        self.state = self._init_state(state_size)
        self.weights = self._init_weights(state_size)


    def _init_state(self, state_size):
        state = [0 for _ in range(state_size)]


        return state


    def _init_weights(self, state_size):
        weights = []
        for i in range(state_size):
            weights.append([0 for _ in range(state_size)])


        return weights

    def store_patterns(self, patterns):
        for i, _ in enumerate(self.weights):
            for j, _ in enumerate(self.weights[i]):
                if i == j:
                    self.weights[i][j] = 0
                else:
                    self.weights[i][j] = sum([pattern[i] * pattern[j] for pattern in patterns])
                    self.weights[i][j] = int(self.weights[i][j] / len(patterns))
                # print(f'{self.weights[i][j]}', end=' ')
            # print()



    def recall_pattern(self, pattern):
        for i, _ in enumerate(pattern):
            self.state[i] = pattern[i]

        print("0-th iteration")
        self.display_pattern(self.state)


        iteration = 1
        while True:
            n_update = 0
            for i, _ in enumerate(self.weights):
                h = sum([self.weights[i][j] * self.state[j] for j, _ in enumerate(self.weights[i])])
                new_state = 1 if h >= 0 else -1
                if new_state != self.state[i]:
                    n_update += 1
                    self.state[i] = new_state

            print(f"{iteration}-th iteration")
            self.display_pattern(self.state)
            iteration += 1
            if n_update == 0:
                break



    def create_noisy_pattern(self, pattern, noise_rate):
        noisy_pattern = []
        for i, _ in enumerate(pattern):
            r = random.random()
            if r < noise_rate:
                noisy_pattern.append(-pattern[i])
            else:
                noisy_pattern.append(pattern[i])

        return noisy_pattern



    def display_pattern(self, pattern):
        for i in range(self.input_size[0]):
            for j in range(self.input_size[1]):
                print(f'{"*" if pattern[i * self.input_size[1] + j] == -1 else " "}', end='')
            print()
        print()


if __name__ == "__main__":
    patterns = [
        [1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1],
        [1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1,
         1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1,
         1, 1, 1, -1, -1, -1, -1, 1, 1, 1, 1, 1,
         1, 1, -1, -1, 1, -1, -1, 1, 1, 1, 1, 1,
         1, -1, -1, 1, 1, -1, -1, 1, 1, 1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1,
         1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1,
         1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
         1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1]
    ]

model = HNN(input_size=(10, 12))
print("Patterns")
for i, pattern in enumerate(patterns):
    print(f"{i}-th pattern")
    model.display_pattern(pattern)
print()

model.store_patterns(patterns)
# print("Model weight")
# for i, _ in enumerate(model.weights):
#     print(f"{model.weights[i]}")

for i, pattern in enumerate(patterns):
    noisy_pattern = model.create_noisy_pattern(pattern, noise_rate=0.80)
    print(f"Recalling {i}-th pattern")
    model.recall_pattern(noisy_pattern)
