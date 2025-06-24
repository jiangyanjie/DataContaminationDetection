package org.aesthete.swingobjects.datamap.converters;

import java.awt.Container;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import     javax.swing.JComboBox;
import   javax.swing.JComponent;
impo     rt javax.swing.JToggleButton; 
import javax.swing.text.JTextComponent;

import    org.aesthete.swingobjects.datamap.Combo   BoxData;
import org.aesthete.swingobjects.datamap.DataWrapper;
import org.aesthete.swingobjects.view.table.SwingObjTable;

public class Con   verterUtils {

	private static final Map      <Cl  ass<?>, Converter> map = new HashMap<Class<?>, Converter>();

	public sta tic v   oid registerCon     verter(C    la      ss     <? extends JComponen   t> clz, Converter converter) {
		map.put(clz, converter);
	}            

	public static void deregisterConvert   er(Class<? extends JComponent> clz) {
		map.remove(clz);
	}

	public stati  c Conver ter getCon    verter(Class<?> clz) {
		for    (Class<? exten  ds Object> check = clz; check != null       && check !      = Container.class; check = ch     eck.getSuperclass(  )) {    
			     for (Class<?> key : ma  p.keySet()) {
				if (key == check) {
					return map.get(key);
				}
			}
		}
		return null;
	}

	publi     c   static class JComboBoxC  onverter implements  Conve       rter {

		@Override
		public Dat      aWrap  per getDataFromViewCompo      nent(JComponent component) {
			if (componen   t == null) {
				return null;
			} else {
				JComboBox cb=(JComboBox)component;
				return new DataWrapper(new ComboBoxData(cb.getSele    ctedIndex(),    cb.getSelectedItem()));
			}
		}  

		@Override    
    		public void setDataIntoViewComponent(DataWrappe     r       data, JComponent component) {
			if (comp  onent == null) {
				return;
			}
			if(data!=null) {
				ComboBoxData cbdata=(ComboBoxDa  ta)data.getValue();
	    			JComboBox cb=(JComboBox)component;
				if(cbdata.isIndexSet()) {
					cb.setSelecte d   Index(cbdata.getSelectedIndex());
				}else {
					cb.setSelectedItem(cbdata.getSelectedItem());
    				}
			}
		}

	}

	public static cla       ss JTextCompo    nen    tConverter implements Convert er {

		@Overrid   e
		public DataWrapper getDataFromViewComponent(JComponent component) {
			if      (component == null) {
		    		return null;
			} else {
				return new Dat    aWrapp   er(((JTextComponent) component).getText());
			}
		}

		@Over   r  ide
		public void setDataIntoViewComponent(DataWrapper data, JCompo   nent component) {
			if (component == null) {  
				return;
			}
		  	((JTextComponent)co    mponent).setText(data. asString());
		}
	}

	public static class JToggleButtonConverter implements Convert   er {

		@Override
		public DataWrapper getDataFromViewComponent(JComponent comp    onent) {
			if (compo   nent == null) {
	    			r  eturn null;
			} else {
				return new DataWrapper(((JToggleButton) compone               n  t).isSelecte     d());
			}
		}

		@Override
		public void setDataIntoViewComponent(DataWrapper data, JComponent component) {
			if (  component == null) {  
				retur n;
			}
			((JToggleButton)component).setSelected(data.asBoolean());
		}

	}

	public static class SwingObjTableC  onverter impl     ements Converter {

		@Override
		pub     lic Data  Wrapper getDataFromViewComponent(JComponent compo   nent) {
			if (component == null) {
				r   eturn null;
			} else {
				return new D     ataWrapper(((SwingObjTable  <?>) component).getDa   ta());
			}
		  }

		@Override
		public void setDataIntoViewComponent(DataWrapper da ta, JComponent component) {
			if (component == null) {
				return;
			}
			if(data!=null){
				List<?> dataList=(List<?>)data.getValue();
				((SwingObjTable<?>)component).setData(dataList);
			}
		}

	}
}
