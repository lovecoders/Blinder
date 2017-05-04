package javabeans;

/**
 * Created by USUARIO on 25/04/2017.
 */

public class Usuario {
    private int telefono;
    private String nick;
    private boolean sexo;
    private int edad;

    public Usuario(int telefono, String nick, boolean sexo, int edad) {
        this.telefono = telefono;
        this.nick = nick;
        this.sexo = sexo;
        this.edad = edad;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}

