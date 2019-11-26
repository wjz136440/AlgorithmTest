package Test;

/**
 * @program: ad-flink
 * @description:
 * @author: joshua.Wang
 * @create: 2019-11-13 16:54
 **/
public class DesignMode {

    private static DesignMode designMode;

    private DesignMode(){ }

    public static DesignMode getDesignMode(){
        if(designMode == null){
            designMode = new DesignMode();
        }
        return designMode;
    }
}
