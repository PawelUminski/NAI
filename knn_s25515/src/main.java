import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main {

    static int o=0;
    static Set<String>set = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("K=");
        int k = sc.nextInt();
        System.out.print("train-set name=");
        String trainSet= sc.next();
        System.out.print("test-set(type 'no' if you want to use custom vectors)");
        String testSet= sc.next();
        List<Vec>trainVec =makeVec(trainSet);
        List<Vec>testVec;
        if (Objects.equals(testSet, "no")){
            while (true) {
                List<Vec> tmpVec = new ArrayList<>();
                System.out.println("Input vector divided by ',' ");
                String str = sc.next();
                str += ",???";
                String[] tmp = str.split(",");
                List<Double> attributesColumn = new ArrayList<>();
                for (int i = 0; i < tmp.length - 1; i++) {
                    attributesColumn.add(Double.parseDouble(tmp[i]));
                }
                tmpVec.add(new Vec(attributesColumn, tmp[tmp.length - 1]));
                testVec = tmpVec;
                knn(testVec, trainVec, k,true);
            }
        }else{
            testVec=makeVec(testSet);
            knn(testVec,trainVec,k,false);
        }
    }
    public static void knn(List<Vec>testVec, List<Vec>trainVec, int k, boolean isUserInput){
        String result="";
        double correct=0;

        for (Vec test : testVec) {
            o++;
            List<DistanceAttribute> distList = new ArrayList<>();

            for (Vec train : trainVec)
                distList.add(new DistanceAttribute(train.getAttriDec(), distanceVec(test, train)));

            Collections.sort(distList);
            List<String> stringList = new ArrayList<>();

            for (int i = 0; i < k; i++) {
                stringList.add(distList.get(i).getTrainingAttribute());
            }

            int max =0;
            for (String string :set) {
                if (Collections.frequency(stringList,string)>max) {
                    max = Collections.frequency(stringList, string);
                    result = string;
                }
            }

            if (result.equals(test.getAttriDec()))
                correct++;

            System.out.println("Nr: "+o);
            System.out.println("knn output: " + result);
            if (!isUserInput)
            System.out.println("correct output: " + test.getAttriDec()+"\n");

        }
        if (!isUserInput)
            System.out.println("\naccuracy: "+ (correct/testVec.size())*100+"%");
    }
    public static double distanceVec(Vec v1, Vec v2){
        if (v1==null||v2==null)
            return 0.0;
        double res=0;
        for (int i = 0; i < v1.getAttri().size(); i++) {
            res+=Math.pow(v1.getAttri().get(i)-v2.getAttri().get(i),2);
        }
        return res;
    }

    public static List<Vec> makeVec(String path) throws IOException {
        String str;
        List<Vec>vector = new ArrayList<>();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        while ((str = br.readLine())!=null&&(!str.equals(""))){
            String [] temp =str.split(",");
            List<Double>attriCol = new ArrayList<>();
            for (int i = 0; i < temp.length-1; i++) {
                attriCol.add(Double.parseDouble(temp[i]));
            }
            vector.add(new Vec(attriCol,temp[temp.length-1]));
            set.add(temp[temp.length-1]);
        }
        return vector;
    }
}
