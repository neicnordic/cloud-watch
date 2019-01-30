import sys
import os
from libcloud.compute.types import Provider
from libcloud.compute.providers import get_driver
from config import CloudConfig

import libcloud.security

PY3 = sys.version_info[0] == 3
if PY3:
    BinaryType = bytes
else:
    BinaryType = str

class InvalidDataFormat(Exception):
    """Invalid data exception"""
    pass

class InvalidInputData(Exception):
    """Invalid input data exception"""
    pass

class MalformedYAML(InvalidInputData):
    """Invalid YAML data exception"""
    pass

def get_format(fmt):
    """Returns a data loading function"""
    try:
        return FORMATS[fmt]()
    except ImportError:
        raise InvalidDataFormat(fmt)

def _load_yaml():
    import yaml
    return yaml.load, yaml.YAMLError, MalformedYAML

FORMATS = {
    'yaml': _load_yaml,
    'yml': _load_yaml,
}

def load_data(path):
    """ read yaml data from file """
    ext = os.path.splitext(path)[1][1:]

    # read from data file
    with open(path) as d_file:
        data = d_file.read()

    if data:
        # Check if format is valid
        try:
            func, except_exc, raise_exc = get_format(ext)
        except InvalidDataFormat:
            if ext in ('yml', 'yaml'):
                raise InvalidDataFormat('%s: install pyyaml to fix' % format)
        # Return data
        try:
            return func(data) or {}
        except except_exc:
            raise raise_exc(u'%s ...' % data[:60])
    else:
        return {}

def load_credentials(filename):
    """ load credentials data """
    credentials = load_data(os.path.abspath(filename))
    return credentials

def get_config(filename='example-config.yaml'):
    credentials = load_credentials(filename)
    return CloudConfig(credentials)

def get_all_drivers(config):
    drivers = []
    for driver in config.get_drivers():
        drivers.append(driver.list_nodes())
    return drivers

def get_driver(config, name):
    drivers = []
    driver = config.get_driver(name)
    drivers.append(driver.list_nodes())
    return drivers

def get_all_nodes(drivers):
    nodes = []
    for driver in drivers:
        for node in driver:
            nodes.append(node)
    return nodes

def main():
    config = get_config()
    drivers = get_all_drivers(config)
    nodes = get_all_nodes(drivers)
    print (nodes)


if __name__ == '__main__':
    main()
