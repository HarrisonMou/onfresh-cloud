package com.onfresh.cloud.api.soluation;

public class Checkout {
    public static void main(String[] args) {
        String word = "internationalization";
        String abbr = "i1iz4n";
        Checkout checkout = new Checkout();
        System.out.println(checkout.valid(word, abbr));
    }
    boolean valid(String word, String abbr){
        char[] chars = abbr.toCharArray();
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] < 49 || chars[i] > 57){
                if (word.charAt(index) != chars[i])
                    return false;
                if(i + 1 < chars.length)
                    index++;
            } else {
//                index += chars[i] - '0';
                sb.append(chars[i]);
                if((i + 1 < chars.length) && (chars[i + 1] >= 49 && chars[i + 1] <= 57))
                    continue;
                else {
                    index += Integer.valueOf(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
        }
        return index + 1 == word.length() ? true : false;
    }
}
