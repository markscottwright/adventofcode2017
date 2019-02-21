import itertools

from position import Position


def spiral_directions():
    """An iterator that spirals out, starting by moving east"""
    side = 1
    while True:
        for _ in range(side): yield 'e'
        for _ in range(side): yield 'n'
        side += 1
        for _ in range(side): yield 'w'
        for _ in range(side): yield 's'
        side += 1
    

def build_spiral(max_value):
    address_to_pos = {}

    pos = Position(0,0)
    for address, direction in zip(range(1, max_value+1), spiral_directions()):
        address_to_pos[address] = pos
        if direction == 'e': pos = pos.e()
        if direction == 'n': pos = pos.n()
        if direction == 'w': pos = pos.w()
        if direction == 's': pos = pos.s()
    return address_to_pos


def stresstest_memory(max_value):
    """
    write value of sum of surrounding cells to each cell and return when written
    value exceeds max_value
    """
    address_to_pos = {}
    pos_to_value = {}

    pos = Position(0,0)
    pos_to_value[pos] = 1
    for address, direction in zip(itertools.count(1), spiral_directions()):

        address_to_pos[address] = pos

        # we already set the value for address 1 above
        if address > 1:
            value = sum(
                pos_to_value.get(p, 0) for p in pos.surrounding_positions())
            if value > max_value:
                return value
            pos_to_value[pos] = value

        if direction == 'e': pos = pos.e()
        if direction == 'n': pos = pos.n()
        if direction == 'w': pos = pos.w()
        if direction == 's': pos = pos.s()


if __name__=='__main__':
    origin = Position(0,0)
    memory_spiral = build_spiral(361527)
    assert 0 == origin.distance(memory_spiral[1])
    assert 3 == origin.distance(memory_spiral[12])
    assert 2 == origin.distance(memory_spiral[23])
    assert 31 == origin.distance(memory_spiral[1024])

    print("day 3 part 1:", origin.distance(memory_spiral[361527]))
    print("day 3 part 2:", stresstest_memory(361527))

