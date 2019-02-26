import collections


def contains_duplicates(items):
    counted = collections.Counter(items)
    return any(count > 1 for count in counted.values())


def contains_anagrams(words):
    letter_sets = [tuple(sorted(letter for letter in word)) for word in words]
    counted = collections.Counter(letter_sets)
    return any(count > 1 for count in counted.values())


if __name__ == '__main__':
    with open("day4.txt") as f:
        pass_phrases = [l.split() for l in f.readlines()]

    num_valid = sum(1 for p in pass_phrases if not contains_duplicates(p))
    print("day 4 part one:", num_valid)

    num_valid = sum(
        1 for p in pass_phrases
        if not contains_anagrams(p) and not contains_duplicates(p))
    print("day 4 part two:", num_valid)
