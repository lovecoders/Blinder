package javabeans;



public class Preferencias {
    private int sexo;
    private int edadmin;
    private int edadmax;
    private boolean relacion;
    private String nick;

    public Preferencias(int sexo, int edadmin, int edadmax, boolean relacion, String nick) {
        this.sexo = sexo;
        this.edadmin = edadmin;
        this.edadmax = edadmax;
        this.relacion = relacion;
        this.nick = nick;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getEdadmin() {
        return edadmin;
    }

    public void setEdadmin(int edadmin) {
        this.edadmin = edadmin;
    }

    public int getEdadmax() {
        return edadmax;
    }

    public void setEdadmax(int edadmax) {
        this.edadmax = edadmax;
    }

    public boolean isRelacion() {
        return relacion;
    }

    public void setRelacion(boolean relacion) {
        this.relacion = relacion;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
