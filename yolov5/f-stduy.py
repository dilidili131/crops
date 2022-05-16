# -*- coding: utf-8 -*-#
from flask import Flask, render_template, request, jsonify, make_response
from werkzeug.utils import secure_filename
import os
import cv2
import time
import json
import torch
from PIL import Image
from io import BytesIO
import json
import numpy as np
from datetime import timedelta
import yolo5
import argparse
import torch.backends.cudnn as cudnn
from utils import google_utils
from utils.datasets import *
from utils.utils import *
import base64
set_upload_path = 'static/images'

app = Flask(__name__)

# 定义路由
@app.route("/", methods=['POST'])
def get_frame():
    upload_file = request.files["photo"]

    basepath = os.path.dirname(__file__)
    # 文件上传目录地址
    upload_path = os.path.join(basepath, set_upload_path, secure_filename(upload_file.filename))
    upload_file.save(upload_path)
    # if upload_file:
    #     file_paths = os.path.join(file_path,file_name)
    #     upload_file.save(file_paths)
    #     print(file_paths)


    with torch.no_grad():
        res = yolo5.detect(o_source=upload_path)

        return res
    return res
if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5001)






