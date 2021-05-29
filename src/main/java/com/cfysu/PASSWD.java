package com.cfysu;

import ch.qos.logback.core.util.FileUtil;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2021/5/28.
 */
public class PASSWD {

    public static void main(String[] args) {
        PASSWD passwd = new PASSWD();

        List<String> templateOne = Arrays.asList("cj", "lcj", "jd", "yhd", "jingdong", "yihaodian");

        List<String> tempalateTwo = Arrays.asList("->pwd=");

        List<String> tempateThree = Arrays.asList("1425", "1234", "142536", "123456", "12345", "14253");

        List<String> pailieOne = passwd.dxxplzuList(templateOne);

        List<String> pailieTwo = passwd.dxxplzuList(tempalateTwo);

        List<String> pailieThree = passwd.dxxplzuList(tempateThree);

        StringBuilder stringBuilder = new StringBuilder();
        for (String a : pailieOne) {
            for (String b : pailieTwo) {
                for (String c : pailieThree) {
                    stringBuilder.append("$").append(a).append(b).append(c).append("\n");
                }
            }
        }

        org.aspectj.util.FileUtil.writeAsString(new File("D:\\Download\\RARmmpj\\RARmmpj\\english.dic"), stringBuilder.toString());

    }

    private List<String> dxxplzuList(List<String> list) {
        List<String> res = new ArrayList<>();
        for (String s : list) {
            List<String> strings = daxiaoxiepailiezuhe2(s);
            res.addAll(strings);
        }
        return res;
    }

    public List<String> pailiezuhe(String source) {
        String input = "abc";
        //1.开始排列
        List<String> sortResult = sort(input);
        System.out.println("排列组合,字符串:" + input);
        //2.消除重复列
        HashSet h = new HashSet(sortResult);
        sortResult.clear();
        sortResult.addAll(h);
        //3.打印输出
        sortResult.forEach(e -> System.out.println(e));
        //4.打印个数
        System.out.println("排列组合个数：" + sortResult.size());
        return sortResult;
    }

    private List<String> sort(String input) {
        List<String> sortList = new ArrayList();
        if (input == null || "".equals(input)) {
            System.out.println("提示：您输入了空字符,请输入有效值！");
            return new ArrayList();
        }
        char leftChar = input.charAt(0);
        if (input.length() > 1) {
            String rightString = input.substring(1, input.length());
            List<String> rightStringSortedList = sort(rightString);
            rightStringSortedList.forEach((e) -> {
                for (int i = 0; i < e.length() + 1; i++) {
                    sortList.add(new StringBuffer(e).insert(i, leftChar).toString());
                }
            });
        } else {
            sortList.add(String.valueOf(leftChar));
        }
        return sortList;
    }

    public List<String> daxiaoxiepailiezuhe(String line) {
        List<String> allList = new ArrayList<>();
        char array[] = line.toCharArray();
        //标识字符串的某个位置是字母还是非字母
        boolean flag[] = new boolean[array.length];
        int n = 1;  //字符串大小写组合的总数
        int count = 0;   //统计给定的字符串中包含的字母个数
        for (int i = 0; i < array.length; i++) {
            if (Character.isLetter(array[i])) {
                flag[i] = true; //是字母
                n *= 2; //每出现一个字母，组合的总数乘以2
                count++;
            } else {
                flag[i] = false; //非字母
            }
        }
        //打印出每一种字符串组合
        for (int i = 0; i < n; i++) {
            //将0~n-1转化为二进制数字
            String temp = Integer.toBinaryString(i);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < count - temp.length(); j++) {
                //追加‘0’，将0变为0000的形式
                sb.append("0");
            }
            sb.append(temp);
            char tempArray[] = sb.toString().toCharArray();
            int k = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < flag.length; j++) {
                char val = array[j]; //先取出该位置上的字符
                if (flag[j]) { //该位置上是字母
                    if ('0' == tempArray[k]) { //小写
                        stringBuilder.append(val);
                    } else { //大写
                        stringBuilder.append(val);
                    }
                    k++;
                } else { //该位置上非字母
                    stringBuilder.append(val);
                }
            }
            allList.add(stringBuilder.toString());
        }
        return allList;
    }

    public List<String> daxiaoxiepailiezuhe2(String string) {
        List<String> res = new ArrayList<>();
        dfs(res, string.toCharArray(), 0);
        return res;
    }

    private static void dfs(List<String> res, char[] arr, int pos) {
        res.add(String.valueOf(arr));
        for (int i = pos; i < arr.length; i++) {
            if (arr[i] <= 'z' && arr[i] >= 'a') {
                arr[i] = Character.toUpperCase(arr[i]);
                dfs(res, arr, i + 1);
                arr[i] = Character.toLowerCase(arr[i]);
            }
        }
    }
}
