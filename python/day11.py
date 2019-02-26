from hexcell import HexCell


if __name__ == "__main__":
    c0 = HexCell(0, 0)
    assert 3 == c0.ne().ne().ne().distance(c0)
    assert 0 == c0.ne().ne().sw().sw().distance(c0)
    assert 2 == c0.ne().ne().s().s().distance(c0)
    assert 3 == c0.se().sw().se().sw().sw().distance(c0)
    
    # follow path in input data
    with open('day11.txt') as f:
        steps = f.read().strip().split(',')
    c = origin = HexCell(0, 0)

    max_distance = 0
    for step in steps:
        c = getattr(c, step)()
        max_distance = max(max_distance, c.distance(origin))
    print("day 11 part 1", c.distance(origin))
    print("day 11 part 2", max_distance)
