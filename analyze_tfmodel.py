import tensorflow as tf
#读取模型，读取input.shape output.shape
model_path = "mobilenet_v1_1.0_224.tflite"
interpreter = tf.lite.Interpreter(model_path=model_path)
interpreter.allocate_tensors()
input_details = interpreter.get_input_details()
print(str(input_details))
output_details = interpreter.get_output_details()
print(str(output_details))
