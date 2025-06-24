/*
      * Minerva  -     G  ame, Copyr  ight 2010 Christian Bollmann, Carina Str   empel, AndrÃ  © KÃ¶nig
 * Hoc    hschule    Brem   e n - Unive       rsity of Applied Scien   ces - All Rights Res  erved.
 *
 * $Id: ContinentNotFoundException.j ava 66   3           2010 -07-04 16:24:05Z andre.ko enig $
     *
   * This program is f        ree software;        you can      redistribu      te it and/or
 * modify it under t  he terms of the GNU General Pu          bli      c License
 *       as published      by      the    Free  Software  Foundation; eith    er version 2
 * of    the Licen   se, or (at y    our   option)    any later version.
 *  
 * This program is    distributed in the            h  ope   that it will be useful,   
  * but WITHOUT ANY WARRANTY; without eve  n the implied warranty of
 * ME    RCHANTABILITY     or FITNESS FOR            A                PAR     TICULAR PURPOSE.  See the
 * GNU       Gene ral Publi  c Licens          e for more details.
 *
 * You should    have rece   i  ved   a copy o f      the GNU General Pu blic License
 * along with this prog      ram; if not, w    rite to the Free    Software
 * Founda    ti      o      n, In      c., 51 Frankl  in Stree   t, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Contact:
   *     Christian Bollmann:     cbollmann@stu           d.hs-bremen.de
 *     Ca ri   na Strempel: cstrempel@ stu  d.hs-bremen.de
 *                    AndrÃ© KÃ¶n    ig:     akoenig@stud.hs-br  emen.de
 * 
 * Web:
 *     http://miner  va.idira.de
 * 
 */
package de.hochschule.bremen.mi  nerva.s   erver.persiste   nce.exceptions;

/**
 * Se   arc   hed for an continent which does   not exist.
 * 
 * @version       $Id: ContinentNotFoundException.java 66 3 2010-07-04 16:24:05Z andre.koenig $
    *     @since 1.0
 *
 */
public class ContinentNotFoundException extends EntryNotFoundException {

	private static final long serialVersionUID = 8365171814386246346L;

	public ContinentNotFoundException() {
		super();
	}

	public ContinentNotFoundException(String message) {
		super(message);
	}
}
