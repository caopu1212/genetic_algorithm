package 线性回归

class Main {
    static def n = 0
    static def slope = []      //斜率
    static def intercept = 0.0   //截距
    static def rate = 0.001    //学习率
    static def yList = []
    static def xList = []
    static def windDirection = ["北"  : 360.0,
                                "北北西": 337.5,
                                "東北東": 67.5,
                                "北東" : 45.0,
                                "北西" : 315.0,
                                "東"  : 90.0,
                                "南南東": 157.5,
                                "東南東": 112.5,
                                "南東" : 135,
                                "西南西": 247.5,
                                "西北西": 292.5,
                                "西": 270,
                                "南南西": 202.5,
                                "南": 180,
                                "南西": 135,
                                "北北東": 22.5,
    ]
    /**
     * 计算结果
     * @param targetXList
     * @return
     */
    static def calculate(targetXList) {
        def result = 0.0
        for (int i = 0; i < targetXList.size(); i++) {
            result = result + targetXList[i] * this.slope[i]
        }
        //最后加偏置
        result = result + this.intercept
        return result
    }
    /**
     * loss
     * @param targetXList
     * @return
     */
    static def loss(xList, yList) {
        def result = 0
        for (int i = 0; i < xList.size; i++) {
            result += Math.pow(calculate(xList[i]) - yList[i], 2)
        }
        print("loss = " + result + "\n")
    }

    static def gradientDescent(xList, yList, iteration) {
        for (int k = 0; k < iteration; k++) {
            def slopeGradient = []
            for (int i = 0; i < xList[0].size(); i++) {
                slopeGradient.add(0)
            }
            def interceptGradient = 0
            for (int i = 0; i < xList.size; i++) {
                for (int j = 0; j < xList[0].size(); j++) {
                    slopeGradient[j] += (calculate(xList[i]) - yList[i]) * xList[i][j]
                }
                interceptGradient += calculate(xList[i]) - yList[i]
            }
            for (int i = 0; i < slopeGradient.size(); i++) {
                slopeGradient[i] = slopeGradient[i] / (xList.size)
            }
            intercept = interceptGradient / xList.size
            //更新权重
            for (int i = 0; i < slope.size(); i++) {
                slope[i] = slope[i] - rate * slopeGradient[i]
            }
            //更新偏置
            intercept = intercept - rate * interceptGradient

            loss(xList, yList)
        }
    }
    /**
     * 把入参从java arrayList 转给 groovy list
     * @param inputDatas
     */
    static def init(ArrayList<FileData> inputDatas) {
        for (FileData fileData : inputDatas) {
            this.xList.add(fileData.getFeatures())
            this.yList.add(fileData.getIndependentVariable())
        }
        this.n = xList[0].size()
        // 设置初始权重和偏置
        for (int i = 0; i < n; i++) {
            slope.add(Math.random())
        }
        intercept = Math.random()
    }
    /**
     * 读数据(以前的java代码)
     * @return
     */
    static def readData(path) {
        String pathname = path
        File filename = new File(pathname)
        String line = "";
        ArrayList<FileData> totalList = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader); //
            int count = 0;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                //读取每一行，用，分割
                def splitListString = line.split(" ")
                ArrayList<Double> SplitArrayListDouble = new ArrayList<>();

                for (int i = 0; i < splitListString.length; i++) {
                    if (windDirection.containsKey(splitListString[i])) {
                        SplitArrayListDouble.add(windDirection[splitListString[i]]);
                    } else {
                        SplitArrayListDouble.add(splitListString[i].toDouble());
                    }
                }
                //将每一行最后一个作为自变量。剩下的做特征量存入对象。
                FileData fileData = new FileData();
                fileData.setIndependentVariable(SplitArrayListDouble.get(SplitArrayListDouble.size() - 1));
                SplitArrayListDouble.remove(SplitArrayListDouble.size() - 1);
                fileData.setFeatures(SplitArrayListDouble);
                totalList.add(fileData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalList;
    }
    /**
     * 预测
     * @param xList
     * @param yList
     * @param learningRate
     */
    static def predicate(inputXList) {
        return calculate(inputXList)
    }

    public static void main(String[] args) {
//        def inputDatas = readData("F:\\python\\genetic_algorithm\\机器学习\\src\\线性回归\\data")
        def inputDatas = readData("F:\\python\\genetic_algorithm\\机器学习\\src\\线性回归\\yacht-train-0")
        init(inputDatas)
        gradientDescent(xList, yList, 5000)
        println(123)
    }
}

class FileData {
    Double independentVariable
    ArrayList<Double> features

}
