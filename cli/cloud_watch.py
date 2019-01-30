#!/usr/bin/env python
""" 
 
 
 
 Kim Brugger (29 Jan 2019), contact: kim@brugger.dk
"""

import sys
import pprint
pp = pprint.PrettyPrinter(indent=4)

import argparse
import requests
from prettytable import PrettyTable


def pull_rest(url):

#    print( url )
    
    r = requests.get( url )
    
    if r.status_code == 200:
        return r.json()
    else:
        raise RuntimeError("Could not connect to engine")



def tenants(url):
    data = pull_rest("{url}/{path}".format( url=url, path='tenants'))
    x = PrettyTable()

    x.field_names = ["Name", 'Tenant', 'Region']
    x.align["Name"] = "l"
    x.align["Tenant"] = "l"
    x.align["Region"] = "l"
    for tenant in data:
        x.add_row( [ tenant['name'], tenant['region'], tenant['tenant']] )

    print(x)

    

def vms(url):
    data = pull_rest("{url}/{path}".format( url=url, path='instances'))
    x = PrettyTable()

    x.field_names = ["UUID", "Name", "IPs", 'state', "Created at", 'Tenant', 'Region']
    x.align["UUID"] = "l"
    x.align["Name"] = "l"
    x.align["IPs"] = "l"
    x.align["State"] = "l"
    x.align["Tenant"] = "l"
    x.align["Region"] = "l"
    for instance in data:
        pp.pprint( instance )
        x.add_row( [ instance['id'], instance['name'], instance['public_ips'],
                     instance['state'], instance['created_at'],
                     instance['tenant'], instance['region']] )

    print(x)


def main():
    """ The main loop """


    parser = argparse.ArgumentParser()
    parser.add_argument('-u', '--url', default='http://localhost:8080/v1', help='host and port to connect to')
    subparsers = parser.add_subparsers(dest='subparser')

    tenants_parse   = subparsers.add_parser('tenants')
    vms_parse       = subparsers.add_parser('vms')
#    resources_parse = subparsers.add_parser('resources')
#    kill_parse      = subparsers.add_parser('kill')

    args = parser.parse_args()

#    pp.pprint (args)

    
    if ( args.subparser == 'tenants' ):
        tenants( args.url)

    if ( args.subparser == 'vms' ):
        vms( args.url)

#    if ( args.subparser == 'resources' ):
#        resources( args.url)
        

if __name__ == '__main__':
    main()
