#!/usr/bin/env python3
""" 
 
 
 
 Kim Brugger (29 Jan 2019), contact: kim@brugger.dk
"""

import sys
import pprint
pp = pprint.PrettyPrinter(indent=4)

import argparse
import requests


def tenants(url):
    print( "Listing tentants")


def vms(url):
    print( "Listing vms")


    
def resources(url):
    print( "Listing resoueces")






def main():
    """ The main loop """


    parser = argparse.ArgumentParser()
    parser.add_argument('-u', '--url', default='localhost:8080', help='host and port to connect to')
    subparsers = parser.add_subparsers(dest='subparser')

    tenants_parse   = subparsers.add_parser('tenants')
    vms_parse       = subparsers.add_parser('vms')
    resources_parse = subparsers.add_parser('resources')
    kill_parse      = subparsers.add_parser('kill')

    args = parser.parse_args()

#    pp.pprint (args)

    
    if ( args.subparser == 'tenants' ):
        tenants( args.url)

    if ( args.subparser == 'vms' ):
        vms( args.url)

    if ( args.subparser == 'resources' ):
        resources( args.url)
        

if __name__ == '__main__':
    main()
