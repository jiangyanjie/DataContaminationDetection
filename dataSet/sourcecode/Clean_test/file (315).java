package seker.algorithm.gobang.ai;

import seker.algorithm.gobang.chessboard.Direction;

/**
 * 某条线：落点1 + 两个方向8(前进4+后退4) = 共9个Cell为考虑范围
 * 
 * @author seker
 */
enum AI9 {

    /**
     * 在不可能成为5的单条线内落子（毫无意义），对方无须（甚至无空格）防守。
     */
    AI9_0(1),

    /**
     * 已成1的Cell，对方1步防守之后，此9个Cell内还剩0
     */
    AI9_1_0((AI9_0.weight + 1) * Direction.DIRECTIONS.length),

    /**
     * 已成1的Cell，对方1步防守之后，此9个Cell内还剩1
     * 
     * 2*AI9_1_0是两个方向上的两个AI9_1_0；
     * 而 AI9_1_1是相同方向上的两个AI9_1_0；
     */
    AI9_1_1(AI9_1_0.weight * 2),

    /**
     * 已成2的Cell，对方1步防守之后，此9个Cell内还剩0
     */
    AI9_2_0((AI9_1_1.weight + 1) * 2),

    /**
     * 已成2的Cell，对方1步防守之后，此9个Cell内还剩1
     */
    AI9_2_1(AI9_2_0.weight + AI9_1_0.weight),

    /**
     * 已成2的Cell，对方1步防守之后，此9个Cell内还剩2
     * 
     * 2*AI9_2_0是两个方向上的两个AI9_2_0；
     * 而 AI9_2_2是相同方向上的两个AI9_2_0；
     */
    AI9_2_2(AI9_2_0.weight * 2),

    /**
     * 已成4的Cell，对方1步防守之后，此9个Cell内还剩0
     */
    AI9_3_0((AI9_2_2.weight + 1) * 2),

    /**
     * 已成3的Cell，对方1步防守之后，此9个Cell内还剩1
     */
    AI9_3_1(AI9_3_0.weight + AI9_1_0.weight),

    /**
     * 已成3的Cell，对方1步防守之后，此9个Cell内还剩2
     */
    AI9_3_2(AI9_3_0.weight + AI9_2_0.weight),

    /**
     * 已成3的Cell，对方1步防守之后，此9个Cell内还剩3
     * 
     * 2*AI9_3_0是两个方向上的两个AI9_3_0；
     * 而 AI9_3_3是相同方向上的两个AI9_3_0；
     */
    AI9_3_3(AI9_3_0.weight * 2),

    /**
     * 已成4的Cell，对方1步防守之后，此9个Cell内还剩0
     */
    AI9_4_0((AI9_3_3.weight + 1) * 2),

    /**
     * 已成4的Cell，对方1步防守之后，此9个Cell内还剩1
     */
    AI9_4_1(AI9_4_0.weight + AI9_1_0.weight),

    /**
     * 已成4的Cell，对方1步防守之后，此9个Cell内还剩2
     */
    AI9_4_2(AI9_4_0.weight + AI9_2_0.weight),

    /**
     * 已成4的Cell，对方1步防守之后，此9个Cell内还剩3
     * 
     */
    AI9_4_3(AI9_4_0.weight + AI9_3_0.weight),

    /**
     * 已成4的Cell，对方1步防守之后，此9个Cell内还剩4（即：也能成5，对方已无法破解此9个Cell）
     * 
     * 2*AI9_4_0是两个方向上的两个AI9_4_0；
     * 而 AI9_4_4是相同方向上的两个AI9_4_0；
     */
    AI9_4_4(AI9_4_0.weight * 2),

    /**
     * 已成5的Cell
     */
    AI9_5((AI9_4_4.weight + 1) * Direction.DIRECTIONS.length * 2);

    /**
     * 权值
     * 
     * 两个AI4的value用来拼凑成一个AI9，AI9具有权值(weight)。
     * 同一个Cell应该有4个attack AI9和4个define AI9，这8个AI4就是该Cell权值。
     */
    public final int weight;

    /**
     * 私有构造方法
     * 
     * @param w     权值
     */
    private AI9(int w) {
        weight = w;
    }
}
