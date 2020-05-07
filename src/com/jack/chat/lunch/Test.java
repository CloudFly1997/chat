package com.jack.chat.lunch;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/4/26 23:29
 */

public class Test {
    public static void main(String[] args) {
        String test = "1588839700312@何锦康2020届本科毕业综合训练手册.doc@65534";
        System.out.println(test.substring(0,test.indexOf("@")));
        System.out.println(test.substring(test.indexOf("@")+1,test.lastIndexOf("@")));
        System.out.println(test.substring(test.lastIndexOf("@")+1));
        System.out.println(test.substring(0,test.lastIndexOf("@")));
    }
}
