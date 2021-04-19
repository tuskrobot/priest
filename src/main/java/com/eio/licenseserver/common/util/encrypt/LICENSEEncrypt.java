package com.eio.licenseserver.common.util.encrypt;

import java.util.Arrays;
import java.util.List;

public class LICENSEEncrypt {

    private static List<Integer> tokenSplitRuleList = Arrays.asList(3, 7, 13, 19, 29, 37, 43, 21);
    private static List<Integer> privateKeySplitRuleList = Arrays.asList(2, 5, 11, 17, 23, 31, 41, 47);

    public static String decrypt(String tokenCipherText) throws Exception {
        StringBuffer token = new StringBuffer();
        StringBuffer tokenTemp = new StringBuffer();
        StringBuffer privateKey = new StringBuffer();
        StringBuffer tokenCipherTextBuffer = new StringBuffer(tokenCipherText);
        int tokenCipherTextStart = 0;
        int tokenCipherTextEnd = 0;
        for(int i = 0; i < privateKeySplitRuleList.size(); i++){
            tokenCipherTextStart += privateKeySplitRuleList.get(i);
            tokenCipherTextEnd = tokenCipherTextStart + tokenSplitRuleList.get(i);

            token.append(tokenCipherTextBuffer.substring(tokenCipherTextStart, tokenCipherTextEnd));
            tokenTemp.append(" " + tokenCipherTextBuffer.substring(tokenCipherTextStart, tokenCipherTextEnd));
            tokenCipherTextBuffer.delete(tokenCipherTextStart, tokenCipherTextEnd);
        }

        String tokenStr = token.toString();
        String privateKeyStr = tokenCipherTextBuffer.toString();
        System.out.println("tokenCipherText:" + tokenCipherText);
        System.out.println("tokenTemp:" + tokenTemp);
        return RSAEncrypt.decrypt(tokenStr, privateKeyStr);
    }


    public static String encrypt(String register, String privateKey){
        StringBuffer registerBuffer = new StringBuffer(register);
        StringBuffer privateKeyBuffer = new StringBuffer(privateKey);
        int tokenStart = 0;
        int tokenEnd = 0;
        int tokenStep = 0;
        int privateKeyIndex = 0;
        for(int i=0; i < tokenSplitRuleList.size(); i++){
            if(0 == i){
                privateKeyIndex += privateKeySplitRuleList.get(i);
            }else{
                privateKeyIndex += privateKeySplitRuleList.get(i) + tokenSplitRuleList.get(i-1) ;
            }
            tokenEnd += tokenSplitRuleList.get(i);
            System.out.println("privateKeyIndex:" + privateKeyIndex + " tokenStart: " + tokenStart + " tokenEnd" + tokenEnd);
            privateKeyBuffer.insert(privateKeyIndex, registerBuffer.substring(tokenStart, tokenEnd));
            tokenStart = tokenEnd;

        }

        return privateKeyBuffer.toString();
    }
}


