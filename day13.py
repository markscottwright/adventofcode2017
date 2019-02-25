from pprint import pprint
from itertools import chain, repeat


def parse(line):
    fields = [f.strip() for f in line.strip().split(':')]
    layer = int(fields[0])
    scanner_range = int(fields[1])
    assert scanner_range > 1
    return layer, scanner_range


class CaughtByScanner(Exception):
    pass


def move_through_firewall(ranges, delay=0):
    max_layer = max(ranges.keys())

    scanner_states = dict(
        (l, (0, 'down')) for l in ranges.keys())

    times_caught = []

    movement_plan = chain(
        repeat(None, delay),
        range(max_layer+1))
    for current_layer in movement_plan:
        if scanner_states.get(current_layer, (None, None))[0] == 0:
            if delay == 0:
                times_caught.append([current_layer, ranges[current_layer]])
            else:
                raise CaughtByScanner()

        for layer, pos_and_dir in scanner_states.items():
            scanner_range = ranges[layer]
            if pos_and_dir == (0, 'up'):
                scanner_states[layer] = (1, 'down')
            elif pos_and_dir == (scanner_range-1, 'down'):
                scanner_states[layer] = (scanner_range-2, 'up')
            elif pos_and_dir[1] == 'down':
                scanner_states[layer] = (pos_and_dir[0]+1, 'down')
            elif pos_and_dir[1] == 'up':
                scanner_states[layer] = (pos_and_dir[0]-1, 'up')

    return times_caught
    

if __name__ == "__main__":
    sample_scanner_range_per_layer = dict(parse(l) for l in
         """0: 3
            1: 2
            4: 4
            6: 4""".split("\n"))

    assert 24 == sum(depth*scanner_range for depth, scanner_range
        in move_through_firewall(sample_scanner_range_per_layer))

    with open('day13.txt') as f:
        scanner_range_per_layer = dict(parse(l) for l in f.readlines())
    print("day 13 part 1:",
          sum(depth*scanner_range for depth, scanner_range
              in move_through_firewall(scanner_range_per_layer)))

    # well, this isn't going to work...
    #
    #   I think that s[l] == 0 when time % ((range-1)*2) == 0.  So we can
    #   convert all the scanners into that.
    #
    for delay in range(1, 100000):
        print(delay, end="\r")
        try:
            move_through_firewall(scanner_range_per_layer, delay)
            print("day 13 part 2:", delay)
            break
        except CaughtByScanner:
            pass
