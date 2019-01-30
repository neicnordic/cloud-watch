**Install:**

```
git clone git@github.com:neicnordic/cloud-watch.git
cd cloud-watch/
cd engine/
virtualenv-3.4 .
source bin/activate
pip install --upgrade -r requirements.txt
python setup.py develop
```

**Run**:

```
python app.py
```

**Run engine container**

Run the docker container interactively like this, where `src-dir` is the cloud-watch repo folder, with port 8080 on host mapped to 8080 on container
```
docker run --rm -p 8080:8080 -v <src-dir>:/ -it snapple49/engine:latest sh
```
