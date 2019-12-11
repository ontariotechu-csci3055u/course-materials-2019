from autotest import *

def add(x,y):
    return x + y

T = Test("Quiz",
        """
        This quiz is a basic test on the functionalities
        of the autotest library.
        """)

T.add(10, 
        "simple",
        """
        This is the first test.
        It performs some sort of simple 1 == 1 check.

        ```
        1 == 1
        ```
        """,
        EQ(1,1))
T.add(20,
        "testing add", 
        """

        In this test, we are adding two numbers, 
        and we want to make sure that it is the
        same as the Python built-in function.

        Here we have:

        ```
        add(1, 2) == 1 + 2
        ```
        """,
        lambda: EQ(add(1,2), 1+2))
T.add(5, 
        "expected to fail",
        lambda: EQ(1, 0))
T.add(5,
        "expected to pass",
        lambda: EQ(1, 0.9, 0.1))
T.add(5,
        "expected to fail",
        lambda: EQ(1, 0.9, 0.01))

T.add(5, "expect to pass",
        lambda: Assert('ListEqual', [0,1,2], list(range(3))))

T.add(5, "expect to pass",
        lambda: Assert('SetEqual', set([1,2,0]), set(list(range(3)))))

T.report()
