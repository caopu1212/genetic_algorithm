package 通过输入输出求公式.gp;

import java.util.ArrayList;

public class CommonTools {
    public Double getAverage(ArrayList<Double> inputList) {
        Double total = 0.0;
        for (Double number : inputList
        ) {
            total = total + number;
        }
        return total/inputList.size();
    }


}
