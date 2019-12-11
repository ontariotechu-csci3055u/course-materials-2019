import shutil
import sys
import os

answer = input('Do you want to continue? (y/n) ')
if answer.lower().startswith('y'):
    print("\n")
else:
    print("Cancelled")
    sys.exit(0)

if os.path.exists("./tests"):
    print("./tests already exists.")
    sys.exit(0)

def get_mypath():
    return os.path.abspath(os.path.dirname(sys.argv[0]))

def copy(src, dest, message, copytree=False):
    print(message)
    print("  -> %s" % dest)
    if copytree:
        shutil.copytree(src, dest)
    else:
        shutil.copy(src, dest)

source_autotest_path = get_mypath()
copy(source_autotest_path, './tests/autotest', "autotest library", copytree=True)
copy('./tests/autotest/templates/run_test.py', './tests', "run_test template")

if not os.path.exists('./Makefile'):
    copy('./tests/autotest/templates/Makefile', './Makefile', "Makefile template")

