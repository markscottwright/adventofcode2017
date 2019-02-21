def matching_total(input_data):
    total = 0

    for i in range(len(input_data)):
        if i < len(input_data)-1:
            if input_data[i] == input_data[i+1]:
                total+=int(input_data[i])
        else:
            if input_data[i] == input_data[0]:
                total+=int(input_data[i])

    return total

def matching_total2(input_data):
    total = 0
    n = len(input_data)

    for i in range(n):
        j = (i + n//2) % n
        if input_data[i] == input_data[j]:
            total+=int(input_data[i])

    return total

if __name__=='__main__':
    with open("day1.txt") as f:
        input_data = f.read().strip()

    assert 3 == matching_total('1122')
    assert 4 == matching_total('1111')
    assert 0 == matching_total('1234')
    assert 9 == matching_total('91212129')
    print("day 1 part 1:" + str(matching_total(input_data)))

    assert 6 == matching_total2('1212')
    assert 4 == matching_total2('12131415')
    print("day 1 part 2:" + str(matching_total2(input_data)))
