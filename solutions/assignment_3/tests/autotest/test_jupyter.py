from .utils import *
import nbformat
from nbconvert.preprocessors import ExecutePreprocessor, ClearOutputPreprocessor
import re
import os

ansi_escape = re.compile(r'\x1B[@-_][0-?]*[ -/]*[@-~]')
def remove_ansi(s):
    return ansi_escape.sub('', s)

def NB(notebook, test_notebook):
    working_dir = '.'

    with open(notebook, 'r') as f:
        nb = nbformat.read(f, as_version=4)

    with open(test_notebook, 'r') as f:
        test_nb = nbformat.read(f, as_version=4)

    test_start = len(nb.cells)

    nb.cells.extend(test_nb.cells)

    # run the notebook
    meta = dict(metadata=dict(path=working_dir))
    p1 = ClearOutputPreprocessor()
    p2 = ExecutePreprocessor(allow_errors=True)

    p1.preprocess(nb, meta)
    p2.preprocess(nb, meta)

    try:
        output_notebook = os.path.join(os.environ['REPORT_DIR'],
                os.environ['DRIVER_NAME'] + '.ipynb')
        with open(output_notebook, 'w') as f:
            nbformat.write(nb, f)
    except:
        pass

    tests = []
    for cell in nb.cells[test_start:]:
        if cell.cell_type == 'code':
            tests.append(CellTest(cell))

    return tests

def traceback_string(lines):
    return "\n".join(remove_ansi(x) for x in lines)

class CellTest(TestCase):
    def __init__(self, cell):
        super(CellTest, self).__init__()
        self.cell = cell
        self.with_desc(cell.source)

    def runTest(self):
        if not 'outputs' in self.cell:
            raise RuntimeError("cell does not have outputs")

        err = False
        messages = []
        for output in self.cell.outputs:
            if output.output_type == 'error':
                err = True
            if 'text' in output:
                messages.append(output.text)
            if 'traceback' in output:
                messages.append(traceback_string(output.traceback))
        if err:
            raise TestException("Test failed:\n" \
                    + ("-" * 80) \
                    + "\n".join(messages))
