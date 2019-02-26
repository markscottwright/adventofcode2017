from pprint import pprint


def parse(line):
    "returns program, set(program)"
    program_id = int(line.split()[0])
    connected_programs_str = line.split()[2:]
    connected_program_ids = set(
        int(p.replace(",", "")) for p in connected_programs_str)
    return program_id, connected_program_ids


def all_connected(p, so_far, immediate_connections):
    so_far.add(p)
    for p2 in immediate_connections[p]:
        if p2 not in so_far:
            so_far.add(p2)
            all_connected(p2, so_far, immediate_connections)
    return so_far


def group(immediate_connections):
    # This isn't optimal, since we never check existing groups before descending
    # into all_connected
    groups = set()
    assigned_to_group = set()
    for p in immediate_connections.keys():
        if p not in assigned_to_group:
            new_group = tuple(all_connected(p, set(), immediate_connections))
            groups.add(new_group)
            assigned_to_group.update(new_group)
    return groups
        
        
if __name__ == "__main__":
    test_connections = dict(map(
        lambda l: parse(l.strip()),
        """0 <-> 2
            1 <-> 1
            2 <-> 0, 3, 4
            3 <-> 2, 4
            4 <-> 2, 3, 6
            5 <-> 6
            6 <-> 4, 5""".split("\n")))
    assert 2 == len(group(test_connections))

    with open("day12.txt") as f:
        connections = dict(parse(l.strip()) for l in f.readlines())

    # group and then find the group with '0'
    all_groups = group(connections)
    for g in all_groups:
        if 0 in g:
            group_with_0 = g
    print("day 12 part 1:", len(group_with_0))
    print("day 12 part 2:", len(all_groups))
