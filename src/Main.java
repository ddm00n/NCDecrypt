import com.m00n.me.utils.AESUtil;
import com.m00n.me.utils.DESUtil;

public class Main {

    public static void main(String[] args) throws Exception {
        String cipherText = null;
        String keyHex = "0031002E00310037002E00360032000000000000000000002B411706CAE8F01A";

        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i]) && i + 1 < args.length) {
                cipherText = args[i + 1];
            } else if ("-k".equals(args[i]) && i + 1 < args.length) {
                keyHex = args[i + 1];
            }
        }

        if (cipherText == null) {
            System.out.println("用法: java -jar NCDecrypt.jar -d <密文> [-k <密钥，仅当密文以#开头时需要>]");
            return;
        }


        String plainText;

        if (cipherText.startsWith("#")) {
            cipherText = cipherText.substring(1); // 去掉前缀 #
            plainText = AESUtil.decrypt(cipherText, keyHex);
        } else {
            plainText = DESUtil.decrypt(cipherText);
        }

        System.out.println(">>> 明文数据: " + plainText);
    }
}
