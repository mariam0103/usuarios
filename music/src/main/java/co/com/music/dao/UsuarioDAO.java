package co.com.music.dao;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import co.com.music.exception.musicException;
import co.com.music.model.Usuario;

@Repository
public class UsuarioDAO {

	private static final String GENERO = "genero";
	private static final String TIPO_MUSICA = "tipoMusica";
	private static final String CLAVE = "clave";
	private static final String NOMBRE_USUARIO = "nombreUsuario";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String CORREO = "correo";
	private static final String TELEFONO = "telefono";
	private static final String SEGUNDO_APELLIDO = "segundoApellido";
	private static final String PRIMER_APELLIDO = "primerApellido";
	private static final String NOMBRE = "nombre";
	MongoClient mongo;
	DB db;
	DBCollection table;
	
	@SuppressWarnings("deprecation")
	private void inicializarBD() {
		mongo = crearConexion();
		db = mongo.getDB("database");
		table = db.getCollection("musicbackground");
	}
	
	
	public void crearUsuario(Usuario usuario) {
		try {
			inicializarBD();
			BasicDBObject document = new BasicDBObject();
			document.put(NOMBRE, usuario.getNombre());
			document.put(PRIMER_APELLIDO, usuario.getPrimerApellido());
			document.put(SEGUNDO_APELLIDO, usuario.getSegundoApellido());
			document.put(TELEFONO, usuario.getTelefono());
			document.put(CORREO, usuario.getCorreo());
			document.put(FECHA_NACIMIENTO, usuario.getFechaNacimiento());
			document.put(NOMBRE_USUARIO, usuario.getNombreUsuario());
			document.put(CLAVE, usuario.getClave());
			document.put(TIPO_MUSICA, usuario.getTipoMusica());
			document.put(GENERO, usuario.getGenero());
			table.insert(document);
		} catch (Exception e) {
			throw new musicException("Ocurrio un error creando el usuario");
		}
	}

	private static MongoClient crearConexion() {
		MongoClient mongo = null;
		try {
			mongo = new MongoClient("localhost", 27017);
		} catch (Exception e) {
			throw new musicException("Ocurrio un error conectandose a la BD");
		}
		return mongo;
	}

	public Boolean buscarUsuario(Usuario usuario) {
		try {
			inicializarBD();
			BasicDBObject query = new BasicDBObject();
			query.put(NOMBRE_USUARIO, usuario.getNombreUsuario());
			query.put(CLAVE, usuario.getClave());

			DBCursor cur = table.find(query);
			while (cur.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			throw new musicException("Ocurrio un error buscando el nombre y clave del usuario");
		}
		return false;
	}

}
