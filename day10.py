def rotate(items: list, distance: int):
    return items[distance:] + items[0:distance]


def twist_string(string, lengths):
    pos = 0
    skip_size = 0
    for length in lengths:
        assert length <= len(string)

        # move string so we're always working from the beginning
        work_string = rotate(string, pos)

        twisted_section = work_string[0:length]
        twisted_section.reverse()
        work_string = twisted_section + work_string[length:]

        # move back
        work_string = rotate(work_string, -pos)

        pos = (pos + length + skip_size) % len(string)
        skip_size += 1
        string = work_string
    return string


if __name__ == "__main__":
    lengths = [63, 144, 180, 149, 1, 255, 167, 84,
               125, 65, 188, 0, 2, 254, 229, 24]

    assert rotate(rotate(lengths, 5), -5) == lengths

    test_string = list(range(5))
    test_lengths = [3, 4, 1, 5]
    assert [3, 4, 2, 1, 0] == twist_string(test_string, test_lengths)

    twisted = twist_string(list(range(256)), lengths)
    print("day 10 part 1:", twisted[0] * twisted[1])
