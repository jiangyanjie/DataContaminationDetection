package seker.algorithm.gobang.ai;

/**
 * 某条线：落点1 + 两个方向8(前进4+后退4) = 共9个Cell为考虑范围
 * 
 * 单条线、单个方向4个Cell的情况：
 */
enum AI4 {
    /** */
    AI4_0("|"), 
    
    /** */
    AI4_1_0("_|"), 
    
    /** */
    AI4_1_1("&|"), 
    
    /** */
    AI4_2_0("__|"), 
    
    /** */
    AI4_2_1("&_|"),
    
    /** */
    AI4_2_2("_&|"),
    
    /** */
    AI4_2_3("&&|"), 

    /** */
    AI4_3_0("___|"), 

    /** */
    AI4_3_1("&__|"), 

    /** */
    AI4_3_2("_&_|"), 

    /** */
    AI4_3_3("&&_|"), 

    /** */
    AI4_3_4("__&|"), 

    /** */
    AI4_3_5("&_&|"), 

    /** */
    AI4_3_6("_&&|"), 

    /** */
    AI4_3_7("&&&|"), 

    /** */
    AI4_4_0("____"), 

    /** */
    AI4_4_1("&___"), 

    /** */
    AI4_4_2("_&__"), 

    /** */
    AI4_4_3("&&__"), 

    /** */
    AI4_4_4("__&_"), 

    /** */
    AI4_4_5("&_&_"), 

    /** */
    AI4_4_6("_&&_"), 

    /** */
    AI4_4_7("&&&_"), 

    /** */
    AI4_4_8("___&"), 

    /** */
    AI4_4_9("&__&"), 

    /** */
    AI4_4_10("_&_&"), 
    
    /** */
    AI4_4_11("&&_&"), 
    

    /** */
    AI4_4_12("__&&"), 

    /** */
    AI4_4_13("&_&&"), 

    /** */
    AI4_4_14("_&&&"), 

    /** */
    AI4_4_15("&&&&");

    /**
     * 对方的棋子或墙壁
     */
    public static final char STOP = '|';
    
    /**
     * 同色的棋子
     */
    public static final char SELF = '&';
    
    /**
     * 空格
     */
    public static final char EMPTY = '_';
    
    /**
     * 当前落下的棋子
     */
    public static final char CURR = '*';
    
    /**
     * 两个AI4的value用来拼凑成一个AI9，AI9具有权值(weight)。
     * 同一个Cell应该有4个attack AI9和4个define AI9，这8个AI4就是该Cell权值。
     */
    public final String value;

    /**
     * 私有构造方法
     * @param v Vaule字符串
     */
    private AI4(String v) {
        value = v;
    }

    /**
     * Value字符串转换成AI4类型
     * 
     * @param value Value字符串
     * @return  AI4，如果没有对应的AI4，则抛出异常
     */
    public static AI4 vauleToAI4(String value) {
        AI4[] ai4s = values();
        if (null == ai4s) {
            throw new RuntimeException("AI4.values() is empty.");
        }

        for (AI4 ai4 : ai4s) {
            if (ai4.value.equalsIgnoreCase(value)) {
                return ai4;
            }
        }
        throw new IllegalArgumentException("No enum constant RobotAI.AI4." + value);
    }
}