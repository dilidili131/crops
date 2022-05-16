# # -*- coding: utf-8 -*-#
# from flask import Flask, render_template, request, jsonify, make_response
# from werkzeug.utils import secure_filename
# import os
# import cv2
# import time
# import json
# import torch
# from PIL import Image
# from io import BytesIO
# import json
# import numpy as np
# from datetime import timedelta
# import yolo5
# import argparse
# import torch.backends.cudnn as cudnn
# from utils import google_utils
# from utils.datasets import *
# from utils.utils import *
# # import global_var as glo
# set_upload_path = 'static/images'
# set_result_path = 'static/images'
# ALLOWED_EXTENSIONS = set(['png', 'jpeg', 'JPG', 'PNG', 'bmp','jpg'])
# # glo._init()
# def allowed_file(filename):
#     return '.' in filename and filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS
# app = Flask(__name__)
# app.send_file_max_age_default = timedelta(seconds=1)
# #URL地址
# @app.route('/', methods=['POST', 'GET'])
# def upload():
#     if request.method == 'POST':
#         #data = request.get_data()
#         #json_data = json.loads(data)
#         f = request.files['file']
#         sss = request.form["identifier"]
#         # print(sss)
#         # glo.set_value("identifier",sss)
#         if not (f and allowed_file(f.filename)):
#             return jsonify({"error": 1001, "msg": "File type exception !"})
#         t = f.filename
#         filename_ = t.split('.')[0]
#         user_input = request.form.get("name")
#         basepath = os.path.dirname(__file__)
#         # 文件上传目录地址
#         upload_path = os.path.join(basepath, set_upload_path, secure_filename(f.filename))
#         f.save(upload_path)
#         print(upload_path)
#         # lab, img, loc, res = yolo.yolo_detect(pathIn=upload_path)
#         with torch.no_grad():
#             res = yolo5.detect(o_source = upload_path)
#
#         #检测结果写到的目录
#         # cv2.imwrite(os.path.join(basepath, set_result_path, filename_+'_res.jpg'), img)
#         return res
#     return render_template('upload.html')
# if __name__ == '__main__':
#     app.run(host='127.0.0.1', port=5000)



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

manager = Flask(__name__)

# 定义路由
@manager.route("/", methods=['POST'])
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
    manager.run(host='0.0.0.0', port=5001)




