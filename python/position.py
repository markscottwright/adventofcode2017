class Position:
    """A two dimentional point in space"""

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def e(self): return Position(self.x+1, self.y)
    def n(self): return Position(self.x, self.y-1)
    def w(self): return Position(self.x-1, self.y)
    def s(self): return Position(self.x, self.y+1)

    def distance(self, other):
        'Calculate the Manhattan distance between two points'
        return abs(self.x-other.x) + abs(self.y-other.y)

    def surrounding_positions(self):
        return [
            Position(self.x+1, self.y+1),
            Position(self.x+1, self.y),
            Position(self.x+1, self.y-1),
            Position(self.x, self.y+1),
            Position(self.x, self.y-1),
            Position(self.x-1, self.y+1),
            Position(self.x-1, self.y),
            Position(self.x-1, self.y-1)]

    def __str__(self):
        return "[%d,%d]" % (self.x, self.y)

    def __repr__(self):
        return "Position(%d,%d)" % (self.x, self.y)

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __lt__(self, other):
        return self.x < other.x or self.x == other.y and self.y < other.y

    def __hash__(self):
        return hash((self.x, self.y))
