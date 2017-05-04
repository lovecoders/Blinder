package modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javabeans.Localizacion;
import javabeans.Preferencias;
import javabeans.Usuario;

public class GestionComunicacion {

        //VERIFICAR IP de la máquina que ejecuta el cliente!!!
        String ip = "192.168.1.105";
        int puertoRegistro = 8000;
        int puertoLocalizador = 9000;

        public void enviarAlta(int telefono, String nick, boolean sexo, int edad) {
            try {
                Socket sc = new Socket(ip, puertoRegistro);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("0"); //indica al servidor que el JSON que va a recibir es "tipo 0" (JSON de alta)
                JSONObject job = new JSONObject();
                job.put("telefono", telefono);
                job.put("nick", nick);
                job.put("sexo", sexo);
                job.put("edad", edad);
                salida.println(job.toString());
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void enviarAlta(Usuario usuario) {
            try {
                Socket sc = new Socket(ip, puertoRegistro); //VERIFICAR IP de la máquina que ejecuta el cliente!!!
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("0"); //indica al servidor que el JSON que va a recibir es "tipo 0" (JSON de alta)
                JSONObject job = new JSONObject();
                job.put("telefono", usuario.getTelefono());
                job.put("nick", usuario.getNick());
                job.put("sexo", usuario.isSexo());
                job.put("edad", usuario.getEdad());
                salida.println(job.toString());
                System.out.println("Alta Enviada: "+job.toString());//TRAZA
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void enviarPreferencias(Preferencias preferencias) {
            try {
                Socket sc = new Socket(ip, puertoRegistro);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("1"); //indica al servidor que el JSON que va a recibir es "tipo 1" (JSON de preferencias)
                JSONObject job = new JSONObject();
                job.put("prefsexo", preferencias.getSexo());
                job.put("edadmin", preferencias.getEdadmin());
                job.put("edadmax", preferencias.getEdadmax());
                job.put("prefrelacion", preferencias.isRelacion());
                job.put("nick", preferencias.getNick());
                salida.println(job.toString());
                System.out.println("Preferencias enviadas: "+job.toString());//TRAZA
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void enviarNick(String nick) {
            try {
                Socket sc = new Socket(ip, puertoRegistro);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("2"); //indica al servidor que el JSON que va a recibir es "tipo 2" (JSON de nick)
                JSONObject job = new JSONObject();
                job.put("nick", nick);
                salida.println(job.toString());
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void enviarLocalizacion(String nick, double longitud, double latitud, boolean opcion) {
            try {
                Socket sc = new Socket(ip, puertoLocalizador);
                System.out.println("Entrada en enviarLocalizacion");
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("0"); //indica al servidorLoc que va a recibir JSON de localización
                JSONObject job = new JSONObject();
                job.put("nick", nick);
                job.put("longitud", longitud);
                job.put("latitud", latitud);
                job.put("opcion", opcion);
                salida.println(job.toString());
                System.out.println("CADENA DE LOCALIZACIÓN ENVIADA: "+job);//TRAZA
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void enviarLocalizacion(Localizacion localizacion) {
            try {
                Socket sc = new Socket(ip, puertoLocalizador);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                JSONObject job = new JSONObject();
                job.put("nick", localizacion.getNick());
                job.put("longitud", localizacion.getLongitud());
                job.put("latitud", localizacion.getLatitud());
                job.put("opcion", localizacion.isOpcion());
                salida.println(job.toString());
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Usuario> recuperarCoincidentes(String nick) {
            //este metodo tiene que recibir un nick para poder volcar los coincidentes.
            //creamos un método para recuperar los usuarios coincidentes con nuestras preferencias.
            //enviarNick(nick);
            System.out.println("NICK ENVIADO "+nick);//TRAZA
            //primero enviamos el nick para que se busquen en la base de datos los coincidentes
            //por preferencias
            ArrayList<Usuario> usuariosCoincidentes = new ArrayList<>();
            //creamos un arrayList de Usuarios en el que vamos a volcar los JSON recibidos.
            try {
                System.out.println("ENTRA EN TRY de recuperarCoincidentes");//TRAZA
                String cad = ""; //contendra la cadena JSON
                String s;
                Socket sc = new Socket(ip, puertoRegistro);
                //abrimos un nuevo socket en el puerto 8000, ya que queremos recibir los objetos JSON
                //del servidor.
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("2"); //indica al servidor que el JSON que va a recibir es "tipo 2" (JSON de nick)
                JSONObject jobnick = new JSONObject();
                jobnick.put("nick", nick);
                salida.println(jobnick.toString());
                System.out.println("Solicitud de matches enviada para nick "+jobnick.toString());//TRAZA
                BufferedReader bf = new BufferedReader(new InputStreamReader((sc.getInputStream())));
                //leemos lo que el servidor nos envía.
                while ((s = bf.readLine()) != null) {
                    cad += s;
                }
                //creamos el objeto JSONArray a traves de la cadena JSON recibida
                JSONArray jarray = new JSONArray(cad);
                System.out.println("CADENA RECIBIDA "+cad);//TRAZA
                //recorremos el array y por cada objeto json de ese array
                //creamos un objeto Usuario
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject job = jarray.getJSONObject(i);
                    Usuario usuario = new Usuario(job.getInt("telefono"), job.getString("nick"),
                            job.getBoolean("sexo"), job.getInt("edad"));
                    usuariosCoincidentes.add(usuario);
                }
                sc.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return usuariosCoincidentes;
        }

        public void enviarTelefono(int telefono) {
            //enviamos el telefono para saber si existe en la base de datos
            //e impedir el duplicado en el registro.
            try {
                Socket sc = new Socket(ip, puertoRegistro);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                salida.println("4"); //indica al servidor que el JSON que va a recibir es "tipo 4" (JSON de telefono)
                JSONObject job = new JSONObject();
                job.put("telefono", telefono);
                salida.println(job.toString());
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    public ArrayList<Usuario> recuperarMatches(String nick) {
        //creamos un método para recuperar los usuarios coincidentes a menos de un kilometro.
        //primero enviamos el nick para que se busquen en la base de datos los coincidentes
        //por preferencias
        ArrayList<Usuario> matchesCercanos = new ArrayList<>();
        //creamos un arrayList de Usuarios en el que vamos a volcar los JSON recibidos.
        try {
            System.out.println("ENTRA EN TRY de RecuperarMatches");//TRAZA
            String cad = ""; //contendra la cadena JSON
            String s;
            //abrimos un nuevo socket en el puerto 9000, ya que queremos recibir localizaciones
            Socket sc = new Socket(ip, puertoLocalizador);
            PrintStream salida = new PrintStream(sc.getOutputStream());
            salida.println("1"); //solicita al servidorLoc que envíe JSON de matches
            salida.println(nick);//envía nick al servidorLoc para que recupere los datos de localización
            BufferedReader bf = new BufferedReader(new InputStreamReader((sc.getInputStream())));
            //leemos lo que el servidor nos envía.
            while ((s = bf.readLine()) != null) {
                cad += s;
                System.out.println("Recibiendo String de matches: "+s);
            }
            //creamos el objeto JSONArray a traves de la cadena JSON recibida
            JSONArray jarray = new JSONArray(cad);
            System.out.println("CADENA de match próximo RECIBIDA "+cad);//TRAZA
            //recorremos el array y por cada objeto json de ese array
            //creamos un objeto Usuario
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject job = jarray.getJSONObject(i);
                Usuario usuario = new Usuario(job.getInt("telefono"), job.getString("nick"),
                        job.getBoolean("sexo"), job.getInt("edad"));
                matchesCercanos.add(usuario);
            }
            sc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return matchesCercanos;
    }

        public boolean existeUsuario() {
            boolean existe = false;
            try {
                String cad = ""; //contendra la cadena JSON
                String s;
                Socket sc = new Socket(ip, puertoRegistro);
                //leemos la cadena enviada por el servidor para ver si envia true or false
                BufferedReader bf = new BufferedReader(new InputStreamReader((sc.getInputStream())));
                while ((s = bf.readLine()) != null) {
                    cad += s;
                }

                JSONObject jobj=new JSONObject(cad);
                //creamos el objeto JSONObject con la cadena de texto recibida
                //y lo transformamos a String
                String resultado=jobj.toString();

                if(resultado=="1"){
                   existe=true;
                }else{
                    existe=false;
                }

                sc.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return existe;
        }

    }

