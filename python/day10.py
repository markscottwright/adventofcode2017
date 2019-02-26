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


def dense_hash(sparse_hash):
    dense_hash_value = []
    for i in range(0, 256, 16):
        hash_part = 0
        for v in sparse_hash[i:i+16]:
            hash_part = hash_part ^ v
        dense_hash_value.append(hash_part)
    return dense_hash_value


def sparse_hash(lengths):
    LENGTHS_SUFFIX = [17, 31, 73, 47, 23]
    lengths = lengths + LENGTHS_SUFFIX

    string = list(range(256))
    pos = 0
    skip_size = 0
    for _ in range(64):
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


def knot_hash(ascii_string: str):
    lengths = [ord(c) for c in ascii_string]
    hash_value = dense_hash(sparse_hash(lengths))
    return "".join("%02x" % i for i in hash_value)


if __name__ == "__main__":
    lengths = [63, 144, 180, 149, 1, 255, 167, 84,
               125, 65, 188, 0, 2, 254, 229, 24]

    assert rotate(rotate(lengths, 5), -5) == lengths

    test_string = list(range(5))
    test_lengths = [3, 4, 1, 5]
    assert [3, 4, 2, 1, 0] == twist_string(test_string, test_lengths)

    twisted = twist_string(list(range(256)), lengths)
    print("day 10 part 1:", twisted[0] * twisted[1])

    assert 64 == dense_hash(
        [65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22])[0]
    assert "a2582a3a0e66e6e86e3812dcb672a272" == knot_hash("")
    assert "33efeb34ea91902bb2f59c9920caa6cd" == knot_hash("AoC 2017")
    assert "3efbe78a8d82f29979031a4aa0b16a9d" == knot_hash("1,2,3")
    assert "63960835bcdc130f0b66d7ff4f6a5a8e" == knot_hash("1,2,4")

    input_string = "63,144,180,149,1,255,167,84,125,65,188,0,2,254,229,24"
    print("day 10 part 2:", knot_hash(input_string))
