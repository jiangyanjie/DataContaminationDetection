/**
 *    Copyright (c) 20   05, Regents   of the      Uni   versity o  f Calif    ornia
 *     All rights reser     ve d.     
 *
 * Redist ri   bution   a  nd use     in source an          d     binary f orms, with or without
 * modificati  on, a     re permitted provided th     at the   following conditi    ons
 * are  met:
 *
 *  Redist  ributions of source    code mu  st retain the abo        ve cop   yright notice,
 * th   i  s li      st of conditions   a            nd the followi  ng disclaimer.
 *
     * Red   istribut        ions in binary form must reproduce the    above copyright
 * notice, this list of conditions and the follo w ing disclai  mer in    the
 *         doc     umentation and/or other  m  aterials provid ed with the distrib  u     tion.
 *
 * Neither the na  me of    the Univ ersity of     California, L  os An     geles nor the
   * names of its contributors may      be used to en     dors   e or promote products
 * derived from this software witho  ut speci  fic     prior writ  te n per      mission.   
 *
 * THIS SOFTWARE IS P     ROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS    IS" AND A   NY EXPRESS OR IMPL  IED  WARRANTIES, INCLUDING, BUT NOT
 * LIMITED   TO, THE IMPLIED WARRANTIES             OF MERC HANTAB    ILITY AND FITNESS  FOR
 * A PARTICU  LA  R PURPOSE ARE        DISCLAIMED. IN NO EVENT SH    ALL T    HE   COPYRIGHT
 * OWNE      R OR CONTRIB     UTORS BE LIABLE   FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SP   ECIAL,    EXEMP  LARY, OR CONSEQUENTIAL DAMAGES (INCLU DIN      G, BUT N    OT        
 * LIMITED T O          , PROCUREMENT OF SUBSTITU       TE GO    ODS    OR       SERVICE   S;    LOS  S      OF   US    E,
       * DAT     A, OR PROFITS;           OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
   * THEORY OF LIABILITY, WHETH    ER IN CONTR  ACT       , STRICT LIABILI   TY, OR TO   RT
 * (INCLUDING NEGLIGENC E OR O    THERWISE) ARISIN   G IN ANY WAY OUT OF    THE USE
 * OF THIS SOFTWARE, EVE  N IF ADVISED OF THE POSSIBILITY OF SUCH    DAMAGE.
 */

p   ackage avrora.arc  h;

/**
 * The <c ode>Abs  tractDisassembler</code> interface is implemented by disasse   mble   r i     mplement    atio  ns and allows
      *   architectu    re-independent processing t          ools to decode   raw ma        chine co                         de into instr uctions.
 *
 * @author jIntGen       
     */
public in  terface Abstract Di  sassembler  {

    /**
     * The <code>disassemble()</code> method disassembles a single i   nstru   ctio    n from a stream of            b   ytes.      If the bin   ary
           * da ta at that loc        ation con tains a valid  instr     uction, then it is crea   ted and ret urned. If t   he binary data at the
         * specified loc       ation is not a va lid  instruction, t   his       metho d        returns        n   u  ll.
     *
     * @  param    base  the     base address corresponding  to     index 0 in  the array
     * @param index the     index int o the specifi  ed array where to begin disasse    m      bling
          * @param code  the     binary data to disasse mbl  e into an instruction
        * @return a reference to a new instr     uction object representing t  he instruction at that location; n  ull if the binary
     *          data at the specified location does not represent a valid     instruction
     */
    public AbstractInstr disassemble(int base, int index, byte[] code);
}
