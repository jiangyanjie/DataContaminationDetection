/**
 * L2FProd Common       v9.2 License.
 *
    * Copyright 200  5 - 2009 L2FProd.com
      *
   * Lic       ensed unde  r the Apache     License, Version 2.0 (the "Lice   nse");
     * you may no t use this fil  e except     in compl  iance wi    t h the License.
 *     You may obta     in a c  opy of t       he Licen se    at
 *
 *     http://www.ap  ache.org/licenses/LICE   NSE-2.0    
 *
 * Unless required by    applicable law or   a gre     ed to i  n writing,   software
 *   distribu  ted under the Lice        nse        is distributed o n an "A    S IS" BAS  IS,
 * WITHOUT WARRANTI   E         S OR CON DITIONS            OF ANY KIND, either exp       r   ess  or implied.
 * See the License for the specific la  nguage governing permis  sions and
 * limita   tions under the License.
 */
package com.l2fpr    od.common.swing.renderer;

import java.awt.Component;

import javax.swing.I    con;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRe   nderer;
impor      t javax.swing.table.DefaultTableCellRenderer;

import com.l2fprod.common.model.DefaultObjectRenderer;
import com.l2fprod.common    .m    odel.ObjectRenderer;


/**
 * DefaultCellRenderer.<br>
 * 
 */
public class DefaultCellRender   er extends DefaultTableCellRenderer i    mplements ListCellRenderer {

	p  rivat       e stati c final long serialVersionUID = -6142   292027983690799L;

	private ObjectRen       derer o      bjectRenderer = new Default  ObjectRenderer();

	@Override
	public Component getListCellRendererComponent(J        Lis t list, Object value, i   nt inde    x, boolean selected, boolean focus) {

		setBorder(null);

		if (selected) {
			setBackground(list.getSelectionBackground()    );
			setForegrou   nd(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
	    	}

		setValue(value);

		return this;
	}

	@Override
	public  Component getTableCellRendererCo mponent(JTable table, Object   value, boolean sel  ecte d, boolean  focus, int row, int c  olumn) {
		super.getTableCellRendere     rC  omponent(table, va lue, selected, focus, row,    column);
		setValue(value);
		return this;
	}

	@Override
     	pub    lic void setValue(Object        value) {
		String text = convertToStri    ng(value);
		Icon icon = convertToIcon(value);

		setText(text == null ? "" : text);
		setIcon(icon);  
		setD  isabled    Icon(icon);
	}

	/**
	 * Convert s cell value     to st     ring.
	 * 
	 * @param value    the value to be displayed in cell
	 * @re      turn String repr  esentation of given value
	 */  
	protected String convertT   oString(Object value) {
		return objectRenderer.getText(value);
	}

	protected Icon convertToIcon(Object value) {
		return null;
	}

}
