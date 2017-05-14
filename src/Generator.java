import java.math.BigInteger;
import java.util.Random;

import static java.lang.StrictMath.ceil;

/**
 * Created by stfcr on 5/14/2017.
 */
public class Generator {
    private BigInteger p;
    private BigInteger q;
    private BigInteger alpha;

    public Generator(){
        generate();
    }

    private void generate(){
        q=new BigInteger("11");
        p=q.multiply(new BigInteger("2")).add(new BigInteger("1"));
    }

    public int  generateAlpha(){
        Random r= new Random();
        int a;
        while(true) {
            do {
                a = r.nextInt(p.intValue() - 2);
            } while (a < 2);
            BigInteger alpha=new BigInteger(String.valueOf(a));
            if(Jacoby(alpha,p)==1)
            {
                alpha=(p.subtract(alpha)).mod(p);
            }
            this.alpha=alpha;
            return alpha.intValue();
        }
    }

    public int disecreteLog(){
        int b;

        Random r= new Random();
        do{
            b=r.nextInt(p.intValue()-2);
        }while(b<2);
        int ln= (int) ceil(Math.sqrt(p.intValue()-1));
        int []firstelements=new int[ln];
        int []secondelements=new int[ln];
        firstelements[0]=0;
        secondelements[0]=1;
        for(int i=1;i<ln;i++)
        {
            firstelements[i]=firstelements[i-1]+1;
            secondelements[i]=secondelements[i-1]*2;
        }
        int alpha=this.alpha.intValue();

        int alpham = (int) Math.pow(alpha,(ln));

        BigInteger alphaminusm=new BigInteger(String.valueOf(alpham)).mod(p).modInverse(p);
        for(int i=0;i<ln;i++)
        {   BigInteger newB=new BigInteger(String.valueOf(b));
            BigInteger rez1=alphaminusm.pow(i);
            BigInteger rez=newB.multiply(rez1);
            BigInteger rezultat=new BigInteger(String.valueOf(rez)).mod(p);
            int find1=inVector(firstelements,rezultat.intValue(),ln);
            int find2=inVector(secondelements,rezultat.intValue(),ln);
            if(find1!=-1)
                return (i*ln)+find1;
            if(find2!=-1)
                return (i*ln)+find2;
        }
    return -1;
    }

    public int SilverPolig(){
        p=new BigInteger("13");
        alpha=new BigInteger("2");
        BigInteger beta= new BigInteger("10");
        int ln= (int) ceil(Math.sqrt(p.intValue()-1));
        int [] factoriP=new int[ln];
        int p=this.p.intValue();
        int j=2;
        p--;
        int k=0;
        System.out.println(p);
        for(int i=0;i<ln;i++) {
            if (p % j == 0) {
                factoriP[k] = j;
                p /= j;
                k++;
            } else {
                j++;
            }
        }
        int []factoriDistincti=new int[ln];
        k=0;
        factoriDistincti[0]=factoriP[0];
        for(int i=1;i<ln;i++)
            if(factoriP[i]!=factoriP[i-1])
            {
                factoriDistincti[++k]=factoriP[i];
            }
        for(int i=0;i<ln;i++)
            System.out.print(factoriDistincti[i]+" ");
        System.out.println();
        int[] factoriRidicati=new int[ln];
        k=0;
        factoriRidicati[0]=factoriP[0];
        for(int i=1;i<ln;i++)
        {
            if(factoriP[i]==factoriP[i-1])
            {
                factoriRidicati[k]*=factoriP[i];
            }
            else{
                k++;
                factoriRidicati[k]=factoriP[i];
            }
        }
        return -1;
    }
    public int inVector(int []elements,int element,int ln)
    {
        for(int i=0;i<ln;i++)
            if(elements[i]==element)
                return i;
        return -1;
    }
    private int Jacoby(BigInteger e,BigInteger n){
        int a=e.intValue();
        int p=n.intValue();
        if(p<=0 || p%2==0)
            return 0;
        int j=1;
        if(a<0){
            a=-a;
            if(p%4==3)
                j=-j;
        }
        while(a!=0){
            while(a%2==0)
            {   a/=2;
                if(p%8==3 || p%8==5) {
                    j=-j;
                }
            }
            int aux=a;
            a=p;
            p=aux;
            if(a%4==3 && p%4==3)
            {
                j=-j;
            }
            a=a%p;
        }
        if(p==1)
            return j;
        return 0;
    }
}
