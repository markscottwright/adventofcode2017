import collections


class Program:
    def __init__(self, name, weight, children=[]):
        self.name = name
        self.weight = weight
        self.children = children

    @classmethod
    def parse(cls, line):
        fields = line.split()
        name = fields[0]
        weight = int(fields[1][1:-1])
        if len(fields) > 3:
            children = [f.replace(',', '') for f in fields[3:]]
        else:
            children = []
        return Program(name, weight, children)

    def __repr__(self):
        if self.children == []:
            return "Program(%s,%d)" % (self.name, self.weight)
        else:
            return "Program(%s,%d,[%s])" % (
                self.name, self.weight, ",".join(self.children))


def program_weight(program_name: str, name_to_program: dict):
    program = name_to_program[program_name]
    return program.weight + sum(
        program_weight(c, name_to_program) for c in program.children)


def find_unbalanced_programs(programs):
    """
    Return all programs that have children with unequal weights
    """
    name_to_program = dict((p.name, p) for p in programs)
    for p in programs:
        child_to_weight = dict(
            (c, program_weight(c, name_to_program)) for c in p.children)

        # not all children have the same weight
        if len(set(child_to_weight.values())) > 1:
            yield p


def find_unbalanced_with_balanced_children(progams):
    """
    Return the one program that has children with unequal weights, but those
    children are all balanced
    """

    unbalanced_programs = set(find_unbalanced_programs(programs))
    unbalanced_program_names = set(p.name for p in unbalanced_programs)

    # find a program with only balanced children
    for p in unbalanced_programs:
        unbalanced_children = set(p.children).intersection(
            unbalanced_program_names)
        if not unbalanced_children:
            return p


def find_problem_child_and_weight_change_needed(program, programs):
    """
    Find the one child of that is causing the balance problem
    """
    name_to_program = dict((p.name, p) for p in programs)
    child_to_weight = dict(
        (c, program_weight(c, name_to_program)) for c in program.children)
    most_common_weight, times_seen = collections.Counter(
        child_to_weight.values()).most_common(1)[0]
    for c, w in child_to_weight.items():
        if w != most_common_weight:
            return most_common_weight-w, name_to_program[c]


if __name__ == '__main__':
    with open("day7.txt") as f:
        programs = [Program.parse(l) for l in f]
    p = find_unbalanced_with_balanced_children(programs)
    weight_change, c = find_problem_child_and_weight_change_needed(p, programs)
    print("day 7 part 2:", c.weight+weight_change)
