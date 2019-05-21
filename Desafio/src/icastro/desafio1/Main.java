package icastro.desafio1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import icastro.desafio1.model.Datos;

/**
 * Objetivo: obtener periodos faltantes
 * @author icastro
 *
 */
public class Main {
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Datos datos = llenarDatos();
		
		System.out.println("Fecha Creacion: " + df.format(datos.getFechaCreacion()));
		System.out.println("Fecha Fin: " + df.format(datos.getFechaFin()));
		
		List<Date> listaPeriodos = periodosCreacionFin(datos.getFechaCreacion(), datos.getFechaFin());
		for (Date fechaLista : listaPeriodos) {
			boolean existe = false;
			for (Date fecha : datos.getFehas()) {
				if (fechaLista.compareTo(fecha) == 0)
					existe = true;
			}
			if (!existe)
				System.out.println("falta periodo: " + df.format(fechaLista));
		}
				
	}
	
	
	/**
	 * Metodo que determina los periodos entre fechaCreacion y fechaFin
	 * @param fechaCreacion (Date)
	 * @param fechaFin (Date)
	 * @return List<Date> lista de periodos
	 */
	public static List<Date> periodosCreacionFin(Date fechaCreacion, Date fechaFin) {
		Date fechaPeriodo = fechaCreacion;
		List<Date> lista = new ArrayList<>();
		do {
			Date fecha = new Date();
			fecha.setTime(fechaPeriodo.getTime());
			lista.add(fecha);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaPeriodo);
			cal.add(Calendar.MONTH, 1);
			fechaPeriodo.setTime(cal.getTime().getTime());			
		} while (fechaFin.compareTo(fechaCreacion) >= 0);
		
		return lista;
	}
	
	public static Datos llenarDatos() {
		Datos datos = new Datos();
		try {
            URL url = new URL("http://127.0.0.1:8080/periodos/api");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error : HTTP Error code : "+ conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String inputline;
            StringBuffer response = new StringBuffer();
            while((inputline = br.readLine()) != null) {
            		response.append(inputline);
            }
            System.out.println(response);
            br.close();
            JSONObject json = new JSONObject(response.toString());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            datos.setId(json.getInt("id"));
            datos.setFechaCreacion(df.parse(json.get("fechaCreacion").toString()));
            datos.setFechaFin(df.parse(json.get("fechaFin").toString()));
            
            JSONArray jsonArray = json.getJSONArray("fechas");
            List<Date> fechas = new ArrayList<>();
            for (int i = 0; i < json.getJSONArray("fechas").length(); i++) {
            		fechas.add(df.parse(jsonArray.get(i).toString()));
            }
            datos.setFehas(fechas);

        } catch (Exception e) {
        		e.printStackTrace();
        }
		
		return datos;
	}

}
