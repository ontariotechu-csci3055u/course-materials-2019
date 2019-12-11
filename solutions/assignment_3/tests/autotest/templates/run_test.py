from autotest import *

T = Test("Test 1")

T.add(10, "Adding", lambda: EQ(1+2, 3))
T.add(10, "Bad test", lambda: EQ(1, 2))

# See https://docs.python.org/3.6/library/unittest.html
# for all list of helper functions in the form
# of assertXXX(...)

T.add(10, "Assert", lambda: Assert('ListEqual', [0, 1, 2], list(range(3))))
T.add(10, "Another assert", lambda: Assert('In', 'x', ['x', 'y', 'z']))

import math
T.add(10, "Yet another assert", 
        Assert('AlmostEqual', 3.1415, math.pi, places=2))

#
# More testcase
#

# BASH(command, output, expect_file, expect_returncode, ignore_ws, ignore_case)
# NB(source_nb, test_nb, [output_nb])

T.report()
