/*
 * Copyright (C) 2016 Mariana
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.proyectoHeliox.helper.negocio;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.proyectoHeliox.helper.persistencia.BotonPersistencia;
import org.proyectoHeliox.helper.persistencia.LenguajePersistencia;

/**
 *
 * @author Mariana
 */
public class DefaultValues {
    String active = System.getProperty("user.dir");
    BotonPersistencia b = new BotonPersistencia();
    LenguajePersistencia l = new LenguajePersistencia();

    public void agregarLenguajes() throws SQLException, IOException {
        l.agregarLenguaje("Español", System.getProperty("user.dir") + File.separator+ "icons/españa.png" );
           System.out.println("Agregados Lenguajes");
    }
//        String rutaIcon, String descripcion, String rutaEjecutable, String rutaAudio, int idLenguaje) 

    public void agregarBotones() throws SQLException, IOException {
        b.agregarBoton( active+"\\icons\\10cosas.png", "Lea el texto 10 cosas que todo hablante debe saber acerca de su lengua.", active +"\\material\\10cosasEs.pdf", active+"\\speech\\es_MX\\10cosas_es_MX.ogg", 1);
        b.agregarBoton(active +"\\icons\\compartiendosaberes.png", "Visite el sitio web de compartiendo saberes.", "http://www.compartiendo-saberes.org/",active + "\\speech\\es_MX\\firefox http_--www.compartiendosaberes.org_es_MX.ogg", 1);
        b.agregarBoton("/icons/inali.png", "Visite el sitio web del Instituto Nacional de Lenguas Indígenas.", "http://inali.gob.mx", "/speech/es_MX/inali_es_MX.ogg", 1);
        b.agregarBoton("/icons/ley.png", "Ley General de Derechos Lingüísticos de los Pueblos Indígenas.", "/material/ley-es.pdf", "/speech/es_MX/ley_es_MX.ogg", 1);
        b.agregarBoton("/icons/enadis.png", "Consulta la Encuesta Nacional de Discriminación 2012.", "/material/enadis-es.pdf", "/speech/es_MX/ley_es_MX.ogg", 1);
        b.agregarBoton( "/icons/guardavoces.png", "Consulta el enlace para niños “Los Guardavoces”", "http://site.inali.gob.mx/guarda_voces/", "/speech/es_MX/guardavoces_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Busca información y explora la Red Internet con FIREFOX", "http://www.compartiendo-saberes.org/", "/speech/es_MX/firefox http_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Escribe tus documentos con LIBREOFFICE.", "libreoffice", "/speech/es_MX/libreoffice_es_MX", 1);
        b.agregarBoton("/icon.png", "Organiza tus fotografías con GPICVIEW.", "/material/ley-es.pdf", "/speech/es_MX/gpicview_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Reproduce tus videos y tu música con VLC", "vlc", "/speech/es_MX/vlc_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Habla con tus amigos con PIDGIN", "pidgin", "/speech/es_MX/pidgin_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Lee todo tipo de documentos con OKULAR.", "okular", "/speech/es_MX/okular_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Comparte tus archivos con TRANSMISSION.", "transmission", "/speech/es_MX/pidgin_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Graba tus CDs o DVDs con BRASERO", "brasero", "/speech/es_MX/brasero_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Graba imágenes y videos con tu cámara Web", "cheese", "/speech/es_MX/cheese_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Graba sonido y edita tus grabaciones con AUDACITY", "audacity", "/speech/es_MX/audacity_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Edita tus grabaciones de vídeo con OPENSHOT.", "openshot", "/speech/es_MX/openshot_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Amplia la pantalla con KMAG.", "kmag", "/speech/es_MX/kmag_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Activa un teclado en la pantalla.", "onboard", "/speech/es_MX/onboard_es_MX.ogg", 1);
        b.agregarBoton("/icon.png", "Ajusta tu monitor o pantalla", "enviacam", "/speech/es_MX/enviacam_es_MX.ogg", 1);
        b.agregarBoton("/icons/terminal.png", "Abre una TERMINAL para manejar tu sistema mediante la línea de comandos", "exterm", "/speech/es_MX/terminal_es_MX.ogg", 1);
        System.out.println("Agregados Botones");

    }

}
