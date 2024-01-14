import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    int vSize;
    double alpha;
    double theta;
    double s=0;
    List<Double> vecWeight=new ArrayList<>();
    public Perceptron(int vSize, double alpha){
        this.vSize=vSize;
        this.alpha=alpha;
        for (int i = 0; i < vSize; i++) {
            this.vecWeight.add(Math.random()*5);
        }
        this.theta=0.6;
    }


    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public void teach(Vec v, int correctAns){
        double net=0;
        for (int i = 0; i < v.getAttri().size(); i++) {
            net+= v.getAttri().get(i)*this.vecWeight.get(i);
        }

        int y=(net>=this.theta?1:0);
        s+=Math.pow(correctAns-y,2);
        if (y!=correctAns){
            List<Double> tmpVec= new ArrayList<>(this.vecWeight);
            for (int i = 0; i < v.getAttri().size(); i++) {
                tmpVec.set(i,this.vecWeight.get(i)+this.alpha*(correctAns-y)*v.getAttri().get(i));
            }
            this.vecWeight=tmpVec;
            this.theta=this.theta-this.alpha*(correctAns-y);
        }
    }
    public int check(Vec v){
        double net =0;
        for (int i = 0; i < v.getAttri().size(); i++) {
            net+= v.getAttri().get(i)*this.vecWeight.get(i);
        }

        return (net>= this.theta?1:0);
    }
}
