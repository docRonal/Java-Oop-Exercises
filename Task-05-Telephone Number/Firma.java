public class Firma extends Wpis 
{
    private String nameFirm;
    public Firma (String nameFirm, String address)
    {
        super(address);
        this.nameFirm = nameFirm;
    }
    
    @Override
    public void opisz()
    {
    System.out.println("Firma: " + nameFirm + ", Address: " + address);
    }
}
