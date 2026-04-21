public  class Osoba extends Wpis 
{
    private String imie;
    private String nazwisko;
    public Osoba(String imie, String nazwisko, String address)
    {
    super(address);
    this.imie = imie;
    this.nazwisko = nazwisko;
    }
    String getName()
    {
        return imie;
    }
    String getSername()
    {
        return nazwisko;
    }
    @Override
    public void opisz()
    {
        System.out.println("Osoba: " + imie + " " + nazwisko + ", Adres: " + address);
    }

}
