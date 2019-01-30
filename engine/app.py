import os
import json
import socket
import connexion
import yaml
from flask import request
from flask import Response

app = connexion.App(__name__, specification_dir='./')

#cors = CORS(app.app, resources={r"/api/v1/status*": {"origins": "*"}})

# Production server config
app.app.config.from_pyfile('production.cfg')

# Read the api.yaml file to configure the endpoints
app.add_api('api/api.yaml', strict_validation=True, validate_responses=False)

# Default landing page
@app.route("/")
def docs():
    output = "<h2>Cloud Watch REST API server</h2>"
    output += '<ul><li><a href=/v1/ui>api docs</a></li>'
    return output

@app.route("/health")
def health():
    version = 'version.yaml'
    script_dir = os.path.dirname(__file__)
    abs_file_path = os.path.join(script_dir, version)
    with open(abs_file_path, 'r') as stream:
        try:
            output = yaml.load(stream)
        except yaml.YAMLError as exc:
            print(exc)
            output = dict()
    code = 200
    output['host'] = socket.gethostname()
    msg = json.dumps(output, sort_keys=True, indent=4)
    return Response(msg, mimetype='text/json', status=code)

if __name__ == '__main__':
    host = app.app.config.get('HOST', '0.0.0.0')
    debug = app.app.config.get('DEBUG', False)
    port = app.app.config.get('PORT', 5000)
    app.run(host=host, port=port, debug=debug)
