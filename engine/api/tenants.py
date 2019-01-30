from connexion import NoContent
import libcloud_client

def get():
    config = libcloud_client.get_config('config/config.yaml')
    tenants = config.get_tenants()
    return tenants, 200
