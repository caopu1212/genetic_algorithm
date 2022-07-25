package KNN_k临近算法

class Main {
    def label = ["setosa"    : 0,
                 "versicolor": 1,
                 "virginica" : 2]
    /**
     * 读数据(以前的java代码)
     * @return
     */
    def readData(path) {
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
                    if (label.containsKey(splitListString[i])) {
                        SplitArrayListDouble.add(label[splitListString[i]]);
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
     * 主循环
     * @param dateSet
     * @param input
     * @param K
     * @return
     */
    double pridict(ArrayList<FileData> dateSet, FileData input, int K) {
        List<FileData> fileDataList = []

        //计算 添加 距离到对象
        for (int i = 0; i < dateSet.size(); i++) {
            dateSet.get(i).setDistanceToTarget(calculateDistanceForTwoPoint(input, dateSet.get(i)))
            fileDataList.add(dateSet.get(i))
        }
        //根据距离升序排序
        fileDataList.sort {
            a, b -> a.distanceToTarget.compareTo(b.distanceToTarget)
        }

        def markList = ["", "", ""]
        Integer j = K.times {
            def currentData = fileDataList.get(it)
            //放置mark
            markList[currentData.getIndependentVariable()] += "*"
        }
        def highestOne = 0
        def highestmark = 0
        Integer x = markList.size().times {
            if (markList.get(it).size() >= highestmark) {
                highestOne = it
                highestmark = markList.get(it).size()
            }
        }
        return highestOne
    }
    /**
     * 计算两点间距离
     * @param pointA
     * @param pointB
     * @return
     */
    double calculateDistanceForTwoPoint(FileData pointA, FileData pointB) {
        def features_A = pointA.getFeatures()
        def features_B = pointB.getFeatures()
        def result = 0
        //曼哈顿距离
        for (int i = 0; i < features_A.size(); i++) {
            result += Math.abs(features_A.get(i) - features_B.get(i))
        }
        return result
    }

    static void main(String[] args) {
        Main knn = new Main()
        def resultList = []
        def irisData = knn.readData("F:\\python\\genetic_algorithm\\机器学习\\src\\KNN_k临近算法\\iris_train.csv")
        def testData = knn.readData("F:\\python\\genetic_algorithm\\机器学习\\src\\KNN_k临近算法\\iris_test.csv")
        Integer i = testData.size().times {
            if (knn.pridict(irisData, testData.get(it), 30) == testData.get(it).independentVariable){
                resultList.add(1)
            }else{resultList.add(0)}}
        println(resultList)
        println(resultList.count(1)+"/"+testData.size())
    }
}

class FileData {
    double distanceToTarget
    Double independentVariable
    ArrayList<Double> features
}