import javafx.animation.PauseTransition;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Vector;

public class DataFile {
    private HashMap<String , Integer> mDictionary = null;
    private Decompressor mDecomproser = new Decompressor();
    Vector<Pair<Integer , Character> > Tags = null;
    private boolean Compressed = false;
    private String DataToProcess = null;

    public DataFile(){
        mDictionary = new HashMap<String , Integer>();
        Tags = new Vector<Pair<Integer, Character>>();
    }
    public DataFile(String _DataToProcess){
        mDictionary = new HashMap<String , Integer>();
        Tags = new Vector<Pair<Integer, Character>>();
        DataToProcess = _DataToProcess;
    }
    public void  compresse(@NotNull String input ) {
        int SizeOfInput = input.length();
        String currProccessing  = new String();
        String Prev  = new String();
        int indx  = 1;
        for (int i = 0 ; i < SizeOfInput  ; ++i){
            currProccessing+= input.charAt(i);
            if( !(mDictionary.containsKey(currProccessing))){
                if(currProccessing.length() == 1){
                    mDictionary.put(currProccessing , indx);
                                    /// if we stopped at i then we didn't find string with s[i] , then the next is i
                    Tags.add(new Pair<Integer, Character>(0 ,input.charAt(i) ));
                }
                else {
                    mDictionary.put(currProccessing ,indx );
                    Tags.add(new Pair<Integer, Character>( mDictionary.get(Prev) , input.charAt(i) ));
                }
                indx++;
                currProccessing = "";
//                currProccessing+=input.charAt(i);
            }
            else {
                if (i == input.length() - 1) {

                    Tags.add(new Pair<Integer, Character>(mDictionary.get(currProccessing),  null));
//                    mDictionary.put(currProccessing , indx);
                }
            }
            Prev = currProccessing;

        }
        Compressed = true;
    }
    public void PrintTags(){

        for (Pair<Integer , Character>  currTag: Tags) {
            System.out.println(currTag.getKey() + " " + currTag.getValue());
        }
    }
    public void decompresse(){
//        Decompressor d = new Decompressor();
        System.out.println("----");
        mDecomproser.Decompresse(Tags);

    }
}
