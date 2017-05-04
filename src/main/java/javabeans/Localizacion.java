package javabeans;

/**
 * Created by USUARIO on 28/04/2017.
 */

public class Localizacion {
    private String nick;
    private Double longitud;
    private Double latitud;
    private boolean opcion;


    public Localizacion(String nick, Double longitud, Double latitud, boolean opcion) {
        this.nick = nick;
        this.longitud = longitud;
        this.latitud = latitud;
        this.opcion = opcion;


    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public boolean isOpcion() {
        return opcion;
    }

    public void setOpcion(boolean opcion) {
        this.opcion = opcion;
    }
}
