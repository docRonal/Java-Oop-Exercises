public class WektoryRoznejDlugosciException  extends Exception
{

    private int dlugosc1;
    private int dlugosc2;
    public WektoryRoznejDlugosciException (int dlugosc1, int dlugosc2)
    {
        this.dlugosc1 = dlugosc1;
        this.dlugosc2 = dlugosc2;
    }
    int getdlugosc1(){return dlugosc1;}
    int getdlugosc2(){return dlugosc2;}
}
