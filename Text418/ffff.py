import json
# print("feige011 is start")
# i=0
# while(i<=3):
#     i=i+1
#     print("feige011 is ready")
# print("feige011 is end")
# s='''print("feige011 is start")
# i=0
# while(i<=3):
#     i=i+1
#     print("feige011 is ready")
# print("feige011 is end")'''
# exec(s)
# file=open("fei.txt","r")
file=open("fei.txt",'r')
block=file.readline()
block=eval(block)
list=block['block']
for i in list:
    if i['other'] is '2':
        exec(i['body'])
# print(block['head'])
# print(list[1])
# s=json.dumps(block)
# print(s)
# print(file.readline())
# file.readline();