from libcloud.compute.types import Provider
from libcloud.compute.providers import get_driver

import libcloud.security

class CloudConfig:

    class Provider:
        def __init__(self, provider):
            self.provider = provider

    def __init__(self, configfile):

        # load config yaml
        self.config = configfile
        # populate all fields

        self.providers = [self.Provider(provider) for provider in self.config]


    def set_drivers(self):
        self.drivers = []

        for provider, block in self.config.iteritems():
            if provider == "openstack":
                for tag, values in block.iteritems():
                    OpenStack = get_driver(Provider.OPENSTACK)
                    self.drivers.append(driver = OpenStack(values['user'], values['password'],
                        ex_tenant_name=values['tenant'],
                        ex_domain_name=values['domain'],
                        ex_force_auth_url=values['auth_url'],
                        ex_force_service_region=values['region'],
                        ex_force_auth_version=values['version']))

    def get_drivers(self):
        return list(self.drivers)

    def get_tenants(self):
        pass