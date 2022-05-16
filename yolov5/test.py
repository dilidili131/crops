import cv2
import numpy as np


img = cv2.imread('1221.jpeg')
print(type(img))
img_encode = cv2.imencode('.jpg', img)[1]

data_encode = np.array(img_encode)
str_encode = data_encode.tostring()
print(str_encode)
nparr = np.fromstring(str_encode, np.uint8)
img_decode = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
# cv2.imshow('pic_name',img)

cv2.imshow("img_decode", img_decode)
cv2.waitKey(0)
