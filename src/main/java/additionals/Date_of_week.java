package additionals;

public enum Date_of_week {
    MON("Thứ 2"),
    TUE("Thứ 3"),
    WED("Thứ 4"),
    THU("Thứ 5"),
    FRI("Thứ 6"),
    SAT("Thứ 7"),
    SUN("Chủ nhật");

    private String date;

    private Date_of_week(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return date;
    }

    public static Date_of_week getEnumByValue(String value){
        for(Date_of_week e : Date_of_week.values()){
            if(e.date.equals(value)) return e;
        }
        return null;
    }
}
