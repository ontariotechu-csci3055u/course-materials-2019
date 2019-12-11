import unittest
import traceback
import io
import textwrap
import inspect
import json
import re

def err_string(err):
    if not err:
        return None
    type, value, tb = err
    f = io.StringIO()
    with io.StringIO() as f:
        print(value, file=f)
        #for line in traceback.format_tb(tb):
        #    print(">" + line.rstrip(), file=f)
        return f.getvalue()

class Test(unittest.TestResult):
    def __init__(self, name, desc=None):
        super(Test, self).__init__()
        self.name = name
        self.results = []
        self.desc = None

    def addError(self, test, err):
        self.results.append((test, err))

    def addFailure(self, test, err):
        self.addError(test, err)

    def addSuccess(self, test):
        self.addError(test, None)

    def report(self):
        total = 0.
        success = 0.
        results = []

        for i, (unit, err) in enumerate(self.results):
            row = dict(grade=unit.grade,
                    title=unit.title,
                    desc=unit.desc)
            if err:
                row['err'] = err_string(err)
                row['ok'] = False
            else:
                row['ok'] = True
                success += unit.grade

            total += unit.grade

            results.append(row)

        data = dict(name=self.name,
                total=total,
                success=success,
                results=results)

        print("autotest_output:", json.dumps(data, sort_keys=True))

        return self


    #def test(self, *entries):
    #    run_tests(self, *entries)
    #    return self

    def add(self, *E, **F):
        # guessing grade, title, description, and the testcase

        # (grade, title, tc)
        if len(E) == 3:
            (grade, title, tc) = E
            desc = None
        elif len(E) == 4:
            (grade, title, desc, tc) = E
        else:
            raise Exception(".add(grade, title, desc, tc)")

        run_tests(self, dict(grade=grade, title=title, desc=desc, tc=tc))
        return 

def strip_empty_headers(s):
    if not s:
        return s

    lines = s.split("\n")
    for (i, line) in enumerate(lines):
        if len(line.strip()) > 0:
            break

    return "\n".join(lines[i:])

class TestCase(unittest.TestCase):
    def __init__(self):
        self.grade = 0.0
        self.title = ""
        self.desc = ""
        super(TestCase, self).__init__()

    def with_desc(self, text):
        if text:
            text = strip_empty_headers(text)
            text = textwrap.dedent(text)
            text = text.strip()
            if self.desc:
                self.desc += "\n" + text.strip()
            else:
                self.desc = text
        return self

def run_tests(result, *entries):
    suite = unittest.TestSuite()

    # add the test cases to the suite
    for entry in entries:
        grade = entry['grade']
        title = entry['title']
        tc = entry['tc']
        desc = entry['desc']

        if isinstance(tc, TestCase):
            tc.grade = grade
            tc.title = title
            tc.with_desc(desc)
            suite.addTest(tc)

        elif callable(tc):
            try:
                more_desc = inspect.getsource(tc).strip()
            except:
                more_desc = ""

            tc = tc().with_desc(desc)
            tc.grade = grade
            tc.title = title
            tc.with_desc(more_desc)
            suite.addTest(tc)

        elif isinstance(tc, list):
            if len(tc) > 0:
                entries = []
                for i, t in enumerate(tc):
                    new_grade = grade / len(tc)
                    new_title = "%s (%d)" % (title, i+1)
                    new_desc = desc if i == 0 else None
                    entries.append(dict(grade=new_grade,
                            title=new_title,
                            tc=t,
                            desc=new_desc))

                return run_tests(result, *entries)

        else:
            raise RuntimeError("unknown testcase")

    suite.run(result)

class TestException(Exception):
    def __init__(self, message):
        super(TestException, self).__init__(message)
