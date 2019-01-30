FROM python:2.7-slim

RUN apt update && \
    if [ ! -f /cloud-watch/engine/setup.py ]; then apt install git -y && git clone https://github.com/neicnordic/cloud-watch.git; fi && \
    cd /cloud-watch/engine && \
    pip install --upgrade -r requirements.txt && \
    python setup.py install

WORKDIR /cloud-watch/engine