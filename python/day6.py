def distribute(old_banks):
    banks = [b for b in old_banks]
    bank_to_redistribute = banks.index(max(banks))
    blocks_to_redistribute = banks[bank_to_redistribute]
    banks[bank_to_redistribute] = 0

    i = bank_to_redistribute
    while blocks_to_redistribute > 0:
        i += 1
        if i >= len(banks):
            i = 0
        banks[i] = banks[i] + 1
        blocks_to_redistribute -= 1

    return banks


def num_distributions_until_duplicate(banks):
    banks_history = set()
    banks_history.add(tuple(banks))

    while True:
        banks = distribute(banks)
        if tuple(banks) in banks_history:
            return len(banks_history)
        else:
            banks_history.add(tuple(banks))


def infinite_loop_size(banks):
    step = 0
    state_to_step = {tuple(banks): step}

    while True:
        banks = distribute(banks)
        step += 1
        if tuple(banks) in state_to_step:
            return step - state_to_step[tuple(banks)]
        else:
            state_to_step[tuple(banks)] = step


if __name__ == "__main__":
    with open("day6.txt") as f:
        banks = [int(f) for f in f.read().strip().split()]

    assert [2, 4, 1, 2] == distribute([0, 2, 7, 0])
    assert [3, 1, 2, 3] == distribute([2, 4, 1, 2])
    assert 5 == num_distributions_until_duplicate([0, 2, 7, 0])

    print("day 6 part 1:", num_distributions_until_duplicate(banks))

    assert 4 == infinite_loop_size([0, 2, 7, 0])
    print("day 6 part 2:", infinite_loop_size(banks))
