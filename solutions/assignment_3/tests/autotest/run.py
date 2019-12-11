import os
import sys
import subprocess
import argparse
import json
import time
import textwrap
import io
from datetime import datetime

def json_as_table(data, indent="", ):
    f = io.StringIO()
    if isinstance(data, list):
        padding = 5
        for (i, x) in enumerate(data):
            text = json_as_table(x, " " * padding)
            heading = "({:d})".format(i+1).ljust(padding)
            print(heading + text[padding:], file=f)
    elif isinstance(data, dict):
        k_len = 0
        for k in data.keys():
            k_text = "%s: " % k
            if k_len < len(k_text):
                k_len = len(k_text)

        for k, v in sorted(data.items()):
            k_text = k.ljust(k_len-2) + ": "
            v_text = json_as_table(v, " "*k_len)
            print("%s%s" % (k_text, v_text[k_len:]), file=f)
    else:
        text = str(data)
        print(text, file=f)

    return textwrap.indent(f.getvalue().rstrip(), indent)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--timeout', default=0, type=int)
    parser.add_argument('--path', default=".")
    parser.add_argument("--format", default=False, action='store_true')

    options = parser.parse_args()

    my_dir = os.path.abspath(os.path.dirname(sys.argv[0]))
    my_path = os.path.join(my_dir, '..')

    repo_dir = options.path
    test_dir = os.path.join(repo_dir, "tests")
    timeout = options.timeout if options.timeout > 0 else None
    timestamp = datetime.now().strftime("%Y-%m-%d")
    report_dir = os.path.join(repo_dir, 'tests/outputs', timestamp)

    env = os.environ.copy()
    env['PYTHONPATH'] = ":".join([repo_dir, my_path])
    env['REPORT_DIR'] = report_dir
    try:
        os.makedirs(report_dir)
    except:
        pass

    for f in os.listdir(test_dir):
        if f.endswith(".py") and f.startswith("run_"):
            cmd = ['python3', os.path.join('tests', f)]
            start = time.time()
            driver_name = os.path.splitext(f)[0]

            env['DRIVER_NAME'] = driver_name

            p = subprocess.Popen(cmd,
                    env=env,
                    cwd=repo_dir,
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    encoding='utf8')
            try:
                (all_output, err) = p.communicate(timeout=timeout)
            except subprocess.TimeoutExpired:
                p.terminate()
                all_output, err = "", "Timeout"

            duration = time.time() - start

            # filter out the rest of the output
            dataline = None
            output = ""
            for line in all_output.split("\n"):
                if line.startswith("autotest_output:"):
                    dataline = line[len("autotest_output:"):].strip()
                else:
                    output += line + "\n"
            output = output.strip()

            result = dict(
                driver=f, 
                timestamp=timestamp,
                output=output,
                error=err,
                duration=duration)

            if dataline:
                data = json.loads(dataline)
                result.update(data)

            # save the report
            report_file = os.path.join(report_dir, driver_name + ".report")

            with open(report_file, 'w') as f:
                print(json_as_table(result), file=f)

            json_file = os.path.join(report_dir, driver_name + ".json")
            with open(json_file, "w") as f:
                json.dump(result, f, indent=4, sort_keys=True)

            # output to stdout
            if options.format:
                print(json_as_table(result))
                print("=" * 60)
            else:
                print(json.dumps(result))
