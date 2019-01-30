from connexion import NoContent
import libcloud_client

def get(name=None):
    config = libcloud_client.get_config('config/config.yaml')
    if name:
        drivers = libcloud_client.get_driver(config, name)
    else:
        drivers = libcloud_client.get_all_drivers(config)
    nodes = libcloud_client.get_all_nodes(drivers)
    return get_node_list(nodes), 200

def get_node_list(nodes):
    outlist = list()
    attrs = ['id', 'name', 'public_ips', 'image', 'size', 'state']
    for node in nodes:
        instance = dict()
        for attr in attrs:
            instance[attr] = getattr(node, attr)
        instance['created_at'] = str(node.created_at)
        if not instance['public_ips']:
            instance['public_ips'] = node.private_ips
        if isinstance(instance['public_ips'], list):
            instance['public_ips'] = instance['public_ips'][0]
        instance['tenant'] = node.driver._ex_tenant_name
        instance['region'] = node.driver._ex_force_service_region
        outlist.append(instance)
    return outlist
