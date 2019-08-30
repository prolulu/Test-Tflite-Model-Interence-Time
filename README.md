# Test-Tflite-Model-Interence-Time
This Android Project is designed to test Tflite Model Interence Time in your Android Devices.<br>
本工程旨在帮助初学者测试tflite模型在安卓设备上的运行速度，支持灰度模型、RGB模型、非量化以及量化模型。

## 1.git clone 
```shell
git clone https://github.com/prolulu/Test-Tflite-Model-Interence-Time.git
```

## 2.analyze model using python
有可能这个tflite model的创建者不是你，若model的输入输出未知，此时就需要用几句简单的python脚本来分析出tflite的输入输出。
```python
import tensorflow as tf
#读取模型，读取input.shape output.shape
model_path = "mobilenet_v1_1.0_224.tflite"
interpreter = tf.lite.Interpreter(model_path=model_path)
interpreter.allocate_tensors()
input_details = interpreter.get_input_details()
print(str(input_details))
output_details = interpreter.get_output_details()
print(str(output_details))
```
此analyze_tfmodel.py文件位于根目录下
<br>
<br>

## 3.run in Android
用Android Studio打开android/的工程
<br>
<br>

### 1.在aasets/文件夹放入待测试的模型
![](https://github.com/prolulu/Test-Tflite-Model-Interence-Time/blob/master/docs/assets.png)<br>
<br>
<br>

### 2.在Config.java中更改参数
![](https://github.com/prolulu/Test-Tflite-Model-Interence-Time/blob/master/docs/para.png)<br>
<br>
<br>

### 3.在drawable/文件夹中添加测试图片
![](https://github.com/prolulu/Test-Tflite-Model-Interence-Time/blob/master/docs/picture.png)<br>
若不考虑图片读取时间，而仅仅考虑model运行速度，此步骤可以省略。<br>
添加完测试图片后，在MainActivity Run()方法下更改测试图片<br>
```java
Bitmap bitmap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.test);//更改测试图片
```
<br>

### 4.运行
![](https://github.com/prolulu/Test-Tflite-Model-Interence-Time/blob/master/docs/run.png)<br>
点击start test即可开始测试，第一次点击因为需要加载模型，所以速度会较长。第二次之后的点击为正常速度。<br>
<br>
![](https://github.com/prolulu/Test-Tflite-Model-Interence-Time/blob/master/docs/non-quant.png)<br>
非量化模型<br>
![](https://github.com/prolulu/Test-Tflite-Model-Interence-Time/blob/master/docs/quant.png)<br>
量化模型<br>

## 注
该项目为本人暑期实习项目，来源于开源，回馈给开源。<br>
若有任何对于本项目的疑问和建议欢迎联系lulutyh@163.com
