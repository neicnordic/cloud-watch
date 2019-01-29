import openstack

# Initialize and turn on debug logging
openstack.enable_logging(debug=True)

# Initialize cloud (example)
conn = openstack.connect(cloud='private', region='bgo')

for server in conn.compute.servers():
    print(server.to_dict())
