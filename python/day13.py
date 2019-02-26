from pprint import pprint
from itertools import chain, repeat


def parse(line):
    fields = [f.strip() for f in line.strip().split(':')]
    layer = int(fields[0])
    scanner_range = int(fields[1])
    assert scanner_range > 1
    return layer, scanner_range


def faster_move_through_filewall(ranges):
    # The back and forth movement of the scanner needn't be modelled directly.
    # All that matters is when it is at 0.  If we look at scanner movement per
    # range:
    #
    # 2: 0 1
    # 3: 0 1 2 1
    # 4: 0 1 2 3 2 1
    #
    # therefore, range -> period of repetition is (r-1)*2
    # And if time % ((range-1)*2) == 0, we're "caught"

    for layer, scanner_range in ranges.items():
        picosecond = layer
        if picosecond % ((scanner_range-1)*2) == 0:
            yield layer


def find_delay_with_no_capture(ranges):
    for delay in range(10000000):
        caught = False
        for layer, scanner_range in ranges.items():
            picosecond = layer+delay
            if picosecond % ((scanner_range-1)*2) == 0:
                caught = True
                break
        if not caught:
            return delay


if __name__ == "__main__":
    sample_scanner_range_per_layer = dict(parse(l) for l in
         """0: 3
            1: 2
            4: 4
            6: 4""".split("\n"))

    assert [0, 6] == list(
        faster_move_through_filewall(sample_scanner_range_per_layer))
    assert 24 == sum(depth*sample_scanner_range_per_layer[depth] for depth
              in faster_move_through_filewall(sample_scanner_range_per_layer))

    with open('day13.txt') as f:
        scanner_range_per_layer = dict(parse(l) for l in f.readlines())

    print("day 13 part 1:",
          sum(depth*scanner_range_per_layer[depth] for depth
              in faster_move_through_filewall(scanner_range_per_layer)))
    print("day 13 part 2:",
        find_delay_with_no_capture(scanner_range_per_layer))
