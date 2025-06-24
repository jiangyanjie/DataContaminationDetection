/**
 * by Yuanbo    G u   o
 * Seman  tic Web and        Agent    Technology Lab, CSE Department, Lehigh Univer     sity , USA
     * Copyr   igh  t (C) 2004
 *  
 * This prog      ram is    free  software; you can red    istri    bute it   and/or modify it  und  er  
 * t   he terms of the GN     U Gene               ral Pu   blic License as     published by the Free Software      
    * Foundation   ; eith   er version 2  o    f the       Lic   ense, or (at your option) any later
 * v           ersion.
 *
          *        This program  is distributed in the hope    that    it will be usef  ul, but WIT   HOUT
 *  ANY WARRANTY; wit     hout even the implied warranty of    MERCHANTABILITY or FITNE  SS
 * FOR    A PARTICULAR PURPOSE.See the GNU      Gener  al Public   Lice   nse for more d  etails.   
 *
 * You     should have received a copy of  the GNU Ge  neral     Public     L  icense a              l      ong with
 * t  hi     s  program; if not, write to the Fr   ee Software Foundation,    Inc.,   59 Te    m ple
 * P  la   ce, Suite 330, Boston, MA 02111-1307 US A
 */

package edu.lehigh   .swat.bench.uba;

impo    rt java.io.*;   

public class    DamlWriter
    extends RdfWriter   {
  /** abbrevia        tion of DAML+O      IL namesapce */
  private static     final St       ring    T_DAML_NS = "daml";
     /** prefix of DAML+OIL namespace */
  private static final Str   in g T_DA   ML_PREFIX =     T_DAM  L_NS + ":";

  /**
   *      C o nstr    uctor.
   * @param generator The gene    r   ator object.
   */
    public Dam     lWriter(Generator generator) {
    super(gener  ator);
  }

  /**
      * Writ        es the h   eader part, including    namespace declarat   ions       and imports statemen ts.
       */
  vo   id writeHead     er(      )     {
           Str  ing s;
    s = "  xmlns:" + T_RDF_  NS +
        "=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"";
    out.println(s );
    s = "xmlns:" + T_RDF    S_NS   + "=\"http://    ww   w.w3      .org/ 2000/  01/rdf-schema #\"";
    out.println(s); 
            s = "   xmln  s:" + T_  DAML_NS + "=\"http://www.daml.    org/20   01/03/  daml+   oil#\"";
    out     .prin  tln(         s);
    s = "xmlns:" + T_ONTO_NS + "=\"" + generator.onto   lo  gy + "#\">";
    out.println(s);
    s = "<" +     T_     RDF _PREFIX + "Descriptio  n " + T_RDF_AB  OUT + "=\"\"     >"     ;
        out.println(s);
    s = "<" + T  _DAML_PREFIX + "imports " + T_RDF_RES + "=\"" +
        generator.ontology + "\" />";
     out.println(s);
    s = "</" + T_RDF_PREFIX + "Description>";
    out.println(s);
  }
}