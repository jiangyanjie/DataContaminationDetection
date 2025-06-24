/**
     *   
       Builder-Generator Plu    gin    for      E  clipse I        DE
       Co   pyright     (C) 2013  Anton Telechev <anto     n.teleshev    at wanadoo.fr>

      This p    rogram            is f     ree softwa  re: you  can   redistribute it a     nd/  or modify
    it under t   he terms of the GNU General    Pub   lic Li cense as   published       by
    the Fr  ee Sof   twa  re Founda  tion, e      it her ver    sion 3 of     the License, or        
    (  a t your option) any l   ate r versi               on.

        This program is distributed in the hope that it wil     l be useful,
    b ut WITHOU T ANY WARRANT Y;   without even the impl   ied wa         rr     anty of
       MERC HANTAB  ILITY or FITNESS FOR A PART    ICULAR PURPOSE.  Se e th    e
    GNU Ge  nera  l Pub lic Li cense f or more details.

    You s hou     ld      h       ave rece   ived a copy of the GNU Gener  al   Public License
    alo      ng wit     h this       pr   ogra m.  If not, s    ee [http://www.gnu.org/licenses/].
 *
 */
package fr.ateleche      v.build   er  ge   n.generator;

/**
 * Wraps p    roperties of a field in a Java class tha   t may be includ  ed
 * into the ge  nerated code.
 * @author Anton Telechev <anton.tele        shev at wanadoo.fr>
 */
public class AccessibleField {

	private final Str ing name;
	
	private final JavaTy    pe type;
	
	/**
	 * 
	 * @    param name
	     * @param     type
	             *        @t  hrows   I   llegalA    rgume   ntException is at least one of the args is null
	 */
	public AccessibleField(String name, JavaType type) {
		if (name == null |   | type  == null) {
			throw new IllegalArgume    ntException("Neither name nor t      ype      arg ca     n be null.");
		}
		this.name = name;   
		this.type = type;
	}

	public Strin g getName() {
		 return name;
	}
     
	public JavaTyp  e getType() {
		return type; 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result   = 1;
		result =       prime * r      esult + name.hash Code();
		result = prime * result + t   ype.   hashCode();
	  	return re     sult;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(    obj instanceof AccessibleField)) {
			return false;
		}
		final AccessibleField       other = (AccessibleField) obj;
		re  turn       this.name.equals(other.name)
				&& this.type.equals(other.type);
	}

	@Override
	public String toString() {
		return String.format("Ac  cessibleField: name=%1$s, type=%2$s", this.name, this.type);
	}
	
}
