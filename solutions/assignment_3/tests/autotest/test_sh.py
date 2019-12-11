from .utils import *

import subprocess
import os
import shutil

import tempfile
class BASH(TestCase):
    def __init__(self, 
            command, 
            output=None,
            expect_file=None,
            expect_returncode=None,
            expect_output=None,
            ignore_ws=False,
            ignore_case=False):

        super(BASH, self).__init__()

        self.command = command
        self.output = output
        self.expect_file = expect_file
        self.expect_output = expect_output
        self.ignore_ws = ignore_ws
        self.ignore_case = ignore_case
        self.expect_returncode = expect_returncode

    def setUp(self):
        # Run the command
        process = subprocess.Popen(self.command, stdout=subprocess.PIPE)
        (output_data, err_data) = process.communicate()
        process.wait()

        self.process = process
        self.output_data = output_data.decode('utf8')

        # Save the output if necessary
        if self.output is None:
            (_, tmpfile) = tempfile.mkstemp()
            with open(tmpfile, 'w') as f:
                f.write(self.output_data)
            self.output_file = tmpfile
        else:
            self.output_file = self.output

    def tearDown(self):
        if self.output_file and os.path.exists(self.output_file):
            os.remove(self.output_file)

    def runTest(self):
        if self.expect_returncode:
            self.assertEqual(self.expect_returncode, self.process.returncode)

        if self.expect_output:
            if callable(self.expect_output):
                self.assertTrue(self.expect_output(self.output_data))
            else:
                a = self.output_data
                b = self.expect_output

                if self.ignore_case:
                    a = a.lower()
                    b = b.lower()
                if self.ignore_ws:
                    a = " ".join(a.strip().split())
                    b = " ".join(b.strip().split())
                self.assertEqual(a, b)

        if self.expect_file:
            diff = ['diff', '-y', '--width', '80']
            if self.ignore_ws:
                diff.extend(['--ignore-space-change', 
                    '--ignore-trailing-space',
                    '--ignore-blank-lines'])
            if self.ignore_case:
                diff.extend(['--ignore-case'])

            if self.output_file:
                output_file = self.output_file
            elif self.tmpdir:
                output_file = os.path.join(self.tmpdir, "output")

            diff.extend([output_file, self.expect_file])

            p = subprocess.Popen(diff, stdout=subprocess.PIPE)
            (diff_output, diff_err) = p.communicate()
            p.wait()

            if not p.returncode == 0:
                raise TestException("Output vs Expected\n" \
                        + ("-" * 80) \
                        + "\n" \
                        + diff_output.decode('utf8'))
