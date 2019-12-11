from .utils import *

class EQ(TestCase):
    def __init__(self, value, expected, epsilon=None):
        self.value = value
        self.expected = expected
        self.epsilon = epsilon
        super(EQ, self).__init__()

    def runTest(self):
        if not self.epsilon:
            self.assertEqual(self.value, self.expected)
        else:
            if not (-self.epsilon <= (self.value - self.expected) <= self.epsilon):
                self.fail("failed: |%s - %s| > %s" % (str(self.value),
                    str(self.expected), str(self.epsilon)))

class Assert(TestCase):
    def __init__(self, method, *E, **F):
        if callable(method):
            self.method = method
        else:
            self.method = self.__getattribute__('assert' + method)
        self.E = E
        self.F = F
        super(Assert, self).__init__()
    def runTest(self):
        self.method(*self.E, **self.F)
