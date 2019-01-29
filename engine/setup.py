
try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

config = {
    'description': 'Cloud-watch Engine',
    'download_url': 'https://github.com/neicnordic',
    'version': '1.0',
    'install_requires': [
        'flask',
        'configparser',
        'connexion',
        'connexion[swagger-ui]'
    ],
    'packages': ['api'],
    'scripts': [],
    'name': 'cloud-watch-engine'
}

setup(**config)
