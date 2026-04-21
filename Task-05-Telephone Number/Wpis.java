public abstract class Wpis {
    protected String address;
    public Wpis(String address){
        this.address = address;
    }
    public String getAddress()
    {
        return address;
    }
    public abstract void opisz();
}
