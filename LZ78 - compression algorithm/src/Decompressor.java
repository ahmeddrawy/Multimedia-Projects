import javafx.util.Pair;

import java.util.HashMap;
import java.util.Vector;

public class Decompressor {
    private  String Output ;
    private HashMap<Integer  , String> mDictionary  = null;
    Decompressor(){
        Output = new String();
        mDictionary = new HashMap<Integer, String>();
        mDictionary.put(0, "");
    }
    void Decompresse(Vector<Pair< Integer , Character>>Tags){
        for (int i = 0 ; i  < Tags.size() ; ++i){
            String TagSt = mDictionary.get(Tags.get(i).getKey());
            if(Tags.get(i).getValue() != null) {
                Output += TagSt + Tags.get(i).getValue();
                mDictionary.put(i + 1, TagSt + Tags.get(i).getValue());
            }
            else Output+=TagSt; /// we don't need to add the last string to dictionary
                                // because we already finished and won't need it further
        }

        System.out.println(Output);
    }
}
