package com.zzyl.utils;

/**
 *  工具类补全
 */
public class NoProcessing {

    /***
     *  处理补全编号，所有编号都是15位，共5层，每层关系如下：
     * 第一层：100000000000000
     * 第二层：100001000000000
     * 第三层：100001001000000
     * 第四层：100001001001000
     * 第五层：100001001001001
     * 我们在需要查询当前1级节点以下所有节点时，就不用再递归查询，使用like "resource_no%"即可。
     * @param input 如果为001000000000000处理完成后则变为001
     * @return
 * @return: java.lang.String
     */
    public static String processString(String input) {
        int step = input.length() / 3;
        for (int i =0;i<step;i++ ){
            String targetString = input.substring(input.length()-3,input.length());
            if ("000".equals(targetString)){
                input = input.substring(0,input.length()-3);
            }else {
                break;
            }
        }
        return input;
    }

    public static void main(String[] args) {
//        String input = "100001001001000";
        String input = "100000000000000";
        String processedString = createNo(input,false);
        System.out.println(processedString);
    }

    /***
     *  生产层级编号
     *  据父节点编号生成子节点编号
     *      *   例如：
     *           peerNode=false情况1增加子节点：这个父节点没有子节点，传入父节点100001000000000，新增加一级节点编号：100001001000000
     *           peerNode=true情况2增加平级节点：这个父节点有子节点，传入平级最大的子节点编号100001002000000 新增加一级节点编号：100001003000000
     * @param input 输入编号,没有子节点传入父节点编号，有子节点传入最大子节点编号
     * @param peerNode 是否下属节点
     * @return
     * @return: java.lang.String
     */
    public static String createNo(String input,boolean peerNode) {
        int step = input.length() / 3;
        int supplement = 0;
        for (int i =0;i<step;i++ ){
            String targetString = input.substring(input.length()-3,input.length());
            if ("000".equals(targetString)){
                input = input.substring(0,input.length()-3);
                supplement++;
            }else {
                break;
            }
        }
        if (peerNode){
            input = String.valueOf(Long.valueOf(input) + 1L);
            for (int i =0;i<supplement;i++ ){
                input = input+"000";
            }
        }else {
            input = String.valueOf(Long.valueOf(input+"001"));
            for (int i =0;i<supplement-1;i++ ){
                input = input+"000";
            }
        }
        return input;
    }



}
