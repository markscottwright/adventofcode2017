import string
from pprint import pprint
from collections import defaultdict


class Duet:
    def __init__(self):
        self.registers = defaultdict(lambda: 0)
        self.last_sound = 0

    def val(self, v):
        if isinstance(v, int):
            return v
        else:
            return self.registers[v]

    def snd(self, x):
        self.last_sound = self.val(x)
        return 1

    def rcv(self, x):
        if self.val(x) > 0:
            print("rcv:" + str(self.last_sound))
            return 1000000
        return 1

    def set(self, x, y):
        self.registers[x] = self.val(y)
        return 1


    def add(self, x, y):
        self.registers[x] += self.val(y)
        return 1


    def mul(self, x, y):
        self.registers[x] *= self.val(y)
        return 1


    def mod(self, x, y):
        self.registers[x] = self.registers[x] % self.val(y)
        return 1
        

    def jgz(self, x, y):
        if self.val(x) > 0:
            return self.val(y)
        else:
            return 1

    @classmethod
    def arg_val(cls, arg):
        if arg in string.ascii_letters:
            return arg
        else:
            return int(arg)

    @classmethod
    def parse_iter(cls, source_lines):
        for l in source_lines:
            fields = l.strip().split()
            yield fields[0], [cls.arg_val(f) for f in fields[1:]]

    @classmethod
    def parse(cls, source_lines):
        return list(cls.parse_iter(source_lines))

    def run(self, program, trace=False):
        line_no = 0
        while line_no >= 0 and line_no < len(program):
            cmd, args = program[line_no]
            if trace: print(line_no, cmd, args)
            line_no += getattr(self, cmd)(*args)
        return self.last_sound


if __name__ == "__main__":
    sample_program = Duet.parse(
        l.strip() for l in
        """set a 1
            add a 2
            mul a a
            mod a 5
            snd a
            set a 0
            rcv a
            jgz a -1
            set a 1
            jgz a -2""".splitlines())
    assert 4 == Duet().run(sample_program)

    with open("day18.txt") as f:
        program = Duet.parse(f.readlines())
        print("Day 18 part 1:" + str(Duet().run(program)))
