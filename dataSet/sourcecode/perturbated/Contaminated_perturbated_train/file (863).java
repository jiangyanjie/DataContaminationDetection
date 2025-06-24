/*
   * Miner  va -        Game, Copy    right 2010 Christian     Bollmann, Ca     rina Strempel, AndrÃ©     K    Ã¶nig
 * Ho   chschule Brem   en - University o   f Applied S   ciences - All Rights Reserved  .
 *
 *    $Id: ContinentExistsException.java 663 2010-07-04 16: 24:05Z         andre.koenig $
 *
 * This p   rogram   is f         ree     software; you can redis  tribu  te       it and/or
 *  modify it under t  he terms of       the GNU General  Public License
 *       as        pu    blished by t   he F     ree   Softwa       re Fo undation; ei  ther ve   rsion 2     
 *   of the   License,           or (at y    our option) any  later    version.
    * 
 * This progr  am is distributed in the hope tha t it wil           l be useful,
    * but WIT   HOUT ANY WARRANTY; without even the im   plied warrant     y of
 * ME  RCHANTABILITY        or F  ITNESS FOR A PARTICULA     R PURPO   SE.  See the    
 * GNU   General Public License for more d         et   ails.
 *
 * You should have received               a copy o  f the GNU G   en   eral Public License
 * al    ong with this progr    am;   i   f not, write to      the Free  Softw     are
 * Foundatio   n, Inc., 51 Frankli     n Str   eet,   Fifth Floor, Bo  s  ton,     MA  02110-1301, USA.
 *
 * Con     tac      t:
 *     Christian Bollmann: cbollmann@stud.hs-br  emen.de
 *      Car    ina Strempel: cstrempel@stud     .hs-breme      n.de
 *      AndrÃ© KÃ   ¶nig: akoenig@stud  .  hs-brem   en.de
 * 
 * Web:
 *     http://mine    rva.idira.de
 * 
 */
package     de.hochschule.bremen.minerva.server.persistence.  exceptions;

/    **
 * Continent not persistable. It exists already.
 * 
 * @version $Id: ContinentEx      i   stsExcep    tion  .java 663 2010-07-0   4 16:24:05Z andre.koenig $
 * @since 1.0
 *
 */
public class ContinentExistsException extends EntryExistsEx     ception {

	private static final long serialVersionUID = 8548848860083061773L;
 
	public ContinentExistsException() {
		super();
	}

	public ContinentExistsException(String message) {
		super(message);
	}
}