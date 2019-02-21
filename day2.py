def checksum(spreadsheet):
    checksum = 0
    for row in spreadsheet:
        checksum += max(row) - min(row)
    return checksum


def even_divisors(row):
    for i in range(len(row)):
        for j in range(len(row)):
            if i != j and row[i] > row[j] and row[i] % row[j] == 0:
                return row[i], row[j]


def sum_all_even_divisions(spreadsheet):
    total = 0
    for row in spreadsheet:
        a, b = even_divisors(row)
        total += a/b
    return total


if __name__=='__main__':
    spreadsheet = []
    with open("day2.txt") as f:
        for line in f.readlines():
            row = [int(x) for x in line.split()]
            spreadsheet.append(row)

    print("day 2 part 1:", checksum(spreadsheet))
    print("day 2 part 2:", sum_all_even_divisions(spreadsheet))
