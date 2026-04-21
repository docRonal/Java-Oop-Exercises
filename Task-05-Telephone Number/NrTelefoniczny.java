import java.util.TreeMap;

public class NrTelefoniczny implements Comparable<NrTelefoniczny>
{
    
    private int nrKierunkowy;
    private int nrTelefonu;
    public NrTelefoniczny(int nrKierunkowy, int nrTelefonu)
    {
        this.nrKierunkowy = nrKierunkowy;
        this.nrTelefonu = nrTelefonu;
    
    }
    public int getnrKierunkowy() 
    {
        return nrKierunkowy;
    }
    public int getphoneNumber()
        {    
        return nrTelefonu;
        }
    
    @Override
    public int compareTo(NrTelefoniczny o)
    {
    if(this.nrKierunkowy != o.nrKierunkowy)
        {
        return Integer.compare(this.nrKierunkowy, o.nrKierunkowy);
        }
    return Integer.compare(this.nrTelefonu, o.nrTelefonu);
    }
    @Override
    public String toString()
    {
    return "(" + nrKierunkowy + ") " + nrTelefonu;    
}


}
