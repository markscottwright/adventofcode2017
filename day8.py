import re
import collections


class Instruction:
    def __init__(
            self,
            register_name,
            operation,
            argument, 
            test_register_name, 
            test_operation, 
            test_argument):

        assert operation in ['inc', 'dec']
        assert test_operation in ['!=', '>', '<', '==', '<=', '>=']

        self.register_name = register_name
        self.operation = operation
        self.argument = argument
        self.test_register_name = test_register_name
        self.test_operation = test_operation
        self.test_argument = test_argument

    def apply(self, prev_registers):
        registers = collections.defaultdict(lambda: 0, prev_registers)
        if self.operation == 'inc':
            registers[self.register_name] = \
                registers[self.register_name] + self.argument
        elif self.operation == 'dec':
            registers[self.register_name] = \
                registers[self.register_name] - self.argument
        else:
            print(self.test_operation)
            assert False
        return registers

    def test_and_apply(self, registers):
        if (self.test_operation == '=='
                and registers[self.test_register_name] == self.test_argument):
            return self.apply(registers)
        elif (self.test_operation == '!='
                and registers[self.test_register_name] != self.test_argument):
            return self.apply(registers)
        elif (self.test_operation == '<'
                and registers[self.test_register_name] < self.test_argument):
            return self.apply(registers)
        elif (self.test_operation == '<='
                and registers[self.test_register_name] <= self.test_argument):
            return self.apply(registers)
        elif (self.test_operation == '>'
                and registers[self.test_register_name] > self.test_argument):
            return self.apply(registers)
        elif (self.test_operation == '>='
                and registers[self.test_register_name] >= self.test_argument):
            return self.apply(registers)
        return registers

    @classmethod
    def parse(cls, line):
        m = re.match(
            r"(\w+) (\w+) ([-0-9]+) if (\w+) ([=!<>]+) ([-0-9]+)", line)
        return Instruction(
            m.group(1), m.group(2), int(m.group(3)),
            m.group(4), m.group(5), int(m.group(6)))

    def __repr__(self):
        return "Instruction('%s', '%s', %d, '%s', '%s', %d)" % (
            self.register_name,
            self.operation,
            self.argument,
            self.test_register_name,
            self.test_operation,
            self.test_argument)

    def __str__(self):
        return "%s %s %d if %s %s %d" % (
            self.register_name,
            self.operation,
            self.argument,
            self.test_register_name,
            self.test_operation,
            self.test_argument)

if __name__ == '__main__':
    with open('day8.txt') as f:
        instructions = [Instruction.parse(l) for l in f]

    registers = collections.defaultdict(lambda: 0)
    max_value_ever = 0
    for i in instructions:
        registers = i.test_and_apply(registers)
        max_value_ever = max(max_value_ever, max(registers.values()))
    print("day 8 part 1:", max(registers.values()))
    print("day 8 part 2:", max_value_ever)
