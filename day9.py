def count_garbage(s: str):
    in_garbage = False
    chars = iter(s)
    count = 0

    for c in chars:
        if in_garbage and c == '!':
            next(chars)
        elif not in_garbage and c == '<':
            in_garbage = True
        elif c == '>':
            in_garbage = False
        elif in_garbage:
            count += 1
    return count

def strip_garbage_iter(s: str):
    in_garbage = False
    chars = iter(s)

    for c in chars:
        if in_garbage and c == '!':
            next(chars)
        elif c == '<':
            in_garbage = True
        elif c == '>':
            in_garbage = False
        elif not in_garbage:
            yield c
    assert not in_garbage
        
def strip_garbage(s: str):
    return "".join(strip_garbage_iter(s))


def num_groups(s):
    return sum(1 for c in strip_garbage(s) if c == '}')
    

def read_to_end_of_garbage(chars):
    for c in chars:
        if c == '>':
            return
        elif c == '!':
            next(chars)


def parse_group(chars):
    groups = []
    while True:
        c = next(chars)
        if c == ',':
            continue
        elif c == '{':
            groups.append(parse_group(chars))
        elif c == '<':
            read_to_end_of_garbage(chars)
        elif c == '}':
            return groups


def parse(s):
    chars = iter(s)
    assert next(chars) == "{"
    return parse_group(chars)


def score(groups, base=1):
    return base + sum(score(g, base+1) for g in groups)


if __name__ == '__main__':
    assert "" == strip_garbage('<>')
    assert "" == strip_garbage('<random characters>')
    assert "" == strip_garbage('<<<<>')
    assert "" == strip_garbage('<{!>}>')
    assert "" == strip_garbage('<!!>')
    assert "" == strip_garbage('<!!!>>')
    assert "" == strip_garbage('<{o"i!a,<{i<a>')

    assert 1 == num_groups('{}')
    assert 3 == num_groups('{{{}}}')
    assert 3 == num_groups('{{},{}}')
    assert 6 == num_groups('{{{},{},{{}}}}')
    assert 1 == num_groups('{<{},{},{{}}>}')
    assert 1 == num_groups('{<a>,<a>,<a>,<a>}')
    assert 5 == num_groups('{{<a>},{<a>},{<a>},{<a>}}')
    assert 2 == num_groups('{{<!>},{<!>},{<!>},{<a>}}')

    assert [] == parse("{}")
    assert [[]] == parse("{{}}")
    assert [[],[]] == parse("{{},{}}")

    assert 6 == score(parse("{{{}}}"))
    assert 5 == score(parse("{{},{}}"))
    assert 3 == score(parse('{{<!>},{<!>},{<!>},{<a>}}'))

    with open("day9.txt") as f:
        data = f.read()
    print("day 9 part 1:", score(parse(data)))

    assert 0 == count_garbage("<>")
    assert 17 == count_garbage("<random characters>")
    assert 10 == count_garbage('<{o"i!a,<{i<a>')
    print("day 9 part 2:", count_garbage(data))
