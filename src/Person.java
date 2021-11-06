public class Person {
    String fn;
    String ln;
    int bm;
    int bd;
    int by;
    public Person(){

    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public void setBd(int bd) {
        this.bd = bd;
    }

    public int getBd() {
        return bd;
    }

    public int getBm() {
        return bm;
    }

    public int getBy() {
        return by;
    }


    public String getFn() {
        return fn;
    }

    public String getLn() {
        return ln;
    }

    public void setBm(int bm) {
        this.bm = bm;
    }

    public void setBy(int by) {
        this.by = by;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fn='" + fn + '\'' +
                ", ln='" + ln + '\'' +
                ", bm=" + bm +
                ", bd=" + bd +
                ", by=" + by +
                '}';
    }
}
