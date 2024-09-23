FROM python:latest
LABEL Maintainer="douglasbello"
WORKDIR /usr/app/src
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY scrapper.py .
CMD ["python", "./scrapper.py"]