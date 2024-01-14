import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static int o=0;
    static int p=1;
    static HashMap<String,Integer> ans = new HashMap<>();
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("alpha=");
        double a = sc.nextDouble();
        System.out.print("train-set name=");
        String trainSet= sc.next();
        System.out.print("test-set(type 'no' if you want to use custom vectors)");
        String testSet= sc.next();
        List<Vec> trainVec =makeVec(trainSet);
        List<Vec>testVec;

        Perceptron perceptron = new Perceptron(trainVec.get(0).getAttri().size(),a);
        double E;
        double Emax=0.03;
        int j=0;
        do {
            perceptron.setS(0);
            for (Vec v : trainVec) {
                perceptron.teach(v, ans.get(v.getAttriDec()));
            }
            E=1.0/trainVec.size()*perceptron.getS();
            j++;
        }while (E>=Emax&&j<1000);
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

                for (Vec v : testVec){
                    int y= perceptron.check(v);
                    for(String s : ans.keySet()){
                        if (ans.get(s)==y)
                            System.out.println("Output: "+s);
                    }
                }
            }
        }else{
            testVec=makeVec(testSet);
            int k=0;
            String[] names= new String[2];
            System.out.println(ans.keySet());
            for (String str:ans.keySet()) {
                names[k++]=str;
            }
            double correct=0;
            int inFile=0;
            for (Vec v : testVec){
                int y=perceptron.check(v);
                System.out.println("NR: "+p++);
                System.out.println("Correct output: "+v.getAttriDec());
                System.out.println("Perceptron output: "+names[y]);

                    inFile++;
                if (y==ans.get(v.getAttriDec()))
                    correct++;

            }

            System.out.println("Accuracy : "+(correct/inFile*100)+"%");

        }
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
            if (o<2) {
                if (!ans.containsKey(temp[temp.length - 1])) {
                    ans.put(temp[temp.length - 1], o);
                    o++;
                }
            }
        }
        return vector;
    }
}
