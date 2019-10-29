public class Appearance {

    private int docID;
    private int frequency;

    public Appearance(){}

    public Appearance(int docID, int frequency){
        this.docID = docID;
        this.frequency = frequency;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public int getDocID() {
        return docID;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }
}
