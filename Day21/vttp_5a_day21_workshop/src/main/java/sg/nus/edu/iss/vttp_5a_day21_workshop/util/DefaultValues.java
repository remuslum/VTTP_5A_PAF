package sg.nus.edu.iss.vttp_5a_day21_workshop.util;

public enum DefaultValues {
    LIMIT(5),
    OFFSET(0);

    private final int value;

    private DefaultValues(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
