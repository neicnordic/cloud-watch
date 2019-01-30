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


    def get_driver(self, name):
        for provider, block in self.config.iteritems():
            if provider == "openstack":
                values = block[name]
                OpenStack = get_driver(Provider.OPENSTACK)
                driver = OpenStack(values['user'],
                                   values['password'],
                                   ex_tenant_name=values['tenant'],
                                   ex_domain_name=values['domain'],
                                   ex_force_auth_url=values['auth_url'],
                                   ex_force_service_region=values['region'],
                                   ex_force_auth_version=values['version'])
                return driver
                
            elif provider == "gce":
                values = block[name]
                ComputeEngine = get_driver(Provider.GCE)
                driver = ComputeEngine(
                        values['gce_service_account_email'],
                        values['gce_credentials_file'],
                        project=values['gce_project'])
               return driver
                        
            elif provider == "aws":
                values = block[name]
                AWS_EC2 = get_driver(Provider.EC2)
                 driver = AWS_EC2(
                        values['aws_access_key_id'],
                        values['aws_secret_access_key']))
              return driver

    def set_drivers(self):
        self.drivers = []

        for provider, block in self.config.iteritems():
            if provider == "openstack":
                for tag, values in block.iteritems():
                    OpenStack = get_driver(Provider.OPENSTACK)
                    self.drivers.append(driver = OpenStack(
                        values['user'],
                        values['password'],
                        ex_tenant_name=values['tenant'],
                        ex_domain_name=values['domain'],
                        ex_force_auth_url=values['auth_url'],
                        ex_force_service_region=values['region'],
                        ex_force_auth_version=values['version']))
                        
            elif provider == "gce":
                for tag, values in block.iteritems():
                    ComputeEngine = get_driver(Provider.GCE)
                    self.drivers.append(driver = ComputeEngine(
                        values['gce_service_account_email'],
                        values['gce_credentials_file'],
                        project=values['gce_project']))
                        
            elif provider == "aws":
                for tag, values in block.iteritems():
                    AWS_EC2 = get_driver(Provider.EC2)
                    self.drivers.append(driver = AWS_EC2(
                        values['aws_access_key_id'],
                        values['aws_secret_access_key']))

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
                    
            elif provider == "gce":
                for tag, values in block.iteritems():
                    tenant = {
                        'provider': provider,
                        'tenant': values['gce_service_account_email'],
                        'region': 'not specified',
                        'name': tag
                    }
                    tenants.append(tenant)
                        
            elif provider == "aws":
                for tag, values in block.iteritems():
                    tenant = {
                        'provider': provider,
                        'tenant': values['aws_access_key_id'],
                        'region': 'not specified',
                        'name': tag
                    }
                    tenants.append(tenant)
                                  
        return tenants
