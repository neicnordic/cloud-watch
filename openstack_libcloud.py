import sys
import meh
import os
from os import walk
import collections
import errno
import subprocess
import six
from pkg_resources import iter_entry_points

from libcloud.compute.types import Provider
from libcloud.compute.providers import get_driver

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

def load_credentials():
    """ load credentials data """
    credentials = load_data(os.path.abspath('config.yaml'))
    return credentials

def get_openstack_libcloud():
    credentials = load_credentials()
    libcloud.security.VERIFY_SSL_CERT = False
    OpenStack = get_driver(Provider.OPENSTACK)
    driver = OpenStack(credentials['user'], credentials['password'],
                   ex_force_auth_url=credentials['auth_url'],
                   ex_force_auth_version=credentials['version'])
    return driver

def get_openstack_sizes(driver):
    return driver.list_sizes()[0]

def main():
    driver = get_openstack_libcloud()
    sizes = get_openstack_sizes(driver)
    print sizes

if __name__ == '__main__':
    main()
