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
        self.set_drivers()


    def set_drivers(self):
        self.drivers = []

        for provider, block in self.config.iteritems():
            if provider == "openstack":
                for tag, values in block.iteritems():
                    OpenStack = get_driver(Provider.OPENSTACK)
                    driver = OpenStack(values['user'], values['password'],
                        ex_tenant_name=values['tenant'],
                        ex_domain_name=values['domain'],
                        ex_force_auth_url=values['auth_url'],
                        ex_force_service_region=values['region'],
                        ex_force_auth_version=values['version'])
                    self.drivers.append(driver)

    def get_drivers(self):
        return list(self.drivers)

    def get_tenants(self):
        tenants = list()
        for provider, block in self.config.iteritems():
            if provider == "openstack":
                for tag, values in block.iteritems():
                    tenant = {
                        'provider': provider,
                        'tenant': values['tenant'],
                        'region': values['region'],
                        'name': tag
                    }
                    tenants.append(tenant)
        return tenants
