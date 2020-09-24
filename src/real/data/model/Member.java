package real.data.model;


public class Member {
    String fname;
    String names;
    String idno;
    String hone;
    String pdl;
    String tdb;
    String dl;
    int fecatt;
   
 String category;

    public Member(String fname, String names, String idno, String hone, String pdl,  String tdb,String dl,String  category,Integer fecatt) {
        this.fname = fname;
        this.names = names;
        this.idno = idno;
        this.hone = hone;
        this.pdl = pdl;
         this.tdb=tdb;
         this.dl=dl;
         this.fecatt=fecatt;
       this.category=category;
    }

   

   
    
    

    public String getFname() {
        return fname;
    }

    public String getNames() {
        return names;
    }

    public String getIdno() {
        return idno;
    }
   

    
    

    public String getHone() {
        return hone;
    }

    public String getPdl() {
        return pdl;
    }

   

    public String getCategory() {
        return category;
    }

    public String getTdb() {
        return tdb;
    }

    public String getDl() {
        return dl;
    }

    public int getFecatt() {
        return fecatt;
    }
    

   
    
    
}
