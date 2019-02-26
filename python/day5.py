def run_program_until_exit(jumps_template):
    jumps = [j for j in jumps_template]
    steps = ip = 0

    while ip < len(jumps):
        old_ip = ip
        ip = ip + jumps[ip]
        jumps[old_ip] = jumps[old_ip]+1
        steps += 1

    return steps


def run_program_until_exit_part_two(jumps_template):
    jumps = [j for j in jumps_template]
    steps = ip = 0

    while ip < len(jumps):
        old_ip = ip
        ip = ip + jumps[ip]
        if jumps[old_ip] >= 3:
            jumps[old_ip] = jumps[old_ip]-1
        else:
            jumps[old_ip] = jumps[old_ip]+1
        steps += 1

    return steps


if __name__ == "__main__":
    with open("day5.txt") as f:
        jumps = [int(l) for l in f.readlines()]

    assert 5 == run_program_until_exit([0, 3, 0, 1, -3])
    print("day 5 part one:", run_program_until_exit(jumps))

    assert 10 == run_program_until_exit_part_two([0, 3, 0, 1, -3])
    print("day 5 part two:", run_program_until_exit_part_two(jumps))
