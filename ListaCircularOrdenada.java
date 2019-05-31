public class ListaCircularOrdenada <X extends Comparable<X>> implements Cloneable
{
    private class No
    {
        private X  info;
        private No prox;

        public No (X i, No p)
        {
            this.info = i;
            this.prox = p;
        }

        public No (X i)
        {
            this.info = i;
            this.prox = this;
        }

        public X getInfo ()
        {
            return this.info;
        }

        public No getProx ()
        {
            return this.prox;
        }

        public void setInfo (X i)
        {
            this.info = i;
        }

        public void setProx (No p)
        {
            this.prox = p;
        }
    } //fim da classe No

    private No primeiro, ultimo;

    public void insira (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        if (this.primeiro==null/*&&this.ultimo==null*/)
        {
            this.primeiro = new No (i);
            this.ultimo   = this.primeiro;
            return;
        }

        int comparacao=i.compareTo(this.primeiro.getInfo());

        if (comparacao==0)
            throw new Exception ("Informacao repetida");

        if (comparacao<0)
        {
            this.primeiro = new No (i, this.primeiro);
            this.ultimo.setProx (this.primeiro);
            return;
        }

        No atual=this.primeiro;
        for(;;)
        {
            if (atual.getProx()==this.primeiro)
                break;

            comparacao=i.compareTo(atual.getProx().getInfo());

            if (comparacao==0)
                throw new Exception ("Informacao repetida");

            if (comparacao<0)
                break;

            atual=atual.getProx();
        }

        atual.setProx (new No (i,atual.getProx()));

        if (atual.getProx().getProx()==this.primeiro)
            this.ultimo = atual.getProx();
    }

    // revisar
    public void removaDoInicio () throws Exception
    {
        if (this.primeiro==null/*&&this.ultimo==null*/)
            throw new Exception ("Nada a remover");

        if (this.primeiro==this.ultimo)
        {
            this.primeiro=null;
            this.ultimo  =null;
            return;
        }

        this.primeiro = this.primeiro.getProx();
    }

    // revisar
    public void removaDoFim () throws Exception
    {
        if (this.primeiro==null/*&&this.ultimo==null*/)
            throw new Exception ("Nada a remover");

        if (this.primeiro==this.ultimo)
        {
            this.primeiro=null;
            this.ultimo  =null;
            return;
        }

        No atual;
        for (atual=this.primeiro;
             atual.getProx()!=this.ultimo;
             atual=atual.getProx())
             /*comando vazio*/;

        atual.setProx(null);
        this .ultimo=atual;
    }

    // revisar
    public void remova (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        if (this.primeiro==null/*&&this.ultimo==null*/)
            throw new Exception ("Lista vazia");

        int comp=i.compareTo(this.primeiro.getInfo());

        if (comp==0)
        {
            if (this.ultimo==this.primeiro)
                this.ultimo=null;

            this.primeiro=this.primeiro.getProx();

            return;
        }

        if (comp<0)
            throw new Exception ("Informacao inexistente");

        No atual=this.primeiro;

        for(;;)
        {
            if (atual.getProx()==null)
                throw new Exception ("Informacao inexistente");

            comp=i.compareTo(atual.getProx().getInfo());

            if (comp==0)
            {
                if (this.ultimo==atual.getProx())
                    this.ultimo=atual;

                atual.setProx(atual.getProx().getProx());

                return;
            }

            if (comp<0)
                throw new Exception ("Informacao inexistente");

            atual=atual.getProx();
        }
    }

    // revisar
    public boolean tem (X i) throws Exception
    {
        for (No atual=this.primeiro;
             atual!=null;
             atual=atual.getProx())
             if (i.equals(atual.getInfo()))
                 return true;

        return false;
    }

    // revisar
    public String toString ()
    {
        String ret="[";

        No atual=this.primeiro;

        while (atual!=null)
        {
            ret=ret+atual.getInfo();

            if (atual!=this.ultimo)
                ret=ret+",";

            atual=atual.getProx();
        }

        return ret+"]";
    }

    // revisar
    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        ListaSimplesDesordenada<X> lista =
       (ListaSimplesDesordenada<X>)obj;

        No atualDoThis =this .primeiro,
           atualDaLista=lista.primeiro;

        while (atualDoThis !=null &&
               atualDaLista!=null)
        {
            if (!atualDoThis .getInfo().equals(
                 atualDaLista.getInfo()))
                return false;

            atualDoThis =atualDoThis .getProx();
            atualDaLista=atualDaLista.getProx();
        }

        if (atualDoThis!=null)
            return false;

        if (atualDaLista!=null)
            return false;

        return true;
    }

    // revisar
    public int hashCode ()
    {
        int ret=666;

        for (No atual=this.primeiro;
             atual!=null;
             atual=atual.getProx())
             ret = 17*ret + atual.getInfo().hashCode();

        return ret;
    }


    // revisar
    // construtor de copia
    public ListaSimplesOrdenada (ListaSimplesOrdenada<X> modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        if (modelo.primeiro==null)
            return; // sai do construtor, pq this.primeiro ja é null

        this.primeiro = new No (modelo.primeiro.getInfo(),null);

        No atualDoThis   = this  .primeiro;
        No atualDoModelo = modelo.primeiro.getProx();

        while (atualDoModelo!=null)
        {
            atualDoThis.setProx (new No (atualDoModelo.getInfo(),null));
            atualDoThis   = atualDoThis  .getProx ();
            atualDoModelo = atualDoModelo.getProx ();
        }

        this.ultimo = atualDoThis;
    }

    // revisar
    public Object clone ()
    {
        ListaSimplesOrdenada<X> ret=null;

        try
        {
            ret = new ListaSimplesOrdenada (this);
        }
        catch (Exception erro)
        {} // sei que this NUNCA é null e o contrutor de copia da erro quando seu parametro é null

        return ret;
}