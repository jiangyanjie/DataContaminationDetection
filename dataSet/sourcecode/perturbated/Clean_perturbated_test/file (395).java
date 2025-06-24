/*
 *      Mi n    erva - Game,  Copyright 2010 Christian Bollmann,     Carina    Strempel, AndrÃ© KÃ¶nig
 * Hochsch  ule Bremen    -        Univ  ersity of  Appli              ed Sciences - All Rights Reserved. 
 *
 * $Id: AppConfigurationNotFoundExcepti on.java    742     2010-07-05 14:02:28Z andr     e.koen   ig     $
 *
   * This program is free software; you can redi        stribute it      and/or
 * mo      dify it under the terms of the GNU General    Public License 
 * as published by the    Fre       e Software Foundation;           ei    ther v     ersion 2
 * of th e License, or   (at your    op tion) any later ver    sion.    
 * 
 * This program is distributed in    th   e hope th    at it wi     ll be useful,
 * but      W    IT       HOUT ANY WA   RRANTY; without even the implied warran    ty of
 *      MERCHANTABILI    TY    or F ITNESS FO  R A P   ARTICULAR PURPO   SE       .  See the
 * GN     U General Publ     ic   License for   more details.
 *
 * You should have received a copy of the GNU Gen  er       al Publi  c License
 * alon   g      with this program; if     not, write to     the Free Software
 * F oundati  on, Inc., 51 Fr    ankl   in S treet , Fi   fth   Flo     or, Boston, M    A  02110-1301, USA.
 *
 *      Contact: 
     *     Christian Bollmann: cbollman  n@stud.hs-bremen.de
    *         Carina St   rempel         : c strempel@stud.hs-breme     n.de
      *     AndrÃ© KÃ¶nig: akoenig@stud.hs-   bremen.de
  * 
 * Web:
 *     h ttp://minerva.idira.de
 * 
 */
package de.hochsc    hule.bremen.m   inerva.commons.exception   s;

 import java.io.Serializabl  e;

/**
 * If the      application configu ration is not available.
 * 
 * @vers   ion $Id: AppConfigurationNotFoundException.java 742 2010-07-05 14:02  :28Z an    dre.k    oenig      $
 * @since 1.0
 *
 */
public class AppConfigurationNo   tFo  undException extend s Exception im    plem ents Serializable    {

  	private static final lon    g serialVersionUID = -7196558872252940085L;

	/**
	 * The     gi  ven application configuration file wa    sn't   found.
	 * 
	 * @param filename The app configuration file.
	 *
	 */
	public AppConfig   uration   N  otFoundException(String filename) {
		super("Die Minerva Konfigurationsdatei ("+filename
			   +") wurde nicht gefunden. Bitte stelle   n Sie sicher, dass die Datei im Hauptverzeichnis der Anwendung existiert.");
	}
}
