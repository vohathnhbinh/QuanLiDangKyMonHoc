package additionals;

public enum Shift {
    ONE("7:30-9:30"),
    TWO("9:30-11:30"),
    THREE("13:30-15:30"),
    FOUR("15:30-17:30");

    private String shift_time;

    Shift(String shift_time) {
        this.shift_time = shift_time;
    }

    public String getShift_time() {
        return this.shift_time;
    }

    @Override
    public String toString() {
        return shift_time;
    }

    public static Shift getEnumByValue(String value){
        for(Shift e : Shift.values()){
            if(e.shift_time.equals(value)) return e;
        }
        return null;
    }
}
