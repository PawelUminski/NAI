
import java.util.List;


public class Vec {
    List<Double>attri;
    String attriDec;
    public Vec(List<Double>attributes,String str){
        this.attri=attributes;
        this.attriDec=str;
    }

    public List<Double> getAttri() {
        return attri;
    }

    public String getAttriDec() {
        return attriDec;
    }


}
