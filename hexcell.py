import math


class HexCell:
    """A cell in a hex grid"""

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def n(self): return HexCell(self.x, self.y+1)
    def ne(self): return HexCell(self.x+1, self.y+0.5)
    def se(self): return HexCell(self.x+1, self.y-0.5)
    def s(self): return HexCell(self.x, self.y-1)
    def sw(self): return HexCell(self.x-1, self.y-0.5)
    def nw(self): return HexCell(self.x-1, self.y+0.5)

    def distance(self, other):
        """
        There *has* to be a o(1) algorithm here, but I can't figure it out.  Too
        many off by one possibilities.
        """
        c = self
        d = 0
        while c != other:
            d += 1
            if c.x < other.x:
                if c.y < other.y:
                    c = c.ne()
                else:
                    c = c.se()
            elif c.x > other.x:
                if c.y < other.y:
                    c = c.nw()
                else:
                    c = c.sw()
            elif c.y < other.y:
                c = c.n()
            else:
                c = c.s()
        return d
                

    def surrounding_cells(self):
        return [
            self.n(),
            self.ne(),
            self.se(),
            self.s(),
            self.sw(),
            self.nw(),
            ]

    def __str__(self):
        return "[%d,%d]" % (self.x, self.y)

    def __repr__(self):
        return "HexCell(%d,%d)" % (self.x, self.y)

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __lt__(self, other):
        return self.x < other.x or self.x == other.y and self.y < other.y

    def __hash__(self):
        return hash((self.x, self.y))


if __name__ == "__main__":
    c0 = HexCell(0, 0)
    assert 1 == c0.n().distance(c0)
    assert 1 == c0.ne().distance(c0)
    assert 1 == c0.nw().distance(c0)
    assert 1 == c0.s().distance(c0)
    assert 1 == c0.se().distance(c0)
    assert 1 == c0.sw().distance(c0)
    assert 1 == c0.ne().nw().distance(c0)
    assert 2 == c0.ne().ne().distance(c0)
    assert 2 == c0.ne().se().distance(c0)
    assert 0 == c0.ne().sw().distance(c0)
    assert 1 == c0.ne().s().distance(c0)
    assert 1 == c0.ne().sw().ne().distance(c0)
