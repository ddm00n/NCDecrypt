# NCDecrypt

该工具用于用友NC数据库加密配置文件的解密，适用于老版本的DES加密和较新版本的AES加密。数据库配置文件在：

```
$NCCHOME/ierp/bin/prop.xml
```

## 环境要求

由于jdk8默认不支持AES-256，建议使用更高版本的JDK，本人使用的JDK-11进行测试效果很好。

## 用法

使用-d参数指定字符串进行解密，程序通过是否有#来判断新老版本，因此注意新版的解密一定带上#。

对于老版本的DES加密：

```bash
java -jar NCDecrypt.jar -d jlehfdffcfmohiag
```

对于较新版本的AES加密：

```bash
java -jar NCDecrypt.jar -d #AE7538CF835CC319B2FCE31E3C746EBD -k 0031002E00310037002E00360032000000000000000000002B411706CAE8F01A
```

k参数是密钥，有默认值，可以不进行设置。如果默认值解密失败，可在：

```bash
$NCCHOME/ierp/bin/key.properties
```

文件中读取。

## 注意事项

如果不想安装非JDK8的Java版本，新版本的配置解密可以用python解密，老版本的可以参考```https://github.com/1amfine2333/ncDecode```项目。

```python
# 需要安装 pycryptodome 库
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad
#密钥
secret_key_hex = '0031002E00310037002E00360032000000000000000000002B411706CAE8F01A'
#一个#开头的字符串，删掉#置于此处。
cipher_text_hex = 'AE7538CF835CC319B2FCE31E3C746EBD'
# iv是一个固定值，不要修改
iv_str = '1234567890123456'

key_bytes = bytes.fromhex(secret_key_hex)
cipher_bytes = bytes.fromhex(cipher_text_hex)
iv_bytes = iv_str.encode('utf-8')

cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)

try:
    decrypted = unpad(cipher.decrypt(cipher_bytes), AES.block_size)
    print("解密结果:", decrypted.decode('utf-8'))
except Exception as e:
    print("解密失败:", e)

```

## 参考

https://github.com/1amfine2333/ncDecode