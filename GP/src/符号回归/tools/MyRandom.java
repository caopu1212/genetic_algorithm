package src.符号回归.tools;

import java.math.BigDecimal;

public class MyRandom {

    /**
     * 生成指定范围，指定小数位数的随机数
     * @param max 最大值
     * @param min 最小值
     * @param scale 小数位数
     * @return
     */
    public double makeRandom(float max, float min, int scale){
        BigDecimal cha = new BigDecimal(Math.random() * (max-min) + min);

        return cha.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();//保留 scale 位小数，并四舍五入
    }
}
